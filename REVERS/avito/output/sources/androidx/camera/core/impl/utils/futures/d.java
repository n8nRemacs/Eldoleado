package androidx.camera.core.impl.utils.futures;

import androidx.concurrent.futures.b;
import androidx.core.util.z;
import com.google.common.util.concurrent.D0;
import j.N;
import j.P;
import j.X;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: FutureChain.java */
@X
/* loaded from: classes.dex */
public class d<V> implements D0<V> {

    /* renamed from: b, reason: collision with root package name */
    @N
    public final D0<V> f24250b;

    /* renamed from: c, reason: collision with root package name */
    @P
    public b.a<V> f24251c;

    /* compiled from: FutureChain.java */
    public class a implements b.c<V> {
        public a() {
        }

        @Override // androidx.concurrent.futures.b.c
        public final Object d(@N b.a<V> aVar) {
            d dVar = d.this;
            z.g("The result can only set once!", dVar.f24251c == null);
            dVar.f24251c = aVar;
            return "FutureChain[" + dVar + "]";
        }
    }

    public d(@N D0<V> d02) {
        d02.getClass();
        this.f24250b = d02;
    }

    @N
    public static <V> d<V> a(@N D0<V> d02) {
        return d02 instanceof d ? (d) d02 : new d<>(d02);
    }

    @Override // com.google.common.util.concurrent.D0
    public final void N(@N Runnable runnable, @N Executor executor) {
        this.f24250b.N(runnable, executor);
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z12) {
        return this.f24250b.cancel(z12);
    }

    @Override // java.util.concurrent.Future
    @P
    public V get() {
        return this.f24250b.get();
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.f24250b.isCancelled();
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return this.f24250b.isDone();
    }

    @Override // java.util.concurrent.Future
    @P
    public V get(long j12, @N TimeUnit timeUnit) {
        return this.f24250b.get(j12, timeUnit);
    }

    public d() {
        this.f24250b = androidx.concurrent.futures.b.a(new a());
    }
}
