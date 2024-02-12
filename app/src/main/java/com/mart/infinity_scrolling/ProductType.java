package com.mart.infinity_scrolling;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProductType extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_type);

        recyclerView = findViewById(R.id.recyclerView);  // Replace with your actual RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDataFromServer("A1");
    }

    private void fetchDataFromServer(String productType) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ApiResponse> call = apiService.getCombinedData(productType);

        call.enqueue(new retrofit2.Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.isSuccess()) {
                        List<ProductTypeModel> productTypeModelList = apiResponse.getData();
                        if (productTypeModelList != null && !productTypeModelList.isEmpty()) {
                            adapter = new ProductTypeAdapter(productTypeModelList, ProductType.this);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e(TAG, "Empty or null data in the API response");
                        }
                    } else {
                        Log.e(TAG, "Unsuccessful API response");
                    }
                } else {
                    Log.e(TAG, "Server returned an error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "Error fetching data from server: " + t.getMessage());
            }
        });
    }
}