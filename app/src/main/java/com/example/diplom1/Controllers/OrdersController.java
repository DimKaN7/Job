package com.example.diplom1.Controllers;

import com.example.diplom1.Interfaces.OrdersService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersController {
    private String baseURL;
    private OrdersService ordersService;

    public OrdersController(String baseURL) {
        this.baseURL = baseURL;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ordersService = retrofit.create(OrdersService.class);
    }

    public OrdersService getApi() {
        return ordersService;
    }
}
