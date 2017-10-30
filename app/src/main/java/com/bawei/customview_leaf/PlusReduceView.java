package com.bawei.customview_leaf;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlusReduceView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private TextView plus, reduce;
    private EditText sumEdit;
    private int totalsum;
    private Context context;
    public PlusReduceView(Context context) {
        super(context);
        init(context);
    }

    public PlusReduceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(context);
    }

    public PlusReduceView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        this.context=context;
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.addsubnew, this, true);
        plus = (TextView) findViewById(R.id.view_plus);
        reduce = (TextView) findViewById(R.id.view_reduce);
        sumEdit = (EditText) findViewById(R.id.view_sum);
        plus.setOnClickListener(this);
        reduce.setOnClickListener(this);
        sumEdit.addTextChangedListener(this);
    }

    //初始化数据；
    public void initData(int num) {
        totalsum = num;
        sumEdit.setText(totalsum + "");
    }

    public int getSumData() {
        return totalsum;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_plus:
                getSum();
                initData(totalsum += 1);
                break;
            case R.id.view_reduce:
                getSum();
                if (totalsum <= 1) {
                    Toast.makeText(context,"不买请滚,谢谢",Toast.LENGTH_SHORT).show();
                } else {
                    initData(totalsum -= 1);
                }
                break;

            default:
                break;
        }
    }

    private void getSum() {
        String text = sumEdit.getText().toString().trim();
        totalsum = Integer.parseInt(text);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().equals(totalsum + "")) {
            getSum();
            initData(totalsum);
        }
        if(listener!=null){
            listener.changed(editable.toString());
        }

    }

    public  interface  Listener{
        void changed(String string);
    }
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
