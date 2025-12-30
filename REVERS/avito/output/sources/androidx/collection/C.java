package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: IntIntMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0001\u0004¨\u0006\u0005"}, d2 = {"Landroidx/collection/C;", "", "<init>", "()V", "Landroidx/collection/x0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class C {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25526a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    @Y61.k
    public int[] f25527b;

    /* renamed from: c, reason: collision with root package name */
    @X41.f
    @Y61.k
    public int[] f25528c;

    /* renamed from: d, reason: collision with root package name */
    @X41.f
    public int f25529d;

    /* renamed from: e, reason: collision with root package name */
    @X41.f
    public int f25530e;

    public /* synthetic */ C(C42822w c42822w) {
        this();
    }

    @kotlin.X
    public final int a(int i12) {
        int iHashCode = Integer.hashCode(i12) * (-862048943);
        int i13 = iHashCode ^ (iHashCode << 16);
        int i14 = i13 & 127;
        int i15 = this.f25529d;
        int i16 = (i13 >>> 7) & i15;
        int i17 = 0;
        while (true) {
            long[] jArr = this.f25526a;
            int i18 = i16 >> 3;
            int i19 = (i16 & 7) << 3;
            long j12 = ((jArr[i18 + 1] << (64 - i19)) & ((-i19) >> 63)) | (jArr[i18] >>> i19);
            long j13 = (i14 * 72340172838076673L) ^ j12;
            for (long j14 = (~j13) & (j13 - 72340172838076673L) & (-9187201950435737472L); j14 != 0; j14 &= j14 - 1) {
                int iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j14) >> 3) + i16) & i15;
                if (this.f25527b[iNumberOfTrailingZeros] == i12) {
                    return iNumberOfTrailingZeros;
                }
            }
            if ((j12 & ((~j12) << 6) & (-9187201950435737472L)) != 0) {
                return -1;
            }
            i17 += 8;
            i16 = (i16 + i17) & i15;
        }
    }

    public final int b(int i12) {
        int iA2 = a(i12);
        if (iA2 >= 0) {
            return this.f25528c[iA2];
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x005b, code lost:
    
        return false;
     */
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
            boolean r3 = r1 instanceof androidx.collection.C
            r4 = 0
            if (r3 != 0) goto Le
            return r4
        Le:
            androidx.collection.C r1 = (androidx.collection.C) r1
            int r3 = r1.f25530e
            int r5 = r0.f25530e
            if (r3 == r5) goto L17
            return r4
        L17:
            int[] r3 = r0.f25527b
            int[] r5 = r0.f25528c
            long[] r6 = r0.f25526a
            int r7 = r6.length
            int r7 = r7 + (-2)
            if (r7 < 0) goto L64
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
            if (r11 == 0) goto L66
            int r11 = r8 - r7
            int r11 = ~r11
            int r11 = r11 >>> 31
            r12 = 8
            int r11 = 8 - r11
            r13 = r4
        L3d:
            if (r13 >= r11) goto L61
            r14 = 255(0xff, double:1.26E-321)
            long r14 = r14 & r9
            r16 = 128(0x80, double:6.3E-322)
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 >= 0) goto L5c
            int r14 = r8 << 3
            int r14 = r14 + r13
            r15 = r3[r14]
            r14 = r5[r14]
            int r15 = r1.a(r15)
            if (r15 < 0) goto L5b
            int[] r2 = r1.f25528c
            r2 = r2[r15]
            if (r14 == r2) goto L5c
        L5b:
            return r4
        L5c:
            long r9 = r9 >> r12
            int r13 = r13 + 1
            r2 = 1
            goto L3d
        L61:
            if (r11 != r12) goto L64
            goto L66
        L64:
            r1 = 1
            goto L6c
        L66:
            if (r8 == r7) goto L64
            int r8 = r8 + 1
            r2 = 1
            goto L23
        L6c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.C.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        int[] iArr = this.f25527b;
        int[] iArr2 = this.f25528c;
        long[] jArr = this.f25526a;
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
                            int i17 = iArr[i16];
                            iHashCode += Integer.hashCode(iArr2[i16]) ^ Integer.hashCode(i17);
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x0066 A[PHI: r8
  0x0066: PHI (r8v2 int) = (r8v1 int), (r8v3 int) binds: [B:10:0x002c, B:19:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
    @Y61.k
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String toString() {
        /*
            r18 = this;
            r0 = r18
            int r1 = r0.f25530e
            if (r1 != 0) goto L9
            java.lang.String r1 = "{}"
            return r1
        L9:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "{"
            r1.<init>(r2)
            int[] r2 = r0.f25527b
            int[] r3 = r0.f25528c
            long[] r4 = r0.f25526a
            int r5 = r4.length
            int r5 = r5 + (-2)
            if (r5 < 0) goto L6b
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
            if (r11 == 0) goto L66
            int r11 = r7 - r5
            int r11 = ~r11
            int r11 = r11 >>> 31
            r12 = 8
            int r11 = 8 - r11
            r13 = r6
        L38:
            if (r13 >= r11) goto L64
            r14 = 255(0xff, double:1.26E-321)
            long r14 = r14 & r9
            r16 = 128(0x80, double:6.3E-322)
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 >= 0) goto L60
            int r14 = r7 << 3
            int r14 = r14 + r13
            r15 = r2[r14]
            r14 = r3[r14]
            r1.append(r15)
            java.lang.String r15 = "="
            r1.append(r15)
            r1.append(r14)
            int r8 = r8 + 1
            int r14 = r0.f25530e
            if (r8 >= r14) goto L60
            java.lang.String r14 = ", "
            r1.append(r14)
        L60:
            long r9 = r9 >> r12
            int r13 = r13 + 1
            goto L38
        L64:
            if (r11 != r12) goto L6b
        L66:
            if (r7 == r5) goto L6b
            int r7 = r7 + 1
            goto L1e
        L6b:
            r2 = 125(0x7d, float:1.75E-43)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.C.toString():java.lang.String");
    }

    public C() {
        this.f25526a = i1.f25727a;
        int[] iArr = M.f25564a;
        this.f25527b = iArr;
        this.f25528c = iArr;
    }
}
