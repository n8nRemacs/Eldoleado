package androidx.camera.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import androidx.annotation.RestrictTo;
import androidx.camera.core.C20056e0;
import androidx.camera.core.C20140q0;
import androidx.camera.core.C20144v;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.InterfaceC20127n;
import androidx.camera.core.P0;
import androidx.camera.core.Q0;
import androidx.camera.core.T;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.InterfaceC20088d0;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.Y;
import androidx.camera.core.impl.n0;
import androidx.camera.core.impl.s0;
import androidx.camera.core.v0;
import androidx.camera.video.O;
import androidx.camera.video.b0;
import androidx.camera.view.z;
import androidx.concurrent.futures.b;
import androidx.core.util.C22807v;
import androidx.view.AbstractC22991Y;
import androidx.view.C23038g0;
import androidx.view.InterfaceC23040h0;
import com.google.common.util.concurrent.D0;
import j.InterfaceC42164u;
import j.K;
import j.N;
import j.P;
import j.X;
import j.k0;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: CameraController.java */
@X
/* renamed from: androidx.camera.view.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20193e {

    /* renamed from: a, reason: collision with root package name */
    public C20144v f25415a;

    /* renamed from: b, reason: collision with root package name */
    public int f25416b;

    /* renamed from: c, reason: collision with root package name */
    @N
    public final v0 f25417c;

    /* renamed from: d, reason: collision with root package name */
    @N
    public final C20056e0 f25418d;

    /* renamed from: e, reason: collision with root package name */
    @N
    public final T f25419e;

    /* renamed from: f, reason: collision with root package name */
    @N
    public final b0<O> f25420f;

    /* renamed from: g, reason: collision with root package name */
    @P
    public InterfaceC20127n f25421g;

    /* renamed from: h, reason: collision with root package name */
    @P
    public w f25422h;

    /* renamed from: i, reason: collision with root package name */
    @P
    public P0 f25423i;

    /* renamed from: j, reason: collision with root package name */
    @P
    public v0.c f25424j;

    /* renamed from: k, reason: collision with root package name */
    public final z f25425k;

    /* renamed from: l, reason: collision with root package name */
    @N
    @k0
    public final m f25426l;

    /* renamed from: m, reason: collision with root package name */
    public boolean f25427m;

    /* renamed from: n, reason: collision with root package name */
    public final boolean f25428n;

    /* renamed from: o, reason: collision with root package name */
    public final C20196h<Q0> f25429o;

    /* renamed from: p, reason: collision with root package name */
    public final C20196h<Integer> f25430p;

    /* renamed from: q, reason: collision with root package name */
    public final C23038g0<Integer> f25431q;

    /* renamed from: r, reason: collision with root package name */
    @N
    public final l<Boolean> f25432r;

    /* renamed from: s, reason: collision with root package name */
    @N
    public final l<Float> f25433s;

    /* renamed from: t, reason: collision with root package name */
    @N
    public final l<Float> f25434t;

    /* renamed from: u, reason: collision with root package name */
    @N
    public final HashSet f25435u;

    /* renamed from: v, reason: collision with root package name */
    @N
    public final D0<Void> f25436v;

    /* compiled from: CameraController.java */
    @X
    /* renamed from: androidx.camera.view.e$a */
    public static class a {
        @InterfaceC42164u
        @N
        public static Context a(@N Context context, @P String str) {
            return context.createAttributionContext(str);
        }

        @InterfaceC42164u
        @P
        public static String b(@N Context context) {
            return context.getAttributionTag();
        }
    }

    /* compiled from: CameraController.java */
    @X
    /* renamed from: androidx.camera.view.e$b */
    public static final class b {

        /* compiled from: CameraController.java */
        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        /* renamed from: androidx.camera.view.e$b$a */
        public @interface a {
        }

        @N
        public final String toString() {
            return "aspect ratio: 0 resolution: null";
        }
    }

    /* compiled from: CameraController.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.camera.view.e$c */
    public @interface c {
    }

    public AbstractC20193e() {
        throw null;
    }

    public AbstractC20193e(@N Context context) {
        String strB;
        int i12 = 0;
        D0 d0L = androidx.camera.core.impl.utils.futures.f.l(androidx.camera.lifecycle.i.c(context), new C20189a(), androidx.camera.core.impl.utils.executor.c.a());
        this.f25415a = C20144v.f24563c;
        this.f25416b = 3;
        new HashMap();
        androidx.camera.video.D d12 = O.f24721d0;
        this.f25427m = true;
        this.f25428n = true;
        this.f25429o = new C20196h<>();
        this.f25430p = new C20196h<>();
        this.f25431q = new C23038g0<>(0);
        this.f25432r = new l<>();
        this.f25433s = new l<>();
        this.f25434t = new l<>();
        this.f25435u = new HashSet();
        Context applicationContext = context.getApplicationContext();
        if (Build.VERSION.SDK_INT >= 30 && (strB = a.b(context)) != null) {
            applicationContext = a.a(applicationContext, strB);
        }
        this.f25417c = new v0.a().e();
        this.f25418d = new C20056e0.b().e();
        Y y12 = new Y(s0.Q(new T.c().f23659a));
        InterfaceC20088d0.v(y12);
        this.f25419e = new T(y12);
        O.h hVar = new O.h();
        hVar.a(d12);
        O o12 = new O(hVar.f24799a.a(), hVar.f24800b, hVar.f24801c);
        b0.c cVar = b0.f24861A;
        b0.b bVar = new b0.b(o12);
        UseCaseConfigFactory.CaptureType captureType = UseCaseConfigFactory.CaptureType.f24057e;
        Config.a<UseCaseConfigFactory.CaptureType> aVar = L0.f24006z;
        n0 n0Var = bVar.f24878a;
        n0Var.K(aVar, captureType);
        this.f25420f = new b0<>(new androidx.camera.video.impl.a(s0.Q(n0Var)));
        this.f25436v = androidx.camera.core.impl.utils.futures.f.l(d0L, new C20190b(this, i12), androidx.camera.core.impl.utils.executor.c.d());
        this.f25425k = new z(applicationContext);
        this.f25426l = new m(this);
    }

    @K
    @SuppressLint({"MissingPermission", "WrongConstant"})
    public final void a(@N v0.c cVar, @N P0 p02) {
        androidx.camera.core.impl.utils.t.a();
        if (this.f25424j != cVar) {
            this.f25424j = cVar;
            this.f25417c.G(cVar);
        }
        this.f25423i = p02;
        z zVar = this.f25425k;
        ScheduledExecutorService scheduledExecutorServiceD = androidx.camera.core.impl.utils.executor.c.d();
        m mVar = this.f25426l;
        synchronized (zVar.f25482a) {
            try {
                if (zVar.f25483b.canDetectOrientation()) {
                    zVar.f25484c.put(mVar, new z.c(mVar, scheduledExecutorServiceD));
                    zVar.f25483b.enable();
                }
            } finally {
            }
        }
        h(null);
    }

    @K
    public final void b() {
        androidx.camera.core.impl.utils.t.a();
        w wVar = this.f25422h;
        if (wVar != null) {
            wVar.b(this.f25417c, this.f25418d, this.f25419e, this.f25420f);
        }
        this.f25417c.G(null);
        this.f25421g = null;
        this.f25424j = null;
        this.f25423i = null;
        z zVar = this.f25425k;
        m mVar = this.f25426l;
        synchronized (zVar.f25482a) {
            try {
                z.c cVar = (z.c) zVar.f25484c.get(mVar);
                if (cVar != null) {
                    cVar.f25489c.set(false);
                    zVar.f25484c.remove(mVar);
                }
                if (zVar.f25484c.isEmpty()) {
                    zVar.f25483b.disable();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @K
    public final boolean c(@N C20144v c20144v) {
        androidx.camera.core.impl.utils.t.a();
        c20144v.getClass();
        w wVar = this.f25422h;
        if (wVar == null) {
            throw new IllegalStateException("Camera not initialized. Please wait for the initialization future to finish. See #getInitializationFuture().");
        }
        try {
            return wVar.c(c20144v);
        } catch (CameraInfoUnavailableException unused) {
            C20140q0.d(5, "CameraController");
            return false;
        }
    }

    public final boolean d() {
        return this.f25421g != null;
    }

    @K
    public final void e(@N C20144v c20144v) {
        int i12 = 2;
        androidx.camera.core.impl.utils.t.a();
        C20144v c20144v2 = this.f25415a;
        if (c20144v2 == c20144v) {
            return;
        }
        this.f25415a = c20144v;
        w wVar = this.f25422h;
        if (wVar == null) {
            return;
        }
        wVar.b(this.f25417c, this.f25418d, this.f25419e, this.f25420f);
        h(new t(i12, (C20197i) this, c20144v2));
    }

    @N
    @K
    public final D0<Void> f(float f12) {
        androidx.camera.core.impl.utils.t.a();
        if (d()) {
            return this.f25421g.a().b(f12);
        }
        return this.f25434t.a(Float.valueOf(f12));
    }

    @P
    public abstract InterfaceC20127n g();

    public final void h(@P Runnable runnable) {
        try {
            this.f25421g = g();
            if (!d()) {
                C20140q0.d(3, "CameraController");
                return;
            }
            AbstractC22991Y abstractC22991YE = this.f25421g.getCameraInfo().e();
            final C20196h<Q0> c20196h = this.f25429o;
            AbstractC22991Y abstractC22991Y = c20196h.f25438b;
            if (abstractC22991Y != null) {
                c20196h.b(abstractC22991Y);
            }
            c20196h.f25438b = abstractC22991YE;
            c20196h.a(abstractC22991YE, new InterfaceC23040h0() { // from class: androidx.camera.view.g
                @Override // androidx.view.InterfaceC23040h0
                public final void onChanged(Object obj) {
                    c20196h.setValue(obj);
                }
            });
            AbstractC22991Y abstractC22991YH = this.f25421g.getCameraInfo().h();
            final C20196h<Integer> c20196h2 = this.f25430p;
            AbstractC22991Y abstractC22991Y2 = c20196h2.f25438b;
            if (abstractC22991Y2 != null) {
                c20196h2.b(abstractC22991Y2);
            }
            c20196h2.f25438b = abstractC22991YH;
            c20196h2.a(abstractC22991YH, new InterfaceC23040h0() { // from class: androidx.camera.view.g
                @Override // androidx.view.InterfaceC23040h0
                public final void onChanged(Object obj) {
                    c20196h2.setValue(obj);
                }
            });
            l<Boolean> lVar = this.f25432r;
            lVar.getClass();
            androidx.camera.core.impl.utils.t.a();
            C22807v<b.a<Void>, Boolean> c22807v = lVar.f25444a;
            if (c22807v != null) {
                Boolean bool = c22807v.f44896b;
                boolean zBooleanValue = bool.booleanValue();
                androidx.camera.core.impl.utils.t.a();
                D0<Void> d0A = !d() ? lVar.a(bool) : this.f25421g.a().f(zBooleanValue);
                b.a<Void> aVar = lVar.f25444a.f44895a;
                Objects.requireNonNull(aVar);
                androidx.camera.core.impl.utils.futures.f.i(aVar, d0A);
                lVar.f25444a = null;
            }
            l<Float> lVar2 = this.f25433s;
            lVar2.getClass();
            androidx.camera.core.impl.utils.t.a();
            C22807v<b.a<Void>, Float> c22807v2 = lVar2.f25444a;
            if (c22807v2 != null) {
                Float f12 = c22807v2.f44896b;
                float fFloatValue = f12.floatValue();
                androidx.camera.core.impl.utils.t.a();
                D0<Void> d0A2 = !d() ? lVar2.a(f12) : this.f25421g.a().a(fFloatValue);
                b.a<Void> aVar2 = lVar2.f25444a.f44895a;
                Objects.requireNonNull(aVar2);
                androidx.camera.core.impl.utils.futures.f.i(aVar2, d0A2);
                lVar2.f25444a = null;
            }
            l<Float> lVar3 = this.f25434t;
            lVar3.getClass();
            androidx.camera.core.impl.utils.t.a();
            C22807v<b.a<Void>, Float> c22807v3 = lVar3.f25444a;
            if (c22807v3 != null) {
                D0<Void> d0F = f(c22807v3.f44896b.floatValue());
                b.a<Void> aVar3 = lVar3.f25444a.f44895a;
                Objects.requireNonNull(aVar3);
                androidx.camera.core.impl.utils.futures.f.i(aVar3, d0F);
                lVar3.f25444a = null;
            }
        } catch (RuntimeException e12) {
            if (runnable != null) {
                runnable.run();
            }
            throw e12;
        }
    }
}
