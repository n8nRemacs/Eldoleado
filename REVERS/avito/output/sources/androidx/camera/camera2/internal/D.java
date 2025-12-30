package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.camera2.internal.C20026p;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.concurrent.futures.b;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: Camera2CapturePipeline.java */
@j.X
/* loaded from: classes.dex */
class D {

    /* renamed from: h, reason: collision with root package name */
    public static final Set<CameraCaptureMetaData.AfState> f22931h = Collections.unmodifiableSet(EnumSet.of(CameraCaptureMetaData.AfState.f23892e, CameraCaptureMetaData.AfState.f23893f, CameraCaptureMetaData.AfState.f23894g, CameraCaptureMetaData.AfState.f23895h));

    /* renamed from: i, reason: collision with root package name */
    public static final Set<CameraCaptureMetaData.AwbState> f22932i = Collections.unmodifiableSet(EnumSet.of(CameraCaptureMetaData.AwbState.f23900e, CameraCaptureMetaData.AwbState.f23897b));

    /* renamed from: j, reason: collision with root package name */
    public static final Set<CameraCaptureMetaData.AeState> f22933j;

    /* renamed from: k, reason: collision with root package name */
    public static final Set<CameraCaptureMetaData.AeState> f22934k;

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final C20026p f22935a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final androidx.camera.camera2.internal.compat.workaround.u f22936b;

    /* renamed from: c, reason: collision with root package name */
    public final boolean f22937c;

    /* renamed from: d, reason: collision with root package name */
    @j.N
    public final androidx.camera.core.impl.w0 f22938d;

    /* renamed from: e, reason: collision with root package name */
    @j.N
    public final Executor f22939e;

    /* renamed from: f, reason: collision with root package name */
    public final boolean f22940f;

    /* renamed from: g, reason: collision with root package name */
    public int f22941g = 1;

    /* compiled from: Camera2CapturePipeline.java */
    public static class a implements d {

        /* renamed from: a, reason: collision with root package name */
        public final C20026p f22942a;

        /* renamed from: b, reason: collision with root package name */
        public final androidx.camera.camera2.internal.compat.workaround.n f22943b;

        /* renamed from: c, reason: collision with root package name */
        public final int f22944c;

        /* renamed from: d, reason: collision with root package name */
        public boolean f22945d = false;

        public a(@j.N C20026p c20026p, int i12, @j.N androidx.camera.camera2.internal.compat.workaround.n nVar) {
            this.f22942a = c20026p;
            this.f22944c = i12;
            this.f22943b = nVar;
        }

        @Override // androidx.camera.camera2.internal.D.d
        @j.N
        public final com.google.common.util.concurrent.D0<Boolean> a(@j.P TotalCaptureResult totalCaptureResult) {
            if (!D.b(totalCaptureResult, this.f22944c)) {
                return androidx.camera.core.impl.utils.futures.f.g(Boolean.FALSE);
            }
            C20140q0.d(3, "Camera2CapturePipeline");
            this.f22945d = true;
            androidx.camera.core.impl.utils.futures.d dVarA = androidx.camera.core.impl.utils.futures.d.a(androidx.concurrent.futures.b.a(new C(this, 0)));
            J j12 = new J(1);
            Executor executorA = androidx.camera.core.impl.utils.executor.c.a();
            dVarA.getClass();
            return (androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.l(dVarA, j12, executorA);
        }

        @Override // androidx.camera.camera2.internal.D.d
        public final boolean b() {
            return this.f22944c == 0;
        }

        @Override // androidx.camera.camera2.internal.D.d
        public final void c() {
            if (this.f22945d) {
                C20140q0.d(3, "Camera2CapturePipeline");
                this.f22942a.f23394h.a(false, true);
                this.f22943b.f23262b = false;
            }
        }
    }

    /* compiled from: Camera2CapturePipeline.java */
    public static class b implements d {

        /* renamed from: a, reason: collision with root package name */
        public final C20026p f22946a;

        /* renamed from: b, reason: collision with root package name */
        public boolean f22947b = false;

        public b(@j.N C20026p c20026p) {
            this.f22946a = c20026p;
        }

        @Override // androidx.camera.camera2.internal.D.d
        @j.N
        public final com.google.common.util.concurrent.D0<Boolean> a(@j.P TotalCaptureResult totalCaptureResult) {
            Integer num;
            com.google.common.util.concurrent.D0<Boolean> d0G = androidx.camera.core.impl.utils.futures.f.g(Boolean.TRUE);
            if (totalCaptureResult == null || (num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AF_MODE)) == null) {
                return d0G;
            }
            int iIntValue = num.intValue();
            if (iIntValue == 1 || iIntValue == 2) {
                C20140q0.d(3, "Camera2CapturePipeline");
                Integer num2 = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AF_STATE);
                if (num2 != null && num2.intValue() == 0) {
                    C20140q0.d(3, "Camera2CapturePipeline");
                    this.f22947b = true;
                    this.f22946a.f23394h.d(false);
                }
            }
            return d0G;
        }

