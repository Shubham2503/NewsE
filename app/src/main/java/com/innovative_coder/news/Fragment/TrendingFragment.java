package com.innovative_coder.news.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovative_coder.news.Adapter.Adapter;
import com.innovative_coder.news.NewsDetailActivity;
import com.innovative_coder.news.R;
import com.innovative_coder.news.SharedPref;
import com.innovative_coder.news.Utils;
import com.innovative_coder.news.api.ApiClient;
import com.innovative_coder.news.api.ApiInterface;
import com.innovative_coder.news.common.common;
import com.innovative_coder.news.models.Articles;
import com.innovative_coder.news.models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener{

    private static TrendingFragment INSTANCE=null;
    RecyclerView recyclerView;
    private Adapter adapter;
    private List<Articles> articles = new ArrayList<>();

    public static final String API_KEY = "1d1bbaf4bf494794a278f780fff987d4";
    private RecyclerView.LayoutManager layoutManager;
//    private String TAG = NewsMain.class.getSimpleName();
    private TextView topHeadline;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;
    SharedPref sharedpr;
    private LinearLayout linearLayout;

    public TrendingFragment(){

    }




    public static TrendingFragment getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new TrendingFragment();
        return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void LoadJson(final String keyword){

        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String country = Utils.getCountry();
        String language = Utils.getLanguage();

        Call<News> call;

        if (keyword.length() > 0 ){
            call = apiInterface.getNewsSearch(keyword, language, "publishedAt", API_KEY);
            topHeadline.setText("Results For "+keyword);
        } else {
            call = apiInterface.getNews(country, API_KEY);
            topHeadline.setText("Top-Headlines");
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    adapter = new Adapter(articles, getActivity());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);

                    initListener();

                    topHeadline.setVisibility(View.VISIBLE);


                } else {

                    topHeadline.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }

                    showErrorMessage(
                            R.drawable.no_result,
                            "No Result",
                            "Please Try Again!\n"+
                                    errorCode);

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                topHeadline.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(
                        R.drawable.oops,
                        "Oops..",
                        "Network failure, Please Try Again\n"+
                                t.toString());
            }
        });

    }



    private void initListener(){

        adapter.setOnIteamClickListner(new Adapter.OnIteamClickListner() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);

                Articles article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img",  article.getUrlToImage());
                intent.putExtra("date",  article.getPublishedAt());
                intent.putExtra("source",  article.getSource().getName());
                intent.putExtra("author",  article.getAuthor());
                intent.putExtra("content",article.getContent());

                Pair<View, String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        pair
                );


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent, optionsCompat.toBundle());
                }else {
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending, container, false);

        sharedpr = new SharedPref(view.getContext());

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_trending);
        recyclerView.setHasFixedSize(true);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
//        recyclerView.setLayoutManager(gridLayoutManager);


        swipeRefreshLayout = view.findViewById(R.id.swipe_refres);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        linearLayout = view.findViewById(R.id.linearLay);

        topHeadline = view.findViewById(R.id.topHeadline);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        onLoadingSwipeRefresh("");

        errorLayout = view.findViewById(R.id.errorLayout);
        errorImage = view.findViewById(R.id.errorImage);
        errorTitle = view.findViewById(R.id.errorTitle);
        errorMessage = view.findViewById(R.id.errorMessage);
        btnRetry = view.findViewById(R.id.btnRetry);

        if (sharedpr.loadNightModeState()==true){
            view.getContext().setTheme(R.style.darkTheme);
            topHeadline.setTextColor(Color.parseColor("#ffffff"));
            topHeadline.setBackgroundColor(Color.parseColor("#303030"));
            linearLayout.setBackgroundColor(Color.parseColor("#303030"));
        }
        else {
            view.getContext().setTheme(R.style.AppTheme);
            topHeadline.setTextColor(Color.parseColor("#000000"));
            topHeadline.setBackgroundColor(Color.parseColor("#ffffff"));
            linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        setCategory();
        return view;
    }

    private void setCategory() {


        adapter = new Adapter(articles,getActivity());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (adapter!=null)
        {
            adapter = new Adapter(articles,getActivity());
        }

    }



    @Override
    public void onStop() {
        if (adapter!=null)
            adapter = new Adapter(articles,getActivity());
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null)
            adapter = new Adapter(articles,getActivity());
    }

    private void onLoadingSwipeRefresh(final String keyword){

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson(keyword);
                    }
                }
        );

    }

    private void showErrorMessage(int imageView, String title, String message){

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadingSwipeRefresh("");
            }
        });

    }

    @Override
    public void onRefresh() {
        LoadJson("");
    }


}


