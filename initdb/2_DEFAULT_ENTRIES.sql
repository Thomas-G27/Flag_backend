Do $$

    DECLARE _STUDENT_1 int := NEXTVAL('students_id_seq');

    BEGIN

    INSERT INTO students (id, first_name, last_name, birthdate, major_id) VALUES (_STUDENT_1, 'Paul', 'Harrohide', '2002-06-15', _MAJOR_1);

    END $$;

