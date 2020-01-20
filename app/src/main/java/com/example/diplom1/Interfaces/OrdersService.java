package com.example.diplom1.Interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import com.example.diplom1.Classes.Order;

public interface OrdersService {
    @POST("/api/Orders")
    Call<Void> addOrder(@Body Order order);

    @GET("/api/Orders/{Id}")
    Call<Order> getOrder(@Path("Id") Integer id);

    @GET("/api/Orders")
    Call<List<Order>> getAllOrders();

    @DELETE("/api/Orders/{Id}")
    Call<Void> deleteOrder(@Path("Id") Integer id);
}
