package com.ninhhk.aoremote.Utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.ninhhk.aoremote.R;

import java.util.Set;

public class ActionBarStateUtils {
    public static ActionBarState WAITING_IR_SIGNAL_STATE = new WaitingIRSignalState();
    public static ActionBarState TIMEOUT_WAITING_IR_STATE = new TimeOutWaitingIRState();
    public static ActionBarState RECEIVED_SIGNAL_STATE = new ReceivedSignalState();
    private Context context;
    private TextView txtMsg;
    private ActionBarState actionBarState;
    private Set<View> viewSet;

    public ActionBarStateUtils(Context context, View root, Set<View> viewSet) {
        this.context = context;
        this.viewSet = viewSet;
        txtMsg = root.findViewById(R.id.txt_msg);

        setState(WAITING_IR_SIGNAL_STATE);
    }

    public void setState(@NonNull ActionBarState state) {
        actionBarState = state;
        update();
    }

    public void update() {
        actionBarState.update(context, txtMsg, viewSet);
    }

    public void releaseResource() {
        context = null;
        txtMsg = null;
        viewSet = null;
    }

    public static abstract class ActionBarState {
        public abstract void update(Context context, TextView textView, Set<View> viewSet);
    }

    public static class WaitingIRSignalState extends ActionBarState {

        @Override
        public void update(Context context, TextView textView, Set<View> viewSet) {
            textView.setText(R.string.waiting_ir_signal_state);
            textView.setTextColor(ContextCompat.getColor(context, R.color.waiting_ir_signal));
            textView.setOnClickListener(null);
            ViewsUtil.toggleEnabled(viewSet, false);
        }
    }

    public static class TimeOutWaitingIRState extends ActionBarState {

        @Override
        public void update(Context context, TextView textView, Set<View> viewSet) {
            textView.setText(R.string.timeout_waiting_ir_state);
            textView.setTextColor(ContextCompat.getColor(context, R.color.timeout_waiting_ir));
            textView.setOnClickListener(null);
            ViewsUtil.toggleEnabled(viewSet, false);
        }
    }

    public static class ReceivedSignalState extends ActionBarState {

        @Override
        public void update(Context context, TextView textView, Set<View> viewSet) {
            textView.setText(R.string.received_ir_signal_state);
            textView.setTextColor(ContextCompat.getColor(context, R.color.received_ir_signal));
            textView.setOnClickListener(null);
            ViewsUtil.toggleEnabled(viewSet, true);
        }
    }
}
