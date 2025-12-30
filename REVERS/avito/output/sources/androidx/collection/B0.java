package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: IntSet.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/collection/B0;", "Landroidx/collection/L;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class B0 extends L {

    /* renamed from: e, reason: collision with root package name */
    public int f25525e;

    public B0() {
        this(0, 1, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0079, code lost:
    
        if (((((~r10) << 6) & r10) & (-9187201950435737472L)) == 0) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x007b, code lost:
    
        r2 = r0.c(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0083, code lost:
    
        if (r0.f25525e != 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0097, code lost:
    
        if (((r0.f25559a[r2 >> 3] >> ((r2 & 7) << 3)) & 255) != 254) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0099, code lost:
    
        r20 = r4;
        r30 = r12;
        r3 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a0, code lost:
    
        r2 = r0.f25561c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00a2, code lost:
    
        if (r2 <= 8) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a4, code lost:
    
        r5 = r0.f25562d;
        r20 = kotlin.w0.f410662c;
        r20 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00b8, code lost:
    
        if (java.lang.Long.compareUnsigned(r5 * 32, r2 * 25) > 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00ba, code lost:
    
        r2 = r0.f25559a;
        r3 = r0.f25561c;
        r4 = r0.f25560b;
        r5 = 7;
        r6 = (r3 + 7) >> 3;
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00c6, code lost:
    
        if (r8 >= r6) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00c8, code lost:
    
        r10 = r2[r8] & r14;
        r2[r8] = (-72340172838076674L) & ((~r10) + (r10 >>> r5));
        r8 = r8 + 1;
        r5 = 7;
        r14 = -9187201950435737472L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00e3, code lost:
    
        r6 = r2.length;
        r8 = r6 - 1;
        r6 = r6 - 2;
        r2[r6] = (r2[r6] & 72057594037927935L) | (-72057594037927936L);
        r2[r8] = r2[0];
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00fd, code lost:
    
        if (r5 == r3) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00ff, code lost:
    
        r6 = r5 >> 3;
        r10 = (r5 & 7) << 3;
        r8 = (r2[r6] >> r10) & 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0111, code lost:
    
        if (r8 != 128) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0113, code lost:
    
        r11 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0114, code lost:
    
        r5 = r5 + r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0116, code lost:
    
        r11 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0119, code lost:
    
        if (r8 == 254) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x011c, code lost:
    
        r8 = java.lang.Integer.hashCode(r4[r5]) * (-862048943);
        r8 = r8 ^ (r8 << 16);
        r11 = r8 >>> 7;
        r9 = r0.c(r11);
        r11 = r11 & r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0140, code lost:
    
        if ((((r9 - r11) & r3) / 8) != (((r5 - r11) & r3) / 8)) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0142, code lost:
    
        r2[r6] = ((r8 & 127) << r10) | ((~(255 << r10)) & r2[r6]);
        r2[r2.length - 1] = (r2[0] & 72057594037927935L) | Long.MIN_VALUE;
        r5 = r5 + 1;
        r12 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x016d, code lost:
    
        r30 = r12;
        r11 = r9 >> 3;
        r12 = r2[r11];
        r14 = (r9 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0182, code lost:
    
        if (((r12 >> r14) & 255) != 128) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0184, code lost:
    
        r15 = r7;
        r2[r11] = ((~(255 << r14)) & r12) | ((r8 & 127) << r14);
        r2[r6] = (r2[r6] & (~(255 << r10))) | (128 << r10);
        r4[r9] = r4[r5];
        r4[r5] = 0;
        r8 = r3;
        r10 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x01a7, code lost:
    
        r15 = r7;
        r0 = r8 & 127;
        r8 = r3;
        r10 = r4;
        r2[r11] = (r0 << r14) | ((~(255 << r14)) & r12);
        r0 = r10[r9];
        r10[r9] = r10[r5];
        r10[r5] = r0;
        r5 = r5 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x01c1, code lost:
    
        r2[r2.length - 1] = (r2[0] & 72057594037927935L) | Long.MIN_VALUE;
        r5 = r5 + 1;
        r0 = r35;
        r3 = r8;
        r4 = r10;
        r7 = r15;
        r12 = r30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x01de, code lost:
    
        r30 = r12;
        r0.f25525e = androidx.collection.i1.a(r0.f25561c) - r0.f25562d;
        r1 = r7;
        r3 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01f1, code lost:
    
        r30 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01f6, code lost:
    
        r20 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01f9, code lost:
    
        r1 = androidx.collection.i1.c(r0.f25561c);
        r2 = r0.f25559a;
        r4 = r0.f25560b;
        r5 = r0.f25561c;
        r0.d(r1);
        r1 = r0.f25559a;
        r6 = r0.f25560b;
        r7 = r0.f25561c;
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x020f, code lost:
    
        if (r8 >= r5) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0222, code lost:
    
        if (((r2[r8 >> 3] >> ((r8 & 7) << 3)) & 255) >= 128) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0224, code lost:
    
        r9 = r4[r8];
        r10 = java.lang.Integer.hashCode(r9) * (-862048943);
        r10 = r10 ^ (r10 << 16);
        r13 = r0.c(r10 >>> 7);
        r14 = r4;
        r3 = r10 & 127;
        r10 = r13 >> 3;
        r18 = (r13 & 7) << 3;
        r3 = (r3 << r18) | (r1[r10] & (~(255 << r18)));
        r1[r10] = r3;
        r1[(((r13 - 7) & r7) + (r7 & 7)) >> 3] = r3;
        r6[r13] = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x025e, code lost:
    
        r14 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0260, code lost:
    
        r8 = r8 + 1;
        r4 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0264, code lost:
    
        r3 = 1;
        r1 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0266, code lost:
    
        r19 = r0.c(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x026d, code lost:
    
        r19 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x026f, code lost:
    
        r0.f25562d += r3;
        r1 = r0.f25525e;
        r2 = r0.f25559a;
        r4 = r19 >> 3;
        r5 = r2[r4];
        r7 = (r19 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x028a, code lost:
    
        if (((r5 >> r7) & 255) != 128) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x028c, code lost:
    
        r8 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x028e, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x028f, code lost:
    
        r0.f25525e = r1 - r8;
        r1 = r0.f25561c;
        r5 = (r5 & (~(255 << r7))) | (r30 << r7);
        r2[r4] = r5;
        r2[(((r19 - 7) & r1) + (r1 & 7)) >> 3] = r5;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean b(int r36) {
        /*
            Method dump skipped, instructions count: 715
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.B0.b(int):boolean");
    }

    public final int c(int i12) {
        int i13 = this.f25561c;
        int i14 = i12 & i13;
        int i15 = 0;
        while (true) {
            long[] jArr = this.f25559a;
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

    public final void d(int i12) {
        long[] jArr;
        int iMax = i12 > 0 ? Math.max(7, i1.d(i12)) : 0;
        this.f25561c = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i13];
            Arrays.fill(jArr2, 0, i13, -9187201950435737472L);
            jArr = jArr2;
        }
        this.f25559a = jArr;
        int i14 = iMax >> 3;
        long j12 = 255 << ((iMax & 7) << 3);
        jArr[i14] = (jArr[i14] & (~j12)) | j12;
        this.f25525e = i1.a(this.f25561c) - this.f25562d;
        this.f25560b = new int[iMax];
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0067, code lost:
    
        if (((r6 & ((~r6) << 6)) & (-9187201950435737472L)) == 0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0069, code lost:
    
        r10 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean e(int r19) {
        /*
            r18 = this;
            r0 = r18
            int r1 = java.lang.Integer.hashCode(r19)
            r2 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r1 = r1 * r2
            int r2 = r1 << 16
            r1 = r1 ^ r2
            r2 = r1 & 127(0x7f, float:1.78E-43)
            int r3 = r0.f25561c
            int r1 = r1 >>> 7
            r1 = r1 & r3
            r4 = 0
            r5 = r4
        L16:
            long[] r6 = r0.f25559a
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
            if (r10 == 0) goto L60
            int r10 = java.lang.Long.numberOfTrailingZeros(r8)
            int r10 = r10 >> 3
            int r10 = r10 + r1
            r10 = r10 & r3
            int[] r14 = r0.f25560b
            r14 = r14[r10]
            r15 = r19
            if (r14 != r15) goto L59
            goto L6a
        L59:
            r16 = 1
            long r16 = r8 - r16
            long r8 = r8 & r16
            goto L42
        L60:
            long r8 = ~r6
            r10 = 6
            long r8 = r8 << r10
            long r6 = r6 & r8
            long r6 = r6 & r12
            int r6 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
            if (r6 == 0) goto L73
            r10 = -1
        L6a:
            if (r10 < 0) goto L6d
            r4 = r11
        L6d:
            if (r4 == 0) goto L72
            r0.f(r10)
        L72:
            return r4
        L73:
            int r5 = r5 + 8
            int r1 = r1 + r5
            r1 = r1 & r3
            goto L16
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.B0.e(int):boolean");
    }

    public final void f(int i12) {
        this.f25562d--;
        long[] jArr = this.f25559a;
        int i13 = this.f25561c;
        int i14 = i12 >> 3;
        int i15 = (i12 & 7) << 3;
        long j12 = (jArr[i14] & (~(255 << i15))) | (254 << i15);
        jArr[i14] = j12;
        jArr[(((i12 - 7) & i13) + (i13 & 7)) >> 3] = j12;
    }

    public B0(int i12) {
        super(null);
        if (i12 >= 0) {
            d(i1.e(i12));
        } else {
            K.f.a("Capacity must be a positive value.");
            throw null;
        }
    }

    public /* synthetic */ B0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 6 : i12);
    }
}
