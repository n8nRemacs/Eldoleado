package androidx.camera.core.streamsharing;

import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.H;
import androidx.camera.core.impl.V;
import androidx.camera.core.processing.C;
import androidx.concurrent.futures.b;
import androidx.core.util.z;
import com.google.common.util.concurrent.D0;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/* compiled from: VirtualCameraControl.java */
@X
/* loaded from: classes.dex */
public class j extends V {

    /* renamed from: c, reason: collision with root package name */
    public final c f24561c;

    public j(@N CameraControlInternal cameraControlInternal, @N c cVar) {
        super(cameraControlInternal);
        this.f24561c = cVar;
    }

    @Override // androidx.camera.core.impl.V, androidx.camera.core.impl.CameraControlInternal
    @N
    public final D0 d(@N ArrayList arrayList, int i12, int i13) {
        D0 d0E;
        z.a("Only support one capture config.", arrayList.size() == 1);
        Object objF = 100;
        try {
            objF = ((H) arrayList.get(0)).f23969b.f(H.f23966j);
        } catch (IllegalArgumentException unused) {
        }
        Integer num = (Integer) objF;
        Objects.requireNonNull(num);
        final int iIntValue = num.intValue();
        Object objF2 = 0;
        try {
            objF2 = ((H) arrayList.get(0)).f23969b.f(H.f23965i);
        } catch (IllegalArgumentException unused2) {
        }
        Integer num2 = (Integer) objF2;
        Objects.requireNonNull(num2);
        final int iIntValue2 = num2.intValue();
        C c12 = this.f24561c.f24540a.f24543p;
        if (c12 != null) {
            final androidx.camera.core.processing.k kVar = c12.f24404a;
            d0E = androidx.camera.core.impl.utils.futures.f.h(androidx.concurrent.futures.b.a(new b.c() { // from class: androidx.camera.core.processing.g
                @Override // androidx.concurrent.futures.b.c
                public final Object d(b.a aVar) {
                    k kVar2 = kVar;
                    kVar2.getClass();
                    kVar2.b(new RunnableC20138f(1, kVar2, new C20133a(iIntValue, iIntValue2, aVar)), new x(aVar, 1));
                    return "DefaultSurfaceProcessor#snapshot";
                }
            }));
        } else {
            d0E = androidx.camera.core.impl.utils.futures.f.e(new Exception("Failed to take picture: pipeline is not ready."));
        }
        return androidx.camera.core.impl.utils.futures.f.b(Collections.singletonList(d0E));
    }
}
