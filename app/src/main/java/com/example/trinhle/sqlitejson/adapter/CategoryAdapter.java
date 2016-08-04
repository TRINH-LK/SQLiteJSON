package com.example.trinhle.sqlitejson.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trinhle.sqlitejson.Category;
import com.example.trinhle.sqlitejson.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trinh Le on 03/08/2016.
 */
public class CategoryAdapter extends BaseAdapter {

    Context context;
    List<Category> listCategory;

    public CategoryAdapter(Context context, List<Category> listCategory) {
        this.context = context;
        this.listCategory = listCategory;
    }

    @Override
    public int getCount() {
        return listCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return listCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_item, null);
            holder = new ViewHolder();
            holder.tvCategoryName = (TextView) view.findViewById(R.id.tv_category_name);
            holder.tvDescription = (TextView) view.findViewById(R.id.tv_description);
            holder.tvNumcol = (TextView) view.findViewById(R.id.tv_num_col);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Category category = listCategory.get(position);
        String categoryName =  category.getCategoryName();
        holder.tvCategoryName.setText(categoryName);
        String description = category.getDescription();
        holder.tvDescription.setText(description);
        int numCol = category.getNumCol();
        holder.tvNumcol.setText(Integer.toString(numCol));
        return view;
    }

    class ViewHolder {
        private TextView tvCategoryName;
        private TextView tvDescription;
        private TextView tvNumcol;
    }
}
