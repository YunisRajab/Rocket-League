package com.yunisrajab.rocketleague.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunisrajab.rocketleague.R;
import com.yunisrajab.rocketleague.Objects.Tile;
import com.yunisrajab.rocketleague.Activities.WebActivity;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.TileViewHolder> {

    Context mContext;
    List<Tile>   mTileList;

    public NewsAdapter(Context context, List<Tile> tiles)   {
        this.mContext   =   context;
        this.mTileList   =   tiles;
    }

    @NonNull
    @Override
    public TileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_item_tile, viewGroup, false);
        return new TileViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TileViewHolder tileViewHolder, int i) {
        tileViewHolder.mImage.setImageBitmap(mTileList.get(i).getImg());
        tileViewHolder.mTitle.setText(mTileList.get(i).getTitle());
        tileViewHolder.mDate.setText(mTileList.get(i).getDate());

        tileViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, WebActivity.class);
                mIntent.putExtra("Title", mTileList
                        .get(tileViewHolder.getAdapterPosition()).getTitle());
                mIntent.putExtra("Link", mTileList
                        .get(tileViewHolder.getAdapterPosition()).getLink());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTileList.size();
    }


    class TileViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView mTitle, mDate;
        CardView mCardView;

        TileViewHolder(@NonNull View itemView) {
            super(itemView);

            mCardView = itemView.findViewById(R.id.cardview);
            mImage = itemView.findViewById(R.id.ivImage);
            mTitle = itemView.findViewById(R.id.tvTitle);
            mDate = itemView.findViewById(R.id.tvDate);
        }
    }

}
