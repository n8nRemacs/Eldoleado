package androidx.camera.core;

import androidx.camera.core.impl.InterfaceC20092f0;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/* compiled from: ImageAnalysisNonBlockingAnalyzer.java */
@j.X
/* loaded from: classes.dex */
final class Y extends W {

    /* renamed from: u, reason: collision with root package name */
    public final Executor f23695u;

    /* renamed from: v, reason: collision with root package name */
    public final Object f23696v = new Object();

    /* renamed from: w, reason: collision with root package name */
    @j.B
    @j.k0
    @j.P
    public InterfaceC20120j0 f23697w;

    /* renamed from: x, reason: collision with root package name */
    @j.B
    @j.P
    public b f23698x;

    /* compiled from: ImageAnalysisNonBlockingAnalyzer.java */
    public class a implements androidx.camera.core.impl.utils.futures.c<Void> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ b f23699a;

        public a(b bVar) {
            this.f23699a = bVar;
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@j.N Throwable th2) {
            this.f23699a.close();
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final /* bridge */ /* synthetic */ void onSuccess(Void r12) {
        }
    }

    /* compiled from: ImageAnalysisNonBlockingAnalyzer.java */
    public static class b extends O {

        /* renamed from: e, reason: collision with root package name */
        public final WeakReference<Y> f23700e;

        public b(@j.N InterfaceC20120j0 interfaceC20120j0, @j.N Y y12) {
            super(interfaceC20120j0);
            this.f23700e = new WeakReference<>(y12);
            a(new Z(this, 0));
        }
    }

    public Y(Executor executor) {
        this.f23695u = executor;
    }

    @Override // androidx.camera.core.W
    @j.P
    public final InterfaceC20120j0 b(@j.N InterfaceC20092f0 interfaceC20092f0) {
        return interfaceC20092f0.b();
    }

    @Override // androidx.camera.core.W
    public final void d() {
        synchronized (this.f23696v) {
            try {
                InterfaceC20120j0 interfaceC20120j0 = this.f23697w;
                if (interfaceC20120j0 != null) {
                    interfaceC20120j0.close();
                    this.f23697w = null;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.W
    public final void f(@j.N InterfaceC20120j0 interfaceC20120j0) {
        synchronized (this.f23696v) {
            try {
                if (!this.f23693t) {
                    interfaceC20120j0.close();
                    return;
                }
                if (this.f23698x == null) {
                    b bVar = new b(interfaceC20120j0, this);
                    this.f23698x = bVar;
                    androidx.camera.core.impl.utils.futures.f.a(c(bVar), new a(bVar), androidx.camera.core.impl.utils.executor.c.a());
                } else {
                    if (interfaceC20120j0.r1().a() <= this.f23698x.f23633c.r1().a()) {
                        interfaceC20120j0.close();
                    } else {
                        InterfaceC20120j0 interfaceC20120j02 = this.f23697w;
                        if (interfaceC20120j02 != null) {
                            interfaceC20120j02.close();
                        }
                        this.f23697w = interfaceC20120j0;
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
