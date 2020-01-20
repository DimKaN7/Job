package com.example.diplom1.Classes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.diplom1.Activities.MaterialsActivity;
import com.example.diplom1.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class LayoutHelper {

//    !!!!!!!!!!!!!!!!!!! spinner disable !!!!!!!!!!!!!!!!!!!

//    у плитки 2 слоя вснгда крупная фракция и мелкая количсество крупоной больше считаем одну ячейку указываем сколько ячеек

    private final String TAG = "LayoutHelper";

    private Context context;

    //    слой для командных кнопок
    private LinearLayout linearLayoutButtons;
    //    главный слой, содержащий все слои с тексвьюхами и эдиттекстами
    private LinearLayout linearLayoutMain;
    private ArrayList<LinearLayout> linearLayouts;

    public LayoutHelper(Context context, LinearLayout linearLayoutMain, LinearLayout linearLayoutButtons,
                        ArrayList<LinearLayout> linearLayouts) {
        this.context = context;
        this.linearLayoutMain = linearLayoutMain;
        this.linearLayoutButtons = linearLayoutButtons;
        this.linearLayouts = linearLayouts;
    }

    public void addSeamlessLayout(int layoutNum, String material, String square, String price,
                          boolean editable) {
        LinearLayout linearLayoutNew = new LinearLayout(context);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutNew.setLayoutParams(params);
        linearLayoutNew.setOrientation(LinearLayout.VERTICAL);

        LinearLayout linearLayoutNew1 = new LinearLayout(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutNew1.setLayoutParams(params);
        linearLayoutNew1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout linearLayoutNew2 = new LinearLayout(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutNew2.setLayoutParams(params);
        linearLayoutNew2.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout linearLayoutNew3 = new LinearLayout(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutNew3.setLayoutParams(params);
        linearLayoutNew3.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout linearLayoutNew4 = new LinearLayout(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutNew4.setLayoutParams(params);
        linearLayoutNew4.setOrientation(LinearLayout.HORIZONTAL);

        //-------------------------------------------------------------------------------

        TextView textViewLayer = new TextView(context);
        textViewLayer.setText("Слой " + Integer.toString(layoutNum));
        textViewLayer.setTextColor(Color.BLACK);
        textViewLayer.setTextSize(16);
        textViewLayer.setAllCaps(false);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayer.setLayoutParams(params);
        linearLayoutNew1.addView(textViewLayer);
        linearLayoutNew1.setPadding(0,0, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()));

        //-------------------------------------------------------------------------------

        TextView textViewMaterial = new TextView(context);
        textViewMaterial.setText("Материал: ");
        textViewMaterial.setTextColor(Color.BLACK);
        textViewMaterial.setTextSize(16);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f);
        textViewMaterial.setLayoutParams(params);
        textViewMaterial.setAllCaps(false);

        TextView textViewMaterialChoice = new TextView(context);
        if (material.equals("")) {
            textViewMaterialChoice.setHint("Выберите материал");
        } else {
            textViewMaterialChoice.setText(material);
        }
        textViewMaterialChoice.setTextSize(16);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        textViewMaterialChoice.setLayoutParams(params);
        textViewMaterialChoice.setAllCaps(false);
//        textViewMaterialChose

        linearLayoutNew2.addView(textViewMaterial);
        linearLayoutNew2.addView(textViewMaterialChoice);
        linearLayoutNew2.setPadding(0,0, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()));

        //-------------------------------------------------------------------------------

        TextView textViewSquare = new TextView(context);
        textViewSquare.setText(R.string.sup_square);
        textViewSquare.setTextColor(Color.BLACK);
        textViewSquare.setTextSize(16);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        textViewSquare.setLayoutParams(params);
        textViewSquare.setGravity(Gravity.CENTER_VERTICAL);
        textViewSquare.setAllCaps(false);

        EditText editTextSquare = new EditText(context);
        editTextSquare.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTextSquare.setMaxLines(1);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        editTextSquare.setLayoutParams(params);
        if (!editable) {
            setEnabled(editTextSquare);
        }
        editTextSquare.setText(square);

        linearLayoutNew3.addView(textViewSquare);
        linearLayoutNew3.addView(editTextSquare);
        linearLayoutNew3.setPadding(0,0, 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()));

        //-------------------------------------------------------------------------------

        TextView textViewPrice = new TextView(context);
        textViewPrice.setText(R.string.sup_price);
        textViewPrice.setTextColor(Color.BLACK);
        textViewPrice.setTextSize(16);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        textViewPrice.setLayoutParams(params);
        textViewPrice.setGravity(Gravity.CENTER_VERTICAL);
        textViewPrice.setAllCaps(false);

        EditText editTextPrice = new EditText(context);
        if (!editable) {
            setEnabled(editTextPrice);
        }
        editTextPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTextPrice.setMaxLines(1);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        editTextPrice.setLayoutParams(params);
        editTextPrice.setText(price);

        linearLayoutNew4.addView(textViewPrice);
        linearLayoutNew4.addView(editTextPrice);

        //-------------------------------------------------------------------------------

        linearLayoutNew.addView(linearLayoutNew1);
        linearLayoutNew.addView(linearLayoutNew2);
        linearLayoutNew.addView(linearLayoutNew3);
        linearLayoutNew.addView(linearLayoutNew4);
//        отступы внутри лэйоута
        linearLayoutNew.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()));
