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

        hook();

        findViewById(R.id.btn_do).setOnClickListener(view -> {
            ((TextView)findViewById(R.id.tv)).setText("love");
        });

        findViewById(R.id.btn_a1).setOnClickListener(view -> {
            A.a();
        });

        findViewById(R.id.btn_a2).setOnClickListener(view -> {
            A.aa(10);
        });

        findViewById(R.id.btn_b1).setOnClickListener(view -> {
           int i = new B().b();
            Log.e("llk", "bbbbbbbb " + i);
        });

        findViewById(R.id.btn_b2).setOnClickListener(view -> {
            new B().bb(20);
        });
    }

    private void hook(){
        try {
//            Class<?> a_clazz = Class.forName("com.jj.myapplication.A");
//            LSPlantHooker.hook(a_clazz.getDeclaredMethod("a"),
//                    new LSPlantHooker.HookCallback() {
//                        @Override
//                        public void beforeHookedMethod(Member method, Object thisObj, Object[] params) {
//                            Log.e("llk", "beforeHookedMethod " + method.getName() + " params=" + Arrays.toString(params));
//                        }
//
//                        @Override
//                        public void afterHookedMethod(Member method, Object thisObj, Object result) {
//                            Log.e("llk", "afterHookedMethod " + method.getName() + " result=" + result);
//                        }
//                    });

//            LSPlantHooker.hook(a_clazz.getDeclaredMethod("aa", int.class),
//                    new LSPlantHooker.HookCallback() {
//                        @Override
//                        public void beforeHookedMethod(Member method, Object thisObj, Object[] params) {
//                            Log.e("llk", "beforeHookedMethod " + method.getName() + " params=" + Arrays.toString(params));
//                        }
//
//                        @Override
//                        public void afterHookedMethod(Member method, Object thisObj, Object result) {
//                            Log.e("llk", "afterHookedMethod " + method.getName() + " result=" + result);
//                        }
//                    });

            Class<?> b_clazz = Class.forName("com.jj.myapplication.B");
            LSPlantHooker.hook(b_clazz.getDeclaredMethod("b"),
                    new LSPlantHooker.HookCallback() {
                        @Override
                        public void beforeHookedMethod(Member method, Object thisObj, Object[] params) {
                            Log.e("llk", "beforeHookedMethod " + method.getName() + " params=" + Arrays.toString(params));
                        }

                        @Override
                        public Object afterHookedMethod(Member method, Object thisObj, Object result) {
                            Log.e("llk", "afterHookedMethod " + method.getName() + " result=" + result);
                            return 100;
                        }
                    });
//            LSPlantHooker.hook(b_clazz.getDeclaredMethod("bb", int.class),
//                    new LSPlantHooker.HookCallback() {
//                        @Override
//                        public void beforeHookedMethod(Member method, Object thisObj, Object[] params) {
//                            Log.e("llk", "beforeHookedMethod " + method.getName() + " params=" + Arrays.toString(params));
//                        }
//
//                        @Override
//                        public void afterHookedMethod(Member method, Object thisObj, Object result) {
//                            Log.e("llk", "afterHookedMethod " + method.getName() + " result=" + result);
//                        }
//                    });



//            LSPlantHooker.hook(TextView.class.getDeclaredMethod("setText", CharSequence.class),
//                    new LSPlantHooker.HookCallback() {
//                        @Override
//                        public void beforeHookedMethod(Member method, Object thisObj, Object[] params) {
//                            Log.e("llk", "beforeHookedMethod " + method.getName() + " params=" + Arrays.toString(params));
//                        }
//
//                        @Override
//                        public void afterHookedMethod(Member method, Object thisObj, Object result) {
//                            Log.e("llk", "afterHookedMethod " + method.getName() + " result=" + result);
//                        }
//                    });
        } catch (Exception e) {
            Log.e("llk", "fail=" + e.getMessage());
            e.printStackTrace();
        }
    }

}