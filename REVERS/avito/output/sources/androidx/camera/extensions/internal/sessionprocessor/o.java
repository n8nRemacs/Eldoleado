package androidx.camera.extensions.internal.sessionprocessor;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.util.LongSparseArray;
import j.B;
import j.N;
import j.X;
import java.util.HashMap;

/* compiled from: CaptureResultImageMatcher.java */
@X
/* loaded from: classes.dex */
class o {

    /* renamed from: a, reason: collision with root package name */
    public final Object f24641a = new Object();

    /* renamed from: b, reason: collision with root package name */
    @B
    public final LongSparseArray<TotalCaptureResult> f24642b = new LongSparseArray<>();

    /* renamed from: c, reason: collision with root package name */
    @B
    public final HashMap f24643c = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    @B
    public final LongSparseArray<r> f24644d = new LongSparseArray<>();

    /* renamed from: e, reason: collision with root package name */
    @B
    public a f24645e;

    /* compiled from: CaptureResultImageMatcher.java */
    public interface a {
        void a(@N r rVar, @N TotalCaptureResult totalCaptureResult, int i12);
    }

    public final void a(@N TotalCaptureResult totalCaptureResult, int i12) {
        synchronized (this.f24641a) {
            try {
                Long l12 = (Long) totalCaptureResult.get(CaptureResult.SENSOR_TIMESTAMP);
                long jLongValue = l12 != null ? l12.longValue() : -1L;
                if (jLongValue == -1) {
                    return;
                }
                this.f24642b.put(jLongValue, totalCaptureResult);
                this.f24643c.put(totalCaptureResult, Integer.valueOf(i12));
                d();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void b() {
        synchronized (this.f24641a) {
            try {
                this.f24642b.clear();
                for (int i12 = 0; i12 < this.f24644d.size(); i12++) {
                    this.f24644d.get(this.f24644d.keyAt(i12)).b();
                }
                this.f24644d.clear();
                this.f24643c.clear();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void c(@N r rVar) {
        synchronized (this.f24641a) {
            this.f24644d.put(rVar.get().getTimestamp(), rVar);
        }
        d();
    }

    public final void d() {
        a aVar;
        r rVar;
        TotalCaptureResult totalCaptureResult;
        Integer num;
        synchronized (this.f24641a) {
            try {
                aVar = null;
                rVar = null;
                totalCaptureResult = null;
                for (int size = this.f24642b.size() - 1; size >= 0; size--) {
                    TotalCaptureResult totalCaptureResultValueAt = this.f24642b.valueAt(size);
                    Long l12 = (Long) totalCaptureResultValueAt.get(CaptureResult.SENSOR_TIMESTAMP);
                    long jLongValue = l12 != null ? l12.longValue() : -1L;
                    r rVar2 = this.f24644d.get(jLongValue);
                    if (rVar2 != null) {
                        this.f24644d.remove(jLongValue);
                        this.f24642b.removeAt(size);
                        totalCaptureResult = totalCaptureResultValueAt;
                        rVar = rVar2;
                    }
                }
                e();
            } finally {
            }
        }
        if (rVar == null || totalCaptureResult == null) {
            return;
        }
        synchronized (this.f24641a) {
            try {
                a aVar2 = this.f24645e;
                if (aVar2 != null) {
                    aVar = aVar2;
                    num = (Integer) this.f24643c.get(totalCaptureResult);
                } else {
                    rVar.b();
                    num = null;
                }
            } finally {
            }
        }
        if (aVar != null) {
            aVar.a(rVar, totalCaptureResult, num.intValue());
        }
    }

    public final void e() {
        synchronized (this.f24641a) {
            try {
                if (this.f24644d.size() != 0 && this.f24642b.size() != 0) {
                    long jKeyAt = this.f24644d.keyAt(0);
                    Long lValueOf = Long.valueOf(jKeyAt);
                    long jKeyAt2 = this.f24642b.keyAt(0);
                    androidx.core.util.z.b(!Long.valueOf(jKeyAt2).equals(lValueOf));
                    if (jKeyAt2 > jKeyAt) {
                        for (int size = this.f24644d.size() - 1; size >= 0; size--) {
                            if (this.f24644d.keyAt(size) < jKeyAt2) {
                                this.f24644d.valueAt(size).b();
                                this.f24644d.removeAt(size);
                            }
                        }
                    } else {
                        for (int size2 = this.f24642b.size() - 1; size2 >= 0; size2--) {
                            if (this.f24642b.keyAt(size2) < jKeyAt) {
                                this.f24642b.removeAt(size2);
                            }
                        }
                    }
                }
            } finally {
            }
        }
    }
}
