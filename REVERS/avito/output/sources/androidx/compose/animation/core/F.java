package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: Easing.kt */
@androidx.compose.runtime.H0
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/F;", "Landroidx/compose/animation/core/N;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class F implements N {

    /* renamed from: a, reason: collision with root package name */
    public final float f26051a;

    /* renamed from: b, reason: collision with root package name */
    public final float f26052b;

    /* renamed from: c, reason: collision with root package name */
    public final float f26053c;

    /* renamed from: d, reason: collision with root package name */
    public final float f26054d;

    /* renamed from: e, reason: collision with root package name */
    public final float f26055e;

    /* renamed from: f, reason: collision with root package name */
    public final float f26056f;

    public F(float f12, float f13, float f14, float f15) {
        int iA2;
        this.f26051a = f12;
        this.f26052b = f13;
        this.f26053c = f14;
        this.f26054d = f15;
        if (Float.isNaN(f12) || Float.isNaN(f13) || Float.isNaN(f14) || Float.isNaN(f15)) {
            C20334y0.a("Parameters to CubicBezierEasing cannot be NaN. Actual parameters are: " + f12 + ", " + f13 + ", " + f14 + ", " + f15 + '.');
        }
        float[] fArr = new float[5];
        float f16 = (f13 - 0.0f) * 3.0f;
        float f17 = (f15 - f13) * 3.0f;
        float f18 = (1.0f - f15) * 3.0f;
        double d12 = f16;
        double d13 = f17;
        double d14 = f18;
        double d15 = d13 * 2.0d;
        double d16 = (d12 - d15) + d14;
        if (d16 == 0.0d) {
            iA2 = d13 == d14 ? 0 : androidx.compose.ui.graphics.D.a((float) ((d15 - d14) / (d15 - (d14 * 2.0d))), 0, fArr);
        } else {
            double d17 = -Math.sqrt((d13 * d13) - (d14 * d12));
            double d18 = (-d12) + d13;
            int iA3 = androidx.compose.ui.graphics.D.a((float) ((-(d17 + d18)) / d16), 0, fArr);
            iA2 = androidx.compose.ui.graphics.D.a((float) ((d17 - d18) / d16), iA3, fArr) + iA3;
            if (iA2 > 1) {
                float f19 = fArr[0];
                float f22 = fArr[1];
                if (f19 > f22) {
                    fArr[0] = f22;
                    fArr[1] = f19;
                } else if (f19 == f22) {
                    iA2--;
                }
            }
        }
        float f23 = (f17 - f16) * 2.0f;
        int iA4 = androidx.compose.ui.graphics.D.a((-f23) / (((f18 - f17) * 2.0f) - f23), iA2, fArr) + iA2;
        float fMin = Math.min(0.0f, 1.0f);
        float fMax = Math.max(0.0f, 1.0f);
        for (int i12 = 0; i12 < iA4; i12++) {
            float f24 = fArr[i12];
            float f25 = (((((((((f13 - f15) * 3.0f) + 1.0f) - 0.0f) * f24) + (((f15 - (f13 * 2.0f)) + 0.0f) * 3.0f)) * f24) + f16) * f24) + 0.0f;
            fMin = Math.min(fMin, f25);
            fMax = Math.max(fMax, f25);
        }
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fMin) << 32) | (Float.floatToRawIntBits(fMax) & 4294967295L);
        this.f26055e = Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32));
        this.f26056f = Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L));
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0091 A[PHI: r3
  0x0091: PHI (r3v33 float) = (r3v6 float), (r3v18 float), (r3v25 float), (r3v38 float), (r3v45 float) binds: [B:131:0x0261, B:121:0x0232, B:95:0x01db, B:48:0x00f1, B:22:0x008d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ca A[PHI: r10
  0x00ca: PHI (r10v37 float) = (r10v17 float), (r10v28 float), (r10v45 float) binds: [B:70:0x0173, B:84:0x01a8, B:37:0x00c8] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.compose.animation.core.N
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final float a(float r26) {
        /*
            Method dump skipped, instructions count: 705
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.F.a(float):float");
    }

    public final boolean equals(@Y61.l Object obj) {
        if (obj instanceof F) {
            F f12 = (F) obj;
            if (this.f26051a == f12.f26051a && this.f26052b == f12.f26052b && this.f26053c == f12.f26053c && this.f26054d == f12.f26054d) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Float.hashCode(this.f26054d) + androidx.appcompat.app.r.d(this.f26053c, androidx.appcompat.app.r.d(this.f26052b, Float.hashCode(this.f26051a) * 31, 31), 31);
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("CubicBezierEasing(a=");
        sb2.append(this.f26051a);
        sb2.append(", b=");
        sb2.append(this.f26052b);
        sb2.append(", c=");
        sb2.append(this.f26053c);
        sb2.append(", d=");
        return androidx.appcompat.app.r.k(')', this.f26054d, sb2);
    }
}
