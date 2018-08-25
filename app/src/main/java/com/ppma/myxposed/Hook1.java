package com.ppma.myxposed;

import android.view.KeyEvent;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook1 implements IXposedHookLoadPackage {


    public static String apkName = "com.mingrisoft_it_education";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals(apkName)) return;
        XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, Boolean.TYPE, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                if (param.hasThrowable()) return;
                Class<?> cls = (Class<?>) param.getResult();
                String cls_Name = cls.getName();
                XposedBridge.log("当前Class: " + (String) param.args[0]);

                XposedHelpers.findAndHookMethod("org.json.JSONObject", cls.getClassLoader(), "get", String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String key = (String) param.args[0];
                        if ("picture".equals(key)) {
                            if (param.getResult().equals("http://www.mingrisoft.com/Public/images/lv1.png")) {
                                XposedBridge.log("JSONObject" + " " + key + " = " + (String) param.getResult());
                                XposedBridge.log("改成V2会员");
                                param.setResult("http://www.mingrisoft.com/Public/images/lv2.png");
                            }
                        }
                        if ("new_vip_name".equals(key)) {
                            XposedBridge.log("JSONObject" + " " + key + " = " + (String) param.getResult());
                            XposedBridge.log("改成V2会员");
                            param.setResult("V2");
                        }
                        if (key.equals("new_vip_level")) {
                            XposedBridge.log("JSONObject" + " " + key + " = " + (String) param.getResult());
                            XposedBridge.log("改成V2会员");
                            param.setResult("2");
                        }

                    }


                });

                if (!cls_Name.equals("com.mingrisoft_it_education.MainActivity")) return;
                XposedHelpers.findAndHookMethod(cls, "onKeyDown", int.class, KeyEvent.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if (param.hasThrowable()) return;
                        int key = (Integer) param.args[0];
                        XposedBridge.log(String.valueOf(key));

                    }
                });

            }

        });


    }

}
