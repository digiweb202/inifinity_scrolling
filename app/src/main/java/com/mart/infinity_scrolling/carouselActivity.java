package com.mart.infinity_scrolling;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class carouselActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private SliderAdapter sliderAdapter;
    private Handler handler;
    private Runnable runnable;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);

        viewPager = findViewById(R.id.viewPager);

        // Get the Retrofit client
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Create a call to the API endpoint
        Call<List<ContentModel>> call = apiService.getContent();

        // Enqueue the call to make an asynchronous request
        call.enqueue(new Callback<List<ContentModel>>() {
            @Override
            public void onResponse(Call<List<ContentModel>> call, Response<List<ContentModel>> response) {
                if (response.isSuccessful()) {
                    List<ContentModel> contentList = response.body();
                    if (contentList != null) {
                        sliderAdapter = new SliderAdapter(contentList, carouselActivity.this);
                        viewPager.setAdapter(sliderAdapter);

                        // Set up automatic sliding
                        handler = new Handler(Looper.getMainLooper());
                        runnable = () -> {
                            int currentItem = viewPager.getCurrentItem();
                            int itemCount = sliderAdapter.getItemCount();

                            // Loop back to the first item when reaching the end
                            viewPager.setCurrentItem((currentItem + 1) % itemCount, true);
                        };

                        // Schedule automatic sliding with a delay and interval
                        handler.postDelayed(runnable, 3000); // Delay in milliseconds
                        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                            @Override
                            public void onPageSelected(int position) {
                                handler.removeCallbacks(runnable);
                                handler.postDelayed(runnable, 3000); // Interval in milliseconds
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ContentModel>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove callbacks to prevent memory leaks
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }




}