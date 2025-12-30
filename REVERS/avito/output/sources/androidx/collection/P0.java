package androidx.collection;

import java.util.Arrays;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: OrderedScatterSet.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/P0;", "E", "Landroidx/collection/d1;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class P0<E> extends d1<E> {

    /* renamed from: h, reason: collision with root package name */
    public int f25588h;

    public P0() {
        this(0, 1, null);
    }

    public final boolean b(E e12) {
        int i12 = this.f25699g;
        int iE2 = e(e12);
        this.f25694b[iE2] = e12;
        long[] jArr = this.f25695c;
        int i13 = this.f25696d;
        jArr[iE2] = (i13 & 2147483647L) | 4611686016279904256L;
        if (i13 != Integer.MAX_VALUE) {
            jArr[i13] = ((iE2 & 2147483647L) << 31) | (jArr[i13] & (-4611686016279904257L));
        }
        this.f25696d = iE2;
        if (this.f25697e == Integer.MAX_VALUE) {
            this.f25697e = iE2;
        }
        return this.f25699g != i12;
    }

    @Y61.k
    public final Set<E> c() {
        return new Q0(this);
    }

    public final void d() {
        this.f25699g = 0;
        long[] jArr = this.f25693a;
        if (jArr != i1.f25727a) {
            C42756l.A(jArr, -9187201950435737472L);
            long[] jArr2 = this.f25693a;
            int i12 = this.f25698f;
            int i13 = i12 >> 3;
            long j12 = 255 << ((i12 & 7) << 3);
            jArr2[i13] = (jArr2[i13] & (~j12)) | j12;
        }
        Arrays.fill(this.f25694b, 0, this.f25698f, (Object) null);
        C42756l.A(this.f25695c, 4611686018427387903L);
        this.f25696d = Integer.MAX_VALUE;
        this.f25697e = Integer.MAX_VALUE;
        this.f25588h = i1.a(this.f25698f) - this.f25699g;
    }

    public final int e(E e12) {
        long j12;
        int i12;
        int i13;
        long[] jArr;
        Object[] objArr;
        long j13;
        int i14;
        int i15;
        long[] jArr2;
        int i16 = 1;
        int iHashCode = (e12 != null ? e12.hashCode() : 0) * (-862048943);
        int i17 = iHashCode ^ (iHashCode << 16);
        int i18 = i17 >>> 7;
        int i19 = i17 & 127;
        int i22 = this.f25698f;
        int i23 = i18 & i22;
        int i24 = 0;
        while (true) {
            long[] jArr3 = this.f25693a;
            int i25 = i23 >> 3;
            int i26 = (i23 & 7) << 3;
            long j14 = (((-i26) >> 63) & (jArr3[i25 + i16] << (64 - i26))) | (jArr3[i25] >>> i26);
            long j15 = i19;
            int i27 = i19;
            long j16 = j14 ^ (j15 * 72340172838076673L);
            long j17 = -9187201950435737472L;
            for (long j18 = (~j16) & (j16 - 72340172838076673L) & (-9187201950435737472L); j18 != 0; j18 &= j18 - 1) {
                int iNumberOfTrailingZeros = (i23 + (Long.numberOfTrailingZeros(j18) >> 3)) & i22;
                if (kotlin.jvm.internal.L.f(this.f25694b[iNumberOfTrailingZeros], e12)) {
                    return iNumberOfTrailingZeros;
                }
            }
            if ((j14 & ((~j14) << 6) & (-9187201950435737472L)) != 0) {
                int iF2 = f(i18);
                long j19 = 128;
                long j22 = 255;
                if (this.f25588h != 0 || ((this.f25693a[iF2 >> 3] >> ((iF2 & 7) << 3)) & 255) == 254) {
                    j12 = j15;
                    i12 = 0;
                } else {
                    int i28 = this.f25698f;
                    if (i28 > 8) {
                        j12 = j15;
                        long j23 = this.f25699g;
                        int i29 = kotlin.w0.f410662c;
                        if (Long.compareUnsigned(j23 * 32, i28 * 25) <= 0) {
                            long[] jArr4 = this.f25693a;
                            if (jArr4 == null) {
                                i13 = i18;
                                i12 = 0;
                            } else {
                                int i32 = this.f25698f;
                                Object[] objArr2 = this.f25694b;
                                long[] jArr5 = this.f25695c;
                                long[] jArr6 = new long[i32];
                                i13 = i18;
                                Arrays.fill(jArr6, 0, i32, 9223372034707292159L);
                                char c12 = 7;
                                int i33 = (i32 + 7) >> 3;
                                int i34 = 0;
                                while (i34 < i33) {
                                    long j24 = jArr4[i34] & j17;
                                    jArr4[i34] = (-72340172838076674L) & ((~j24) + (j24 >>> c12));
                                    i34++;
                                    j17 = -9187201950435737472L;
                                    c12 = 7;
                                }
                                int length = jArr4.length;
                                int i35 = length - 1;
                                int i36 = length - 2;
                                jArr4[i36] = (jArr4[i36] & 72057594037927935L) | (-72057594037927936L);
                                jArr4[i35] = jArr4[0];
                                int i37 = 0;
                                while (i37 != i32) {
                                    int i38 = i37 >> 3;
                                    int i39 = (i37 & 7) << 3;
                                    long j25 = (jArr4[i38] >> i39) & j22;
                                    if (j25 == j19) {
                                        i15 = 1;
                                    } else {
                                        i15 = 1;
                                        if (j25 == 254) {
                                            Object obj = objArr2[i37];
                                            int iHashCode2 = (obj != null ? obj.hashCode() : 0) * (-862048943);
                                            int i42 = (iHashCode2 ^ (iHashCode2 << 16)) >>> 7;
                                            int iF3 = f(i42);
                                            int i43 = i42 & i32;
                                            if (((iF3 - i43) & i32) / 8 == ((i37 - i43) & i32) / 8) {
                                                jArr4[i38] = (jArr4[i38] & (~(j22 << i39))) | ((r31 & 127) << i39);
                                                if (jArr6[i37] == 9223372034707292159L) {
                                                    long j26 = i37;
                                                    jArr6[i37] = (j26 << 32) | j26;
                                                }
                                                jArr4[jArr4.length - 1] = jArr4[0];
                                                i37++;
                                                j19 = 128;
                                            } else {
                                                int i44 = iF3 >> 3;
                                                long j27 = jArr4[i44];
                                                int i45 = (iF3 & 7) << 3;
                                                if (((j27 >> i45) & j22) == 128) {
                                                    jArr2 = jArr6;
                                                    jArr4[i44] = (j27 & (~(j22 << i45))) | ((r31 & 127) << i45);
                                                    jArr4[i38] = (jArr4[i38] & (~(j22 << i39))) | (128 << i39);
                                                    objArr2[iF3] = objArr2[i37];
                                                    objArr2[i37] = null;
                                                    jArr5[iF3] = jArr5[i37];
                                                    jArr5[i37] = 4611686018427387903L;
                                                    int i46 = (int) ((jArr2[i37] >> 32) & 4294967295L);
                                                    if (i46 != Integer.MAX_VALUE) {
                                                        jArr2[i46] = iF3 | (jArr2[i46] & (-4294967296L));
                                                        jArr2[i37] = (jArr2[i37] & 4294967295L) | (-4294967296L);
                                                    } else {
                                                        jArr2[i37] = (Integer.MAX_VALUE << 32) | iF3;
                                                    }
                                                    jArr2[iF3] = (i37 << 32) | Integer.MAX_VALUE;
                                                } else {
                                                    jArr2 = jArr6;
                                                    jArr4[i44] = ((r31 & 127) << i45) | (j27 & (~(255 << i45)));
                                                    Object obj2 = objArr2[iF3];
                                                    objArr2[iF3] = objArr2[i37];
                                                    objArr2[i37] = obj2;
                                                    long j28 = jArr5[iF3];
                                                    jArr5[iF3] = jArr5[i37];
                                                    jArr5[i37] = j28;
                                                    int i47 = (int) ((jArr2[i37] >> 32) & 4294967295L);
                                                    if (i47 != Integer.MAX_VALUE) {
                                                        long j29 = iF3;
                                                        jArr2[i47] = (jArr2[i47] & (-4294967296L)) | j29;
                                                        jArr2[i37] = (jArr2[i37] & 4294967295L) | (j29 << 32);
                                                    } else {
                                                        long j32 = iF3;
                                                        jArr2[i37] = j32 | (j32 << 32);
                                                        i47 = i37;
                                                    }
                                                    jArr2[iF3] = (i47 << 32) | i37;
                                                    i37--;
                                                }
                                                jArr4[jArr4.length - 1] = jArr4[0];
                                                i37++;
                                                jArr6 = jArr2;
                                                j19 = 128;
                                                j22 = 255;
                                            }
                                        }
                                    }
                                    i37 += i15;
                                }
                                long[] jArr7 = jArr6;
                                i12 = 0;
                                this.f25588h = i1.a(this.f25698f) - this.f25699g;
                                long[] jArr8 = this.f25695c;
                                int length2 = jArr8.length;
                                for (int i48 = 0; i48 < length2; i48++) {
                                    long j33 = jArr8[i48];
                                    int i49 = (int) ((j33 >> 31) & 2147483647L);
                                    int i52 = (int) (j33 & 2147483647L);
                                    long j34 = j33 & (-4611686018427387904L);
                                    if (i49 == Integer.MAX_VALUE) {
                                        i14 = Integer.MAX_VALUE;
                                        j13 = 4294967295L;
                                    } else {
                                        j13 = 4294967295L;
                                        i14 = (int) (jArr7[i49] & 4294967295L);
                                    }
                                    jArr8[i48] = ((j34 | i14) << 31) | (i52 == Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) (jArr7[i52] & j13));
                                }
                                int i53 = this.f25696d;
                                if (i53 != Integer.MAX_VALUE) {
                                    this.f25696d = (int) (jArr7[i53] & 4294967295L);
                                }
                                int i54 = this.f25697e;
                                if (i54 != Integer.MAX_VALUE) {
                                    this.f25697e = (int) (jArr7[i54] & 4294967295L);
                                }
                            }
                            iF2 = f(i13);
                        } else {
                            i13 = i18;
                        }
                    } else {
                        i13 = i18;
                        j12 = j15;
                    }
                    i12 = 0;
                    int iC2 = i1.c(this.f25698f);
                    long[] jArr9 = this.f25693a;
                    Object[] objArr3 = this.f25694b;
                    long[] jArr10 = this.f25695c;
                    int i55 = this.f25698f;
                    int[] iArr = new int[i55];
                    g(iC2);
                    long[] jArr11 = this.f25693a;
                    Object[] objArr4 = this.f25694b;
                    long[] jArr12 = this.f25695c;
                    int i56 = this.f25698f;
                    int i57 = 0;
                    while (i57 < i55) {
                        if (((jArr9[i57 >> 3] >> ((i57 & 7) << 3)) & 255) < 128) {
                            Object obj3 = objArr3[i57];
                            int iHashCode3 = (obj3 != null ? obj3.hashCode() : 0) * (-862048943);
                            int i58 = iHashCode3 ^ (iHashCode3 << 16);
                            int iF4 = f(i58 >>> 7);
                            jArr = jArr9;
                            objArr = objArr3;
                            long j35 = i58 & 127;
                            int i59 = iF4 >> 3;
                            int i62 = (iF4 & 7) << 3;
                            long j36 = (j35 << i62) | (jArr11[i59] & (~(255 << i62)));
                            jArr11[i59] = j36;
                            jArr11[(((iF4 - 7) & i56) + (i56 & 7)) >> 3] = j36;
                            objArr4[iF4] = obj3;
                            jArr12[iF4] = jArr10[i57];
                            iArr[i57] = iF4;
                        } else {
                            jArr = jArr9;
                            objArr = objArr3;
                        }
                        i57++;
                        jArr9 = jArr;
                        objArr3 = objArr;
                    }
                    long[] jArr13 = this.f25695c;
                    int length3 = jArr13.length;
                    for (int i63 = 0; i63 < length3; i63++) {
                        long j37 = jArr13[i63];
                        jArr13[i63] = (((j37 & (-4611686018427387904L)) | (((int) ((j37 >> 31) & 2147483647L)) == Integer.MAX_VALUE ? Integer.MAX_VALUE : iArr[r7])) << 31) | (((int) (j37 & 2147483647L)) == Integer.MAX_VALUE ? Integer.MAX_VALUE : iArr[r8]);
                    }
                    int i64 = this.f25696d;
                    if (i64 != Integer.MAX_VALUE) {
                        this.f25696d = iArr[i64];
                    }
                    int i65 = this.f25697e;
                    if (i65 != Integer.MAX_VALUE) {
                        this.f25697e = iArr[i65];
                    }
                    iF2 = f(i13);
                }
                this.f25699g++;
                int i66 = this.f25588h;
                long[] jArr14 = this.f25693a;
                int i67 = iF2 >> 3;
                long j38 = jArr14[i67];
                int i68 = (iF2 & 7) << 3;
                this.f25588h = i66 - (((j38 >> i68) & 255) != 128 ? i12 : 1);
                int i69 = this.f25698f;
                long j39 = (j38 & (~(255 << i68))) | (j12 << i68);
                jArr14[i67] = j39;
                jArr14[(((iF2 - 7) & i69) + (i69 & 7)) >> 3] = j39;
                return iF2;
            }
            i24 += 8;
            i23 = (i23 + i24) & i22;
            i16 = 1;
            i19 = i27;
        }
    }

    public final int f(int i12) {
        int i13 = this.f25698f;
        int i14 = i12 & i13;
        int i15 = 0;
        while (true) {
            long[] jArr = this.f25693a;
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

    public final void g(int i12) {
        long[] jArr;
        long[] jArr2;
        int iMax = i12 > 0 ? Math.max(7, i1.d(i12)) : 0;
        this.f25698f = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr3 = new long[i13];
            Arrays.fill(jArr3, 0, i13, -9187201950435737472L);
            jArr = jArr3;
        }
        this.f25693a = jArr;
        int i14 = iMax >> 3;
        long j12 = 255 << ((iMax & 7) << 3);
        jArr[i14] = (jArr[i14] & (~j12)) | j12;
        this.f25588h = i1.a(this.f25698f) - this.f25699g;
        this.f25694b = iMax == 0 ? K.a.f9206c : new Object[iMax];
        if (iMax == 0) {
            jArr2 = n1.f25786a;
        } else {
            long[] jArr4 = new long[iMax];
            Arrays.fill(jArr4, 0, iMax, 4611686018427387903L);
            jArr2 = jArr4;
        }
        this.f25695c = jArr2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x006e, code lost:
    
        if (((r7 & ((~r7) << 6)) & (-9187201950435737472L)) == 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0070, code lost:
    
        r11 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean h(E r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = 0
            if (r1 == 0) goto Lc
            int r3 = r18.hashCode()
            goto Ld
        Lc:
            r3 = r2
        Ld:
            r4 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r3 = r3 * r4
            int r4 = r3 << 16
            r3 = r3 ^ r4
            r4 = r3 & 127(0x7f, float:1.78E-43)
            int r5 = r0.f25698f
            int r3 = r3 >>> 7
            r3 = r3 & r5
            r6 = r2
        L1c:
            long[] r7 = r0.f25693a
            int r8 = r3 >> 3
            r9 = r3 & 7
            int r9 = r9 << 3
            r10 = r7[r8]
            long r10 = r10 >>> r9
            r12 = 1
            int r8 = r8 + r12
            r13 = r7[r8]
            int r7 = 64 - r9
            long r7 = r13 << r7
            long r13 = (long) r9
            long r13 = -r13
            r9 = 63
            long r13 = r13 >> r9
            long r7 = r7 & r13
            long r7 = r7 | r10
            long r9 = (long) r4
            r13 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r9 = r9 * r13
            long r9 = r9 ^ r7
            long r13 = r9 - r13
            long r9 = ~r9
            long r9 = r9 & r13
            r13 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r9 = r9 & r13
        L48:
            r15 = 0
            int r11 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
            if (r11 == 0) goto L67
            int r11 = java.lang.Long.numberOfTrailingZeros(r9)
            int r11 = r11 >> 3
            int r11 = r11 + r3
            r11 = r11 & r5
            java.lang.Object[] r15 = r0.f25694b
            r15 = r15[r11]
            boolean r15 = kotlin.jvm.internal.L.f(r15, r1)
            if (r15 == 0) goto L61
            goto L71
        L61:
            r15 = 1
            long r15 = r9 - r15
            long r9 = r9 & r15
            goto L48
        L67:
            long r9 = ~r7
            r11 = 6
            long r9 = r9 << r11
            long r7 = r7 & r9
            long r7 = r7 & r13
            int r7 = (r7 > r15 ? 1 : (r7 == r15 ? 0 : -1))
            if (r7 == 0) goto L7a
            r11 = -1
        L71:
            if (r11 < 0) goto L74
            r2 = r12
        L74:
            if (r2 == 0) goto L79
            r0.i(r11)
        L79:
            return r2
        L7a:
            int r6 = r6 + 8
            int r3 = r3 + r6
            r3 = r3 & r5
            goto L1c
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.P0.h(java.lang.Object):boolean");
    }

    @kotlin.X
    public final void i(int i12) {
        this.f25699g--;
        long[] jArr = this.f25693a;
        int i13 = this.f25698f;
        int i14 = i12 >> 3;
        int i15 = (i12 & 7) << 3;
        long j12 = (jArr[i14] & (~(255 << i15))) | (254 << i15);
        jArr[i14] = j12;
        jArr[(((i12 - 7) & i13) + (i13 & 7)) >> 3] = j12;
        this.f25694b[i12] = null;
        long[] jArr2 = this.f25695c;
        long j13 = jArr2[i12];
        int i16 = (int) ((j13 >> 31) & 2147483647L);
        int i17 = (int) (j13 & 2147483647L);
        if (i16 != Integer.MAX_VALUE) {
            jArr2[i16] = (jArr2[i16] & (-2147483648L)) | (i17 & 2147483647L);
        } else {
            this.f25696d = i17;
        }
        if (i17 != Integer.MAX_VALUE) {
            jArr2[i17] = ((i16 & 2147483647L) << 31) | (jArr2[i17] & (-4611686016279904257L));
        } else {
            this.f25697e = i16;
        }
        jArr2[i12] = 4611686018427387903L;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean j(@Y61.k java.util.Collection<? extends E> r17) {
        /*
            r16 = this;
            r0 = r16
            java.lang.Object[] r1 = r0.f25694b
            int r2 = r0.f25699g
            long[] r3 = r0.f25693a
            int r4 = r3.length
            int r4 = r4 + (-2)
            r5 = 0
            if (r4 < 0) goto L51
            r6 = r5
        Lf:
            r7 = r3[r6]
            long r9 = ~r7
            r11 = 7
            long r9 = r9 << r11
            long r9 = r9 & r7
            r11 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r9 = r9 & r11
            int r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r9 == 0) goto L4c
            int r9 = r6 - r4
            int r9 = ~r9
            int r9 = r9 >>> 31
            r10 = 8
            int r9 = 8 - r9
            r11 = r5
        L29:
            if (r11 >= r9) goto L4a
            r12 = 255(0xff, double:1.26E-321)
            long r12 = r12 & r7
            r14 = 128(0x80, double:6.3E-322)
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 >= 0) goto L46
            int r12 = r6 << 3
            int r12 = r12 + r11
            r13 = r17
            java.lang.Iterable r13 = (java.lang.Iterable) r13
            r14 = r1[r12]
            boolean r13 = kotlin.collections.C42745f0.r(r13, r14)
            if (r13 != 0) goto L46
            r0.i(r12)
        L46:
            long r7 = r7 >> r10
            int r11 = r11 + 1
            goto L29
        L4a:
            if (r9 != r10) goto L51
        L4c:
            if (r6 == r4) goto L51
            int r6 = r6 + 1
            goto Lf
        L51:
            int r1 = r0.f25699g
            if (r2 == r1) goto L56
            r5 = 1
        L56:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.P0.j(java.util.Collection):boolean");
    }

    public /* synthetic */ P0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 6 : i12);
    }

    public P0(int i12) {
        super(null);
        if (i12 >= 0) {
            g(i1.e(i12));
        } else {
            K.f.a("Capacity must be a positive value.");
            throw null;
        }
    }
}
