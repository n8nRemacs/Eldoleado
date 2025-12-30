package androidx.camera.core.processing;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.C0;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.camera.core.ProcessingException;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.G0;
import androidx.camera.core.processing.z;
import com.google.common.util.concurrent.D0;
import j.K;
import j.N;
import j.P;
import j.X;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* compiled from: SurfaceProcessorNode.java */
@X
/* loaded from: classes.dex */
public class C implements o<b, c> {

    /* renamed from: a, reason: collision with root package name */
    @N
    public final k f24404a;

    /* renamed from: b, reason: collision with root package name */
    @N
    public final CameraInternal f24405b;

    /* renamed from: c, reason: collision with root package name */
    @P
    public c f24406c;

    /* compiled from: SurfaceProcessorNode.java */
    public class a implements androidx.camera.core.impl.utils.futures.c<C0> {
        public a() {
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@N Throwable th2) {
            C20140q0.d(5, "SurfaceProcessorNode");
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void onSuccess(@P C0 c02) throws IOException {
            C0 c03 = c02;
            c03.getClass();
            try {
                k kVar = C.this.f24404a;
                if (kVar.f24447f.get()) {
                    c03.close();
                } else {
                    kVar.b(new RunnableC20138f(0, kVar, c03), new x(c03, 2));
                }
            } catch (ProcessingException unused) {
                C20140q0.c("SurfaceProcessorNode");
            }
        }
    }

    /* compiled from: SurfaceProcessorNode.java */
    @VY0.c
    public static abstract class b {
        @N
        public static b c(@N z zVar, @N List<d> list) {
            return new C20136d(zVar, list);
        }

        @N
        public abstract List<d> a();

        @N
        public abstract z b();
    }

    /* compiled from: SurfaceProcessorNode.java */
    public static class c extends HashMap<d, z> {
    }

    /* compiled from: SurfaceProcessorNode.java */
    @VY0.c
    public static abstract class d {
        @N
        public static d h(int i12, int i13, @N Rect rect, @N Size size, int i14, boolean z12) {
            return new C20137e(UUID.randomUUID(), i12, i13, rect, size, i14, z12);
        }

        @N
        public abstract Rect a();

        public abstract int b();

        public abstract boolean c();

        public abstract int d();

        @N
        public abstract Size e();

        public abstract int f();

        @N
        public abstract UUID g();
    }

    public C(@N CameraInternal cameraInternal, @N k kVar) {
        this.f24405b = cameraInternal;
        this.f24404a = kVar;
    }

    public final void a(@N z zVar, Map.Entry<d, z> entry) {
        final z value = entry.getValue();
        final Size sizeE = zVar.f24501g.e();
        final int iB2 = entry.getKey().b();
        final Rect rectA = entry.getKey().a();
        final int iD2 = entry.getKey().d();
        final boolean zC2 = entry.getKey().c();
        final CameraInternal cameraInternal = zVar.f24497c ? this.f24405b : null;
        value.getClass();
        androidx.camera.core.impl.utils.t.a();
        value.b();
        androidx.core.util.z.g("Consumer can only be linked once.", !value.f24505k);
        value.f24505k = true;
        final z.a aVar = value.f24507m;
        androidx.camera.core.impl.utils.futures.f.a(androidx.camera.core.impl.utils.futures.f.m(aVar.c(), new androidx.camera.core.impl.utils.futures.a() { // from class: androidx.camera.core.processing.w
            @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
            public final D0 apply(Object obj) {
                z.a aVar2 = aVar;
                Surface surface = (Surface) obj;
                z zVar2 = value;
                surface.getClass();
                try {
                    aVar2.d();
                    A a12 = new A(surface, iB2, zVar2.f24501g.e(), sizeE, rectA, iD2, zC2, cameraInternal);
                    a12.f24401k.N(new x(aVar2, 0), androidx.camera.core.impl.utils.executor.c.a());
                    zVar2.f24504j = a12;
                    return androidx.camera.core.impl.utils.futures.f.g(a12);
                } catch (DeferrableSurface.SurfaceClosedException e12) {
                    return androidx.camera.core.impl.utils.futures.f.e(e12);
                }
            }
        }, androidx.camera.core.impl.utils.executor.c.d()), new a(), androidx.camera.core.impl.utils.executor.c.d());
    }

    public final void b() {
        this.f24404a.e();
        androidx.camera.core.impl.utils.executor.c.d().execute(new x(this, 5));
    }

    @N
    @K
    public final c c(@N b bVar) {
        z zVar;
        androidx.camera.core.impl.utils.t.a();
        this.f24406c = new c();
        C20136d c20136d = (C20136d) bVar;
        Iterator<d> it = c20136d.f24423b.iterator();
        while (true) {
            boolean zHasNext = it.hasNext();
            zVar = c20136d.f24422a;
            if (!zHasNext) {
                break;
            }
            d next = it.next();
            c cVar = this.f24406c;
            Rect rectA = next.a();
            int iD2 = next.d();
            boolean zC2 = next.c();
            Matrix matrix = new Matrix(zVar.f24496b);
            RectF rectF = new RectF(rectA);
            Size sizeE = next.e();
            RectF rectF2 = androidx.camera.core.impl.utils.u.f24303a;
            float f12 = 0;
            matrix.postConcat(androidx.camera.core.impl.utils.u.a(rectF, new RectF(f12, f12, sizeE.getWidth(), sizeE.getHeight()), iD2, zC2));
            androidx.core.util.z.b(androidx.camera.core.impl.utils.u.d(androidx.camera.core.impl.utils.u.f(new Size(rectA.width(), rectA.height()), iD2), false, next.e()));
            G0.a aVarF = zVar.f24501g.f();
            aVarF.e(next.e());
            G0 g0A = aVarF.a();
            int iF2 = next.f();
            int iB2 = next.b();
            Size sizeE2 = next.e();
            C20136d c20136d2 = c20136d;
            cVar.put(next, new z(iF2, iB2, g0A, matrix, false, new Rect(0, 0, sizeE2.getWidth(), sizeE2.getHeight()), zVar.f24503i - iD2, -1, zVar.f24499e != zC2));
            c20136d = c20136d2;
        }
        c cVar2 = this.f24406c;
        K0 k0C = zVar.c(this.f24405b);
        k0C.b(androidx.camera.core.impl.utils.executor.c.d(), new y(cVar2, 2));
        try {
            k kVar = this.f24404a;
            if (kVar.f24447f.get()) {
                k0C.c();
            } else {
                int i12 = 3;
                kVar.b(new RunnableC20138f(i12, kVar, k0C), new x(k0C, i12));
            }
        } catch (ProcessingException unused) {
            C20140q0.c("SurfaceProcessorNode");
        }
        for (Map.Entry<d, z> entry : this.f24406c.entrySet()) {
            a(zVar, entry);
            entry.getValue().a(new Me.m(this, zVar, entry, 3));
        }
        return this.f24406c;
    }
}
