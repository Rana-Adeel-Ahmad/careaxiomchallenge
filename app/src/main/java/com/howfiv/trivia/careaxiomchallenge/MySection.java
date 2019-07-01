package com.howfiv.trivia.careaxiomchallenge;

import android.content.Context;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.howfiv.trivia.careaxiomchallenge.networking.ApiModel;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import java.util.List;

public class MySection extends StatelessSection {
    List<ApiModel> itemList;
    String header;
    Context context;

    public MySection(Context context, String header, List<ApiModel> List) {
        // call constructor with layout resources for this Section header and items
        super(SectionParameters.builder()
                .itemResourceId(R.layout.section_item)
                .headerResourceId(R.layout.section_header)
                .build());

        this.context =context;
        this.header = header;
        this.itemList = List;
    }

    @Override
    public int getContentItemsTotal() {
        return itemList.size() ; // number of items of this section
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new MyHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        MyHeaderViewHolder headerHolder = (MyHeaderViewHolder) holder;

        // bind your header view here
        headerHolder.tvItem.setText(header);
    }

    class MyHeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItem;

        public MyHeaderViewHolder(View itemView) {
            super(itemView);

            tvItem = (TextView) itemView.findViewById(R.id.title_tv);
        }
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;
        ApiModel item = itemList.get(position);
        // bind your view here
        itemHolder.tvItem.setText(item.getTitle());
        Glide.with(context)
                .load(item.getThumbnailUrl())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemHolder.Image);
    }


    class MyItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItem;
        private final ImageView Image;

        public MyItemViewHolder(View itemView) {
            super(itemView);

            tvItem = (TextView) itemView.findViewById(R.id.title_tv);
            Image = (ImageView) itemView.findViewById(R.id.tumbnail_iv);
        }
    }
}
