package suer_fi.developmentkitapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by sure-fi on 2/5/18.
 */

public class RowLayout extends ViewGroup {
    AttributeSet attributeSet;
    String hint;
    public RowLayout(Context context) {
        super(context);
        Log.d("DEBUG","se ejecuta 1");
    }

    public RowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        attributeSet = attrs;
        Log.d("DEBUG","se ejecuta 2");
        Log.d("DEBUG",String.valueOf(attrs.getAttributeCount()));

        View parentView = (View) getParent();

        for (int i = 0; i < attributeSet.getAttributeCount();i++){
            Log.d("DEBUG","12---" + attributeSet.getAttributeName(i) + "---" +  attributeSet.getAttributeValue(i));
            if("hint".equals(attributeSet.getAttributeName(i))){
                Log.d("DEBUG","00---" + attributeSet.getAttributeValue(i));
                hint = attributeSet.getAttributeValue(i);
                Log.d("DEBUG","01---" + attributeSet.getAttributeValue(i));
            }
        }
    }

    public RowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("DEBUG","se ejecuta 3");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        Log.d("DEBUG","02 --- " +  String.valueOf(count));
        if (count == 1){
            EditText child = (EditText) getChildAt(0);
            child.setHint(hint);
            child.layout(l, t,
                    r, b);
        }

    }

    public static class LayoutParams extends MarginLayoutParams{

        public String hint;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs,R.styleable.RowLayout);

        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(LayoutParams source) {
            super(source);
        }
    }
}