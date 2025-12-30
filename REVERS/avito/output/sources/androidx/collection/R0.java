package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: ScatterMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/collection/R0;", "K", "V", "Landroidx/collection/h1;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class R0<K, V> extends h1<K, V> {

    /* renamed from: f, reason: collision with root package name */
    public int f25606f;

    public R0() {
        this(0, 1, null);
    }

    public final void g() {
        this.f25725e = 0;
        long[] jArr = this.f25721a;
        if (jArr != i1.f25727a) {
            C42756l.A(jArr, -9187201950435737472L);
            long[] jArr2 = this.f25721a;
            int i12 = this.f25724d;
            int i13 = i12 >> 3;
            long j12 = 255 << ((i12 & 7) << 3);
            jArr2[i13] = (jArr2[i13] & (~j12)) | j12;
        }
        Arrays.fill(this.f25723c, 0, this.f25724d, (Object) null);
        Arrays.fill(this.f25722b, 0, this.f25724d, (Object) null);
        this.f25606f = i1.a(this.f25724d) - this.f25725e;
    }

    public final int h(int i12) {
        int i13 = this.f25724d;
        int i14 = i12 & i13;
        int i15 = 0;
        while (true) {
            long[] jArr = this.f25721a;
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

    @kotlin.X
    public final int i(K k12) {
        long j12;
        int i12;
        int i13;
        int i14;
        int i15;
        long[] jArr;
        Object[] objArr;
        int i16;
        int i17;
        int i18;
        int i19 = 1;
        int iHashCode = (k12 != null ? k12.hashCode() : 0) * (-862048943);
        int i22 = iHashCode ^ (iHashCode << 16);
        int i23 = i22 >>> 7;
        int i24 = i22 & 127;
        int i25 = this.f25724d;
        int i26 = i23 & i25;
        int i27 = 0;
        while (true) {
            long[] jArr2 = this.f25721a;
            int i28 = i26 >> 3;
            int i29 = (i26 & 7) << 3;
            long j13 = (((-i29) >> 63) & (jArr2[i28 + i19] << (64 - i29))) | (jArr2[i28] >>> i29);
            long j14 = i24;
            int i32 = i24;
            long j15 = j13 ^ (j14 * 72340172838076673L);
            long j16 = -9187201950435737472L;
            for (long j17 = (~j15) & (j15 - 72340172838076673L) & (-9187201950435737472L); j17 != 0; j17 &= j17 - 1) {
                int iNumberOfTrailingZeros = (i26 + (Long.numberOfTrailingZeros(j17) >> 3)) & i25;
                if (kotlin.jvm.internal.L.f(this.f25722b[iNumberOfTrailingZeros], k12)) {
                    return iNumberOfTrailingZeros;
                }
            }
            if ((j13 & ((~j13) << 6) & (-9187201950435737472L)) != 0) {
                int iH2 = h(i23);
                long j18 = 255;
                if (this.f25606f != 0 || ((this.f25721a[iH2 >> 3] >> ((iH2 & 7) << 3)) & 255) == 254) {
                    j12 = j14;
                    i12 = 1;
                    i13 = 0;
                } else {
                    int i33 = this.f25724d;
                    if (i33 > 8) {
                        i14 = i23;
                        long j19 = this.f25725e;
                        int i34 = kotlin.w0.f410662c;
                        if (Long.compareUnsigned(j19 * 32, i33 * 25) <= 0) {
                            long[] jArr3 = this.f25721a;
                            int i35 = this.f25724d;
                            Object[] objArr2 = this.f25722b;
                            Object[] objArr3 = this.f25723c;
                            char c12 = 7;
                            int i36 = (i35 + 7) >> 3;
                            int i37 = 0;
                            while (i37 < i36) {
                                long j22 = jArr3[i37] & j16;
                                jArr3[i37] = (-72340172838076674L) & ((~j22) + (j22 >>> c12));
                                i37++;
                                c12 = 7;
                                j16 = -9187201950435737472L;
                            }
                            int length = jArr3.length;
                            int i38 = length - 1;
                            int i39 = length - 2;
                            jArr3[i39] = (jArr3[i39] & 72057594037927935L) | (-72057594037927936L);
                            jArr3[i38] = jArr3[0];
                            int i42 = 0;
                            while (i42 != i35) {
                                int i43 = i42 >> 3;
                                int i44 = (i42 & 7) << 3;
                                long j23 = (jArr3[i43] >> i44) & 255;
                                if (j23 == 128) {
                                    i16 = 1;
                                } else {
                                    i16 = 1;
                                    if (j23 == 254) {
                                        Object obj = objArr2[i42];
                                        int iHashCode2 = (obj != null ? obj.hashCode() : 0) * (-862048943);
                                        int i45 = (iHashCode2 ^ (iHashCode2 << 16)) >>> 7;
                                        int iH3 = h(i45);
                                        int i46 = i45 & i35;
                                        if (((iH3 - i46) & i35) / 8 == ((i42 - i46) & i35) / 8) {
                                            jArr3[i43] = (jArr3[i43] & (~(255 << i44))) | ((r10 & 127) << i44);
                                            jArr3[jArr3.length - 1] = jArr3[0];
                                            i42++;
                                            j14 = j14;
                                        } else {
                                            long j24 = j14;
                                            int i47 = iH3 >> 3;
                                            long j25 = jArr3[i47];
                                            int i48 = (iH3 & 7) << 3;
                                            if (((j25 >> i48) & 255) == 128) {
                                                i17 = i35;
                                                int i49 = i42;
                                                jArr3[i47] = (j25 & (~(255 << i48))) | ((r10 & 127) << i48);
                                                jArr3[i43] = (jArr3[i43] & (~(255 << i44))) | (128 << i44);
                                                objArr2[iH3] = objArr2[i49];
                                                objArr2[i49] = null;
                                                objArr3[iH3] = objArr3[i49];
                                                objArr3[i49] = null;
                                                i18 = i49;
                                            } else {
                                                i17 = i35;
                                                int i52 = i42;
                                                jArr3[i47] = ((r10 & 127) << i48) | (j25 & (~(255 << i48)));
                                                Object obj2 = objArr2[iH3];
                                                objArr2[iH3] = objArr2[i52];
                                                objArr2[i52] = obj2;
                                                Object obj3 = objArr3[iH3];
                                                objArr3[iH3] = objArr3[i52];
                                                objArr3[i52] = obj3;
                                                i18 = i52 - 1;
                                            }
                                            jArr3[jArr3.length - 1] = jArr3[0];
                                            i42 = i18 + 1;
                                            j14 = j24;
                                            i35 = i17;
                                        }
                                    }
                                }
                                i42 += i16;
                            }
                            j12 = j14;
                            i13 = 0;
                            this.f25606f = i1.a(this.f25724d) - this.f25725e;
                            i15 = i14;
                            i12 = 1;
                        }
                        iH2 = h(i15);
                    } else {
                        i14 = i23;
                    }
                    j12 = j14;
                    i13 = 0;
                    int iC2 = i1.c(this.f25724d);
                    long[] jArr4 = this.f25721a;
                    Object[] objArr4 = this.f25722b;
                    Object[] objArr5 = this.f25723c;
                    int i53 = this.f25724d;
                    j(iC2);
                    long[] jArr5 = this.f25721a;
                    Object[] objArr6 = this.f25722b;
                    Object[] objArr7 = this.f25723c;
                    int i54 = this.f25724d;
                    int i55 = 0;
                    while (i55 < i53) {
                        if (((jArr4[i55 >> 3] >> ((i55 & 7) << 3)) & j18) < 128) {
                            Object obj4 = objArr4[i55];
                            int iHashCode3 = (obj4 != null ? obj4.hashCode() : 0) * (-862048943);
                            int i56 = iHashCode3 ^ (iHashCode3 << 16);
                            int iH4 = h(i56 >>> 7);
                            long j26 = i56 & 127;
                            int i57 = iH4 >> 3;
                            int i58 = (iH4 & 7) << 3;
                            jArr = jArr4;
                            objArr = objArr4;
                            long j27 = (jArr5[i57] & (~(255 << i58))) | (j26 << i58);
                            jArr5[i57] = j27;
                            jArr5[(((iH4 - 7) & i54) + (i54 & 7)) >> 3] = j27;
                            objArr6[iH4] = obj4;
                            objArr7[iH4] = objArr5[i55];
                        } else {
                            jArr = jArr4;
                            objArr = objArr4;
                        }
                        i55++;
                        jArr4 = jArr;
                        objArr4 = objArr;
                        j18 = 255;
                    }
                    i12 = 1;
                    i15 = i14;
                    iH2 = h(i15);
                }
                this.f25725e += i12;
                int i59 = this.f25606f;
                long[] jArr6 = this.f25721a;
                int i62 = iH2 >> 3;
                long j28 = jArr6[i62];
                int i63 = (iH2 & 7) << 3;
                if (((j28 >> i63) & 255) != 128) {
                    i12 = i13;
                }
                this.f25606f = i59 - i12;
                int i64 = this.f25724d;
                long j29 = (j28 & (~(255 << i63))) | (j12 << i63);
                jArr6[i62] = j29;
                jArr6[(((iH2 - 7) & i64) + (i64 & 7)) >> 3] = j29;
                return ~iH2;
            }
            i19 = 1;
            i27 += 8;
            i26 = (i26 + i27) & i25;
            i24 = i32;
        }
    }

    public final void j(int i12) {
        long[] jArr;
        int iMax = i12 > 0 ? Math.max(7, i1.d(i12)) : 0;
        this.f25724d = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i13];
            Arrays.fill(jArr2, 0, i13, -9187201950435737472L);
            int i14 = iMax >> 3;
            long j12 = 255 << ((iMax & 7) << 3);
            jArr2[i14] = (jArr2[i14] & (~j12)) | j12;
            jArr = jArr2;
        }
        this.f25721a = jArr;
        this.f25606f = i1.a(this.f25724d) - this.f25725e;
        Object[] objArr = K.a.f9206c;
        this.f25722b = iMax == 0 ? objArr : new Object[iMax];
        if (iMax != 0) {
            objArr = new Object[iMax];
        }
        this.f25723c = objArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0069, code lost:
    
        if (((r4 & ((~r4) << 6)) & (-9187201950435737472L)) == 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x006b, code lost:
    
        r10 = -1;
     */
    @Y61.l
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final V k(K r14) {
        /*
            r13 = this;
            r0 = 0
            if (r14 == 0) goto L8
            int r1 = r14.hashCode()
            goto L9
        L8:
            r1 = r0
        L9:
            r2 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r1 = r1 * r2
            int r2 = r1 << 16
            r1 = r1 ^ r2
            r2 = r1 & 127(0x7f, float:1.78E-43)
            int r3 = r13.f25724d
            int r1 = r1 >>> 7
        L16:
            r1 = r1 & r3
            long[] r4 = r13.f25721a
            int r5 = r1 >> 3
            r6 = r1 & 7
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
            long r6 = (long) r2
            r8 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r6 = r6 * r8
            long r6 = r6 ^ r4
            long r8 = r6 - r8
            long r6 = ~r6
            long r6 = r6 & r8
            r8 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r6 = r6 & r8
        L43:
            r10 = 0
            int r12 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r12 == 0) goto L62
            int r10 = java.lang.Long.numberOfTrailingZeros(r6)
            int r10 = r10 >> 3
            int r10 = r10 + r1
            r10 = r10 & r3
            java.lang.Object[] r11 = r13.f25722b
            r11 = r11[r10]
            boolean r11 = kotlin.jvm.internal.L.f(r11, r14)
            if (r11 == 0) goto L5c
            goto L6c
        L5c:
            r10 = 1
            long r10 = r6 - r10
            long r6 = r6 & r10
            goto L43
        L62:
            long r6 = ~r4
            r12 = 6
            long r6 = r6 << r12
            long r4 = r4 & r6
            long r4 = r4 & r8
            int r4 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r4 == 0) goto L75
            r10 = -1
        L6c:
            if (r10 < 0) goto L73
            java.lang.Object r14 = r13.l(r10)
            return r14
        L73:
            r14 = 0
            return r14
        L75:
            int r0 = r0 + 8
            int r1 = r1 + r0
            goto L16
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.R0.k(java.lang.Object):java.lang.Object");
    }

    @Y61.l
    @kotlin.X
    public final V l(int i12) {
        this.f25725e--;
        long[] jArr = this.f25721a;
        int i13 = this.f25724d;
        int i14 = i12 >> 3;
        int i15 = (i12 & 7) << 3;
        long j12 = (jArr[i14] & (~(255 << i15))) | (254 << i15);
        jArr[i14] = j12;
        jArr[(((i12 - 7) & i13) + (i13 & 7)) >> 3] = j12;
        this.f25722b[i12] = null;
        Object[] objArr = this.f25723c;
        V v12 = (V) objArr[i12];
        objArr[i12] = null;
        return v12;
    }

    public final void m(K k12, V v12) {
        int i12 = i(k12);
        if (i12 < 0) {
            i12 = ~i12;
        }
        this.f25722b[i12] = k12;
        this.f25723c[i12] = v12;
    }

    public /* synthetic */ R0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 6 : i12);
    }

    public R0(int i12) {
        super(null);
        if (i12 >= 0) {
            j(i1.e(i12));
        } else {
            K.f.a("Capacity must be a positive value.");
            throw null;
        }
    }
}
