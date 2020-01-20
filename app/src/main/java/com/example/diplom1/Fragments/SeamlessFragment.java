package com.example.diplom1.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diplom1.Activities.MainActivity;
import com.example.diplom1.Activities.MaterialsActivity;
import com.example.diplom1.Classes.LayoutHelper;
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

public class SeamlessFragment extends Fragment implements View.OnClickListener {

//    !!!!!!!!!!!!!!!!!!!  !!!!!!!!!!!!!!!!!!!

    private final String TAG = "SeamlessFragment";
    private static final String BASE_URL = "http://diplombukata.somee.com";

//    наша возвращаемая view-шка
    private View view;
//    кнопки, editText-ы и textView для отображения стоимости и информации о заказе
    private Button buttonAddLayer;
    private Button buttonDeleteLayer;
    private Button buttonAddOrder;
    private EditText editTextCustomer;
    private EditText editTextExecutor;
    private EditText editTextCustomerINN;
    private EditText editTextExecutorINN;
    private EditText editTextCustomerAddress;
    private EditText editTextExecutorAddress;
    private TextView textViewCost;
//    слой для командных кнопок
    private LinearLayout linearLayoutButtons;
//    главный слой, содержащий все слои с тексвьюхами и эдиттекстами
    private LinearLayout linearLayoutMain;
    private ScrollView scrollView;

    private ArrayList<LinearLayout> linearLayouts = new ArrayList<>();

    private OrdersController ordersController;
    private OrdersService ordersService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_seamless, container, false);
        scrollView = view.findViewById(R.id.scrollView);

        ordersController = new OrdersController(BASE_URL);
        ordersService = ordersController.getApi();

        linearLayoutButtons = view.findViewById(R.id.linearLayoutButtons);
        linearLayoutMain = view.findViewById(R.id.linearLayoutMain);
        buttonAddLayer = view.findViewById(R.id.buttonAddLayer);
        buttonDeleteLayer = view.findViewById(R.id.buttonDeleteLayer);
        buttonAddOrder = view.findViewById(R.id.buttonAddOrder);
        buttonAddLayer.setOnClickListener(this);
        buttonDeleteLayer.setOnClickListener(this);
        buttonAddOrder.setOnClickListener(this);

//        textViewMaterilaChose = view.findViewById(R.id.textViewMaterialChose);
//        textViewMaterilaChose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), MaterialsActivity.class);
//                intent.putExtra("type", "seamless");
//                startActivity(intent);
//            }
//        });

        editTextCustomer = view.findViewById(R.id.editTextCustomer);
        editTextExecutor = view.findViewById(R.id.editTextExecutor);
        editTextCustomerINN = view.findViewById(R.id.editTextCustomerINN);
        editTextExecutorINN = view.findViewById(R.id.editTextExecutorINN);
        editTextCustomerAddress = view.findViewById(R.id.editTextCustomerAddress);
        editTextExecutorAddress = view.findViewById(R.id.editTextExecutorAddress);

        textViewCost = view.findViewById(R.id.textViewCost);

//        addLayout(linearLayouts.size() + 1, "", "", "", "");
//        loadState();

//        здесь этого нет, тк после этой процедуры в цикле фрагмента будет onResume, и получается,
//        что будет 2 первых слоя, что не хорошечно


