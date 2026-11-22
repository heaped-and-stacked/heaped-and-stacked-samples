CREATE TABLE wallet (
    wallet_id BIGINT NOT NULL,
    balance_in_cents BIGINT NOT NULL,
    CONSTRAINT wallet_id_pk PRIMARY KEY (wallet_id)
);