package com.mart.infinity_scrolling;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api_product_data.php") // replace with your actual API endpoint
    Call<List<DataModel>> fetchData(
            @Query("count") int count,
            @Query("page") int page
    );

    @GET("api_single_product_data.php")
    Call<List<SingleProductModel>> getData(
            @Query("product_id") String productID,
            @Query("seller_sku") String sellerSKU
    );

    @GET("api_product_type.php")
    Call<ApiResponse> getCombinedData(@Query("productType") String productType);

}
