package ru.romanow.highload.model;

import com.google.common.base.MoreObjects;

public class AverageValueInCategoryInfo {
    private Double average;
    private String category;

    public AverageValueInCategoryInfo(Double average, String category) {
        this.average = average;
        this.category = category;
    }

    public Double getAverage() {
        return average;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("average", average)
                          .add("category", category)
                          .toString();
    }
}
