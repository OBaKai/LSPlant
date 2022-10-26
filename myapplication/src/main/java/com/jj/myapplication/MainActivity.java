package com.jj.myapplication;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.lsposed.lsplant.LSPlantHooker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.util.Arrays;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.btn_hook).setOnClickListener(view -> {
            hook();
        });

        findViewById(R.id.btn_do).setOnClickListener(view -> {
            ((TextView)findViewById(R.id.tv)).setText("love");
        });
    }

    private void hook(){
        try {
            LSPlantHooker.hook(TextView.class.getDeclaredMethod("setText", CharSequence.class),
                    new LSPlantHooker.HookCallback() {
                        @Override
                        public void beforeHookedMethod(Member method, Object thisObj, Object[] params) {
                            Log.e("llk", "beforeHookedMethod " + method.getName() + " params=" + Arrays.toString(params));
                        }

                        @Override
                        public void afterHookedMethod(Member method, Object thisObj, Object result) {
                            Log.e("llk", "afterHookedMethod " + method.getName() + " result=" + result);
                        }
                    });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}