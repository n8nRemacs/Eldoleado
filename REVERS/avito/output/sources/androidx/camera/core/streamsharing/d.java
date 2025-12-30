package androidx.camera.core.streamsharing;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Size;
import androidx.annotation.RestrictTo;
import androidx.camera.core.C20056e0;
import androidx.camera.core.F;
import androidx.camera.core.N0;
import androidx.camera.core.impl.B;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.G0;
import androidx.camera.core.impl.InterfaceC20086c0;
import androidx.camera.core.impl.InterfaceC20088d0;
import androidx.camera.core.impl.InterfaceC20106m0;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.n0;
import androidx.camera.core.impl.s0;
import androidx.camera.core.impl.utils.t;
import androidx.camera.core.impl.utils.u;
import androidx.camera.core.processing.C;
import androidx.camera.core.processing.z;
import androidx.camera.core.v0;
import j.K;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: StreamSharing.java */
@X
/* loaded from: classes.dex */
public class d extends N0 {

    /* renamed from: n, reason: collision with root package name */
    @N
    public final f f24541n;

    /* renamed from: o, reason: collision with root package name */
    @N
    public final h f24542o;

    /* renamed from: p, reason: collision with root package name */
    @P
    public C f24543p;

    /* renamed from: q, reason: collision with root package name */
    @P
    public z f24544q;

    /* renamed from: r, reason: collision with root package name */
    @P
    public z f24545r;

    /* renamed from: s, reason: collision with root package name */
    public SessionConfig.b f24546s;

    /* compiled from: StreamSharing.java */
    public interface a {
    }

    public d(@N CameraInternal cameraInternal, @N HashSet hashSet, @N UseCaseConfigFactory useCaseConfigFactory) {
        super(H(hashSet));
        this.f24541n = H(hashSet);
        this.f24542o = new h(cameraInternal, hashSet, useCaseConfigFactory, new c(this));
    }

