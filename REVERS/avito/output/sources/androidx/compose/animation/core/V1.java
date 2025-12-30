package androidx.compose.animation.core;

import androidx.collection.C20247y0;
import androidx.compose.animation.core.AbstractC20330x;
import androidx.compose.animation.core.C;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: VectorizedAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003BE\b\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00070\u0006\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\t\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000f\u001a\u00020\u000e¢\u0006\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Landroidx/compose/animation/core/V1;", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/P1;", "Landroidx/collection/F;", "timestamps", "Landroidx/collection/J;", "Landroidx/compose/animation/core/U1;", "keyframes", "", "durationMillis", "delayMillis", "Landroidx/compose/animation/core/N;", "defaultEasing", "Landroidx/compose/animation/core/B;", "initialArcMode", "<init>", "(Landroidx/collection/F;Landroidx/collection/J;IILandroidx/compose/animation/core/N;ILkotlin/jvm/internal/w;)V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class V1<V extends AbstractC20330x> implements P1<V> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final androidx.collection.F f26157a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final androidx.collection.J<U1<V>> f26158b;

    /* renamed from: c, reason: collision with root package name */
    public final int f26159c;

    /* renamed from: d, reason: collision with root package name */
    public final int f26160d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.k
    public final N f26161e;

    /* renamed from: f, reason: collision with root package name */
    public final int f26162f;

    /* renamed from: g, reason: collision with root package name */
    @Y61.k
    public int[] f26163g;

    /* renamed from: h, reason: collision with root package name */
    @Y61.k
    public float[] f26164h;

    /* renamed from: i, reason: collision with root package name */
    @Y61.l
    public V f26165i;

    /* renamed from: j, reason: collision with root package name */
    @Y61.l
    public V f26166j;

    /* renamed from: k, reason: collision with root package name */
    @Y61.l
    public V f26167k;

    /* renamed from: l, reason: collision with root package name */
    @Y61.l
    public V f26168l;

    /* renamed from: m, reason: collision with root package name */
    @Y61.k
    public float[] f26169m;

    /* renamed from: n, reason: collision with root package name */
    @Y61.k
    public float[] f26170n;

    /* renamed from: o, reason: collision with root package name */
    @Y61.k
    public C f26171o;

    public V1() {
        throw null;
    }

    public V1(androidx.collection.F f12, androidx.collection.J j12, int i12, int i13, N n12, int i14, C42822w c42822w) {
        this.f26157a = f12;
        this.f26158b = j12;
        this.f26159c = i12;
        this.f26160d = i13;
        this.f26161e = n12;
        this.f26162f = i14;
        this.f26163g = N1.f26112a;
        float[] fArr = N1.f26113b;
        this.f26164h = fArr;
        this.f26169m = fArr;
        this.f26170n = fArr;
        this.f26171o = N1.f26114c;
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V c(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        int i12;
        long jA2 = N1.a(this, j12 / 1000000);
        if (jA2 < 0) {
            return v14;
        }
        i(v12, v13, v14);
        V v15 = this.f26166j;
        int i13 = 0;
        if (this.f26171o == N1.f26114c) {
            AbstractC20330x abstractC20330xE = e((jA2 - 1) * 1000000, v12, v13, v14);
            AbstractC20330x abstractC20330xE2 = e(jA2 * 1000000, v12, v13, v14);
            int f26416d = abstractC20330xE.getF26416d();
            while (i13 < f26416d) {
                v15.e((abstractC20330xE.a(i13) - abstractC20330xE2.a(i13)) * 1000.0f, i13);
                i13++;
            }
            return v15;
        }
        int i14 = (int) jA2;
        androidx.collection.F f12 = this.f26157a;
        int i15 = f12.f25545b;
        if (i15 <= 0) {
            K.f.b("");
            throw null;
        }
        int i16 = i15 - 1;
        int i17 = 0;
        while (true) {
            if (i17 > i16) {
                i12 = -(i17 + 1);
                break;
            }
            i12 = (i17 + i16) >>> 1;
            int i18 = f12.f25544a[i12];
            if (i18 >= i14) {
                if (i18 <= i14) {
                    break;
                }
                i16 = i12 - 1;
            } else {
                i17 = i12 + 1;
            }
        }
        if (i12 < -1) {
            i12 = -(i12 + 2);
        }
        float fH2 = h(i12, i14, false);
        float[] fArr = this.f26170n;
        C.a[][] aVarArr = this.f26171o.f26025a;
        float f13 = aVarArr[0][0].f26026a;
        float f14 = aVarArr[aVarArr.length - 1][0].f26027b;
        if (fH2 < f13) {
            fH2 = f13;
        }
        if (fH2 <= f14) {
            f14 = fH2;
        }
        int length = fArr.length;
        boolean z12 = false;
        for (C.a[] aVarArr2 : aVarArr) {
            int i19 = 0;
            int i22 = 0;
            while (i19 < length - 1) {
                C.a aVar = aVarArr2[i22];
                if (f14 <= aVar.f26027b) {
                    if (aVar.f26041p) {
                        fArr[i19] = aVar.f26042q;
                        fArr[i19 + 1] = aVar.f26043r;
                    } else {
                        aVar.c(f14);
                        fArr[i19] = aVar.a();
                        fArr[i19 + 1] = aVar.b();
                    }
                    z12 = true;
                }
                i19 += 2;
                i22++;
            }
            if (z12) {
                break;
            }
        }
        int length2 = fArr.length;
        while (i13 < length2) {
            v15.e(fArr[i13], i13);
            i13++;
        }
        return v15;
    }

    /* JADX WARN: Type inference failed for: r6v22 */
    /* JADX WARN: Type inference failed for: r6v5, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r6v8 */
    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V e(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        int i12;
        ?? r62;
        V v15;
        V v16;
        int i13;
        C.a[][] aVarArr;
        int i14;
        V v17 = v12;
        V v18 = v13;
        int iA2 = (int) N1.a(this, j12 / 1000000);
        androidx.collection.J<U1<V>> j13 = this.f26158b;
        U1<V> u1B = j13.b(iA2);
        if (u1B != null) {
            return u1B.f26151a;
        }
        if (iA2 >= this.f26159c) {
            return v18;
        }
        if (iA2 <= 0) {
            return v17;
        }
        i(v17, v18, v14);
        V v19 = this.f26165i;
        C c12 = this.f26171o;
        C c13 = N1.f26114c;
        androidx.collection.F f12 = this.f26157a;
        int i15 = 0;
        if (c12 == c13) {
            int i16 = f12.f25545b;
            if (i16 <= 0) {
                K.f.b("");
                throw null;
            }
            int i17 = 1;
            int i18 = i16 - 1;
            int i19 = 0;
            while (true) {
                if (i19 > i18) {
                    int i22 = i17;
                    i12 = -(i19 + (i22 == true ? 1 : 0));
                    r62 = i22;
                    break;
                }
                i12 = (i19 + i18) >>> i17;
                int i23 = f12.f25544a[i12];
                if (i23 >= iA2) {
                    if (i23 <= iA2) {
                        r62 = 1;
                        break;
                    }
                    i18 = i12 - 1;
                } else {
                    i19 = i12 + 1;
                }
                i17 = 1;
            }
            if (i12 < -1) {
                i12 = -(i12 + 2);
            }
            float fH2 = h(i12, iA2, r62);
            U1<V> u1B2 = j13.b(f12.a(i12));
            if (u1B2 != null && (v16 = u1B2.f26151a) != null) {
                v17 = v16;
            }
            U1<V> u1B3 = j13.b(f12.a(i12 + r62));
            if (u1B3 != null && (v15 = u1B3.f26151a) != null) {
                v18 = v15;
            }
            int f26416d = v19.getF26416d();
            for (int i24 = 0; i24 < f26416d; i24++) {
                v19.e((v18.a(i24) * fH2) + ((1 - fH2) * v17.a(i24)), i24);
            }
            return v19;
        }
        int i25 = f12.f25545b;
        if (i25 <= 0) {
            K.f.b("");
            throw null;
        }
        int i26 = i25 - 1;
        int i27 = 0;
        while (true) {
            if (i27 > i26) {
                i13 = -(i27 + 1);
                break;
            }
            i13 = (i27 + i26) >>> 1;
            int i28 = f12.f25544a[i13];
            if (i28 >= iA2) {
                if (i28 <= iA2) {
                    break;
                }
                i26 = i13 - 1;
            } else {
                i27 = i13 + 1;
            }
        }
        if (i13 < -1) {
            i13 = -(i13 + 2);
        }
        float fH3 = h(i13, iA2, false);
        float[] fArr = this.f26169m;
        C.a[][] aVarArr2 = this.f26171o.f26025a;
        int length = aVarArr2.length - 1;
        float f13 = aVarArr2[0][0].f26026a;
        float f14 = aVarArr2[length][0].f26027b;
        int length2 = fArr.length;
        if (fH3 < f13 || fH3 > f14) {
            if (fH3 > f14) {
                f13 = f14;
            } else {
                length = 0;
            }
            float f15 = fH3 - f13;
            int i29 = 0;
            int i32 = 0;
            while (i29 < length2 - 1) {
                C.a aVar = aVarArr2[length][i32];
                boolean z12 = aVar.f26041p;
                float f16 = aVar.f26043r;
                float f17 = aVar.f26042q;
                if (z12) {
                    float f18 = aVar.f26026a;
                    float f19 = aVar.f26036k;
                    aVarArr = aVarArr2;
                    float f22 = aVar.f26030e;
                    i14 = length;
                    float f23 = aVar.f26028c;
                    fArr[i29] = (f17 * f15) + androidx.appcompat.app.r.c(f22, f23, (f13 - f18) * f19, f23);
                    float f24 = (f13 - f18) * f19;
                    float f25 = aVar.f26031f;
                    float f26 = aVar.f26029d;
                    fArr[i29 + 1] = (f16 * f15) + androidx.appcompat.app.r.c(f25, f26, f24, f26);
                } else {
                    aVarArr = aVarArr2;
                    i14 = length;
                    aVar.c(f13);
                    fArr[i29] = (aVar.a() * f15) + (aVar.f26039n * aVar.f26033h) + f17;
                    fArr[i29 + 1] = (aVar.b() * f15) + (aVar.f26040o * aVar.f26034i) + f16;
                }
                i29 += 2;
                i32++;
                aVarArr2 = aVarArr;
                length = i14;
            }
        } else {
            int length3 = aVarArr2.length;
            int i33 = 0;
            boolean z13 = false;
            while (i33 < length3) {
                int i34 = i15;
                int i35 = i34;
                while (i34 < length2 - 1) {
                    C.a aVar2 = aVarArr2[i33][i35];
                    if (fH3 <= aVar2.f26027b) {
                        if (aVar2.f26041p) {
                            float f27 = aVar2.f26026a;
                            float f28 = aVar2.f26036k;
                            float f29 = aVar2.f26030e;
                            float f32 = aVar2.f26028c;
                            fArr[i34] = androidx.appcompat.app.r.c(f29, f32, (fH3 - f27) * f28, f32);
                            float f33 = aVar2.f26031f;
                            float f34 = aVar2.f26029d;
                            fArr[i34 + 1] = androidx.appcompat.app.r.c(f33, f34, (fH3 - f27) * f28, f34);
                        } else {
                            aVar2.c(fH3);
                            fArr[i34] = (aVar2.f26039n * aVar2.f26033h) + aVar2.f26042q;
                            fArr[i34 + 1] = (aVar2.f26040o * aVar2.f26034i) + aVar2.f26043r;
                        }
                        z13 = true;
                    }
                    i34 += 2;
                    i35++;
                }
                if (z13) {
                    break;
                }
                i33++;
                i15 = 0;
            }
        }
        int length4 = fArr.length;
        for (int i36 = 0; i36 < length4; i36++) {
            v19.e(fArr[i36], i36);
        }
        return v19;
    }

    @Override // androidx.compose.animation.core.P1
    /* renamed from: f, reason: from getter */
    public final int getF26160d() {
        return this.f26160d;
    }

    @Override // androidx.compose.animation.core.P1
    /* renamed from: g, reason: from getter */
    public final int getF26159c() {
        return this.f26159c;
    }

    public final float h(int i12, int i13, boolean z12) {
        N n12;
        float f12;
        androidx.collection.F f13 = this.f26157a;
        if (i12 >= f13.f25545b - 1) {
            f12 = i13;
        } else {
            int iA2 = f13.a(i12);
            int iA3 = f13.a(i12 + 1);
            if (i13 == iA2) {
                f12 = iA2;
            } else {
                int i14 = iA3 - iA2;
                U1<V> u1B = this.f26158b.b(iA2);
                if (u1B == null || (n12 = u1B.f26152b) == null) {
                    n12 = this.f26161e;
                }
                float f14 = i14;
                float fA2 = n12.a((i13 - iA2) / f14);
                if (z12) {
                    return fA2;
                }
                f12 = (f14 * fA2) + iA2;
            }
        }
        return f12 / 1000;
    }

    public final void i(V v12, V v13, V v14) {
        float[] fArr;
        boolean z12 = this.f26171o != N1.f26114c;
        V v15 = this.f26165i;
        androidx.collection.J<U1<V>> j12 = this.f26158b;
        androidx.collection.F f12 = this.f26157a;
        if (v15 == null) {
            this.f26165i = (V) v12.c();
            this.f26166j = (V) v14.c();
            int i12 = f12.f25545b;
            float[] fArr2 = new float[i12];
            for (int i13 = 0; i13 < i12; i13++) {
                fArr2[i13] = f12.a(i13) / 1000;
            }
            this.f26164h = fArr2;
            int i14 = f12.f25545b;
            int[] iArr = new int[i14];
            for (int i15 = 0; i15 < i14; i15++) {
                U1<V> u1B = j12.b(f12.a(i15));
                int i16 = u1B != null ? u1B.f26153c : this.f26162f;
                B.f26018b.getClass();
                if (i16 != 0) {
                    z12 = true;
                }
                iArr[i15] = i16;
            }
            this.f26163g = iArr;
        }
        if (z12) {
            if (this.f26171o != N1.f26114c && kotlin.jvm.internal.L.f(this.f26167k, v12) && kotlin.jvm.internal.L.f(this.f26168l, v13)) {
                return;
            }
            this.f26167k = v12;
            this.f26168l = v13;
            int f26416d = v12.getF26416d() + (v12.getF26416d() % 2);
            this.f26169m = new float[f26416d];
            this.f26170n = new float[f26416d];
            int i17 = f12.f25545b;
            float[][] fArr3 = new float[i17][];
            for (int i18 = 0; i18 < i17; i18++) {
                int iA2 = f12.a(i18);
                U1<V> u1B2 = j12.b(iA2);
                if (iA2 == 0 && u1B2 == null) {
                    fArr = new float[f26416d];
                    for (int i19 = 0; i19 < f26416d; i19++) {
                        fArr[i19] = v12.a(i19);
                    }
                } else if (iA2 == this.f26159c && u1B2 == null) {
                    fArr = new float[f26416d];
                    for (int i22 = 0; i22 < f26416d; i22++) {
                        fArr[i22] = v13.a(i22);
                    }
                } else {
                    fArr = new float[f26416d];
                    for (int i23 = 0; i23 < f26416d; i23++) {
                        fArr[i23] = u1B2.f26151a.a(i23);
                    }
                }
                fArr3[i18] = fArr;
            }
            this.f26171o = new C(this.f26163g, this.f26164h, fArr3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public V1(Map map, int i12, int i13, int i14, C42822w c42822w) {
        int i15 = (i14 & 4) != 0 ? 0 : i13;
        C20247y0 c20247y0 = new C20247y0(map.size() + 2, null);
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            c20247y0.d(((Number) ((Map.Entry) it.next()).getKey()).intValue());
        }
        if (!map.containsKey(0)) {
            c20247y0.c();
        }
        if (!map.containsKey(Integer.valueOf(i12))) {
            c20247y0.d(i12);
        }
        int i16 = c20247y0.f25545b;
        if (i16 != 0) {
            Arrays.sort(c20247y0.f25544a, 0, i16);
        }
        androidx.collection.A0 a02 = new androidx.collection.A0(0, 1, null);
        for (Map.Entry entry : map.entrySet()) {
            int iIntValue = ((Number) entry.getKey()).intValue();
            kotlin.Q q12 = (kotlin.Q) entry.getValue();
            AbstractC20330x abstractC20330x = (AbstractC20330x) q12.f406619b;
            N n12 = (N) q12.f406620c;
            B.f26018b.getClass();
            a02.h(iIntValue, new U1(abstractC20330x, n12, 0, null));
        }
        O o12 = Q.f26124d;
        B.f26018b.getClass();
        this(c20247y0, a02, i12, i15, o12, 0, null);
    }
}
