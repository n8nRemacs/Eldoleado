package androidx.camera.view;

import android.util.Size;
import androidx.camera.core.C20140q0;
import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.K0;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.view.PreviewView;
import androidx.camera.view.u;
import androidx.concurrent.futures.b;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class n implements b.c, K0.e, u.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f25446b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f25447c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f25448d;

    public /* synthetic */ n(PreviewView.a aVar, q qVar, CameraInternal cameraInternal) {
        this.f25447c = aVar;
        this.f25446b = qVar;
        this.f25448d = cameraInternal;
    }

    @Override // androidx.camera.core.K0.e
    public void a(K0.d dVar) {
        u uVar;
        PreviewView.a aVar = (PreviewView.a) this.f25446b;
        aVar.getClass();
        Objects.toString(dVar);
        C20140q0.d(3, "PreviewView");
        boolean z12 = ((CameraInternal) this.f25447c).c().b() == 0;
        PreviewView previewView = PreviewView.this;
        Size size = ((K0) this.f25448d).f23590b;
        r rVar = previewView.f25382d;
        rVar.getClass();
        Objects.toString(dVar);
        Objects.toString(size);
        C20140q0.d(3, "PreviewTransform");
        rVar.f25461b = dVar.a();
        rVar.f25462c = dVar.c();
        rVar.f25464e = dVar.e();
        rVar.f25460a = size;
        rVar.f25465f = z12;
        rVar.f25466g = dVar.f();
        rVar.f25463d = dVar.d();
        if (dVar.e() == -1 || ((uVar = previewView.f25381c) != null && (uVar instanceof E))) {
            previewView.f25383e = true;
        } else {
            previewView.f25383e = false;
        }
        previewView.b();
    }

    public void b() {
        q qVar;
        AtomicReference<q> atomicReference = PreviewView.this.f25385g;
        while (true) {
            qVar = (q) this.f25446b;
            if (atomicReference.compareAndSet(qVar, null)) {
                qVar.b(PreviewView.StreamState.f25404b);
                break;
            } else if (atomicReference.get() != qVar) {
                break;
            }
        }
        androidx.camera.core.impl.utils.futures.d dVar = qVar.f25458e;
        if (dVar != null) {
            dVar.cancel(false);
            qVar.f25458e = null;
        }
        ((CameraInternal) this.f25448d).f().b(qVar);
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        ((q) this.f25446b).getClass();
        InterfaceC20142t interfaceC20142t = (InterfaceC20142t) this.f25447c;
        p pVar = new p(aVar, interfaceC20142t);
        ((ArrayList) this.f25448d).add(pVar);
        ((androidx.camera.core.impl.B) interfaceC20142t).l(androidx.camera.core.impl.utils.executor.c.a(), pVar);
        return "waitForCaptureResult";
    }

    public /* synthetic */ n(Object obj, Object obj2, Object obj3) {
        this.f25446b = obj;
        this.f25447c = obj2;
        this.f25448d = obj3;
    }
}
