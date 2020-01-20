package com.example.diplom1.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diplom1.Activities.MainActivity;
import com.example.diplom1.Activities.MaterialsActivity;
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

public class TileFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "TileFragment";
    private static final String BASE_URL = "http://diplombukata.somee.com";

    private View view;

    private EditText editTextCustomer;
    private EditText editTextExecutor;
    private EditText editTextTileNum;

    private EditText editTextCustomerINN;
    private EditText editTextExecutorINN;
    private EditText editTextCustomerAddress;
    private EditText editTextExecutorAddress;

    private TextView textViewSquare1, textViewSquare2;
    private TextView textViewPrice1, textViewPrice2;

    private ArrayList<TextView> textViewsMaterials;
    private ArrayList<EditText> editTextsSquares;
    private ArrayList<EditText> editTextsPrices;

    private TextView textViewCost;
    private Button buttonAddOrder;

    private ScrollView scrollView;

    private OrdersController ordersController;
    private OrdersService ordersService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tile, container, false);
        scrollView = view.findViewById(R.id.scrollView);

//        materialsController = new MaterialsController(BASE_URL);
//        materialsService = materialsController.getApi();

        ordersController = new OrdersController(BASE_URL);
        ordersService = ordersController.getApi();

        editTextCustomer = view.findViewById(R.id.editTextCustomer);
        editTextExecutor = view.findViewById(R.id.editTextExecutor);
        editTextTileNum = view.findViewById(R.id.editTextTileNum);

        editTextCustomerINN = view.findViewById(R.id.editTextCustomerINN);
        editTextExecutorINN = view.findViewById(R.id.editTextExecutorINN);
        editTextCustomerAddress = view.findViewById(R.id.editTextCustomerAddress);
        editTextExecutorAddress = view.findViewById(R.id.editTextExecutorAddress);

        textViewSquare1 = view.findViewById(R.id.textViewSquare1);
        textViewPrice1 = view.findViewById(R.id.textViewPrice1);
        textViewSquare2 = view.findViewById(R.id.textViewSquare2);
        textViewPrice2 = view.findViewById(R.id.textViewPrice2);

        textViewSquare1.setText(R.string.sup_square);
        textViewPrice1.setText(R.string.sup_price);
        textViewSquare2.setText(R.string.sup_square);
        textViewPrice2.setText(R.string.sup_price);

        textViewsMaterials = new ArrayList<>();
        editTextsSquares = new ArrayList<>();
        editTextsPrices = new ArrayList<>();

        textViewsMaterials.add((TextView) view.findViewById(R.id.textViewMaterialChoice1));
        textViewsMaterials.add((TextView) view.findViewById(R.id.textViewMaterialChoice2));

        editTextsSquares.add((EditText)view.findViewById(R.id.editTextSquareLarge));
        editTextsSquares.add((EditText)view.findViewById(R.id.editTextSquareSmall));

        editTextsPrices.add((EditText)view.findViewById(R.id.editTextPriceLarge));
        editTextsPrices.add((EditText)view.findViewById(R.id.editTextPriceSmall));

        //  добавляем текст вочеры для автоматического счета стоимости
        editTextTileNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!editTextsSquares.get(0).getText().toString().trim().isEmpty() &&
                        !editTextsSquares.get(1).getText().toString().trim().isEmpty() &&
                        !editTextsPrices.get(0).getText().toString().trim().isEmpty() &&
                        !editTextsPrices.get(1).getText().toString().trim().isEmpty() &&
                        !editTextTileNum.getText().toString().trim().isEmpty()) {
                    textViewCost.setText(Double.toString(performCalculation()) + " р.");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        editTextsSquares.get(0).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!editTextsSquares.get(0).getText().toString().trim().isEmpty() &&
                        !editTextsSquares.get(1).getText().toString().trim().isEmpty() &&
                        !editTextsPrices.get(0).getText().toString().trim().isEmpty() &&
                        !editTextsPrices.get(1).getText().toString().trim().isEmpty() &&
                        !editTextTileNum.getText().toString().trim().isEmpty()) {
                    textViewCost.setText(Double.toString(performCalculation()) + " р.");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        editTextsSquares.get(1).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!editTextsSquares.get(0).getText().toString().trim().isEmpty() &&
                        !editTextsSquares.get(1).getText().toString().trim().isEmpty() &&
                        !editTextsPrices.get(0).getText().toString().trim().isEmpty() &&
                        !editTextsPrices.get(1).getText().toString().trim().isEmpty() &&
                        !editTextTileNum.getText().toString().trim().isEmpty()) {
                    textViewCost.setText(Double.toString(performCalculation()) + " р.");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        editTextsPrices.get(0).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!editTextsSquares.get(0).getText().toString().trim().isEmpty() &&
                        !editTextsSquares.get(1).getText().toString().trim().isEmpty() &&
                        !editTextsPrices.get(0).getText().toString().trim().isEmpty() &&
                        !editTextsPrices.get(1).getText().toString().trim().isEmpty() &&
                        !editTextTileNum.getText().toString().trim().isEmpty()) {
                    textViewCost.setText(Double.toString(performCalculation()) + " р.");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        editTextsPrices.get(1).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!editTextsSquares.get(0).getText().toString().trim().isEmpty() &&
                        !editTextsSquares.get(1).getText().toString().trim().isEmpty() &&
                        !editTextsPrices.get(0).getText().toString().trim().isEmpty() &&
                        !editTextsPrices.get(1).getText().toString().trim().isEmpty() &&
                        !editTextTileNum.getText().toString().trim().isEmpty()) {
                    try {
                        textViewCost.setText(Double.toString(performCalculation()) + " р.");
                    } catch (Exception e) { textViewCost.setText(" 0.0 р."); }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        textViewsMaterials.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MaterialsActivity.class);
                intent.putExtra("type", "tile");
