package playground.wallet;

import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    /**
     * Moves an amount from one wallet to another.
     *
     * @return true when the transfer committed, false when it was rejected and nothing changed
     */
    public boolean transfer(long sourceWalletId, long destinationWalletId, long amountInCents) {
        // TODO: move the money atomically and safely under concurrent access (README, Exercises)
        throw new UnsupportedOperationException("TODO");
    }
}
