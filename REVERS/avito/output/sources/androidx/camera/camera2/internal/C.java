package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import androidx.camera.camera2.impl.b;
import androidx.camera.camera2.internal.C20039w;
import androidx.camera.camera2.internal.D;
import androidx.camera.camera2.internal.compat.workaround.w;
import androidx.camera.core.C20140q0;
import androidx.camera.core.CameraControl;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.H;
import androidx.camera.core.impl.InterfaceC20092f0;
import androidx.camera.core.impl.SessionConfig;
import androidx.concurrent.futures.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class C implements b.c, androidx.camera.core.impl.utils.futures.a, w.b, InterfaceC20092f0.a, androidx.camera.camera2.internal.compat.workaround.c {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f22910b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f22911c;

    public /* synthetic */ C(D.c cVar, H.a aVar) {
        this.f22910b = 2;
        this.f22911c = aVar;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0.a
    public void a(InterfaceC20092f0 interfaceC20092f0) throws Exception {
        d1 d1Var = (d1) this.f22911c;
        d1Var.getClass();
        try {
            InterfaceC20120j0 interfaceC20120j0B = interfaceC20092f0.b();
            if (interfaceC20120j0B != null) {
                d1Var.f23314b.b(interfaceC20120j0B);
            }
        } catch (IllegalStateException e12) {
            e12.getMessage();
            C20140q0.b("ZslControlImpl");
        }
    }

    @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
    public com.google.common.util.concurrent.D0 apply(Object obj) {
        int i12 = D.c.f22950k;
        D.c cVar = (D.c) this.f22911c;
        if (!Boolean.TRUE.equals((Boolean) obj)) {
            return androidx.camera.core.impl.utils.futures.f.g(null);
        }
        long j12 = cVar.f22956f;
        G g12 = new G(0);
        Set<CameraCaptureMetaData.AfState> set = D.f22931h;
        D.e eVar = new D.e(j12, g12);
        cVar.f22953c.m(eVar);
        return eVar.f22961b;
    }

    @Override // androidx.camera.camera2.internal.compat.workaround.c
    public Object b(CameraCharacteristics.Key key) {
        return ((androidx.camera.camera2.internal.compat.n) this.f22911c).a(key);
    }

    public com.google.common.util.concurrent.D0 c(CameraDevice cameraDevice, androidx.camera.camera2.internal.compat.params.l lVar, List list) {
        return super/*androidx.camera.camera2.internal.N0*/.e(cameraDevice, lVar, list);
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        androidx.camera.core.impl.r rVar;
        switch (this.f22910b) {
            case 0:
                D.a aVar2 = (D.a) this.f22911c;
                C20034t0 c20034t0 = aVar2.f22942a.f23394h;
                if (c20034t0.f23443d) {
                    H.a aVar3 = new H.a();
                    aVar3.f23978c = c20034t0.f23453n;
                    aVar3.f23981f = true;
                    b.a aVar4 = new b.a();
                    aVar4.c(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 1);
                    aVar3.c(aVar4.a());
                    aVar3.b(new C20032s0(aVar));
                    C20026p c20026p = c20034t0.f23440a;
                    List<androidx.camera.core.impl.H> listSingletonList = Collections.singletonList(aVar3.e());
                    C20039w.e eVar = (C20039w.e) c20026p.f23392f;
                    eVar.getClass();
                    listSingletonList.getClass();
                    C20039w c20039w = C20039w.this;
                    c20039w.getClass();
                    ArrayList arrayList = new ArrayList();
                    for (androidx.camera.core.impl.H h12 : listSingletonList) {
                        H.a aVar5 = new H.a(h12);
                        if (h12.f23970c == 5 && (rVar = h12.f23975h) != null) {
                            aVar5.f23983h = rVar;
                        }
                        if (Collections.unmodifiableList(h12.f23968a).isEmpty() && h12.f23973f) {
                            HashSet hashSet = aVar5.f23976a;
                            if (hashSet.isEmpty()) {
                                Iterator<SessionConfig> it = c20039w.f23466b.b().iterator();
                                while (it.hasNext()) {
                                    List listUnmodifiableList = Collections.unmodifiableList(it.next().f24017f.f23968a);
                                    if (!listUnmodifiableList.isEmpty()) {
                                        Iterator it2 = listUnmodifiableList.iterator();
                                        while (it2.hasNext()) {
                                            aVar5.d((DeferrableSurface) it2.next());
                                        }
                                    }
                                }
                                if (hashSet.isEmpty()) {
                                    C20140q0.d(5, "Camera2CameraImpl");
                                }
                            } else {
                                C20140q0.d(5, "Camera2CameraImpl");
                            }
                        }
                        arrayList.add(aVar5.e());
                    }
                    c20039w.s("Issue capture request");
                    c20039w.f23478n.b(arrayList);
                } else {
                    aVar.d(new CameraControl.OperationCanceledException("Camera is not active."));
                }
                aVar2.f22943b.f23262b = true;
                return "AePreCapture";
            case 1:
            default:
                C20026p c20026p2 = (C20026p) this.f22911c;
                c20026p2.getClass();
                c20026p2.f23389c.execute(new RunnableC20016k(4, c20026p2, aVar));
                return "updateSessionConfigAsync";
            case 2:
                int i12 = D.c.f22950k;
                ((H.a) this.f22911c).b(new H(aVar));
                return "submitStillCapture";
            case 3:
                ((D.e) this.f22911c).f22960a = aVar;
                return "waitFor3AResult";
        }
    }

    public /* synthetic */ C(Object obj, int i12) {
        this.f22910b = i12;
        this.f22911c = obj;
    }
}
