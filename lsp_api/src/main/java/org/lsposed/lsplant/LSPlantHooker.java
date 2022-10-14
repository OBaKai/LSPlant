package org.lsposed.lsplant;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class LSPlantHooker {

    static {
        System.loadLibrary("lsp_api");
    }

    private Method backup; //用于执行原有方法
    private Member target;
    private HookCallback callback;

    public interface HookCallback{
        void beforeHookedMethod(Member method, Object[] params) throws Throwable;
        void afterHookedMethod(Member method, Object result) throws Throwable;
    }

    private LSPlantHooker() {
    }

    private native Method doHook(Member original, Method callback);

    private native boolean doUnhook(Member target);

    public Object nativeCallback(Object[] args) throws Throwable {
        //args第一个参数为hook方法所在的对象，后面的才是参数
        Object targetObject = args[0];
        Object[] params = null;
        if (args.length > 1){
            params = new Object[args.length - 1];
            System.arraycopy(args, 1, params, 0, args.length - 1);
        }

        callback.beforeHookedMethod(target, params);
        Object result = backup.invoke(targetObject, params);
        callback.afterHookedMethod(target, result);
        return result;
    }

    public boolean unhook() {
        return doUnhook(target);
    }

    public static LSPlantHooker hook(Member target, HookCallback callback) {
        if (callback == null) return null;

        LSPlantHooker hooker = new LSPlantHooker();
        try {
            //当hook目标被调用，native就会通知给这个java方法
            var callbackMethod = LSPlantHooker.class.getDeclaredMethod("nativeCallback", Object[].class);
            var result = hooker.doHook(target, callbackMethod);
            if (result == null) return null;
            hooker.backup = result;
            hooker.target = target;
            hooker.callback = callback;
        } catch (NoSuchMethodException ignored) { }

        return hooker;
    }
}
