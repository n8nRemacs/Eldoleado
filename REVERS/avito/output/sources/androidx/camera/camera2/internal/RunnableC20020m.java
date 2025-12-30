package androidx.camera.camera2.internal;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.util.Rational;
import androidx.camera.camera2.internal.C20026p;
import androidx.camera.core.CameraControl;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.concurrent.futures.b;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.camera2.internal.m, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20020m implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23368b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f23369c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f23370d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f23371e;

    public /* synthetic */ RunnableC20020m(Object obj, Object obj2, Object obj3, int i12) {
        this.f23368b = i12;
        this.f23369c = obj;
        this.f23370d = obj2;
        this.f23371e = obj3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v16, types: [androidx.camera.camera2.internal.p$c, androidx.camera.camera2.internal.p0] */
    @Override // java.lang.Runnable
    public final void run() {
        Rational rational;
        final long jW2;
        switch (this.f23368b) {
            case 0:
                C20026p.a aVar = ((C20026p) this.f23369c).f23410x;
                HashSet hashSet = aVar.f23411a;
                AbstractC20109p abstractC20109p = (AbstractC20109p) this.f23371e;
                hashSet.add(abstractC20109p);
                aVar.f23412b.put(abstractC20109p, (Executor) this.f23370d);
                break;
            default:
                final C20034t0 c20034t0 = (C20034t0) this.f23369c;
                b.a<androidx.camera.core.N> aVar2 = (b.a) this.f23370d;
                androidx.camera.core.M m12 = (androidx.camera.core.M) this.f23371e;
                if (!c20034t0.f23443d) {
                    aVar2.d(new CameraControl.OperationCanceledException("Camera is not active."));
                    break;
                } else {
                    Rect rectN0 = c20034t0.f23440a.f23395i.f23098e.n0();
                    if (c20034t0.f23444e != null) {
                        rational = c20034t0.f23444e;
                    } else {
                        Rect rectN02 = c20034t0.f23440a.f23395i.f23098e.n0();
                        rational = new Rational(rectN02.width(), rectN02.height());
                    }
                    List<androidx.camera.core.s0> list = m12.f23606a;
                    Integer num = (Integer) c20034t0.f23440a.f23391e.a(CameraCharacteristics.CONTROL_MAX_REGIONS_AF);
                    List<MeteringRectangle> listC = c20034t0.c(list, num == null ? 0 : num.intValue(), rational, rectN0, 1);
                    List<androidx.camera.core.s0> list2 = m12.f23607b;
                    Integer num2 = (Integer) c20034t0.f23440a.f23391e.a(CameraCharacteristics.CONTROL_MAX_REGIONS_AE);
                    List<MeteringRectangle> listC2 = c20034t0.c(list2, num2 == null ? 0 : num2.intValue(), rational, rectN0, 2);
                    List<androidx.camera.core.s0> list3 = m12.f23608c;
                    Integer num3 = (Integer) c20034t0.f23440a.f23391e.a(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB);
                    List<MeteringRectangle> listC3 = c20034t0.c(list3, num3 == null ? 0 : num3.intValue(), rational, rectN0, 4);
                    if (!listC.isEmpty() || !listC2.isEmpty() || !listC3.isEmpty()) {
                        c20034t0.f23440a.f23388b.f23414a.remove(c20034t0.f23454o);
                        b.a<androidx.camera.core.N> aVar3 = c20034t0.f23458s;
                        if (aVar3 != null) {
                            aVar3.d(new CameraControl.OperationCanceledException("Cancelled by another startFocusAndMetering()"));
                            c20034t0.f23458s = null;
                        }
                        c20034t0.f23440a.f23388b.f23414a.remove(null);
                        ScheduledFuture<?> scheduledFuture = c20034t0.f23448i;
                        if (scheduledFuture != null) {
                            scheduledFuture.cancel(true);
                            c20034t0.f23448i = null;
                        }
                        c20034t0.f23458s = aVar2;
                        MeteringRectangle[] meteringRectangleArr = C20034t0.f23439t;
                        MeteringRectangle[] meteringRectangleArr2 = (MeteringRectangle[]) listC.toArray(meteringRectangleArr);
                        MeteringRectangle[] meteringRectangleArr3 = (MeteringRectangle[]) listC2.toArray(meteringRectangleArr);
                        MeteringRectangle[] meteringRectangleArr4 = (MeteringRectangle[]) listC3.toArray(meteringRectangleArr);
                        C20027p0 c20027p0 = c20034t0.f23454o;
                        C20026p c20026p = c20034t0.f23440a;
                        c20026p.f23388b.f23414a.remove(c20027p0);
                        ScheduledFuture<?> scheduledFuture2 = c20034t0.f23448i;
                        if (scheduledFuture2 != null) {
                            scheduledFuture2.cancel(true);
                            c20034t0.f23448i = null;
                        }
                        ScheduledFuture<?> scheduledFuture3 = c20034t0.f23449j;
                        if (scheduledFuture3 != null) {
                            scheduledFuture3.cancel(true);
                            c20034t0.f23449j = null;
                        }
                        c20034t0.f23455p = meteringRectangleArr2;
                        c20034t0.f23456q = meteringRectangleArr3;
                        c20034t0.f23457r = meteringRectangleArr4;
                        if (meteringRectangleArr2.length > 0) {
                            c20034t0.f23446g = true;
                            c20034t0.f23451l = false;
                            c20034t0.f23452m = false;
                            jW2 = c20026p.w();
                            c20034t0.d(true);
                        } else {
                            c20034t0.f23446g = false;
                            c20034t0.f23451l = true;
                            c20034t0.f23452m = false;
                            jW2 = c20026p.w();
                        }
                        c20034t0.f23447h = 0;
                        final boolean z12 = c20026p.r(1) == 1;
                        ?? r32 = new C20026p.c() { // from class: androidx.camera.camera2.internal.p0
                            @Override // androidx.camera.camera2.internal.C20026p.c
                            public final boolean a(TotalCaptureResult totalCaptureResult) {
                                C20034t0 c20034t02 = c20034t0;
                                c20034t02.getClass();
                                Integer num4 = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AF_STATE);
                                if (c20034t02.f23455p.length > 0) {
                                    if (!z12 || num4 == null) {
                                        c20034t02.f23452m = true;
                                        c20034t02.f23451l = true;
                                    } else if (c20034t02.f23447h.intValue() == 3) {
                                        if (num4.intValue() == 4) {
                                            c20034t02.f23452m = true;
                                            c20034t02.f23451l = true;
                                        } else if (num4.intValue() == 5) {
                                            c20034t02.f23452m = false;
                                            c20034t02.f23451l = true;
                                        }
                                    }
                                }
                                if (!c20034t02.f23451l || !C20026p.u(totalCaptureResult, jW2)) {
                                    if (c20034t02.f23447h.equals(num4) || num4 == null) {
                                        return false;
                                    }
                                    c20034t02.f23447h = num4;
                                    return false;
                                }
                                boolean z13 = c20034t02.f23452m;
                                ScheduledFuture<?> scheduledFuture4 = c20034t02.f23449j;
                                if (scheduledFuture4 != null) {
                                    scheduledFuture4.cancel(true);
                                    c20034t02.f23449j = null;
                                }
                                b.a<androidx.camera.core.N> aVar4 = c20034t02.f23458s;
                                if (aVar4 != null) {
                                    aVar4.b(new androidx.camera.core.N(z13));
                                    c20034t02.f23458s = null;
                                }
                                return true;
                            }
                        };
                        c20034t0.f23454o = r32;
                        c20026p.m(r32);
                        long j12 = c20034t0.f23450k + 1;
                        c20034t0.f23450k = j12;
                        RunnableC20029q0 runnableC20029q0 = new RunnableC20029q0(c20034t0, j12, 0);
                        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                        ScheduledExecutorService scheduledExecutorService = c20034t0.f23442c;
                        c20034t0.f23449j = scheduledExecutorService.schedule(runnableC20029q0, 5000L, timeUnit);
                        long j13 = m12.f23609d;
                        if (j13 > 0) {
                            c20034t0.f23448i = scheduledExecutorService.schedule(new RunnableC20029q0(c20034t0, j12, 1), j13, timeUnit);
                            break;
                        }
                    } else {
                        aVar2.d(new IllegalArgumentException("None of the specified AF/AE/AWB MeteringPoints is supported on this camera."));
                        break;
                    }
                }
                break;
        }
    }
}
