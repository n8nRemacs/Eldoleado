package androidx.camera.camera2.internal;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.camera2.internal.C20026p;
import androidx.camera.camera2.internal.D;
import androidx.camera.core.CameraControl;
import androidx.concurrent.futures.b;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class E implements androidx.camera.core.impl.utils.futures.a, b.c {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f22973b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f22974c;

    public /* synthetic */ E(Object obj, int i12) {
        this.f22974c = obj;
        this.f22973b = i12;
    }

    @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
    public com.google.common.util.concurrent.D0 apply(Object obj) {
        TotalCaptureResult totalCaptureResult = (TotalCaptureResult) obj;
        int i12 = D.c.f22950k;
        D.c cVar = (D.c) this.f22974c;
        if (D.b(totalCaptureResult, this.f22973b)) {
            cVar.f22956f = D.c.f22949j;
        }
        return cVar.f22958h.a(totalCaptureResult);
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(final b.a aVar) {
        final C20021m0 c20021m0 = (C20021m0) this.f22974c;
        c20021m0.getClass();
        final int i12 = this.f22973b;
        c20021m0.f23374c.execute(new Runnable() { // from class: androidx.camera.camera2.internal.k0
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v6, types: [androidx.camera.camera2.internal.l0, androidx.camera.camera2.internal.p$c] */
            @Override // java.lang.Runnable
            public final void run() {
                C20021m0 c20021m02 = c20021m0;
                boolean z12 = c20021m02.f23375d;
                final b.a<Integer> aVar2 = aVar;
                if (!z12) {
                    C20023n0 c20023n0 = c20021m02.f23373b;
                    synchronized (c20023n0.f23380a) {
                        c20023n0.f23382c = 0;
                    }
                    aVar2.d(new CameraControl.OperationCanceledException("Camera is not active."));
                    return;
                }
                c20021m02.a();
                androidx.core.util.z.g("mRunningCompleter should be null when starting set a new exposure compensation value", c20021m02.f23376e == null);
                androidx.core.util.z.g("mRunningCaptureResultListener should be null when starting set a new exposure compensation value", c20021m02.f23377f == null);
                final int i13 = i12;
                ?? r12 = new C20026p.c() { // from class: androidx.camera.camera2.internal.l0
                    @Override // androidx.camera.camera2.internal.C20026p.c
                    public final boolean a(TotalCaptureResult totalCaptureResult) {
                        Integer num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_STATE);
                        Integer num2 = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
                        int i14 = i13;
                        b.a aVar3 = aVar2;
                        if (num != null && num2 != null) {
                            int iIntValue = num.intValue();
                            if ((iIntValue == 2 || iIntValue == 3 || iIntValue == 4) && num2.intValue() == i14) {
                                aVar3.b(Integer.valueOf(i14));
                                return true;
                            }
                        } else if (num2 != null && num2.intValue() == i14) {
                            aVar3.b(Integer.valueOf(i14));
                            return true;
                        }
                        return false;
                    }
                };
                c20021m02.f23377f = r12;
                c20021m02.f23376e = aVar2;
                C20026p c20026p = c20021m02.f23372a;
                c20026p.m(r12);
                c20026p.w();
            }
        });
        return "setExposureCompensationIndex[" + i12 + "]";
    }
}
