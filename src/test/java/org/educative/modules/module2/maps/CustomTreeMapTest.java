package org.educative.modules.module2.maps;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomTreeMapTest {
    private static Comparable<Object> person(String name, int age) throws Exception {
        Class<?> personClass = Class.forName(CustomTreeMap.class.getName() + "$Person");
        Constructor<?> constructor = personClass.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        @SuppressWarnings("unchecked")
        Comparable<Object> person = (Comparable<Object>) constructor.newInstance(name, age);
        return person;
    }

    @Test
    void compareToOrdersByAgeThenName() throws Exception {
        assertTrue(person("Zed", 20).compareTo(person("Alice", 25)) < 0);
        assertTrue(person("Alice", 30).compareTo(person("Bob", 30)) < 0);
        assertEquals(0, person("Alice", 25).compareTo(person("Alice", 25)));
    }

    @Test
    void equalsAndHashCodeUseNameAndAge() throws Exception {
        Object alice = person("Alice", 25);

        assertEquals(alice, person("Alice", 25));
        assertEquals(alice.hashCode(), person("Alice", 25).hashCode());
        assertNotEquals(alice, person("Alice", 26));
        assertNotEquals(alice, person("Bob", 25));
    }

    @Test
    void treeMapKeepsPeopleSortedAndTreatsEqualPeopleAsOneKey() throws Exception {
        TreeMap<Object, String> people = new TreeMap<>();
        people.put(person("John", 30), "Programmer");
        people.put(person("Alice", 25), "Designer");
        people.put(person("John", 30), "Manager");

        assertEquals(2, people.size());
        assertEquals("Person{name='Alice', age=25}", people.firstKey().toString());
        assertEquals("Manager", people.get(person("John", 30)));
    }

    @Test
    void mainPrintsBothMapsInAgeOrder() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            CustomTreeMap.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String expectedLine = "{Person{name='Alice', age=25}=Designer, Person{name='John', age=30}=Programmer}";
        String expected = expectedLine + System.lineSeparator() + expectedLine + System.lineSeparator();
        assertEquals(expected, output.toString(StandardCharsets.UTF_8));
    }
}
