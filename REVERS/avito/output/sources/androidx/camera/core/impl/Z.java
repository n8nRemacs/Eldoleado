package androidx.camera.core.impl;

import androidx.camera.core.C20056e0;
import androidx.camera.core.InterfaceC20128n0;
import androidx.camera.core.impl.Config;

/* compiled from: ImageCaptureConfig.java */
@j.X
/* loaded from: classes.dex */
public final class Z implements L0<C20056e0>, InterfaceC20088d0, androidx.camera.core.internal.f {

    /* renamed from: G, reason: collision with root package name */
    public static final Config.a<Integer> f24070G;

    /* renamed from: H, reason: collision with root package name */
    public static final Config.a<Integer> f24071H;

    /* renamed from: I, reason: collision with root package name */
    public static final Config.a<G> f24072I;

    /* renamed from: J, reason: collision with root package name */
    public static final Config.a<Integer> f24073J;

    /* renamed from: K, reason: collision with root package name */
    public static final Config.a<InterfaceC20128n0> f24074K;

    /* renamed from: L, reason: collision with root package name */
    public static final Config.a<Boolean> f24075L;

    /* renamed from: M, reason: collision with root package name */
    public static final Config.a<Integer> f24076M;

    /* renamed from: N, reason: collision with root package name */
    public static final Config.a<Integer> f24077N;

    /* renamed from: F, reason: collision with root package name */
    public final s0 f24078F;

    static {
        Class cls = Integer.TYPE;
        f24070G = Config.a.a(cls, "camerax.core.imageCapture.captureMode");
        f24071H = Config.a.a(cls, "camerax.core.imageCapture.flashMode");
        f24072I = Config.a.a(G.class, "camerax.core.imageCapture.captureBundle");
        f24073J = Config.a.a(Integer.class, "camerax.core.imageCapture.bufferFormat");
        Config.a.a(Integer.class, "camerax.core.imageCapture.maxCaptureStages");
        f24074K = Config.a.a(InterfaceC20128n0.class, "camerax.core.imageCapture.imageReaderProxyProvider");
        f24075L = Config.a.a(Boolean.TYPE, "camerax.core.imageCapture.useSoftwareJpegEncoder");
        f24076M = Config.a.a(cls, "camerax.core.imageCapture.flashType");
        f24077N = Config.a.a(cls, "camerax.core.imageCapture.jpegCompressionQuality");
    }

    public Z(@j.N s0 s0Var) {
        this.f24078F = s0Var;
    }

    @Override // androidx.camera.core.impl.x0
    @j.N
    public final Config getConfig() {
        return this.f24078F;
    }

    @Override // androidx.camera.core.impl.InterfaceC20086c0
    public final int getInputFormat() {
        return ((Integer) f(InterfaceC20086c0.f24088e)).intValue();
    }
}
