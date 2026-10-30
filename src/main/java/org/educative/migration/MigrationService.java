package org.educative.migration;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Domain/persistence types below are normally provided by the platform; minimal
// declarations are included so the solution compiles standalone.
interface Being {
    String getName();
}

class Group {
    private final String name;

    Group(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Product implements Being {
    private String name;

    Product(String name) {
        this.name = name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

class Category implements Being {
    private String name;

    Category(String name) {
        this.name = name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

interface PersistenceManager {
    Category createCategory(String name, Group g) throws IOException;

    Product createProduct(String name, Group g) throws IOException;

    void addProductToCategory(Product p, Category c);

    void removeProductFromCategory(Product p, Category c);

    void updateProduct(Product p, String newName);

    void updateCategory(Category c, String newName);

    void deleteProduct(Product p);

    void deleteCategory(Category c);

    List<Category> getCategories(Product p);

    List<Product> getProducts(Category c);
}

public class MigrationService {

    public enum Type {
        COPY_SINGLE, MOVE_SINGLE, COPY_MANY, MOVE_MANY
    }

    private static final String COPY_SUFFIX = "-copy";

    private final PersistenceManager persistenceManager;

    public MigrationService(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    public void migrate(Being object, Group group, Type type) throws IOException {
        switch (type) {
            case COPY_SINGLE:
                copySingle(object, group);
                break;
            case MOVE_SINGLE:
                moveSingle(object, group);
                break;
            case COPY_MANY:
                copyMany(object, group);
                break;
            case MOVE_MANY:
                moveMany(object, group);
                break;
            default:
                throw new IllegalArgumentException("Unsupported migration type: " + type);
        }
    }

    private void copySingle(Being object, Group group) throws IOException {
        copyBeings(Set.of(object), group);
    }

    private void moveSingle(Being object, Group group) throws IOException {
        moveBeings(Set.of(object), group);
    }

    private void copyMany(Being object, Group group) throws IOException {
        copyBeings(collectDependencies(object), group);
    }

    private void moveMany(Being object, Group group) throws IOException {
        moveBeings(collectDependencies(object), group);
    }

    // Creates "-copy" beings; originals stay untouched and copies are rolled back on failure.
    private Map<Being, Being> copyBeings(Set<Being> beings, Group group) throws IOException {
        Map<Being, Being> copies = new LinkedHashMap<>();
        try {
            for (Being b : beings) {
                copies.put(b, create(b, group));
            }
            recreateRelations(beings, copies);
        } catch (IOException | RuntimeException e) {
            rollback(copies.values());
            throw e;
        }
        return copies;
    }

    // Originals are removed only after every copy exists; copies then take the original names.
    private void moveBeings(Set<Being> beings, Group group) throws IOException {
        Map<Being, Being> copies = copyBeings(beings, group);
        for (Being b : beings) {
            delete(b);
        }
        for (Map.Entry<Being, Being> e : copies.entrySet()) {
            rename(e.getValue(), e.getKey().getName());
        }
    }

    private Set<Being> collectDependencies(Being start) {
        Set<Being> result = new LinkedHashSet<>();
        Set<String> seen = new LinkedHashSet<>();
        Deque<Being> queue = new ArrayDeque<>();
        queue.add(start);
        seen.add(key(start));
        while (!queue.isEmpty()) {
            Being current = queue.poll();
            result.add(current);
            List<? extends Being> related = current instanceof Product
                    ? persistenceManager.getCategories((Product) current)
                    : persistenceManager.getProducts((Category) current);
            for (Being r : related) {
                if (seen.add(key(r))) {
                    queue.add(r);
                }
            }
        }
        return result;
    }

    private void recreateRelations(Set<Being> beings, Map<Being, Being> copies) {
        Map<String, Being> byName = new LinkedHashMap<>();
        for (Map.Entry<Being, Being> e : copies.entrySet()) {
            byName.put(key(e.getKey()), e.getValue());
        }
        for (Being b : beings) {
            if (b instanceof Category) {
                Category copyCategory = (Category) copies.get(b);
                for (Product p : persistenceManager.getProducts((Category) b)) {
                    Being copyProduct = byName.get(key(p));
                    if (copyProduct != null) {
                        persistenceManager.addProductToCategory((Product) copyProduct, copyCategory);
                    }
                }
            }
        }
    }

    private Being create(Being b, Group group) throws IOException {
        String name = b.getName() + COPY_SUFFIX;
        return b instanceof Product
                ? persistenceManager.createProduct(name, group)
                : persistenceManager.createCategory(name, group);
    }

    private void delete(Being b) {
        if (b instanceof Product) {
            persistenceManager.deleteProduct((Product) b);
        } else {
            persistenceManager.deleteCategory((Category) b);
        }
    }

    private void rename(Being b, String newName) {
        if (b instanceof Product) {
            persistenceManager.updateProduct((Product) b, newName);
        } else {
            persistenceManager.updateCategory((Category) b, newName);
        }
    }

    private void rollback(Iterable<Being> created) {
        List<Being> list = new ArrayList<>();
        created.forEach(list::add);
        for (Being b : list) {
            try {
                delete(b);
            } catch (RuntimeException ignored) {
                // best effort: originals are intact regardless
            }
        }
    }

    // Beings are identified by name, not by reference.
    private static String key(Being b) {
        return (b instanceof Product ? "P:" : "C:") + b.getName();
    }

    // In-memory persistence used only by main(); names are unique across all beings.
    static class InMemoryPersistenceManager implements PersistenceManager {
        private final Map<String, Product> products = new LinkedHashMap<>();
        private final Map<String, Category> categories = new LinkedHashMap<>();
        private final Map<String, Set<String>> membership = new LinkedHashMap<>();
        private final Map<String, Group> groups = new LinkedHashMap<>();
        private final Set<String> failingNames = new LinkedHashSet<>();

        void failOnCreate(String name) {
            failingNames.add(name);
        }

        private void checkCreate(String name) throws IOException {
            if (failingNames.contains(name)) {
                throw new IOException("Simulated persistence failure for " + name);
            }
            if (products.containsKey(name) || categories.containsKey(name)) {
                throw new IllegalStateException("Duplicate name: " + name);
            }
        }

        @Override
        public Category createCategory(String name, Group g) throws IOException {
            checkCreate(name);
            Category c = new Category(name);
            categories.put(name, c);
            membership.put(name, new LinkedHashSet<>());
            groups.put(name, g);
            return c;
        }

        @Override
        public Product createProduct(String name, Group g) throws IOException {
            checkCreate(name);
            Product p = new Product(name);
            products.put(name, p);
            groups.put(name, g);
            return p;
        }

        @Override
        public void addProductToCategory(Product p, Category c) {
            membership.get(c.getName()).add(p.getName());
        }

        @Override
        public void removeProductFromCategory(Product p, Category c) {
            membership.get(c.getName()).remove(p.getName());
        }

        @Override
        public void updateProduct(Product p, String newName) {
            if (products.containsKey(newName) || categories.containsKey(newName)) {
                throw new IllegalStateException("Duplicate name: " + newName);
            }
            String old = p.getName();
            products.remove(old);
            p.setName(newName);
            products.put(newName, p);
            groups.put(newName, groups.remove(old));
            for (Set<String> members : membership.values()) {
                if (members.remove(old)) {
                    members.add(newName);
                }
            }
        }

        @Override
        public void updateCategory(Category c, String newName) {
            if (products.containsKey(newName) || categories.containsKey(newName)) {
                throw new IllegalStateException("Duplicate name: " + newName);
            }
            String old = c.getName();
            categories.remove(old);
            c.setName(newName);
            categories.put(newName, c);
            membership.put(newName, membership.remove(old));
            groups.put(newName, groups.remove(old));
        }

        @Override
        public void deleteProduct(Product p) {
            products.remove(p.getName());
            groups.remove(p.getName());
            membership.values().forEach(m -> m.remove(p.getName()));
        }

        @Override
        public void deleteCategory(Category c) {
            categories.remove(c.getName());
            groups.remove(c.getName());
            membership.remove(c.getName());
        }

        @Override
        public List<Category> getCategories(Product p) {
            List<Category> result = new ArrayList<>();
            membership.forEach((cat, members) -> {
                if (members.contains(p.getName())) {
                    result.add(categories.get(cat));
                }
            });
            return result;
        }

        @Override
        public List<Product> getProducts(Category c) {
            List<Product> result = new ArrayList<>();
            for (String n : membership.getOrDefault(c.getName(), Set.of())) {
                result.add(products.get(n));
            }
            return result;
        }

        void print(String title) {
            System.out.println("--- " + title);
            categories.keySet().forEach(n -> System.out.println(
                    "  category " + n + " [" + groups.get(n) + "] -> " + membership.get(n)));
            products.keySet().forEach(n -> System.out.println(
                    "  product  " + n + " [" + groups.get(n) + "]"));
        }
    }

    private static InMemoryPersistenceManager seed(Group logic) throws IOException {
        InMemoryPersistenceManager pm = new InMemoryPersistenceManager();
        Category puzzle = pm.createCategory("puzzle", logic);
        Category balls = pm.createCategory("balls", logic);
        Product p1 = pm.createProduct("puzzle_1", logic);
        Product p2 = pm.createProduct("puzzle_2", logic);
        Product b1 = pm.createProduct("ball_1", logic);
        pm.addProductToCategory(p1, puzzle);
        pm.addProductToCategory(p2, puzzle);
        pm.addProductToCategory(b1, balls);
        return pm;
    }

    public static void main(String[] args) throws IOException {
        Group logic = new Group("logic");
        Group sport = new Group("sport");

        InMemoryPersistenceManager pm = seed(logic);
        pm.print("initial");
        Product puzzle1 = pm.products.get("puzzle_1");

        new MigrationService(pm).migrate(puzzle1, sport, Type.COPY_SINGLE);
        pm.print("COPY_SINGLE puzzle_1");

        pm = seed(logic);
        new MigrationService(pm).migrate(pm.products.get("puzzle_1"), sport, Type.MOVE_SINGLE);
        pm.print("MOVE_SINGLE puzzle_1");

        pm = seed(logic);
        new MigrationService(pm).migrate(pm.products.get("puzzle_1"), sport, Type.COPY_MANY);
        pm.print("COPY_MANY puzzle_1 (puzzle, puzzle_1, puzzle_2 copied)");

        pm = seed(logic);
        new MigrationService(pm).migrate(pm.categories.get("puzzle"), sport, Type.MOVE_MANY);
        pm.print("MOVE_MANY puzzle category");

        pm = seed(logic);
        pm.failOnCreate("puzzle_2-copy");
        try {
            new MigrationService(pm).migrate(pm.categories.get("puzzle"), sport, Type.MOVE_MANY);
        } catch (IOException e) {
            System.out.println("Expected failure: " + e.getMessage());
        }
        pm.print("MOVE_MANY with failure (originals intact, no copies left)");
    }
}
