package com.innovative_coder.news.ViewHolder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovative_coder.news.ListNews;
import com.innovative_coder.news.R;
import com.innovative_coder.news.api.ItemClickListner;


public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView category_Name;
    public ImageView background_image;

    ItemClickListner itemClickListner;


    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        background_image = (ImageView)itemView.findViewById(R.id.imagee);
        category_Name = (TextView)itemView.findViewById(R.id.name);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        itemClickListner.onClick(v,getAdapterPosition() );

    }
}
