package playground.wallet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        fail("Write this test: the transfer is rejected, and neither wallet changes");
    }

    // 4. A zero amount: rejected
    @Test
    void transferringZero() {
        fail("Write this test: the transfer is rejected");
    }

    // 5. A negative amount: rejected
    @Test
    void transferringANegativeAmount() {
        fail("Write this test: the transfer is rejected");
    }

    // 6. The same wallet as source and destination: rejected
    @Test
    void transferringToTheSameWallet() {
        fail("Write this test: the transfer is rejected");
    }

    // 7. A destination that doesn't exist: rejected, and the source keeps its money
    @Test
    void transferringToAWalletThatDoesNotExist() {
        fail("Write this test: the transfer is rejected, and the source keeps its money");
    }

    // 8. A failure after the debit: the destination holds the largest BIGINT, so the database refuses the credit. Both wallets stay unchanged
    @Test
    void failingAfterTheDebit() {
        fail("Write this test: the destination holds Long.MAX_VALUE, so the credit fails; both wallets stay unchanged");
    }

    // 9. 100 transfers of 15 at once, from a source with 1000: 66 succeed and 34 are rejected. The source ends at 10, and the destination gains 990
    @Test
    void transferringFromOneWalletConcurrently() {
        fail("Write this test: 100 transfers of 15 from 1000 at once; 66 succeed, the source ends at 10");
    }

    // 10. 50 transfers of 10 from A to B and 50 from B to A, at the same time, both starting at 1000: all 100 succeed, and both wallets end at 1000
    @Test
    void transferringInOppositeDirectionsConcurrently() {
        fail("Write this test: 50 transfers of 10 each way at once; all succeed, both wallets end where they started");
    }
}
