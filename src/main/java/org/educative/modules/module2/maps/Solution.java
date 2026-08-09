package org.educative.modules.module2.maps;

import java.util.TreeMap;
import java.util.Objects;
import java.util.Map;

class Product implements Comparable<Product> {
    private int id;
    private String name;
    // TODO: Implement constructor, getters, Comparable interface, equals, hashCode, and toString methods.
    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Product other) {
        int id = Integer.compare(this.id, other.id);
        if (id != 0) {
            return id;
        }
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name );
    }

    @Override
    public String toString() {
        return "Product{id='" + id + "', name=" + name + "}";
    }
}

public class Solution {
    public static void main(String[] args) {
        // TODO: Create a TreeMap named inventory with Product as the key and Integer as the value.

        // TODO: Populate the inventory with Product objects and their corresponding stock quantities.

        // TODO: Use the floorEntry method to find and display the product with the greatest id less than or equal to a specified value.
        TreeMap<Product, Integer> library = new TreeMap<>();
        library.put(new Product(101, "Product-101"), 101);
        library.put(new Product(102, "Product-102"), 102);
        library.put(new Product(103, "Product-103"), 103);
        // TODO: Add another book to the library with the publication year as the key, remember the book's uniqueness comes from its title and year.
        // TODO: Iterate through the library and print the book title and its publication year.
        for (Map.Entry<Product, Integer> entry : library.entrySet()) {
            Product product = entry.getKey();
            System.out.println(product.getId() + " - " + product.getName());
        }
    }
}