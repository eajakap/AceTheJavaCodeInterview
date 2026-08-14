package org.educative.modules.module2.hashtables;
import java.util.Objects;

public class HashFunction {

    static class Student {
        private int studentId;
        private String name;

        public Student(int studentId, String name) {
            // Store the student's unique ID and name
            this.studentId = studentId;
            this.name = name;
        }

        @Override
        public int hashCode() {
            // Compute the hash value using the student ID
            return Objects.hash(studentId);
        }

        @Override
        public boolean equals(Object other) {
            // Two Student objects are equal if their IDs are the same
            if (this == other) return true;
            if (!(other instanceof Student)) return false;
            Student student = (Student) other;
            return this.studentId == student.studentId;
        }
    }

    public static void main(String[] args) {
        System.out.println("\"apple\".hashCode() = " + "apple".hashCode());
        System.out.println("Integer.hashCode(42) = " + Integer.hashCode(42));
        System.out.println("\"hello\".hashCode() = " + "hello".hashCode());
        Student student = new Student(42, "apple");
        System.out.println("student.hashCode() = " + student.hashCode());
    }

}
