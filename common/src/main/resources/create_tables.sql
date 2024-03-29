CREATE TABLE IF NOT EXISTS adresse
(
    id_adresse UUID PRIMARY KEY,
    numero     VARCHAR(4),
    rue        VARCHAR(64),
    ville      VARCHAR(64),
    pays       VARCHAR(32)
);
CREATE INDEX IF NOT EXISTS idx_addr_rue ON adresse (rue);
CREATE INDEX IF NOT EXISTS idx_addr_ville ON adresse (ville);
CREATE INDEX IF NOT EXISTS idx_addr_pays ON adresse (pays);

CREATE TABLE IF NOT EXISTS hotel
(
    id_hotel UUID PRIMARY KEY,
    adresse  UUID REFERENCES adresse (id_adresse)              NOT NULL,
    nom      VARCHAR(64)                                       NOT NULL,
    etoiles  SMALLSERIAL CHECK (etoiles >= 1 AND etoiles <= 5) NOT NULL,
    url      VARCHAR(128)                                      NOT NULL
);

CREATE TABLE IF NOT EXISTS chambre
(
    id_chambre UUID PRIMARY KEY,
    id_hotel   UUID REFERENCES hotel (id_hotel) ON DELETE CASCADE NOT NULL,
    places     SMALLSERIAL CHECK (places > 1)                     NOT NULL,
    prix       REAL                                               NOT NULL
);
CREATE INDEX IF NOT EXISTS idx_chambre_place ON chambre (places);
CREATE INDEX IF NOT EXISTS idx_chambre_prix ON chambre (prix);

CREATE TABLE IF NOT EXISTS carte_bancaire
(
    numero       VARCHAR(20) PRIMARY KEY,
    mois         SMALLSERIAL CHECK (mois >= 1 AND mois <= 12) NOT NULL,
    annee        SMALLSERIAL CHECK (annee >= 1970)            NOT NULL,
    cryptogramme VARCHAR(3)                                   NOT NULL
);

CREATE TABLE IF NOT EXISTS client
(
    id_client UUID PRIMARY KEY,
    nom       VARCHAR(64)                                    NOT NULL,
    prenom    VARCHAR(64)                                    NOT NULL,
    carte     VARCHAR(20) REFERENCES carte_bancaire (numero) NOT NULL
);

CREATE TABLE IF NOT EXISTS reservation
(
    id_reservation UUID PRIMARY KEY,
    id_hotel       UUID REFERENCES hotel (id_hotel) ON DELETE CASCADE,
    id_chambre     UUID REFERENCES chambre (id_chambre) ON DELETE CASCADE,
    id_client      UUID REFERENCES client (id_client) ON DELETE CASCADE,
    debut          DATE NOT NULL,
    fin            DATE NOT NULL
);
CREATE INDEX IF NOT EXISTS idx_res_hotel ON reservation (id_chambre);
CREATE INDEX IF NOT EXISTS idx_res_chambre ON reservation (id_hotel);
CREATE INDEX IF NOT EXISTS idx_res_client ON reservation (id_client);
CREATE INDEX IF NOT EXISTS idx_res_debut ON reservation (debut);
CREATE INDEX IF NOT EXISTS idx_res_fin ON reservation (fin);

CREATE TABLE IF NOT EXISTS agence
(
    id_agence UUID PRIMARY KEY,
    nom       VARCHAR(64)  NOT NULL,
    password  VARCHAR(64)  NOT NULL,
    url       VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS partenariat
(
    id_agence UUID REFERENCES agence (id_agence) ON DELETE CASCADE,
    id_hotel  UUID REFERENCES hotel (id_hotel) ON DELETE CASCADE,
    taux      REAL NOT NULL,
    PRIMARY KEY (id_agence, id_hotel)
);