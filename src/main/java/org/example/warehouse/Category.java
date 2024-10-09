package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Category {

    private final String categoryName;

    private static final Map<String, Category> cache = new HashMap<>();

    private Category(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        this.categoryName = capitalizeFirstLetter(name);
    }

    public static Category of(String name) {
        return cache.computeIfAbsent(name, Category::new);
    }

    public String getName() {
        return categoryName;
    }

    private String capitalizeFirstLetter(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(categoryName);
    }
}