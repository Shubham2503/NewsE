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
import com.innovative_coder.news.Adapter.SpacesItemDecoration;
import com.innovative_coder.news.ListNews;
import com.innovative_coder.news.R;
import com.innovative_coder.news.ViewHolder.CategoryViewHolder;
import com.innovative_coder.news.ViewHolder.SourceViewHolder;
import com.innovative_coder.news.api.ItemClickListner;
import com.innovative_coder.news.common.common;
import com.innovative_coder.news.models.CategoryItem;
import com.innovative_coder.news.models.SourceItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class SourcesFragment extends Fragment {


    //Firebase
    FirebaseDatabase database;
    DatabaseReference source;

    //Firebase UI Adapter
    FirebaseRecyclerOptions<SourceItem> options;
    FirebaseRecyclerAdapter<SourceItem, SourceViewHolder> adapter;

    //View
    RecyclerView recyclerView;

    private static SourcesFragment INSTANCE=null;

    public SourcesFragment() {
        database = FirebaseDatabase.getInstance();
        source = database.getReference(common.SOURCES);

        options = new FirebaseRecyclerOptions.Builder<SourceItem>()
                .setQuery(source,SourceItem.class) //select All
                .build();

        adapter = new FirebaseRecyclerAdapter<SourceItem, SourceViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final SourceViewHolder holder, int position, @NonNull final SourceItem model) {
                Picasso.with(getActivity())
                        .load(model.getIc()) //////////////////////////////////////////////////////////////////////////////////
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
                                        .load(model.getIc())
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
                        intent.putExtra("From","Sources");
                        intent.putExtra("SourceName",model.getId());
                        startActivity(intent);


                    }
                });

            }




            @Override
            public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.source_layout,parent,false);
                return new SourceViewHolder(itemView);
            }
        };

    }

    public static SourcesFragment getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new SourcesFragment();
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
        View view = inflater.inflate(R.layout.fragment_sources, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_sources);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        gridLayoutManager.getClipToPadding();

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
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
