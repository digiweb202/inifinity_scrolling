package com.mart.infinity_scrolling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchResultAdapter searchResultAdapter;
    private EditText editTextSearch;
    private List<ProductTypeModel> allData = new ArrayList<>(); // Store all data here
    private int currentPage = 1;
    private boolean isFetchingData = false;
    private ApiService apiService;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editTextSearch = findViewById(R.id.editTextText);

        // Retrieve search query from intent
        String searchQuery = getIntent().getStringExtra("searchQuery");

        // Set the search query in editTextSearch
        editTextSearch.setText(searchQuery);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        // Initialize and set the adapter with all data
        // Initialize and set the adapter with an empty list initially
        allData = new ArrayList<>();
        searchResultAdapter = new SearchResultAdapter(allData, this, new SearchResultAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                // Load more data when the user scrolls to the bottom
                if (!isFetchingData) {
                    currentPage++;
                    fetchData();
                }
            }
        });
        recyclerView.setAdapter(searchResultAdapter);

        // Add a TextWatcher to EditText for live search
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        // Initial data fetch
        fetchData();
    }

//    // Replace this method with your actual data fetching logic
//    private List<ProductTypeModel> fetchData() {
//        // Example data - replace this with your actual data fetching logic
//        // For simplicity, creating a dummy list
//        List<ProductTypeModel> dummyData = /* Your data fetching logic here */;
//        return dummyData;
//    }

    // Fetch data from the API
// Fetch data from the API
    private void fetchData() {
        isFetchingData = true;

        Call<ApiResponse> call = apiService.getWatchesData(editTextSearch.getText().toString(), currentPage);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    List<ProductTypeModel> newData = response.body().getData();

                    // Check if allData is null and initialize it
                    if (allData == null) {

                        allData = new ArrayList<>();
                    }

                    // Add new data
                    allData.addAll(newData);

                    // Notify the adapter
                    searchResultAdapter.notifyDataSetChanged();
                }
                isFetchingData = false;
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                isFetchingData = false;
                // Handle API call failure
            }
        });
    }

    // Filter the data based on the search query
    private void filter(String query) {
        // Filter logic
        // Clear the current data
        allData.clear();
        currentPage = 1;
        // Fetch new data based on the search query
        fetchData();
    }

}