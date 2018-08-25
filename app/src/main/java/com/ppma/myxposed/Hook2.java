package com.ppma.myxposed;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Hook2 implements IXposedHookLoadPackage {

    public static String apkName = "com.mingrisoft_it_education";

    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(apkName)) return;
        XposedBridge.log("目标程序：" + apkName);
        XposedHelpers.findAndHookMethod("com.stub.StubApp", lpparam.classLoader, "ᵢˋ", Context.class, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Context context = (Context) param.args[0];
                ClassLoader classLoader = context.getClassLoader();
                XposedHelpers.findAndHookMethod("org.json.JSONObject", classLoader, "get", String.class, new XC_MethodHook() {

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


                XposedHelpers.findAndHookMethod("com.mingrisoft_it_education.Individual.MyVipActivity", classLoader, "userInfoResult", String.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String key = (String) param.args[0];
                        XposedBridge.log("jsonStr:" + key);
                    }
                });
            }
        });


    }

}
