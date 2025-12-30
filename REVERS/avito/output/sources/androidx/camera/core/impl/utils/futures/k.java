package androidx.camera.core.impl.utils.futures;

import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: ListFuture.java */
@X
/* loaded from: classes.dex */
class k<V> implements D0<List<V>> {

    /* renamed from: b, reason: collision with root package name */
    @P
    public ArrayList f24269b;

    /* renamed from: c, reason: collision with root package name */
    @P
    public ArrayList f24270c;

    /* renamed from: d, reason: collision with root package name */
    public final boolean f24271d;

    /* renamed from: e, reason: collision with root package name */
    @N
    public final AtomicInteger f24272e;

    /* renamed from: f, reason: collision with root package name */
    @N
    public final D0<List<V>> f24273f = androidx.concurrent.futures.b.a(new h(this));

    /* renamed from: g, reason: collision with root package name */
    public b.a<List<V>> f24274g;

    public k(@N ArrayList arrayList, boolean z12, @N Executor executor) {
        this.f24269b = arrayList;
        this.f24270c = new ArrayList(arrayList.size());
        this.f24271d = z12;
        this.f24272e = new AtomicInteger(arrayList.size());
        N(new i(this), androidx.camera.core.impl.utils.executor.c.a());
        if (this.f24269b.isEmpty()) {
            this.f24274g.b(new ArrayList(this.f24270c));
            return;
        }
        for (int i12 = 0; i12 < this.f24269b.size(); i12++) {
            this.f24270c.add(null);
        }
        ArrayList arrayList2 = this.f24269b;
        for (int i13 = 0; i13 < arrayList2.size(); i13++) {
            D0 d02 = (D0) arrayList2.get(i13);
            d02.N(new j(this, i13, d02), executor);
        }
    }

    @Override // com.google.common.util.concurrent.D0
    public final void N(@N Runnable runnable, @N Executor executor) {
        this.f24273f.N(runnable, executor);
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z12) {
        ArrayList arrayList = this.f24269b;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((D0) it.next()).cancel(z12);
            }
        }
        return this.f24273f.cancel(z12);
    }

    @Override // java.util.concurrent.Future
    public final Object get(long j12, @N TimeUnit timeUnit) {
        return this.f24273f.get(j12, timeUnit);
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.f24273f.isCancelled();
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return this.f24273f.isDone();
    }

    @Override // java.util.concurrent.Future
    @P
    public final Object get() throws InterruptedException {
        ArrayList arrayList = this.f24269b;
        D0<List<V>> d02 = this.f24273f;
        if (arrayList != null && !d02.isDone()) {
            Iterator it = arrayList.iterator();
            loop0: while (it.hasNext()) {
                D0 d03 = (D0) it.next();
                while (!d03.isDone()) {
                    try {
                        d03.get();
                    } catch (Error e12) {
                        throw e12;
                    } catch (InterruptedException e13) {
                        throw e13;
                    } catch (Throwable unused) {
                        if (this.f24271d) {
                            break loop0;
                        }
                    }
                }
            }
        }
        return d02.get();
    }
}
