
INSERT INTO utilisateur (name, mdp, email, is_admin)
VALUES
    ('Alice', 'password123', 'alice@example.com', true),
    ('Bob', 'securepwd', 'bob@example.com', false);

INSERT INTO game (score, categorie, utilisateur_id)
VALUES
    (85.5, 'world', 1),
    (92.0, 'world', 2),
    (77.3, 'europe', 1);

