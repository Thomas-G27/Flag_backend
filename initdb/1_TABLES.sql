-- 1) LANGUAGES
CREATE TABLE IF NOT EXISTS languages (languages_id   BIGSERIAL PRIMARY KEY,
                                      name           VARCHAR(128) NOT NULL UNIQUE
);

-- 2) CONTINENT
CREATE TABLE IF NOT EXISTS continent (continent_id   BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(128) NOT NULL UNIQUE
);

-- 3) PAYS  (pays appartient à un continent)
CREATE TABLE IF NOT EXISTS pays (pays_id BIGSERIAL PRIMARY KEY,
                                 name    VARCHAR(128) NOT NULL,
                                 url_drapeau TEXT,
                                 continent_id BIGINT,
                                 CONSTRAINT fk_pays_continent
                                    FOREIGN KEY (continent_id) REFERENCES continent(continent_id) ON DELETE SET NULL
);

-- 4) TABLE DE LIAISON MANY-TO-MANY : PAYS ↔ LANGUAGES
CREATE TABLE IF NOT EXISTS pays_language (pays_id     BIGINT NOT NULL,
                                          language_id BIGINT NOT NULL,
                                          PRIMARY KEY (pays_id, language_id),
                                          CONSTRAINT fk_pl_pays
                                             FOREIGN KEY (pays_id) REFERENCES pays(pays_id) ON DELETE CASCADE,
                                          CONSTRAINT fk_pl_language
                                             FOREIGN KEY (language_id) REFERENCES languages(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_pl_language ON pays_language(language_id);
CREATE INDEX IF NOT EXISTS idx_pl_pays     ON pays_language(pays_id);

-- 5) UTILISATEUR (éviter le mot réservé USER)
CREATE TABLE IF NOT EXISTS utilisateur (user_id BIGSERIAL PRIMARY KEY,
                                        name    VARCHAR(128) NOT NULL,
                                        email   VARCHAR(255) NOT NULL UNIQUE
);

-- 6) PARTIE (historique des parties) — rattache la partie à un utilisateur (optionnel)
CREATE TABLE IF NOT EXISTS partie (partie_id  BIGSERIAL PRIMARY KEY,
                                   score      INTEGER     NOT NULL DEFAULT 0,
                                   "date"     TIMESTAMP   NOT NULL DEFAULT NOW(),
                                   user_id    BIGINT,
                                   CONSTRAINT fk_partie_user
                                      FOREIGN KEY (user_id) REFERENCES utilisateur(user_id) ON DELETE SET NULL
);

-- Index utiles
CREATE INDEX IF NOT EXISTS idx_pays_continent  ON pays(continent_id);
CREATE INDEX IF NOT EXISTS idx_partie_user     ON partie(user_id);