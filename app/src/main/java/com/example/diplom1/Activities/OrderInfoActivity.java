package com.example.diplom1.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.diplom1.Classes.LayoutHelper;
import com.example.diplom1.Classes.Order;
import com.example.diplom1.R;

import java.util.ArrayList;

public class OrderInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "OrderInfoActivity";

    private EditText editTextCustomer;
    private EditText editTextCustomerAddress;
    private EditText editTextCustomerINN;

    private EditText editTextExecutor;
    private EditText editTextExecutorAddress;
    private EditText editTextExecutorINN;

    private TextView textViewCost;
    private ScrollView scrollView;
    private Button buttonEdit;

    private LinearLayout linearLayoutMain;
    private LinearLayout linearLayoutButtons;
    private ArrayList<LinearLayout> linearLayouts;

    private int id;
    private int tileNum;

    private String customer;
    private String customerAddress;
    private String customerINN;

    private String executor;
    private String executorAddress;
    private String executorINN;

    private String materials;
    private String squares;
    private String prices;
    private String cost;
    private Bundle bundle;

    private String type;

    private LayoutHelper layoutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Информация о заказе");
        setSupportActionBar(toolbar);

        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            View v = getCurrentFocus();
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) { }

        linearLayouts = new ArrayList<>();

        id = getIntent().getIntExtra("id", -1);

        linearLayoutMain = findViewById(R.id.linearLayoutMain);
        linearLayoutButtons = findViewById(R.id.linearLayoutButtons);

        editTextCustomer = findViewById(R.id.editTextCustomer);
        editTextCustomer.setClickable(false);
        editTextCustomer.setFocusable(false);
        editTextCustomer.setFocusableInTouchMode(false);
        //        -------
        editTextCustomerAddress = findViewById(R.id.editTextCustomerAddress);
        editTextCustomerAddress.setClickable(false);
        editTextCustomerAddress.setFocusable(false);
        editTextCustomerAddress.setFocusableInTouchMode(false);
        //        -------
        editTextCustomerINN = findViewById(R.id.editTextCustomerINN);
        editTextCustomerINN.setClickable(false);
        editTextCustomerINN.setFocusable(false);
        editTextCustomerINN.setFocusableInTouchMode(false);

        editTextExecutor = findViewById(R.id.editTextExecutor);
        editTextExecutor.setClickable(false);
        editTextExecutor.setFocusable(false);
        editTextExecutor.setFocusableInTouchMode(false);
        //        -------
        editTextExecutorAddress = findViewById(R.id.editTextExecutorAddress);
        editTextExecutorAddress.setClickable(false);
        editTextExecutorAddress.setFocusable(false);
        editTextExecutorAddress.setFocusableInTouchMode(false);
        //        -------
        editTextExecutorINN = findViewById(R.id.editTextExecutorINN);
        editTextExecutorINN.setClickable(false);
        editTextExecutorINN.setFocusable(false);
        editTextExecutorINN.setFocusableInTouchMode(false);

        textViewCost = findViewById(R.id.textViewCost);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(this);

        id = getIntent().getIntExtra("id", 0);
        tileNum = getIntent().getIntExtra("tileNum", 0);
        type = getIntent().getStringExtra("type");

        customer = getIntent().getStringExtra("customer");
        customerAddress = getIntent().getStringExtra("customerAddress");
        customerINN = getIntent().getStringExtra("customerINN");

        executor = getIntent().getStringExtra("executor");
        executorAddress = getIntent().getStringExtra("executorAddress");
        executorINN = getIntent().getStringExtra("executorINN");

        materials = getIntent().getStringExtra("materials");
        squares = getIntent().getStringExtra("squares");
        prices = getIntent().getStringExtra("prices");
        cost = getIntent().getStringExtra("cost");

        editTextCustomer.setText(customer);
        editTextCustomerAddress.setText(customerAddress);
        editTextCustomerINN.setText(customerINN);

        editTextExecutor.setText(executor);
        editTextExecutorAddress.setText(executorAddress);
        editTextExecutorINN.setText(executorINN);

        textViewCost.setText(cost);

        ArrayList<String> materialsList = new ArrayList<>();
        ArrayList<String> squaresList = new ArrayList<>();
        ArrayList<String> pricesList = new ArrayList<>();

        if (type.equals("seamless")) {
            for (int i = 0; i < materials.split("%").length; i++) {
                String material = materials.split("%")[i];
                String square = squares.split("%")[i];
                String price = prices.split("%")[i];

                layoutHelper = new LayoutHelper(this, linearLayoutMain, linearLayoutButtons,
                        linearLayouts);
                layoutHelper.addSeamlessLayout(i + 1, material, square, price, false);

                materialsList.add(material);
                squaresList.add(square);
                pricesList.add(price);
            }
            bundle = new Bundle();
            bundle.putString("type", type);

            bundle.putString("customer", customer);
            bundle.putString("customerAddress_seamless", customerAddress);
            bundle.putString("customerINN_seamless", customerINN);

            bundle.putString("executor", executor);
            bundle.putString("executorAddress_seamless", executorAddress);
            bundle.putString("executorINN_seamless", executorINN);

            bundle.putString("cost", cost);
            bundle.putStringArrayList("materials", materialsList);
            bundle.putStringArrayList("squares", squaresList);
            bundle.putStringArrayList("prices", pricesList);
        }
        else {
            layoutHelper = new LayoutHelper(this, linearLayoutMain, linearLayoutButtons,
                    linearLayouts);
            layoutHelper.addTileLayouts(materials, squares, prices, tileNum);
            for (int i = 0; i < 2; i++) {
                materialsList.add(materials.split("%")[i]);
                squaresList.add(squares.split("%")[i]);
                pricesList.add(prices.split("%")[i]);
            }
            bundle = new Bundle();
            bundle.putString("type", type);

            bundle.putString("customer", customer);
            bundle.putString("customerAddress_tile", customerAddress);
            bundle.putString("customerINN_tile", customerINN);

            bundle.putString("executor", executor);
            bundle.putString("executorAddress_tile", executorAddress);
            bundle.putString("executorINN_tile", executorINN);

            bundle.putString("cost", cost);
            bundle.putInt("tileNum", tileNum);
            bundle.putStringArrayList("materials", materialsList);
            bundle.putStringArrayList("squares", squaresList);
            bundle.putStringArrayList("prices", pricesList);
        }

        scrollView = findViewById(R.id.scrollView);

//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//            }
//        });

    }

    @Override
    public void onClick(View view) {
        translateOrderInfo();
    }

    private void translateOrderInfo() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("translation", bundle);
        startActivity(intent);
        this.finish();
    }

}
