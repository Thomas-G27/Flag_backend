create table students
(
    id SERIAL PRIMARY KEY,
    first_name TEXT not null,
    last_name TEXT not null,
    birthdate date null,
    major_id int null,
    image bytea null
);

create table majors
(
    id SERIAL PRIMARY KEY,
    name TEXT not null,
    description TEXT not null
);

create table courses
(
    id SERIAL PRIMARY KEY,
    name TEXT not null,
    hours int not null
);

create table student_course
(
    id SERIAL PRIMARY KEY,
    student_id int not null,
    course_id int not null
);

-- 1) LANGUAGES
CREATE TABLE IF NOT EXISTS languages (
                                         id   BIGSERIAL PRIMARY KEY,
                                         name VARCHAR(128) NOT NULL UNIQUE
);

-- 2) CONTINENT
CREATE TABLE IF NOT EXISTS continent (
                                         continent_id   BIGSERIAL PRIMARY KEY,
                                         name VARCHAR(128) NOT NULL UNIQUE
);

-- 3) PAYS  (pays appartient à un continent)
CREATE TABLE IF NOT EXISTS pays (
                                    pays_id          BIGSERIAL PRIMARY KEY,
                                    name        VARCHAR(128) NOT NULL,
                                    drapeau     TEXT,
                                    continent_id BIGINT,
                                    CONSTRAINT fk_pays_continent
                                        FOREIGN KEY (continent_id) REFERENCES continent(continent_id) ON DELETE SET NULL
);

-- 4) TABLE DE LIAISON MANY-TO-MANY : PAYS ↔ LANGUAGES
CREATE TABLE IF NOT EXISTS pays_language (
                                             pays_id     BIGINT NOT NULL,
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
CREATE TABLE IF NOT EXISTS utilisateur (
                                        user_id    BIGSERIAL PRIMARY KEY,
                                        name  VARCHAR(128) NOT NULL,
                                        email VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE partie (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        score VARCHAR(50),
                        date VARCHAR(50)
);
CREATE TABLE partie_pays (
                             partie_id BIGINT NOT NULL,
                             pays_id BIGINT NOT NULL,
                             PRIMARY KEY (partie_id, pays_id),
                             CONSTRAINT fk_partie_pays_partie FOREIGN KEY (partie_id)
                                 REFERENCES partie (id) ON DELETE CASCADE,
                             CONSTRAINT fk_partie_pays_pays FOREIGN KEY (pays_id)
                                 REFERENCES pays (id) ON DELETE CASCADE
);
CREATE TABLE question (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          partie_id BIGINT,
                          ordre INT,
                          enonce TEXT NOT NULL,
                          optionA VARCHAR(255),
                          optionB VARCHAR(255),
                          optionC VARCHAR(255),
                          optionD VARCHAR(255),
                          correctOption VARCHAR(10),
                          userOption VARCHAR(10),
                          isCorrect BOOLEAN,
                          pays_id BIGINT,
                          CONSTRAINT fk_question_partie FOREIGN KEY (partie_id)
                              REFERENCES partie (id) ON DELETE CASCADE,
                          CONSTRAINT fk_question_pays FOREIGN KEY (pays_id)
                              REFERENCES pays (id) ON DELETE SET NULL
);


CREATE INDEX IF NOT EXISTS idx_pays_continent  ON pays(continent_id);
CREATE INDEX IF NOT EXISTS idx_partie_user     ON partie(user_id);