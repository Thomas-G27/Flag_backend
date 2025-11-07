-- 1) UTILISATEUR (évite le mot réservé USER)
CREATE TABLE IF NOT EXISTS utilisateur (
                                           id       BIGSERIAL PRIMARY KEY,
                                           name     VARCHAR(128) NOT NULL UNIQUE,
                                           mdp      VARCHAR(128) NOT NULL,
                                           email    VARCHAR(255) NOT NULL UNIQUE,
                                           is_admin BOOLEAN
);

-- 2) GAME (chaque partie appartient à un utilisateur)
CREATE TABLE IF NOT EXISTS game (
                                    id           BIGSERIAL PRIMARY KEY,
                                    score        FLOAT NOT NULL DEFAULT 0,
                                    game_date    TIMESTAMP NOT NULL DEFAULT NOW(),
                                    categorie    VARCHAR(128) NOT NULL,

-- permet de conserver la game si un user est supprimé
                                    utilisateur_id BIGINT,
                                        CONSTRAINT fk_game_utilisateur
                                        FOREIGN KEY (utilisateur_id)
                                        REFERENCES utilisateur(id)
                                        ON DELETE SET NULL
);
