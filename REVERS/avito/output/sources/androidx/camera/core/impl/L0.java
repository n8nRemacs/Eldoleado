package androidx.camera.core.impl;

import android.util.Range;
import androidx.camera.core.C20144v;
import androidx.camera.core.N0;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.H;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.internal.i;
import androidx.camera.core.internal.k;

/* compiled from: UseCaseConfig.java */
@j.X
/* loaded from: classes.dex */
public interface L0<T extends N0> extends androidx.camera.core.internal.i<T>, androidx.camera.core.internal.k, InterfaceC20086c0 {

    /* renamed from: q, reason: collision with root package name */
    public static final Config.a<SessionConfig> f23997q = Config.a.a(SessionConfig.class, "camerax.core.useCase.defaultSessionConfig");

    /* renamed from: r, reason: collision with root package name */
    public static final Config.a<H> f23998r = Config.a.a(H.class, "camerax.core.useCase.defaultCaptureConfig");

    /* renamed from: s, reason: collision with root package name */
    public static final Config.a<SessionConfig.d> f23999s = Config.a.a(SessionConfig.d.class, "camerax.core.useCase.sessionConfigUnpacker");

    /* renamed from: t, reason: collision with root package name */
    public static final Config.a<H.b> f24000t = Config.a.a(H.b.class, "camerax.core.useCase.captureConfigUnpacker");

    /* renamed from: u, reason: collision with root package name */
    public static final Config.a<Integer> f24001u = Config.a.a(Integer.TYPE, "camerax.core.useCase.surfaceOccupancyPriority");

    /* renamed from: v, reason: collision with root package name */
    public static final Config.a<C20144v> f24002v = Config.a.a(C20144v.class, "camerax.core.useCase.cameraSelector");

    /* renamed from: w, reason: collision with root package name */
    public static final Config.a<Range<Integer>> f24003w = Config.a.a(Range.class, "camerax.core.useCase.targetFrameRate");

    /* renamed from: x, reason: collision with root package name */
    public static final Config.a<Boolean> f24004x;

    /* renamed from: y, reason: collision with root package name */
    public static final Config.a<Boolean> f24005y;

    /* renamed from: z, reason: collision with root package name */
    public static final Config.a<UseCaseConfigFactory.CaptureType> f24006z;

    /* compiled from: UseCaseConfig.java */
    public interface a<T extends N0, C extends L0<T>, B> extends i.a<T, B>, androidx.camera.core.L<T>, k.a<B> {
        @j.N
        C b();
    }

    static {
        Class cls = Boolean.TYPE;
        f24004x = Config.a.a(cls, "camerax.core.useCase.zslDisabled");
        f24005y = Config.a.a(cls, "camerax.core.useCase.highResolutionDisabled");
        f24006z = Config.a.a(UseCaseConfigFactory.CaptureType.class, "camerax.core.useCase.captureType");
    }

    @j.P
    default C20144v A() {
        return (C20144v) g(f24002v, null);
    }

    @j.P
    default SessionConfig H() {
        return (SessionConfig) g(f23997q, null);
    }

    @j.P
    default SessionConfig.d O() {
        return (SessionConfig.d) g(f23999s, null);
    }

    default boolean m() {
        return ((Boolean) g(f24004x, Boolean.FALSE)).booleanValue();
    }

    default boolean q() {
        return ((Boolean) g(f24005y, Boolean.FALSE)).booleanValue();
    }

    default int r() {
        return ((Integer) f(f24001u)).intValue();
    }

    @j.P
    default Range u() {
        return (Range) g(f24003w, null);
    }

    default int y() {
        return ((Integer) g(f24001u, 0)).intValue();
    }

    @j.N
    default UseCaseConfigFactory.CaptureType z() {
        return (UseCaseConfigFactory.CaptureType) f(f24006z);
    }
}
