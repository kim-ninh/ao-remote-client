package com.ninhhk.aoremote.selecting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ninhhk.aoremote.R;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private String[] brands = new String[0];
    private OnBrandItemClickListenter onBrandItemClickListenter;

    public BrandAdapter(String[] brands, OnBrandItemClickListenter onBrandItemClickListenter) {
        this.brands = brands;
        this.onBrandItemClickListenter = onBrandItemClickListenter;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.remote_brand_item, parent, false);
        return new BrandViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        String brand = brands[position];
        holder.bind(brand, onBrandItemClickListenter);
    }

    @Override
    public int getItemCount() {
        return brands.length;
    }

    interface OnBrandItemClickListenter {
        void onBrandItemClick(String brandName);
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_brand_name;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_brand_name = itemView.findViewById(R.id.txt_brand_name);
        }

        public void bind(final String brandName, final BrandAdapter.OnBrandItemClickListenter onBrandItemClickListener) {
            txt_brand_name.setText(brandName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBrandItemClickListener != null) {
                        onBrandItemClickListener.onBrandItemClick(brandName);
                    }
                }
            });
        }
    }
}