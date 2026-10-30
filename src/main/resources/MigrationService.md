# MigrationService

## System Description

The company has a system consisting of the following:

- **`Product`** – a single product (e.g., `puzzle_1`, `puzzle_2`, `ball_1`, `ball_2`).
- **`Category`** – a product category (e.g., `puzzle`, `balls`).
- **`Being`** – an interface implemented by `Product` and `Category`, providing `String getName()`.
- **`Group`** – a recently introduced class representing groups of products and categories (e.g., `logic`, `sport`). This is where the migration is performed.
- **`PersistenceManager`** – an interface/class representing the persistence layer, with the following methods:

| Method | Description |
|--------|-------------|
| `Category createCategory(String name, Group g)` | Creates a new category with the given name in the given group. |
| `Product createProduct(String name, Group g)` | Creates a new product with the given name in the given group. |
| `void addProductToCategory(Product p, Category c)` | Adds product `p` to category `c`. |
| `void removeProductFromCategory(Product p, Category c)` | Removes product `p` from category `c`. |
| `void updateProduct(Product p, String newName)` | Renames the specified product. |
| `void updateCategory(Category c, String newName)` | Renames the specified category. |
| `void deleteProduct(Product p)` | Removes the product from the application (by name, not by reference). |
| `void deleteCategory(Category c)` | Removes the category from the application (by name, not by reference). |
| `List<Category> getCategories(Product p)` | Lists all categories the product belongs to. |
| `List<Product> getProducts(Category c)` | Lists all products in the category. |

## Requirements

Implement the `MigrationService` class with the `migrate(Being object, Group group, Type type)` method. Behavior depends on the migration `Type`:

1. **Copy** the specified `Being` to the desired `Group`.
2. **Move** the specified `Being` to the desired `Group`.
3. **Copy with dependencies** – copy the specified `Being` and everything related to it.
   *Example:* copying product `puzzle_1`, which belongs to the `puzzle` category (which also contains `puzzle_2`), copies all three beings. Copying the `puzzle` category copies it along with all its children.
4. **Move with dependencies** – move the specified `Being` and everything related to it.
   *Example:* moving product `puzzle_1` moves `puzzle_1`, `puzzle` and `puzzle_2`. Moving the `puzzle` category moves it along with all its children.

## Assumptions

- Names of beings must be unique. Creating two beings with the same name throws an exception.
- When copying a being, add the `-copy` suffix to its name.
- A `MigrationService` backbone is provided; start from it.
- `MigrationService` defines a `Type` enum describing the kind of migration to perform (single or many, i.e., with dependencies; copy or move).
- Create, update and delete `Product`s and `Category`s **only** through `PersistenceManager` (their constructors are not available).

## Hints

- Handle the case where `createCategory` or `createProduct` throws an `IOException`.
- Make the service safe: if persistence fails, the customer's original beings must not be lost.
- You may use `System.out.println()` for debugging.

## Environment

- Java 17

## Skeleton

```java
public void migrate(Being object, Group group, Type type) throws IOException {
}

public enum Type {
    COPY_SINGLE, MOVE_SINGLE, COPY_MANY, MOVE_MANY
}
```
