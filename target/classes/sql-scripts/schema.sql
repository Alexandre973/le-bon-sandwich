-- CREATE TABLE item (
--     PRIMARY KEY (id),
--     id varchar(255) DEFAULT NULL,
--     uri varchar(255) DEFAULT NULL,
--     libelle varchar(255) DEFAULT NULL,
--     tarif bigint(20) DEFAULT NULL,
--     quantite bigint(20) DEFAULT NULL,
-- --     commande_id varchar(255) NOT NULL,
-- );
--
-- CREATE TABLE commande (
--     PRIMARY KEY (id),
--     id varchar(255) DEFAULT NULL,
--     nom varchar(255) DEFAULT NULL,
--     mail varchar(255) DEFAULT NULL,
--     created_at varchar(255) DEFAULT NULL,
--     livraison varchar(255) DEFAULT NULL,
--     updated_at varchar(255) DEFAULT NULL,
--     montant bigint(20) DEFAULT NULL,
--     status varchar(255) DEFAULT NULL,
--     remise varchar(255) DEFAULT NULL,
--     token varchar(255) DEFAULT NULL,
--     client_id varchar(255) DEFAULT NULL,
--     ref_paiement varchar(255) DEFAULT NULL,
--     date_paiement varchar(255) DEFAULT NULL,
--     mode_paiement varchar(255) DEFAULT NULL,
-- --     items varchar(255) REFERENCES item(commande_id),
-- );

CREATE TABLE app_user (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    username varchar(255) NOT NULL,
    PRIMARY KEY (id)
);
