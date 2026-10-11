package org.educative.modules.module2.maps;

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Demonstrates using a custom key type in a {@link TreeMap}, ordered either by
 * the key's natural ordering or by an explicit {@link Comparator}.
 */
public class CustomTreeMap {
    /**
     * A person ordered by age, then by name. The ordering is consistent with
     * {@link #equals(Object)}, so two people compare as equal only when both
     * fields match.
     */
    private static class Person implements Comparable<Person> {
        private String name;
        private int age;

        /**
         * Creates a person.
         *
         * @param name the person's name
         * @param age the person's age
         */
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        /** @return the person's age */
        public int getAge() {
            return age;
        }

        /** @return the person's name */
        public String getName() {
            return name;
        }

        /**
         * Compares this person with another by age, using name to break ties.
         *
         * @param other the person to compare with
         * @return a negative value, zero, or a positive value if this person
         *         sorts before, equal to, or after {@code other}
         */
        @Override
        public int compareTo(Person other) {
            int ageCompare = Integer.compare(this.age, other.age);
            if (ageCompare != 0) {
                return ageCompare;
            }
            return this.name.compareTo(other.name);
        }

        /** Two people are equal when both name and age match. */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && name.equals(person.name);
        }

        /** Uses the same fields as {@link #equals(Object)}. */
        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }

    /** Prints maps keyed by {@link Person}, ordered naturally and by a comparator. */
    public static void main(String[] args) {
        // No comparator is supplied, so keys are ordered by Person.compareTo.
        TreeMap<Person, String> people = new TreeMap<>();

        Person john = new Person("John", 30);
        Person alice = new Person("Alice", 25);

        people.put(john, "Programmer");
        people.put(alice, "Designer");

        // Output: {Person{name='Alice', age=25}=Designer, Person{name='John', age=30}=Programmer}
        System.out.println(people);

        // An explicit comparator overrides the natural ordering; this one
        // happens to apply the same age-then-name rule.
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
