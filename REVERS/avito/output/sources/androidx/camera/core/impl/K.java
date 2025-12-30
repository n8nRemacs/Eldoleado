package androidx.camera.core.impl;

import androidx.camera.core.impl.q0;
import java.util.concurrent.Executor;

/* compiled from: ConstantObservable.java */
@j.X
/* loaded from: classes.dex */
public final class K<T> implements q0<T> {

    /* renamed from: b, reason: collision with root package name */
    public static final K<Object> f23988b = new K<>(null);

    /* renamed from: a, reason: collision with root package name */
    public final com.google.common.util.concurrent.D0<T> f23989a;

    public K(@j.P T t12) {
        this.f23989a = androidx.camera.core.impl.utils.futures.f.g(t12);
    }

    @Override // androidx.camera.core.impl.q0
    @j.N
    public final com.google.common.util.concurrent.D0<T> c() {
        return this.f23989a;
    }

    @Override // androidx.camera.core.impl.q0
    public final void d(@j.N Executor executor, @j.N q0.a<? super T> aVar) {
        this.f23989a.N(new RunnableC20098i0(2, this, aVar), executor);
    }

    @Override // androidx.camera.core.impl.q0
    public final void b(@j.N q0.a<? super T> aVar) {
    }
}
