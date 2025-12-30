package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: IntIntMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/collection/x0;", "Landroidx/collection/C;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.x0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20245x0 extends C {

    /* renamed from: f, reason: collision with root package name */
    public int f25832f;

    public C20245x0() {
        this(0, 1, null);
    }

    public final void c() {
        this.f25530e = 0;
        long[] jArr = this.f25526a;
        if (jArr != i1.f25727a) {
            C42756l.A(jArr, -9187201950435737472L);
            long[] jArr2 = this.f25526a;
            int i12 = this.f25529d;
            int i13 = i12 >> 3;
            long j12 = 255 << ((i12 & 7) << 3);
            jArr2[i13] = (jArr2[i13] & (~j12)) | j12;
        }
        this.f25832f = i1.a(this.f25529d) - this.f25530e;
    }

    public final int d(int i12) {
        int i13 = this.f25529d;
        int i14 = i12 & i13;
        int i15 = 0;
        while (true) {
            long[] jArr = this.f25526a;
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

    public final void e(int i12) {
        long[] jArr;
        int iMax = i12 > 0 ? Math.max(7, i1.d(i12)) : 0;
        this.f25529d = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i13];
            Arrays.fill(jArr2, 0, i13, -9187201950435737472L);
            jArr = jArr2;
        }
        this.f25526a = jArr;
        int i14 = iMax >> 3;
        long j12 = 255 << ((iMax & 7) << 3);
        jArr[i14] = (jArr[i14] & (~j12)) | j12;
        this.f25832f = i1.a(this.f25529d) - this.f25530e;
        this.f25527b = new int[iMax];
        this.f25528c = new int[iMax];
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0073, code lost:
    
        if (((((~r9) << 6) & r9) & (-9187201950435737472L)) == 0) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0075, code lost:
    
        r2 = r0.d(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x007d, code lost:
    
        if (r0.f25832f != 0) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0092, code lost:
    
        if (((r0.f25526a[r2 >> 3] >> ((r2 & 7) << 3)) & 255) != 254) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0094, code lost:
    
        r32 = r11;
        r9 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0099, code lost:
    
        r2 = r0.f25529d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x009b, code lost:
    
        if (r2 <= 8) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x009d, code lost:
    
        r3 = r0.f25530e;
        r15 = kotlin.w0.f410662c;
        r23 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00b1, code lost:
    
        if (java.lang.Long.compareUnsigned(r3 * 32, r2 * 25) > 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00b3, code lost:
    
        r2 = r0.f25526a;
        r3 = r0.f25529d;
        r4 = r0.f25527b;
        r5 = r0.f25528c;
        r6 = 7;
        r15 = (r3 + 7) >> 3;
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00c1, code lost:
    
        if (r7 >= r15) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00c3, code lost:
    
        r9 = r2[r7] & r13;
        r2[r7] = (-72340172838076674L) & ((~r9) + (r9 >>> r6));
        r7 = r7 + 1;
        r6 = 7;
        r13 = -9187201950435737472L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00de, code lost:
    
        r7 = r2.length;
        r8 = r7 - 1;
        r7 = r7 - 2;
        r2[r7] = (r2[r7] & 72057594037927935L) | (-72057594037927936L);
        r2[r8] = r2[0];
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00f8, code lost:
    
        if (r6 == r3) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00fa, code lost:
    
        r7 = r6 >> 3;
        r10 = (r6 & 7) << 3;
        r8 = (r2[r7] >> r10) & 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x010c, code lost:
    
        if (r8 != 128) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x010e, code lost:
    
        r15 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x010f, code lost:
    
        r6 = r6 + r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0111, code lost:
    
        r15 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0114, code lost:
    
        if (r8 == 254) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0117, code lost:
    
        r8 = java.lang.Integer.hashCode(r4[r6]) * (-862048943);
        r15 = (r8 ^ (r8 << 16)) >>> 7;
        r9 = r0.d(r15);
        r15 = r15 & r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x013b, code lost:
    
        if ((((r9 - r15) & r3) / 8) != (((r6 - r15) & r3) / 8)) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x013d, code lost:
    
        r2[r7] = ((r8 & 127) << r10) | ((~(255 << r10)) & r2[r7]);
        r2[r2.length - 1] = (r2[0] & 72057594037927935L) | Long.MIN_VALUE;
        r6 = r6 + 1;
        r11 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0168, code lost:
    
        r32 = r11;
        r11 = r9 >> 3;
        r12 = r2[r11];
        r14 = (r9 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x017d, code lost:
    
        if (((r12 >> r14) & 255) != 128) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x017f, code lost:
    
        r2[r11] = ((r8 & 127) << r14) | ((~(255 << r14)) & r12);
        r2[r7] = (r2[r7] & (~(255 << r10))) | (128 << r10);
        r4[r9] = r4[r6];
        r4[r6] = 0;
        r5[r9] = r5[r6];
        r5[r6] = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x01a7, code lost:
    
        r2[r11] = ((r8 & 127) << r14) | ((~(255 << r14)) & r12);
        r0 = r4[r9];
        r4[r9] = r4[r6];
        r4[r6] = r0;
        r0 = r5[r9];
        r5[r9] = r5[r6];
        r5[r6] = r0;
        r6 = r6 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x01c8, code lost:
    
        r2[r2.length - 1] = (r2[0] & 72057594037927935L) | Long.MIN_VALUE;
        r6 = r6 + 1;
        r0 = r36;
        r11 = r32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x01e2, code lost:
    
        r32 = r11;
        r0.f25832f = androidx.collection.i1.a(r0.f25529d) - r0.f25530e;
        r1 = r23;
        r9 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01f5, code lost:
    
        r32 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01f9, code lost:
    
        r23 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01fc, code lost:
    
        r1 = androidx.collection.i1.c(r0.f25529d);
        r2 = r0.f25526a;
        r3 = r0.f25527b;
        r4 = r0.f25528c;
        r5 = r0.f25529d;
        r0.e(r1);
        r1 = r0.f25526a;
        r6 = r0.f25527b;
        r7 = r0.f25528c;
        r8 = r0.f25529d;
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0216, code lost:
    
        if (r10 >= r5) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x022a, code lost:
    
        if (((r2[r10 >> 3] >> ((r10 & 7) << 3)) & 255) >= 128) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x022c, code lost:
    
        r11 = r3[r10];
        r12 = java.lang.Integer.hashCode(r11) * (-862048943);
        r12 = r12 ^ (r12 << 16);
        r9 = r0.d(r12 >>> 7);
        r14 = r12 & 127;
        r12 = r9 >> 3;
        r17 = (r9 & 7) << 3;
        r13 = (r1[r12] & (~(255 << r17))) | (r14 << r17);
        r1[r12] = r13;
        r1[(((r9 - 7) & r8) + (r8 & 7)) >> 3] = r13;
        r6[r9] = r11;
        r7[r9] = r4[r10];
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x026c, code lost:
    
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0270, code lost:
    
        r9 = 1;
        r1 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0273, code lost:
    
        r2 = r0.d(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0277, code lost:
    
        r0.f25530e += r9;
        r1 = r0.f25832f;
        r3 = r0.f25526a;
        r4 = r2 >> 3;
        r5 = r3[r4];
        r7 = (r2 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0292, code lost:
    
        if (((r5 >> r7) & 255) != 128) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0294, code lost:
    
        r16 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0297, code lost:
    
        r16 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0299, code lost:
    
        r0.f25832f = r1 - r16;
        r1 = r0.f25529d;
        r5 = (r5 & (~(255 << r7))) | (r32 << r7);
        r3[r4] = r5;
        r3[(((r2 - 7) & r1) + (r1 & 7)) >> 3] = r5;
        r1 = ~r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void f(int r37, int r38) {
        /*
            Method dump skipped, instructions count: 721
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.C20245x0.f(int, int):void");
    }

    public C20245x0(int i12) {
        super(null);
        if (i12 >= 0) {
            e(i1.e(i12));
        } else {
            K.f.a("Capacity must be a positive value.");
            throw null;
        }
    }

    public /* synthetic */ C20245x0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 6 : i12);
    }
}
