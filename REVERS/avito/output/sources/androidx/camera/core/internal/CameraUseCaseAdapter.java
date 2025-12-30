package androidx.camera.core.internal;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.AbstractC20131p;
import androidx.camera.core.C20140q0;
import androidx.camera.core.CameraControl;
import androidx.camera.core.InterfaceC20127n;
import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.N0;
import androidx.camera.core.P0;
import androidx.camera.core.impl.A0;
import androidx.camera.core.impl.B0;
import androidx.camera.core.impl.C20115w;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.G0;
import androidx.camera.core.impl.InterfaceC20112t;
import androidx.camera.core.impl.InterfaceC20116x;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.s0;
import androidx.core.util.z;
import j.B;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

@X
/* loaded from: classes.dex */
public final class CameraUseCaseAdapter implements InterfaceC20127n {

    /* renamed from: b, reason: collision with root package name */
    @N
    public final CameraInternal f24311b;

    /* renamed from: c, reason: collision with root package name */
    public final InterfaceC20116x f24312c;

    /* renamed from: d, reason: collision with root package name */
    public final UseCaseConfigFactory f24313d;

    /* renamed from: e, reason: collision with root package name */
    public final a f24314e;

    /* renamed from: h, reason: collision with root package name */
    @B
    public final E.a f24317h;

    /* renamed from: i, reason: collision with root package name */
    @B
    @P
    public P0 f24318i;

    /* renamed from: o, reason: collision with root package name */
    @B
    @P
    public N0 f24324o;

    /* renamed from: p, reason: collision with root package name */
    @B
    @P
    public androidx.camera.core.streamsharing.d f24325p;

    /* renamed from: q, reason: collision with root package name */
    @N
    public final A0 f24326q;

    /* renamed from: r, reason: collision with root package name */
    @N
    public final B0 f24327r;

    /* renamed from: f, reason: collision with root package name */
    @B
    public final ArrayList f24315f = new ArrayList();

    /* renamed from: g, reason: collision with root package name */
    @B
    public final ArrayList f24316g = new ArrayList();

    /* renamed from: j, reason: collision with root package name */
    @B
    @N
    public List<AbstractC20131p> f24319j = Collections.emptyList();

    /* renamed from: k, reason: collision with root package name */
    @B
    @N
    public InterfaceC20112t f24320k = C20115w.f24305a;

    /* renamed from: l, reason: collision with root package name */
    public final Object f24321l = new Object();

    /* renamed from: m, reason: collision with root package name */
    @B
    public boolean f24322m = true;

    /* renamed from: n, reason: collision with root package name */
    @B
    public Config f24323n = null;

    public static final class CameraException extends Exception {
    }

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        public final ArrayList f24328a = new ArrayList();

        public a(LinkedHashSet<CameraInternal> linkedHashSet) {
            Iterator<CameraInternal> it = linkedHashSet.iterator();
            while (it.hasNext()) {
                this.f24328a.add(it.next().c().k());
            }
        }

        public final boolean equals(Object obj) {
            if (obj instanceof a) {
                return this.f24328a.equals(((a) obj).f24328a);
            }
            return false;
        }

        public final int hashCode() {
            return this.f24328a.hashCode() * 53;
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public L0<?> f24329a;

        /* renamed from: b, reason: collision with root package name */
        public L0<?> f24330b;

        public b() {
            throw null;
        }
    }

    public CameraUseCaseAdapter(@N LinkedHashSet<CameraInternal> linkedHashSet, @N E.a aVar, @N InterfaceC20116x interfaceC20116x, @N UseCaseConfigFactory useCaseConfigFactory) {
        CameraInternal next = linkedHashSet.iterator().next();
        this.f24311b = next;
        this.f24314e = new a(new LinkedHashSet(linkedHashSet));
        this.f24317h = aVar;
        this.f24312c = interfaceC20116x;
        this.f24313d = useCaseConfigFactory;
        A0 a02 = new A0(next.l());
        this.f24326q = a02;
        this.f24327r = new B0(next.c(), a02);
    }