//        отступ вне лэйоута
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ((LinearLayout.LayoutParams) params).setMargins(0, 0, 0,
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        10f, context.getResources().getDisplayMetrics()));
        linearLayoutNew.setLayoutParams(params);

        linearLayoutNew.setBackground(context.getDrawable(R.drawable.layout_border));
        linearLayouts.add(linearLayoutNew);
        linearLayoutMain.removeView(linearLayoutButtons);
        linearLayoutMain.addView(linearLayoutNew);
        linearLayoutMain.addView(linearLayoutButtons);
    }

//    так как число слоев всегда фиксированное можем бахать сразу все лэйауты
    public void addTileLayouts(String materials, String squares, String prices, int tileNum) {
        ArrayList<String> materialsList = new ArrayList<>();
        ArrayList<String> squaresList = new ArrayList<>();
        ArrayList<String> pricesList = new ArrayList<>();
        materialsList.add(materials.split("%")[0]);
        materialsList.add(materials.split("%")[1]);
        squaresList.add(squares.split("%")[0]);
        squaresList.add(squares.split("%")[1]);
        pricesList.add(prices.split("%")[0]);
        pricesList.add(prices.split("%")[1]);

//        здесь создаются слои с заказчиком и исполнителем и слой плитки
//        слои с хар-ками крупной и мелкой фракции создаются отдельной функцией addTileLayout
        //-------------------------------------------------------------------------------
        LinearLayout linearLayoutNew1 = new LinearLayout(context);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ((LinearLayout.LayoutParams) params).setMargins(0, 0, 0,
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()));
        linearLayoutNew1.setLayoutParams(params);
        linearLayoutNew1.setOrientation(LinearLayout.VERTICAL);
        linearLayoutNew1.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()), 0);
        linearLayoutNew1.setBackground(context.getDrawable(R.drawable.layout_border));
        //--------------------------------
        LinearLayout linearLayoutHorizontal1 = new LinearLayout(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutHorizontal1.setLayoutParams(params);
        linearLayoutHorizontal1.setOrientation(LinearLayout.HORIZONTAL);

        TextView textViewLayer = new TextView(context);
        textViewLayer.setText("Плитка ");
        textViewLayer.setTextColor(Color.BLACK);
        textViewLayer.setTextSize(16);
        textViewLayer.setAllCaps(false);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayer.setLayoutParams(params);
        linearLayoutHorizontal1.addView(textViewLayer);
        //--------------------------------
        LinearLayout linearLayoutHorizontal2 = new LinearLayout(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutHorizontal2.setLayoutParams(params);
        linearLayoutHorizontal2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutHorizontal2.setPadding(0, 0,
                0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()));

        TextView textViewTileNum = new TextView(context);
        textViewTileNum.setText("Количество плиток: ");
        textViewTileNum.setTextColor(Color.BLACK);
        textViewTileNum.setTextSize(16);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        textViewTileNum.setLayoutParams(params);
        textViewTileNum.setAllCaps(false);

        EditText editTextTileNum = new EditText(context);
        setEnabled(editTextTileNum);
        editTextTileNum.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTextTileNum.setMaxLines(1);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        editTextTileNum.setLayoutParams(params);
        editTextTileNum.setText(Integer.toString(tileNum));

        linearLayoutHorizontal2.addView(textViewTileNum);
        linearLayoutHorizontal2.addView(editTextTileNum);
        //--------------------------------
        linearLayoutNew1.addView(linearLayoutHorizontal1);
        linearLayoutNew1.addView(linearLayoutHorizontal2);
        //-------------------------------------------------------------------------------

        linearLayoutMain.removeView(linearLayoutButtons);
        linearLayoutMain.addView(linearLayoutNew1);
        linearLayoutMain.addView(addTileLayout(context.getResources().getString(R.string.tile_large),
                materialsList.get(0), squaresList.get(0), pricesList.get(0)));
        linearLayoutMain.addView(addTileLayout(context.getResources().getString(R.string.tile_small),
                materialsList.get(1), squaresList.get(1), pricesList.get(1)));
        linearLayoutMain.addView(linearLayoutButtons);

    }

