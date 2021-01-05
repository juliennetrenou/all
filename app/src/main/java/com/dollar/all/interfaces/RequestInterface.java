package com.dollar.all.interfaces;

import com.dollar.all.models.Category;
import com.dollar.all.models.Product;
import com.dollar.all.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RequestInterface {

    @POST("save")
    Call<User> saveUser(@Body User user);

    @GET("sign-in/{email}/{password}")
    Call<User> login(@Path("email") String email, @Path("password") String password);

    @GET("nbEmployee")
    Call<Long> nbUser();

    @GET("allEmployee")
    Call<List<User>> allUser();

    @GET("allCategory")
    Call<List<Category>> allCategory();

    @POST("saveProduct")
    Call<Product> saveProduct(@Body Product product);

    @GET("nbProductNbByFruit")
    Call<Long> nbProductByFruit();

    @GET("nbProductNbByVegetable")
    Call<Long> nbProductByVegetable();

    @GET("nbProduct")
    Call<Long> nbProduct();

    @GET("allProductByCategory")
    Call<List<Product>> allProductByCategory();

    @GET("allProduct")
    Call<List<Product>> allProduct();
}

