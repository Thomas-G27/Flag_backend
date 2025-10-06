package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.Course;
import com.takima.backskeleton.models.Major;

import java.time.Instant;
import java.util.List;
public class StudentDto {
    private String firstName;
    private String lastName;
    private Instant birthdate;
    private List<Course> courses;
    private Major major;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Instant getBirthdate() {
        return birthdate;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Major getMajor() {
        return major;
    }

    public static final class StudentDtoBuilder {
        private String firstName;
        private String lastName;
        private Instant birthdate;
        private List<Course> courses;
        private Major major;

        public StudentDtoBuilder() {
        }

        public static StudentDtoBuilder aStudentDto() {
            return new StudentDtoBuilder();
        }

        public StudentDtoBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public StudentDtoBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public StudentDtoBuilder birthdate(Instant birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public StudentDtoBuilder courses(List<Course> courses) {
            this.courses = courses;
            return this;
        }

        public StudentDtoBuilder major(Major major) {
            this.major = major;
            return this;
        }

        public StudentDto build() {
            StudentDto studentDto = new StudentDto();
            studentDto.lastName = this.lastName;
            studentDto.major = this.major;
            studentDto.firstName = this.firstName;
            studentDto.birthdate = this.birthdate;
            studentDto.courses = this.courses;
            return studentDto;
        }
    }
}
