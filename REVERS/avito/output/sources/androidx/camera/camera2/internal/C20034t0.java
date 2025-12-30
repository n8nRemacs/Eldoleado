package androidx.camera.camera2.internal;

import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.MeteringRectangle;
import android.util.Rational;
import androidx.camera.camera2.impl.b;
import androidx.camera.camera2.internal.C20039w;
import androidx.camera.camera2.internal.compat.quirk.C19991b;
import androidx.camera.core.C20140q0;
import androidx.camera.core.CameraControl;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.H;
import androidx.camera.core.impl.SessionConfig;
import androidx.concurrent.futures.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/* compiled from: FocusMeteringControl.java */
@j.S
@j.X
/* renamed from: androidx.camera.camera2.internal.t0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C20034t0 {

    /* renamed from: t, reason: collision with root package name */
    public static final MeteringRectangle[] f23439t = new MeteringRectangle[0];

    /* renamed from: a, reason: collision with root package name */
    public final C20026p f23440a;

    /* renamed from: b, reason: collision with root package name */
    public final Executor f23441b;

    /* renamed from: c, reason: collision with root package name */
    public final ScheduledExecutorService f23442c;

    /* renamed from: f, reason: collision with root package name */
    @j.N
    public final androidx.camera.camera2.internal.compat.workaround.l f23445f;

    /* renamed from: i, reason: collision with root package name */
    public ScheduledFuture<?> f23448i;

    /* renamed from: j, reason: collision with root package name */
    public ScheduledFuture<?> f23449j;

    /* renamed from: p, reason: collision with root package name */
    public MeteringRectangle[] f23455p;

    /* renamed from: q, reason: collision with root package name */
    public MeteringRectangle[] f23456q;

    /* renamed from: r, reason: collision with root package name */
    public MeteringRectangle[] f23457r;

    /* renamed from: s, reason: collision with root package name */
    public b.a<androidx.camera.core.N> f23458s;

    /* renamed from: d, reason: collision with root package name */
    public volatile boolean f23443d = false;

    /* renamed from: e, reason: collision with root package name */
    public volatile Rational f23444e = null;

    /* renamed from: g, reason: collision with root package name */
    public boolean f23446g = false;

    /* renamed from: h, reason: collision with root package name */
    @j.N
    public Integer f23447h = 0;

    /* renamed from: k, reason: collision with root package name */
    public long f23450k = 0;

    /* renamed from: l, reason: collision with root package name */
    public boolean f23451l = false;

    /* renamed from: m, reason: collision with root package name */
    public boolean f23452m = false;

    /* renamed from: n, reason: collision with root package name */
    public int f23453n = 1;

    /* renamed from: o, reason: collision with root package name */
    public C20027p0 f23454o = null;

    public C20034t0(@j.N C20026p c20026p, @j.N ScheduledExecutorService scheduledExecutorService, @j.N Executor executor, @j.N androidx.camera.core.impl.w0 w0Var) {
        MeteringRectangle[] meteringRectangleArr = f23439t;
        this.f23455p = meteringRectangleArr;
        this.f23456q = meteringRectangleArr;
        this.f23457r = meteringRectangleArr;
        this.f23458s = null;
        this.f23440a = c20026p;
        this.f23441b = executor;
        this.f23442c = scheduledExecutorService;
        this.f23445f = new androidx.camera.camera2.internal.compat.workaround.l(w0Var);
    }

    public final void a(boolean z12, boolean z13) {
        androidx.camera.core.impl.r rVar;
        if (this.f23443d) {
            H.a aVar = new H.a();
            aVar.f23981f = true;
            aVar.f23978c = this.f23453n;
            b.a aVar2 = new b.a();
            if (z12) {
                aVar2.c(CaptureRequest.CONTROL_AF_TRIGGER, 2);
            }
            if (z13) {
                aVar2.c(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 2);
            }
            aVar.c(aVar2.a());
            C20026p c20026p = this.f23440a;
            List<androidx.camera.core.impl.H> listSingletonList = Collections.singletonList(aVar.e());
            C20039w.e eVar = (C20039w.e) c20026p.f23392f;
            eVar.getClass();
            listSingletonList.getClass();
            C20039w c20039w = C20039w.this;
            c20039w.getClass();
            ArrayList arrayList = new ArrayList();
            for (androidx.camera.core.impl.H h12 : listSingletonList) {
                H.a aVar3 = new H.a(h12);
                if (h12.f23970c == 5 && (rVar = h12.f23975h) != null) {
                    aVar3.f23983h = rVar;
                }
                if (Collections.unmodifiableList(h12.f23968a).isEmpty() && h12.f23973f) {
                    HashSet hashSet = aVar3.f23976a;
                    if (hashSet.isEmpty()) {
                        Iterator<SessionConfig> it = c20039w.f23466b.b().iterator();
                        while (it.hasNext()) {
                            List listUnmodifiableList = Collections.unmodifiableList(it.next().f24017f.f23968a);
                            if (!listUnmodifiableList.isEmpty()) {
                                Iterator it2 = listUnmodifiableList.iterator();
                                while (it2.hasNext()) {
                                    aVar3.d((DeferrableSurface) it2.next());
                                }
                            }
                        }
                        if (hashSet.isEmpty()) {
                            C20140q0.d(5, "Camera2CameraImpl");
                        }
                    } else {
                        C20140q0.d(5, "Camera2CameraImpl");
                    }
                }
                arrayList.add(aVar3.e());
            }
            c20039w.s("Issue capture request");
            c20039w.f23478n.b(arrayList);
        }
    }

    public final void b() {
        C20026p c20026p = this.f23440a;
        c20026p.f23388b.f23414a.remove(null);
        c20026p.f23388b.f23414a.remove(this.f23454o);
        b.a<androidx.camera.core.N> aVar = this.f23458s;
        if (aVar != null) {
            aVar.d(new CameraControl.OperationCanceledException("Cancelled by cancelFocusAndMetering()"));
            this.f23458s = null;
        }
        ScheduledFuture<?> scheduledFuture = this.f23448i;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.f23448i = null;
        }
        ScheduledFuture<?> scheduledFuture2 = this.f23449j;
        if (scheduledFuture2 != null) {
            scheduledFuture2.cancel(true);
            this.f23449j = null;
        }
        if (this.f23455p.length > 0) {
            a(true, false);
        }
        MeteringRectangle[] meteringRectangleArr = f23439t;
        this.f23455p = meteringRectangleArr;
        this.f23456q = meteringRectangleArr;
        this.f23457r = meteringRectangleArr;
        this.f23446g = false;
        c20026p.w();
    }

    @j.N
    public final List<MeteringRectangle> c(@j.N List<androidx.camera.core.s0> list, int i12, @j.N Rational rational, @j.N Rect rect, int i13) {
        if (list.isEmpty() || i12 == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Rational rational2 = new Rational(rect.width(), rect.height());
        for (androidx.camera.core.s0 s0Var : list) {
            if (arrayList.size() == i12) {
                break;
            }
            float f12 = s0Var.f24533a;
            if (f12 >= 0.0f && f12 <= 1.0f) {
                float f13 = s0Var.f24534b;
                if (f13 >= 0.0f && f13 <= 1.0f) {
                    PointF pointF = (i13 == 1 && this.f23445f.f23258a.a(C19991b.class)) ? new PointF(1.0f - f12, f13) : new PointF(f12, f13);
                    if (!rational.equals(rational2)) {
                        if (rational.compareTo(rational2) > 0) {
                            float fDoubleValue = (float) (rational.doubleValue() / rational2.doubleValue());
                            pointF.y = (1.0f / fDoubleValue) * (((float) ((fDoubleValue - 1.0d) / 2.0d)) + pointF.y);
                        } else {
                            float fDoubleValue2 = (float) (rational2.doubleValue() / rational.doubleValue());
                            pointF.x = (1.0f / fDoubleValue2) * (((float) ((fDoubleValue2 - 1.0d) / 2.0d)) + pointF.x);
                        }
                    }
                    int iWidth = (int) ((pointF.x * rect.width()) + rect.left);
                    int iHeight = (int) ((pointF.y * rect.height()) + rect.top);
                    float fWidth = rect.width();
                    float f14 = s0Var.f24535c;
                    int i14 = ((int) (fWidth * f14)) / 2;
                    int iHeight2 = ((int) (f14 * rect.height())) / 2;
                    Rect rect2 = new Rect(iWidth - i14, iHeight - iHeight2, iWidth + i14, iHeight + iHeight2);
                    rect2.left = Math.min(Math.max(rect2.left, rect.left), rect.right);
                    rect2.right = Math.min(Math.max(rect2.right, rect.left), rect.right);
                    rect2.top = Math.min(Math.max(rect2.top, rect.top), rect.bottom);
                    rect2.bottom = Math.min(Math.max(rect2.bottom, rect.top), rect.bottom);
                    MeteringRectangle meteringRectangle = new MeteringRectangle(rect2, 1000);
                    if (meteringRectangle.getWidth() != 0 && meteringRectangle.getHeight() != 0) {
                        arrayList.add(meteringRectangle);
                    }
                }
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public final void d(boolean z12) {
        androidx.camera.core.impl.r rVar;
        if (this.f23443d) {
            H.a aVar = new H.a();
            aVar.f23978c = this.f23453n;
            aVar.f23981f = true;
            b.a aVar2 = new b.a();
            aVar2.c(CaptureRequest.CONTROL_AF_TRIGGER, 1);
            if (z12) {
                aVar2.c(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(this.f23440a.q(1)));
            }
            aVar.c(aVar2.a());
            aVar.b(new C20030r0());
            C20026p c20026p = this.f23440a;
            List<androidx.camera.core.impl.H> listSingletonList = Collections.singletonList(aVar.e());
            C20039w.e eVar = (C20039w.e) c20026p.f23392f;
            eVar.getClass();
            listSingletonList.getClass();
            C20039w c20039w = C20039w.this;
            c20039w.getClass();
            ArrayList arrayList = new ArrayList();
            for (androidx.camera.core.impl.H h12 : listSingletonList) {
                H.a aVar3 = new H.a(h12);
                if (h12.f23970c == 5 && (rVar = h12.f23975h) != null) {
                    aVar3.f23983h = rVar;
                }
                if (Collections.unmodifiableList(h12.f23968a).isEmpty() && h12.f23973f) {
                    HashSet hashSet = aVar3.f23976a;
                    if (hashSet.isEmpty()) {
                        Iterator<SessionConfig> it = c20039w.f23466b.b().iterator();
                        while (it.hasNext()) {
                            List listUnmodifiableList = Collections.unmodifiableList(it.next().f24017f.f23968a);
                            if (!listUnmodifiableList.isEmpty()) {
                                Iterator it2 = listUnmodifiableList.iterator();
                                while (it2.hasNext()) {
                                    aVar3.d((DeferrableSurface) it2.next());
                                }
                            }
                        }
                        if (hashSet.isEmpty()) {
                            C20140q0.d(5, "Camera2CameraImpl");
                        }
                    } else {
                        C20140q0.d(5, "Camera2CameraImpl");
                    }
                }
                arrayList.add(aVar3.e());
            }
            c20039w.s("Issue capture request");
            c20039w.f23478n.b(arrayList);
        }
    }
}
