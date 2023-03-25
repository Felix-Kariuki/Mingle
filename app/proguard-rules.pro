# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
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

# Firebase
-keep class com.google.android.gms.** { *; }
-keep class com.google.firebase.** { *; }

#common
-keep class com.flexcode.wedate.common.data.LogService.** { *; }
-keep class com.flexcode.wedate.common.data.LogServiceImpl.** { *; }
##auth
-keep class  com.flexcode.wedate.auth.data.models.** { *; }
-keep class  com.flexcode.wedate.auth.data.local.datastore.** { *; }
##chatscreen
-keep class  com.flexcode.wedate.chatsscreen.data.model.** { *; }
##home
-keep class  com.flexcode.wedate.home.data.model.** { *; }
##love calc
-keep class  com.flexcode.wedate.lovecalculator.domain.model.** { *; }
-keep class  com.flexcode.wedate.lovecalculator.data.dto.** { *; }
## matches
-keep class  com.flexcode.wedate.matches.data.model.** { *; }