//    для добавления слоя с фракциями
    private LinearLayout addTileLayout(String tileName, String material, String square, String price) {
        LinearLayout linearLayoutNew = new LinearLayout(context);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ((LinearLayout.LayoutParams) params).setMargins(0, 0, 0,
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()));
        linearLayoutNew.setLayoutParams(params);
        linearLayoutNew.setOrientation(LinearLayout.VERTICAL);
        linearLayoutNew.setPadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()));
        linearLayoutNew.setBackground(context.getDrawable(R.drawable.layout_border));
        //--------------------------------
        LinearLayout linearLayoutHorizontal1 = new LinearLayout(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutHorizontal1.setLayoutParams(params);
        linearLayoutHorizontal1.setOrientation(LinearLayout.HORIZONTAL);

        TextView textViewLayer = new TextView(context);
        textViewLayer.setText(tileName);
        textViewLayer.setTextColor(Color.BLACK);
        textViewLayer.setTextSize(16);
        textViewLayer.setAllCaps(false);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayer.setLayoutParams(params);
        linearLayoutHorizontal1.addView(textViewLayer);
        linearLayoutHorizontal1.setPadding(0,0, 0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()));
        //--------------------------------
        LinearLayout linearLayoutHorizontal2 = new LinearLayout(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutHorizontal2.setLayoutParams(params);
        linearLayoutHorizontal2.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(context);
        textView.setText("Материал: ");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(16);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f);
        textView.setLayoutParams(params);
        textView.setAllCaps(false);

        TextView textViewMaterialChoice1 = new TextView(context);
        if (material.equals("")) {
            textViewMaterialChoice1.setHint("Выберите материал");
        } else {
            textViewMaterialChoice1.setText(material);
        }
        textViewMaterialChoice1.setTextSize(16);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        textViewMaterialChoice1.setLayoutParams(params);
        textViewMaterialChoice1.setAllCaps(false);

        linearLayoutHorizontal2.addView(textView);
        linearLayoutHorizontal2.addView(textViewMaterialChoice1);
        //--------------------------------
        LinearLayout linearLayoutHorizontal3 = new LinearLayout(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutHorizontal3.setLayoutParams(params);
        linearLayoutHorizontal3.setOrientation(LinearLayout.HORIZONTAL);

        textView = new TextView(context);
        textView.setText(R.string.sup_square);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        textView.setLayoutParams(params);
        textView.setAllCaps(false);

        EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText.setMaxLines(1);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        editText.setLayoutParams(params);
        setEnabled(editText);
        editText.setText(square);

        linearLayoutHorizontal3.addView(textView);
        linearLayoutHorizontal3.addView(editText);
        //--------------------------------
        LinearLayout linearLayoutHorizontal4 = new LinearLayout(context);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutHorizontal4.setLayoutParams(params);
        linearLayoutHorizontal4.setOrientation(LinearLayout.HORIZONTAL);

        textView = new TextView(context);
        textView.setText(R.string.sup_price);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        textView.setLayoutParams(params);
        textView.setAllCaps(false);

        editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText.setMaxLines(1);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        editText.setLayoutParams(params);
        setEnabled(editText);
        editText.setText(price);

        linearLayoutHorizontal4.addView(textView);
        linearLayoutHorizontal4.addView(editText);
        //--------------------------------
        linearLayoutNew.addView(linearLayoutHorizontal1);
        linearLayoutNew.addView(linearLayoutHorizontal2);
        linearLayoutNew.addView(linearLayoutHorizontal3);
        linearLayoutNew.addView(linearLayoutHorizontal4);

        return linearLayoutNew;
    }

    public void deleteLastLayout() {
        if (linearLayouts.size() > 1) {
            linearLayoutMain.removeView(linearLayoutButtons);
            linearLayoutMain.removeView(linearLayouts.get(linearLayouts.size() - 1));
            linearLayouts.remove(linearLayouts.size() - 1);
            linearLayoutMain.addView(linearLayoutButtons);
        }
    }

    public void deleteAllLayouts(boolean exceptFirst) {
        linearLayoutMain.removeView(linearLayoutButtons);
        if (exceptFirst) {
            linearLayoutMain.removeView(linearLayoutButtons);
            for (int i = 1; i < linearLayouts.size(); i++) {
                linearLayoutMain.removeView(linearLayouts.get(i));
                linearLayouts.remove(i);
            }
            linearLayoutMain.addView(linearLayoutButtons);
        } else {
            linearLayoutMain.removeView(linearLayoutButtons);
            for (int i = 0; i < linearLayouts.size(); i++) {
                linearLayoutMain.removeView(linearLayouts.get(i));
                linearLayouts.remove(i);
            }
            linearLayoutMain.addView(linearLayoutButtons);
        }
    }

    public void findAllChildren(LinearLayout layout, ArrayList<View> result) {
        /*
        0 - tv
        1 - tv
        2 - tv material
        3 - tv
        4 - et square
        5 - tv
        6 - et price
         */
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if (view instanceof LinearLayout)
                findAllChildren((LinearLayout) view, result);
            else {
                result.add(view);
            }
        }
    }

    public void clearLayout(LinearLayout linearLayout) {
        ArrayList<View> views = new ArrayList<>();
        findAllChildren(linearLayout, views);
        ((TextView)views.get(2)).setText("");
        ((TextView)views.get(2)).setHint("Выберите материал");
        for (int i = 0; i < views.size(); i++) {
            if (views.get(i) instanceof EditText) {
                ((EditText) views.get(i)).setText("");
            }
        }
    }

    public ArrayList<String> getContent(LinearLayout layout) {
        /*
        вспомогательная функция для получения всего содержимого определенного лэйаута(слоя)
        возвращаем эрэйлист со значениями
        0 - материал
        1 - площадь
        2 - цена
         */
        ArrayList<String> result = new ArrayList<>();
        ArrayList<View> views = new ArrayList<>();
        findAllChildren(layout, views);
        for (View view : views) {
            if (view instanceof EditText) {
                result.add(((EditText) view).getText().toString().trim().isEmpty() ? "<empty>" : ((EditText) view).getText().toString().trim());
            }
            else if (view instanceof TextView && views.indexOf(view) == 2) {
                result.add(((TextView) view).getText().toString().trim().isEmpty() ? "<empty>" : ((TextView) view).getText().toString());
            }
        }
        return result;
    }

    private void setEnabled(EditText editText) {
        editText.setClickable(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }

}
