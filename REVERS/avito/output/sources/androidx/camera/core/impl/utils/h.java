package androidx.camera.core.impl.utils;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import j.InterfaceC42164u;
import j.N;
import j.P;
import j.X;

/* compiled from: ContextUtil.java */
@X
/* loaded from: classes.dex */
public final class h {

    /* compiled from: ContextUtil.java */
    @X
    public static class a {
        @InterfaceC42164u
        @N
        public static Context a(@N Context context, @P String str) {
            return context.createAttributionContext(str);
        }

        @InterfaceC42164u
        @P
        public static String b(@N Context context) {
            return context.getAttributionTag();
        }
    }

    @N
    public static Context a(@N Context context) {
        String strB;
        Context applicationContext = context.getApplicationContext();
        return (Build.VERSION.SDK_INT < 30 || (strB = a.b(context)) == null) ? applicationContext : a.a(applicationContext, strB);
    }

    @P
    public static Application b(@N Context context) {
        String strB;
        Context contextA = a(context);
        while (contextA instanceof ContextWrapper) {
            if (contextA instanceof Application) {
                return (Application) contextA;
            }
            ContextWrapper contextWrapper = (ContextWrapper) contextA;
            Context baseContext = contextWrapper.getBaseContext();
            contextA = (Build.VERSION.SDK_INT < 30 || (strB = a.b(contextWrapper)) == null) ? baseContext : a.a(baseContext, strB);
        }
        return null;
    }
}
