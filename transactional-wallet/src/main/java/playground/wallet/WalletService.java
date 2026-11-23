package playground.wallet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    /**
     * Moves an amount from one wallet to another.
     * If the credit fails, the debit rolls back with it.
     * Don't catch that exception in here, or the debit commits
     *
     * @return true when the transfer committed, false when it was rejected and nothing changed
     */
    @Transactional
    public boolean transfer(long sourceWalletId, long destinationWalletId, long amountInCents) {
        if (sourceWalletId == destinationWalletId) {
            return false;
        }
        if (amountInCents <= 0) {
            return false;
        }
        // arrange the wallets so that we don't deadlock
        final Optional<Long> sourceBalance;
        final Optional<Long> destinationBalance;

        if (sourceWalletId < destinationWalletId) {
            sourceBalance = walletRepository.fetchBalance(sourceWalletId);
            destinationBalance = walletRepository.fetchBalance(destinationWalletId);
        } else {
            destinationBalance = walletRepository.fetchBalance(destinationWalletId);
            sourceBalance = walletRepository.fetchBalance(sourceWalletId);
        }

        if (sourceBalance.isEmpty() || destinationBalance.isEmpty()) {
            return false;
        }

        // Attempting to transfer more than the available balance fails
        if (sourceBalance.get() < amountInCents) {
            return false;
        }

        return walletRepository.updateBalance(sourceWalletId, -amountInCents) +
                walletRepository.updateBalance(destinationWalletId, amountInCents) == 2;
    }

}
