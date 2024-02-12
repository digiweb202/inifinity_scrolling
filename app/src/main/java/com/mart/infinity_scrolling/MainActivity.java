package com.mart.infinity_scrolling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "https://test.njoymart.in/apis/";

    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private List<DataModel> dataList;
    private Button btn1;
    private int currentPage = 1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.button2);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProductType.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        dataList = new ArrayList<>();
        adapter = new DataAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Implement infinite scrolling
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading() && !isLastPage()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= DataModel.PAGE_SIZE) {
                        loadMoreItems();
                    }
                }
            }
        });

        // Initial data loading
        fetchData();
    }
    private void fetchData() {
        RetrofitClient.getClient()
                .create(ApiService.class)
                .fetchData(DataModel.PAGE_SIZE, currentPage)
                .enqueue(new Callback<List<DataModel>>() {
                    @Override
                    public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                        if (response.isSuccessful()) {
                            List<DataModel> newData = response.body();

                            if (newData != null) {
                                dataList.addAll(newData);
                                adapter.notifyDataSetChanged();
                                currentPage++;
                            }
                        } else {
                            Log.e(TAG, "Request failed with code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DataModel>> call, Throwable t) {
                        Log.e(TAG, "Network request failed: " + t.getMessage());
                    }
                });
    }


    private void loadMoreItems() {
        fetchData();
    }

    private boolean isLoading() {
        // Add your loading indicator logic here
        return false;
    }

    private boolean isLastPage() {
        // Add your last page logic here
        return false;
    }
}