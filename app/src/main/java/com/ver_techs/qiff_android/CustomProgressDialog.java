package com.ver_techs.qiff_android;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomProgressDialog extends ProgressDialog {

    public CustomProgressDialog(Context context) {
        super(context, R.style.TransparentProgressDialog);
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.custom_progress_dialog_layout);
    }

}