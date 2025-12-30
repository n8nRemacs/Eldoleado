package androidx.appcompat.widget;

import android.R;
import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.RestrictTo;
import j.InterfaceC42164u;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: DrawableUtils.java */
@RestrictTo
/* loaded from: classes.dex */
public class V {

    /* renamed from: a, reason: collision with root package name */
    public static final int[] f22424a = {R.attr.state_checked};

    /* renamed from: b, reason: collision with root package name */
    public static final int[] f22425b = new int[0];

    /* renamed from: c, reason: collision with root package name */
    public static final Rect f22426c = new Rect();

    /* compiled from: DrawableUtils.java */
    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final boolean f22427a;

        /* renamed from: b, reason: collision with root package name */
        public static final Method f22428b;

        /* renamed from: c, reason: collision with root package name */
        public static final Field f22429c;

        /* renamed from: d, reason: collision with root package name */
        public static final Field f22430d;

        /* renamed from: e, reason: collision with root package name */
        public static final Field f22431e;

        /* renamed from: f, reason: collision with root package name */
        public static final Field f22432f;

        /* JADX WARN: Removed duplicated region for block: B:25:0x004c  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0059  */
        static {
            /*
                r0 = 1
                r1 = 0
                r2 = 0
                java.lang.String r3 = "android.graphics.Insets"
                java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch: java.lang.NoSuchFieldException -> L3f java.lang.ClassNotFoundException -> L42 java.lang.NoSuchMethodException -> L45
                java.lang.Class<android.graphics.drawable.Drawable> r4 = android.graphics.drawable.Drawable.class
                java.lang.String r5 = "getOpticalInsets"
                java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch: java.lang.NoSuchFieldException -> L3f java.lang.ClassNotFoundException -> L42 java.lang.NoSuchMethodException -> L45
                java.lang.reflect.Method r4 = r4.getMethod(r5, r6)     // Catch: java.lang.NoSuchFieldException -> L3f java.lang.ClassNotFoundException -> L42 java.lang.NoSuchMethodException -> L45
                java.lang.String r5 = "left"
                java.lang.reflect.Field r5 = r3.getField(r5)     // Catch: java.lang.NoSuchFieldException -> L36 java.lang.ClassNotFoundException -> L39 java.lang.NoSuchMethodException -> L3c
                java.lang.String r6 = "top"
                java.lang.reflect.Field r6 = r3.getField(r6)     // Catch: java.lang.NoSuchFieldException -> L2f java.lang.ClassNotFoundException -> L32 java.lang.NoSuchMethodException -> L34
                java.lang.String r7 = "right"
                java.lang.reflect.Field r7 = r3.getField(r7)     // Catch: java.lang.Throwable -> L2d
                java.lang.String r8 = "bottom"
                java.lang.reflect.Field r3 = r3.getField(r8)     // Catch: java.lang.Throwable -> L48
                r8 = r0
                goto L4a
            L2d:
                r7 = r1
                goto L48
            L2f:
                r6 = r1
            L30:
                r7 = r6
                goto L48
            L32:
                r6 = r1
                goto L30
            L34:
                r6 = r1
                goto L30
            L36:
                r5 = r1
            L37:
                r6 = r5
                goto L30
            L39:
                r5 = r1
            L3a:
                r6 = r5
                goto L30
            L3c:
                r5 = r1
            L3d:
                r6 = r5
                goto L30
            L3f:
                r4 = r1
                r5 = r4
                goto L37
            L42:
                r4 = r1
                r5 = r4
                goto L3a
            L45:
                r4 = r1
                r5 = r4
                goto L3d
            L48:
                r3 = r1
                r8 = r2
            L4a:
                if (r8 == 0) goto L59
                androidx.appcompat.widget.V.a.f22428b = r4
                androidx.appcompat.widget.V.a.f22429c = r5
                androidx.appcompat.widget.V.a.f22430d = r6
                androidx.appcompat.widget.V.a.f22431e = r7
                androidx.appcompat.widget.V.a.f22432f = r3
                androidx.appcompat.widget.V.a.f22427a = r0
                goto L65
            L59:
                androidx.appcompat.widget.V.a.f22428b = r1
                androidx.appcompat.widget.V.a.f22429c = r1
                androidx.appcompat.widget.V.a.f22430d = r1
                androidx.appcompat.widget.V.a.f22431e = r1
                androidx.appcompat.widget.V.a.f22432f = r1
                androidx.appcompat.widget.V.a.f22427a = r2
            L65:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.V.a.<clinit>():void");
        }
    }

    /* compiled from: DrawableUtils.java */
    @j.X
    public static class b {
        @InterfaceC42164u
        public static Insets a(Drawable drawable) {
            return drawable.getOpticalInsets();
        }
    }

    public static void a(@j.N Drawable drawable) {
        String name = drawable.getClass().getName();
        int i12 = Build.VERSION.SDK_INT;
        if (i12 < 29 || i12 >= 31 || !"android.graphics.drawable.ColorStateListDrawable".equals(name)) {
            return;
        }
        int[] state = drawable.getState();
        if (state == null || state.length == 0) {
            drawable.setState(f22424a);
        } else {
            drawable.setState(f22425b);
        }
        drawable.setState(state);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @j.N
    public static Rect b(@j.N Drawable drawable) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i12 = Build.VERSION.SDK_INT;
        if (i12 >= 29) {
            Insets insetsA = b.a(drawable);
            return new Rect(insetsA.left, insetsA.top, insetsA.right, insetsA.bottom);
        }
        boolean z12 = drawable instanceof androidx.core.graphics.drawable.j;
        Drawable drawableB = drawable;
        if (z12) {
            drawableB = ((androidx.core.graphics.drawable.j) drawable).b();
        }
        if (i12 >= 29) {
            boolean z13 = a.f22427a;
        } else if (a.f22427a) {
            try {
                Object objInvoke = a.f22428b.invoke(drawableB, new Object[0]);
                if (objInvoke != null) {
                    return new Rect(a.f22429c.getInt(objInvoke), a.f22430d.getInt(objInvoke), a.f22431e.getInt(objInvoke), a.f22432f.getInt(objInvoke));
                }
            } catch (IllegalAccessException | InvocationTargetException unused) {
            }
        }
        return f22426c;
    }

    public static PorterDuff.Mode c(int i12, PorterDuff.Mode mode) {
        if (i12 == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i12 == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i12 == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i12) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }
}
