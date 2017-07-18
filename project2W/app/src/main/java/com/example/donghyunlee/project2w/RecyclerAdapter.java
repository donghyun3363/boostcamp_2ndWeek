package com.example.donghyunlee.project2w;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DONGHYUNLEE on 2017-07-11.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ContentViewHolder> {

    final static int UNSELECT = 0;
    final static int SELECT = 1;
    Context context;
    List<ContentItem> items;
    int item_layout;


    public RecyclerAdapter(Context context, List<ContentItem> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }
    /*
            ViewHolder
         */
    class ContentViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.storeImage) ImageView storeImage;
        @BindView(R.id.storeName) TextView storeName;
        @BindView(R.id.storeContent) TextView storeContent;
        @BindView(R.id.storeCheck) ImageButton storeCheck;
        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView) ;
        }
    }
    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, null));
    }
    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        final ContentItem item = items.get(position);
        Glide.with(context).load(item.getStoreImg()).into(holder.storeImage);//  holder.storeImage.
        holder.storeName.setText(item.getStoreName());
        holder.storeContent.setText(item.getStoreContent());
        /*
            컨텐츠 내 버튼 선택
         */
        // modified, 상수는 앞에 선언하는 것이 좋다.
        if(UNSELECT == item.getCheckbutton()) {
            holder.storeCheck.setImageResource(R.drawable.ic_borderstar);
        }
        else {
            holder.storeCheck.setImageResource(R.drawable.ic_fillstar);
        }
        holder.storeCheck.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                ImageView storeCheck = (ImageView) v.findViewById(R.id.storeCheck);
                if(item.getCheckbutton() == UNSELECT)
                {
                    storeCheck.setImageResource(R.drawable.ic_fillstar);
                    item.setCheckbutton(SELECT);
                }
                else{
                    storeCheck.setImageResource(R.drawable.ic_borderstar);
                    item.setCheckbutton(UNSELECT);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}

