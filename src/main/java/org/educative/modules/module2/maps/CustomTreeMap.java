package org.educative.modules.module2.maps;

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;

public class CustomTreeMap {
    static class Person implements Comparable<Person> {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Getters and other methods...
        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        @Override
        public int compareTo(Person other) {
            int ageCompare = Integer.compare(this.age, other.age);
            if (ageCompare != 0) {
                return ageCompare;
            }
            return this.name.compareTo(other.name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && name.equals(person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }

    public static void main(String[] args) {
        TreeMap<Person, String> people = new TreeMap<>();

        Person john = new Person("John", 30);
        Person alice = new Person("Alice", 25);

        people.put(john, "Programmer");
        people.put(alice, "Designer");

        System.out.println(people); // Output: {Person{name='Alice', age=25}=Designer, Person{name='John', age=30}=Programmer}

        TreeMap<Person, String> peopleByName = new TreeMap<>(
                Comparator.comparing(Person::getAge).thenComparing(Person::getName)
        );

        Person johnByName = new Person("John", 30);
        Person aliceByName   = new Person("Alice", 25);

        peopleByName.put(johnByName, "Programmer");
        peopleByName.put(aliceByName, "Designer");

        System.out.println(peopleByName);
    }
}
