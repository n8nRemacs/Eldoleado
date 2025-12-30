package androidx.camera.core.impl;

import androidx.camera.core.InterfaceC20128n0;
import androidx.camera.core.T;
import androidx.camera.core.impl.Config;

/* compiled from: ImageAnalysisConfig.java */
@j.X
/* loaded from: classes.dex */
public final class Y implements L0<androidx.camera.core.T>, InterfaceC20088d0, androidx.camera.core.internal.j {

    /* renamed from: G, reason: collision with root package name */
    public static final Config.a<Integer> f24063G = Config.a.a(T.b.class, "camerax.core.imageAnalysis.backpressureStrategy");

    /* renamed from: H, reason: collision with root package name */
    public static final Config.a<Integer> f24064H = Config.a.a(Integer.TYPE, "camerax.core.imageAnalysis.imageQueueDepth");

    /* renamed from: I, reason: collision with root package name */
    public static final Config.a<InterfaceC20128n0> f24065I = Config.a.a(InterfaceC20128n0.class, "camerax.core.imageAnalysis.imageReaderProxyProvider");

    /* renamed from: J, reason: collision with root package name */
    public static final Config.a<Integer> f24066J = Config.a.a(T.e.class, "camerax.core.imageAnalysis.outputImageFormat");

    /* renamed from: K, reason: collision with root package name */
    public static final Config.a<Boolean> f24067K = Config.a.a(Boolean.class, "camerax.core.imageAnalysis.onePixelShiftEnabled");

    /* renamed from: L, reason: collision with root package name */
    public static final Config.a<Boolean> f24068L = Config.a.a(Boolean.class, "camerax.core.imageAnalysis.outputImageRotationEnabled");

    /* renamed from: F, reason: collision with root package name */
    public final s0 f24069F;

    public Y(@j.N s0 s0Var) {
        this.f24069F = s0Var;
    }

    @Override // androidx.camera.core.impl.x0
    @j.N
    public final Config getConfig() {
        return this.f24069F;
    }

    @Override // androidx.camera.core.impl.InterfaceC20086c0
    public final int getInputFormat() {
        return 35;
    }
}
