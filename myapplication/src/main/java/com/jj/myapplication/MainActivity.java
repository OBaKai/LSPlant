package com.jj.myapplication;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.lsposed.lsplant.LSPlantHooker;

import java.lang.reflect.InvocationTargetException;
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
                    MainActivity.class.getDeclaredMethod("setTextHook", LSPlantHooker.MethodCallback.class),
                    this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setTextHook(LSPlantHooker.MethodCallback cs){
        Log.e("llk", "setTextHook1 " + cs);
        Log.e("llk", "setTextHook2 " + cs.backup);
        Log.e("llk", "setTextHook3 " + Arrays.toString(cs.args));

        try {
            cs.backup.invoke(cs.args[0], cs.args[1]);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //((TextView)cs.args[0]).setText((CharSequence)cs.args[1]);
    }

}