//        Material mat1 = new Material();
//        mat1.setId(0);
//        mat1.setTitle("mat1");
//        mat1.setMaterialCount("100");
//        mat1.setMaterialCode("qwe");
//        mat1.setCountryOfOrigin("rus");
//
//        Material mat2 = new Material();
//        mat2.setId(1);
//        mat2.setTitle("mat2");
//        mat2.setMaterialCount("200");
//        mat2.setMaterialCode("rty");
//        mat2.setCountryOfOrigin("eng");
//
//        materials.add(mat1);
//        materials.add(mat2);
//
//        titles.add(mat1.getTitle());
//        titles.add(mat2.getTitle());

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddLayer:
                LayoutHelper layoutHelper = new LayoutHelper(getContext(), linearLayoutMain, linearLayoutButtons,
                        linearLayouts);
                layoutHelper.addSeamlessLayout(linearLayouts.size() + 1, "", "",
                        "",  true);
                for (LinearLayout linearLayout : linearLayouts) {
                    addTextListeners(linearLayout);
                    addClickListener(linearLayout);
                }
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                break;
            case R.id.buttonDeleteLayer:
                layoutHelper = new LayoutHelper(getContext(), linearLayoutMain, linearLayoutButtons,
                        linearLayouts);
                if (linearLayouts.size() > 1) {
                    layoutHelper.deleteLastLayout();
                    if (isFilled()) {
                        textViewCost.setText(Double.toString(performCalculation()) + " р.");
                    }
                } else {
                    clearAll();
                }
                break;
            case R.id.buttonAddOrder:
                if (isFilled()) {
                    addOrder();
//                  скрываем клавиатуру
                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        View v = getActivity().getCurrentFocus();
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    } catch (Exception e) { }
                } else {
                    Toast.makeText(getContext(), "Не все поля заполнены.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onPause() {
//        Log.e(TAG, "LOL");
//        здесь сейв
        saveState();
        super.onPause();
    }

    @Override
    public void onResume() {
//        Log.e(TAG, "LOL");
//        здесь лоад
        Bundle bundle = this.getArguments();
        if (bundle == null) {
            loadState();
        } else {
            loadState(bundle);
        }
        checkConnection();
////        Log.e(TAG, Integer.toString(linearLayouts.size()));
        super.onResume();
    }

    private void addOrder() {
        LayoutHelper layoutHelper = new LayoutHelper(getContext(), linearLayoutMain, linearLayoutButtons,
                linearLayouts);
        String mats = "", sqs = "", prs = "", cost;
        for (int i = 0; i < linearLayouts.size() - 1; i++) {
            mats += layoutHelper.getContent(linearLayouts.get(i)).get(0) + "%";
            sqs += layoutHelper.getContent(linearLayouts.get(i)).get(1) + "%";
            prs += layoutHelper.getContent(linearLayouts.get(i)).get(2) + "%";
        }
        mats += layoutHelper.getContent(linearLayouts.get(linearLayouts.size() - 1)).get(0);
        sqs += layoutHelper.getContent(linearLayouts.get(linearLayouts.size() - 1)).get(1);
        prs += layoutHelper.getContent(linearLayouts.get(linearLayouts.size() - 1)).get(2);
        cost = textViewCost.getText().toString();

//        Log.e(TAG, mats);

        String customer = editTextCustomer.getText().toString();
        String executor = editTextExecutor.getText().toString();
        String customerINN = editTextCustomerINN.getText().toString();
        String executorINN = editTextExecutorINN.getText().toString();
        String customerAddress = editTextCustomerAddress.getText().toString();
        String executorAddress = editTextExecutorAddress.getText().toString();

        Order order = new Order();
        order.setType("seamless");

        order.setCustomer(customer);
        order.setCustomerAddress(customerAddress);
        order.setCustomerINN(customerINN);

        order.setExecutor(executor);
        order.setExecutorAddress(executorAddress);
        order.setExecutorINN(executorINN);

        order.setMaterials(mats);
        order.setSquares(sqs);
        order.setPrices(prs);
        order.setCost(cost);
        order.setTileNum(0);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Пожалуйста, подождите...");
        progressDialog.setMessage("Подключение к серверу");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Call<Void> call = ordersService.addOrder(order);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    //reduceMaterialsCountInDataBase(linearLayouts);
                    clearAll();
                    Toast.makeText(getContext(), "Заказ добавлен.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                MainActivity.isConnected = false;
                Toast.makeText(getContext(), "Подключение к серверу отсутствует.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private double performCalculation() {
        double result = 0;
        ArrayList<View> viewsList = new ArrayList<>();
        LayoutHelper layoutHelper = new LayoutHelper(getContext(), linearLayoutMain, linearLayoutButtons,
                linearLayouts);
        for (int i = 0; i < linearLayouts.size(); i++) {
            layoutHelper.findAllChildren(linearLayouts.get(i), viewsList);
        }
        /*
        в листе viewsList будут все компоненты кроме лэйаутов
        0 - tv
        1 - tv
        2 - tv material
        3 - tv
        4 - et square
        5 - tv
        6 - et price
        нам нужны 5 + (7), 6 + (7)
         */
        try {
            for (int i = 0; i < viewsList.size() / 7; i++) {
                result += Double.parseDouble(((EditText) viewsList.get(4 + i * 7)).getText().toString()) *
                        Double.parseDouble(((EditText) viewsList.get(6 + i * 7)).getText().toString());
            }
        } catch (java.lang.NumberFormatException ex) { }
        return result;
    }

    private void saveState() {
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        ArrayList<View> viewsList = new ArrayList<>();
        LayoutHelper layoutHelper = new LayoutHelper(getContext(), linearLayoutMain,
                linearLayoutButtons, linearLayouts);
        for (int i = 0; i < linearLayouts.size(); i++) {
            layoutHelper.findAllChildren(linearLayouts.get(i), viewsList);
        }
        editor.putString("layoutsNum_seamless", Integer.toString(linearLayouts.size()));
//        Log.e(TAG, Integer.toString(linearLayouts.size()));
        editor.putString("customer_seamless", editTextCustomer.getText().toString());
        editor.putString("executor_seamless", editTextExecutor.getText().toString());
        editor.putString("customerINN_seamless", editTextCustomerINN.getText().toString());
        editor.putString("executorINN_seamless", editTextExecutorINN.getText().toString());
        editor.putString("customerAddress_seamless", editTextCustomerAddress.getText().toString());
        editor.putString("executorAddress_seamless", editTextExecutorAddress.getText().toString());
        for (int i = 0; i < viewsList.size()/7; i++) {
            editor.putString("material_seamless_" + Integer.toString(i), ((TextView) viewsList.get(2 + i * 7)).getText().toString());
            editor.putString("square_seamless_" + Integer.toString(i), ((EditText) viewsList.get(4 + i * 7)).getText().toString());
            editor.putString("price_seamless_" + Integer.toString(i), ((EditText) viewsList.get(6 + i * 7)).getText().toString());
        }
        editor.commit();
    }

    private void loadState() {
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        LayoutHelper layoutHelper = new LayoutHelper(getContext(), linearLayoutMain, linearLayoutButtons,
                linearLayouts);
        layoutHelper.deleteAllLayouts(false);
        if (preferences.getAll().size() == 0) {
            layoutHelper.addSeamlessLayout(1, "", "", "",  true);
        } else {
            int layoutsNum = Integer.parseInt(preferences.getString("layoutsNum_seamless", null));
            editTextCustomer.setText(preferences.getString("customer_seamless", null));
            editTextExecutor.setText(preferences.getString("executor_seamless", null));
            editTextCustomerINN.setText(preferences.getString("customerINN_seamless", null));
            editTextExecutorINN.setText(preferences.getString("executorINN_seamless", null));
            editTextCustomerAddress.setText(preferences.getString("customerAddress_seamless", null));
            editTextExecutorAddress.setText(preferences.getString("executorAddress_seamless", null));
//            12 так как помимо 4 полей (исполнителя и заказчика) с этой странице в преференсах
//            хранятятся так же поля со страницы плитки (их всего 10)
            for (int i = 0; i < layoutsNum; i++) {
                String mat = preferences.getString("material_seamless_" + Integer.toString(i), null);
                String squ = preferences.getString("square_seamless_" + Integer.toString(i), null);
                String pri = preferences.getString("price_seamless_" + Integer.toString(i), null);
                layoutHelper.addSeamlessLayout(i + 1, mat, squ, pri, true);
            }

            for (LinearLayout linearLayout : linearLayouts) { addTextListeners(linearLayout); }
            textViewCost.setText(Double.toString(performCalculation()) + " р.");
        }
        for (LinearLayout linearLayout : linearLayouts) {
            addTextListeners(linearLayout);
            addClickListener(linearLayout);
        }
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.commit();
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//            }
//        });
    }

//    процедура для загрузки состояния из OrderInfoActivity и из MaterialsActivity
    private void loadState(Bundle bundle) {
//        getMaterialsAndUpdateAdapter();
        if (bundle.getString("customer") != null) {
            String customer = bundle.getString("customer");
            String executor = bundle.getString("executor");
            String customerINN = bundle.getString("customerINN_seamless");
            String executorINN = bundle.getString("executorINN_seamless");
            String customerAddress = bundle.getString("customerAddress_seamless");
            String executorAddress = bundle.getString("executorAddress_seamless");
            String cost = bundle.getString("cost");

            editTextCustomer.setText(customer);
            editTextExecutor.setText(executor);
            editTextCustomerINN.setText(customerINN);
            editTextExecutorINN.setText(executorINN);
            editTextCustomerAddress.setText(customerAddress);
            editTextExecutorAddress.setText(executorAddress);
            textViewCost.setText(cost);

            ArrayList<String> materials = bundle.getStringArrayList("materials");
            ArrayList<String> squares = bundle.getStringArrayList("squares");
            ArrayList<String> prices = bundle.getStringArrayList("prices");

            LayoutHelper layoutHelper = new LayoutHelper(getContext(), linearLayoutMain, linearLayoutButtons,
                    linearLayouts);
            layoutHelper.deleteAllLayouts(false);

            for (int i = 0; i < materials.size(); i++) {
                String mat = materials.get(i);
                String squ = squares.get(i);
                String pri = prices.get(i);
                layoutHelper.addSeamlessLayout(i + 1, mat, squ, pri, true);
            }

            for (LinearLayout linearLayout : linearLayouts) {
                addTextListeners(linearLayout);
                addClickListener(linearLayout);
            }
        }
        else {
            loadState();
            String title = bundle.getString("title");
            Double price = bundle.getDouble("price");
            LinearLayout layout = linearLayouts.get(bundle.getInt("id"));
            ArrayList<View> viewsList = new ArrayList<>();
            LayoutHelper layoutHelper = new LayoutHelper(getContext(), linearLayoutMain, linearLayoutButtons,
                    linearLayouts);
            layoutHelper.findAllChildren(layout, viewsList);
            ((TextView) viewsList.get(2)).setText(title);
            ((EditText) viewsList.get(6)).setText(Double.toString(price));
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    private void addClickListener(final LinearLayout linearLayout) {
        ArrayList<View> viewsList = new ArrayList<>();
        LayoutHelper layoutHelper = new LayoutHelper(getContext(), linearLayoutMain, linearLayoutButtons,
                linearLayouts);
        layoutHelper.findAllChildren(linearLayout, viewsList);
        TextView textView = (TextView) viewsList.get(2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MaterialsActivity.class);
                intent.putExtra("type", "seamless");
//                порядковый номер поля этого слоя
                intent.putExtra("id", linearLayouts.indexOf(linearLayout));
                startActivity(intent);
//                Log.e(TAG, Integer.toString(linearLayouts.indexOf(linearLayout)));
            }
        });
    }

    private void addTextListeners(LinearLayout linearLayout) {
        /*
        процедура для добавления обработчиков изменения текста в текствьюхи цены и площади
        для автоматического расчета стоимости
         */
        ArrayList<View> viewsList = new ArrayList<>();
        LayoutHelper layoutHelper = new LayoutHelper(getContext(), linearLayoutMain, linearLayoutButtons,
                linearLayouts);
        layoutHelper.findAllChildren(linearLayout, viewsList);
        ((EditText)viewsList.get(4)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isFilled()) { textViewCost.setText(Double.toString(performCalculation()) + " р."); }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        ((EditText)viewsList.get(6)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isFilled()) { textViewCost.setText(Double.toString(performCalculation()) + " р."); }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    private boolean isFilled() {
        /*
        функция для проверки заполненности всех полей, необходимых для расчета стоимости (+ поле
        материала)
         */
        LayoutHelper layoutHelper = new LayoutHelper(getContext(), linearLayoutMain, linearLayoutButtons,
                linearLayouts);
        if (editTextCustomer.getText().toString().trim().length() == 0 ||
            editTextExecutor.getText().toString().trim().length() == 0 ||
            editTextCustomerINN.getText().toString().trim().length() == 0 ||
            editTextExecutorINN.getText().toString().trim().length() == 0 ||
            editTextCustomerAddress.getText().toString().trim().length() == 0 ||
            editTextExecutorAddress.getText().toString().trim().length() == 0) {
            return false;
        } else {
            for (LinearLayout linearLayout : linearLayouts) {
                ArrayList<View> viewsList = new ArrayList<>();
                layoutHelper.findAllChildren(linearLayout, viewsList);
                if (((TextView) viewsList.get(2)).getText().toString().trim().isEmpty() ||
                        ((EditText) viewsList.get(4)).getText().toString().trim().isEmpty() ||
                        ((EditText) viewsList.get(6)).getText().toString().trim().isEmpty()) {
//                Toast.makeText(getContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    private void clearAll() {
        editTextCustomer.setText("");
        editTextExecutor.setText("");
        editTextCustomerINN.setText("");
        editTextExecutorINN.setText("");
        editTextCustomerAddress.setText("");
        editTextExecutorAddress.setText("");
        textViewCost.setText("0.0 р.");

        LayoutHelper layoutHelper = new LayoutHelper(getContext(), linearLayoutMain, linearLayoutButtons,
                linearLayouts);
        for (LinearLayout linearLayout : linearLayouts) {
            layoutHelper.clearLayout(linearLayout);
        }

        layoutHelper.deleteAllLayouts(true);
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
