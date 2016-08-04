package com.example.trinhle.sqlitejson;

import java.util.ArrayList;

/**
 * Created by Trinh Le on 02/08/2016.
 */
public interface CategoryListener {
    public void addCategory(Category category);
    public ArrayList<Category> getAllCategory();
    public int getCategoryCount();

}
