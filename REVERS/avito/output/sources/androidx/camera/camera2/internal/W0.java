package androidx.camera.camera2.internal;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.camera2.internal.C20026p;
import androidx.camera.core.CameraControl;
import androidx.concurrent.futures.b;
import androidx.view.C23038g0;
import java.util.concurrent.Executor;

/* compiled from: TorchControl.java */
@j.X
/* loaded from: classes.dex */
final class W0 {

    /* renamed from: a, reason: collision with root package name */
    public final C20026p f23078a;

    /* renamed from: b, reason: collision with root package name */
    public final C23038g0<Integer> f23079b = new C23038g0<>(0);

    /* renamed from: c, reason: collision with root package name */
    public final boolean f23080c;

    /* renamed from: d, reason: collision with root package name */
    public final Executor f23081d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f23082e;

    /* renamed from: f, reason: collision with root package name */
    public b.a<Void> f23083f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f23084g;

    public W0(@j.N C20026p c20026p, @j.N androidx.camera.camera2.internal.compat.n nVar, @j.N Executor executor) {
        this.f23078a = c20026p;
        this.f23081d = executor;
        this.f23080c = androidx.camera.camera2.internal.compat.workaround.g.a(new C(nVar, 7));
        c20026p.m(new C20026p.c() { // from class: androidx.camera.camera2.internal.V0
            @Override // androidx.camera.camera2.internal.C20026p.c
            public final boolean a(TotalCaptureResult totalCaptureResult) {
                W0 w02 = this.f23074a;
                if (w02.f23083f != null) {
                    Integer num = (Integer) totalCaptureResult.getRequest().get(CaptureRequest.FLASH_MODE);
                    if ((num != null && num.intValue() == 2) == w02.f23084g) {
                        w02.f23083f.b(null);
                        w02.f23083f = null;
                    }
                }
                return false;
            }
        });
    }

    public static void b(@j.N C23038g0 c23038g0, Integer num) {
        if (androidx.camera.core.impl.utils.t.b()) {
            c23038g0.setValue(num);
        } else {
            c23038g0.postValue(num);
        }
    }

    public final void a(@j.P b.a<Void> aVar, boolean z12) {
        if (!this.f23080c) {
            if (aVar != null) {
                aVar.d(new IllegalStateException("No flash unit"));
                return;
            }
            return;
        }
        boolean z13 = this.f23082e;
        C23038g0<Integer> c23038g0 = this.f23079b;
        if (!z13) {
            b(c23038g0, 0);
            if (aVar != null) {
                aVar.d(new CameraControl.OperationCanceledException("Camera is not active."));
                return;
            }
            return;
        }
        this.f23084g = z12;
        this.f23078a.o(z12);
        b(c23038g0, Integer.valueOf(z12 ? 1 : 0));
        b.a<Void> aVar2 = this.f23083f;
        if (aVar2 != null) {
            aVar2.d(new CameraControl.OperationCanceledException("There is a new enableTorch being set"));
        }
        this.f23083f = aVar;
    }
}
