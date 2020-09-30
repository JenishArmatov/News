package com.jenishbek.armatov.news.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jenishbek.armatov.news.Countrys;
import com.jenishbek.armatov.news.dto.CountryDTO;
import com.jenishbek.armatov.news.R;
import com.jenishbek.armatov.news.ui.activitys.NewsActivity;

import java.util.ArrayList;

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeRecycleViewAdapter.ViewHolder>{
    public static int POSITION_OF_COUNTRY;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;
        LinearLayout v;
        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView)itemView.findViewById(R.id.text_view_country_name);
            v = itemView.findViewById(R.id.country_name_linear_layout);
        }
    }


    private ArrayList<CountryDTO> arrayList;
    private Context context;

    public HomeRecycleViewAdapter(Context c){

        arrayList = new ArrayList<>();
        Countrys countrys = new Countrys();
        arrayList = countrys.country;
        context = c;

    }


    @Override
    public int getItemCount() {
        return arrayList.size();

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.country_name, parent, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.countryName.setText(arrayList.get(position).getCity());
        holder.v.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POSITION_OF_COUNTRY = position;
                NewsPageAdapter.countryID = arrayList.get(position).getCityID();
                context.startActivity(new Intent(context, NewsActivity.class));

            }
        });

    }


}
