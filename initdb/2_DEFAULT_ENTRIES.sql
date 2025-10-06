Do $$

    DECLARE _STUDENT_1 int := NEXTVAL('students_id_seq');
    DECLARE _MAJOR_1 int := NEXTVAL('majors_id_seq');
    DECLARE _COURSE_1 int := NEXTVAL('courses_id_seq');

    BEGIN

    INSERT INTO majors (id, name, description) VALUES (_MAJOR_1, 'Informatique', 'Ouaiiis du code partout');
    INSERT INTO majors (name, description) VALUES ('Construction', 'Beaucoup de béton et des poutres');
    INSERT INTO majors (name, description) VALUES ('Aéronautique', 'Vive le vent');
    INSERT INTO majors (name, description) VALUES ('Data', 'Trop cool plein de données à ordonner');
    INSERT INTO majors (name, description) VALUES ('Energie & Environnement', 'On est full green');
    INSERT INTO majors (name, description) VALUES ('Management', 'Des managers de qualité');
    INSERT INTO majors (name, description) VALUES ('Santé', 'On connait tous les os et tous les muscles du corps humain');
    INSERT INTO majors (name, description) VALUES ('Architecture durable', 'Objectif 0 carbone');
    INSERT INTO majors (name, description) VALUES ('Design Industriel Durable', 'On resistera à la fin du pétrole');

    INSERT INTO students (id, first_name, last_name, birthdate, major_id) VALUES (_STUDENT_1, 'Paul', 'Harrohide', '2002-06-15', _MAJOR_1);
    INSERT INTO students (first_name, last_name, birthdate, major_id) VALUES ('Jean', 'Bonbeur', '2001-08-21',_MAJOR_1);
    INSERT INTO students (first_name, last_name, birthdate, major_id) VALUES ('Alain', 'Térieur', '2000-01-11', _MAJOR_1);

    INSERT INTO courses (id, name, hours) VALUES (_COURSE_1, 'Java', 30);
    INSERT INTO courses (name, hours) VALUES ('German', 30);
    INSERT INTO courses (name, hours) VALUES ('Internet of Things', 30);
    INSERT INTO courses (name, hours) VALUES ('Thermodynamic', 30);
    INSERT INTO courses (name, hours) VALUES ('Anatomy', 30);
    INSERT INTO courses (name, hours) VALUES ('Maths', 30);
    INSERT INTO courses (name, hours) VALUES ('Spanish', 30);
    INSERT INTO courses (name, hours) VALUES ('Lean Management', 30);
    INSERT INTO student_course (student_id, course_id) VALUES (_STUDENT_1, _COURSE_1);

    END $$;

