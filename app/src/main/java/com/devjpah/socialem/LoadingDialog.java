package com.devjpah.socialem;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog dialog;
    private String message;
    private TextView tvLoading;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public LoadingDialog(Activity activity, String message) {
        this.activity = activity;
        this.message = message;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View root = inflater.inflate(R.layout.loading,null);
        builder.setView(root);
        builder.setCancelable(true);
        String msg = (message != null) ? message : "Cargando...";
        tvLoading = root.findViewById(R.id.tv_loading);
        tvLoading.setText(msg);
        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
