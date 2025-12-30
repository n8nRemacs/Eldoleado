package androidx.camera.core.impl;

import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.q0;
import androidx.view.C23038g0;
import androidx.view.InterfaceC23040h0;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: LiveDataObservable.java */
@j.X
/* renamed from: androidx.camera.core.impl.k0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20102k0<T> implements q0<T> {

    /* renamed from: a, reason: collision with root package name */
    public final C23038g0<b<T>> f24145a = new C23038g0<>();

    /* renamed from: b, reason: collision with root package name */
    @j.B
    public final HashMap f24146b = new HashMap();

    /* compiled from: LiveDataObservable.java */
    /* renamed from: androidx.camera.core.impl.k0$a */
    public static final class a<T> implements InterfaceC23040h0<b<T>> {

        /* renamed from: b, reason: collision with root package name */
        public final AtomicBoolean f24147b = new AtomicBoolean(true);

        /* renamed from: c, reason: collision with root package name */
        public final q0.a<? super T> f24148c;

        /* renamed from: d, reason: collision with root package name */
        public final Executor f24149d;

        public a(@j.N Executor executor, @j.N q0.a<? super T> aVar) {
            this.f24149d = executor;
            this.f24148c = aVar;
        }

        @Override // androidx.view.InterfaceC23040h0
        public final void onChanged(@j.N Object obj) {
            this.f24149d.execute(new RunnableC20098i0(this, (b) obj));
        }
    }

    /* compiled from: LiveDataObservable.java */
    /* renamed from: androidx.camera.core.impl.k0$b */
    public static final class b<T> {

        /* renamed from: a, reason: collision with root package name */
        @j.P
        public final CameraInternal.State f24150a;

        public b(@j.P CameraInternal.State state) {
            this.f24150a = state;
        }

        @j.N
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("[Result: <");
            sb2.append("Value: " + this.f24150a);
            sb2.append(">]");
            return sb2.toString();
        }
    }

    @Override // androidx.camera.core.impl.q0
    public final void b(@j.N q0.a<? super T> aVar) {
        synchronized (this.f24146b) {
            try {
                a aVar2 = (a) this.f24146b.remove(aVar);
                if (aVar2 != null) {
                    aVar2.f24147b.set(false);
                    androidx.camera.core.impl.utils.executor.c.d().execute(new RunnableC20098i0(0, this, aVar2));
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.impl.q0
    @j.N
    public final com.google.common.util.concurrent.D0<T> c() {
        return androidx.concurrent.futures.b.a(new Ae0.c(this, 8));
    }

    @Override // androidx.camera.core.impl.q0
    public final void d(@j.N Executor executor, @j.N q0.a<? super T> aVar) {
        synchronized (this.f24146b) {
            a aVar2 = (a) this.f24146b.get(aVar);
            if (aVar2 != null) {
                aVar2.f24147b.set(false);
            }
            a aVar3 = new a(executor, aVar);
            this.f24146b.put(aVar, aVar3);
            androidx.camera.core.impl.utils.executor.c.d().execute(new RunnableC20100j0(this, aVar2, aVar3, 0));
        }
    }
}
