package androidx.camera.core.impl.utils.futures;

import androidx.concurrent.futures.b;
import androidx.core.util.z;
import com.google.common.util.concurrent.D0;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: ListFuture.java */
/* loaded from: classes.dex */
class j implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24266b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ D0 f24267c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ k f24268d;

    public j(k kVar, int i12, D0 d02) {
        this.f24268d = kVar;
        this.f24266b = i12;
        this.f24267c = d02;
    }

    @Override // java.lang.Runnable
    public final void run() {
        b.a aVar;
        ArrayList arrayList;
        int iDecrementAndGet;
        int i12 = this.f24266b;
        D0 d02 = this.f24267c;
        k kVar = this.f24268d;
        AtomicInteger atomicInteger = kVar.f24272e;
        ArrayList arrayList2 = kVar.f24270c;
        Future future = kVar.f24273f;
        boolean zIsDone = future.isDone();
        boolean z12 = kVar.f24271d;
        if (zIsDone || arrayList2 == null) {
            z.g("Future was done before all dependencies completed", z12);
            return;
        }
        try {
            try {
                try {
                    try {
                        z.g("Tried to set value from future which is not done", d02.isDone());
                        arrayList2.set(i12, f.d(d02));
                        iDecrementAndGet = atomicInteger.decrementAndGet();
                        z.g("Less than 0 remaining futures", iDecrementAndGet >= 0);
                    } catch (ExecutionException e12) {
                        if (z12) {
                            kVar.f24274g.d(e12.getCause());
                        }
                        int iDecrementAndGet2 = atomicInteger.decrementAndGet();
                        z.g("Less than 0 remaining futures", iDecrementAndGet2 >= 0);
                        if (iDecrementAndGet2 != 0) {
                            return;
                        }
                        ArrayList arrayList3 = kVar.f24270c;
                        if (arrayList3 != null) {
                            aVar = kVar.f24274g;
                            arrayList = new ArrayList(arrayList3);
                        }
                    }
                } catch (Error e13) {
                    kVar.f24274g.d(e13);
                    int iDecrementAndGet3 = atomicInteger.decrementAndGet();
                    z.g("Less than 0 remaining futures", iDecrementAndGet3 >= 0);
                    if (iDecrementAndGet3 != 0) {
                        return;
                    }
                    ArrayList arrayList4 = kVar.f24270c;
                    if (arrayList4 != null) {
                        aVar = kVar.f24274g;
                        arrayList = new ArrayList(arrayList4);
                    }
                }
            } catch (CancellationException unused) {
                if (z12) {
                    kVar.cancel(false);
                }
                int iDecrementAndGet4 = atomicInteger.decrementAndGet();
                z.g("Less than 0 remaining futures", iDecrementAndGet4 >= 0);
                if (iDecrementAndGet4 != 0) {
                    return;
                }
                ArrayList arrayList5 = kVar.f24270c;
                if (arrayList5 != null) {
                    aVar = kVar.f24274g;
                    arrayList = new ArrayList(arrayList5);
                }
            } catch (RuntimeException e14) {
                if (z12) {
                    kVar.f24274g.d(e14);
                }
                int iDecrementAndGet5 = atomicInteger.decrementAndGet();
                z.g("Less than 0 remaining futures", iDecrementAndGet5 >= 0);
                if (iDecrementAndGet5 != 0) {
                    return;
                }
                ArrayList arrayList6 = kVar.f24270c;
                if (arrayList6 != null) {
                    aVar = kVar.f24274g;
                    arrayList = new ArrayList(arrayList6);
                }
            }
            if (iDecrementAndGet == 0) {
                ArrayList arrayList7 = kVar.f24270c;
                if (arrayList7 != null) {
                    aVar = kVar.f24274g;
                    arrayList = new ArrayList(arrayList7);
                    aVar.b(arrayList);
                    return;
                }
                z.g(null, future.isDone());
            }
        } catch (Throwable th2) {
            int iDecrementAndGet6 = atomicInteger.decrementAndGet();
            z.g("Less than 0 remaining futures", iDecrementAndGet6 >= 0);
            if (iDecrementAndGet6 == 0) {
                ArrayList arrayList8 = kVar.f24270c;
                if (arrayList8 != null) {
                    kVar.f24274g.b(new ArrayList(arrayList8));
                } else {
                    z.g(null, future.isDone());
                }
            }
            throw th2;
        }
    }
}
