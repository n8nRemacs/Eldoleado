package androidx.camera.core.streamsharing;

import androidx.camera.core.C20056e0;
import androidx.camera.core.N0;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.B;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.q0;
import androidx.camera.core.impl.utils.t;
import androidx.camera.core.processing.z;
import j.K;
import j.N;
import j.P;
import j.X;
import j.k0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: VirtualCamera.java */
@X
/* loaded from: classes.dex */
class h implements CameraInternal {

    /* renamed from: b, reason: collision with root package name */
    @N
    public final HashSet f24551b;

    /* renamed from: e, reason: collision with root package name */
    @N
    public final UseCaseConfigFactory f24554e;

    /* renamed from: f, reason: collision with root package name */
    @N
    public final CameraInternal f24555f;

    /* renamed from: h, reason: collision with root package name */
    @N
    public final j f24557h;

    /* renamed from: c, reason: collision with root package name */
    @N
    public final HashMap f24552c = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    @N
    public final HashMap f24553d = new HashMap();

    /* renamed from: g, reason: collision with root package name */
    @N
    public final AbstractC20109p f24556g = new g(this);

    public h(@N CameraInternal cameraInternal, @N HashSet hashSet, @N UseCaseConfigFactory useCaseConfigFactory, @N c cVar) {
        this.f24555f = cameraInternal;
        this.f24554e = useCaseConfigFactory;
        this.f24551b = hashSet;
        this.f24557h = new j(cameraInternal.l(), cVar);
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            this.f24553d.put((N0) it.next(), Boolean.FALSE);
        }
    }

    public static void p(@N z zVar, @N DeferrableSurface deferrableSurface, @N SessionConfig sessionConfig) {
        zVar.e();
        try {
            zVar.g(deferrableSurface);
        } catch (DeferrableSurface.SurfaceClosedException unused) {
            Iterator<SessionConfig.c> it = sessionConfig.f24016e.iterator();
            while (it.hasNext()) {
                it.next().onError();
            }
        }
    }

    @k0
    @P
    public static DeferrableSurface q(@N N0 n02) {
        List<DeferrableSurface> listB = n02 instanceof C20056e0 ? n02.f23627m.b() : Collections.unmodifiableList(n02.f23627m.f24017f.f23968a);
        androidx.core.util.z.g(null, listB.size() <= 1);
        if (listB.size() == 1) {
            return listB.get(0);
        }
        return null;
    }

    @Override // androidx.camera.core.N0.d
    @K
    public final void b(@N N0 n02) {
        t.a();
        if (s(n02)) {
            z zVarR = r(n02);
            DeferrableSurface deferrableSurfaceQ = q(n02);
            if (deferrableSurfaceQ != null) {
                p(zVarR, deferrableSurfaceQ, n02.f23627m);
                return;
            }
            t.a();
            zVarR.b();
            zVarR.d();
        }
    }

    @Override // androidx.camera.core.impl.CameraInternal
    @N
    public final B c() {
        return this.f24555f.c();
    }

    @Override // androidx.camera.core.impl.CameraInternal
    @N
    public final q0<CameraInternal.State> f() {
        return this.f24555f.f();
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public final void g(@N ArrayList arrayList) {
        throw new UnsupportedOperationException("Operation not supported by VirtualCamera.");
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public final void i(@N ArrayList arrayList) {
        throw new UnsupportedOperationException("Operation not supported by VirtualCamera.");
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public final boolean j() {
        return false;
    }

    @Override // androidx.camera.core.N0.d
    @K
    public final void k(@N N0 n02) {
        t.a();
        if (s(n02)) {
            return;
        }
        this.f24553d.put(n02, Boolean.TRUE);
        DeferrableSurface deferrableSurfaceQ = q(n02);
        if (deferrableSurfaceQ != null) {
            p(r(n02), deferrableSurfaceQ, n02.f23627m);
        }
    }

    @Override // androidx.camera.core.impl.CameraInternal
    @N
    public final CameraControlInternal l() {
        return this.f24557h;
    }

    @Override // androidx.camera.core.N0.d
    @K
    public final void n(@N N0 n02) {
        DeferrableSurface deferrableSurfaceQ;
        t.a();
        z zVarR = r(n02);
        zVarR.e();
        if (s(n02) && (deferrableSurfaceQ = q(n02)) != null) {
            p(zVarR, deferrableSurfaceQ, n02.f23627m);
        }
    }

    @Override // androidx.camera.core.N0.d
    @K
    public final void o(@N N0 n02) {
        t.a();
        if (s(n02)) {
            this.f24553d.put(n02, Boolean.FALSE);
            z zVarR = r(n02);
            t.a();
            zVarR.b();
            zVarR.d();
        }
    }

    @N
    public final z r(@N N0 n02) {
        z zVar = (z) this.f24552c.get(n02);
        Objects.requireNonNull(zVar);
        return zVar;
    }

    public final boolean s(@N N0 n02) {
        Boolean bool = (Boolean) this.f24553d.get(n02);
        Objects.requireNonNull(bool);
        return bool.booleanValue();
    }
}
