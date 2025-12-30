package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: LongSet.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0001\u0004¨\u0006\u0005"}, d2 = {"Landroidx/collection/a0;", "", "<init>", "()V", "Landroidx/collection/I0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.a0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20200a0 {

    /* renamed from: a, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25674a;

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    @Y61.k
    public long[] f25675b;

    /* renamed from: c, reason: collision with root package name */
    @X41.f
    public int f25676c;

    /* renamed from: d, reason: collision with root package name */
    @X41.f
    public int f25677d;

    public /* synthetic */ AbstractC20200a0(C42822w c42822w) {
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
            int r3 = r0.f25676c
            int r1 = r1 >>> 7
            r1 = r1 & r3
            r4 = 0
            r5 = r4
        L16:
            long[] r6 = r0.f25674a
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
            long[] r14 = r0.f25675b
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
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.AbstractC20200a0.a(long):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(@Y61.l java.lang.Object r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = 1
            if (r1 != r0) goto L8
            return r2
        L8:
            boolean r3 = r1 instanceof androidx.collection.AbstractC20200a0
            r4 = 0
            if (r3 != 0) goto Le
            return r4
        Le:
            androidx.collection.a0 r1 = (androidx.collection.AbstractC20200a0) r1
            int r3 = r1.f25677d
            int r5 = r0.f25677d
            if (r3 == r5) goto L17
            return r4
        L17:
            long[] r3 = r0.f25675b
            long[] r5 = r0.f25674a
            int r6 = r5.length
            int r6 = r6 + (-2)
            if (r6 < 0) goto L5d
            r7 = r4
        L21:
            r8 = r5[r7]
            long r10 = ~r8
            r12 = 7
            long r10 = r10 << r12
            long r10 = r10 & r8
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r10 = r10 & r12
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 == 0) goto L58
            int r10 = r7 - r6
            int r10 = ~r10
            int r10 = r10 >>> 31
            r11 = 8
            int r10 = 8 - r10
            r12 = r4
        L3b:
            if (r12 >= r10) goto L56
            r13 = 255(0xff, double:1.26E-321)
            long r13 = r13 & r8
            r15 = 128(0x80, double:6.3E-322)
            int r13 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r13 >= 0) goto L52
            int r13 = r7 << 3
            int r13 = r13 + r12
            r13 = r3[r13]
            boolean r13 = r1.a(r13)
            if (r13 != 0) goto L52
            return r4
        L52:
            long r8 = r8 >> r11
            int r12 = r12 + 1
            goto L3b
        L56:
            if (r10 != r11) goto L5d
        L58:
            if (r7 == r6) goto L5d
            int r7 = r7 + 1
            goto L21
        L5d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.AbstractC20200a0.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        long[] jArr = this.f25675b;
        long[] jArr2 = this.f25674a;
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
                            iHashCode = Long.hashCode(jArr[(i13 << 3) + i15]) + iHashCode;
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x005d A[PHI: r7
  0x005d: PHI (r7v2 int) = (r7v1 int), (r7v3 int) binds: [B:6:0x0026, B:18:0x005b] A[DONT_GENERATE, DONT_INLINE]] */
    @Y61.k
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String toString() {
        /*
            r17 = this;
            r0 = r17
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "["
            r1.append(r2)
            long[] r2 = r0.f25675b
            long[] r3 = r0.f25674a
            int r4 = r3.length
            int r4 = r4 + (-2)
            if (r4 < 0) goto L62
            r5 = 0
            r6 = r5
            r7 = r6
        L18:
            r8 = r3[r6]
            long r10 = ~r8
            r12 = 7
            long r10 = r10 << r12
            long r10 = r10 & r8
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r10 = r10 & r12
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 == 0) goto L5d
            int r10 = r6 - r4
            int r10 = ~r10
            int r10 = r10 >>> 31
            r11 = 8
            int r10 = 8 - r10
            r12 = r5
        L32:
            if (r12 >= r10) goto L5b
            r13 = 255(0xff, double:1.26E-321)
            long r13 = r13 & r8
            r15 = 128(0x80, double:6.3E-322)
            int r13 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r13 >= 0) goto L57
            int r13 = r6 << 3
            int r13 = r13 + r12
            r13 = r2[r13]
            r15 = -1
            if (r7 != r15) goto L4b
            java.lang.String r2 = "..."
            r1.append(r2)
            goto L67
        L4b:
            if (r7 == 0) goto L52
            java.lang.String r15 = ", "
            r1.append(r15)
        L52:
            r1.append(r13)
            int r7 = r7 + 1
        L57:
            long r8 = r8 >> r11
            int r12 = r12 + 1
            goto L32
        L5b:
            if (r10 != r11) goto L62
        L5d:
            if (r6 == r4) goto L62
            int r6 = r6 + 1
            goto L18
        L62:
            java.lang.String r2 = "]"
            r1.append(r2)
        L67:
            java.lang.String r1 = r1.toString()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.AbstractC20200a0.toString():java.lang.String");
    }

    public AbstractC20200a0() {
        this.f25674a = i1.f25727a;
        this.f25675b = C20202b0.f25680a;
    }
}
