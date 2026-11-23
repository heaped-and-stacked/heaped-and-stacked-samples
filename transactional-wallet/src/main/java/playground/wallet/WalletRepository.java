package playground.wallet;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WalletRepository {

    // The SQL for wallets goes here, as plain SQL through JdbcClient
    private final JdbcClient jdbcClient;

    public WalletRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // Locks the row until the transaction ends, so no other transfer can read this balance in between
    public Optional<Long> fetchBalance(long walletId) {
        return jdbcClient.sql(
                "SELECT balance_in_cents FROM wallet WHERE wallet_id = :walletId FOR UPDATE"
        ).param("walletId", walletId).query(Long.class).optional();
    }

    // handles both credit and debit. If the amount is negative, it's a debit
    public int updateBalance(long walletId, long amount) {
        return jdbcClient.sql(
                "UPDATE wallet SET balance_in_cents = balance_in_cents + :amount WHERE wallet_id = :walletId"
        ).param("amount", amount).param("walletId", walletId).update();
    }
}
