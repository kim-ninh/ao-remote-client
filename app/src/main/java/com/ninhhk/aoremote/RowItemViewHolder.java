package com.ninhhk.aoremote;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ninhhk.aoremote.model.Remote;

public class RowItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView img_device_icon;
    private TextView txt_device_name;

    public RowItemViewHolder(@NonNull View itemView) {
        super(itemView);
        img_device_icon = itemView.findViewById(R.id.img_device_icon);
        txt_device_name = itemView.findViewById(R.id.txt_device_name);
    }

    public void bindText(String name) {
        txt_device_name.setText(name);
    }

    public void bindImage(@DrawableRes int resId) {
        img_device_icon.setImageResource(resId);
    }

    public void setViewClickListener(final Remote remote, final RemoteAdapter.OnItemClickListener clickListener) {
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(remote);
                }
            }
        });
    }
}
