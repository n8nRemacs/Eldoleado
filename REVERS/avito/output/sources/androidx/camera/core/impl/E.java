package androidx.camera.core.impl;

import E.a;
import androidx.camera.core.C20140q0;
import androidx.camera.core.InterfaceC20127n;
import androidx.camera.core.impl.CameraInternal;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;

/* compiled from: CameraStateRegistry.java */
@j.X
/* loaded from: classes.dex */
public final class E implements a.b {

    /* renamed from: a, reason: collision with root package name */
    public final StringBuilder f23940a = new StringBuilder();

    /* renamed from: b, reason: collision with root package name */
    public final Object f23941b;

    /* renamed from: c, reason: collision with root package name */
    public int f23942c;

    /* renamed from: d, reason: collision with root package name */
    @j.B
    public final C.a f23943d;

    /* renamed from: e, reason: collision with root package name */
    @j.B
    public final HashMap f23944e;

    /* renamed from: f, reason: collision with root package name */
    @j.B
    public int f23945f;

    /* compiled from: CameraStateRegistry.java */
    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public CameraInternal.State f23946a = null;

        /* renamed from: b, reason: collision with root package name */
        public final Executor f23947b;

        /* renamed from: c, reason: collision with root package name */
        public final b f23948c;

        /* renamed from: d, reason: collision with root package name */
        public final c f23949d;

        public a(@j.N Executor executor, @j.N b bVar, @j.N c cVar) {
            this.f23947b = executor;
            this.f23948c = bVar;
            this.f23949d = cVar;
        }
    }

    /* compiled from: CameraStateRegistry.java */
    public interface b {
        void a();
    }

    /* compiled from: CameraStateRegistry.java */
    public interface c {
        void a();
    }

    public E(@j.N C.a aVar) {
        Object obj = new Object();
        this.f23941b = obj;
        this.f23944e = new HashMap();
        this.f23942c = 1;
        synchronized (obj) {
            this.f23943d = aVar;
            this.f23945f = this.f23942c;
        }
    }

    @Override // E.a.b
    public final void a(int i12, int i13) {
        synchronized (this.f23941b) {
            boolean z12 = true;
            this.f23942c = i13 == 2 ? 2 : 1;
            boolean z13 = i12 != 2 && i13 == 2;
            if (i12 != 2 || i13 == 2) {
                z12 = false;
            }
            if (z13 || z12) {
                e();
            }
        }
    }

    @j.B
    @j.P
    public final a b(@j.N String str) {
        HashMap map = this.f23944e;
        for (InterfaceC20127n interfaceC20127n : map.keySet()) {
            if (str.equals(((B) interfaceC20127n.getCameraInfo()).k())) {
                return (a) map.get(interfaceC20127n);
            }
        }
        return null;
    }

    public final boolean c() {
        synchronized (this.f23941b) {
            try {
                Iterator it = this.f23944e.entrySet().iterator();
                while (it.hasNext()) {
                    if (((a) ((Map.Entry) it.next()).getValue()).f23946a == CameraInternal.State.CLOSING) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void d(@j.N androidx.camera.core.InterfaceC20127n r8, @j.N androidx.camera.core.impl.CameraInternal.State r9, boolean r10) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.impl.E.d(androidx.camera.core.n, androidx.camera.core.impl.CameraInternal$State, boolean):void");
    }

    @j.B
    @j.l0
    public final void e() {
        boolean zD2 = C20140q0.d(3, "CameraStateRegistry");
        StringBuilder sb2 = this.f23940a;
        if (zD2) {
            sb2.setLength(0);
            sb2.append("Recalculating open cameras:\n");
            sb2.append(String.format(Locale.US, "%-45s%-22s\n", "Camera", "State"));
            sb2.append("-------------------------------------------------------------------\n");
        }
        int i12 = 0;
        for (Map.Entry entry : this.f23944e.entrySet()) {
            if (C20140q0.d(3, "CameraStateRegistry")) {
                sb2.append(String.format(Locale.US, "%-45s%-22s\n", ((InterfaceC20127n) entry.getKey()).toString(), ((a) entry.getValue()).f23946a != null ? ((a) entry.getValue()).f23946a.toString() : GrsBaseInfo.CountryCodeSource.UNKNOWN));
            }
            CameraInternal.State state = ((a) entry.getValue()).f23946a;
            if (state != null && state.f23918b) {
                i12++;
            }
        }
        if (C20140q0.d(3, "CameraStateRegistry")) {
            sb2.append("-------------------------------------------------------------------\n");
            Locale locale = Locale.US;
            sb2.append(androidx.appcompat.app.r.l(i12, this.f23942c, "Open count: ", " (Max allowed: ", ")"));
            C20140q0.d(3, "CameraStateRegistry");
        }
        this.f23945f = Math.max(this.f23942c - i12, 0);
    }

    public final void f(@j.N InterfaceC20127n interfaceC20127n, @j.N Executor executor, @j.N b bVar, @j.N c cVar) {
        synchronized (this.f23941b) {
            androidx.core.util.z.g("Camera is already registered: " + interfaceC20127n, !this.f23944e.containsKey(interfaceC20127n));
            this.f23944e.put(interfaceC20127n, new a(executor, bVar, cVar));
        }
    }

    public final boolean g(@j.N InterfaceC20127n interfaceC20127n) {
        boolean z12;
        synchronized (this.f23941b) {
            try {
                a aVar = (a) this.f23944e.get(interfaceC20127n);
                androidx.core.util.z.f(aVar, "Camera must first be registered with registerCamera()");
                z12 = true;
                if (C20140q0.d(3, "CameraStateRegistry")) {
                    this.f23940a.setLength(0);
                    StringBuilder sb2 = this.f23940a;
                    Locale locale = Locale.US;
                    Integer numValueOf = Integer.valueOf(this.f23945f);
                    CameraInternal.State state = aVar.f23946a;
                    sb2.append(String.format(locale, "tryOpenCamera(%s) [Available Cameras: %d, Already Open: %b (Previous state: %s)]", interfaceC20127n, numValueOf, Boolean.valueOf(state != null && state.f23918b), aVar.f23946a));
                }
                if (this.f23945f > 0) {
                    aVar.f23946a = CameraInternal.State.OPENING;
                } else {
                    CameraInternal.State state2 = aVar.f23946a;
                    if (state2 != null && state2.f23918b) {
                        aVar.f23946a = CameraInternal.State.OPENING;
                    } else {
                        z12 = false;
                    }
                }
                if (C20140q0.d(3, "CameraStateRegistry")) {
                    StringBuilder sb3 = this.f23940a;
                    Locale locale2 = Locale.US;
                    sb3.append(" --> ".concat(z12 ? "SUCCESS" : "FAIL"));
                    C20140q0.d(3, "CameraStateRegistry");
                }
                if (z12) {
                    e();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return z12;
    }

    public final boolean h(@j.N String str, @j.P String str2) {
        synchronized (this.f23941b) {
            try {
                boolean z12 = true;
                if (this.f23943d.f1850e != 2) {
                    return true;
                }
                CameraInternal.State state = null;
                CameraInternal.State state2 = b(str) != null ? b(str).f23946a : null;
                if (str2 != null && b(str2) != null) {
                    state = b(str2).f23946a;
                }
                CameraInternal.State state3 = CameraInternal.State.OPEN;
                boolean z13 = state3.equals(state2) || CameraInternal.State.CONFIGURED.equals(state2);
                boolean z14 = state3.equals(state) || CameraInternal.State.CONFIGURED.equals(state);
                if (!z13 || !z14) {
                    z12 = false;
                }
                return z12;
            } finally {
            }
        }
    }
}
