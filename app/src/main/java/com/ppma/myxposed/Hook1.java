package com.ppma.myxposed;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;



public class Hook1 implements IXposedHookLoadPackage {


    public static String apkName = "com.mingrisoft_it_education";
    public static String start = "http://www.mingrisoft.com/Public/images/lv";
    public SharedPreferences m_sp;
    public String m_user_id;

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
                if(cls_Name.startsWith(apkName)) util("当前Class", cls_Name);

                if (cls_Name.equals("com.mingrisoft_it_education.VideoMedia.VideoMediaPlayPage")) {
                    XposedHelpers.findAndHookMethod(cls_Name, cls.getClassLoader(), "initData", Bundle.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            //spLogin
                            Object object = param.thisObject;
                            Class page = object.getClass();
                            Method method = page.getMethod("getSharedPreferences",new Class[]{String.class,int.class});
                            m_sp = (SharedPreferences) method.invoke(object,new Object[]{"login",0});
                            m_user_id = m_sp.getString("user_id", "");
                            util("m_sp",m_user_id);
                            m_sp.edit().putString("user_id", "106013").commit();
/*                            Field spLogin = page.getDeclaredField("spLogin");
                            spLogin.setAccessible(true);*/
                            /*m_sp = (SharedPreferences) */
/*                            Object obj_spLogin = spLogin.get(param.thisObject);
                            Class[] classes = new Class[]{String.class,String.class};
                            Object[] objects= new Object[]{"user_id", ""};
                            String user_id = (String)callMethod(obj_spLogin,"getString",classes,objects);
                            util("user_id",user_id);*/
/*                            m_user_id = m_sp.getString("user_id", "");
                            util("user_id",m_user_id);*/
//                            m_sp.edit().putString("user_id", "106013").commit();

                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            util("user_id",m_sp.getString("user_id",""));
                            m_sp.edit().putString("user_id", m_user_id).commit();
/*                            util("afterHookedMethod", "VideoMediaPlayPage");
                            Class page = param.thisObject.getClass();
                            //entityId
                            Field entityId = page.getDeclaredField("entityId");
                            entityId.setAccessible(true);
                            //courseType
                            Field courseType = page.getDeclaredField("courseType");
                            courseType.setAccessible(true);
                            //courseUrl
                            Field courseUrl = page.getDeclaredField("courseUrl");
                            courseUrl.setAccessible(true);
                            courseUrl.set(param.thisObject,
                                    "http://www.mingrisoft.com/ApiNew/getVideoInfoNew/"
                                            + "video_id/"
                                            + entityId.get(param.thisObject)
                                            + "/course_type/"
                                            + courseType.get(param.thisObject)
                                            + "/token/qwert"
                                            + "/user_id/"
                                            + "106013");
                            //courseUrl
                            Field mHandler = page.getDeclaredField("mHandler");
                            mHandler.setAccessible(true);
                            //videoImplement
                            Field videoImplement = page.getDeclaredField("videoImplement");
                            videoImplement.setAccessible(true);
                            Object obj_videoImplement = videoImplement.get(param.thisObject);
                            Class[] classes = new Class[]{Context.class, Handler.class, String.class, int.class};
                            Object[] obj_togther = new Object[]{param.thisObject, mHandler.get(param.thisObject), courseUrl.get(param.thisObject), 100};
                            callMethod(obj_videoImplement, "ObtainVideoPageData", classes, obj_togther);*/
                        }
                    });
                }

                if (cls_Name.startsWith("com.mingrisoft_it_education")) util("Class", cls_Name);

                XposedHelpers.findAndHookMethod("org.json.JSONObject", cls.getClassLoader(), "get", String.class, new XC_MethodHook() {
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


            }

        });


    }

    public void util(String modifier, String key, Object code) {
        XposedBridge.log(modifier + " " + key + " " + code);
    }

    public void util(String key, Object code) {
        XposedBridge.log(key + ": " + code);
    }

    public static Object callMethod(Object instance, String methodName, Class[] classes, Object[] objects)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        Method method = instance.getClass().getDeclaredMethod(methodName, classes);
        method.setAccessible(true);
        return method.invoke(instance, objects);
    }

}
