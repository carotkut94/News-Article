package com.death.na.models;

import io.realm.RealmObject;

/**
 * Created by rajora_sd on 4/20/2017.
 */

public class Favs extends RealmObject {
    String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    boolean isSelected;
}
