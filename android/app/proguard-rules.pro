-keepattributes LineNumberTable,SourceFile
-keep class androidx.core.app.NotificationCompat$* { *; }
-keep public class * extends androidx.fragment.app.Fragment

-keep class kotlinx.** { *; }

# This is generated automatically by the Android Gradle plugin.-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn edu.umd.cs.findbugs.annotations.SuppressFBWarnings
-dontwarn java.lang.instrument.ClassDefinition
-dontwarn java.lang.instrument.IllegalClassFormatException
-dontwarn java.lang.instrument.UnmodifiableClassException
-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean
-dontwarn org.junit.jupiter.api.extension.ExtendWith
-dontwarn org.junit.jupiter.api.extension.ExtensionContext$Namespace
-dontwarn org.junit.jupiter.api.extension.ExtensionContext$Store$CloseableResource
-dontwarn org.junit.jupiter.api.extension.ExtensionContext$Store
-dontwarn org.junit.jupiter.api.extension.ExtensionContext
-dontwarn org.junit.jupiter.api.extension.InvocationInterceptor$Invocation
-dontwarn org.junit.jupiter.api.extension.InvocationInterceptor
-dontwarn org.junit.jupiter.api.extension.ReflectiveInvocationContext
-dontwarn org.junit.jupiter.api.parallel.ResourceAccessMode
-dontwarn org.junit.jupiter.api.parallel.ResourceLock
-dontwarn org.junit.platform.commons.support.AnnotationSupport
-dontwarn reactor.blockhound.BlockHound$Builder
-dontwarn reactor.blockhound.integration.BlockHoundIntegration


# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# @Serializable and @Polymorphic are used at runtime for polymorphic serialization.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

-dontwarn org.slf4j.impl.StaticLoggerBinder
-dontwarn org.slf4j.impl.StaticMDCBinder