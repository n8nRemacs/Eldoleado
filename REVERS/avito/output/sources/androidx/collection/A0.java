package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: IntObjectMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/A0;", "V", "Landroidx/collection/J;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class A0<V> extends J<V> {

    /* renamed from: f, reason: collision with root package name */
    public int f25524f;

    public A0() {
        this(0, 1, null);
    }

    public final void c() {
        this.f25557e = 0;
        long[] jArr = this.f25553a;
        if (jArr != i1.f25727a) {
            C42756l.A(jArr, -9187201950435737472L);
            long[] jArr2 = this.f25553a;
            int i12 = this.f25556d;
            int i13 = i12 >> 3;
            long j12 = 255 << ((i12 & 7) << 3);
            jArr2[i13] = (jArr2[i13] & (~j12)) | j12;
        }
        Arrays.fill(this.f25555c, 0, this.f25556d, (Object) null);
        this.f25524f = i1.a(this.f25556d) - this.f25557e;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x01d9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int d(int r35) {
        /*
            Method dump skipped, instructions count: 688
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.A0.d(int):int");
    }

    public final int e(int i12) {
        int i13 = this.f25556d;
        int i14 = i12 & i13;
        int i15 = 0;
        while (true) {
            long[] jArr = this.f25553a;
            int i16 = i14 >> 3;
            int i17 = (i14 & 7) << 3;
            long j12 = ((jArr[i16 + 1] << (64 - i17)) & ((-i17) >> 63)) | (jArr[i16] >>> i17);
            long j13 = j12 & ((~j12) << 7) & (-9187201950435737472L);
            if (j13 != 0) {
                return (i14 + (Long.numberOfTrailingZeros(j13) >> 3)) & i13;
            }
            i15 += 8;
            i14 = (i14 + i15) & i13;
        }
    }

    public final void f(int i12) {
        long[] jArr;
        int iMax = i12 > 0 ? Math.max(7, i1.d(i12)) : 0;
        this.f25556d = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i13];
            Arrays.fill(jArr2, 0, i13, -9187201950435737472L);
            jArr = jArr2;
        }
        this.f25553a = jArr;
        int i14 = iMax >> 3;
        long j12 = 255 << ((iMax & 7) << 3);
        jArr[i14] = (jArr[i14] & (~j12)) | j12;
        this.f25524f = i1.a(this.f25556d) - this.f25557e;
        this.f25554b = new int[iMax];
        this.f25555c = new Object[iMax];
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0061, code lost:
    
        if (((r4 & ((~r4) << 6)) & (-9187201950435737472L)) == 0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0063, code lost:
    
        r10 = -1;
     */
    @Y61.l
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final V g(int r14) {
        /*
            r13 = this;
            int r0 = java.lang.Integer.hashCode(r14)
            r1 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r0 = r0 * r1
            int r1 = r0 << 16
            r0 = r0 ^ r1
            r1 = r0 & 127(0x7f, float:1.78E-43)
            int r2 = r13.f25556d
            int r0 = r0 >>> 7
            r0 = r0 & r2
            r3 = 0
        L13:
            long[] r4 = r13.f25553a
            int r5 = r0 >> 3
            r6 = r0 & 7
            int r6 = r6 << 3
            r7 = r4[r5]
            long r7 = r7 >>> r6
            int r5 = r5 + 1
            r9 = r4[r5]
            int r4 = 64 - r6
            long r4 = r9 << r4
            long r9 = (long) r6
            long r9 = -r9
            r6 = 63
            long r9 = r9 >> r6
            long r4 = r4 & r9
            long r4 = r4 | r7
            long r6 = (long) r1
            r8 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r6 = r6 * r8
            long r6 = r6 ^ r4
            long r8 = r6 - r8
            long r6 = ~r6
            long r6 = r6 & r8
            r8 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r6 = r6 & r8
        L3f:
            r10 = 0
            int r12 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r12 == 0) goto L5a
            int r10 = java.lang.Long.numberOfTrailingZeros(r6)
            int r10 = r10 >> 3
            int r10 = r10 + r0
            r10 = r10 & r2
            int[] r11 = r13.f25554b
            r11 = r11[r10]
            if (r11 != r14) goto L54
            goto L64
        L54:
            r10 = 1
            long r10 = r6 - r10
            long r6 = r6 & r10
            goto L3f
        L5a:
            long r6 = ~r4
            r12 = 6
            long r6 = r6 << r12
            long r4 = r4 & r6
            long r4 = r4 & r8
            int r4 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r4 == 0) goto L97
            r10 = -1
        L64:
            r14 = 0
            if (r10 < 0) goto L96
            int r0 = r13.f25557e
            int r0 = r0 + (-1)
            r13.f25557e = r0
            long[] r0 = r13.f25553a
            int r1 = r13.f25556d
            int r2 = r10 >> 3
            r3 = r10 & 7
            int r3 = r3 << 3
            r4 = r0[r2]
            r6 = 255(0xff, double:1.26E-321)
            long r6 = r6 << r3
            long r6 = ~r6
            long r4 = r4 & r6
            r6 = 254(0xfe, double:1.255E-321)
            long r6 = r6 << r3
            long r3 = r4 | r6
            r0[r2] = r3
            int r2 = r10 + (-7)
            r2 = r2 & r1
            r1 = r1 & 7
            int r2 = r2 + r1
            int r1 = r2 >> 3
            r0[r1] = r3
            java.lang.Object[] r0 = r13.f25555c
            r1 = r0[r10]
            r0[r10] = r14
            return r1
        L96:
            return r14
        L97:
            int r3 = r3 + 8
            int r0 = r0 + r3
            r0 = r0 & r2
            goto L13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.A0.g(int):java.lang.Object");
    }

    public final void h(int i12, V v12) {
        int iD2 = d(i12);
        this.f25554b[iD2] = i12;
        this.f25555c[iD2] = v12;
    }

    public /* synthetic */ A0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 6 : i12);
    }

    public A0(int i12) {
        super(null);
        if (i12 >= 0) {
            f(i1.e(i12));
        } else {
            K.f.a("Capacity must be a positive value.");
            throw null;
        }
    }
}
