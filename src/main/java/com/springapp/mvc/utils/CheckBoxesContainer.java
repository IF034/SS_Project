package com.springapp.mvc.utils;
public class CheckBoxesContainer {


    private int[] selectedItems;
    private int selectedCategoryId;

    public int getSelectedCityId() {
        return selectedCityId;
    }

    public void setSelectedCityId(int selectedCityId) {
        this.selectedCityId = selectedCityId;
    }

    private int selectedCityId;

    public int getSelectedCategoryId() {
        return selectedCategoryId;
    }

    public void setSelectedCategoryId(int selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }

    public CheckBoxesContainer() {
    }

    public CheckBoxesContainer(int[] selectedList) {
        this.selectedItems = selectedList;
    }

    public int[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(int[] selectedList) {
        this.selectedItems = selectedList;
    }
}
