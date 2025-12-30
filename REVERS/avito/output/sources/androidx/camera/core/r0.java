package androidx.camera.core;

import android.media.ImageReader;
import android.util.LongSparseArray;
import android.view.Surface;
import androidx.annotation.RestrictTo;
import androidx.camera.core.O;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.InterfaceC20092f0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* compiled from: MetadataImageReader.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public class r0 implements InterfaceC20092f0, O.a {

    /* renamed from: a, reason: collision with root package name */
    public final Object f24518a;

    /* renamed from: b, reason: collision with root package name */
    public final AbstractC20109p f24519b;

    /* renamed from: c, reason: collision with root package name */
    @j.B
    public int f24520c;

    /* renamed from: d, reason: collision with root package name */
    public final S f24521d;

    /* renamed from: e, reason: collision with root package name */
    @j.B
    public boolean f24522e;

    /* renamed from: f, reason: collision with root package name */
    @j.B
    public final InterfaceC20092f0 f24523f;

    /* renamed from: g, reason: collision with root package name */
    @j.B
    @j.P
    public InterfaceC20092f0.a f24524g;

    /* renamed from: h, reason: collision with root package name */
    @j.B
    @j.P
    public Executor f24525h;

    /* renamed from: i, reason: collision with root package name */
    @j.B
    public final LongSparseArray<InterfaceC20060g0> f24526i;

    /* renamed from: j, reason: collision with root package name */
    @j.B
    public final LongSparseArray<InterfaceC20120j0> f24527j;

    /* renamed from: k, reason: collision with root package name */
    @j.B
    public int f24528k;

    /* renamed from: l, reason: collision with root package name */
    @j.B
    public final ArrayList f24529l;

    /* renamed from: m, reason: collision with root package name */
    @j.B
    public final ArrayList f24530m;

    /* compiled from: MetadataImageReader.java */
    public class a extends AbstractC20109p {
        public a() {
        }

        @Override // androidx.camera.core.impl.AbstractC20109p
        public final void b(@j.N androidx.camera.core.impl.r rVar) {
            r0 r0Var = r0.this;
            synchronized (r0Var.f24518a) {
                try {
                    if (r0Var.f24522e) {
                        return;
                    }
                    r0Var.f24526i.put(rVar.a(), new androidx.camera.core.internal.c(rVar));
                    r0Var.k();
                } finally {
                }
            }
        }
    }

    public r0(int i12, int i13, int i14, int i15) {
        C20053d c20053d = new C20053d(ImageReader.newInstance(i12, i13, i14, i15));
        this.f24518a = new Object();
        this.f24519b = new a();
        this.f24520c = 0;
        this.f24521d = new S(this);
        this.f24522e = false;
        this.f24526i = new LongSparseArray<>();
        this.f24527j = new LongSparseArray<>();
        this.f24530m = new ArrayList();
        this.f24523f = c20053d;
        this.f24528k = 0;
        this.f24529l = new ArrayList(g());
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final InterfaceC20120j0 a() {
        synchronized (this.f24518a) {
            try {
                if (this.f24529l.isEmpty()) {
                    return null;
                }
                if (this.f24528k >= this.f24529l.size()) {
                    throw new IllegalStateException("Maximum image number reached.");
                }
                ArrayList arrayList = this.f24529l;
                int i12 = this.f24528k;
                this.f24528k = i12 + 1;
                InterfaceC20120j0 interfaceC20120j0 = (InterfaceC20120j0) arrayList.get(i12);
                this.f24530m.add(interfaceC20120j0);
                return interfaceC20120j0;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final InterfaceC20120j0 b() {
        synchronized (this.f24518a) {
            try {
                if (this.f24529l.isEmpty()) {
                    return null;
                }
                if (this.f24528k >= this.f24529l.size()) {
                    throw new IllegalStateException("Maximum image number reached.");
                }
                ArrayList arrayList = new ArrayList();
                for (int i12 = 0; i12 < this.f24529l.size() - 1; i12++) {
                    if (!this.f24530m.contains(this.f24529l.get(i12))) {
                        arrayList.add((InterfaceC20120j0) this.f24529l.get(i12));
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((InterfaceC20120j0) it.next()).close();
                }
                int size = this.f24529l.size();
                ArrayList arrayList2 = this.f24529l;
                this.f24528k = size;
                InterfaceC20120j0 interfaceC20120j0 = (InterfaceC20120j0) arrayList2.get(size - 1);
                this.f24530m.add(interfaceC20120j0);
                return interfaceC20120j0;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void c() {
        synchronized (this.f24518a) {
            this.f24523f.c();
            this.f24524g = null;
            this.f24525h = null;
            this.f24520c = 0;
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void close() {
        synchronized (this.f24518a) {
            try {
                if (this.f24522e) {
                    return;
                }
                Iterator it = new ArrayList(this.f24529l).iterator();
                while (it.hasNext()) {
                    ((InterfaceC20120j0) it.next()).close();
                }
                this.f24529l.clear();
                ((C20053d) this.f24523f).close();
                this.f24522e = true;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final void d(@j.N InterfaceC20092f0.a aVar, @j.N Executor executor) {
        synchronized (this.f24518a) {
            aVar.getClass();
            this.f24524g = aVar;
            executor.getClass();
            this.f24525h = executor;
            ((C20053d) this.f24523f).d(this.f24521d, executor);
        }
    }

    @Override // androidx.camera.core.O.a
    public final void e(@j.N O o12) {
        synchronized (this.f24518a) {
            h(o12);
        }
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int f() {
        int iF2;
        synchronized (this.f24518a) {
            iF2 = this.f24523f.f();
        }
        return iF2;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int g() {
        int iG2;
        synchronized (this.f24518a) {
            iG2 = this.f24523f.g();
        }
        return iG2;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int getHeight() {
        int height;
        synchronized (this.f24518a) {
            height = this.f24523f.getHeight();
        }
        return height;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    @j.P
    public final Surface getSurface() {
        Surface surface;
        synchronized (this.f24518a) {
            surface = this.f24523f.getSurface();
        }
        return surface;
    }

    @Override // androidx.camera.core.impl.InterfaceC20092f0
    public final int getWidth() {
        int width;
        synchronized (this.f24518a) {
            width = this.f24523f.getWidth();
        }
        return width;
    }

    public final void h(O o12) {
        synchronized (this.f24518a) {
            try {
                int iIndexOf = this.f24529l.indexOf(o12);
                if (iIndexOf >= 0) {
                    this.f24529l.remove(iIndexOf);
                    int i12 = this.f24528k;
                    if (iIndexOf <= i12) {
                        this.f24528k = i12 - 1;
                    }
                }
                this.f24530m.remove(o12);
                if (this.f24520c > 0) {
                    j(this.f24523f);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void i(z0 z0Var) {
        InterfaceC20092f0.a aVar;
        Executor executor;
        synchronized (this.f24518a) {
            try {
                if (this.f24529l.size() < g()) {
                    z0Var.a(this);
                    this.f24529l.add(z0Var);
                    aVar = this.f24524g;
                    executor = this.f24525h;
                } else {
                    C20140q0.d(3, "TAG");
                    z0Var.close();
                    aVar = null;
                    executor = null;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (aVar != null) {
            if (executor != null) {
                executor.execute(new RunnableC20051c(2, this, aVar));
            } else {
                aVar.a(this);
            }
        }
    }

    public final void j(InterfaceC20092f0 interfaceC20092f0) {
        InterfaceC20120j0 interfaceC20120j0A;
        synchronized (this.f24518a) {
            try {
                if (this.f24522e) {
                    return;
                }
                int size = this.f24527j.size() + this.f24529l.size();
                if (size >= interfaceC20092f0.g()) {
                    C20140q0.d(3, "MetadataImageReader");
                    return;
                }
                do {
                    try {
                        interfaceC20120j0A = interfaceC20092f0.a();
                        if (interfaceC20120j0A != null) {
                            this.f24520c--;
                            size++;
                            this.f24527j.put(interfaceC20120j0A.r1().a(), interfaceC20120j0A);
                            k();
                        }
                    } catch (IllegalStateException unused) {
                        C20140q0.d(3, "MetadataImageReader");
                        interfaceC20120j0A = null;
                    }
                    if (interfaceC20120j0A == null || this.f24520c <= 0) {
                        break;
                    }
                } while (size < interfaceC20092f0.g());
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void k() {
        synchronized (this.f24518a) {
            try {
                for (int size = this.f24526i.size() - 1; size >= 0; size--) {
                    InterfaceC20060g0 interfaceC20060g0ValueAt = this.f24526i.valueAt(size);
                    long jA2 = interfaceC20060g0ValueAt.a();
                    InterfaceC20120j0 interfaceC20120j0 = this.f24527j.get(jA2);
                    if (interfaceC20120j0 != null) {
                        this.f24527j.remove(jA2);
                        this.f24526i.removeAt(size);
                        i(new z0(interfaceC20120j0, null, interfaceC20060g0ValueAt));
                    }
                }
                l();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void l() {
        synchronized (this.f24518a) {
            try {
                if (this.f24527j.size() != 0 && this.f24526i.size() != 0) {
                    long jKeyAt = this.f24527j.keyAt(0);
                    Long lValueOf = Long.valueOf(jKeyAt);
                    long jKeyAt2 = this.f24526i.keyAt(0);
                    androidx.core.util.z.b(!Long.valueOf(jKeyAt2).equals(lValueOf));
                    if (jKeyAt2 > jKeyAt) {
                        for (int size = this.f24527j.size() - 1; size >= 0; size--) {
                            if (this.f24527j.keyAt(size) < jKeyAt2) {
                                this.f24527j.valueAt(size).close();
                                this.f24527j.removeAt(size);
                            }
                        }
                    } else {
                        for (int size2 = this.f24526i.size() - 1; size2 >= 0; size2--) {
                            if (this.f24526i.keyAt(size2) < jKeyAt) {
                                this.f24526i.removeAt(size2);
                            }
                        }
                    }
                }
            } finally {
            }
        }
    }
}
