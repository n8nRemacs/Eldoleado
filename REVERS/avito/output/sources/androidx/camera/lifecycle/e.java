package androidx.camera.lifecycle;

import androidx.camera.core.P0;
import androidx.camera.core.internal.CameraUseCaseAdapter;
import androidx.core.util.z;
import androidx.view.InterfaceC22982O;
import androidx.view.InterfaceC22983P;
import androidx.view.InterfaceC23042i0;
import androidx.view.Lifecycle;
import j.B;
import j.N;
import j.P;
import j.X;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: LifecycleCameraRepository.java */
@X
/* loaded from: classes.dex */
final class e {

    /* renamed from: a, reason: collision with root package name */
    public final Object f24662a = new Object();

    /* renamed from: b, reason: collision with root package name */
    @B
    public final HashMap f24663b = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    @B
    public final HashMap f24664c = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    @B
    public final ArrayDeque<InterfaceC22983P> f24665d = new ArrayDeque<>();

    /* renamed from: e, reason: collision with root package name */
    @B
    @P
    public E.a f24666e;

    /* compiled from: LifecycleCameraRepository.java */
    @VY0.c
    public static abstract class a {
        @N
        public abstract CameraUseCaseAdapter.a a();

        @N
        public abstract InterfaceC22983P b();
    }

    /* compiled from: LifecycleCameraRepository.java */
    public static class b implements InterfaceC22982O {

        /* renamed from: b, reason: collision with root package name */
        public final e f24667b;

        /* renamed from: c, reason: collision with root package name */
        public final InterfaceC22983P f24668c;

        public b(InterfaceC22983P interfaceC22983P, e eVar) {
            this.f24668c = interfaceC22983P;
            this.f24667b = eVar;
        }

        @InterfaceC23042i0(Lifecycle.Event.ON_DESTROY)
        public void onDestroy(InterfaceC22983P interfaceC22983P) {
            e eVar = this.f24667b;
            synchronized (eVar.f24662a) {
                try {
                    b bVarB = eVar.b(interfaceC22983P);
                    if (bVarB == null) {
                        return;
                    }
                    eVar.f(interfaceC22983P);
                    Iterator it = ((Set) eVar.f24664c.get(bVarB)).iterator();
                    while (it.hasNext()) {
                        eVar.f24663b.remove((a) it.next());
                    }
                    eVar.f24664c.remove(bVarB);
                    bVarB.f24668c.getLifecycle().c(bVarB);
                } finally {
                }
            }
        }

        @InterfaceC23042i0(Lifecycle.Event.ON_START)
        public void onStart(InterfaceC22983P interfaceC22983P) {
            this.f24667b.e(interfaceC22983P);
        }

        @InterfaceC23042i0(Lifecycle.Event.ON_STOP)
        public void onStop(InterfaceC22983P interfaceC22983P) {
            this.f24667b.f(interfaceC22983P);
        }
    }

