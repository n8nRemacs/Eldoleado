package androidx.camera.core;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.concurrent.futures.b;
import androidx.core.util.InterfaceC22791e;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: SurfaceRequest.java */
@j.X
/* loaded from: classes.dex */
public final class K0 {

    /* renamed from: o, reason: collision with root package name */
    @RestrictTo
    public static final Range<Integer> f23588o = androidx.camera.core.impl.G0.f23964a;

    /* renamed from: a, reason: collision with root package name */
    public final Object f23589a = new Object();

    /* renamed from: b, reason: collision with root package name */
    public final Size f23590b;

    /* renamed from: c, reason: collision with root package name */
    @j.N
    public final F f23591c;

    /* renamed from: d, reason: collision with root package name */
    public final Range<Integer> f23592d;

    /* renamed from: e, reason: collision with root package name */
    public final CameraInternal f23593e;

    /* renamed from: f, reason: collision with root package name */
    public final com.google.common.util.concurrent.D0<Surface> f23594f;

    /* renamed from: g, reason: collision with root package name */
    public final b.a<Surface> f23595g;

    /* renamed from: h, reason: collision with root package name */
    public final com.google.common.util.concurrent.D0<Void> f23596h;

    /* renamed from: i, reason: collision with root package name */
    @j.N
    public final b.a<Void> f23597i;

    /* renamed from: j, reason: collision with root package name */
    public final b.a<Void> f23598j;

    /* renamed from: k, reason: collision with root package name */
    public final DeferrableSurface f23599k;

    /* renamed from: l, reason: collision with root package name */
    @j.B
    @j.P
    public d f23600l;

    /* renamed from: m, reason: collision with root package name */
    @j.B
    @j.P
    public e f23601m;

    /* renamed from: n, reason: collision with root package name */
    @j.B
    @j.P
    public Executor f23602n;

    /* compiled from: SurfaceRequest.java */
    public class a implements androidx.camera.core.impl.utils.futures.c<Void> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ InterfaceC22791e f23603a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Surface f23604b;

        public a(InterfaceC22791e interfaceC22791e, Surface surface) {
            this.f23603a = interfaceC22791e;
            this.f23604b = surface;
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@j.N Throwable th2) {
            androidx.core.util.z.g("Camera surface session should only fail with request cancellation. Instead failed due to:\n" + th2, th2 instanceof b);
            this.f23603a.accept(new C20123l(1, this.f23604b));
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void onSuccess(@j.P Void r32) {
            this.f23603a.accept(new C20123l(0, this.f23604b));
        }
    }

    /* compiled from: SurfaceRequest.java */
    public static final class b extends RuntimeException {
    }

    /* compiled from: SurfaceRequest.java */
    @VY0.c
    public static abstract class c {

        /* compiled from: SurfaceRequest.java */
        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface a {
        }

        public abstract int a();

        @j.N
        public abstract Surface b();
    }

    /* compiled from: SurfaceRequest.java */
    @VY0.c
    public static abstract class d {
        @j.N
        @RestrictTo
        public static d g(@j.N Rect rect, int i12, int i13, boolean z12, @j.N Matrix matrix, boolean z13) {
            return new C20125m(rect, i12, i13, z12, matrix, z13);
        }

        @j.N
        public abstract Rect a();

        @RestrictTo
        public abstract boolean b();

        public abstract int c();

        @j.N
        @RestrictTo
        public abstract Matrix d();

        @RestrictTo
        public abstract int e();

        @RestrictTo
        public abstract boolean f();
    }

    /* compiled from: SurfaceRequest.java */
    public interface e {
        void a(@j.N d dVar);
    }

