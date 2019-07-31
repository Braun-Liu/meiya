package com.meiya.got.po;

import java.io.Serializable;
import java.math.BigDecimal;

public class FoodsOrder implements Serializable {
    private Foods foods;
    private Integer quantity;
    private BigDecimal price;

    public FoodsOrder() {
    }

    @Override
    public String toString() {
        return "FoodsOrder{" +
                "foods=" + foods +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public Foods getFoods() {
        return foods;
    }

    public void setFoods(Foods foods) {
        this.foods = foods;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
