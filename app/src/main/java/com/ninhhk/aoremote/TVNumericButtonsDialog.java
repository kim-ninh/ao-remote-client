package com.ninhhk.aoremote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Set;

public class TVNumericButtonsDialog extends BottomSheetDialogFragment {

    private final Set<View> views;

    public TVNumericButtonsDialog(Set<View> views) {
        this.views = views;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tv_numberic_buttons, container, false);

        views.add(v.findViewById(R.id.digit_0));
        views.add(v.findViewById(R.id.digit_1));
        views.add(v.findViewById(R.id.digit_2));
        views.add(v.findViewById(R.id.digit_3));
        views.add(v.findViewById(R.id.digit_4));
        views.add(v.findViewById(R.id.digit_5));
        views.add(v.findViewById(R.id.digit_6));
        views.add(v.findViewById(R.id.digit_7));
        views.add(v.findViewById(R.id.digit_8));
        views.add(v.findViewById(R.id.digit_9));
        views.add(v.findViewById(R.id.digit_seperator));

        return v;
    }
}
