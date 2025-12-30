package androidx.camera.core.impl;

import Fc1.G3;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.concurrent.futures.b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: DeferrableSurfaces.java */
@j.X
/* loaded from: classes.dex */
public final class O {
    public static void a(@j.N List<DeferrableSurface> list) {
        Iterator<DeferrableSurface> it = list.iterator();
        while (it.hasNext()) {
            it.next().b();
        }
    }

    public static void b(@j.N List<DeferrableSurface> list) throws DeferrableSurface.SurfaceClosedException {
        if (list.isEmpty()) {
            return;
        }
        int i12 = 0;
        do {
            try {
                list.get(i12).d();
                i12++;
            } catch (DeferrableSurface.SurfaceClosedException e12) {
                for (int i13 = i12 - 1; i13 >= 0; i13--) {
                    list.get(i13).b();
                }
                throw e12;
            }
        } while (i12 < list.size());
    }

    @j.N
    public static com.google.common.util.concurrent.D0 c(@j.N List list, @j.N final Executor executor, @j.N final ScheduledExecutorService scheduledExecutorService) {
        final ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(androidx.camera.core.impl.utils.futures.f.h(((DeferrableSurface) it.next()).c()));
        }
        return androidx.concurrent.futures.b.a(new b.c() { // from class: androidx.camera.core.impl.M
            @Override // androidx.concurrent.futures.b.c
            public final Object d(b.a aVar) {
                com.google.common.util.concurrent.D0 d0K = androidx.camera.core.impl.utils.futures.f.k(arrayList);
                Executor executor2 = executor;
                ScheduledFuture<?> scheduledFutureSchedule = scheduledExecutorService.schedule(new RunnableC20100j0(executor2, d0K, aVar, 1), 5000L, TimeUnit.MILLISECONDS);
                aVar.a(new G3(d0K, 6), executor2);
                androidx.camera.core.impl.utils.futures.f.a(d0K, new N(aVar, scheduledFutureSchedule), executor2);
                return "surfaceList";
            }
        });
    }
}
