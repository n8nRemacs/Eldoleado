package androidx.camera.view;

import android.graphics.Matrix;
import android.graphics.PointF;
import androidx.annotation.RestrictTo;
import androidx.camera.core.t0;
import j.InterfaceC42148d;
import j.N;
import j.P;
import j.X;

/* compiled from: PreviewViewMeteringPointFactory.java */
@RestrictTo
@X
/* loaded from: classes.dex */
class v extends t0 {

    /* renamed from: c, reason: collision with root package name */
    public static final PointF f25477c = new PointF(2.0f, 2.0f);

    /* renamed from: a, reason: collision with root package name */
    @N
    public final r f25478a;

    /* renamed from: b, reason: collision with root package name */
    @j.B
    @P
    public Matrix f25479b;

    public v(@N r rVar) {
        this.f25478a = rVar;
    }

    @Override // androidx.camera.core.t0
    @N
    @InterfaceC42148d
    public final PointF a(float f12, float f13) {
        float[] fArr = {f12, f13};
        synchronized (this) {
            try {
                Matrix matrix = this.f25479b;
                if (matrix == null) {
                    return f25477c;
                }
                matrix.mapPoints(fArr);
                return new PointF(fArr[0], fArr[1]);
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
