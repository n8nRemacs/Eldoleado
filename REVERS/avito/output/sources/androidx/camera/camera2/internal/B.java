package androidx.camera.camera2.internal;

import D.h;
import android.hardware.camera2.CameraCaptureSession;
import androidx.camera.camera2.internal.A;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.H;

/* compiled from: Camera2CaptureOptionUnpacker.java */
@j.X
/* loaded from: classes.dex */
class B implements H.b {

    /* renamed from: a, reason: collision with root package name */
    public static final B f22908a = new B();

    @Override // androidx.camera.core.impl.H.b
    @j.S
    public void a(@j.N androidx.camera.core.impl.Z z12, @j.N H.a aVar) {
        int i12;
        androidx.camera.core.impl.H h12 = (androidx.camera.core.impl.H) z12.g(androidx.camera.core.impl.L0.f23998r, null);
        androidx.camera.core.impl.s0 s0Var = androidx.camera.core.impl.s0.f24172H;
        Config.a<Integer> aVar2 = androidx.camera.core.impl.H.f23965i;
        androidx.camera.core.impl.H hE2 = new H.a().e();
        if (h12 != null) {
            aVar.a(h12.f23972e);
            s0Var = h12.f23969b;
            i12 = h12.f23970c;
        } else {
            i12 = hE2.f23970c;
        }
        aVar.f23977b = androidx.camera.core.impl.n0.S(s0Var);
        aVar.f23978c = ((Integer) z12.g(androidx.camera.camera2.impl.b.f22889G, Integer.valueOf(i12))).intValue();
        aVar.b(new Z((CameraCaptureSession.CaptureCallback) z12.g(androidx.camera.camera2.impl.b.f22893K, new A.b())));
        aVar.c(h.a.c(z12).a());
    }
}
