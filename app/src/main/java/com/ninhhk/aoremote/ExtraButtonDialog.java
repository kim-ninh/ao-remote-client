package com.ninhhk.aoremote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ninhhk.aoremote.model.RemoteButton;

import java.util.List;

public class ExtraButtonDialog extends BottomSheetDialogFragment {

    private List<RemoteButton> remoteButtons;
    private View.OnClickListener clickListener;

    public ExtraButtonDialog(List<RemoteButton> remoteButtons, View.OnClickListener clickListener) {
        this.remoteButtons = remoteButtons;
        this.clickListener = clickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.extra_button_list, container, false);
        RecyclerView rcvExtraButton = v.findViewById(R.id.rcv_extra_button);
        ButtonAdapter buttonAdapter = new ButtonAdapter(remoteButtons, clickListener);

        rcvExtraButton.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rcvExtraButton.setAdapter(buttonAdapter);

        return v;
    }

    public static class ButtonAdapter extends RecyclerView.Adapter<ButtonViewHolder> {
        private final View.OnClickListener clickListener;
        private List<RemoteButton> remoteButtons;

        public ButtonAdapter(List<RemoteButton> remoteButtons, View.OnClickListener clickListener) {
            this.remoteButtons = remoteButtons;
            this.clickListener = clickListener;
        }

        @NonNull
        @Override
        public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.extra_button_item, parent, false);
            return new ButtonViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
            RemoteButton currentButton = remoteButtons.get(position);
            holder.bind(currentButton, clickListener);
        }

        @Override
        public int getItemCount() {
            return remoteButtons.size();
        }
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder {
        private Button button;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.extra_button);
        }

        public void bind(RemoteButton remoteButton, View.OnClickListener clickListener) {
            String hexStr = remoteButton.getCode();
//            byte[] bytes = IRUtils.prontoHexToBytes(hexStr);

            button.setText(remoteButton.getName());
            button.setTag(remoteButton.getName());
            button.setTag(R.id.ir_data, hexStr);
            button.setOnClickListener(clickListener);
        }
    }

}
