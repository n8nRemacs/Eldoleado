package androidx.camera.camera2.internal;

/* compiled from: ZoomStateImpl.java */
@j.X
/* loaded from: classes.dex */
class a1 implements androidx.camera.core.Q0 {

    /* renamed from: a, reason: collision with root package name */
    public float f23102a;

    /* renamed from: b, reason: collision with root package name */
    public final float f23103b;

    /* renamed from: c, reason: collision with root package name */
    public final float f23104c;

    /* renamed from: d, reason: collision with root package name */
    public float f23105d;

    public a1(float f12, float f13) {
        this.f23103b = f12;
        this.f23104c = f13;
    }

    @Override // androidx.camera.core.Q0
    public final float a() {
        return this.f23105d;
    }

    @Override // androidx.camera.core.Q0
    public final float b() {
        return this.f23102a;
    }

    @Override // androidx.camera.core.Q0
    public final float c() {
        return this.f23103b;
    }

    @Override // androidx.camera.core.Q0
    public final float d() {
        return this.f23104c;
    }

    public final void e(float f12) {
        if (f12 > 1.0f || f12 < 0.0f) {
            throw new IllegalArgumentException("Requested linearZoom " + f12 + " is not within valid range [0..1]");
        }
        this.f23105d = f12;
        float f13 = this.f23103b;
        if (f12 != 1.0f) {
            float f14 = this.f23104c;
            if (f12 == 0.0f) {
                f13 = f14;
            } else {
                double d12 = 1.0f / f14;
                double d13 = 1.0d / ((((1.0f / f13) - d12) * f12) + d12);
                double d14 = f14;
                double d15 = f13;
                if (d13 < d14) {
                    d13 = d14;
                } else if (d13 > d15) {
                    d13 = d15;
                }
                f13 = (float) d13;
            }
        }
        this.f23102a = f13;
    }

    public final void f(float f12) {
        float f13 = this.f23103b;
        float f14 = this.f23104c;
        if (f12 > f13 || f12 < f14) {
            StringBuilder sb2 = new StringBuilder("Requested zoomRatio ");
            sb2.append(f12);
            sb2.append(" is not within valid range [");
            sb2.append(f14);
            sb2.append(" , ");
            throw new IllegalArgumentException(G.d(f13, "]", sb2));
        }
        this.f23102a = f12;
        float f15 = 0.0f;
        if (f13 != f14) {
            if (f12 == f13) {
                f15 = 1.0f;
            } else if (f12 != f14) {
                float f16 = 1.0f / f14;
                f15 = ((1.0f / f12) - f16) / ((1.0f / f13) - f16);
            }
        }
        this.f23105d = f15;
    }
}
