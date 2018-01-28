# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 保护类中的所有方法名
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   *;
}
#混淆前后的映射
-printmapping mapping.txt
# 警告略过
-ignorewarnings
#
-keepattributes Exceptions,InnerClasses,Signature,*Annotation*,SourceFile,LineNumberTable,Deprecated,LocalVariable*Table,Synthetic,EnclosingMethod

# 指定代码的压缩级别
-optimizationpasses 5
# 是否使用大小写混合
-dontusemixedcaseclassnames
# 混淆时是否做预校验
-dontpreverify
# 混淆时是否记录日志
-verbose
#不压缩指定的文件
-dontshrink
# 不优化指定的文件
-dontoptimize

#混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep class com.dysj.R$*{
	static final int *;
}

#友盟
-keepclassmembers class * {
   <init>(org.json.JSONObject);
}
-keepclassmembers enum * {
    static **[] values();
    static ** valueOf(java.lang.String);
}

-keep class com.umeng.fb.ui.ThreadView {}
-keep,allowshrinking class org.android.agoo.service.* {
    <fields>;
    <methods>;
}

-keep,allowshrinking class com.umeng.message.* {
    <fields>;
    <methods>;
}
# 友盟 end

#EventBus

-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(Java.lang.Throwable);
}


#微信
-keep interface com.tencent.**
-keep class com.tencent.** {*;}
-keep class com.tencent.mm.sdk.** {*;}
#微信 end

#科大讯飞
-keep class com.iflytek.**{*;}
#-keepattributes Signature
-libraryjars libs/armeabi/libmsc.so
#科大讯飞 end

-keep class * extends android.app.Fragment
-keep class * extends android.app.Activity      # 保持哪些类不被混淆
-keep class * extends android.app.Application   # 保持哪些类不被混淆
-keep class * extends android.app.Service       # 保持哪些类不被混淆
-keep class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-keep class android.support.** {*;}
-keep class * extends android.support.v4.**
-keep class * extends android.support.v4.**{*;}
-keep class * extends android.support.v7.**
-keep class * extends android.support.v7.**{*;}
-keep class * extends android.support.annotation.**

# 保持移动统计的代码不被混淆
-keep class cn.cmcc.android.logcollect.** {*;}
#-dontwarn class cn.cmcc.android.logcollect.**

# 保留在Activity中的方法参数是view的方法，这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{ void *(android.view.View); }

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * { void *(**On*Event); void *(**On*Listener); }

#------------------  下方是android平台自带的排除项，这里不要动         ----------------

-keep public class * extends android.app.Activity{
	public <fields>;
	public <methods>;
}

-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepattributes *Annotation*

-keepclasseswithmembernames class *{
	native <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * {
    ... *JNI*(...);
}

-keepclasseswithmembernames class * {
	... *JRI*(...);
}

-keep class **JNI* {*;}


#https://github.com/Justson/AgentWeb
-keep class com.just.library.** {
    *;
}
-dontwarn com.just.library.**
-keepclassmembers class com.just.library.agentweb.AndroidInterface{ *; }


#高德地图


    #3D 地图 V5.0.0之前：
    -keep   class com.amap.api.maps.**{*;}
    -keep   class com.autonavi.amap.mapcore.*{*;}
    -keep   class com.amap.api.trace.**{*;}

    #3D 地图 V5.0.0之后：
    -keep   class com.amap.api.maps.**{*;}
    -keep   class com.autonavi.**{*;}
    -keep   class com.amap.api.trace.**{*;}

    #定位
    -keep class com.amap.api.location.**{*;}
    -keep class com.amap.api.fence.**{*;}
    -keep class com.autonavi.aps.amapapi.model.**{*;}

    #搜索
    -keep   class com.amap.api.services.**{*;}

    #2D地图
    -keep class com.amap.api.maps2d.**{*;}
    -keep class com.amap.api.mapcore2d.**{*;}

    #导航
    -keep class com.amap.api.navi.**{*;}
    -keep class com.autonavi.**{*;}



    -keep class com.chad.library.adapter.** {
    *;
    }
    -keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
    -keep public class * extends com.chad.library.adapter.base.BaseViewHolder
    -keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
         <init>(...);
    }



    -keep class com.blankj.utilcode.** { *; }
    -keepclassmembers class com.blankj.utilcode.** { *; }
    -dontwarn com.blankj.utilcode.**

    -dontwarn com.squareup.picasso.**
    -dontwarn com.bumptech.glide.**

    -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep public class * extends com.bumptech.glide.module.AppGlideModule
    -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
      **[] $VALUES;
      public *;
    }

    # for DexGuard only
    -keepresourcexmlelements manifest/application/meta-data@value=GlideModule

    -dontwarn com.tencent.bugly.**
    -keep public class com.tencent.bugly.**{*;}
    -keep class android.support.**{*;}


    #3D 地图 V5.0.0之前：
    -keep   class com.amap.api.maps.**{*;}
    -keep   class com.autonavi.amap.mapcore.*{*;}
    -keep   class com.amap.api.trace.**{*;}

    #3D 地图 V5.0.0之后：
    -keep   class com.amap.api.maps.**{*;}
    -keep   class com.autonavi.**{*;}
    -keep   class com.amap.api.trace.**{*;}

    #定位
    -keep class com.amap.api.location.**{*;}
    -keep class com.amap.api.fence.**{*;}
    -keep class com.autonavi.aps.amapapi.model.**{*;}

    #搜索
    -keep   class com.amap.api.services.**{*;}

    #2D地图
    -keep class com.amap.api.maps2d.**{*;}
    -keep class com.amap.api.mapcore2d.**{*;}

    #导航
    -keep class com.amap.api.navi.**{*;}
    -keep class com.autonavi.**{*;}

    #https://github.com/huanghaibin-dev/CalendarView
    -keepclasseswithmembers class * {
        public <init>(android.content.Context);
    }