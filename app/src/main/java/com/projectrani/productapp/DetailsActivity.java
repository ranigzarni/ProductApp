package com.projectrani.productapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.paris.Paris;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private static final String URL_DATA = "https://sfa-api.pti-cosmetics.com/product?limit=20";

    TextView tvProductName, tvDefCode, tvProductId, tvProductRule, tvGtPrice, tvMtPrice, tvGtBatam, tvMtBatam, tvBarcode, tvUnit;
    TextView details, titleDefCode, titleProductId, titleProductRule, titleGtPrice, titleMtPrice, titleGtBatam, titleMtBatam, titleBarcode, titleUnit;
    ImageView imgViewProduct;
    RelativeLayout layout;
    String oldCode, productId, productRule;
    int gtPrice, mtPrice, gtBatam, mtBatam, unit;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvProductName = findViewById(R.id.productName);
        imgViewProduct = findViewById(R.id.imageProduct);
        tvDefCode = findViewById(R.id.odooCode);
        tvProductId = findViewById(R.id.productId);
        tvProductRule = findViewById(R.id.productRule);
        tvGtPrice = findViewById(R.id.gtPrice);
        tvMtPrice = findViewById(R.id.mtPrice);
        tvGtBatam = findViewById(R.id.gtBatam);
        tvMtBatam = findViewById(R.id.mtBatam);
        tvBarcode = findViewById(R.id.barcode);
        tvUnit = findViewById(R.id.unit);

        details = findViewById(R.id.details);
        titleDefCode = findViewById(R.id.titleOdooCode);
        titleProductId = findViewById(R.id.titleProductId);
        titleProductRule = findViewById(R.id.titleProductRule);
        titleGtPrice = findViewById(R.id.titleGtPrice);
        titleMtPrice = findViewById(R.id.titleMtPrice);
        titleGtBatam = findViewById(R.id.titleGtBatam);
        titleMtBatam = findViewById(R.id.titleMtBatam);
        titleBarcode = findViewById(R.id.titleBarcode);
        titleUnit = findViewById(R.id.titleUnit);

        layout = findViewById(R.id.mainLayout);

        Intent i = getIntent();

        String brandAPI = i.getStringExtra("brand");
        String wardah = "brand:Wardah";
        String ix = "brand:IX";
        String putri = "brand:Putri";
        ImageView imageBrand = findViewById(R.id.imageBrand);
        if (brandAPI.equals(wardah) == true){
            imageBrand.setBackground(getResources().getDrawable(R.drawable.wardah));
            View view = this.getWindow().getDecorView();
            view.setBackgroundColor(getResources().getColor(R.color.bgwardah));
            layoutWardah();
        }
        else if (brandAPI.equals(ix) == true){
            imageBrand.setBackground(getResources().getDrawable(R.drawable.ix));
            View view = this.getWindow().getDecorView();
            view.setBackgroundColor(getResources().getColor(R.color.bgix));
        }
        else if (brandAPI.equals(putri) == true){
            imageBrand.setBackground(getResources().getDrawable(R.drawable.putri));
            View view = this.getWindow().getDecorView();
            view.setBackgroundColor(getResources().getColor(R.color.bgix));
        }

        String title = i.getStringExtra("title");
        tvProductName.setText(title);

        String kodeOdoo = i.getStringExtra("image");
        String imageUrl = "https://sfa.pti-cosmetics.com/sfa/web/image/" + kodeOdoo + ".png";
        Picasso.with(context).load(imageUrl).fit().centerInside().into(imgViewProduct);
        tvDefCode.setText(kodeOdoo);

        String productId = i.getStringExtra("productId");
        tvProductId.setText(productId);

        String gtPrice = i.getStringExtra("gtPrice");
        tvGtPrice.setText("Rp " + gtPrice);

        String mtPrice = i.getStringExtra("mtPrice");
        tvMtPrice.setText("Rp " + mtPrice);

        String gtBatam = i.getStringExtra("gtBatam");
        tvGtBatam.setText("Rp " + gtBatam);

        String mtBatam = i.getStringExtra("mtBatam");
        tvMtBatam.setText("Rp " + mtBatam);

        String barcode = i.getStringExtra("barcode");
        tvBarcode.setText(barcode);

        String unit = i.getStringExtra("unit");
        tvUnit.setText(unit);

        String productRule = i.getStringExtra("rule");
        String[] rule = productRule.split("_");
        tvProductRule.setText(rule[1] + " " + rule[2]);
    }

    private void layoutWardah(){
        tvProductName.setTextColor(getResources().getColor(R.color.textwardah));
        tvProductRule.setTextColor(getResources().getColor(R.color.textwardah));
        tvUnit.setTextColor(getResources().getColor(R.color.textwardah));
        tvBarcode.setTextColor(getResources().getColor(R.color.textwardah));
        tvMtBatam.setTextColor(getResources().getColor(R.color.textwardah));
        tvGtBatam.setTextColor(getResources().getColor(R.color.textwardah));
        tvMtPrice.setTextColor(getResources().getColor(R.color.textwardah));
        tvGtPrice.setTextColor(getResources().getColor(R.color.textwardah));
        tvProductId.setTextColor(getResources().getColor(R.color.textwardah));
        tvDefCode.setTextColor(getResources().getColor(R.color.textwardah));

        details.setTextColor(getResources().getColor(R.color.textwardah));
        titleUnit.setTextColor(getResources().getColor(R.color.textwardah));
        titleBarcode.setTextColor(getResources().getColor(R.color.textwardah));
        titleMtBatam.setTextColor(getResources().getColor(R.color.textwardah));
        titleGtBatam.setTextColor(getResources().getColor(R.color.textwardah));
        titleMtPrice.setTextColor(getResources().getColor(R.color.textwardah));
        titleGtPrice.setTextColor(getResources().getColor(R.color.textwardah));
        titleProductId.setTextColor(getResources().getColor(R.color.textwardah));
        titleDefCode.setTextColor(getResources().getColor(R.color.textwardah));
        titleProductRule.setTextColor(getResources().getColor(R.color.textwardah));
        titleProductId.setTextColor(getResources().getColor(R.color.textwardah));
    }

    private void layoutIx() {

    }

    private void layoutMakeOver() {

    }

    private void layoutEmina() {

    }

}