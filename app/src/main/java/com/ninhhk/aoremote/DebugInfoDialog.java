package com.ninhhk.aoremote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DebugInfoDialog extends BottomSheetDialogFragment {

    private String message;

    public DebugInfoDialog(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.debug_info_layout, container, false);
        TextView txtDebugInfo = v.findViewById(R.id.txtDebugInfo);
        txtDebugInfo.setText(message);
        return v;
    }
}
