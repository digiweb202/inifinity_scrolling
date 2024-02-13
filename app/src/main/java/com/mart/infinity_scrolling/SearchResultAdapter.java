package com.mart.infinity_scrolling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private List<ProductTypeModel> watchesList;
    private List<ProductTypeModel> originalWatchesList; // Store the original unfiltered data
    private Context context;
    private LoadMoreListener loadMoreListener;

    public SearchResultAdapter(List<ProductTypeModel> watchesList, Context context, LoadMoreListener loadMoreListener) {
        this.watchesList = watchesList != null ? watchesList : new ArrayList<>();
        this.originalWatchesList = new ArrayList<>(this.watchesList);
        this.context = context;
        this.loadMoreListener = loadMoreListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductTypeModel productTypeModel = watchesList.get(position);
        ProductListmodel watch = productTypeModel.watches;
        List<ProductListImgModel> watchesImgList = productTypeModel.watches_img;

        // Bind watch data to the ViewHolder
        holder.itemNameTextView.setText(watch.item_Name);
        holder.priceTextView.setText("$" + watch.your_Price);

        // Load the main image using Picasso
        if (!watchesImgList.isEmpty()) {
            String mainImageUrl = watchesImgList.get(0).main_Image_URL;
            Picasso.get().load(mainImageUrl).into(holder.watchImageView);
        }

        // Check if we need to load more data
        if (position == watchesList.size() - 1 && loadMoreListener != null) {
            loadMoreListener.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return watchesList != null ? watchesList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView watchImageView;
        TextView itemNameTextView, priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            watchImageView = itemView.findViewById(R.id.pic);
            itemNameTextView = itemView.findViewById(R.id.titleText);
            priceTextView = itemView.findViewById(R.id.feeTxt);
        }
    }

    // Add this method to filter the data and update the adapter
    public void filterList(List<ProductTypeModel> filteredList) {
        watchesList = filteredList;
        notifyDataSetChanged();
    }

    // Interface to handle load more events
    public interface LoadMoreListener {
        void onLoadMore();
    }
}