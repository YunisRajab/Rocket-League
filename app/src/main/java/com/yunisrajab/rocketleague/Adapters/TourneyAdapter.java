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
import com.yunisrajab.rocketleague.Objects.Tourney;
import com.yunisrajab.rocketleague.Activities.WebActivity;

import java.util.List;

public class TourneyAdapter extends RecyclerView.Adapter<TourneyAdapter.TourneyViewHolder> {

    Context mContext;
    List<Tourney>   mTourneyList;
    String type;

    public TourneyAdapter(Context context, List<Tourney> tourneys, String type)   {
        this.mContext   =   context;
        this.mTourneyList   =   tourneys;
        this.type   =   type;
    }

    @NonNull
    @Override
    public TourneyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_item_row, viewGroup, false);
        return new TourneyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TourneyViewHolder tourneyViewHolder, int i) {
        tourneyViewHolder.mImage.setImageBitmap(mTourneyList.get(i).getIconLink());
        tourneyViewHolder.mCountry.setImageBitmap(mTourneyList.get(i).getCountryLink());
        tourneyViewHolder.mTitle.setText(mTourneyList.get(i).getTitle());
        tourneyViewHolder.mDate.setText(mTourneyList.get(i).getDate());

        tourneyViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, WebActivity.class);
                mIntent.putExtra("Title", mTourneyList
                        .get(tourneyViewHolder.getAdapterPosition()).getTitle());
                mIntent.putExtra("Link", mTourneyList
                        .get(tourneyViewHolder.getAdapterPosition()).getTitleLink());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTourneyList.size();
    }


    class TourneyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage, mCountry;
        TextView mTitle, mDate;
        CardView mCardView;

        TourneyViewHolder(@NonNull View itemView) {
            super(itemView);

            mCardView = itemView.findViewById(R.id.cardview);
            mImage = itemView.findViewById(R.id.ivImage);
            mCountry = itemView.findViewById(R.id.ivCountry);
            mTitle = itemView.findViewById(R.id.tvTitle);
            mDate = itemView.findViewById(R.id.tvDate);
        }
    }

}
