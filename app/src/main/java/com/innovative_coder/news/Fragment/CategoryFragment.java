package com.innovative_coder.news.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.innovative_coder.news.ListNews;
import com.innovative_coder.news.R;
import com.innovative_coder.news.ViewHolder.CategoryViewHolder;
import com.innovative_coder.news.api.ItemClickListner;
import com.innovative_coder.news.common.common;
import com.innovative_coder.news.models.CategoryItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class CategoryFragment extends Fragment {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference CatagoryBackground;

    //Firebase UI Adapter
    FirebaseRecyclerOptions<CategoryItem> options;
    FirebaseRecyclerAdapter<CategoryItem, CategoryViewHolder> adapter;

    //View
    RecyclerView recyclerView;

    private static CategoryFragment INSTANCE=null;

    public CategoryFragment() {
        database = FirebaseDatabase.getInstance();
        CatagoryBackground = database.getReference(common.STR_CATEGORY_BACKGROUND);

        options = new FirebaseRecyclerOptions.Builder<CategoryItem>()
                .setQuery(CatagoryBackground,CategoryItem.class) //select All
                .build();

        adapter = new FirebaseRecyclerAdapter<CategoryItem, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position, @NonNull final CategoryItem model) {
                Picasso.with(getActivity())
                        .load(model.getImagelink()) //////////////////////////////////////////////////////////////////////////////////
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder( R.drawable.progress_animation )
                        .into(holder.background_image, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                // Tryagain online if cache failed
                                Picasso.with(getActivity())
                                        .load(model.getImagelink())
                                        .placeholder( R.drawable.progress_animation )
                                        .error(R.drawable.ic_terrain_black_24dp)
                                        .into(holder.background_image, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {
                                                Log.e("ERROR","Couldn't fetch image");
                                            }
                                        });
                            }
                        });

                holder.category_Name.setText(model.getName());

                holder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position) {
                        common.CATEGORY_ID_SELECTED = adapter.getRef(position).getKey();//Get key of item
                        common.CATEGORY_SELECTED = model.getName();
                        Intent intent = new Intent(getActivity(), ListNews.class);
                        intent.putExtra("From","Category");
                        startActivity(intent);


                    }
                });

            }




            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_category_item,parent,false);
                return new CategoryViewHolder(itemView);
            }
        };

    }

    public static CategoryFragment getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new CategoryFragment();
        return INSTANCE;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catregory, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_category);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        setCategory();
        return view;
    }

    private void setCategory() {

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onStart() {
        super.onStart();

        if (adapter!=null)
        {
            adapter.startListening();
        }

    }

    @Override
    public void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null)
            adapter.startListening();
    }
}