    public final void a(@N c cVar, @P P0 p02, @N List list, @N List list2, @P E.a aVar) {
        synchronized (this.f24662a) {
            try {
                z.b(!list2.isEmpty());
                this.f24666e = aVar;
                InterfaceC22983P interfaceC22983PK = cVar.k();
                Set set = (Set) this.f24664c.get(b(interfaceC22983PK));
                E.a aVar2 = this.f24666e;
                if (aVar2 == null || aVar2.a() != 2) {
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        c cVar2 = (c) this.f24663b.get((a) it.next());
                        cVar2.getClass();
                        if (!cVar2.equals(cVar) && !cVar2.n().isEmpty()) {
                            throw new IllegalArgumentException("Multiple LifecycleCameras with use cases are registered to the same LifecycleOwner.");
                        }
                    }
                }
                try {
                    CameraUseCaseAdapter cameraUseCaseAdapter = cVar.f24660d;
                    synchronized (cameraUseCaseAdapter.f24321l) {
                        cameraUseCaseAdapter.f24318i = p02;
                    }
                    CameraUseCaseAdapter cameraUseCaseAdapter2 = cVar.f24660d;
                    synchronized (cameraUseCaseAdapter2.f24321l) {
                        cameraUseCaseAdapter2.f24319j = list;
                    }
                    cVar.b(list2);
                    if (interfaceC22983PK.getLifecycle().getF46705d().a(Lifecycle.State.f46682e)) {
                        e(interfaceC22983PK);
                    }
                } catch (CameraUseCaseAdapter.CameraException e12) {
                    throw new IllegalArgumentException(e12.getMessage());
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final b b(InterfaceC22983P interfaceC22983P) {
        synchronized (this.f24662a) {
            try {
                for (b bVar : this.f24664c.keySet()) {
                    if (interfaceC22983P.equals(bVar.f24668c)) {
                        return bVar;
                    }
                }
                return null;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final boolean c(InterfaceC22983P interfaceC22983P) {
        synchronized (this.f24662a) {
            try {
                b bVarB = b(interfaceC22983P);
                if (bVarB == null) {
                    return false;
                }
                Iterator it = ((Set) this.f24664c.get(bVarB)).iterator();
                while (it.hasNext()) {
                    c cVar = (c) this.f24663b.get((a) it.next());
                    cVar.getClass();
                    if (!cVar.n().isEmpty()) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void d(c cVar) {
        synchronized (this.f24662a) {
            try {
                InterfaceC22983P interfaceC22983PK = cVar.k();
                androidx.camera.lifecycle.a aVar = new androidx.camera.lifecycle.a(interfaceC22983PK, cVar.f24660d.f24314e);
                b bVarB = b(interfaceC22983PK);
                Set hashSet = bVarB != null ? (Set) this.f24664c.get(bVarB) : new HashSet();
                hashSet.add(aVar);
                this.f24663b.put(aVar, cVar);
                if (bVarB == null) {
                    b bVar = new b(interfaceC22983PK, this);
                    this.f24664c.put(bVar, hashSet);
                    interfaceC22983PK.getLifecycle().a(bVar);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void e(InterfaceC22983P interfaceC22983P) {
        synchronized (this.f24662a) {
            try {
                if (c(interfaceC22983P)) {
                    if (this.f24665d.isEmpty()) {
                        this.f24665d.push(interfaceC22983P);
                    } else {
                        E.a aVar = this.f24666e;
                        if (aVar == null || aVar.a() != 2) {
                            InterfaceC22983P interfaceC22983PPeek = this.f24665d.peek();
                            if (!interfaceC22983P.equals(interfaceC22983PPeek)) {
                                g(interfaceC22983PPeek);
                                this.f24665d.remove(interfaceC22983P);
                                this.f24665d.push(interfaceC22983P);
                            }
                        }
                    }
                    h(interfaceC22983P);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void f(InterfaceC22983P interfaceC22983P) {
        synchronized (this.f24662a) {
            try {
                this.f24665d.remove(interfaceC22983P);
                g(interfaceC22983P);
                if (!this.f24665d.isEmpty()) {
                    h(this.f24665d.peek());
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void g(InterfaceC22983P interfaceC22983P) {
        synchronized (this.f24662a) {
            try {
                b bVarB = b(interfaceC22983P);
                if (bVarB == null) {
                    return;
                }
                Iterator it = ((Set) this.f24664c.get(bVarB)).iterator();
                while (it.hasNext()) {
                    c cVar = (c) this.f24663b.get((a) it.next());
                    cVar.getClass();
                    cVar.o();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void h(InterfaceC22983P interfaceC22983P) {
        synchronized (this.f24662a) {
            try {
                Iterator it = ((Set) this.f24664c.get(b(interfaceC22983P))).iterator();
                while (it.hasNext()) {
                    c cVar = (c) this.f24663b.get((a) it.next());
                    cVar.getClass();
                    if (!cVar.n().isEmpty()) {
                        cVar.p();
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
