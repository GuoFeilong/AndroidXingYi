package com.android.commonframe.ui.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.android.commonframe.R;


/**
 * 带清除按钮的editext
 * Created by feilong.guo on 16/11/11.
 */

public class PaymentClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher, Callback {
    private Drawable mClearDrawable;

    public PaymentClearEditText(Context context) {
        this(context, null);
    }

    public PaymentClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PaymentClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.icon_et_clear);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
        // 禁止粘贴
        setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        setLongClickable(false);
        setCustomSelectionActionModeCallback(this);
        setHintTextColor(Color.parseColor("#CCCCCC"));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setClearIconVisible(s.toString().length() > 0);
    }

    @Override
    public boolean onActionItemClicked(ActionMode arg0, MenuItem arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onCreateActionMode(ActionMode arg0, Menu arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
        // TODO Auto-generated method stub
        return false;
    }


}