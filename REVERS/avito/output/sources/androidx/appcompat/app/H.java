package androidx.appcompat.app;

/* compiled from: TwilightCalculator.java */
/* loaded from: classes.dex */
class H {

    /* renamed from: d, reason: collision with root package name */
    public static H f21500d;

    /* renamed from: a, reason: collision with root package name */
    public long f21501a;

    /* renamed from: b, reason: collision with root package name */
    public long f21502b;

    /* renamed from: c, reason: collision with root package name */
    public int f21503c;

    public final void a(double d12, double d13, long j12) {
        double d14 = (0.01720197f * ((j12 - 946728000000L) / 8.64E7f)) + 6.24006f;
        double dSin = (Math.sin(r4 * 3.0f) * 5.236000106378924E-6d) + r.b(2.0f * r4, 3.4906598739326E-4d, r.b(d14, 0.03341960161924362d, d14)) + 1.796593063d + 3.141592653589793d;
        double dSin2 = (Math.sin(2.0d * dSin) * (-0.0069d)) + r.b(d14, 0.0053d, Math.round((r3 - 9.0E-4f) - r4) + 9.0E-4f + ((-d13) / 360.0d));
        double dAsin = Math.asin(Math.sin(0.4092797040939331d) * Math.sin(dSin));
        double d15 = 0.01745329238474369d * d12;
        double dSin3 = (Math.sin(-0.10471975803375244d) - (Math.sin(dAsin) * Math.sin(d15))) / (Math.cos(dAsin) * Math.cos(d15));
        if (dSin3 >= 1.0d) {
            this.f21503c = 1;
            this.f21501a = -1L;
            this.f21502b = -1L;
        } else {
            if (dSin3 <= -1.0d) {
                this.f21503c = 0;
                this.f21501a = -1L;
                this.f21502b = -1L;
                return;
            }
            double dAcos = (float) (Math.acos(dSin3) / 6.283185307179586d);
            this.f21501a = Math.round((dSin2 + dAcos) * 8.64E7d) + 946728000000L;
            long jRound = Math.round((dSin2 - dAcos) * 8.64E7d) + 946728000000L;
            this.f21502b = jRound;
            if (jRound >= j12 || this.f21501a <= j12) {
                this.f21503c = 1;
            } else {
                this.f21503c = 0;
            }
        }
    }
}
