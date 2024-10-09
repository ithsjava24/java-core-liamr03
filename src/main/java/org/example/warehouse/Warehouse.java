package org.example.warehouse;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private static final Map<String, Warehouse> instances = new HashMap<>();
    private final String name;

    private final List<ProductRecord> products;

    private Warehouse(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public static Warehouse getInstance() {
        return new Warehouse("DefaultStore");
    }

    public static Warehouse getInstance(String name) {
        return instances.computeIfAbsent(name, Warehouse::new);
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public ProductRecord addProduct(UUID uuid, String productName, Category category, BigDecimal price) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty."); // Throw exception if product name is null or empty
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");  // Throw exception if category is null
        }
        if (uuid == null) {
            uuid = UUID.randomUUID();
        } else if (getProductById(uuid).isPresent() ) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        ProductRecord productRecord = new ProductRecord(uuid, productName, category, price);
        products.add(productRecord);
        return productRecord;
    }

    public Optional<ProductRecord> getProductById(UUID uuid) {
        return products.stream()
                .filter(product -> product.uuid().equals(uuid))
                .findFirst();
    }

    public void updateProductPrice(UUID uuid, BigDecimal bigDecimal) {
        Optional<ProductRecord> productOpt = getProductById(uuid);
            if (productOpt.isPresent()) {
                ProductRecord product = productOpt.get();
                if (product.getName() == null || product.getName().isEmpty()) {
                    throw new IllegalArgumentException("Product name can't be null or empty when updating."); // Check if product name is empty or null
                }
                product.price(bigDecimal);
            } else {
                throw new IllegalArgumentException("Product with that id doesn't exist.");
            }
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }

    public List<ProductRecord> getChangedProducts() {
        return products.stream()
                .filter(ProductRecord::isChanged)
                .collect(Collectors.toList());
    }

    public List<ProductRecord> getProductsBy(Category meat) {
        if (meat == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        return products.stream()
                .filter(product -> product.category().equals(meat))
                .collect(Collectors.toList());
    }
}
