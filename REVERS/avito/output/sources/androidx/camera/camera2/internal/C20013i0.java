package androidx.camera.camera2.internal;

import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.util.Size;
import android.view.Display;
import androidx.camera.core.impl.SurfaceConfig;
import java.util.Locale;

/* compiled from: DisplayInfoManager.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.i0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C20013i0 {

    /* renamed from: e, reason: collision with root package name */
    public static final Size f23343e = new Size(1920, 1080);

    /* renamed from: f, reason: collision with root package name */
    public static final Size f23344f = new Size(320, 240);

    /* renamed from: g, reason: collision with root package name */
    public static final Size f23345g = new Size(640, 480);

    /* renamed from: h, reason: collision with root package name */
    public static final Object f23346h = new Object();

    /* renamed from: i, reason: collision with root package name */
    public static volatile C20013i0 f23347i;

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final DisplayManager f23348a;

    /* renamed from: b, reason: collision with root package name */
    public volatile Size f23349b = null;

    /* renamed from: c, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.k f23350c = new androidx.camera.camera2.internal.compat.workaround.k();

    /* renamed from: d, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.d f23351d = new androidx.camera.camera2.internal.compat.workaround.d();

    public C20013i0(@j.N Context context) {
        this.f23348a = (DisplayManager) context.getSystemService("display");
    }

    @j.N
    public static C20013i0 b(@j.N Context context) {
        if (f23347i == null) {
            synchronized (f23346h) {
                try {
                    if (f23347i == null) {
                        f23347i = new C20013i0(context);
                    }
                } finally {
                }
            }
        }
        return f23347i;
    }

    @j.P
    public static Display d(@j.N Display[] displayArr, boolean z12) {
        Display display = null;
        int i12 = -1;
        for (Display display2 : displayArr) {
            if (!z12 || display2.getState() != 1) {
                Point point = new Point();
                display2.getRealSize(point);
                int i13 = point.x * point.y;
                if (i13 > i12) {
                    display = display2;
                    i12 = i13;
                }
            }
        }
        return display;
    }

    public final Size a() {
        Size sizeC;
        Point point = new Point();
        c(false).getRealSize(point);
        Size size = new Size(point.x, point.y);
        Size size2 = androidx.camera.core.internal.utils.c.f24370a;
        if (size.getHeight() * size.getWidth() < androidx.camera.core.internal.utils.c.a(f23344f)) {
            size = this.f23351d.f23250a != null ? (Size) androidx.camera.camera2.internal.compat.quirk.E.f23209a.get(Build.MODEL.toUpperCase(Locale.US)) : null;
            if (size == null) {
                size = f23345g;
            }
        }
        if (size.getHeight() > size.getWidth()) {
            size = new Size(size.getHeight(), size.getWidth());
        }
        int height = size.getHeight() * size.getWidth();
        Size size3 = f23343e;
        if (height > size3.getHeight() * size3.getWidth()) {
            size = size3;
        }
        return (this.f23350c.f23257a == null || (sizeC = androidx.camera.camera2.internal.compat.quirk.o.c(SurfaceConfig.ConfigType.f24041b)) == null) ? size : sizeC.getHeight() * sizeC.getWidth() > size.getHeight() * size.getWidth() ? sizeC : size;
    }

    @j.N
    public final Display c(boolean z12) {
        Display[] displays = this.f23348a.getDisplays();
        if (displays.length == 1) {
            return displays[0];
        }
        Display displayD = d(displays, z12);
        if (displayD == null && z12) {
            displayD = d(displays, false);
        }
        if (displayD != null) {
            return displayD;
        }
        throw new IllegalArgumentException("No display can be found from the input display manager!");
    }

    @j.N
    public final Size e() {
        if (this.f23349b != null) {
            return this.f23349b;
        }
        this.f23349b = a();
        return this.f23349b;
    }
}
