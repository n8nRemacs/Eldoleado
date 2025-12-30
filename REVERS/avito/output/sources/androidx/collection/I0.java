package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: LongSet.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/collection/I0;", "Landroidx/collection/a0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class I0 extends AbstractC20200a0 {

    /* renamed from: e, reason: collision with root package name */
    public int f25552e;

    public I0() {
        this(0, 1, null);
    }

    public final int b(int i12) {
        int i13 = this.f25676c;
        int i14 = i12 & i13;
        int i15 = 0;
        while (true) {
            long[] jArr = this.f25674a;
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

    public final void c(int i12) {
        long[] jArr;
        int iMax = i12 > 0 ? Math.max(7, i1.d(i12)) : 0;
        this.f25676c = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i13];
            Arrays.fill(jArr2, 0, i13, -9187201950435737472L);
            jArr = jArr2;
        }
        this.f25674a = jArr;
        int i14 = iMax >> 3;
        long j12 = 255 << ((iMax & 7) << 3);
        jArr[i14] = (jArr[i14] & (~j12)) | j12;
        this.f25552e = i1.a(this.f25676c) - this.f25677d;
        this.f25675b = new long[iMax];
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0071, code lost:
    
        if (((((~r8) << 6) & r8) & (-9187201950435737472L)) == 0) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0073, code lost:
    
        r1 = b(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x007b, code lost:
    
        if (r39.f25552e != 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0090, code lost:
    
        if (((r39.f25674a[r1 >> 3] >> ((r1 & 7) << 3)) & 255) != 254) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0092, code lost:
    
        r33 = r10;
        r10 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0097, code lost:
    
        r1 = r39.f25676c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0099, code lost:
    
        if (r1 <= 8) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x009b, code lost:
    
        r2 = r39.f25677d;
        r14 = kotlin.w0.f410662c;
        r25 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00af, code lost:
    
        if (java.lang.Long.compareUnsigned(r2 * 32, r1 * 25) > 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00b1, code lost:
    
        r1 = r39.f25674a;
        r2 = r39.f25676c;
        r3 = r39.f25675b;
        r4 = 7;
        r5 = (r2 + 7) >> 3;
        r14 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00bd, code lost:
    
        if (r14 >= r5) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00bf, code lost:
    
        r6 = r1[r14] & r12;
        r1[r14] = (-72340172838076674L) & ((~r6) + (r6 >>> r4));
        r14 = r14 + 1;
        r4 = 7;
        r12 = -9187201950435737472L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00d7, code lost:
    
        r5 = r1.length;
        r6 = r5 - 1;
        r5 = r5 - 2;
        r1[r5] = (r1[r5] & 72057594037927935L) | (-72057594037927936L);
        r1[r6] = r1[0];
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00f2, code lost:
    
        if (r4 == r2) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00f4, code lost:
    
        r5 = r4 >> 3;
        r12 = (r4 & 7) << 3;
        r6 = (r1[r5] >> r12) & 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0103, code lost:
    
        if (r6 != 128) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0105, code lost:
    
        r13 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0106, code lost:
    
        r4 = r4 + r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0108, code lost:
    
        r13 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x010b, code lost:
    
        if (r6 == 254) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x010e, code lost:
    
        r6 = java.lang.Long.hashCode(r3[r4]) * (-862048943);
        r13 = (r6 ^ (r6 << 16)) >>> 7;
        r7 = b(r13);
        r13 = r13 & r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0132, code lost:
    
        if ((((r7 - r13) & r2) / 8) != (((r4 - r13) & r2) / 8)) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0134, code lost:
    
        r33 = r10;
        r1[r5] = ((r6 & 127) << r12) | ((~(255 << r12)) & r1[r5]);
        r6 = 1;
        r1[r1.length - 1] = (r1[0] & 72057594037927935L) | Long.MIN_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x014f, code lost:
    
        r4 = r4 + r6;
        r10 = r33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0153, code lost:
    
        r33 = r10;
        r10 = r7 >> 3;
        r35 = r1[r10];
        r11 = (r7 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0166, code lost:
    
        if (((r35 >> r11) & 255) != 128) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0168, code lost:
    
        r1[r10] = (r35 & (~(255 << r11))) | ((r6 & 127) << r11);
        r1[r5] = (r1[r5] & (~(255 << r12))) | (128 << r12);
        r3[r7] = r3[r4];
        r3[r4] = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x018d, code lost:
    
        r1[r10] = ((r6 & 127) << r11) | (r35 & (~(255 << r11)));
        r5 = r3[r7];
        r3[r7] = r3[r4];
        r3[r4] = r5;
        r4 = r4 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x01a3, code lost:
    
        r6 = 1;
        r1[r1.length - 1] = (r1[0] & 72057594037927935L) | Long.MIN_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01b0, code lost:
    
        r33 = r10;
        r39.f25552e = androidx.collection.i1.a(r39.f25676c) - r39.f25677d;
        r1 = r25;
        r10 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01c3, code lost:
    
        r33 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01c7, code lost:
    
        r25 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01ca, code lost:
    
        r1 = androidx.collection.i1.c(r39.f25676c);
        r2 = r39.f25674a;
        r3 = r39.f25675b;
        r4 = r39.f25676c;
        c(r1);
        r1 = r39.f25674a;
        r5 = r39.f25675b;
        r6 = r39.f25676c;
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x01e0, code lost:
    
        if (r7 >= r4) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x01f1, code lost:
    
        if (((r2[r7 >> 3] >> ((r7 & 7) << 3)) & 255) >= 128) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x01f3, code lost:
    
        r11 = r3[r7];
        r15 = java.lang.Long.hashCode(r11) * (-862048943);
        r10 = b((r15 ^ (r15 << 16)) >>> 7);
        r16 = r10 >> 3;
        r19 = (r10 & 7) << 3;
        r13 = (r1[r16] & (~(255 << r19))) | ((r15 & 127) << r19);
        r1[r16] = r13;
        r1[(((r10 - 7) & r6) + (r6 & 7)) >> 3] = r13;
        r5[r10] = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0235, code lost:
    
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0238, code lost:
    
        r10 = 1;
        r1 = r25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x023b, code lost:
    
        r1 = b(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x023f, code lost:
    
        r17 = r1;
        r39.f25677d += r10;
        r1 = r39.f25552e;
        r2 = r39.f25674a;
        r3 = r17 >> 3;
        r4 = r2[r3];
        r6 = (r17 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x025a, code lost:
    
        if (((r4 >> r6) & 255) != 128) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x025c, code lost:
    
        r18 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x025f, code lost:
    
        r18 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0261, code lost:
    
        r39.f25552e = r1 - r18;
        r1 = r39.f25676c;
        r4 = (r4 & (~(255 << r6))) | (r33 << r6);
        r2[r3] = r4;
        r2[(((r17 - 7) & r1) + (r1 & 7)) >> 3] = r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void d(long r40) {
        /*
            Method dump skipped, instructions count: 652
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.I0.d(long):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0064, code lost:
    
        if (((r5 & ((~r5) << 6)) & (-9187201950435737472L)) == 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0066, code lost:
    
        r11 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void e(long r16) {
        /*
            r15 = this;
            r0 = r15
            int r1 = java.lang.Long.hashCode(r16)
            r2 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r1 = r1 * r2
            int r2 = r1 << 16
            r1 = r1 ^ r2
            r2 = r1 & 127(0x7f, float:1.78E-43)
            int r3 = r0.f25676c
            int r1 = r1 >>> 7
            r1 = r1 & r3
            r4 = 0
        L14:
            long[] r5 = r0.f25674a
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
            long[] r12 = r0.f25675b
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
            if (r5 == 0) goto L93
            r11 = -1
        L67:
            if (r11 < 0) goto L92
            int r1 = r0.f25677d
            int r1 = r1 + (-1)
            r0.f25677d = r1
            long[] r1 = r0.f25674a
            int r2 = r0.f25676c
            int r3 = r11 >> 3
            r4 = r11 & 7
            int r4 = r4 << 3
            r5 = r1[r3]
            r7 = 255(0xff, double:1.26E-321)
            long r7 = r7 << r4
            long r7 = ~r7
            long r5 = r5 & r7
            r7 = 254(0xfe, double:1.255E-321)
            long r7 = r7 << r4
            long r4 = r5 | r7
            r1[r3] = r4
            int r11 = r11 + (-7)
            r3 = r11 & r2
            r2 = r2 & 7
            int r3 = r3 + r2
            int r2 = r3 >> 3
            r1[r2] = r4
        L92:
            return
        L93:
            int r4 = r4 + 8
            int r1 = r1 + r4
            r1 = r1 & r3
            goto L14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.I0.e(long):void");
    }

    public I0(int i12) {
        super(null);
        if (i12 >= 0) {
            c(i1.e(i12));
        } else {
            K.f.a("Capacity must be a positive value.");
            throw null;
        }
    }

    public /* synthetic */ I0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 6 : i12);
    }
}
