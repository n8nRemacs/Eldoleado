package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: ObjectFloatMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\t\b\u0004¢\u0006\u0004\b\u0003\u0010\u0004\u0082\u0001\u0001\u0005¨\u0006\u0006"}, d2 = {"Landroidx/collection/V0;", "K", "", "<init>", "()V", "Landroidx/collection/L0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class V0<K> {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25642a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    @Y61.k
    public Object[] f25643b;

    /* renamed from: c, reason: collision with root package name */
    @X41.f
    @Y61.k
    public float[] f25644c;

    /* renamed from: d, reason: collision with root package name */
    @X41.f
    public int f25645d;

    /* renamed from: e, reason: collision with root package name */
    @X41.f
    public int f25646e;

    public /* synthetic */ V0(C42822w c42822w) {
        this();
    }

    @kotlin.X
    public final int a(K k12) {
        int i12 = 0;
        int iHashCode = (k12 != null ? k12.hashCode() : 0) * (-862048943);
        int i13 = iHashCode ^ (iHashCode << 16);
        int i14 = i13 & 127;
        int i15 = this.f25645d;
        int i16 = i13 >>> 7;
        while (true) {
            int i17 = i16 & i15;
            long[] jArr = this.f25642a;
            int i18 = i17 >> 3;
            int i19 = (i17 & 7) << 3;
            long j12 = ((jArr[i18 + 1] << (64 - i19)) & ((-i19) >> 63)) | (jArr[i18] >>> i19);
            long j13 = (i14 * 72340172838076673L) ^ j12;
            for (long j14 = (~j13) & (j13 - 72340172838076673L) & (-9187201950435737472L); j14 != 0; j14 &= j14 - 1) {
                int iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j14) >> 3) + i17) & i15;
                if (kotlin.jvm.internal.L.f(this.f25643b[iNumberOfTrailingZeros], k12)) {
                    return iNumberOfTrailingZeros;
                }
            }
            if ((j12 & ((~j12) << 6) & (-9187201950435737472L)) != 0) {
                return -1;
            }
            i12 += 8;
            i16 = i17 + i12;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x005e, code lost:
    
        return false;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(@Y61.l java.lang.Object r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = 1
            if (r1 != r0) goto L8
            return r2
        L8:
            boolean r3 = r1 instanceof androidx.collection.V0
            r4 = 0
            if (r3 != 0) goto Le
            return r4
        Le:
            androidx.collection.V0 r1 = (androidx.collection.V0) r1
            int r3 = r1.f25646e
            int r5 = r0.f25646e
            if (r3 == r5) goto L17
            return r4
        L17:
            java.lang.Object[] r3 = r0.f25643b
            float[] r5 = r0.f25644c
            long[] r6 = r0.f25642a
            int r7 = r6.length
            int r7 = r7 + (-2)
            if (r7 < 0) goto L67
            r8 = r4
        L23:
            r9 = r6[r8]
            long r11 = ~r9
            r13 = 7
            long r11 = r11 << r13
            long r11 = r11 & r9
            r13 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r11 = r11 & r13
            int r11 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r11 == 0) goto L69
            int r11 = r8 - r7
            int r11 = ~r11
            int r11 = r11 >>> 31
            r12 = 8
            int r11 = 8 - r11
            r13 = r4
        L3d:
            if (r13 >= r11) goto L64
            r14 = 255(0xff, double:1.26E-321)
            long r14 = r14 & r9
            r16 = 128(0x80, double:6.3E-322)
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 >= 0) goto L5f
            int r14 = r8 << 3
            int r14 = r14 + r13
            r15 = r3[r14]
            r14 = r5[r14]
            int r15 = r1.a(r15)
            if (r15 < 0) goto L5e
            float[] r2 = r1.f25644c
            r2 = r2[r15]
            int r2 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r2 != 0) goto L5e
            goto L5f
        L5e:
            return r4
        L5f:
            long r9 = r9 >> r12
            int r13 = r13 + 1
            r2 = 1
            goto L3d
        L64:
            if (r11 != r12) goto L67
            goto L69
        L67:
            r1 = 1
            goto L6f
        L69:
            if (r8 == r7) goto L67
            int r8 = r8 + 1
            r2 = 1
            goto L23
        L6f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.V0.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        Object[] objArr = this.f25643b;
        float[] fArr = this.f25644c;
        long[] jArr = this.f25642a;
        int length = jArr.length - 2;
        int i12 = 0;
        if (length >= 0) {
            int i13 = 0;
            int iHashCode = 0;
            while (true) {
                long j12 = jArr[i13];
                if ((((~j12) << 7) & j12 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i14 = 8 - ((~(i13 - length)) >>> 31);
                    for (int i15 = 0; i15 < i14; i15++) {
                        if ((255 & j12) < 128) {
                            int i16 = (i13 << 3) + i15;
                            Object obj = objArr[i16];
                            iHashCode += Float.hashCode(fArr[i16]) ^ (obj != null ? obj.hashCode() : 0);
                        }
                        j12 >>= 8;
                    }
                    if (i14 != 8) {
                        return iHashCode;
                    }
                }
                if (i13 == length) {
                    i12 = iHashCode;
                    break;
                }
                i13++;
            }
        }
        return i12;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x006a A[PHI: r8
  0x006a: PHI (r8v2 int) = (r8v1 int), (r8v3 int) binds: [B:10:0x002c, B:22:0x0068] A[DONT_GENERATE, DONT_INLINE]] */
    @Y61.k
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String toString() {
        /*
            r18 = this;
            r0 = r18
            int r1 = r0.f25646e
            if (r1 != 0) goto L9
            java.lang.String r1 = "{}"
            return r1
        L9:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "{"
            r1.<init>(r2)
            java.lang.Object[] r2 = r0.f25643b
            float[] r3 = r0.f25644c
            long[] r4 = r0.f25642a
            int r5 = r4.length
            int r5 = r5 + (-2)
            if (r5 < 0) goto L6f
            r6 = 0
            r7 = r6
            r8 = r7
        L1e:
            r9 = r4[r7]
            long r11 = ~r9
            r13 = 7
            long r11 = r11 << r13
            long r11 = r11 & r9
            r13 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r11 = r11 & r13
            int r11 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r11 == 0) goto L6a
            int r11 = r7 - r5
            int r11 = ~r11
            int r11 = r11 >>> 31
            r12 = 8
            int r11 = 8 - r11
            r13 = r6
        L38:
            if (r13 >= r11) goto L68
            r14 = 255(0xff, double:1.26E-321)
            long r14 = r14 & r9
            r16 = 128(0x80, double:6.3E-322)
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 >= 0) goto L64
            int r14 = r7 << 3
            int r14 = r14 + r13
            r15 = r2[r14]
            r14 = r3[r14]
            if (r15 != r0) goto L4e
            java.lang.String r15 = "(this)"
        L4e:
            r1.append(r15)
            java.lang.String r15 = "="
            r1.append(r15)
            r1.append(r14)
            int r8 = r8 + 1
            int r14 = r0.f25646e
            if (r8 >= r14) goto L64
            java.lang.String r14 = ", "
            r1.append(r14)
        L64:
            long r9 = r9 >> r12
            int r13 = r13 + 1
            goto L38
        L68:
            if (r11 != r12) goto L6f
        L6a:
            if (r7 == r5) goto L6f
            int r7 = r7 + 1
            goto L1e
        L6f:
            r2 = 125(0x7d, float:1.75E-43)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.V0.toString():java.lang.String");
    }

    public V0() {
        this.f25642a = i1.f25727a;
        this.f25643b = K.a.f9206c;
        this.f25644c = C20246y.f25833a;
    }
}
