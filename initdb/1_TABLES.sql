create table students
(
    id SERIAL PRIMARY KEY,
    first_name TEXT not null,
    last_name TEXT not null,
    birthdate date null,
    major_id int null,
    image bytea null
);

-- 5) UTILISATEUR (éviter le mot réservé USER)
CREATE TABLE IF NOT EXISTS utilisateur (
                                        id    BIGSERIAL PRIMARY KEY,
                                        name  VARCHAR(128) NOT NULL UNIQUE,
                                        mdp   VARCHAR(128) NOT NULL,
                                        email VARCHAR(255) NOT NULL UNIQUE
);

-- 6) PARTIE (historique des parties) — rattache la partie à un utilisateur (optionnel)
CREATE TABLE IF NOT EXISTS game (
                                      id        BIGSERIAL PRIMARY KEY,
                                      score     FLOAT        NOT NULL DEFAULT 0,
                                      "date"    TIMESTAMP    NOT NULL DEFAULT NOW(),
                                      categorie VARCHAR(128) NOT NULL,
                                      user_id   BIGINT,
                                      CONSTRAINT fk_partie_user
                                          FOREIGN KEY (user_id) REFERENCES utilisateur(id) ON DELETE SET NULL
);

-- Index utiles
CREATE INDEX IF NOT EXISTS idx_partie_user     ON game(user_id);