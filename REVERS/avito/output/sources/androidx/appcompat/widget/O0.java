package androidx.appcompat.widget;

import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import androidx.annotation.RestrictTo;
import j.InterfaceC42164u;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: ViewUtils.java */
@RestrictTo
/* loaded from: classes.dex */
public class O0 {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f22233a;

    /* renamed from: b, reason: collision with root package name */
    public static Method f22234b;

    /* compiled from: ViewUtils.java */
    @j.X
    public static class a {
        @InterfaceC42164u
        public static void a(@j.N View view, @j.N Rect rect, @j.N Rect rect2) {
            Insets systemWindowInsets = view.computeSystemWindowInsets(new WindowInsets.Builder().setSystemWindowInsets(Insets.of(rect)).build(), rect2).getSystemWindowInsets();
            rect.set(systemWindowInsets.left, systemWindowInsets.top, systemWindowInsets.right, systemWindowInsets.bottom);
        }
    }

    public static void a(@j.N View view, @j.N Rect rect, @j.N Rect rect2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 29) {
            a.a(view, rect, rect2);
            return;
        }
        if (!f22233a) {
            f22233a = true;
            try {
                Method declaredMethod = View.class.getDeclaredMethod("computeFitSystemWindows", Rect.class, Rect.class);
                f22234b = declaredMethod;
                if (!declaredMethod.isAccessible()) {
                    f22234b.setAccessible(true);
                }
            } catch (NoSuchMethodException unused) {
            }
        }
        Method method = f22234b;
        if (method != null) {
            try {
                method.invoke(view, rect, rect2);
            } catch (Exception unused2) {
            }
        }
    }
}
