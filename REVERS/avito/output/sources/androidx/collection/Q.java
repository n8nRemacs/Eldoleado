package androidx.collection;

import com.huawei.hms.framework.common.ContainerUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: LongIntMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0001\u0004¨\u0006\u0005"}, d2 = {"Landroidx/collection/Q;", "", "<init>", "()V", "Landroidx/collection/E0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class Q {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25589a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25590b;

    /* renamed from: c, reason: collision with root package name */
    @X41.f
    @Y61.k
    public int[] f25591c;

    /* renamed from: d, reason: collision with root package name */
    @X41.f
    public int f25592d;

    /* renamed from: e, reason: collision with root package name */
    @X41.f
    public int f25593e;

    public /* synthetic */ Q(C42822w c42822w) {
        this();
    }

    @kotlin.X
    public final int a(long j12) {
        int iHashCode = Long.hashCode(j12) * (-862048943);
        int i12 = iHashCode ^ (iHashCode << 16);
        int i13 = i12 & 127;
        int i14 = this.f25592d;
        int i15 = (i12 >>> 7) & i14;
        int i16 = 0;
        while (true) {
            long[] jArr = this.f25589a;
            int i17 = i15 >> 3;
            int i18 = (i15 & 7) << 3;
            long j13 = ((jArr[i17 + 1] << (64 - i18)) & ((-i18) >> 63)) | (jArr[i17] >>> i18);
            long j14 = (i13 * 72340172838076673L) ^ j13;
            for (long j15 = (~j14) & (j14 - 72340172838076673L) & (-9187201950435737472L); j15 != 0; j15 &= j15 - 1) {
                int iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j15) >> 3) + i15) & i14;
                if (this.f25590b[iNumberOfTrailingZeros] == j12) {
                    return iNumberOfTrailingZeros;
                }
            }
            if ((j13 & ((~j13) << 6) & (-9187201950435737472L)) != 0) {
                return -1;
            }
            i16 += 8;
            i15 = (i15 + i16) & i14;
        }
    }

    public final int b(long j12) {
        int iA2 = a(j12);
        if (iA2 >= 0) {
            return this.f25591c[iA2];
        }
        K.f.c("Cannot find value for key " + j12);
        throw null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0060, code lost:
    
        return false;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0068  */
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
            boolean r3 = r1 instanceof androidx.collection.Q
            r4 = 0
            if (r3 != 0) goto Le
            return r4
        Le:
            androidx.collection.Q r1 = (androidx.collection.Q) r1
            int r3 = r1.f25593e
            int r5 = r0.f25593e
            if (r3 == r5) goto L17
            return r4
        L17:
            long[] r3 = r0.f25590b
            int[] r5 = r0.f25591c
            long[] r6 = r0.f25589a
            int r7 = r6.length
            int r7 = r7 + (-2)
            if (r7 < 0) goto L6d
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
            if (r11 == 0) goto L68
            int r11 = r8 - r7
            int r11 = ~r11
            int r11 = r11 >>> 31
            r12 = 8
            int r11 = 8 - r11
            r13 = r4
        L3d:
            if (r13 >= r11) goto L66
            r14 = 255(0xff, double:1.26E-321)
            long r14 = r14 & r9
            r16 = 128(0x80, double:6.3E-322)
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 >= 0) goto L61
            int r14 = r8 << 3
            int r14 = r14 + r13
            r15 = r13
            r12 = r3[r14]
            r14 = r5[r14]
            int r12 = r1.a(r12)
            if (r12 < 0) goto L60
            int[] r13 = r1.f25591c
            r12 = r13[r12]
            if (r14 == r12) goto L5d
            goto L60
        L5d:
            r12 = 8
            goto L62
        L60:
            return r4
        L61:
            r15 = r13
        L62:
            long r9 = r9 >> r12
            int r13 = r15 + 1
            goto L3d
        L66:
            if (r11 != r12) goto L6d
        L68:
            if (r8 == r7) goto L6d
            int r8 = r8 + 1
            goto L23
        L6d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.Q.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        long[] jArr = this.f25590b;
        int[] iArr = this.f25591c;
        long[] jArr2 = this.f25589a;
        int length = jArr2.length - 2;
        int i12 = 0;
        if (length >= 0) {
            int i13 = 0;
            int iHashCode = 0;
            while (true) {
                long j12 = jArr2[i13];
                if ((((~j12) << 7) & j12 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i14 = 8 - ((~(i13 - length)) >>> 31);
                    for (int i15 = 0; i15 < i14; i15++) {
                        if ((255 & j12) < 128) {
                            int i16 = (i13 << 3) + i15;
                            long j13 = jArr[i16];
                            iHashCode += Integer.hashCode(iArr[i16]) ^ Long.hashCode(j13);
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

    @Y61.k
    public final String toString() {
        int i12;
        int i13;
        if (this.f25593e == 0) {
            return "{}";
        }
        StringBuilder sb2 = new StringBuilder("{");
        long[] jArr = this.f25590b;
        int[] iArr = this.f25591c;
        long[] jArr2 = this.f25589a;
        int length = jArr2.length - 2;
        if (length >= 0) {
            int i14 = 0;
            int i15 = 0;
            while (true) {
                long j12 = jArr2[i14];
                if ((((~j12) << 7) & j12 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i16 = 8 - ((~(i14 - length)) >>> 31);
                    int i17 = 0;
                    while (i17 < i16) {
                        if ((255 & j12) < 128) {
                            int i18 = (i14 << 3) + i17;
                            i13 = i14;
                            long j13 = jArr[i18];
                            int i19 = iArr[i18];
                            sb2.append(j13);
                            sb2.append(ContainerUtils.KEY_VALUE_DELIMITER);
                            sb2.append(i19);
                            i15++;
                            if (i15 < this.f25593e) {
                                sb2.append(", ");
                            }
                        } else {
                            i13 = i14;
                        }
                        j12 >>= 8;
                        i17++;
                        i14 = i13;
                    }
                    int i22 = i14;
                    if (i16 != 8) {
                        break;
                    }
                    i12 = i22;
                } else {
                    i12 = i14;
                }
                if (i12 == length) {
                    break;
                }
                i14 = i12 + 1;
            }
        }
        sb2.append('}');
        return sb2.toString();
    }

    public Q() {
        this.f25589a = i1.f25727a;
        this.f25590b = C20202b0.f25680a;
        this.f25591c = M.f25564a;
    }
}
