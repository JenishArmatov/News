package com.jenishbek.armatov.news.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jenishbek.armatov.news.Countrys;
import com.jenishbek.armatov.news.R;
import com.jenishbek.armatov.news.ui.activitys.NewsActivity;
import com.jenishbek.armatov.news.ui.activitys.NewsDetalActivity;
import com.squareup.picasso.Picasso;


public class NewsPageAdapter  extends RecyclerView.Adapter<NewsPageAdapter.ViewHolder>{
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView newsImage;
        LinearLayout v;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            description = (TextView)itemView.findViewById(R.id.description);
            newsImage = itemView.findViewById(R.id.news_image_view);

            v = itemView.findViewById(R.id.article_linear_layout);
        }
    }


    private Context context;
    public static String countryID = "";
    public static int pos;
    public int itemCount = 10;

    public NewsPageAdapter(Context c){

        Countrys countrys = new Countrys();
        countryID = countrys.country.get(HomeRecycleViewAdapter.POSITION_OF_COUNTRY).getCityID();
        context = c;
        if (NewsActivity.articles.size() < itemCount){
            itemCount = NewsActivity.articles.size();
        }


    }


    @Override
    public int getItemCount() {
        return itemCount;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.article_layout, parent, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText(NewsActivity.articles.get(position).getTitle());
        holder.description.setText(NewsActivity.articles.get(position).getDescription());
        if (NewsActivity.articles.get(position).getUrlToImage() != null &&
                NewsActivity.articles.get(position).getUrlToImage().equals("")){
            holder.newsImage.setImageBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.news_image)));
        }else {
            Picasso.with(context).load(NewsActivity.articles.get(position)
                    .getUrlToImage())
                    .error(R.drawable.news_image)
                    .into(holder.newsImage);
        }


        holder.v.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                context.startActivity(new Intent(context, NewsDetalActivity.class));
            }
        });

    }


}
