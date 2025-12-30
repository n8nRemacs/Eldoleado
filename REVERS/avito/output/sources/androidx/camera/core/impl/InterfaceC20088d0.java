package androidx.camera.core.impl;

import android.util.Pair;
import android.util.Size;
import androidx.camera.core.C20055e;
import androidx.camera.core.impl.Config;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ImageOutputConfig.java */
@j.X
/* renamed from: androidx.camera.core.impl.d0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public interface InterfaceC20088d0 extends x0 {

    /* renamed from: g, reason: collision with root package name */
    public static final Config.a<Integer> f24093g = Config.a.a(C20055e.class, "camerax.core.imageOutput.targetAspectRatio");

    /* renamed from: h, reason: collision with root package name */
    public static final Config.a<Integer> f24094h;

    /* renamed from: i, reason: collision with root package name */
    public static final Config.a<Integer> f24095i;

    /* renamed from: j, reason: collision with root package name */
    public static final Config.a<Integer> f24096j;

    /* renamed from: k, reason: collision with root package name */
    public static final Config.a<Size> f24097k;

    /* renamed from: l, reason: collision with root package name */
    public static final Config.a<Size> f24098l;

    /* renamed from: m, reason: collision with root package name */
    public static final Config.a<Size> f24099m;

    /* renamed from: n, reason: collision with root package name */
    public static final Config.a<List<Pair<Integer, Size[]>>> f24100n;

    /* renamed from: o, reason: collision with root package name */
    public static final Config.a<G.c> f24101o;

    /* renamed from: p, reason: collision with root package name */
    public static final Config.a<List<Size>> f24102p;

    /* compiled from: ImageOutputConfig.java */
    /* renamed from: androidx.camera.core.impl.d0$a */
    public interface a<B> {
        @j.N
        B a(@j.N Size size);

        @j.N
        B c(int i12);
    }

    /* compiled from: ImageOutputConfig.java */
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.camera.core.impl.d0$b */
    public @interface b {
    }

    /* compiled from: ImageOutputConfig.java */
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.camera.core.impl.d0$c */
    public @interface c {
    }

    /* compiled from: ImageOutputConfig.java */
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.camera.core.impl.d0$d */
    public @interface d {
    }

    static {
        Class cls = Integer.TYPE;
        f24094h = Config.a.a(cls, "camerax.core.imageOutput.targetRotation");
        f24095i = Config.a.a(cls, "camerax.core.imageOutput.appTargetRotation");
        f24096j = Config.a.a(cls, "camerax.core.imageOutput.mirrorMode");
        f24097k = Config.a.a(Size.class, "camerax.core.imageOutput.targetResolution");
        f24098l = Config.a.a(Size.class, "camerax.core.imageOutput.defaultResolution");
        f24099m = Config.a.a(Size.class, "camerax.core.imageOutput.maxResolution");
        f24100n = Config.a.a(List.class, "camerax.core.imageOutput.supportedResolutions");
        f24101o = Config.a.a(G.c.class, "camerax.core.imageOutput.resolutionSelector");
        f24102p = Config.a.a(List.class, "camerax.core.imageOutput.customOrderedResolutions");
    }

    static void v(@j.N InterfaceC20088d0 interfaceC20088d0) {
        boolean zN2 = interfaceC20088d0.N();
        boolean z12 = interfaceC20088d0.k() != null;
        if (zN2 && z12) {
            throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
        }
        if (interfaceC20088d0.t() != null) {
            if (zN2 || z12) {
                throw new IllegalArgumentException("Cannot use setTargetResolution or setTargetAspectRatio with setResolutionSelector on the same config.");
            }
        }
    }

    @j.N
    default G.c C() {
        return (G.c) f(f24101o);
    }

    default int D(int i12) {
        return ((Integer) g(f24094h, Integer.valueOf(i12))).intValue();
    }

    default int F() {
        return ((Integer) g(f24096j, 0)).intValue();
    }

    @j.P
    default Size L() {
        return (Size) g(f24098l, null);
    }

    default boolean N() {
        return c(f24093g);
    }

    @j.P
    default List h() {
        return (List) g(f24100n, null);
    }

    @j.P
    default Size k() {
        return (Size) g(f24097k, null);
    }

    @j.P
    default Size l() {
        return (Size) g(f24099m, null);
    }

    @j.P
    default ArrayList p() {
        List list = (List) g(f24102p, null);
        if (list != null) {
            return new ArrayList(list);
        }
        return null;
    }

    default int s() {
        return ((Integer) g(f24095i, -1)).intValue();
    }

    @j.P
    default G.c t() {
        return (G.c) g(f24101o, null);
    }

    default int x() {
        return ((Integer) f(f24093g)).intValue();
    }
}
