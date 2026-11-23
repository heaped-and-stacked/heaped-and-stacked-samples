package playground.wallet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests 1 and 2 are given. Write each of the others before the code that makes it pass,
 * replacing its fail(...).
 */
@SpringBootTest
@Testcontainers
class WalletTransferTest {

    @Container
    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine")
            // PostgreSQL allows 100 connections by default, fewer than the pool may open
            .withCommand("postgres", "-c", "max_connections=200");

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    private static final long WALLET_A = 1;
    private static final long WALLET_B = 2;

    @Autowired
    private JdbcClient jdbcClient;

    @Autowired
    private WalletService walletService;

    @BeforeEach
    void deleteWallets() {
        jdbcClient.sql("DELETE FROM wallet").update();
    }

    private void insertWallet(long walletId, long balanceInCents) {
        jdbcClient.sql("INSERT INTO wallet (wallet_id, balance_in_cents) VALUES (:walletId, :balanceInCents)")
                .param("walletId", walletId)
                .param("balanceInCents", balanceInCents)
                .update();
    }

    private long balanceOf(long walletId) {
        return jdbcClient.sql("SELECT balance_in_cents FROM wallet WHERE wallet_id = :walletId")
                .param("walletId", walletId)
                .query(Long.class)
                .single();
    }

    // 1. The wallet table exists, which shows Docker and the database work
    @Test
    void walletTableIsCreated() {
        // When - the database started, and Flyway ran the migration
        final Integer tables = jdbcClient.sql(
                        "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'wallet'")
                .query(Integer.class)
                .single();

        // Then - the wallet table exists
        assertEquals(1, tables);
    }

    // 2. A transfer with enough funds debits the source and credits the destination by exactly the amount
    @Test
    void transferringWithEnoughFunds() {
        // Given - a source with 2000 and a destination with 500
        insertWallet(WALLET_A, 2000);
        insertWallet(WALLET_B, 500);

        // When - 1000 is transferred
        final boolean transferred = walletService.transfer(WALLET_A, WALLET_B, 1000);

        // Then - it committed, the source lost exactly 1000 and the destination gained it
        assertTrue(transferred);
        assertEquals(1000, balanceOf(WALLET_A));
        assertEquals(1500, balanceOf(WALLET_B));
    }

    // 3. More than the balance: rejected, and neither wallet changes
    @Test
    void transferringMoreThanTheBalance() {
        // Given - a source with 2000 and a destination with 500
        insertWallet(WALLET_A, 2000);
        insertWallet(WALLET_B, 500);

        // When - 2500 is transferred
        final boolean transferred = walletService.transfer(WALLET_A, WALLET_B, 2500);

        // Then - rejected, the source and destination unchanged
        assertFalse(transferred);
        assertEquals(2000, balanceOf(WALLET_A));
        assertEquals(500, balanceOf(WALLET_B));
    }

    // 4. A zero amount: rejected
    @Test
    void transferringZero() {
        // Given - a source with 2000 and a destination with 500
        insertWallet(WALLET_A, 2000);
        insertWallet(WALLET_B, 500);

        // When - 0 is transferred
        final boolean transferred = walletService.transfer(WALLET_A, WALLET_B, 0);

        // Then - rejected, the source and the destination unchanged
        assertFalse(transferred);
        assertEquals(2000, balanceOf(WALLET_A));
        assertEquals(500, balanceOf(WALLET_B));
    }

    // 5. A negative amount: rejected
    @Test
    void transferringANegativeAmount() {
        // Given - a source with 2000 and a destination with 500
        insertWallet(WALLET_A, 2000);
        insertWallet(WALLET_B, 500);

        // When - '-500' is transferred
        final boolean transferred = walletService.transfer(WALLET_A, WALLET_B, -500);

        // Then - rejected, the source and the destination unchanged
        assertFalse(transferred);
        assertEquals(2000, balanceOf(WALLET_A));
        assertEquals(500, balanceOf(WALLET_B));
    }

    // 6. The same wallet as source and destination: rejected
    @Test
    void transferringToTheSameWallet() {
        // Given - a wallet with 2000
        insertWallet(WALLET_A, 2000);

        // When - 500 is transferred
        final boolean transferred = walletService.transfer(WALLET_A, WALLET_A, 500);

        // Then - rejected, the wallet unchanged
        assertFalse(transferred);
        assertEquals(2000, balanceOf(WALLET_A));
    }

