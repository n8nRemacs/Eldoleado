package androidx.camera.video;

import androidx.camera.core.C20140q0;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

/* compiled from: Recorder.java */
/* loaded from: classes.dex */
class N implements androidx.camera.core.impl.utils.futures.c<InterfaceC20169k> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ l0 f24717a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ O f24718b;

    public N(O o12, l0 l0Var) {
        this.f24718b = o12;
        this.f24717a = l0Var;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        Objects.toString(th2);
        C20140q0.d(3, "Recorder");
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(@j.P InterfaceC20169k interfaceC20169k) {
        InterfaceC20169k interfaceC20169k2;
        InterfaceC20169k interfaceC20169k3 = interfaceC20169k;
        Objects.toString(interfaceC20169k3);
        C20140q0.d(3, "Recorder");
        if (interfaceC20169k3 == null) {
            return;
        }
        O o12 = this.f24718b;
        ScheduledFuture<?> scheduledFuture = o12.f24748W;
        if (scheduledFuture != null && scheduledFuture.cancel(false) && (interfaceC20169k2 = o12.f24728C) != null && interfaceC20169k2 == interfaceC20169k3) {
            O.q(interfaceC20169k2);
        }
        o12.f24751Z = this.f24717a;
        o12.z(null);
        o12.t(o12.n());
    }
}
