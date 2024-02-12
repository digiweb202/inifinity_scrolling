package com.mart.infinity_scrolling;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private static List<DataModel> dataList;
    private LayoutInflater inflater;
    private Context context; // Declare context as a member variable
    public DataAdapter(Context context, List<DataModel> dataList) {
        this.inflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.context = context; // Initialize context
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel data = dataList.get(position);
        // Bind data to ViewHolder views
        holder.txtProductName.setText(data.getProductName());
        holder.txtPrice.setText(data.getYourPrice());

        Picasso.get().load(data.getMainImageurl()).into(holder.imgProduct);

        // Bind more fields as needed
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName;
        TextView txtPrice;
        ImageView imgProduct;  // Change the type to ImageView

        @SuppressLint("WrongViewCast")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.titleText);
            txtPrice = itemView.findViewById(R.id.feeTxt);
            imgProduct = itemView.findViewById(R.id.pic);  // Change the ID and type

            // Add click listener to itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the clicked product
                    DataModel clickedProduct = dataList.get(getAdapterPosition());

                    // Show a Toast with product details
                    showProductDetails(clickedProduct);
                }
            });
        }

        private void showProductDetails(DataModel product) {
            String details = "Product Name: " + product.getProductID() +
                    "\nPrice: " + product.getSellerSKU() +
                    "\nOther details as needed";
            // Create an Intent to start the details_product activity
            Intent intent = new Intent(itemView.getContext(), details_product.class);

            // Pass necessary data to the details_product activity using Intent extras
            intent.putExtra("PRODUCT_NAME", product.getProductName());
            intent.putExtra("PRODUCT_PRICE", product.getYourPrice());
            intent.putExtra("PRODUCT_IMAGE_URL", product.getMainImageurl());
            intent.putExtra("PRODUCT_ID",product.getProductID());
            intent.putExtra("SELLER_SKU",product.getSellerSKU());
            // Add more data as needed

            // Start the details_product activity
            itemView.getContext().startActivity(intent);
            // Show Toast
            Toast.makeText(itemView.getContext(), details, Toast.LENGTH_SHORT).show();
        }
    }
}
