package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRecord {
    public final UUID uuid;
    private final String name;
    private final Category category;
    private BigDecimal price;
    private boolean changed;

    public ProductRecord(UUID uuid, String name, Category category, BigDecimal price) {
        this.uuid = uuid;
        this.name = name;
        this.category = category;
        this.price = (price == null) ? BigDecimal.ZERO : price;
        this.changed = false;
    }

    public UUID uuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Category category() {
        return category;
    }

    public BigDecimal price() {
        return price;
    }

    public void price(BigDecimal price) {
        this.price = price;
        this.changed = true;
    }

    public boolean isChanged() {
        return changed;
    }
}