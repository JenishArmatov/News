package com.jenishbek.armatov.news.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jenishbek.armatov.news.R;
import com.jenishbek.armatov.news.ui.adapters.NewsPageAdapter;
import com.squareup.picasso.Picasso;

public class NewsDetalActivity extends AppCompatActivity {
    private TextView title;
    private TextView description;
    private TextView newsURL;
    private TextView avtor;
    private TextView date;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detal);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.news_image_view);
        newsURL = findViewById(R.id.news_url);
        avtor = findViewById(R.id.avtor);
        date = findViewById(R.id.date);
        if (NewsActivity.articles.get(NewsPageAdapter.pos) == null){
            return;
        }

        Picasso.with(this).load(NewsActivity.articles.get(NewsPageAdapter.pos)
                .getUrlToImage())
                .error(R.drawable.news_image)
                .into(imageView);
        description.setText(NewsActivity.articles.get(NewsPageAdapter.pos).getDescription());
        title.setText(NewsActivity.articles.get(NewsPageAdapter.pos).getTitle());
        newsURL.setText("More details " +  NewsActivity.articles.get(NewsPageAdapter.pos).getUrl());
        avtor.setText("Author: " + NewsActivity.articles.get(NewsPageAdapter.pos).getAuthor());
        date.setText("publishedAt: " + NewsActivity.articles.get(NewsPageAdapter.pos).getPublishedAt());
        newsURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(NewsActivity.articles.get(NewsPageAdapter.pos).getUrl()));
                startActivity(intent);
            }
        });



    }
}