        @Override // androidx.camera.camera2.internal.D.d
        public final boolean b() {
            return true;
        }

        @Override // androidx.camera.camera2.internal.D.d
        public final void c() {
            if (this.f22947b) {
                C20140q0.d(3, "Camera2CapturePipeline");
                this.f22946a.f23394h.a(true, false);
            }
        }
    }

    /* compiled from: Camera2CapturePipeline.java */
    @j.k0
    public static class c {

        /* renamed from: i, reason: collision with root package name */
        public static final long f22948i;

        /* renamed from: j, reason: collision with root package name */
        public static final long f22949j;

        /* renamed from: k, reason: collision with root package name */
        public static final /* synthetic */ int f22950k = 0;

        /* renamed from: a, reason: collision with root package name */
        public final int f22951a;

        /* renamed from: b, reason: collision with root package name */
        public final Executor f22952b;

        /* renamed from: c, reason: collision with root package name */
        public final C20026p f22953c;

        /* renamed from: d, reason: collision with root package name */
        public final androidx.camera.camera2.internal.compat.workaround.n f22954d;

        /* renamed from: e, reason: collision with root package name */
        public final boolean f22955e;

        /* renamed from: f, reason: collision with root package name */
        public long f22956f = f22948i;

        /* renamed from: g, reason: collision with root package name */
        public final ArrayList f22957g = new ArrayList();

        /* renamed from: h, reason: collision with root package name */
        public final a f22958h = new a();

        /* compiled from: Camera2CapturePipeline.java */
        public class a implements d {
            public a() {
            }

            @Override // androidx.camera.camera2.internal.D.d
            @j.N
            public final com.google.common.util.concurrent.D0<Boolean> a(@j.P TotalCaptureResult totalCaptureResult) {
                ArrayList arrayList = new ArrayList();
                Iterator it = c.this.f22957g.iterator();
                while (it.hasNext()) {
                    arrayList.add(((d) it.next()).a(totalCaptureResult));
                }
                return androidx.camera.core.impl.utils.futures.f.l(androidx.camera.core.impl.utils.futures.f.b(arrayList), new J(2), androidx.camera.core.impl.utils.executor.c.a());
            }

            @Override // androidx.camera.camera2.internal.D.d
            public final boolean b() {
                Iterator it = c.this.f22957g.iterator();
                while (it.hasNext()) {
                    if (((d) it.next()).b()) {
                        return true;
                    }
                }
                return false;
            }

            @Override // androidx.camera.camera2.internal.D.d
            public final void c() {
                Iterator it = c.this.f22957g.iterator();
                while (it.hasNext()) {
                    ((d) it.next()).c();
                }
            }
        }

        static {
            TimeUnit timeUnit = TimeUnit.SECONDS;
            f22948i = timeUnit.toNanos(1L);
            f22949j = timeUnit.toNanos(5L);
        }

        public c(int i12, @j.N Executor executor, @j.N C20026p c20026p, boolean z12, @j.N androidx.camera.camera2.internal.compat.workaround.n nVar) {
            this.f22951a = i12;
            this.f22952b = executor;
            this.f22953c = c20026p;
            this.f22955e = z12;
            this.f22954d = nVar;
        }
    }

    /* compiled from: Camera2CapturePipeline.java */
    public interface d {
        @j.N
        com.google.common.util.concurrent.D0<Boolean> a(@j.P TotalCaptureResult totalCaptureResult);

        boolean b();

        void c();
    }

    /* compiled from: Camera2CapturePipeline.java */
    public static class e implements C20026p.c {

        /* renamed from: a, reason: collision with root package name */
        public b.a<TotalCaptureResult> f22960a;

        /* renamed from: c, reason: collision with root package name */
        public final long f22962c;

        /* renamed from: d, reason: collision with root package name */
        public final a f22963d;

        /* renamed from: b, reason: collision with root package name */
        public final com.google.common.util.concurrent.D0<TotalCaptureResult> f22961b = androidx.concurrent.futures.b.a(new C(this, 3));

        /* renamed from: e, reason: collision with root package name */
        public volatile Long f22964e = null;

        /* compiled from: Camera2CapturePipeline.java */
        public interface a {
            boolean a(@j.N TotalCaptureResult totalCaptureResult);
        }

        public e(long j12, @j.P a aVar) {
            this.f22962c = j12;
            this.f22963d = aVar;
        }

        @Override // androidx.camera.camera2.internal.C20026p.c
        public final boolean a(@j.N TotalCaptureResult totalCaptureResult) {
            Long l12 = (Long) totalCaptureResult.get(CaptureResult.SENSOR_TIMESTAMP);
            if (l12 != null && this.f22964e == null) {
                this.f22964e = l12;
            }
            Long l13 = this.f22964e;
            if (0 != this.f22962c && l13 != null && l12 != null && l12.longValue() - l13.longValue() > this.f22962c) {
                this.f22960a.b(null);
                C20140q0.d(3, "Camera2CapturePipeline");
                return true;
            }
            a aVar = this.f22963d;
            if (aVar != null && !aVar.a(totalCaptureResult)) {
                return false;
            }
            this.f22960a.b(totalCaptureResult);
            return true;
        }
    }

    /* compiled from: Camera2CapturePipeline.java */
    public static class f implements d {

        /* renamed from: e, reason: collision with root package name */
        public static final long f22965e = TimeUnit.SECONDS.toNanos(2);

        /* renamed from: f, reason: collision with root package name */
        public static final /* synthetic */ int f22966f = 0;

        /* renamed from: a, reason: collision with root package name */
        public final C20026p f22967a;

        /* renamed from: b, reason: collision with root package name */
        public final int f22968b;

        /* renamed from: c, reason: collision with root package name */
        public boolean f22969c = false;

        /* renamed from: d, reason: collision with root package name */
        public final Executor f22970d;

        public f(@j.N C20026p c20026p, int i12, @j.N Executor executor) {
            this.f22967a = c20026p;
            this.f22968b = i12;
            this.f22970d = executor;
        }

        @Override // androidx.camera.camera2.internal.D.d
        @j.N
        public final com.google.common.util.concurrent.D0<Boolean> a(@j.P TotalCaptureResult totalCaptureResult) {
            if (D.b(totalCaptureResult, this.f22968b)) {
                if (!this.f22967a.f23402p) {
                    C20140q0.d(3, "Camera2CapturePipeline");
                    this.f22969c = true;
                    androidx.camera.core.impl.utils.futures.d dVarA = androidx.camera.core.impl.utils.futures.d.a(androidx.concurrent.futures.b.a(new I(this)));
                    I i12 = new I(this);
                    Executor executor = this.f22970d;
                    dVarA.getClass();
                    return (androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.l((androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.m(dVarA, i12, executor), new J(0), androidx.camera.core.impl.utils.executor.c.a());
                }
                C20140q0.d(3, "Camera2CapturePipeline");
            }
            return androidx.camera.core.impl.utils.futures.f.g(Boolean.FALSE);
        }

        @Override // androidx.camera.camera2.internal.D.d
        public final boolean b() {
            return this.f22968b == 0;
        }

        @Override // androidx.camera.camera2.internal.D.d
        public final void c() {
            if (this.f22969c) {
                this.f22967a.f23396j.a(null, false);
                C20140q0.d(3, "Camera2CapturePipeline");
            }
        }
    }

    static {
        CameraCaptureMetaData.AeState aeState = CameraCaptureMetaData.AeState.f23881f;
        CameraCaptureMetaData.AeState aeState2 = CameraCaptureMetaData.AeState.f23880e;
        CameraCaptureMetaData.AeState aeState3 = CameraCaptureMetaData.AeState.f23877b;
        Set<CameraCaptureMetaData.AeState> setUnmodifiableSet = Collections.unmodifiableSet(EnumSet.of(aeState, aeState2, aeState3));
        f22933j = setUnmodifiableSet;
        EnumSet enumSetCopyOf = EnumSet.copyOf((Collection) setUnmodifiableSet);
        enumSetCopyOf.remove(aeState2);
        enumSetCopyOf.remove(aeState3);
        f22934k = Collections.unmodifiableSet(enumSetCopyOf);
    }

    public D(@j.N C20026p c20026p, @j.N androidx.camera.camera2.internal.compat.n nVar, @j.N androidx.camera.core.impl.w0 w0Var, @j.N Executor executor) {
        this.f22935a = c20026p;
        Integer num = (Integer) nVar.a(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        this.f22940f = num != null && num.intValue() == 2;
        this.f22939e = executor;
        this.f22938d = w0Var;
        this.f22936b = new androidx.camera.camera2.internal.compat.workaround.u(w0Var);
        this.f22937c = androidx.camera.camera2.internal.compat.workaround.g.a(new C(nVar, 7));
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean a(@j.P android.hardware.camera2.TotalCaptureResult r5, boolean r6) {
        /*
            r0 = 0
            if (r5 != 0) goto L4
            return r0
        L4:
            androidx.camera.camera2.internal.h r1 = new androidx.camera.camera2.internal.h
            r1.<init>(r5)
            androidx.camera.core.impl.CameraCaptureMetaData$AfMode r2 = r1.i()
            androidx.camera.core.impl.CameraCaptureMetaData$AfMode r3 = androidx.camera.core.impl.CameraCaptureMetaData.AfMode.f23885c
            r4 = 1
            if (r2 == r3) goto L29
            androidx.camera.core.impl.CameraCaptureMetaData$AfMode r2 = r1.i()
            androidx.camera.core.impl.CameraCaptureMetaData$AfMode r3 = androidx.camera.core.impl.CameraCaptureMetaData.AfMode.f23884b
            if (r2 == r3) goto L29
            androidx.camera.core.impl.CameraCaptureMetaData$AfState r2 = r1.e()
            java.util.Set<androidx.camera.core.impl.CameraCaptureMetaData$AfState> r3 = androidx.camera.camera2.internal.D.f22931h
            boolean r2 = r3.contains(r2)
            if (r2 == 0) goto L27
            goto L29
        L27:
            r2 = r0
            goto L2a
        L29:
            r2 = r4
        L2a:
            android.hardware.camera2.CaptureResult$Key r3 = android.hardware.camera2.CaptureResult.CONTROL_AE_MODE
            java.lang.Object r3 = r5.get(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 != 0) goto L3a
            r3 = r4
            goto L3b
        L3a:
            r3 = r0
        L3b:
            if (r6 == 0) goto L50
            if (r3 != 0) goto L4e
            androidx.camera.core.impl.CameraCaptureMetaData$AeState r6 = r1.g()
            java.util.Set<androidx.camera.core.impl.CameraCaptureMetaData$AeState> r3 = androidx.camera.camera2.internal.D.f22934k
            boolean r6 = r3.contains(r6)
            if (r6 == 0) goto L4c
            goto L4e
        L4c:
            r6 = r0
            goto L5f
        L4e:
            r6 = r4
            goto L5f
        L50:
            if (r3 != 0) goto L4e
            androidx.camera.core.impl.CameraCaptureMetaData$AeState r6 = r1.g()
            java.util.Set<androidx.camera.core.impl.CameraCaptureMetaData$AeState> r3 = androidx.camera.camera2.internal.D.f22933j
            boolean r6 = r3.contains(r6)
            if (r6 == 0) goto L4c
            goto L4e
        L5f:
            android.hardware.camera2.CaptureResult$Key r3 = android.hardware.camera2.CaptureResult.CONTROL_AWB_MODE
            java.lang.Object r5 = r5.get(r3)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            if (r5 != 0) goto L6e
            goto L7a
        L6e:
            androidx.camera.core.impl.CameraCaptureMetaData$AwbState r5 = r1.f()
            java.util.Set<androidx.camera.core.impl.CameraCaptureMetaData$AwbState> r3 = androidx.camera.camera2.internal.D.f22932i
            boolean r5 = r3.contains(r5)
            if (r5 == 0) goto L7c
        L7a:
            r5 = r4
            goto L7d
        L7c:
            r5 = r0
        L7d:
            androidx.camera.core.impl.CameraCaptureMetaData$AeState r3 = r1.g()
            r3.toString()
            androidx.camera.core.impl.CameraCaptureMetaData$AfState r3 = r1.e()
            r3.toString()
            androidx.camera.core.impl.CameraCaptureMetaData$AwbState r1 = r1.f()
            r1.toString()
            r1 = 3
            java.lang.String r3 = "Camera2CapturePipeline"
            androidx.camera.core.C20140q0.d(r1, r3)
            if (r2 == 0) goto L9f
            if (r6 == 0) goto L9f
            if (r5 == 0) goto L9f
            r0 = r4
        L9f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.internal.D.a(android.hardware.camera2.TotalCaptureResult, boolean):boolean");
    }

    public static boolean b(@j.P TotalCaptureResult totalCaptureResult, int i12) {
        if (i12 == 0) {
            Integer num = totalCaptureResult != null ? (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_STATE) : null;
            return num != null && num.intValue() == 4;
        }
        if (i12 == 1) {
            return true;
        }
        if (i12 == 2) {
            return false;
        }
        throw new AssertionError(i12);
    }
}