//                порядковый номер поля этого слоя
                intent.putExtra("id", 0);
                startActivity(intent);
            }
        });
        textViewsMaterials.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MaterialsActivity.class);
                intent.putExtra("type", "tile");
//                порядковый номер поля этого слоя
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });

        textViewCost = view.findViewById(R.id.textViewCost);
        buttonAddOrder = view.findViewById(R.id.buttonAddOrder);
        buttonAddOrder.setOnClickListener(this);

        return view;
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
//        Log.e(TAG, Integer.toString(linearLayouts.size()));
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (isFilled()) {
            addOrder();
            try {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                View v = getActivity().getCurrentFocus();
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            } catch (Exception e) { }
        }
        else {
            Toast.makeText(getContext(), "Не все поля заполнены.", Toast.LENGTH_SHORT).show();
        }

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    private void addOrder() {
        int tile_num;
        String customer, executor, materials, squares, prices, cost;

        customer = editTextCustomer.getText().toString();
        executor = editTextExecutor.getText().toString();
        String customerINN = editTextCustomerINN.getText().toString();
        String executorINN = editTextExecutorINN.getText().toString();
        String customerAddress = editTextCustomerAddress.getText().toString();
        String executorAddress = editTextExecutorAddress.getText().toString();

        materials = textViewsMaterials.get(0).getText().toString() + "%" +
                textViewsMaterials.get(1).getText().toString();
        squares = editTextsSquares.get(0).getText().toString() + "%" +
                editTextsSquares.get(1).getText().toString();
        prices = editTextsPrices.get(0).getText().toString() + "%" +
                editTextsPrices.get(1).getText().toString();
        cost = textViewCost.getText().toString();
        tile_num = Integer.parseInt(editTextTileNum.getText().toString());

        Order order = new Order();
        order.setType("tile");

        order.setCustomer(customer);
        order.setCustomerAddress(customerAddress);
        order.setCustomerINN(customerINN);

        order.setExecutor(executor);
        order.setExecutorAddress(executorAddress);
        order.setExecutorINN(executorINN);

        order.setMaterials(materials);
        order.setSquares(squares);
        order.setPrices(prices);
        order.setCost(cost);
        order.setTileNum(tile_num);

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
                    progressDialog.dismiss();
                    clearAll();
                    Toast.makeText(getContext(), "Заказ добавлен.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                MainActivity.isConnected = false;
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Подключение к серверу отсутствует.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double performCalculation() {
        double result;
        int tile_num = Integer.parseInt(editTextTileNum.getText().toString());
        double sq1 = Double.parseDouble(editTextsSquares.get(0).getText().toString());
        double sq2 = Double.parseDouble(editTextsSquares.get(1).getText().toString());

        double pr1 = Double.parseDouble(editTextsPrices.get(0).getText().toString());
        double pr2 = Double.parseDouble(editTextsPrices.get(1).getText().toString());
        result = (sq1 * pr1 + sq2 * pr2) * tile_num;
        return result;
    }

    private void saveState() {
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("customer_tile", editTextCustomer.getText().toString());
        editor.putString("executor_tile", editTextExecutor.getText().toString());
        editor.putString("customerINN_tile", editTextCustomerINN.getText().toString());
        editor.putString("executorINN_tile", editTextExecutorINN.getText().toString());
        editor.putString("customerAddress_tile", editTextCustomerAddress.getText().toString());
        editor.putString("executorAddress_tile", editTextExecutorAddress.getText().toString());
        editor.putString("tileNum", editTextTileNum.getText().toString());
        for (int i = 0; i < 2; i++) {
            editor.putString("material_tile_" + Integer.toString(i), textViewsMaterials.get(i).getText().toString());
            editor.putString("square_tile_" + Integer.toString(i), editTextsSquares.get(i).getText().toString());
            editor.putString("price_tile_" + Integer.toString(i), editTextsPrices.get(i).getText().toString());
        }
        editor.commit();
    }

    private void loadState() {
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editTextCustomer.setText(preferences.getString("customer_tile", null));
        editTextExecutor.setText(preferences.getString("executor_tile", null));
        editTextCustomerINN.setText(preferences.getString("customerINN_tile", null));
        editTextExecutorINN.setText(preferences.getString("executorINN_tile", null));
        editTextCustomerAddress.setText(preferences.getString("customerAddress_tile", null));
        editTextExecutorAddress.setText(preferences.getString("executorAddress_tile", null));
        editTextTileNum.setText(preferences.getString("tileNum", null));

        for (int i = 0; i < 2; i++) {
            String mat = preferences.getString("material_tile_" + Integer.toString(i), null);
            String squ = preferences.getString("square_tile_" + Integer.toString(i), null);
            String pri = preferences.getString("price_tile_" + Integer.toString(i), null);

            textViewsMaterials.get(i).setText(mat);
            editTextsSquares.get(i).setText(squ);
            editTextsPrices.get(i).setText(pri);
        }
        try {
            textViewCost.setText(Double.toString(performCalculation()) + " р.");
        } catch (Exception e) { textViewCost.setText("0.0 р."); }
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//            }
//        });
    }

//        процедура для загрузки состояния из OrderInfoActivity и из MaterialsActivity
    private void loadState(Bundle bundle) {
        if (bundle.getString("customer") != null) {
            String customer = bundle.getString("customer");
            String executor = bundle.getString("executor");
            String customerINN = bundle.getString("customerINN_tile");
            String executorINN = bundle.getString("executorINN_tile");
            String customerAddress = bundle.getString("customerAddress_tile");
            String executorAddress = bundle.getString("executorAddress_tile");
            String cost = bundle.getString("cost");
            int tileNum = bundle.getInt("tileNum");

            editTextCustomer.setText(customer);
            editTextExecutor.setText(executor);
            editTextCustomerINN.setText(customerINN);
            editTextExecutorINN.setText(executorINN);
            editTextCustomerAddress.setText(customerAddress);
            editTextExecutorAddress.setText(executorAddress);
            editTextTileNum.setText(Integer.toString(tileNum));
            textViewCost.setText(cost);

            ArrayList<String> materials = bundle.getStringArrayList("materials");
            ArrayList<String> squares = bundle.getStringArrayList("squares");
            ArrayList<String> prices = bundle.getStringArrayList("prices");

            for (int i = 0; i < 2; i++) {
                textViewsMaterials.get(i).setText(materials.get(i));
                editTextsSquares.get(i).setText(squares.get(i));
                editTextsPrices.get(i).setText(prices.get(i));
            }
        }
        else {
            loadState();
            String title = bundle.getString("title");
            Double price = bundle.getDouble("price");
            int id = bundle.getInt("id");
            textViewsMaterials.get(id).setText(title);
            editTextsPrices.get(id).setText(Double.toString(price));
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    private boolean isFilled() {
        if (editTextCustomer.getText().toString().trim().length() == 0 ||
            editTextExecutor.getText().toString().trim().length() == 0 ||
            editTextCustomerINN.getText().toString().trim().length() == 0 ||
            editTextExecutorINN.getText().toString().trim().length() == 0 ||
            editTextCustomerAddress.getText().toString().trim().length() == 0 ||
            editTextExecutorAddress.getText().toString().trim().length() == 0 ||
            textViewsMaterials.get(0).getText().toString().trim().isEmpty() ||
            textViewsMaterials.get(1).getText().toString().trim().isEmpty() ||
            editTextsSquares.get(0).getText().toString().trim().isEmpty() ||
            editTextsSquares.get(1).getText().toString().trim().isEmpty() ||
            editTextsPrices.get(0).getText().toString().trim().isEmpty() ||
            editTextsPrices.get(1).getText().toString().trim().isEmpty() ||
            editTextTileNum.getText().toString().trim().isEmpty()) {
//            Toast.makeText(getContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            return false;
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
        editTextTileNum.setText("");
        textViewCost.setText("0.0 р.");

        for (int i = 0; i < 2; i++) {
            textViewsMaterials.get(i).setText("");
            textViewsMaterials.get(i).setHint("Выберите материал");
            editTextsSquares.get(i).setText("");
            editTextsPrices.get(i).setText("");
        }
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
