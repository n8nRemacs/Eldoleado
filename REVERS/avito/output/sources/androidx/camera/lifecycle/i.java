package androidx.camera.lifecycle;

import E.a;
import android.content.Context;
import androidx.camera.core.AbstractC20131p;
import androidx.camera.core.C20144v;
import androidx.camera.core.C20148z;
import androidx.camera.core.InterfaceC20127n;
import androidx.camera.core.InterfaceC20141s;
import androidx.camera.core.N0;
import androidx.camera.core.O0;
import androidx.camera.core.P0;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.InterfaceC20116x;
import androidx.camera.core.impl.InterfaceC20117y;
import androidx.camera.core.impl.U;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.t;
import androidx.camera.core.internal.CameraUseCaseAdapter;
import androidx.camera.lifecycle.e;
import androidx.concurrent.futures.b;
import androidx.core.util.z;
import androidx.view.InterfaceC22983P;
import androidx.view.Lifecycle;
import com.google.common.util.concurrent.D0;
import j.B;
import j.K;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.Executor;
import q.InterfaceC47174a;

/* compiled from: ProcessCameraProvider.java */
@X
/* loaded from: classes.dex */
public final class i implements d {

    /* renamed from: f, reason: collision with root package name */
    public static final i f24674f = new i();

    /* renamed from: b, reason: collision with root package name */
    @B
    public D0<C20148z> f24676b;

    /* renamed from: e, reason: collision with root package name */
    public C20148z f24679e;

    /* renamed from: a, reason: collision with root package name */
    public final Object f24675a = new Object();

    /* renamed from: c, reason: collision with root package name */
    @B
    public final D0<Void> f24677c = androidx.camera.core.impl.utils.futures.f.g(null);

    /* renamed from: d, reason: collision with root package name */
    public final e f24678d = new e();