    // 7. A destination that doesn't exist: rejected, and the source keeps its money
    @Test
    void transferringToAWalletThatDoesNotExist() {
        // Given - a wallet with 2000
        insertWallet(WALLET_A, 2000);

        // When - 500 is transferred
        final boolean transferred = walletService.transfer(WALLET_A, WALLET_B, 500);

        // Then - rejected, the wallet unchanged
        assertFalse(transferred);
        assertEquals(2000, balanceOf(WALLET_A));
    }

    // 8. A failure after the debit: the destination holds the largest BIGINT, so the database refuses the credit. Both wallets stay unchanged
    @Test
    void failingAfterTheDebit() {
        // Given - a source with 2000 and a destination with the largest BIGINT
        insertWallet(WALLET_A, 2000);
        insertWallet(WALLET_B, Long.MAX_VALUE);

        // When - 500 is transferred
        assertThrows(DataAccessException.class, () -> walletService.transfer(WALLET_A, WALLET_B, 500));

        // Then - throws, the source and the destination unchanged
        assertEquals(2000, balanceOf(WALLET_A));
        assertEquals(Long.MAX_VALUE, balanceOf(WALLET_B));
    }

    // 9. 100 transfers of 15 at once, from a source with 1000: 66 succeed and 34 are rejected. The source ends at 10, and the destination gains 990
    @Test
    void transferringFromOneWalletConcurrently() {
        // Given - a source with 1000 and a destination with 0
        insertWallet(WALLET_A, 1000);
        insertWallet(WALLET_B, 0);

        final AtomicInteger success = new AtomicInteger(0);
        final AtomicInteger failed = new AtomicInteger(0);
        final AtomicInteger errors = new AtomicInteger(0);
        final CyclicBarrier startGate = new CyclicBarrier(100);

        // When - 100 transfers of 15 in parallel
        try (ExecutorService executor = Executors.newFixedThreadPool(100)) {
            for (int i = 0; i < 100; i++) {
                executor.submit(() -> {
                    try {
                        startGate.await();
                        boolean transferred = walletService.transfer(WALLET_A, WALLET_B, 15);
                        if (transferred) {
                            success.incrementAndGet();
                        } else {
                            failed.incrementAndGet();
                        }
                    } catch (Exception e) {
                        errors.incrementAndGet();
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        // Then - 66 succeed, 34 rejected, the source has 10 and the destination 990
        assertEquals(66, success.get());
        assertEquals(34, failed.get());
        assertEquals(0, errors.get());
        assertEquals(10, balanceOf(WALLET_A));
        assertEquals(990, balanceOf(WALLET_B));
    }

    // 10. 50 transfers of 10 from A to B and 50 from B to A, at the same time, both starting at 1000: all 100 succeed, and both wallets end at 1000
    @Test
    void transferringInOppositeDirectionsConcurrently() {
        // Given - a source with 1000 and a destination with 1000
        insertWallet(WALLET_A, 1000);
        insertWallet(WALLET_B, 1000);

        final AtomicInteger success = new AtomicInteger(0);
        final AtomicInteger failed = new AtomicInteger(0);
        final AtomicInteger errors = new AtomicInteger(0);
        final CyclicBarrier startGate = new CyclicBarrier(100);

        // When - 100 transfers of 10 in parallel, 50 from A to B and 50 from B to A
        try (ExecutorService executor = Executors.newFixedThreadPool(100)) {
            for (int i = 0; i < 50; i++) {
                executor.submit(() -> {
                    try {
                        startGate.await();
                        boolean transferred = walletService.transfer(WALLET_A, WALLET_B, 10);
                        if (transferred) {
                            success.incrementAndGet();
                        } else {
                            failed.incrementAndGet();
                        }
                    } catch (Exception e) {
                        errors.incrementAndGet();
                        throw new RuntimeException(e);
                    }
                });
                executor.submit(() -> {
                    try {
                        startGate.await();
                        boolean transferred = walletService.transfer(WALLET_B, WALLET_A, 10);
                        if (transferred) {
                            success.incrementAndGet();
                        } else {
                            failed.incrementAndGet();
                        }
                    } catch (Exception e) {
                        errors.incrementAndGet();
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        // Then - 100 succeed, 0 rejected or failed. The source has 1000 and the destination 1000
        assertEquals(100, success.get());
        assertEquals(0, failed.get());
        assertEquals(0, errors.get());
        assertEquals(1000, balanceOf(WALLET_A));
        assertEquals(1000, balanceOf(WALLET_B));
    }
}
