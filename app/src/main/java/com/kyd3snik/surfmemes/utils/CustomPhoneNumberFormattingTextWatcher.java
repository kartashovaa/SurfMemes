package com.kyd3snik.surfmemes.utils;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.widget.EditText;

public class CustomPhoneNumberFormattingTextWatcher extends PhoneNumberFormattingTextWatcher {
    private EditText edTelData;
    private boolean backspacingFlag = false;
    private boolean editedFlag = false;
    private int cursorComplement;

    public CustomPhoneNumberFormattingTextWatcher(EditText edTelData) {
        this.edTelData = edTelData;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        cursorComplement = s.length() - edTelData.getSelectionStart();
        backspacingFlag = count > after;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // nothing to do here =D
    }

    @Override
    public void afterTextChanged(Editable s) {
        String string = s.toString();
        String phone = string.replaceAll("[^\\d]", "");
        if (!editedFlag) {
            if (phone.length() >= 9 && !backspacingFlag) {
                editedFlag = true;
                String ans = "+7 (" + phone.substring(1, 4) + ") " + phone.substring(4, 7) + " " + phone.substring(7, 9) + " " + phone.substring(9);
                edTelData.setText(ans);
                edTelData.setSelection(edTelData.getText().length() - cursorComplement);

            } else if (phone.length() >= 4 && !backspacingFlag) {
                editedFlag = true;
                String ans = "+7 (" + phone.substring(1, 4) + ") " + phone.substring(4);
                edTelData.setText(ans);
                edTelData.setSelection(edTelData.getText().length() - cursorComplement);
            }
        } else {
            editedFlag = false;
        }
    }
}
