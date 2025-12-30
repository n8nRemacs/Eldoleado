package androidx.collection;

import com.huawei.hms.framework.common.ContainerUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: LongObjectMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\t\b\u0004¢\u0006\u0004\b\u0003\u0010\u0004\u0082\u0001\u0001\u0005¨\u0006\u0006"}, d2 = {"Landroidx/collection/Y;", "V", "", "<init>", "()V", "Landroidx/collection/H0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class Y<V> {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25652a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25653b;

    /* renamed from: c, reason: collision with root package name */
    @X41.f
    @Y61.k
    public Object[] f25654c;

    /* renamed from: d, reason: collision with root package name */
    @X41.f
    public int f25655d;

    /* renamed from: e, reason: collision with root package name */
    @X41.f
    public int f25656e;

    public /* synthetic */ Y(C42822w c42822w) {
        this();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0066, code lost:
    
        if (((r6 & ((~r6) << 6)) & (-9187201950435737472L)) == 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0068, code lost:
    
        r10 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean a(long r18) {
        /*
            r17 = this;
            r0 = r17
            int r1 = java.lang.Long.hashCode(r18)
            r2 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r1 = r1 * r2
            int r2 = r1 << 16
            r1 = r1 ^ r2
            r2 = r1 & 127(0x7f, float:1.78E-43)
            int r3 = r0.f25655d
            int r1 = r1 >>> 7
            r1 = r1 & r3
            r4 = 0
            r5 = r4
        L16:
            long[] r6 = r0.f25652a
            int r7 = r1 >> 3
            r8 = r1 & 7
            int r8 = r8 << 3
            r9 = r6[r7]
            long r9 = r9 >>> r8
            r11 = 1
            int r7 = r7 + r11
            r12 = r6[r7]
            int r6 = 64 - r8
            long r6 = r12 << r6
            long r12 = (long) r8
            long r12 = -r12
            r8 = 63
            long r12 = r12 >> r8
            long r6 = r6 & r12
            long r6 = r6 | r9
            long r8 = (long) r2
            r12 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r8 = r8 * r12
            long r8 = r8 ^ r6
            long r12 = r8 - r12
            long r8 = ~r8
            long r8 = r8 & r12
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r8 = r8 & r12
        L42:
            r14 = 0
            int r10 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r10 == 0) goto L5f
            int r10 = java.lang.Long.numberOfTrailingZeros(r8)
            int r10 = r10 >> 3
            int r10 = r10 + r1
            r10 = r10 & r3
            long[] r14 = r0.f25653b
            r15 = r14[r10]
            int r14 = (r15 > r18 ? 1 : (r15 == r18 ? 0 : -1))
            if (r14 != 0) goto L59
            goto L69
        L59:
            r14 = 1
            long r14 = r8 - r14
            long r8 = r8 & r14
            goto L42
        L5f:
            long r8 = ~r6
            r10 = 6
            long r8 = r8 << r10
            long r6 = r6 & r8
            long r6 = r6 & r12
            int r6 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
            if (r6 == 0) goto L6d
            r10 = -1
        L69:
            if (r10 < 0) goto L6c
            r4 = r11
        L6c:
            return r4
        L6d:
            int r5 = r5 + 8
            int r1 = r1 + r5
            r1 = r1 & r3
            goto L16
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.Y.a(long):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0064, code lost:
    
        if (((r5 & ((~r5) << 6)) & (-9187201950435737472L)) == 0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0066, code lost:
    
        r11 = -1;
     */
    @Y61.l
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final V b(long r16) {
        /*
            r15 = this;
            r0 = r15
            int r1 = java.lang.Long.hashCode(r16)
            r2 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r1 = r1 * r2
            int r2 = r1 << 16
            r1 = r1 ^ r2
            r2 = r1 & 127(0x7f, float:1.78E-43)
            int r3 = r0.f25655d
            int r1 = r1 >>> 7
            r1 = r1 & r3
            r4 = 0
        L14:
            long[] r5 = r0.f25652a
            int r6 = r1 >> 3
            r7 = r1 & 7
            int r7 = r7 << 3
            r8 = r5[r6]
            long r8 = r8 >>> r7
            int r6 = r6 + 1
            r10 = r5[r6]
            int r5 = 64 - r7
            long r5 = r10 << r5
            long r10 = (long) r7
            long r10 = -r10
            r7 = 63
            long r10 = r10 >> r7
            long r5 = r5 & r10
            long r5 = r5 | r8
            long r7 = (long) r2
            r9 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r7 = r7 * r9
            long r7 = r7 ^ r5
            long r9 = r7 - r9
            long r7 = ~r7
            long r7 = r7 & r9
            r9 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r7 = r7 & r9
        L40:
            r11 = 0
            int r13 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r13 == 0) goto L5d
            int r11 = java.lang.Long.numberOfTrailingZeros(r7)
            int r11 = r11 >> 3
            int r11 = r11 + r1
            r11 = r11 & r3
            long[] r12 = r0.f25653b
            r13 = r12[r11]
            int r12 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            if (r12 != 0) goto L57
            goto L67
        L57:
            r11 = 1
            long r11 = r7 - r11
            long r7 = r7 & r11
            goto L40
        L5d:
            long r7 = ~r5
            r13 = 6
            long r7 = r7 << r13
            long r5 = r5 & r7
            long r5 = r5 & r9
            int r5 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r5 == 0) goto L70
            r11 = -1
        L67:
            if (r11 < 0) goto L6e
            java.lang.Object[] r1 = r0.f25654c
            r1 = r1[r11]
            goto L6f
        L6e:
            r1 = 0
        L6f:
            return r1
        L70:
            int r4 = r4 + 8
            int r1 = r1 + r4
            r1 = r1 & r3
            goto L14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.Y.b(long):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x005e, code lost:
    
        return false;
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0074  */
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
            boolean r3 = r1 instanceof androidx.collection.Y
            r4 = 0
            if (r3 != 0) goto Le
            return r4
        Le:
            androidx.collection.Y r1 = (androidx.collection.Y) r1
            int r3 = r1.f25656e
            int r5 = r0.f25656e
            if (r3 == r5) goto L17
            return r4
        L17:
            long[] r3 = r0.f25653b
            java.lang.Object[] r5 = r0.f25654c
            long[] r6 = r0.f25652a
            int r7 = r6.length
            int r7 = r7 + (-2)
            if (r7 < 0) goto L79
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
            if (r11 == 0) goto L74
            int r11 = r8 - r7
            int r11 = ~r11
            int r11 = r11 >>> 31
            r12 = 8
            int r11 = 8 - r11
            r13 = r4
        L3d:
            if (r13 >= r11) goto L72
            r14 = 255(0xff, double:1.26E-321)
            long r14 = r14 & r9
            r16 = 128(0x80, double:6.3E-322)
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 >= 0) goto L6d
            int r14 = r8 << 3
            int r14 = r14 + r13
            r15 = r13
            r12 = r3[r14]
            r14 = r5[r14]
            if (r14 != 0) goto L5f
            java.lang.Object r14 = r1.b(r12)
            if (r14 != 0) goto L5e
            boolean r12 = r1.a(r12)
            if (r12 != 0) goto L6a
        L5e:
            return r4
        L5f:
            java.lang.Object r12 = r1.b(r12)
            boolean r12 = r14.equals(r12)
            if (r12 != 0) goto L6a
            return r4
        L6a:
            r12 = 8
            goto L6e
        L6d:
            r15 = r13
        L6e:
            long r9 = r9 >> r12
            int r13 = r15 + 1
            goto L3d
        L72:
            if (r11 != r12) goto L79
        L74:
            if (r8 == r7) goto L79
            int r8 = r8 + 1
            goto L23
        L79:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.Y.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        long[] jArr = this.f25653b;
        Object[] objArr = this.f25654c;
        long[] jArr2 = this.f25652a;
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
                            Object obj = objArr[i16];
                            iHashCode += (obj != null ? obj.hashCode() : 0) ^ Long.hashCode(j13);
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
        if (this.f25656e == 0) {
            return "{}";
        }
        StringBuilder sb2 = new StringBuilder("{");
        long[] jArr = this.f25653b;
        Object[] objArr = this.f25654c;
        long[] jArr2 = this.f25652a;
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
                            Object obj = objArr[i18];
                            sb2.append(j13);
                            sb2.append(ContainerUtils.KEY_VALUE_DELIMITER);
                            if (obj == this) {
                                obj = "(this)";
                            }
                            sb2.append(obj);
                            i15++;
                            if (i15 < this.f25656e) {
                                sb2.append(", ");
                            }
                        } else {
                            i13 = i14;
                        }
                        j12 >>= 8;
                        i17++;
                        i14 = i13;
                    }
                    int i19 = i14;
                    if (i16 != 8) {
                        break;
                    }
                    i12 = i19;
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

    public Y() {
        this.f25652a = i1.f25727a;
        this.f25653b = C20202b0.f25680a;
        this.f25654c = K.a.f9206c;
    }
}
