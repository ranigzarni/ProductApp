package com.projectrani.productapp;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import org.w3c.dom.Text;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ProductList> productLists;
    private Context context;

    public MyAdapter(List<ProductList> productLists, Context context) {
        this.productLists = productLists;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductList productList = productLists.get(position);

        String brandAPI = productList.getDesc();
        try {

            String[] brand = brandAPI.split(":");
            holder.textViewDesc.setText(brand[1]);
        } catch (ArrayIndexOutOfBoundsException e){
            holder.textViewDesc.setText(productList.getDesc());
        }

        String kodeOdoo = productList.getImageURL();
        String imageUrl = "https://sfa.pti-cosmetics.com/sfa/web/image/" + kodeOdoo + ".png";
        //Picasso.with(context).load(imageUrl).fit().centerInside().into(holder.imageView);
        Picasso.with(getContext())
                .load(imageUrl)
                .placeholder(R.drawable.noimage) // Your dummy image...
                .into(holder.imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // Image is loaded successfully...
                    }

                    @Override
                    public void onError() {
                        // Unable to load image, may be due to incorrect URL, no network...
                    }
                });


        holder.textViewTitle.setText(productList.getTitle());

    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textViewTitle;
        public TextView textViewDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductList productList = productLists.get(getAdapterPosition());
                    Intent i = new Intent(v.getContext(), DetailsActivity.class);
                    i.putExtra("title", productList.getTitle());
                    i.putExtra("image", productList.getImageURL());
                    i.putExtra("brand", productList.getDesc());
                    i.putExtra("productId", productList.getProductId());
                    i.putExtra("gtPrice", productList.getGtPrice());
                    i.putExtra("mtPrice", productList.getMtPrice());
                    i.putExtra("gtBatam", productList.getGtBatam());
                    i.putExtra("mtBatam", productList.getMtBatam());
                    i.putExtra("barcode", productList.getBarcode());
                    i.putExtra("unit", productList.getUnit());
                    i.putExtra("rule", productList.getRule());
                    v.getContext().startActivity(i);
                }
            });

            imageView = itemView.findViewById(R.id.image_view);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewDesc = itemView.findViewById(R.id.description);
        }
    }
}
