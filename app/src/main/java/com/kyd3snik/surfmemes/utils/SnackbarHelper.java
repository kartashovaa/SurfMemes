package com.kyd3snik.surfmemes.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarHelper {
    public static void showMessage(View view, String message, int color) {
        Snackbar snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snack.getView().setBackgroundColor(color);
        snack.show();
    }
}
