package com.jenishbek.armatov.news.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.jenishbek.armatov.news.ApiClient;
import com.jenishbek.armatov.news.interfaces.ApiInterface;
import com.jenishbek.armatov.news.dto.Article;
import com.jenishbek.armatov.news.Countrys;
import com.jenishbek.armatov.news.CustomScrollView;
import com.jenishbek.armatov.news.dto.News;
import com.jenishbek.armatov.news.R;
import com.jenishbek.armatov.news.StorageUtil;
import com.jenishbek.armatov.news.ui.adapters.HomeRecycleViewAdapter;
import com.jenishbek.armatov.news.ui.adapters.NewsPageAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    public static List<Article> articles = new ArrayList<>();
    public SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        articles = new StorageUtil(this).getArticle(new Countrys().country.get(HomeRecycleViewAdapter.POSITION_OF_COUNTRY).getCityID());
        if (articles == null){
            articles = new ArrayList<>();
        }else {
            initRecycle();
        }
        onLoadingSwipeRefresh("");
    }
    public void initRecycle(){
        RecyclerView newsRV = (RecyclerView) findViewById(R.id.recycle_view);
        newsRV.setNestedScrollingEnabled(false);

        NewsPageAdapter adapterNewsRV = new NewsPageAdapter(NewsActivity.this);
        newsRV.setAdapter(adapterNewsRV);
        LinearLayoutManager llmr = new LinearLayoutManager(NewsActivity.this);
        newsRV.setLayoutManager(llmr);
        adapterNewsRV.notifyDataSetChanged();
        CustomScrollView scrollView = (CustomScrollView) findViewById(R.id.customScrollView);

        scrollView.setOnBottomReachedListener(new CustomScrollView.OnBottomReachedListener() {

            @Override
            public void onBottomReached() {
                if (adapterNewsRV.itemCount + 10 <= articles.size()){
                    adapterNewsRV.itemCount = adapterNewsRV.itemCount + 10;
                }else {
                    articles.size();
                }
                adapterNewsRV.notifyItemChanged(10);
            }
        });
    }
    public void LoadJson(final String keyword){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String country = NewsPageAdapter.countryID;
        Call<News> call;
        if (keyword.length() > 0 ){
            call = apiInterface.getNewsSearch(keyword, "publishedAt", "7d539e325d584a4d86c25a354ef7eaf9");
        } else {
            call = apiInterface.getNews(country, "7d539e325d584a4d86c25a354ef7eaf9");
        }
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){
                    articles = response.body().getArticle();
                    new StorageUtil(NewsActivity.this).addArticle(articles, country);
                    initRecycle();
                    swipeRefreshLayout.setRefreshing(false);

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(NewsActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest News...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    onLoadingSwipeRefresh(query);
                }
                else {
                    Toast.makeText(NewsActivity.this, "Type more than two letters!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false, false);
        return true;
    }

    @Override
    public void onRefresh() {
        LoadJson("");
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
}