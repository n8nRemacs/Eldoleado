package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.util.Pair;
import android.util.Size;
import androidx.camera.camera2.internal.Z0;
import androidx.camera.camera2.internal.compat.quirk.C19996g;
import androidx.camera.core.C20140q0;
import androidx.camera.core.CameraState;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.Timebase;
import androidx.view.AbstractC22991Y;
import androidx.view.C23034e0;
import androidx.view.C23038g0;
import androidx.view.InterfaceC23040h0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: Camera2CameraInfoImpl.java */
@j.S
@j.X
/* renamed from: androidx.camera.camera2.internal.z, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20045z implements androidx.camera.core.impl.B {

    /* renamed from: a, reason: collision with root package name */
    public final String f23525a;

    /* renamed from: b, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.n f23526b;

    /* renamed from: c, reason: collision with root package name */
    public final D.f f23527c;

    /* renamed from: e, reason: collision with root package name */
    @j.B
    @j.P
    public C20026p f23529e;

    /* renamed from: h, reason: collision with root package name */
    @j.N
    public final a<CameraState> f23532h;

    /* renamed from: j, reason: collision with root package name */
    @j.N
    public final androidx.camera.core.impl.w0 f23534j;

    /* renamed from: k, reason: collision with root package name */
    @j.N
    public final M f23535k;

    /* renamed from: l, reason: collision with root package name */
    @j.N
    public final androidx.camera.camera2.internal.compat.w f23536l;

    /* renamed from: d, reason: collision with root package name */
    public final Object f23528d = new Object();

    /* renamed from: f, reason: collision with root package name */
    @j.B
    @j.P
    public a<Integer> f23530f = null;

    /* renamed from: g, reason: collision with root package name */
    @j.B
    @j.P
    public a<androidx.camera.core.Q0> f23531g = null;

    /* renamed from: i, reason: collision with root package name */
    @j.B
    @j.P
    public ArrayList f23533i = null;

    /* compiled from: Camera2CameraInfoImpl.java */
    /* renamed from: androidx.camera.camera2.internal.z$a */
    public static class a<T> extends C23034e0<T> {

        /* renamed from: b, reason: collision with root package name */
        public C23038g0 f23537b;

        /* renamed from: c, reason: collision with root package name */
        public final T f23538c;

        public a(T t12) {
            this.f23538c = t12;
        }

        @Override // androidx.view.C23034e0
        public final <S> void a(@j.N AbstractC22991Y<S> abstractC22991Y, @j.N InterfaceC23040h0<? super S> interfaceC23040h0) {
            throw new UnsupportedOperationException();
        }

        public final void c(@j.N C23038g0 c23038g0) {
            C23038g0 c23038g02 = this.f23537b;
            if (c23038g02 != null) {
                b(c23038g02);
            }
            this.f23537b = c23038g0;
            super.a(c23038g0, new InterfaceC23040h0() { // from class: androidx.camera.camera2.internal.y
                @Override // androidx.view.InterfaceC23040h0
                public final void onChanged(Object obj) {
                    this.f23523b.setValue(obj);
                }
            });
        }

        @Override // androidx.view.AbstractC22991Y
        public final T getValue() {
            C23038g0 c23038g0 = this.f23537b;
            return c23038g0 == null ? this.f23538c : c23038g0.getValue();
        }
    }

    public C20045z(@j.N String str, @j.N androidx.camera.camera2.internal.compat.w wVar) {
        str.getClass();
        this.f23525a = str;
        this.f23536l = wVar;
        androidx.camera.camera2.internal.compat.n nVarB = wVar.b(str);
        this.f23526b = nVarB;
        this.f23527c = new D.f(this);
        this.f23534j = C19996g.a(nVarB);
        this.f23535k = new M(str);
        this.f23532h = new a<>(CameraState.a(CameraState.Type.f23555f));
    }

    @Override // androidx.camera.core.impl.B
    @j.N
    public final Set<androidx.camera.core.F> a() {
        return androidx.camera.camera2.internal.compat.params.b.a(this.f23526b).c();
    }

    @Override // androidx.camera.core.InterfaceC20142t
    public final int b() {
        Integer num = (Integer) this.f23526b.a(CameraCharacteristics.LENS_FACING);
        androidx.core.util.z.a("Unable to get the lens facing of the camera.", num != null);
        int iIntValue = num.intValue();
        if (iIntValue == 0) {
            return 0;
        }
        if (iIntValue == 1) {
            return 1;
        }
        if (iIntValue == 2) {
            return 2;
        }
        throw new IllegalArgumentException(G.e(iIntValue, "The given lens facing integer: ", " can not be recognized."));
    }

    @Override // androidx.camera.core.impl.B
    public final void c(@j.N AbstractC20109p abstractC20109p) {
        synchronized (this.f23528d) {
            try {
                C20026p c20026p = this.f23529e;
                if (c20026p != null) {
                    c20026p.f23389c.execute(new RunnableC20016k(0, c20026p, abstractC20109p));
                    return;
                }
                ArrayList arrayList = this.f23533i;
                if (arrayList == null) {
                    return;
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    if (((Pair) it.next()).first == abstractC20109p) {
                        it.remove();
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.impl.B
    @j.N
    public final androidx.camera.core.impl.Q d() {
        return this.f23535k;
    }

    @Override // androidx.camera.core.InterfaceC20142t
    @j.N
    public final AbstractC22991Y<androidx.camera.core.Q0> e() {
        synchronized (this.f23528d) {
            try {
                C20026p c20026p = this.f23529e;
                if (c20026p != null) {
                    a<androidx.camera.core.Q0> aVar = this.f23531g;
                    if (aVar != null) {
                        return aVar;
                    }
                    return c20026p.f23395i.f23097d;
                }
                if (this.f23531g == null) {
                    Z0.b bVarA = Z0.a(this.f23526b);
                    a1 a1Var = new a1(bVarA.getMaxZoom(), bVarA.getMinZoom());
                    a1Var.f(1.0f);
                    this.f23531g = new a<>(androidx.camera.core.internal.e.f(a1Var));
                }
                return this.f23531g;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.InterfaceC20142t
    public final int f() {
        return m(0);
    }

    @Override // androidx.camera.core.impl.B
    @j.N
    public final androidx.camera.core.impl.w0 g() {
        return this.f23534j;
    }

    @Override // androidx.camera.core.InterfaceC20142t
    @j.N
    public final AbstractC22991Y<Integer> h() {
        synchronized (this.f23528d) {
            try {
                C20026p c20026p = this.f23529e;
                if (c20026p == null) {
                    if (this.f23530f == null) {
                        this.f23530f = new a<>(0);
                    }
                    return this.f23530f;
                }
                a<Integer> aVar = this.f23530f;
                if (aVar != null) {
                    return aVar;
                }
                return c20026p.f23396j.f23079b;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.impl.B
    @j.N
    public final Timebase i() {
        Integer num = (Integer) this.f23526b.a(CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE);
        num.getClass();
        return num.intValue() != 1 ? Timebase.f24048b : Timebase.f24049c;
    }

    @Override // androidx.camera.core.InterfaceC20142t
    @j.N
    public final String j() {
        Integer num = (Integer) this.f23526b.a(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        num.getClass();
        return num.intValue() == 2 ? "androidx.camera.camera2.legacy" : "androidx.camera.camera2";
    }

    @Override // androidx.camera.core.impl.B
    @j.N
    public final String k() {
        return this.f23525a;
    }

    @Override // androidx.camera.core.impl.B
    public final void l(@j.N Executor executor, @j.N AbstractC20109p abstractC20109p) {
        synchronized (this.f23528d) {
            try {
                C20026p c20026p = this.f23529e;
                if (c20026p != null) {
                    c20026p.f23389c.execute(new RunnableC20020m(c20026p, executor, abstractC20109p, 0));
                } else {
                    if (this.f23533i == null) {
                        this.f23533i = new ArrayList();
                    }
                    this.f23533i.add(new Pair(abstractC20109p, executor));
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.InterfaceC20142t
    public final int m(int i12) {
        Integer num = (Integer) this.f23526b.a(CameraCharacteristics.SENSOR_ORIENTATION);
        num.getClass();
        return androidx.camera.core.impl.utils.e.a(androidx.camera.core.impl.utils.e.b(i12), num.intValue(), 1 == b());
    }

    @Override // androidx.camera.core.InterfaceC20142t
    public final boolean n() {
        androidx.camera.camera2.internal.compat.n nVar = this.f23526b;
        Objects.requireNonNull(nVar);
        return androidx.camera.camera2.internal.compat.workaround.g.a(new C(nVar, 7));
    }

    @Override // androidx.camera.core.impl.B
    @j.N
    public final List<Size> o(int i12) {
        Size[] sizeArrA = this.f23526b.b().a(i12);
        return sizeArrA != null ? Arrays.asList(sizeArrA) : Collections.emptyList();
    }

    @Override // androidx.camera.core.InterfaceC20142t
    @j.N
    public final androidx.camera.core.K q() {
        synchronized (this.f23528d) {
            try {
                C20026p c20026p = this.f23529e;
                if (c20026p == null) {
                    return new C20023n0(this.f23526b);
                }
                return c20026p.f23397k.f23373b;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void r(@j.N C20026p c20026p) {
        synchronized (this.f23528d) {
            try {
                this.f23529e = c20026p;
                a<androidx.camera.core.Q0> aVar = this.f23531g;
                if (aVar != null) {
                    aVar.c(c20026p.f23395i.f23097d);
                }
                a<Integer> aVar2 = this.f23530f;
                if (aVar2 != null) {
                    aVar2.c(this.f23529e.f23396j.f23079b);
                }
                ArrayList arrayList = this.f23533i;
                if (arrayList != null) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        Pair pair = (Pair) it.next();
                        C20026p c20026p2 = this.f23529e;
                        Executor executor = (Executor) pair.second;
                        AbstractC20109p abstractC20109p = (AbstractC20109p) pair.first;
                        c20026p2.getClass();
                        c20026p2.f23389c.execute(new RunnableC20020m(c20026p2, executor, abstractC20109p, 0));
                    }
                    this.f23533i = null;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        ((Integer) this.f23526b.a(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).getClass();
        C20140q0.d(4, "Camera2CameraInfo");
    }
}
