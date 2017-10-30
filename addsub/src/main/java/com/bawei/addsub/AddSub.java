package com.bawei.addsub;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by 张祺钒
 * on2017/10/30.
 */

public class AddSub extends EditText implements TextWatcher {
    public AddSub(Context context) {
        this(context,null);
    }

    public AddSub(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AddSub(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        System.out.println("-----------------------");
        System.out.println(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
