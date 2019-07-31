package com.meiya.got.po;

public class FoodCate {

    private int id;
    private String foodcate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodcate() {
        return foodcate;
    }

    public void setFoodcate(String foodcate) {
        this.foodcate = foodcate;
    }

    @Override
    public String toString() {
        return "FoodCate{" +
                "id=" + id +
                ", foodcate='" + foodcate + '\'' +
                '}';
    }
}