    public static f H(HashSet hashSet) {
        e eVar = new e();
        Config.a<Integer> aVar = InterfaceC20086c0.f24088e;
        n0 n0Var = eVar.f24547a;
        n0Var.K(aVar, 34);
        n0Var.K(L0.f24006z, UseCaseConfigFactory.CaptureType.f24058f);
        ArrayList arrayList = new ArrayList();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            N0 n02 = (N0) it.next();
            if (n02.f23620f.c(L0.f24006z)) {
                arrayList.add(n02.f23620f.z());
            }
        }
        n0Var.K(f.f24548G, arrayList);
        n0Var.K(InterfaceC20088d0.f24096j, 2);
        return new f(s0.Q(n0Var));
    }

    public final void E() {
        z zVar = this.f24544q;
        if (zVar != null) {
            t.a();
            zVar.d();
            zVar.f24509o = true;
            this.f24544q = null;
        }
        z zVar2 = this.f24545r;
        if (zVar2 != null) {
            t.a();
            zVar2.d();
            zVar2.f24509o = true;
            this.f24545r = null;
        }
        C c12 = this.f24543p;
        if (c12 != null) {
            c12.b();
            this.f24543p = null;
        }
    }

    @N
    @K
    public final SessionConfig F(@N final String str, @N final L0<?> l02, @N final G0 g02) {
        t.a();
        CameraInternal cameraInternalC = c();
        cameraInternalC.getClass();
        Matrix matrix = this.f23624j;
        boolean zJ2 = cameraInternalC.j();
        Size sizeE = g02.e();
        Rect rect = this.f23623i;
        if (rect == null) {
            rect = new Rect(0, 0, sizeE.getWidth(), sizeE.getHeight());
        }
        z zVar = new z(3, 34, g02, matrix, zJ2, rect, h(cameraInternalC, false), -1, m(cameraInternalC));
        this.f24544q = zVar;
        if (this.f23626l != null) {
            throw null;
        }
        this.f24545r = zVar;
        this.f24543p = new C(cameraInternalC, new androidx.camera.core.processing.k(g02.b()));
        z zVar2 = this.f24545r;
        h hVar = this.f24542o;
        hVar.getClass();
        HashMap map = new HashMap();
        Iterator it = hVar.f24551b.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            N0 n02 = (N0) it.next();
            boolean z12 = n02 instanceof v0;
            int iM2 = z12 ? hVar.f24555f.getCameraInfo().m(((InterfaceC20088d0) ((v0) n02).f23620f).D(0)) : 0;
            int i12 = z12 ? 1 : n02 instanceof C20056e0 ? 4 : 2;
            int i13 = n02 instanceof C20056e0 ? 256 : 34;
            Rect rect2 = zVar2.f24498d;
            RectF rectF = u.f24303a;
            map.put(n02, C.d.h(i12, i13, rect2, u.f(new Size(rect2.width(), rect2.height()), iM2), iM2, n02.m(hVar)));
        }
        C.c cVarC = this.f24543p.c(C.b.c(this.f24545r, new ArrayList(map.values())));
        HashMap map2 = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            map2.put((N0) entry.getKey(), cVarC.get(entry.getValue()));
        }
        HashMap map3 = hVar.f24552c;
        map3.clear();
        map3.putAll(map2);
        for (Map.Entry entry2 : map3.entrySet()) {
            N0 n03 = (N0) entry2.getKey();
            z zVar3 = (z) entry2.getValue();
            n03.B(zVar3.f24498d);
            n03.z(zVar3.f24496b);
            n03.f23621g = n03.x(zVar3.f24501g);
            n03.q();
        }
        SessionConfig.b bVarM = SessionConfig.b.m(l02, g02.e());
        z zVar4 = this.f24544q;
        zVar4.getClass();
        t.a();
        zVar4.b();
        androidx.core.util.z.g("Consumer can only be linked once.", !zVar4.f24505k);
        zVar4.f24505k = true;
        bVarM.i(zVar4.f24507m, F.f23562d);
        bVarM.g(hVar.f24556g);
        if (g02.d() != null) {
            bVarM.e(g02.d());
        }
        bVarM.d(new SessionConfig.c() { // from class: androidx.camera.core.streamsharing.b
            @Override // androidx.camera.core.impl.SessionConfig.c
            public final void onError() {
                d dVar = this.f24536a;
                dVar.E();
                String str2 = str;
                if (dVar.k(str2)) {
                    dVar.D(dVar.F(str2, l02, g02));
                    dVar.p();
                    h hVar2 = dVar.f24542o;
                    hVar2.getClass();
                    t.a();
                    Iterator it2 = hVar2.f24551b.iterator();
                    while (it2.hasNext()) {
                        hVar2.n((N0) it2.next());
                    }
                }
            }
        });
        this.f24546s = bVarM;
        return bVarM.k();
    }

    @N
    public final Set<N0> G() {
        return this.f24542o.f24551b;
    }

    @Override // androidx.camera.core.N0
    @P
    public final L0<?> f(boolean z12, @N UseCaseConfigFactory useCaseConfigFactory) {
        f fVar = this.f24541n;
        Config configA = useCaseConfigFactory.a(fVar.z(), 1);
        if (z12) {
            configA = Config.P(configA, fVar.f24549F);
        }
        if (configA == null) {
            return null;
        }
        return ((e) j(configA)).b();
    }

    @Override // androidx.camera.core.N0
    @N
    public final Set<Integer> i() {
        HashSet hashSet = new HashSet();
        hashSet.add(3);
        return hashSet;
    }

    @Override // androidx.camera.core.N0
    @N
    public final L0.a<?, ?, ?> j(@N Config config) {
        return new e(n0.S(config));
    }

    @Override // androidx.camera.core.N0
    public final void r() {
        h hVar = this.f24542o;
        Iterator it = hVar.f24551b.iterator();
        while (it.hasNext()) {
            N0 n02 = (N0) it.next();
            n02.a(hVar, null, n02.f(true, hVar.f24554e));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.util.List] */
    @Override // androidx.camera.core.N0
    @N
    public final L0<?> t(@N B b12, @N L0.a<?, ?, ?> aVar) {
        CameraInternal cameraInternal;
        InterfaceC20106m0 interfaceC20106m0D = aVar.d();
        h hVar = this.f24542o;
        hVar.getClass();
        HashSet hashSet = new HashSet();
        Iterator it = hVar.f24551b.iterator();
        while (true) {
            boolean zHasNext = it.hasNext();
            cameraInternal = hVar.f24555f;
            if (!zHasNext) {
                break;
            }
            N0 n02 = (N0) it.next();
            hashSet.add(n02.n(cameraInternal.c(), null, n02.f(true, hVar.f24554e)));
        }
        ArrayList arrayList = new ArrayList(cameraInternal.c().o(34));
        Rect rectJ = cameraInternal.l().j();
        RectF rectF = u.f24303a;
        new Size(rectJ.width(), rectJ.height());
        Config.a<List<Size>> aVar2 = InterfaceC20088d0.f24102p;
        Iterator it2 = hashSet.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            ?? r52 = (List) ((L0) it2.next()).g(InterfaceC20088d0.f24102p, null);
            if (r52 != 0) {
                arrayList = r52;
                break;
            }
        }
        interfaceC20106m0D.K(aVar2, arrayList);
        Config.a<Integer> aVar3 = L0.f24001u;
        Iterator it3 = hashSet.iterator();
        int iMax = 0;
        while (it3.hasNext()) {
            iMax = Math.max(iMax, ((L0) it3.next()).r());
        }
        interfaceC20106m0D.K(aVar3, Integer.valueOf(iMax));
        return aVar.b();
    }

    @Override // androidx.camera.core.N0
    public final void u() {
        Iterator it = this.f24542o.f24551b.iterator();
        while (it.hasNext()) {
            ((N0) it.next()).u();
        }
    }

    @Override // androidx.camera.core.N0
    public final void v() {
        Iterator it = this.f24542o.f24551b.iterator();
        while (it.hasNext()) {
            ((N0) it.next()).v();
        }
    }

    @Override // androidx.camera.core.N0
    @N
    @RestrictTo
    public final G0 w(@N Config config) {
        this.f24546s.e(config);
        D(this.f24546s.k());
        G0.a aVarF = this.f23621g.f();
        aVarF.d(config);
        return aVarF.a();
    }

    @Override // androidx.camera.core.N0
    @N
    public final G0 x(@N G0 g02) {
        D(F(e(), this.f23620f, g02));
        o();
        return g02;
    }

    @Override // androidx.camera.core.N0
    public final void y() {
        E();
        h hVar = this.f24542o;
        Iterator it = hVar.f24551b.iterator();
        while (it.hasNext()) {
            ((N0) it.next()).C(hVar);
        }
    }
}
