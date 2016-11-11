# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/user/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 7
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


-keep public class * extends android.support.v4.app.FragmentActivity
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.Fragment
-keep public class com.android.vending.licensing.ILicensingService
-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient
-keep class android.support.v4.**{*;}
-keep class android.app.ActivityManagerNative
-keep class sun.misc.Unsafe
-keep class com.android.vending.licensing.ILicensingService
-keep class android.webkit.**{*;}
-keep class com.asiatravel.asiatravel.api.**{*;}

-keep class * implements java.io.Serializable
-keep class com.asiatravel.asiatravel.model.** { *; }
-keep class com.asiatravel.common.tracking.**{ *;}
-keep class com.asiatravel.common.api.**{ *;}

-keep class com.alibaba.fastjson.** { *; }
#baidu location
-keep class com.baidu.location.**{*;}
-keep class android.net.http.** { *; }
-keep class org.codehaus.mojo.**{*;}
-renamesourcefileattribute SourceFile
-keepattributes Signature,SourceFile,LineNumberTable
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-dontwarn android.net.http.**
-dontwarn org.codehaus.mojo.**
-dontwarn android.support.v4.**
-dontwarn android.webkit.WebView
-dontwarn android.net.http.SslError
-dontwarn android.webkit.**
-dontwarn android.support.**
-dontwarn com.markupartist.**
-dontwarn com.alibaba.fastjson.**

#-keep class com.asiatravel.asiatravel.activity.promotion.ATCommonPromotionsActivity$JsInterface {
#    public <fields>;
#    public <methods>;
#}
#
#-keep class com.asiatravel.asiatravel.api.request.ATAPIRequest {
#    <methods>;
#}

-keepclassmembers class * extends WebView{
    public <methods>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class com.umeng.**{*;}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keep public class com.asiatravel.asiatravel.R$*{
    public static final int *;
}

#fabric needs
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keep public class * extends java.lang.Exception

#retrifit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.*.**
-dontwarn java.nio.**
-keep class okio.*.**

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#rx
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#Alipay
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }

-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**

-keep class com.ut.** {*;}
-dontwarn com.ut.**

-keep class com.ta.** {*;}
-dontwarn com.ta.**

-keep class anet.**{*;}
-keep class org.android.spdy.**{*;}
-keep class org.android.agoo.**{*;}
-dontwarn anet.**
-dontwarn org.android.spdy.**
-dontwarn org.android.agoo.**

#weichat pay
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}

#Union pay
-keep class org.simalliance.openmobileapi.** {*;}
-keep class org.simalliance.openmobileapi.service.** {*;}
-keep class com.unionpay.** {*;}
-dontwarn com.unionpay.**
#talking data
-dontwarn com.tendcloud.tenddata.**
-keep class com.tendcloud.** {*;}
-keep public class com.tendcloud.tenddata.** { public protected *;}
-keepclassmembers class com.tendcloud.tenddata.**{
public void *(***);
}
-keep class com.talkingdata.sdk.TalkingDataSDK {public *;}
-keep class com.apptalkingdata.** {*;}