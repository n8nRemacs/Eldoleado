package androidx.camera.camera2.internal;

import D.h;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.util.Rational;
import android.util.Size;
import androidx.camera.camera2.impl.b;
import androidx.camera.camera2.internal.A;
import androidx.camera.camera2.internal.S;
import androidx.camera.camera2.internal.U;
import androidx.camera.camera2.internal.compat.quirk.C20001l;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.SessionConfig;
import java.util.Iterator;

/* compiled from: Camera2SessionOptionUnpacker.java */
@j.X
/* loaded from: classes.dex */
final class O implements SessionConfig.d {

    /* renamed from: a, reason: collision with root package name */
    public static final O f23045a = new O();

    @Override // androidx.camera.core.impl.SessionConfig.d
    @j.S
    public final void a(@j.N Size size, @j.N androidx.camera.core.impl.L0<?> l02, @j.N SessionConfig.b bVar) {
        SessionConfig sessionConfigH = l02.H();
        Config config = androidx.camera.core.impl.s0.f24172H;
        int i12 = SessionConfig.a().f24017f.f23970c;
        if (sessionConfigH != null) {
            androidx.camera.core.impl.H h12 = sessionConfigH.f24017f;
            i12 = h12.f23970c;
            Iterator<CameraDevice.StateCallback> it = sessionConfigH.f24013b.iterator();
            while (it.hasNext()) {
                bVar.c(it.next());
            }
            Iterator<CameraCaptureSession.StateCallback> it2 = sessionConfigH.f24014c.iterator();
            while (it2.hasNext()) {
                bVar.h(it2.next());
            }
            bVar.a(h12.f23972e);
            config = h12.f23969b;
        }
        bVar.q(config);
        if (l02 instanceof androidx.camera.core.impl.u0) {
            Rational rational = androidx.camera.camera2.internal.compat.workaround.o.f23263a;
            if (((androidx.camera.camera2.internal.compat.quirk.C) C20001l.f23216a.b(androidx.camera.camera2.internal.compat.quirk.C.class)) != null) {
                if (!androidx.camera.camera2.internal.compat.workaround.o.f23263a.equals(new Rational(size.getWidth(), size.getHeight()))) {
                    b.a aVar = new b.a();
                    aVar.c(CaptureRequest.TONEMAP_MODE, 2);
                    bVar.e(aVar.a());
                }
            }
        }
        bVar.s(((Integer) l02.g(androidx.camera.camera2.impl.b.f22889G, Integer.valueOf(i12))).intValue());
        bVar.c((CameraDevice.StateCallback) l02.g(androidx.camera.camera2.impl.b.f22891I, new U.b()));
        bVar.h((CameraCaptureSession.StateCallback) l02.g(androidx.camera.camera2.impl.b.f22892J, new S.b()));
        bVar.b(new Z((CameraCaptureSession.CaptureCallback) l02.g(androidx.camera.camera2.impl.b.f22893K, new A.b())));
        androidx.camera.core.impl.n0 n0VarR = androidx.camera.core.impl.n0.R();
        Config.a aVar2 = androidx.camera.camera2.impl.b.f22894L;
        n0VarR.K(aVar2, (androidx.camera.camera2.impl.d) l02.g(aVar2, androidx.camera.camera2.impl.d.b()));
        Config.a aVar3 = androidx.camera.camera2.impl.b.f22896N;
        n0VarR.K(aVar3, (String) l02.g(aVar3, null));
        Config.a aVar4 = androidx.camera.camera2.impl.b.f22890H;
        Long l12 = (Long) l02.g(aVar4, -1L);
        l12.getClass();
        n0VarR.K(aVar4, l12);
        bVar.e(n0VarR);
        bVar.e(h.a.c(l02).a());
    }
}
