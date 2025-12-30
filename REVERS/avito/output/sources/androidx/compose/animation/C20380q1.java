package androidx.compose.animation;

import kotlin.Metadata;

/* compiled from: FlingCalculator.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/q1;", "", "a", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.q1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20380q1 {

    /* renamed from: a, reason: collision with root package name */
    public final float f26555a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.ui.unit.d f26556b;

    /* renamed from: c, reason: collision with root package name */
    public final float f26557c;

    /* compiled from: FlingCalculator.kt */
    @androidx.compose.runtime.internal.P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/q1$a;", "", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.q1$a */
    public static final /* data */ class a {

        /* renamed from: a, reason: collision with root package name */
        public final float f26558a;

        /* renamed from: b, reason: collision with root package name */
        public final float f26559b;

        /* renamed from: c, reason: collision with root package name */
        public final long f26560c;

        public a(float f12, float f13, long j12) {
            this.f26558a = f12;
            this.f26559b = f13;
            this.f26560c = j12;
        }

        public final boolean equals(@Y61.l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return Float.compare(this.f26558a, aVar.f26558a) == 0 && Float.compare(this.f26559b, aVar.f26559b) == 0 && this.f26560c == aVar.f26560c;
        }

        public final int hashCode() {
            return Long.hashCode(this.f26560c) + androidx.appcompat.app.r.d(this.f26559b, Float.hashCode(this.f26558a) * 31, 31);
        }

        @Y61.k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("FlingInfo(initialVelocity=");
            sb2.append(this.f26558a);
            sb2.append(", distance=");
            sb2.append(this.f26559b);
            sb2.append(", duration=");
            return androidx.appcompat.app.r.u(sb2, this.f26560c, ')');
        }
    }

    public C20380q1(float f12, @Y61.k androidx.compose.ui.unit.d dVar) {
        this.f26555a = f12;
        this.f26556b = dVar;
        float f42846b = dVar.getF42846b();
        float f13 = C20382r1.f26572a;
        this.f26557c = f42846b * 386.0878f * 160.0f * 0.84f;
    }

    @Y61.k
    public final a a(float f12) {
        double dB2 = b(f12);
        double d12 = C20382r1.f26572a;
        double d13 = d12 - 1.0d;
        return new a(f12, (float) (Math.exp((d12 / d13) * dB2) * this.f26555a * this.f26557c), (long) (Math.exp(dB2 / d13) * 1000.0d));
    }

    public final double b(float f12) {
        C20254b c20254b = C20254b.f26001a;
        float f13 = this.f26555a * this.f26557c;
        c20254b.getClass();
        return Math.log((Math.abs(f12) * 0.35f) / f13);
    }
}
