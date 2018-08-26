package com.ppma.myxposed;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.logging.Handler;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Hook2 implements IXposedHookLoadPackage {

    public static String apkName = "com.mingrisoft_it_education";
    public static String start = "http://www.mingrisoft.com/Public/images/lv";

    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(apkName)) return;
        XposedBridge.log("目标程序：" + apkName);

        XposedHelpers.findAndHookMethod("com.stub.StubApp", lpparam.classLoader, "ᵢˋ", Context.class, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Context context = (Context) param.args[0];
                ClassLoader classLoader = context.getClassLoader();
                util("Class",context.getClass().getName());
                if(context.getClass().getName().equals("com.mingrisoft_it_education.VideoMedia.VideoImplement"))
                XposedHelpers.findAndHookMethod("com.mingrisoft_it_education.VideoMedia.VideoImplement", classLoader, "ObtainVideoPageData", Context.class, Handler.class, String.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        String key = (String) param.args[2];
                        util("courseUrl",key);
                        key="http://www.mingrisoft.com/ApiNew/getVideoInfoNew/video_id/2809/course_type/1/token/qwert/user_id/100000";
                    }
                });

                XposedHelpers.findAndHookMethod("org.json.JSONObject", classLoader, "get", String.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String key = (String) param.args[0];

                        if ("picture".equals(key)) {
                            if (((String) param.getResult()).startsWith(start)) {
                                util(key, param.getResult());
                                XposedBridge.log("改成V2会员");
                                param.setResult(start + "2.png");
                            }
                        }
                        if ("new_vip_name".equals(key)) {
                            util(key, param.getResult());
                            XposedBridge.log("改成V2会员");
                            param.setResult("V2");
                        }
                        if (key.equals("new_vip_level")) {
                            util(key, param.getResult());
                            XposedBridge.log("改成V2会员");
                            param.setResult("2");
                        }

                        if ("end_time".equals(key)) {
                            util(key, param.getResult());
                            XposedBridge.log("改成V2会员");
                            param.setResult("2019-09-09");
                        }

                    }


                });



                XposedHelpers.findAndHookMethod("org.json.JSONObject", classLoader, "getString", String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        String key = (String) param.args[0];
                        if (key.equals("video")) util(key, (String) param.getResult());
                        if (key.equals("file_path")) util(key, (String) param.getResult());
                        if (key.equals("level_resource")) util(key, (String) param.getResult());
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

    public void util(String key, Object code) {
        XposedBridge.log(key + ": " + code);
    }

}
