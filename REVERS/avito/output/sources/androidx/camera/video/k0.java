package androidx.camera.video;

import androidx.camera.core.C20140q0;
import androidx.camera.video.internal.encoder.InterfaceC20169k;

/* compiled from: VideoEncoderSession.java */
/* loaded from: classes.dex */
class k0 implements androidx.camera.core.impl.utils.futures.c<InterfaceC20169k> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ l0 f25287a;

    public k0(l0 l0Var) {
        this.f25287a = l0Var;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        C20140q0.d(5, "VideoEncoderSession");
        this.f25287a.b();
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final /* bridge */ /* synthetic */ void onSuccess(@j.P InterfaceC20169k interfaceC20169k) {
    }
}
