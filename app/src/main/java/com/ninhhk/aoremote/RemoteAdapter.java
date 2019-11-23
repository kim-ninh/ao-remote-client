package com.ninhhk.aoremote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ninhhk.aoremote.model.Remote;

import java.util.ArrayList;
import java.util.List;

public class RemoteAdapter extends RecyclerView.Adapter<RowItemViewHolder> {
    private static String tv;
    private static String ac;
    private static String fan;
    private static String projector;
    private static String tv_box;
    private List<Remote> remotes = new ArrayList<>();
    private OnItemClickListener clickListener;

    public RemoteAdapter(List<Remote> remotes) {
        this.remotes = remotes;
    }

    public RemoteAdapter(List<Remote> remotes, OnItemClickListener clickListener) {
        this.remotes = remotes;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RowItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.remote_item, parent, false);
        loadStringResource(parent.getContext());
        return new RowItemViewHolder(v);
    }

    private void loadStringResource(Context context) {
        tv = context.getString(R.string.tv);
        ac = context.getString(R.string.ac);
        fan = context.getString(R.string.fan);
        projector = context.getString(R.string.projector);
        tv_box = context.getString(R.string.tv_box);
    }

    @Override
    public void onBindViewHolder(@NonNull RowItemViewHolder holder, int position) {
        Remote remote = remotes.get(position);
        holder.bindText(remote.getName());
        holder.bindImage(getIcon(remote.getDeviceType()));
        holder.setViewClickListener(remote, clickListener);
    }

    private @DrawableRes
    int getIcon(String deviceType) {
        if (deviceType.equals(tv))
            return R.drawable.television_type;

        if (deviceType.equals(ac))
            return R.drawable.air_conditioner_type;

        if (deviceType.equals(fan))
            return R.drawable.fan_type;

        if (deviceType.equals(projector))
            return R.drawable.projector_type;

        if (deviceType.equals(tv_box))
            return R.drawable.apple_tv_type;

        return 0;
    }

    @Override
    public int getItemCount() {
        return remotes.size();
    }


    interface OnItemClickListener {
        void onItemClick(Remote remote);
    }

}
