Do $$
    DECLARE _CONTINENT_1 int := 1;

    BEGIN

    -- Continents
    INSERT INTO continent (id, name) VALUES
        (_CONTINENT_1, 'Europe');
--         ,(NEXTVAL('continent_id_seq'), 'Asie'),
--         (NEXTVAL('continent_id_seq'), 'Afrique'),
--         (NEXTVAL('continent_id_seq'), 'Amérique'),
--         (NEXTVAL('continent_id_seq'), 'Océanie')
--
--     -- Pays
--     INSERT INTO pays (pays_id, name, url_drapeau, continent_id) VALUES
--         (_PAYS_1, 'France', 'https://upload.wikimedia.org/wikipedia/commons/c/c3/Flag_of_France.svg', _CONTINENT_1),
--         (NEXTVAL('pays_id_seq'), 'Allemagne', 'https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg', _CONTINENT_1),
--         (NEXTVAL('pays_id_seq'), 'Italie', 'https://upload.wikimedia.org/wikipedia/en/0/03/Flag_of_Italy.svg', _CONTINENT_1)
--
--     -- Languages
--     INSERT INTO languages (languages_id, name) VALUES
--         (_LANGUAGE_1, 'Français'),
--         (NEXTVAL('languages_id_seq'), 'Anglais'),
--         (NEXTVAL('languages_id_seq'), 'Allemand'),
--         (NEXTVAL('languages_id_seq'), 'Italien')
--
--     -- users
--     INSERT INTO utilisateur (user_id, name, email) VALUES
--         (_USER_1, 'Admin', 'admin@admin.com'),
--         (NEXTVAL('utilisateur_user_id_seq'), 'User1', 'user1@flag.com')
--
--     -- parties
--     INSERT INTO partie (partie_id, score, "date", user_id) VALUES
--         (_PARTIE_1, 92, NOW(), _USER_1),
--         (NEXTVAL('partie_partie_id_seq'), 70, NOW(), _USER_1)
--
    END $$;

