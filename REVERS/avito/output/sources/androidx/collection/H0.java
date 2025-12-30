package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: LongObjectMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/H0;", "V", "Landroidx/collection/Y;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class H0<V> extends Y<V> {

    /* renamed from: f, reason: collision with root package name */
    public int f25551f;

    public H0() {
        this(0, 1, null);
    }

    public final void c() {
        this.f25656e = 0;
        long[] jArr = this.f25652a;
        if (jArr != i1.f25727a) {
            C42756l.A(jArr, -9187201950435737472L);
            long[] jArr2 = this.f25652a;
            int i12 = this.f25655d;
            int i13 = i12 >> 3;
            long j12 = 255 << ((i12 & 7) << 3);
            jArr2[i13] = (jArr2[i13] & (~j12)) | j12;
        }
        Arrays.fill(this.f25654c, 0, this.f25655d, (Object) null);
        this.f25551f = i1.a(this.f25655d) - this.f25656e;
    }

    public final int d(long j12) {
        long j13;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18 = 1;
        int iHashCode = Long.hashCode(j12) * (-862048943);
        int i19 = iHashCode ^ (iHashCode << 16);
        int i22 = i19 >>> 7;
        int i23 = i19 & 127;
        int i24 = this.f25655d;
        int i25 = i22 & i24;
        int i26 = 0;
        while (true) {
            long[] jArr = this.f25652a;
            int i27 = i25 >> 3;
            int i28 = (i25 & 7) << 3;
            int i29 = i26;
            long j14 = (((-i28) >> 63) & (jArr[i27 + i18] << (64 - i28))) | (jArr[i27] >>> i28);
            long j15 = i23;
            int i32 = i23;
            long j16 = j14 ^ (j15 * 72340172838076673L);
            long j17 = -9187201950435737472L;
            for (long j18 = (~j16) & (j16 - 72340172838076673L) & (-9187201950435737472L); j18 != 0; j18 &= j18 - 1) {
                int iNumberOfTrailingZeros = (i25 + (Long.numberOfTrailingZeros(j18) >> 3)) & i24;
                if (this.f25653b[iNumberOfTrailingZeros] == j12) {
                    return iNumberOfTrailingZeros;
                }
            }
            if ((((~j14) << 6) & j14 & (-9187201950435737472L)) != 0) {
                int iE2 = e(i22);
                if (this.f25551f != 0 || ((this.f25652a[iE2 >> 3] >> ((iE2 & 7) << 3)) & 255) == 254) {
                    j13 = j15;
                    i12 = 1;
                } else {
                    int i33 = this.f25655d;
                    if (i33 > 8) {
                        long j19 = this.f25656e;
                        int i34 = kotlin.w0.f410662c;
                        j13 = j15;
                        if (Long.compareUnsigned(j19 * 32, i33 * 25) <= 0) {
                            long[] jArr2 = this.f25652a;
                            int i35 = this.f25655d;
                            long[] jArr3 = this.f25653b;
                            Object[] objArr = this.f25654c;
                            int i36 = (i35 + 7) >> 3;
                            int i37 = 0;
                            while (i37 < i36) {
                                long j22 = jArr2[i37] & j17;
                                jArr2[i37] = (-72340172838076674L) & ((~j22) + (j22 >>> 7));
                                i37++;
                                j17 = -9187201950435737472L;
                            }
                            int length = jArr2.length;
                            int i38 = length - 1;
                            int i39 = length - 2;
                            jArr2[i39] = (jArr2[i39] & 72057594037927935L) | (-72057594037927936L);
                            jArr2[i38] = jArr2[0];
                            int i42 = 0;
                            while (i42 != i35) {
                                int i43 = i42 >> 3;
                                int i44 = (i42 & 7) << 3;
                                long j23 = (jArr2[i43] >> i44) & 255;
                                if (j23 == 128) {
                                    i15 = 1;
                                } else {
                                    i15 = 1;
                                    if (j23 == 254) {
                                        int iHashCode2 = Long.hashCode(jArr3[i42]) * (-862048943);
                                        int i45 = (iHashCode2 ^ (iHashCode2 << 16)) >>> 7;
                                        int iE3 = e(i45);
                                        int i46 = i45 & i35;
                                        if (((iE3 - i46) & i35) / 8 == ((i42 - i46) & i35) / 8) {
                                            i16 = i22;
                                            jArr2[i43] = (jArr2[i43] & (~(255 << i44))) | ((r10 & 127) << i44);
                                            i17 = 1;
                                            jArr2[jArr2.length - 1] = (jArr2[0] & 72057594037927935L) | Long.MIN_VALUE;
                                        } else {
                                            i16 = i22;
                                            int i47 = iE3 >> 3;
                                            long j24 = jArr2[i47];
                                            int i48 = (iE3 & 7) << 3;
                                            if (((j24 >> i48) & 255) == 128) {
                                                jArr2[i47] = ((r10 & 127) << i48) | (j24 & (~(255 << i48)));
                                                jArr2[i43] = (jArr2[i43] & (~(255 << i44))) | (128 << i44);
                                                jArr3[iE3] = jArr3[i42];
                                                jArr3[i42] = 0;
                                                objArr[iE3] = objArr[i42];
                                                objArr[i42] = null;
                                            } else {
                                                jArr2[i47] = ((r10 & 127) << i48) | (j24 & (~(255 << i48)));
                                                long j25 = jArr3[iE3];
                                                jArr3[iE3] = jArr3[i42];
                                                jArr3[i42] = j25;
                                                Object obj = objArr[iE3];
                                                objArr[iE3] = objArr[i42];
                                                objArr[i42] = obj;
                                                i42--;
                                            }
                                            i17 = 1;
                                            jArr2[jArr2.length - 1] = (jArr2[0] & 72057594037927935L) | Long.MIN_VALUE;
                                        }
                                        i42 += i17;
                                        i22 = i16;
                                    }
                                }
                                i42 += i15;
                            }
                            this.f25551f = i1.a(this.f25655d) - this.f25656e;
                            i14 = i22;
                            i12 = 1;
                            iE2 = e(i14);
                        } else {
                            i13 = i22;
                        }
                    } else {
                        i13 = i22;
                        j13 = j15;
                    }
                    int iC2 = i1.c(this.f25655d);
                    long[] jArr4 = this.f25652a;
                    long[] jArr5 = this.f25653b;
                    Object[] objArr2 = this.f25654c;
                    int i49 = this.f25655d;
                    f(iC2);
                    long[] jArr6 = this.f25652a;
                    long[] jArr7 = this.f25653b;
                    Object[] objArr3 = this.f25654c;
                    int i52 = this.f25655d;
                    for (int i53 = 0; i53 < i49; i53++) {
                        if (((jArr4[i53 >> 3] >> ((i53 & 7) << 3)) & 255) < 128) {
                            long j26 = jArr5[i53];
                            int iHashCode3 = Long.hashCode(j26) * (-862048943);
                            int iE4 = e((iHashCode3 ^ (iHashCode3 << 16)) >>> 7);
                            int i54 = iE4 >> 3;
                            int i55 = (iE4 & 7) << 3;
                            long j27 = (jArr6[i54] & (~(255 << i55))) | ((r15 & 127) << i55);
                            jArr6[i54] = j27;
                            jArr6[(((iE4 - 7) & i52) + (i52 & 7)) >> 3] = j27;
                            jArr7[iE4] = j26;
                            objArr3[iE4] = objArr2[i53];
                        }
                    }
                    i12 = 1;
                    i14 = i13;
                    iE2 = e(i14);
                }
                this.f25656e += i12;
                int i56 = this.f25551f;
                long[] jArr8 = this.f25652a;
                int i57 = iE2 >> 3;
                long j28 = jArr8[i57];
                int i58 = (iE2 & 7) << 3;
                this.f25551f = i56 - (((j28 >> i58) & 255) == 128 ? i12 : 0);
                int i59 = this.f25655d;
                long j29 = (j28 & (~(255 << i58))) | (j13 << i58);
                jArr8[i57] = j29;
                jArr8[(((iE2 - 7) & i59) + (i59 & 7)) >> 3] = j29;
                return iE2;
            }
            i26 = i29 + 8;
            i25 = (i25 + i26) & i24;
            i18 = 1;
            i23 = i32;
        }
    }

    public final int e(int i12) {
        int i13 = this.f25655d;
        int i14 = i12 & i13;
        int i15 = 0;
        while (true) {
            long[] jArr = this.f25652a;
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
        this.f25655d = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i13];
            Arrays.fill(jArr2, 0, i13, -9187201950435737472L);
            jArr = jArr2;
        }
        this.f25652a = jArr;
        int i14 = iMax >> 3;
        long j12 = 255 << ((iMax & 7) << 3);
        jArr[i14] = (jArr[i14] & (~j12)) | j12;
        this.f25551f = i1.a(this.f25655d) - this.f25656e;
        this.f25653b = new long[iMax];
        this.f25654c = new Object[iMax];
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0064, code lost:
    
        if (((r5 & ((~r5) << 6)) & (-9187201950435737472L)) == 0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0066, code lost:
    
        r11 = -1;
     */
    @Y61.l
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final V g(long r16) {
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
            if (r5 == 0) goto L9a
            r11 = -1
        L67:
            r1 = 0
            if (r11 < 0) goto L99
            int r2 = r0.f25656e
            int r2 = r2 + (-1)
            r0.f25656e = r2
            long[] r2 = r0.f25652a
            int r3 = r0.f25655d
            int r4 = r11 >> 3
            r5 = r11 & 7
            int r5 = r5 << 3
            r6 = r2[r4]
            r8 = 255(0xff, double:1.26E-321)
            long r8 = r8 << r5
            long r8 = ~r8
            long r6 = r6 & r8
            r8 = 254(0xfe, double:1.255E-321)
            long r8 = r8 << r5
            long r5 = r6 | r8
            r2[r4] = r5
            int r4 = r11 + (-7)
            r4 = r4 & r3
            r3 = r3 & 7
            int r4 = r4 + r3
            int r3 = r4 >> 3
            r2[r3] = r5
            java.lang.Object[] r2 = r0.f25654c
            r3 = r2[r11]
            r2[r11] = r1
            return r3
        L99:
            return r1
        L9a:
            int r4 = r4 + 8
            int r1 = r1 + r4
            r1 = r1 & r3
            goto L14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.H0.g(long):java.lang.Object");
    }

    public final void h(long j12, V v12) {
        int iD2 = d(j12);
        this.f25653b[iD2] = j12;
        this.f25654c[iD2] = v12;
    }

    public /* synthetic */ H0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 6 : i12);
    }

    public H0(int i12) {
        super(null);
        if (i12 >= 0) {
            f(i1.e(i12));
        } else {
            K.f.a("Capacity must be a positive value.");
            throw null;
        }
    }
}