    @N
    public static Matrix n(@N Rect rect, @N Size size) {
        z.a("Cannot compute viewport crop rects zero sized sensor rect.", rect.width() > 0 && rect.height() > 0);
        RectF rectF = new RectF(rect);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(new RectF(0.0f, 0.0f, size.getWidth(), size.getHeight()), rectF, Matrix.ScaleToFit.CENTER);
        matrix.invert(matrix);
        return matrix;
    }

    public static boolean u(G0 g02, SessionConfig sessionConfig) {
        Config configD = g02.d();
        s0 s0Var = sessionConfig.f24017f.f23969b;
        if (configD.e().size() != sessionConfig.f24017f.f23969b.e().size()) {
            return true;
        }
        for (Config.a<?> aVar : configD.e()) {
            if (!s0Var.f24173F.containsKey(aVar) || !Objects.equals(s0Var.f(aVar), configD.f(aVar))) {
                return true;
            }
        }
        return false;
    }

    @N
    public static ArrayList w(@N ArrayList arrayList, @N List list) {
        ArrayList arrayList2 = new ArrayList(list);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            N0 n02 = (N0) it.next();
            n02.getClass();
            n02.f23626l = null;
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                AbstractC20131p abstractC20131p = (AbstractC20131p) it2.next();
                abstractC20131p.getClass();
                if (n02.l(0)) {
                    z.g(n02 + " already has effect" + n02.f23626l, n02.f23626l == null);
                    z.b(n02.l(0));
                    n02.f23626l = abstractC20131p;
                    arrayList2.remove(abstractC20131p);
                }
            }
        }
        return arrayList2;
    }

    @Override // androidx.camera.core.InterfaceC20127n
    @N
    public final CameraControl a() {
        return this.f24326q;
    }

    public final void b() {
        synchronized (this.f24321l) {
            try {
                if (!this.f24322m) {
                    this.f24311b.i(this.f24316g);
                    synchronized (this.f24321l) {
                        try {
                            if (this.f24323n != null) {
                                this.f24311b.l().h(this.f24323n);
                            }
                        } finally {
                        }
                    }
                    Iterator it = this.f24316g.iterator();
                    while (it.hasNext()) {
                        ((N0) it.next()).q();
                    }
                    this.f24322m = true;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.InterfaceC20127n
    @N
    public final InterfaceC20142t getCameraInfo() {
        return this.f24327r;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0097  */
    @j.P
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final androidx.camera.core.N0 k(@j.N java.util.LinkedHashSet r9) {
        /*
            r8 = this;
            java.lang.Object r0 = r8.f24321l
            monitor-enter(r0)
            java.lang.Object r1 = r8.f24321l     // Catch: java.lang.Throwable -> L5a
            monitor-enter(r1)     // Catch: java.lang.Throwable -> L5a
            androidx.camera.core.impl.t r2 = r8.f24320k     // Catch: java.lang.Throwable -> L9a
            int r2 = r2.M()     // Catch: java.lang.Throwable -> L9a
            r3 = 0
            r4 = 1
            if (r2 != r4) goto L12
            r2 = r4
            goto L13
        L12:
            r2 = r3
        L13:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L9a
            if (r2 == 0) goto L97
            java.util.Iterator r1 = r9.iterator()     // Catch: java.lang.Throwable -> L5a
            r2 = r3
            r5 = r2
        L1c:
            boolean r6 = r1.hasNext()     // Catch: java.lang.Throwable -> L5a
            if (r6 == 0) goto L34
            java.lang.Object r6 = r1.next()     // Catch: java.lang.Throwable -> L5a
            androidx.camera.core.N0 r6 = (androidx.camera.core.N0) r6     // Catch: java.lang.Throwable -> L5a
            boolean r7 = r6 instanceof androidx.camera.core.v0     // Catch: java.lang.Throwable -> L5a
            if (r7 == 0) goto L2e
            r5 = r4
            goto L1c
        L2e:
            boolean r6 = r6 instanceof androidx.camera.core.C20056e0     // Catch: java.lang.Throwable -> L5a
            if (r6 == 0) goto L1c
            r2 = r4
            goto L1c
        L34:
            if (r2 == 0) goto L5c
            if (r5 != 0) goto L5c
            androidx.camera.core.N0 r9 = r8.f24324o     // Catch: java.lang.Throwable -> L5a
            boolean r1 = r9 instanceof androidx.camera.core.v0     // Catch: java.lang.Throwable -> L5a
            if (r1 == 0) goto L3f
            goto L98
        L3f:
            androidx.camera.core.v0$a r9 = new androidx.camera.core.v0$a     // Catch: java.lang.Throwable -> L5a
            r9.<init>()     // Catch: java.lang.Throwable -> L5a
            java.lang.String r1 = "Preview-Extra"
            androidx.camera.core.impl.Config$a<java.lang.String> r2 = androidx.camera.core.internal.i.f24361B     // Catch: java.lang.Throwable -> L5a
            androidx.camera.core.impl.n0 r3 = r9.f24574a     // Catch: java.lang.Throwable -> L5a
            r3.K(r2, r1)     // Catch: java.lang.Throwable -> L5a
            androidx.camera.core.v0 r9 = r9.e()     // Catch: java.lang.Throwable -> L5a
            androidx.appcompat.app.r r1 = new androidx.appcompat.app.r     // Catch: java.lang.Throwable -> L5a
            r1.<init>()     // Catch: java.lang.Throwable -> L5a
            r9.G(r1)     // Catch: java.lang.Throwable -> L5a
            goto L98
        L5a:
            r9 = move-exception
            goto L9d
        L5c:
            java.util.Iterator r9 = r9.iterator()     // Catch: java.lang.Throwable -> L5a
            r1 = r3
        L61:
            boolean r2 = r9.hasNext()     // Catch: java.lang.Throwable -> L5a
            if (r2 == 0) goto L79
            java.lang.Object r2 = r9.next()     // Catch: java.lang.Throwable -> L5a
            androidx.camera.core.N0 r2 = (androidx.camera.core.N0) r2     // Catch: java.lang.Throwable -> L5a
            boolean r5 = r2 instanceof androidx.camera.core.v0     // Catch: java.lang.Throwable -> L5a
            if (r5 == 0) goto L73
            r3 = r4
            goto L61
        L73:
            boolean r2 = r2 instanceof androidx.camera.core.C20056e0     // Catch: java.lang.Throwable -> L5a
            if (r2 == 0) goto L61
            r1 = r4
            goto L61
        L79:
            if (r3 == 0) goto L97
            if (r1 != 0) goto L97
            androidx.camera.core.N0 r9 = r8.f24324o     // Catch: java.lang.Throwable -> L5a
            boolean r1 = r9 instanceof androidx.camera.core.C20056e0     // Catch: java.lang.Throwable -> L5a
            if (r1 == 0) goto L84
            goto L98
        L84:
            androidx.camera.core.e0$b r9 = new androidx.camera.core.e0$b     // Catch: java.lang.Throwable -> L5a
            r9.<init>()     // Catch: java.lang.Throwable -> L5a
            java.lang.String r1 = "ImageCapture-Extra"
            androidx.camera.core.impl.Config$a<java.lang.String> r2 = androidx.camera.core.internal.i.f24361B     // Catch: java.lang.Throwable -> L5a
            androidx.camera.core.impl.n0 r3 = r9.f23736a     // Catch: java.lang.Throwable -> L5a
            r3.K(r2, r1)     // Catch: java.lang.Throwable -> L5a
            androidx.camera.core.e0 r9 = r9.e()     // Catch: java.lang.Throwable -> L5a
            goto L98
        L97:
            r9 = 0
        L98:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5a
            return r9
        L9a:
            r9 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L9a
            throw r9     // Catch: java.lang.Throwable -> L5a
        L9d:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5a
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.internal.CameraUseCaseAdapter.k(java.util.LinkedHashSet):androidx.camera.core.N0");
    }

    /* JADX WARN: Removed duplicated region for block: B:180:0x02aa A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01de  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.HashMap o(int r24, @j.N androidx.camera.core.impl.B r25, @j.N java.util.ArrayList r26, @j.N java.util.ArrayList r27, @j.N java.util.HashMap r28) {
        /*
            Method dump skipped, instructions count: 1187
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.internal.CameraUseCaseAdapter.o(int, androidx.camera.core.impl.B, java.util.ArrayList, java.util.ArrayList, java.util.HashMap):java.util.HashMap");
    }

    @P
    public final androidx.camera.core.streamsharing.d p(@N LinkedHashSet linkedHashSet, boolean z12) {
        synchronized (this.f24321l) {
            try {
                HashSet hashSetS = s(linkedHashSet, z12);
                if (hashSetS.size() < 2) {
                    return null;
                }
                androidx.camera.core.streamsharing.d dVar = this.f24325p;
                if (dVar != null && dVar.G().equals(hashSetS)) {
                    androidx.camera.core.streamsharing.d dVar2 = this.f24325p;
                    Objects.requireNonNull(dVar2);
                    return dVar2;
                }
                int[] iArr = {1, 2, 4};
                HashSet hashSet = new HashSet();
                Iterator it = hashSetS.iterator();
                while (it.hasNext()) {
                    N0 n02 = (N0) it.next();
                    for (int i12 = 0; i12 < 3; i12++) {
                        int i13 = iArr[i12];
                        if (n02.l(i13)) {
                            if (hashSet.contains(Integer.valueOf(i13))) {
                                return null;
                            }
                            hashSet.add(Integer.valueOf(i13));
                        }
                    }
                }
                return new androidx.camera.core.streamsharing.d(this.f24311b, hashSetS, this.f24313d);
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void q() {
        synchronized (this.f24321l) {
            if (this.f24322m) {
                this.f24311b.g(new ArrayList(this.f24316g));
                synchronized (this.f24321l) {
                    CameraControlInternal cameraControlInternalL = this.f24311b.l();
                    this.f24323n = cameraControlInternalL.c();
                    cameraControlInternalL.e();
                }
                this.f24322m = false;
            }
        }
    }

    public final int r() {
        synchronized (this.f24321l) {
            try {
                return this.f24317h.a() == 2 ? 1 : 0;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @N
    public final HashSet s(@N LinkedHashSet linkedHashSet, boolean z12) {
        int i12;
        HashSet hashSet = new HashSet();
        synchronized (this.f24321l) {
            try {
                Iterator<AbstractC20131p> it = this.f24319j.iterator();
                while (it.hasNext()) {
                    it.next().getClass();
                }
                i12 = z12 ? 3 : 0;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        Iterator it2 = linkedHashSet.iterator();
        while (it2.hasNext()) {
            N0 n02 = (N0) it2.next();
            z.a("Only support one level of sharing for now.", !(n02 instanceof androidx.camera.core.streamsharing.d));
            if (n02.l(i12)) {
                hashSet.add(n02);
            }
        }
        return hashSet;
    }

    @N
    public final List<N0> t() {
        ArrayList arrayList;
        synchronized (this.f24321l) {
            arrayList = new ArrayList(this.f24315f);
        }
        return arrayList;
    }

    public final void v(@N ArrayList arrayList) {
        synchronized (this.f24321l) {
            LinkedHashSet linkedHashSet = new LinkedHashSet(this.f24315f);
            linkedHashSet.removeAll(arrayList);
            x(linkedHashSet, false);
        }
    }

    public final void x(@N LinkedHashSet linkedHashSet, boolean z12) {
        boolean z13;
        boolean z14;
        G0 g02;
        Config configD;
        synchronized (this.f24321l) {
            try {
                N0 n0K = k(linkedHashSet);
                androidx.camera.core.streamsharing.d dVarP = p(linkedHashSet, z12);
                ArrayList arrayList = new ArrayList(linkedHashSet);
                if (n0K != null) {
                    arrayList.add(n0K);
                }
                if (dVarP != null) {
                    arrayList.add(dVarP);
                    arrayList.removeAll(dVarP.G());
                }
                ArrayList arrayList2 = new ArrayList(arrayList);
                arrayList2.removeAll(this.f24316g);
                ArrayList arrayList3 = new ArrayList(arrayList);
                arrayList3.retainAll(this.f24316g);
                ArrayList arrayList4 = new ArrayList(this.f24316g);
                arrayList4.removeAll(arrayList);
                UseCaseConfigFactory useCaseConfigFactoryB = this.f24320k.B();
                UseCaseConfigFactory useCaseConfigFactory = this.f24313d;
                HashMap map = new HashMap();
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    N0 n02 = (N0) it.next();
                    L0<?> l0F = n02.f(false, useCaseConfigFactoryB);
                    androidx.camera.core.streamsharing.d dVar = dVarP;
                    L0<?> l0F2 = n02.f(true, useCaseConfigFactory);
                    b bVar = new b();
                    bVar.f24329a = l0F;
                    bVar.f24330b = l0F2;
                    map.put(n02, bVar);
                    dVarP = dVar;
                }
                androidx.camera.core.streamsharing.d dVar2 = dVarP;
                try {
                    z13 = false;
                    try {
                        HashMap mapO = o(r(), this.f24311b.c(), arrayList2, arrayList3, map);
                        y(arrayList, mapO);
                        ArrayList arrayListW = w(arrayList, this.f24319j);
                        ArrayList arrayList5 = new ArrayList(linkedHashSet);
                        arrayList5.removeAll(arrayList);
                        ArrayList arrayListW2 = w(arrayList5, arrayListW);
                        if (arrayListW2.size() > 0) {
                            arrayListW2.toString();
                            C20140q0.d(5, "CameraUseCaseAdapter");
                        }
                        Iterator it2 = arrayList4.iterator();
                        while (it2.hasNext()) {
                            ((N0) it2.next()).C(this.f24311b);
                        }
                        this.f24311b.g(arrayList4);
                        if (!arrayList4.isEmpty()) {
                            Iterator it3 = arrayList3.iterator();
                            while (it3.hasNext()) {
                                N0 n03 = (N0) it3.next();
                                if (mapO.containsKey(n03) && (configD = (g02 = (G0) mapO.get(n03)).d()) != null && u(g02, n03.f23627m)) {
                                    n03.f23621g = n03.w(configD);
                                }
                            }
                        }
                        Iterator it4 = arrayList2.iterator();
                        while (it4.hasNext()) {
                            N0 n04 = (N0) it4.next();
                            b bVar2 = (b) map.get(n04);
                            Objects.requireNonNull(bVar2);
                            n04.a(this.f24311b, bVar2.f24329a, bVar2.f24330b);
                            G0 g03 = (G0) mapO.get(n04);
                            g03.getClass();
                            n04.f23621g = n04.x(g03);
                        }
                        if (this.f24322m) {
                            this.f24311b.i(arrayList2);
                        }
                        Iterator it5 = arrayList2.iterator();
                        while (it5.hasNext()) {
                            ((N0) it5.next()).q();
                        }
                        this.f24315f.clear();
                        this.f24315f.addAll(linkedHashSet);
                        this.f24316g.clear();
                        this.f24316g.addAll(arrayList);
                        this.f24324o = n0K;
                        this.f24325p = dVar2;
                    } catch (IllegalArgumentException e12) {
                        e = e12;
                        if (!z12) {
                            synchronized (this.f24321l) {
                                z14 = this.f24320k == C20115w.f24305a ? true : z13;
                            }
                            if (z14 && this.f24317h.a() != 2) {
                                x(linkedHashSet, true);
                                return;
                            }
                        }
                        throw e;
                    }
                } catch (IllegalArgumentException e13) {
                    e = e13;
                    z13 = false;
                }
            } finally {
            }
        }
    }

    public final void y(@N ArrayList arrayList, @N HashMap map) {
        synchronized (this.f24321l) {
            try {
                if (this.f24318i != null) {
                    boolean z12 = this.f24311b.c().b() == 0;
                    Rect rectJ = this.f24311b.l().j();
                    Rational rational = this.f24318i.f23648b;
                    int iM2 = this.f24311b.c().m(this.f24318i.f23649c);
                    P0 p02 = this.f24318i;
                    HashMap mapA = l.a(rectJ, z12, rational, iM2, p02.f23647a, p02.f23650d, map);
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        N0 n02 = (N0) it.next();
                        Rect rect = (Rect) mapA.get(n02);
                        rect.getClass();
                        n02.B(rect);
                        Rect rectJ2 = this.f24311b.l().j();
                        G0 g02 = (G0) map.get(n02);
                        g02.getClass();
                        n02.z(n(rectJ2, g02.e()));
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