    @N
    public static D0<i> c(@N final Context context) {
        D0<C20148z> d0A;
        context.getClass();
        final i iVar = f24674f;
        synchronized (iVar.f24675a) {
            try {
                d0A = iVar.f24676b;
                if (d0A == null) {
                    final C20148z c20148z = new C20148z(context);
                    d0A = androidx.concurrent.futures.b.a(new b.c() { // from class: androidx.camera.lifecycle.g
                        @Override // androidx.concurrent.futures.b.c
                        public final Object d(b.a aVar) {
                            i iVar2 = this.f24670b;
                            C20148z c20148z2 = c20148z;
                            synchronized (iVar2.f24675a) {
                                androidx.camera.core.impl.utils.futures.d dVarA = androidx.camera.core.impl.utils.futures.d.a(iVar2.f24677c);
                                Ae0.c cVar = new Ae0.c(c20148z2, 9);
                                Executor executorA = androidx.camera.core.impl.utils.executor.c.a();
                                dVarA.getClass();
                                androidx.camera.core.impl.utils.futures.f.a((androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.m(dVarA, cVar, executorA), new h(aVar, c20148z2), androidx.camera.core.impl.utils.executor.c.a());
                            }
                            return "ProcessCameraProvider-initializeCameraX";
                        }
                    });
                    iVar.f24676b = d0A;
                }
            } finally {
            }
        }
        return androidx.camera.core.impl.utils.futures.f.l(d0A, new InterfaceC47174a() { // from class: androidx.camera.lifecycle.f
            @Override // q.InterfaceC47174a
            public final Object apply(Object obj) {
                i iVar2 = i.f24674f;
                iVar2.f24679e = (C20148z) obj;
                androidx.camera.core.impl.utils.h.a(context);
                return iVar2;
            }
        }, androidx.camera.core.impl.utils.executor.c.a());
    }

    @N
    @K
    public final InterfaceC20127n a(@N InterfaceC22983P interfaceC22983P, @N C20144v c20144v, @N O0 o02) {
        int i12;
        C20148z c20148z = this.f24679e;
        if (c20148z == null) {
            i12 = 0;
        } else {
            InterfaceC20117y interfaceC20117y = c20148z.f24603f;
            if (interfaceC20117y == null) {
                throw new IllegalStateException("CameraX not initialized yet.");
            }
            i12 = interfaceC20117y.d().f1850e;
        }
        if (i12 == 2) {
            throw new UnsupportedOperationException("bindToLifecycle for single camera is not supported in concurrent camera mode, call unbindAll() first");
        }
        C20148z c20148z2 = this.f24679e;
        if (c20148z2 != null) {
            InterfaceC20117y interfaceC20117y2 = c20148z2.f24603f;
            if (interfaceC20117y2 == null) {
                throw new IllegalStateException("CameraX not initialized yet.");
            }
            C.a aVarD = interfaceC20117y2.d();
            if (1 != aVarD.f1850e) {
                Iterator it = aVarD.f1846a.iterator();
                while (it.hasNext()) {
                    ((a.b) it.next()).a(aVarD.f1850e, 1);
                }
            }
            if (aVarD.f1850e == 2) {
                aVarD.f1848c.clear();
            }
            aVarD.f1850e = 1;
        }
        return b(interfaceC22983P, c20144v, o02.f23635a, o02.f23637c, (N0[]) o02.f23636b.toArray(new N0[0]));
    }

    @N
    public final InterfaceC20127n b(@N InterfaceC22983P interfaceC22983P, @N C20144v c20144v, @P P0 p02, @N List<AbstractC20131p> list, @N N0... n0Arr) {
        c cVar;
        Collection<c> collectionUnmodifiableCollection;
        c cVar2;
        c cVar3;
        boolean zContains;
        t.a();
        C20144v.a aVar = new C20144v.a(c20144v.f24564a);
        for (N0 n02 : n0Arr) {
            C20144v c20144vA = n02.f23620f.A();
            if (c20144vA != null) {
                Iterator<InterfaceC20141s> it = c20144vA.f24564a.iterator();
                while (it.hasNext()) {
                    aVar.f24565a.add(it.next());
                }
            }
        }
        LinkedHashSet<InterfaceC20141s> linkedHashSet = aVar.f24565a;
        C20144v c20144v2 = new C20144v();
        c20144v2.f24564a = linkedHashSet;
        LinkedHashSet<CameraInternal> linkedHashSetA = c20144v2.a(this.f24679e.f24598a.a());
        if (linkedHashSetA.isEmpty()) {
            throw new IllegalArgumentException("Provided camera selector unable to resolve a camera for the given use case");
        }
        CameraUseCaseAdapter.a aVar2 = new CameraUseCaseAdapter.a(linkedHashSetA);
        e eVar = this.f24678d;
        synchronized (eVar.f24662a) {
            cVar = (c) eVar.f24663b.get(new a(interfaceC22983P, aVar2));
        }
        e eVar2 = this.f24678d;
        synchronized (eVar2.f24662a) {
            collectionUnmodifiableCollection = Collections.unmodifiableCollection(eVar2.f24663b.values());
        }
        for (N0 n03 : n0Arr) {
            for (c cVar4 : collectionUnmodifiableCollection) {
                synchronized (cVar4.f24658b) {
                    zContains = ((ArrayList) cVar4.f24660d.t()).contains(n03);
                }
                if (zContains && cVar4 != cVar) {
                    throw new IllegalStateException(String.format("Use case %s already bound to a different lifecycle.", n03));
                }
            }
        }
        if (cVar == null) {
            e eVar3 = this.f24678d;
            InterfaceC20117y interfaceC20117y = this.f24679e.f24603f;
            if (interfaceC20117y == null) {
                throw new IllegalStateException("CameraX not initialized yet.");
            }
            C.a aVarD = interfaceC20117y.d();
            C20148z c20148z = this.f24679e;
            InterfaceC20116x interfaceC20116x = c20148z.f24604g;
            if (interfaceC20116x == null) {
                throw new IllegalStateException("CameraX not initialized yet.");
            }
            UseCaseConfigFactory useCaseConfigFactory = c20148z.f24605h;
            if (useCaseConfigFactory == null) {
                throw new IllegalStateException("CameraX not initialized yet.");
            }
            CameraUseCaseAdapter cameraUseCaseAdapter = new CameraUseCaseAdapter(linkedHashSetA, aVarD, interfaceC20116x, useCaseConfigFactory);
            synchronized (eVar3.f24662a) {
                try {
                    z.a("LifecycleCamera already exists for the given LifecycleOwner and set of cameras", eVar3.f24663b.get(new a(interfaceC22983P, cameraUseCaseAdapter.f24314e)) == null);
                    if (interfaceC22983P.getLifecycle().getF46705d() == Lifecycle.State.f46679b) {
                        throw new IllegalArgumentException("Trying to create LifecycleCamera with destroyed lifecycle.");
                    }
                    cVar3 = new c(interfaceC22983P, cameraUseCaseAdapter);
                    if (((ArrayList) cameraUseCaseAdapter.t()).isEmpty()) {
                        cVar3.o();
                    }
                    eVar3.d(cVar3);
                } finally {
                }
            }
            cVar2 = cVar3;
        } else {
            cVar2 = cVar;
        }
        Iterator<InterfaceC20141s> it2 = c20144v.f24564a.iterator();
        while (it2.hasNext()) {
            InterfaceC20141s next = it2.next();
            if (next.a() != InterfaceC20141s.f24532a) {
                U.a(next.a());
            }
        }
        cVar2.e(null);
        if (n0Arr.length == 0) {
            return cVar2;
        }
        e eVar4 = this.f24678d;
        List listAsList = Arrays.asList(n0Arr);
        InterfaceC20117y interfaceC20117y2 = this.f24679e.f24603f;
        if (interfaceC20117y2 == null) {
            throw new IllegalStateException("CameraX not initialized yet.");
        }
        eVar4.a(cVar2, p02, list, listAsList, interfaceC20117y2.d());
        return cVar2;
    }

    @K
    public final void d(@N N0... n0Arr) {
        int i12;
        t.a();
        C20148z c20148z = this.f24679e;
        if (c20148z == null) {
            i12 = 0;
        } else {
            InterfaceC20117y interfaceC20117y = c20148z.f24603f;
            if (interfaceC20117y == null) {
                throw new IllegalStateException("CameraX not initialized yet.");
            }
            i12 = interfaceC20117y.d().f1850e;
        }
        if (i12 == 2) {
            throw new UnsupportedOperationException("unbind usecase is not supported in concurrent camera mode, call unbindAll() first");
        }
        e eVar = this.f24678d;
        List listAsList = Arrays.asList(n0Arr);
        synchronized (eVar.f24662a) {
            Iterator it = eVar.f24663b.keySet().iterator();
            while (it.hasNext()) {
                c cVar = (c) eVar.f24663b.get((e.a) it.next());
                boolean zIsEmpty = cVar.n().isEmpty();
                synchronized (cVar.f24658b) {
                    ArrayList arrayList = new ArrayList(listAsList);
                    arrayList.retainAll(cVar.f24660d.t());
                    cVar.f24660d.v(arrayList);
                }
                if (!zIsEmpty && cVar.n().isEmpty()) {
                    eVar.f(cVar.k());
                }
            }
        }
    }

    @K
    public final void e() {
        t.a();
        C20148z c20148z = this.f24679e;
        if (c20148z != null) {
            InterfaceC20117y interfaceC20117y = c20148z.f24603f;
            if (interfaceC20117y == null) {
                throw new IllegalStateException("CameraX not initialized yet.");
            }
            C.a aVarD = interfaceC20117y.d();
            if (aVarD.f1850e != 0) {
                Iterator it = aVarD.f1846a.iterator();
                while (it.hasNext()) {
                    ((a.b) it.next()).a(aVarD.f1850e, 0);
                }
            }
            if (aVarD.f1850e == 2) {
                aVarD.f1848c.clear();
            }
            aVarD.f1850e = 0;
        }
        e eVar = this.f24678d;
        synchronized (eVar.f24662a) {
            Iterator it2 = eVar.f24663b.keySet().iterator();
            while (it2.hasNext()) {
                c cVar = (c) eVar.f24663b.get((e.a) it2.next());
                synchronized (cVar.f24658b) {
                    CameraUseCaseAdapter cameraUseCaseAdapter = cVar.f24660d;
                    cameraUseCaseAdapter.v((ArrayList) cameraUseCaseAdapter.t());
                }
                eVar.f(cVar.k());
            }
        }
    }
}