    @RestrictTo
    public K0(@j.N Size size, @j.N CameraInternal cameraInternal, @j.N F f12, @j.N Range range, @j.N androidx.camera.core.processing.t tVar) {
        this.f23590b = size;
        this.f23593e = cameraInternal;
        this.f23591c = f12;
        this.f23592d = range;
        final String str = "SurfaceRequest[size: " + size + ", id: " + hashCode() + "]";
        final AtomicReference atomicReference = new AtomicReference(null);
        final int i12 = 0;
        com.google.common.util.concurrent.D0 d0A = androidx.concurrent.futures.b.a(new b.c() { // from class: androidx.camera.core.G0
            @Override // androidx.concurrent.futures.b.c
            public final Object d(b.a aVar) {
                String str2 = str;
                AtomicReference atomicReference2 = atomicReference;
                switch (i12) {
                    case 0:
                        Range<Integer> range2 = K0.f23588o;
                        atomicReference2.set(aVar);
                        return AK.c.s(new StringBuilder(), str2, "-cancellation");
                    case 1:
                        Range<Integer> range3 = K0.f23588o;
                        atomicReference2.set(aVar);
                        return AK.c.s(new StringBuilder(), str2, "-status");
                    default:
                        Range<Integer> range4 = K0.f23588o;
                        atomicReference2.set(aVar);
                        return AK.c.s(new StringBuilder(), str2, "-Surface");
                }
            }
        });
        b.a<Void> aVar = (b.a) atomicReference.get();
        aVar.getClass();
        this.f23598j = aVar;
        final AtomicReference atomicReference2 = new AtomicReference(null);
        final int i13 = 1;
        com.google.common.util.concurrent.D0<Void> d0A2 = androidx.concurrent.futures.b.a(new b.c() { // from class: androidx.camera.core.G0
            @Override // androidx.concurrent.futures.b.c
            public final Object d(b.a aVar2) {
                String str2 = str;
                AtomicReference atomicReference22 = atomicReference2;
                switch (i13) {
                    case 0:
                        Range<Integer> range2 = K0.f23588o;
                        atomicReference22.set(aVar2);
                        return AK.c.s(new StringBuilder(), str2, "-cancellation");
                    case 1:
                        Range<Integer> range3 = K0.f23588o;
                        atomicReference22.set(aVar2);
                        return AK.c.s(new StringBuilder(), str2, "-status");
                    default:
                        Range<Integer> range4 = K0.f23588o;
                        atomicReference22.set(aVar2);
                        return AK.c.s(new StringBuilder(), str2, "-Surface");
                }
            }
        });
        this.f23596h = d0A2;
        androidx.camera.core.impl.utils.futures.f.a(d0A2, new H0(aVar, d0A), androidx.camera.core.impl.utils.executor.c.a());
        b.a aVar2 = (b.a) atomicReference2.get();
        aVar2.getClass();
        final AtomicReference atomicReference3 = new AtomicReference(null);
        final int i14 = 2;
        com.google.common.util.concurrent.D0<Surface> d0A3 = androidx.concurrent.futures.b.a(new b.c() { // from class: androidx.camera.core.G0
            @Override // androidx.concurrent.futures.b.c
            public final Object d(b.a aVar22) {
                String str2 = str;
                AtomicReference atomicReference22 = atomicReference3;
                switch (i14) {
                    case 0:
                        Range<Integer> range2 = K0.f23588o;
                        atomicReference22.set(aVar22);
                        return AK.c.s(new StringBuilder(), str2, "-cancellation");
                    case 1:
                        Range<Integer> range3 = K0.f23588o;
                        atomicReference22.set(aVar22);
                        return AK.c.s(new StringBuilder(), str2, "-status");
                    default:
                        Range<Integer> range4 = K0.f23588o;
                        atomicReference22.set(aVar22);
                        return AK.c.s(new StringBuilder(), str2, "-Surface");
                }
            }
        });
        this.f23594f = d0A3;
        b.a<Surface> aVar3 = (b.a) atomicReference3.get();
        aVar3.getClass();
        this.f23595g = aVar3;
        I0 i02 = new I0(this, size);
        this.f23599k = i02;
        com.google.common.util.concurrent.D0 d0H = androidx.camera.core.impl.utils.futures.f.h(i02.f23933e);
        androidx.camera.core.impl.utils.futures.f.a(d0A3, new J0(d0H, aVar2, str), androidx.camera.core.impl.utils.executor.c.a());
        d0H.N(new RunnableC20048a0(this, 2), androidx.camera.core.impl.utils.executor.c.a());
        Executor executorA = androidx.camera.core.impl.utils.executor.c.a();
        AtomicReference atomicReference4 = new AtomicReference(null);
        androidx.camera.core.impl.utils.futures.f.a(androidx.concurrent.futures.b.a(new C20145w(2, this, atomicReference4)), new L0(tVar), executorA);
        b.a<Void> aVar4 = (b.a) atomicReference4.get();
        aVar4.getClass();
        this.f23597i = aVar4;
    }

    public final void a(@j.N final Surface surface, @j.N Executor executor, @j.N final InterfaceC22791e<c> interfaceC22791e) {
        if (!this.f23595g.b(surface)) {
            com.google.common.util.concurrent.D0<Surface> d02 = this.f23594f;
            if (!d02.isCancelled()) {
                androidx.core.util.z.g(null, d02.isDone());
                try {
                    d02.get();
                    final int i12 = 0;
                    executor.execute(new Runnable() { // from class: androidx.camera.core.F0
                        @Override // java.lang.Runnable
                        public final void run() {
                            InterfaceC22791e interfaceC22791e2 = interfaceC22791e;
                            Surface surface2 = surface;
                            switch (i12) {
                                case 0:
                                    Range<Integer> range = K0.f23588o;
                                    interfaceC22791e2.accept(new C20123l(3, surface2));
                                    break;
                                default:
                                    Range<Integer> range2 = K0.f23588o;
                                    interfaceC22791e2.accept(new C20123l(4, surface2));
                                    break;
                            }
                        }
                    });
                    return;
                } catch (InterruptedException | ExecutionException unused) {
                    final int i13 = 1;
                    executor.execute(new Runnable() { // from class: androidx.camera.core.F0
                        @Override // java.lang.Runnable
                        public final void run() {
                            InterfaceC22791e interfaceC22791e2 = interfaceC22791e;
                            Surface surface2 = surface;
                            switch (i13) {
                                case 0:
                                    Range<Integer> range = K0.f23588o;
                                    interfaceC22791e2.accept(new C20123l(3, surface2));
                                    break;
                                default:
                                    Range<Integer> range2 = K0.f23588o;
                                    interfaceC22791e2.accept(new C20123l(4, surface2));
                                    break;
                            }
                        }
                    });
                    return;
                }
            }
        }
        androidx.camera.core.impl.utils.futures.f.a(this.f23596h, new a(interfaceC22791e, surface), executor);
    }

    public final void b(@j.N Executor executor, @j.N e eVar) {
        d dVar;
        synchronized (this.f23589a) {
            this.f23601m = eVar;
            this.f23602n = executor;
            dVar = this.f23600l;
        }
        if (dVar != null) {
            executor.execute(new E0(eVar, dVar, 1));
        }
    }

    public final void c() {
        this.f23595g.d(new DeferrableSurface.SurfaceUnavailableException("Surface request will not complete."));
    }
}
