package androidx.compose.animation;

import com.huawei.updatesdk.service.otaupdate.UpdateStatusCode;
import kotlin.Metadata;

/* compiled from: SplineBasedDecay.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bÀ\u0002\u0018\u00002\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/b;", "", "<init>", "()V", "a", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20254b {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public static final C20254b f26001a = new C20254b();

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final float[] f26002b;

    /* compiled from: SplineBasedDecay.kt */
    @androidx.compose.runtime.internal.P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/b$a;", "", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.b$a */
    public static final /* data */ class a {

        /* renamed from: a, reason: collision with root package name */
        public final float f26003a;

        /* renamed from: b, reason: collision with root package name */
        public final float f26004b;

        public a(float f12, float f13) {
            this.f26003a = f12;
            this.f26004b = f13;
        }

        public final boolean equals(@Y61.l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return Float.compare(this.f26003a, aVar.f26003a) == 0 && Float.compare(this.f26004b, aVar.f26004b) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.f26004b) + (Float.hashCode(this.f26003a) * 31);
        }

        @Y61.k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("FlingResult(distanceCoefficient=");
            sb2.append(this.f26003a);
            sb2.append(", velocityCoefficient=");
            return androidx.appcompat.app.r.k(')', this.f26004b, sb2);
        }
    }

    static {
        float f12;
        float f13;
        float f14;
        float f15;
        float f16;
        float f17;
        float f18;
        float f19;
        float[] fArr = new float[UpdateStatusCode.DialogButton.CONFIRM];
        f26002b = fArr;
        float[] fArr2 = new float[UpdateStatusCode.DialogButton.CONFIRM];
        float f22 = 0.0f;
        float f23 = 0.0f;
        for (int i12 = 0; i12 < 100; i12++) {
            float f24 = i12 / 100;
            float f25 = 1.0f;
            while (true) {
                f12 = ((f25 - f22) / 2.0f) + f22;
                f13 = 1.0f - f12;
                f14 = f12 * 3.0f * f13;
                f15 = f12 * f12 * f12;
                float f26 = (((f12 * 0.35000002f) + (f13 * 0.175f)) * f14) + f15;
                if (Math.abs(f26 - f24) < 1.0E-5d) {
                    break;
                } else if (f26 > f24) {
                    f25 = f12;
                } else {
                    f22 = f12;
                }
            }
            float f27 = 0.5f;
            fArr[i12] = (((f13 * 0.5f) + f12) * f14) + f15;
            float f28 = 1.0f;
            while (true) {
                f16 = ((f28 - f23) / 2.0f) + f23;
                f17 = 1.0f - f16;
                f18 = f16 * 3.0f * f17;
                f19 = f16 * f16 * f16;
                float f29 = (((f17 * f27) + f16) * f18) + f19;
                if (Math.abs(f29 - f24) >= 1.0E-5d) {
                    if (f29 > f24) {
                        f28 = f16;
                    } else {
                        f23 = f16;
                    }
                    f27 = 0.5f;
                }
            }
            fArr2[i12] = (((f16 * 0.35000002f) + (f17 * 0.175f)) * f18) + f19;
        }
        fArr2[100] = 1.0f;
        fArr[100] = 1.0f;
    }

    @Y61.k
    public static a a(float f12) {
        float f13 = 0.0f;
        float f14 = 1.0f;
        float f15 = kotlin.ranges.s.f(f12, 0.0f, 1.0f);
        float f16 = 100;
        int i12 = (int) (f16 * f15);
        if (i12 < 100) {
            float f17 = i12 / f16;
            int i13 = i12 + 1;
            float f18 = i13 / f16;
            float[] fArr = f26002b;
            float f19 = fArr[i12];
            float f22 = (fArr[i13] - f19) / (f18 - f17);
            float fC2 = androidx.appcompat.app.r.c(f15, f17, f22, f19);
            f13 = f22;
            f14 = fC2;
        }
        return new a(f14, f13);
    }
}
