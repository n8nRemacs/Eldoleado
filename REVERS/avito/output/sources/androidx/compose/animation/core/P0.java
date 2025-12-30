package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: SpringSimulation.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/P0;", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class P0 {

    /* renamed from: a, reason: collision with root package name */
    public float f26118a;

    /* renamed from: b, reason: collision with root package name */
    public double f26119b;

    /* renamed from: c, reason: collision with root package name */
    public float f26120c;

    public final long a(float f12, float f13, long j12) {
        double dSin;
        double dCos;
        double dExp;
        double dExp2;
        float f14 = f12 - this.f26118a;
        double d12 = j12 / 1000.0d;
        float f15 = this.f26120c;
        double d13 = f15 * f15;
        double d14 = this.f26119b;
        double d15 = (-f15) * d14;
        if (f15 > 1.0f) {
            double dSqrt = Math.sqrt(d13 - 1) * d14;
            double d16 = d15 + dSqrt;
            double d17 = d15 - dSqrt;
            double d18 = f14;
            double d19 = ((d17 * d18) - f13) / (d17 - d16);
            double d22 = d18 - d19;
            double d23 = d17 * d12;
            double d24 = d12 * d16;
            dSin = (Math.exp(d24) * d19) + (Math.exp(d23) * d22);
            dExp = Math.exp(d23) * d22 * d17;
            dExp2 = Math.exp(d24) * d19 * d16;
        } else {
            if (f15 != 1.0f) {
                double d25 = 1;
                double dSqrt2 = Math.sqrt(d25 - d13) * d14;
                double d26 = f14;
                double d27 = (((-d15) * d26) + f13) * (d25 / dSqrt2);
                double d28 = dSqrt2 * d12;
                double d29 = d12 * d15;
                dSin = ((Math.sin(d28) * d27) + (Math.cos(d28) * d26)) * Math.exp(d29);
                dCos = (((Math.cos(d28) * dSqrt2 * d27) + (Math.sin(d28) * (-dSqrt2) * d26)) * Math.exp(d29)) + (d15 * dSin);
                float f16 = (float) dCos;
                return (Float.floatToRawIntBits(f16) & 4294967295L) | (Float.floatToRawIntBits((float) (dSin + this.f26118a)) << 32);
            }
            double d32 = f14;
            double d33 = (d14 * d32) + f13;
            double d34 = (-d14) * d12;
            double d35 = (d12 * d33) + d32;
            dSin = Math.exp(d34) * d35;
            dExp = Math.exp(d34) * d35 * (-this.f26119b);
            dExp2 = Math.exp(d34) * d33;
        }
        dCos = dExp2 + dExp;
        float f162 = (float) dCos;
        return (Float.floatToRawIntBits(f162) & 4294967295L) | (Float.floatToRawIntBits((float) (dSin + this.f26118a)) << 32);
    }
}
