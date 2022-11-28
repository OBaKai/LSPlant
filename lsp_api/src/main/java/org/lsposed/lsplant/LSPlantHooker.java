package org.lsposed.lsplant;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

//aar打包： ./gradlew :lsp_api:bundleDebugAar
public class LSPlantHooker {

    static {
        System.loadLibrary("lsp_api");
    }

    private Method backup; //用于执行原有方法
    private Member target;
    private HookCallback callback;

    public interface HookCallback{
        void beforeHookedMethod(Member method, Object thisObj, Object[] params) throws Throwable;
        Object afterHookedMethod(Member method, Object thisObj, Object result) throws Throwable;
    }

    private LSPlantHooker() {
    }

    private native Method doHook(Member original, Method callback);

    private native boolean doUnhook(Member target);

    public Object nativeCallback(Object[] args) throws Throwable {
        //Log.e("llk", "nativeCallback=" + Arrays.toString(args));
        Object targetObject = null;
        Object[] params = null;
        //三种情况
        //静态方法（无参）：args直接是空的
        //静态方法（有参）：args都是传参的数据，args[0]并不是目标对象
        //非静态方法：args[0]是目标对象
        if (args != null && args.length > 0){
            if (!Modifier.isStatic(target.getModifiers())){ //非静态方法
                targetObject = args[0];
                params = new Object[args.length - 1];
                System.arraycopy(args, 1, params, 0, args.length - 1);
            }else {
                params = new Object[args.length];
                System.arraycopy(args, 0, params, 0, args.length);
            }
        }

        callback.beforeHookedMethod(target, targetObject, params);
        Object result = backup.invoke(targetObject, params);
        Object new_result = callback.afterHookedMethod(target, targetObject, result);
        if (new_result == null){
            return result;
        }
        return new_result;
    }

    public boolean unhook() {
        return doUnhook(target);
    }

    public static LSPlantHooker hook(Member target, HookCallback callback) {
        if (callback == null) return null;

        LSPlantHooker hooker = new LSPlantHooker();
        try {
            //当hook目标被调用，native就会通知给这个java方法
            Method callbackMethod = LSPlantHooker.class.getDeclaredMethod("nativeCallback", Object[].class);
            Method result = hooker.doHook(target, callbackMethod);
            if (result == null) return null;
            hooker.backup = result;
            hooker.target = target;
            hooker.callback = callback;
        } catch (NoSuchMethodException ignored) { }

        return hooker;
    }
}
