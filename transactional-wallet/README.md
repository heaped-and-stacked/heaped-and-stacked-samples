# Transactional wallet

**How do concurrent money transfers stay correct in the database?**

## Scope

- **Topics:** transactions, row locking, deadlocks, testing concurrent code
- **Tools:** PostgreSQL (started by Testcontainers, so Docker must be running), Spring Boot,
  `JdbcClient` with plain SQL, Flyway, JUnit 5; `ExecutorService` and `CountDownLatch` or
  `CyclicBarrier` for the concurrent tests
- **Not covered:** JPA, a REST layer, retries

## The problem

A transfer is a debit and a credit. If they run in separate transactions, a failure between them
loses money. If they run in one transaction but read the balance first and write it later, two
transfers from the same wallet can both read 1000, both decide there's enough, and both debit.
The balance goes negative, or one debit is lost.

With more than one instance of the service, Java locks don't help: each instance has its own. The
database is the only place all of them share, so it has to do the locking.

Locking brings its own trap. A transfer from A to B and one from B to A, at the same time, can each
end up waiting for the other.

`WalletService.transfer` returns `true` when the transfer committed, and `false` when it was
rejected. When the database itself fails, it may throw instead; either way, nothing changes.

## Exercises

Write each test in `WalletTransferTest` before the code that makes it pass. Tests 1 and 2 are
given. The code goes in `WalletService.transfer`, and its SQL in `WalletRepository`, through
`JdbcClient`. The schema is given, so no migration is needed. No `synchronized` or other JVM
locks: PostgreSQL is the only source of truth.

1. The `wallet` table exists, which shows Docker and the database work.
2. A transfer with enough funds debits the source and credits the destination by exactly the amount.
3. More than the balance: rejected, and neither wallet changes.
4. A zero amount: rejected.
5. A negative amount: rejected.
6. The same wallet as source and destination: rejected.
7. A destination that doesn't exist: rejected, and the source keeps its money.
8. A failure after the debit: the destination holds the largest `BIGINT`, so the database refuses
   the credit. Both wallets stay unchanged.
9. 100 transfers of 15 at once, from a source with 1000: 66 succeed and 34 are rejected. The
   source ends at 10, and the destination gains 990.
10. 50 transfers of 10 from A to B and 50 from B to A, at the same time, both starting at 1000:
    all 100 succeed, and both wallets end at 1000.
