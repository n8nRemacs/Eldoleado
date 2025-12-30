package androidx.compose.animation.core;

import com.huawei.updatesdk.service.otaupdate.UpdateStatusCode;
import java.util.Arrays;
import kotlin.Metadata;

/* compiled from: ArcSpline.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/C;", "", "a", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class C {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final a[][] f26025a;

    /* compiled from: ArcSpline.kt */
    @androidx.compose.runtime.internal.P
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/C$a;", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        public final float f26026a;

        /* renamed from: b, reason: collision with root package name */
        public final float f26027b;

        /* renamed from: c, reason: collision with root package name */
        public final float f26028c;

        /* renamed from: d, reason: collision with root package name */
        public final float f26029d;

        /* renamed from: e, reason: collision with root package name */
        public final float f26030e;

        /* renamed from: f, reason: collision with root package name */
        public final float f26031f;

        /* renamed from: g, reason: collision with root package name */
        public final float f26032g;

        /* renamed from: h, reason: collision with root package name */
        public float f26033h;

        /* renamed from: i, reason: collision with root package name */
        public float f26034i;

        /* renamed from: j, reason: collision with root package name */
        @Y61.k
        public final float[] f26035j;

        /* renamed from: k, reason: collision with root package name */
        public final float f26036k;

        /* renamed from: l, reason: collision with root package name */
        public final float f26037l;

        /* renamed from: m, reason: collision with root package name */
        public final float f26038m;

        /* renamed from: n, reason: collision with root package name */
        @X41.f
        public final float f26039n;

        /* renamed from: o, reason: collision with root package name */
        @X41.f
        public final float f26040o;

        /* renamed from: p, reason: collision with root package name */
        @X41.f
        public final boolean f26041p;

        /* renamed from: q, reason: collision with root package name */
        @X41.f
        public final float f26042q;

        /* renamed from: r, reason: collision with root package name */
        @X41.f
        public final float f26043r;

        public a(float f12, float f13, float f14, float f15, float f16, float f17, int i12) {
            float f18;
            this.f26026a = f12;
            this.f26027b = f13;
            this.f26028c = f14;
            this.f26029d = f15;
            this.f26030e = f16;
            this.f26031f = f17;
            float f19 = f16 - f14;
            float f22 = f17 - f15;
            boolean z12 = true;
            boolean z13 = i12 == 1 || (i12 == 4 ? f22 > 0.0f : !(i12 != 5 || f22 >= 0.0f));
            float f23 = z13 ? -1.0f : 1.0f;
            this.f26038m = f23;
            float f24 = 1 / (f13 - f12);
            this.f26036k = f24;
            this.f26035j = new float[UpdateStatusCode.DialogButton.CONFIRM];
            boolean z14 = i12 == 3;
            if (z14 || Math.abs(f19) < 0.001f || Math.abs(f22) < 0.001f) {
                float fHypot = (float) Math.hypot(f22, f19);
                this.f26032g = fHypot;
                this.f26037l = fHypot * f24;
                this.f26042q = f19 * f24;
                this.f26043r = f22 * f24;
                this.f26039n = Float.NaN;
                this.f26040o = Float.NaN;
            } else {
                this.f26039n = f19 * f23;
                this.f26040o = f22 * (-f23);
                this.f26042q = z13 ? f16 : f14;
                this.f26043r = z13 ? f15 : f17;
                float f25 = f16 - f14;
                float f26 = f15 - f17;
                float[] fArr = D.f26048a;
                int i13 = 90;
                float f27 = 90;
                float f28 = f26;
                float fHypot2 = 0.0f;
                float f29 = 0.0f;
                int i14 = 1;
                while (true) {
                    double radians = (float) Math.toRadians((i14 * 90.0d) / i13);
                    float fSin = ((float) Math.sin(radians)) * f25;
                    float fCos = ((float) Math.cos(radians)) * f26;
                    f18 = f27;
                    fHypot2 += (float) Math.hypot(fSin - f29, fCos - f28);
                    fArr[i14] = fHypot2;
                    i13 = 90;
                    if (i14 == 90) {
                        break;
                    }
                    i14++;
                    f28 = fCos;
                    f27 = f18;
                    f29 = fSin;
                }
                this.f26032g = fHypot2;
                int i15 = 1;
                while (true) {
                    fArr[i15] = fArr[i15] / fHypot2;
                    if (i15 == 90) {
                        break;
                    } else {
                        i15++;
                    }
                }
                float[] fArr2 = this.f26035j;
                int length = fArr2.length;
                for (int i16 = 0; i16 < length; i16++) {
                    float f32 = i16 / 100.0f;
                    int iBinarySearch = Arrays.binarySearch(fArr, 0, 91, f32);
                    if (iBinarySearch >= 0) {
                        fArr2[i16] = iBinarySearch / f18;
                    } else if (iBinarySearch == -1) {
                        fArr2[i16] = 0.0f;
                    } else {
                        int i17 = -iBinarySearch;
                        int i18 = i17 - 2;
                        float f33 = i18;
                        float f34 = fArr[i18];
                        fArr2[i16] = (((f32 - f34) / (fArr[i17 - 1] - f34)) + f33) / f18;
                    }
                }
                this.f26037l = this.f26032g * this.f26036k;
                z12 = z14;
            }
            this.f26041p = z12;
        }

        public final float a() {
            float f12 = this.f26039n * this.f26034i;
            return f12 * this.f26038m * (this.f26037l / ((float) Math.hypot(f12, (-this.f26040o) * this.f26033h)));
        }

        public final float b() {
            float f12 = this.f26039n * this.f26034i;
            float f13 = (-this.f26040o) * this.f26033h;
            return f13 * this.f26038m * (this.f26037l / ((float) Math.hypot(f12, f13)));
        }

        public final void c(float f12) {
            float f13 = (this.f26038m == -1.0f ? this.f26027b - f12 : f12 - this.f26026a) * this.f26036k;
            float fC2 = 0.0f;
            if (f13 > 0.0f) {
                fC2 = 1.0f;
                if (f13 < 1.0f) {
                    float f14 = f13 * 100;
                    int i12 = (int) f14;
                    float[] fArr = this.f26035j;
                    float f15 = fArr[i12];
                    fC2 = androidx.appcompat.app.r.c(fArr[i12 + 1], f15, f14 - i12, f15);
                }
            }
            double d12 = fC2 * 1.5707964f;
            this.f26033h = (float) Math.sin(d12);
            this.f26034i = (float) Math.cos(d12);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0023 A[PHI: r10
  0x0023: PHI (r10v1 int) = (r10v0 int), (r10v6 int), (r10v7 int) binds: [B:5:0x0014, B:10:0x001d, B:12:0x0020] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public C(@Y61.k int[] r27, @Y61.k float[] r28, @Y61.k float[][] r29) {
        /*
            r26 = this;
            r0 = r28
            r26.<init>()
            int r1 = r0.length
            r2 = 1
            int r1 = r1 - r2
            androidx.compose.animation.core.C$a[][] r3 = new androidx.compose.animation.core.C.a[r1][]
            r4 = 0
            r6 = r2
            r7 = r6
            r5 = r4
        Le:
            if (r5 >= r1) goto L74
            r8 = r27[r5]
            r9 = 2
            r10 = 3
            if (r8 == 0) goto L23
            if (r8 == r2) goto L2c
            if (r8 == r9) goto L2a
            if (r8 == r10) goto L25
            r10 = 4
            if (r8 == r10) goto L23
            r10 = 5
            if (r8 == r10) goto L23
            goto L2e
        L23:
            r7 = r10
            goto L2e
        L25:
            if (r6 != r2) goto L2c
            goto L2a
        L28:
            r7 = r6
            goto L2e
        L2a:
            r6 = r9
            goto L28
        L2c:
            r6 = r2
            goto L28
        L2e:
            r8 = r29[r5]
            int r18 = r5 + 1
            r19 = r29[r18]
            r20 = r0[r5]
            r21 = r0[r18]
            int r10 = r8.length
            int r10 = r10 / r9
            int r11 = r8.length
            int r11 = r11 % r9
            int r9 = r11 + r10
            androidx.compose.animation.core.C$a[] r15 = new androidx.compose.animation.core.C.a[r9]
            r14 = r4
        L41:
            if (r14 >= r9) goto L6d
            int r10 = r14 * 2
            androidx.compose.animation.core.C$a r22 = new androidx.compose.animation.core.C$a
            r13 = r8[r10]
            int r11 = r10 + 1
            r16 = r8[r11]
            r17 = r19[r10]
            r23 = r19[r11]
            r10 = r22
            r11 = r20
            r12 = r21
            r24 = r14
            r14 = r16
            r25 = r15
            r15 = r17
            r16 = r23
            r17 = r7
            r10.<init>(r11, r12, r13, r14, r15, r16, r17)
            r25[r24] = r22
            int r14 = r24 + 1
            r15 = r25
            goto L41
        L6d:
            r25 = r15
            r3[r5] = r25
            r5 = r18
            goto Le
        L74:
            r5 = r26
            r5.f26025a = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.C.<init>(int[], float[], float[][]):void");
    }
}
