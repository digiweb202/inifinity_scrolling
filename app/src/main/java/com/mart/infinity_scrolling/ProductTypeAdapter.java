package com.mart.infinity_scrolling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductTypeAdapter extends RecyclerView.Adapter<ProductTypeAdapter.ViewHolder> {

private List<ProductTypeModel> productList;
private Context context;

public ProductTypeAdapter(List<ProductTypeModel> productList, Context context) {
        this.productList = productList;
        this.context = context;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_type, parent, false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductTypeModel productTypeModel = productList.get(position);
        holder.bind(productTypeModel);
        }

@Override
public int getItemCount() {
        return productList.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView productNameTextView;  // Change this to the actual views in your item layout
    ImageView imageView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        // Initialize your views here
        productNameTextView = itemView.findViewById(R.id.productNameTextView);
      imageView  = itemView.findViewById(R.id.imageView);// Change to your actual view ID
    }

    public void bind(ProductTypeModel productTypeModel) {
        // Bind data to your views here
        productNameTextView.setText(productTypeModel.watches.getProduct_ID());
        // Check if watches_img list is not empty before accessing elements
        if (!productTypeModel.watches_img.isEmpty()) {
            ProductListImgModel firstWatchesImg = productTypeModel.watches_img.get(0);
            String mainImageURL = firstWatchesImg.getOther_Image_URL1();

            // Load the image into the ImageView using Picasso
            Picasso.get().load(mainImageURL).into(imageView);
        } else {
            Toast.makeText(context, "Preview not avaliable", Toast.LENGTH_SHORT).show();
        }
    }
}
}