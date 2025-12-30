package androidx.camera.core;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Size;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.G0;
import androidx.camera.core.impl.InterfaceC20088d0;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import j.InterfaceC42153i;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/* compiled from: UseCase.java */
@j.X
/* loaded from: classes.dex */
public abstract class N0 {

    /* renamed from: d, reason: collision with root package name */
    @j.P
    public androidx.camera.core.impl.L0<?> f23618d;

    /* renamed from: e, reason: collision with root package name */
    @j.N
    public androidx.camera.core.impl.L0<?> f23619e;

    /* renamed from: f, reason: collision with root package name */
    @j.N
    public androidx.camera.core.impl.L0<?> f23620f;

    /* renamed from: g, reason: collision with root package name */
    public androidx.camera.core.impl.G0 f23621g;

    /* renamed from: h, reason: collision with root package name */
    @j.P
    public androidx.camera.core.impl.L0<?> f23622h;

    /* renamed from: i, reason: collision with root package name */
    @j.P
    public Rect f23623i;

    /* renamed from: k, reason: collision with root package name */
    @j.B
    public CameraInternal f23625k;

    /* renamed from: l, reason: collision with root package name */
    @j.P
    public AbstractC20131p f23626l;

    /* renamed from: a, reason: collision with root package name */
    public final HashSet f23615a = new HashSet();

    /* renamed from: b, reason: collision with root package name */
    public final Object f23616b = new Object();

    /* renamed from: c, reason: collision with root package name */
    public c f23617c = c.f23630c;

    /* renamed from: j, reason: collision with root package name */
    @j.N
    public Matrix f23624j = new Matrix();

    /* renamed from: m, reason: collision with root package name */
    @j.N
    public SessionConfig f23627m = SessionConfig.a();

    /* compiled from: UseCase.java */
    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f23628a;

