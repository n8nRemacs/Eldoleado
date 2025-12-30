package androidx.camera.core.imagecapture;

import androidx.camera.core.C20140q0;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.O;
import androidx.camera.core.imagecapture.O;
import androidx.camera.core.impl.C20094g0;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.H;
import androidx.camera.core.impl.Z;
import androidx.camera.core.y0;
import com.google.common.util.concurrent.D0;
import j.X;
import j.k0;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: TakePictureManager.java */
@X
/* loaded from: classes.dex */
public class N implements O.a, O.a {

    /* renamed from: b, reason: collision with root package name */
    public final q f23802b;

    /* renamed from: c, reason: collision with root package name */
    public r f23803c;

    /* renamed from: d, reason: collision with root package name */
    @j.P
    public D f23804d;

    /* renamed from: e, reason: collision with root package name */
    public final ArrayList f23805e;

    /* renamed from: a, reason: collision with root package name */
    @k0
    public final ArrayDeque f23801a = new ArrayDeque();

    /* renamed from: f, reason: collision with root package name */
    public boolean f23806f = false;

    @j.K
    public N(@j.N q qVar) {
        androidx.camera.core.impl.utils.t.a();
        this.f23802b = qVar;
        this.f23805e = new ArrayList();
    }

    @j.K
    public final void a() {
        androidx.camera.core.impl.utils.t.a();
        ImageCaptureException imageCaptureException = new ImageCaptureException("Camera is closed.", null);
        ArrayDeque arrayDeque = this.f23801a;
        Iterator it = arrayDeque.iterator();
        while (it.hasNext()) {
            O o12 = (O) it.next();
            o12.a().execute(new z(5, o12, imageCaptureException));
        }
        arrayDeque.clear();
        Iterator it2 = new ArrayList(this.f23805e).iterator();
        while (it2.hasNext()) {
            D d12 = (D) it2.next();
            d12.getClass();
            androidx.camera.core.impl.utils.t.a();
            if (!d12.f23776d.isDone()) {
                androidx.camera.core.impl.utils.t.a();
                d12.f23779g = true;
                D0<Void> d02 = d12.f23780h;
                Objects.requireNonNull(d02);
                d02.cancel(true);
                d12.f23777e.d(imageCaptureException);
                d12.f23778f.b(null);
                androidx.camera.core.impl.utils.t.a();
                O o13 = d12.f23773a;
                o13.a().execute(new z(5, o13, imageCaptureException));
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @j.K
    public final void b() {
        O o12;
        boolean z12 = false;
        androidx.camera.core.impl.utils.t.a();
        if (this.f23804d == null && !this.f23806f) {
            r rVar = this.f23803c;
            rVar.getClass();
            androidx.camera.core.impl.utils.t.a();
            if (rVar.f23858c.a() == 0 || (o12 = (O) this.f23801a.poll()) == null) {
                return;
            }
            D d12 = new D(o12, this);
            androidx.core.util.z.g(null, !(this.f23804d != null));
            this.f23804d = d12;
            androidx.camera.core.impl.utils.t.a();
            D0<Void> d02 = d12.f23775c;
            d02.N(new L(this, null == true ? 1 : 0), androidx.camera.core.impl.utils.executor.c.a());
            this.f23805e.add(d12);
            androidx.camera.core.impl.utils.t.a();
            d12.f23776d.N(new z(4, this, d12), androidx.camera.core.impl.utils.executor.c.a());
            r rVar2 = this.f23803c;
            androidx.camera.core.impl.utils.t.a();
            rVar2.getClass();
            androidx.camera.core.impl.utils.t.a();
            androidx.camera.core.impl.G g12 = (androidx.camera.core.impl.G) rVar2.f23856a.g(Z.f24072I, androidx.camera.core.C.a());
            Objects.requireNonNull(g12);
            ArrayList arrayList = new ArrayList();
            String strValueOf = String.valueOf(g12.hashCode());
            List<androidx.camera.core.impl.I> listA = g12.a();
            Objects.requireNonNull(listA);
            for (androidx.camera.core.impl.I i12 : listA) {
                H.a aVar = new H.a();
                androidx.camera.core.impl.H h12 = rVar2.f23857b;
                aVar.f23978c = h12.f23970c;
                aVar.c(h12.f23969b);
                aVar.a(o12.j());
                C20066b c20066b = rVar2.f23861f;
                C20094g0 c20094g0 = c20066b.f23853b;
                Objects.requireNonNull(c20094g0);
                aVar.d(c20094g0);
                if (c20066b.f23811d == 256) {
                    r.f23855g.getClass();
                    if (((androidx.camera.core.internal.compat.quirk.d) androidx.camera.core.internal.compat.quirk.b.f24337a.b(androidx.camera.core.internal.compat.quirk.d.class)) != null) {
                        Config.a<Integer> aVar2 = androidx.camera.core.impl.H.f23965i;
                    } else {
                        aVar.f23977b.K(androidx.camera.core.impl.H.f23965i, Integer.valueOf(o12.h()));
                    }
                    aVar.f23977b.K(androidx.camera.core.impl.H.f23966j, Integer.valueOf(((o12.f() != null ? true : z12) && androidx.camera.core.impl.utils.u.b(o12.c(), c20066b.f23810c)) ? o12.b() == 0 ? 100 : 95 : o12.e()));
                }
                aVar.c(i12.a().f23969b);
                z12 = false;
                aVar.f23982g.f23987a.put(strValueOf, 0);
                aVar.b(c20066b.f23852a);
                arrayList.add(aVar.e());
            }
            C20076l c20076l = new C20076l(arrayList, d12);
            B b12 = new B(g12, o12.g(), o12.c(), o12.h(), o12.e(), o12.i(), d12, d02);
            r rVar3 = this.f23803c;
            rVar3.getClass();
            androidx.camera.core.impl.utils.t.a();
            rVar3.f23861f.f23815h.accept(b12);
            androidx.camera.core.impl.utils.t.a();
            q qVar = this.f23802b;
            qVar.a();
            D0<Void> d0C = qVar.c(arrayList);
            androidx.camera.core.impl.utils.futures.f.a(d0C, new M(this, c20076l), androidx.camera.core.impl.utils.executor.c.d());
            androidx.camera.core.impl.utils.t.a();
            if (d12.f23780h == null) {
                z12 = true;
            }
            androidx.core.util.z.g("CaptureRequestFuture can only be set once.", z12);
            d12.f23780h = d0C;
        }
    }

    @j.K
    public final void c() {
        androidx.camera.core.impl.utils.t.a();
        this.f23806f = true;
        D d12 = this.f23804d;
        if (d12 != null) {
            androidx.camera.core.impl.utils.t.a();
            if (d12.f23776d.isDone()) {
                return;
            }
            ImageCaptureException imageCaptureException = new ImageCaptureException("The request is aborted silently and retried.", null);
            androidx.camera.core.impl.utils.t.a();
            d12.f23779g = true;
            D0<Void> d02 = d12.f23780h;
            Objects.requireNonNull(d02);
            d02.cancel(true);
            d12.f23777e.d(imageCaptureException);
            d12.f23778f.b(null);
            d12.f23774b.d(d12.f23773a);
        }
    }

    @j.K
    public final void d(@j.N O o12) {
        androidx.camera.core.impl.utils.t.a();
        C20140q0.d(3, "TakePictureManager");
        this.f23801a.addFirst(o12);
        b();
    }

    @Override // androidx.camera.core.O.a
    public final void e(@j.N androidx.camera.core.O o12) {
        androidx.camera.core.impl.utils.executor.c.d().execute(new L(this, 1));
    }

    @j.K
    public final void f(@j.N r rVar) {
        androidx.camera.core.impl.utils.t.a();
        this.f23803c = rVar;
        rVar.getClass();
        androidx.camera.core.impl.utils.t.a();
        C20079o c20079o = rVar.f23858c;
        c20079o.getClass();
        androidx.camera.core.impl.utils.t.a();
        androidx.core.util.z.g("The ImageReader is not initialized.", c20079o.f23847c != null);
        y0 y0Var = c20079o.f23847c;
        synchronized (y0Var.f24589a) {
            y0Var.f24594f = this;
        }
    }
}
