package androidx.camera.core.impl.utils.futures;

import androidx.appcompat.app.r;
import androidx.camera.core.impl.utils.futures.g;
import androidx.concurrent.futures.b;
import androidx.core.util.z;
import com.google.common.util.concurrent.D0;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import q.InterfaceC47174a;

/* compiled from: Futures.java */
@X
/* loaded from: classes.dex */
public final class f {

    /* renamed from: a, reason: collision with root package name */
    public static final InterfaceC47174a<?, ?> f24254a = new b();

    /* JADX INFO: Add missing generic type declarations: [I, O] */
    /* compiled from: Futures.java */
    public class a<I, O> implements androidx.camera.core.impl.utils.futures.a<I, O> {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ InterfaceC47174a f24255b;

        public a(InterfaceC47174a interfaceC47174a) {
            this.f24255b = interfaceC47174a;
        }

        @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
        @N
        public final D0<O> apply(I i12) {
            return f.g(this.f24255b.apply(i12));
        }
    }

    /* JADX INFO: Add missing generic type declarations: [I] */
    /* compiled from: Futures.java */
    public class c<I> implements androidx.camera.core.impl.utils.futures.c<I> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ b.a f24256a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ InterfaceC47174a f24257b;

        public c(b.a aVar, InterfaceC47174a interfaceC47174a) {
            this.f24256a = aVar;
            this.f24257b = interfaceC47174a;
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@N Throwable th2) {
            this.f24256a.d(th2);
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void onSuccess(@P I i12) {
            b.a aVar = this.f24256a;
            try {
                ((b) this.f24257b).getClass();
                aVar.b(i12);
            } catch (Throwable th2) {
                aVar.d(th2);
            }
        }
    }

    /* compiled from: Futures.java */
    public class d implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ D0 f24258b;

        public d(D0 d02) {
            this.f24258b = d02;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.f24258b.cancel(true);
        }
    }

    /* compiled from: Futures.java */
    public static final class e<V> implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final D0 f24259b;

        /* renamed from: c, reason: collision with root package name */
        public final androidx.camera.core.impl.utils.futures.c<? super V> f24260c;

        public e(D0 d02, androidx.camera.core.impl.utils.futures.c cVar) {
            this.f24259b = d02;
            this.f24260c = cVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            androidx.camera.core.impl.utils.futures.c<? super V> cVar = this.f24260c;
            try {
                cVar.onSuccess((Object) f.c(this.f24259b));
            } catch (Error e12) {
                e = e12;
                cVar.a(e);
            } catch (RuntimeException e13) {
                e = e13;
                cVar.a(e);
            } catch (ExecutionException e14) {
                Throwable cause = e14.getCause();
                if (cause == null) {
                    cVar.a(e14);
                } else {
                    cVar.a(cause);
                }
            }
        }

        public final String toString() {
            StringBuilder sb2 = new StringBuilder();
            r.C(e.class, sb2, ",");
            sb2.append(this.f24260c);
            return sb2.toString();
        }
    }

    public static <V> void a(@N D0<V> d02, @N androidx.camera.core.impl.utils.futures.c<? super V> cVar, @N Executor executor) {
        cVar.getClass();
        d02.N(new e(d02, cVar), executor);
    }

    @N
    public static D0 b(@N List list) {
        return new k(new ArrayList(list), true, androidx.camera.core.impl.utils.executor.c.a());
    }

    @P
    public static Object c(@N D0 d02) {
        z.g("Future was expected to be done, " + d02, d02.isDone());
        return d(d02);
    }

    @P
    public static <V> V d(@N Future<V> future) {
        V v12;
        boolean z12 = false;
        while (true) {
            try {
                v12 = future.get();
                break;
            } catch (InterruptedException unused) {
                z12 = true;
            } catch (Throwable th2) {
                if (z12) {
                    Thread.currentThread().interrupt();
                }
                throw th2;
            }
        }
        if (z12) {
            Thread.currentThread().interrupt();
        }
        return v12;
    }

    @N
    public static <V> D0<V> e(@N Throwable th2) {
        return new g.a(th2);
    }

    @N
    public static ScheduledFuture f(@N RejectedExecutionException rejectedExecutionException) {
        return new g.b(rejectedExecutionException);
    }

    @N
    public static <V> D0<V> g(@P V v12) {
        return v12 == null ? g.c.f24262c : new g.c(v12);
    }

    @N
    public static <V> D0<V> h(@N D0<V> d02) {
        d02.getClass();
        return d02.isDone() ? d02 : androidx.concurrent.futures.b.a(new androidx.camera.core.impl.utils.futures.e(d02));
    }

    public static void i(@N b.a aVar, @N D0 d02) {
        j(true, d02, f24254a, aVar, androidx.camera.core.impl.utils.executor.c.a());
    }

    public static <I, O> void j(boolean z12, @N D0<I> d02, @N InterfaceC47174a<? super I, ? extends O> interfaceC47174a, @N b.a<O> aVar, @N Executor executor) {
        d02.getClass();
        interfaceC47174a.getClass();
        aVar.getClass();
        executor.getClass();
        a(d02, new c(aVar, interfaceC47174a), executor);
        if (z12) {
            aVar.a(new d(d02), androidx.camera.core.impl.utils.executor.c.a());
        }
    }

    @N
    public static D0 k(@N List list) {
        return new k(new ArrayList(list), false, androidx.camera.core.impl.utils.executor.c.a());
    }

    @N
    public static <I, O> D0<O> l(@N D0<I> d02, @N InterfaceC47174a<? super I, ? extends O> interfaceC47174a, @N Executor executor) {
        return m(d02, new a(interfaceC47174a), executor);
    }

    @N
    public static <I, O> D0<O> m(@N D0<I> d02, @N androidx.camera.core.impl.utils.futures.a<? super I, ? extends O> aVar, @N Executor executor) {
        androidx.camera.core.impl.utils.futures.b bVar = new androidx.camera.core.impl.utils.futures.b(aVar, d02);
        d02.N(bVar, executor);
        return bVar;
    }

    /* compiled from: Futures.java */
    public class b implements InterfaceC47174a<Object, Object> {
        @Override // q.InterfaceC47174a
        public final Object apply(Object obj) {
            return obj;
        }
    }
}