        static {
            int[] iArr = new int[c.values().length];
            f23628a = iArr;
            try {
                iArr[1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f23628a[0] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* compiled from: UseCase.java */
    @RestrictTo
    public interface b {
        void a();

        void j5();
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: UseCase.java */
    public static final class c {

        /* renamed from: b, reason: collision with root package name */
        public static final c f23629b;

        /* renamed from: c, reason: collision with root package name */
        public static final c f23630c;

        /* renamed from: d, reason: collision with root package name */
        public static final /* synthetic */ c[] f23631d;

        static {
            c cVar = new c("ACTIVE", 0);
            f23629b = cVar;
            c cVar2 = new c("INACTIVE", 1);
            f23630c = cVar2;
            f23631d = new c[]{cVar, cVar2};
        }

        public c() {
            throw null;
        }

        public static c valueOf(String str) {
            return (c) Enum.valueOf(c.class, str);
        }

        public static c[] values() {
            return (c[]) f23631d.clone();
        }
    }

    /* compiled from: UseCase.java */
    @RestrictTo
    public interface d {
        void b(@j.N N0 n02);

        void k(@j.N N0 n02);

        void n(@j.N N0 n02);

        void o(@j.N N0 n02);
    }

    @RestrictTo
    public N0(@j.N androidx.camera.core.impl.L0<?> l02) {
        this.f23619e = l02;
        this.f23620f = l02;
    }

    @RestrictTo
    public final boolean A(int i12) {
        Size sizeK;
        int iD2 = ((InterfaceC20088d0) this.f23620f).D(-1);
        if (iD2 != -1 && iD2 == i12) {
            return false;
        }
        L0.a<?, ?, ?> aVarJ = j(this.f23619e);
        InterfaceC20088d0 interfaceC20088d0 = (InterfaceC20088d0) aVarJ.b();
        int iD3 = interfaceC20088d0.D(-1);
        if (iD3 == -1 || iD3 != i12) {
            ((InterfaceC20088d0.a) aVarJ).c(i12);
        }
        if (iD3 != -1 && i12 != -1 && iD3 != i12) {
            if (Math.abs(androidx.camera.core.impl.utils.e.b(i12) - androidx.camera.core.impl.utils.e.b(iD3)) % 180 == 90 && (sizeK = interfaceC20088d0.k()) != null) {
                ((InterfaceC20088d0.a) aVarJ).a(new Size(sizeK.getHeight(), sizeK.getWidth()));
            }
        }
        this.f23619e = aVarJ.b();
        CameraInternal cameraInternalC = c();
        if (cameraInternalC == null) {
            this.f23620f = this.f23619e;
            return true;
        }
        this.f23620f = n(cameraInternalC.c(), this.f23618d, this.f23622h);
        return true;
    }

    @InterfaceC42153i
    @RestrictTo
    public void B(@j.N Rect rect) {
        this.f23623i = rect;
    }

    @RestrictTo
    public final void C(@j.N CameraInternal cameraInternal) {
        y();
        b bVarN = this.f23620f.n();
        if (bVarN != null) {
            bVarN.j5();
        }
        synchronized (this.f23616b) {
            androidx.core.util.z.b(cameraInternal == this.f23625k);
            this.f23615a.remove(this.f23625k);
            this.f23625k = null;
        }
        this.f23621g = null;
        this.f23623i = null;
        this.f23620f = this.f23619e;
        this.f23618d = null;
        this.f23622h = null;
    }

    @RestrictTo
    public final void D(@j.N SessionConfig sessionConfig) {
        this.f23627m = sessionConfig;
        for (DeferrableSurface deferrableSurface : sessionConfig.b()) {
            if (deferrableSurface.f23938j == null) {
                deferrableSurface.f23938j = getClass();
            }
        }
    }

    @SuppressLint({"WrongConstant"})
    @RestrictTo
    public final void a(@j.N CameraInternal cameraInternal, @j.P androidx.camera.core.impl.L0<?> l02, @j.P androidx.camera.core.impl.L0<?> l03) {
        synchronized (this.f23616b) {
            this.f23625k = cameraInternal;
            this.f23615a.add(cameraInternal);
        }
        this.f23618d = l02;
        this.f23622h = l03;
        androidx.camera.core.impl.L0<?> l0N = n(cameraInternal.c(), this.f23618d, this.f23622h);
        this.f23620f = l0N;
        b bVarN = l0N.n();
        if (bVarN != null) {
            cameraInternal.c();
            bVarN.a();
        }
        r();
    }

    @RestrictTo
    @j.P
    public final Size b() {
        androidx.camera.core.impl.G0 g02 = this.f23621g;
        if (g02 != null) {
            return g02.e();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public final CameraInternal c() {
        CameraInternal cameraInternal;
        synchronized (this.f23616b) {
            cameraInternal = this.f23625k;
        }
        return cameraInternal;
    }

    @j.N
    @RestrictTo
    public final CameraControlInternal d() {
        synchronized (this.f23616b) {
            try {
                CameraInternal cameraInternal = this.f23625k;
                if (cameraInternal == null) {
                    return CameraControlInternal.f23908a;
                }
                return cameraInternal.l();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @j.N
    @RestrictTo
    public final String e() {
        CameraInternal cameraInternalC = c();
        androidx.core.util.z.f(cameraInternalC, "No camera attached to use case: " + this);
        return cameraInternalC.c().k();
    }

    @RestrictTo
    @j.P
    public abstract androidx.camera.core.impl.L0<?> f(boolean z12, @j.N UseCaseConfigFactory useCaseConfigFactory);

    @j.N
    @RestrictTo
    public final String g() {
        String strJ = this.f23620f.j("<UnknownUseCase-" + hashCode() + ">");
        Objects.requireNonNull(strJ);
        return strJ;
    }

    @j.F
    @RestrictTo
    public int h(@j.N CameraInternal cameraInternal, boolean z12) {
        int iM2 = cameraInternal.c().m(((InterfaceC20088d0) this.f23620f).D(0));
        if (cameraInternal.j() || !z12) {
            return iM2;
        }
        RectF rectF = androidx.camera.core.impl.utils.u.f24303a;
        return (((-iM2) % 360) + 360) % 360;
    }

    @j.N
    @RestrictTo
    public Set<Integer> i() {
        return Collections.emptySet();
    }

    @j.N
    @RestrictTo
    public abstract L0.a<?, ?, ?> j(@j.N Config config);

    @RestrictTo
    public final boolean k(@j.N String str) {
        if (c() == null) {
            return false;
        }
        return Objects.equals(str, e());
    }

    @RestrictTo
    public final boolean l(int i12) {
        Iterator<Integer> it = i().iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            if ((i12 & iIntValue) == iIntValue) {
                return true;
            }
        }
        return false;
    }

    @RestrictTo
    public final boolean m(@j.N CameraInternal cameraInternal) {
        int iF2 = ((InterfaceC20088d0) this.f23620f).F();
        if (iF2 == 0) {
            return false;
        }
        if (iF2 == 1) {
            return true;
        }
        if (iF2 == 2) {
            return cameraInternal.d();
        }
        throw new AssertionError(AK.c.g(iF2, "Unknown mirrorMode: "));
    }

    @j.N
    @RestrictTo
    public final androidx.camera.core.impl.L0<?> n(@j.N androidx.camera.core.impl.B b12, @j.P androidx.camera.core.impl.L0<?> l02, @j.P androidx.camera.core.impl.L0<?> l03) {
        androidx.camera.core.impl.n0 n0VarR;
        if (l03 != null) {
            n0VarR = androidx.camera.core.impl.n0.S(l03);
            n0VarR.f24173F.remove(androidx.camera.core.internal.i.f24361B);
        } else {
            n0VarR = androidx.camera.core.impl.n0.R();
        }
        boolean zC2 = this.f23619e.c(InterfaceC20088d0.f24093g);
        TreeMap<Config.a<?>, Map<Config.OptionPriority, Object>> treeMap = n0VarR.f24173F;
        if (zC2 || this.f23619e.c(InterfaceC20088d0.f24097k)) {
            Config.a<G.c> aVar = InterfaceC20088d0.f24101o;
            if (treeMap.containsKey(aVar)) {
                treeMap.remove(aVar);
            }
        }
        androidx.camera.core.impl.L0<?> l04 = this.f23619e;
        Config.a<G.c> aVar2 = InterfaceC20088d0.f24101o;
        if (l04.c(aVar2)) {
            Config.a<Size> aVar3 = InterfaceC20088d0.f24099m;
            if (treeMap.containsKey(aVar3) && ((G.c) this.f23619e.f(aVar2)).f6126b != null) {
                treeMap.remove(aVar3);
            }
        }
        Iterator<Config.a<?>> it = this.f23619e.e().iterator();
        while (it.hasNext()) {
            Config.E(n0VarR, n0VarR, this.f23619e, it.next());
        }
        if (l02 != null) {
            for (Config.a<?> aVar4 : l02.e()) {
                if (!aVar4.c().equals(androidx.camera.core.internal.i.f24361B.c())) {
                    Config.E(n0VarR, n0VarR, l02, aVar4);
                }
            }
        }
        if (treeMap.containsKey(InterfaceC20088d0.f24097k)) {
            Config.a<Integer> aVar5 = InterfaceC20088d0.f24093g;
            if (treeMap.containsKey(aVar5)) {
                treeMap.remove(aVar5);
            }
        }
        Config.a<G.c> aVar6 = InterfaceC20088d0.f24101o;
        if (treeMap.containsKey(aVar6)) {
            ((G.c) n0VarR.f(aVar6)).getClass();
        }
        return t(b12, j(n0VarR));
    }

    @RestrictTo
    public final void o() {
        this.f23617c = c.f23629b;
        q();
    }

    @RestrictTo
    public final void p() {
        Iterator it = this.f23615a.iterator();
        while (it.hasNext()) {
            ((d) it.next()).n(this);
        }
    }

    @RestrictTo
    public final void q() {
        int iOrdinal = this.f23617c.ordinal();
        HashSet hashSet = this.f23615a;
        if (iOrdinal == 0) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                ((d) it.next()).k(this);
            }
        } else {
            if (iOrdinal != 1) {
                return;
            }
            Iterator it2 = hashSet.iterator();
            while (it2.hasNext()) {
                ((d) it2.next()).o(this);
            }
        }
    }

    @j.N
    @RestrictTo
    public androidx.camera.core.impl.L0<?> t(@j.N androidx.camera.core.impl.B b12, @j.N L0.a<?, ?, ?> aVar) {
        return aVar.b();
    }

    @j.N
    @RestrictTo
    public androidx.camera.core.impl.G0 w(@j.N Config config) {
        androidx.camera.core.impl.G0 g02 = this.f23621g;
        if (g02 == null) {
            throw new UnsupportedOperationException("Attempt to update the implementation options for a use case without attached stream specifications.");
        }
        G0.a aVarF = g02.f();
        aVarF.d(config);
        return aVarF.a();
    }

    @InterfaceC42153i
    @RestrictTo
    public void z(@j.N Matrix matrix) {
        this.f23624j = new Matrix(matrix);
    }

    @RestrictTo
    public void r() {
    }

    @RestrictTo
    public void s() {
    }

    @InterfaceC42153i
    @RestrictTo
    public void u() {
    }

    @RestrictTo
    public void v() {
    }

    @RestrictTo
    public void y() {
    }

    @j.N
    @RestrictTo
    public androidx.camera.core.impl.G0 x(@j.N androidx.camera.core.impl.G0 g02) {
        return g02;
    }
}
