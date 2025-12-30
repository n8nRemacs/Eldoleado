package androidx.camera.core;

import android.graphics.PointF;
import androidx.annotation.RestrictTo;

/* compiled from: MeteringPointFactory.java */
@j.X
/* loaded from: classes.dex */
public abstract class t0 {
    @RestrictTo
    public t0() {
    }

    @j.N
    @RestrictTo
    public abstract PointF a(float f12, float f13);

    @j.N
    public final s0 b(float f12, float f13, float f14) {
        PointF pointFA = a(f12, f13);
        float f15 = pointFA.x;
        float f16 = pointFA.y;
        s0 s0Var = new s0();
        s0Var.f24533a = f15;
        s0Var.f24534b = f16;
        s0Var.f24535c = f14;
        return s0Var;
    }
}
