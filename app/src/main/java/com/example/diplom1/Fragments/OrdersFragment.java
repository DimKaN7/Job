package com.example.diplom1.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.diplom1.Activities.MainActivity;
import com.example.diplom1.Activities.OrderInfoActivity;
import com.example.diplom1.Classes.Material;
import com.example.diplom1.Classes.Order;
import com.example.diplom1.Controllers.OrdersController;
import com.example.diplom1.Interfaces.OrdersService;
import com.example.diplom1.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {

    private final String TAG = "OrdersFragment";
    private static final String BASE_URL = "http://192.168.0.104:3000/";

    private View view;
    private SwipeMenuListView listViewOrders;

    private OrdersController ordersController;
    private OrdersService ordersService;

    private List<Order> orderList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        ordersController = new OrdersController(BASE_URL);
        ordersService = ordersController.getApi();

        listViewOrders = view.findViewById(R.id.listViewOrders);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem infoItem = new SwipeMenuItem(getContext());
                infoItem.setBackground(new ColorDrawable(Color.rgb(0, 0, 255)));
                infoItem.setWidth(170);
                infoItem.setIcon(R.drawable.ic_info);
                menu.addMenuItem(infoItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255, 0, 0)));
                deleteItem.setWidth(170);
                deleteItem.setIcon(R.drawable.ic_delete_);
                menu.addMenuItem(deleteItem);
            }
        };
        listViewOrders.setMenuCreator(creator);
//        после нажатия - вылязят кнопки
        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listViewOrders.smoothOpenMenu(i);
            }
        });
        listViewOrders.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //  position starts with 0, database id - with 1
                if (index == 0) {
                    Call<Order> call = ordersService.getOrder(orderList.get(position).getId());
                    call.enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            if (response.isSuccessful()) {
                                Order order = response.body();
                                if (order != null) {
                                    int id = order.getId();
                                    int tileNum = order.getTileNum();
                                    String type = order.getType();

                                    String customer = order.getCustomer();
                                    String customerAddress = order.getCustomerAddress();
                                    String customerINN = order.getCustomerINN();

                                    String executor = order.getExecutor();
                                    String executorAddress = order.getExecutorAddress();
                                    String executorINN = order.getExecutorINN();

                                    String materials = order.getMaterials();
                                    String squares = order.getSquares();
                                    String prices = order.getPrices();
                                    String cost = order.getCost();
                                    Intent intent = new Intent(getContext(), OrderInfoActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("tileNum", tileNum);
                                    intent.putExtra("type", type);
                                    intent.putExtra("customer", customer);
                                    intent.putExtra("customerAddress", customerAddress);
                                    intent.putExtra("customerINN", customerINN);
                                    intent.putExtra("executor", executor);
                                    intent.putExtra("executorAddress", executorAddress);
                                    intent.putExtra("executorINN", executorINN);
                                    intent.putExtra("materials", materials);
                                    intent.putExtra("squares", squares);
                                    intent.putExtra("prices", prices);
                                    intent.putExtra("cost", cost);
                                    startActivity(intent);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Order> call, Throwable t) { }
                    });
                }
                else {
                    Call<Void> call = ordersService.deleteOrder(orderList.get(position).getId());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Заказ удален", Toast.LENGTH_SHORT).show();
                                refreshListView();
                            }
                            else {
                                Toast.makeText(getContext(), "Соединение с сервером отсутствует", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) { }
                    });
                }
                return false;
            }
        });
        refreshListView();
        return view;
    }

    @Override
    public void onResume() {
        checkConnection();
        super.onResume();
    }

    private void refreshListView() {
//        Log.d(TAG, );
        orderList.clear();
        Call<List<Order>> call = ordersService.getAllOrders();
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    orderList = response.body();
                    ArrayList<String> adapterOrders = new ArrayList<>();
                    for (Order o : orderList) {
                        adapterOrders.add("Заказчик: " + o.getCustomer() + "; исполнитель: " +
                                o.getExecutor() + "; тип: " + (o.getType().equals("seamless") ? "бесшовка." : "плитка."));
                    }
                    ListAdapter listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, adapterOrders);
                    listViewOrders.setAdapter(listAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) { }
        });
    }

    private void checkConnection() {
        if (MainActivity.isConnected == false) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Пожалуйста, подождите...");
            progressDialog.setMessage("Подключение к серверу");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            Call<List<Order>> call = ordersService.getAllOrders();
            call.enqueue(new Callback<List<Order>>() {
                @Override
                public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                    if (response.isSuccessful()) {
                        MainActivity.isConnected = true;
                        Toast.makeText(getContext(), "Подключение к серверу установлено.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                    MainActivity.isConnected = false;
                    Toast.makeText(getContext(), "Подключение к серверу отсутствует.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

}
