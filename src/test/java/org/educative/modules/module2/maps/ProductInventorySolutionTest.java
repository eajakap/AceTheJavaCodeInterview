package org.educative.modules.module2.maps;

import org.educative.modules.module2.maps.ProductInventorySolution.Product;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductInventorySolutionTest {
    @Test
    void productComparesByIdThenName() {
        assertTrue(new Product(1, "Z").compareTo(new Product(2, "A")) < 0);
        assertTrue(new Product(1, "A").compareTo(new Product(1, "B")) < 0);
        assertEquals(0, new Product(1, "A").compareTo(new Product(1, "A")));
    }

    @Test
    void productEqualsHashCodeAndToStringUseIdAndName() {
        Product product = new Product(101, "Widget");

        assertEquals(product, new Product(101, "Widget"));
        assertEquals(product.hashCode(), new Product(101, "Widget").hashCode());
        assertNotEquals(product, new Product(102, "Widget"));
        assertNotEquals(product, new Product(101, "Gadget"));
        assertEquals(101, product.getId());
        assertEquals("Widget", product.getName());
        assertEquals("Product{id='101', name=Widget}", product.toString());
    }

    @Test
    void floorEntryFindsGreatestProductAtOrBelowId() {
        TreeMap<Product, Integer> inventory = new TreeMap<>();
        inventory.put(new Product(101, "A"), 1);
        inventory.put(new Product(103, "C"), 3);

        assertEquals(new Product(101, "A"), inventory.floorKey(new Product(102, "")));
    }

    @Test
    void mainPrintsProductsInIdOrder() {
        List<String> lines = ConsoleCapture.linesPrintedBy(() -> ProductInventorySolution.main(new String[0]));

        assertEquals(List.of("101 - Product-101", "102 - Product-102", "103 - Product-103"), lines);
    }
}
