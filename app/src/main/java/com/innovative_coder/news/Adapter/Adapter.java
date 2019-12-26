package com.innovative_coder.news.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.innovative_coder.news.R;
import com.innovative_coder.news.SharedPref;
import com.innovative_coder.news.Utils;
import com.innovative_coder.news.models.Articles;

import java.text.ParseException;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Articles> articles;
    private Context context;

    private OnIteamClickListner onIteamClickListner;


    public Adapter(List<Articles> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(view,onIteamClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Articles model = articles.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.title.setText(model.getTitle());
        holder.decs.setText(model.getDescription());
        holder.source.setText(model.getSource().getName());
        try {
            holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(model.getPublishedAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            holder.published_at.setText(Utils.DateFormat(model.getPublishedAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.author.setText(model.getAuthor());

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnIteamClickListner(OnIteamClickListner onIteamClickListner){
        this.onIteamClickListner = onIteamClickListner;

    }

    public interface OnIteamClickListner {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title,decs,author,published_at,source,time;
        ImageView imageView;
        ProgressBar progressBar;
        OnIteamClickListner onIteamClickListner;

        private MyViewHolder(@NonNull View itemView,OnIteamClickListner onIteamClickListner) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            decs = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            published_at = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.progress_load_img);

            this.onIteamClickListner = onIteamClickListner;
        }

        @Override
        public void onClick(View v) {
            onIteamClickListner.onItemClick(v,getAdapterPosition());
        }
    }
}
