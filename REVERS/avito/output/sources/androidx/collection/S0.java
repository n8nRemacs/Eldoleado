package androidx.collection;

import java.util.Arrays;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: ScatterSet.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/S0;", "E", "Landroidx/collection/j1;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class S0<E> extends j1<E> {

    /* renamed from: e, reason: collision with root package name */
    public int f25608e;

    public S0() {
        this(0, 1, null);
    }

    public final boolean e(E e12) {
        int i12 = this.f25732d;
        this.f25730b[h(e12)] = e12;
        return this.f25732d != i12;
    }

    @Y61.k
    public final Set<E> f() {
        return new T0(this);
    }

    public final void g() {
        this.f25732d = 0;
        long[] jArr = this.f25729a;
        if (jArr != i1.f25727a) {
            C42756l.A(jArr, -9187201950435737472L);
            long[] jArr2 = this.f25729a;
            int i12 = this.f25731c;
            int i13 = i12 >> 3;
            long j12 = 255 << ((i12 & 7) << 3);
            jArr2[i13] = (jArr2[i13] & (~j12)) | j12;
        }
        Arrays.fill(this.f25730b, 0, this.f25731c, (Object) null);
        this.f25608e = i1.a(this.f25731c) - this.f25732d;
    }

    public final int h(E e12) {
        long j12;
        int i12;
        int i13;
        int i14;
        Object[] objArr;
        int i15;
        long j13;
        int i16;
        Object[] objArr2;
        int i17;
        int i18 = 1;
        int iHashCode = (e12 != null ? e12.hashCode() : 0) * (-862048943);
        int i19 = iHashCode ^ (iHashCode << 16);
        int i22 = i19 >>> 7;
        int i23 = i19 & 127;
        int i24 = this.f25731c;
        int i25 = i22 & i24;
        int i26 = 0;
        while (true) {
            long[] jArr = this.f25729a;
            int i27 = i25 >> 3;
            int i28 = (i25 & 7) << 3;
            long j14 = (((-i28) >> 63) & (jArr[i27 + i18] << (64 - i28))) | (jArr[i27] >>> i28);
            long j15 = i23;
            int i29 = i23;
            long j16 = j14 ^ (j15 * 72340172838076673L);
            long j17 = -9187201950435737472L;
            for (long j18 = (~j16) & (j16 - 72340172838076673L) & (-9187201950435737472L); j18 != 0; j18 &= j18 - 1) {
                int iNumberOfTrailingZeros = (i25 + (Long.numberOfTrailingZeros(j18) >> 3)) & i24;
                if (kotlin.jvm.internal.L.f(this.f25730b[iNumberOfTrailingZeros], e12)) {
                    return iNumberOfTrailingZeros;
                }
            }
            if ((j14 & ((~j14) << 6) & (-9187201950435737472L)) != 0) {
                int i32 = i(i22);
                if (this.f25608e != 0 || ((this.f25729a[i32 >> 3] >> ((i32 & 7) << 3)) & 255) == 254) {
                    j12 = j15;
                    i12 = 1;
                } else {
                    int i33 = this.f25731c;
                    if (i33 > 8) {
                        i13 = i22;
                        long j19 = this.f25732d;
                        int i34 = kotlin.w0.f410662c;
                        if (Long.compareUnsigned(j19 * 32, i33 * 25) <= 0) {
                            long[] jArr2 = this.f25729a;
                            int i35 = this.f25731c;
                            Object[] objArr3 = this.f25730b;
                            char c12 = 7;
                            int i36 = (i35 + 7) >> 3;
                            int i37 = 0;
                            while (i37 < i36) {
                                long j22 = jArr2[i37] & j17;
                                jArr2[i37] = (-72340172838076674L) & ((~j22) + (j22 >>> c12));
                                i37++;
                                c12 = 7;
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
                                        Object obj = objArr3[i42];
                                        int iHashCode2 = (obj != null ? obj.hashCode() : 0) * (-862048943);
                                        int i45 = (iHashCode2 ^ (iHashCode2 << 16)) >>> 7;
                                        int i46 = i(i45);
                                        int i47 = i45 & i35;
                                        if (((i46 - i47) & i35) / 8 == ((i42 - i47) & i35) / 8) {
                                            j13 = j15;
                                            jArr2[i43] = (jArr2[i43] & (~(255 << i44))) | ((r7 & 127) << i44);
                                            jArr2[jArr2.length - 1] = (jArr2[0] & 72057594037927935L) | Long.MIN_VALUE;
                                            i42++;
                                        } else {
                                            j13 = j15;
                                            int i48 = i46 >> 3;
                                            long j24 = jArr2[i48];
                                            int i49 = (i46 & 7) << 3;
                                            if (((j24 >> i49) & 255) == 128) {
                                                i16 = i35;
                                                int i52 = i42;
                                                objArr2 = objArr3;
                                                jArr2[i48] = ((r7 & 127) << i49) | ((~(255 << i49)) & j24);
                                                jArr2[i43] = (jArr2[i43] & (~(255 << i44))) | (128 << i44);
                                                objArr2[i46] = objArr2[i52];
                                                objArr2[i52] = null;
                                                i17 = i52;
                                            } else {
                                                i16 = i35;
                                                int i53 = i42;
                                                objArr2 = objArr3;
                                                jArr2[i48] = ((r7 & 127) << i49) | ((~(255 << i49)) & j24);
                                                Object obj2 = objArr2[i46];
                                                objArr2[i46] = objArr2[i53];
                                                objArr2[i53] = obj2;
                                                i17 = i53 - 1;
                                            }
                                            jArr2[jArr2.length - 1] = (jArr2[0] & 72057594037927935L) | Long.MIN_VALUE;
                                            i42 = i17 + 1;
                                            objArr3 = objArr2;
                                            i35 = i16;
                                        }
                                        j15 = j13;
                                    }
                                }
                                i42 += i15;
                            }
                            j12 = j15;
                            this.f25608e = i1.a(this.f25731c) - this.f25732d;
                            i14 = i13;
                            i12 = 1;
                        }
                        i32 = i(i14);
                    } else {
                        i13 = i22;
                    }
                    j12 = j15;
                    int i54 = 0;
                    int iC2 = i1.c(this.f25731c);
                    long[] jArr3 = this.f25729a;
                    Object[] objArr4 = this.f25730b;
                    int i55 = this.f25731c;
                    j(iC2);
                    long[] jArr4 = this.f25729a;
                    Object[] objArr5 = this.f25730b;
                    int i56 = this.f25731c;
                    int i57 = 0;
                    while (i57 < i55) {
                        if (((jArr3[i57 >> 3] >> ((i57 & 7) << 3)) & 255) < 128) {
                            Object obj3 = objArr4[i57];
                            int iHashCode3 = (obj3 != null ? obj3.hashCode() : i54) * (-862048943);
                            int i58 = i((iHashCode3 ^ (iHashCode3 << 16)) >>> 7);
                            int i59 = i58 >> 3;
                            int i62 = (i58 & 7) << 3;
                            objArr = objArr5;
                            long j25 = (jArr4[i59] & (~(255 << i62))) | ((r13 & 127) << i62);
                            jArr4[i59] = j25;
                            jArr4[(((i58 - 7) & i56) + (i56 & 7)) >> 3] = j25;
                            objArr[i58] = obj3;
                        } else {
                            objArr = objArr5;
                        }
                        i57++;
                        objArr5 = objArr;
                        i54 = 0;
                    }
                    i12 = 1;
                    i14 = i13;
                    i32 = i(i14);
                }
                this.f25732d += i12;
                int i63 = this.f25608e;
                long[] jArr5 = this.f25729a;
                int i64 = i32 >> 3;
                long j26 = jArr5[i64];
                int i65 = (i32 & 7) << 3;
                if (((j26 >> i65) & 255) != 128) {
                    i12 = 0;
                }
                this.f25608e = i63 - i12;
                int i66 = this.f25731c;
                long j27 = (j26 & (~(255 << i65))) | (j12 << i65);
                jArr5[i64] = j27;
                jArr5[(((i32 - 7) & i66) + (i66 & 7)) >> 3] = j27;
                return i32;
            }
            i26 += 8;
            i25 = (i25 + i26) & i24;
            i18 = 1;
            i23 = i29;
        }
    }

    public final int i(int i12) {
        int i13 = this.f25731c;
        int i14 = i12 & i13;
        int i15 = 0;
        while (true) {
            long[] jArr = this.f25729a;
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

    public final void j(int i12) {
        long[] jArr;
        int iMax = i12 > 0 ? Math.max(7, i1.d(i12)) : 0;
        this.f25731c = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i13];
            Arrays.fill(jArr2, 0, i13, -9187201950435737472L);
            jArr = jArr2;
        }
        this.f25729a = jArr;
        int i14 = iMax >> 3;
        long j12 = 255 << ((iMax & 7) << 3);
        jArr[i14] = (jArr[i14] & (~j12)) | j12;
        this.f25608e = i1.a(this.f25731c) - this.f25732d;
        this.f25730b = iMax == 0 ? K.a.f9206c : new Object[iMax];
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0069, code lost:
    
        if (((r4 & ((~r4) << 6)) & (-9187201950435737472L)) == 0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x006b, code lost:
    
        r10 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void k(E r14) {
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
            int r3 = r13.f25731c
            int r1 = r1 >>> 7
        L16:
            r1 = r1 & r3
            long[] r4 = r13.f25729a
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
            java.lang.Object[] r11 = r13.f25730b
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
            if (r4 == 0) goto L72
            r10 = -1
        L6c:
            if (r10 < 0) goto L71
            r13.o(r10)
        L71:
            return
        L72:
            int r0 = r0 + 8
            int r1 = r1 + r0
            goto L16
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.S0.k(java.lang.Object):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void l(@Y61.k j1<E> j1Var) {
        Object[] objArr = j1Var.f25730b;
        long[] jArr = j1Var.f25729a;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i12 = 0;
        while (true) {
            long j12 = jArr[i12];
            if ((((~j12) << 7) & j12 & (-9187201950435737472L)) != -9187201950435737472L) {
                int i13 = 8 - ((~(i12 - length)) >>> 31);
                for (int i14 = 0; i14 < i13; i14++) {
                    if ((255 & j12) < 128) {
                        m(objArr[(i12 << 3) + i14]);
                    }
                    j12 >>= 8;
                }
                if (i13 != 8) {
                    return;
                }
            }
            if (i12 == length) {
                return;
            } else {
                i12++;
            }
        }
    }

    public final void m(E e12) {
        this.f25730b[h(e12)] = e12;
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
    public final boolean n(E r18) {
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
            int r5 = r0.f25731c
            int r3 = r3 >>> 7
            r3 = r3 & r5
            r6 = r2
        L1c:
            long[] r7 = r0.f25729a
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
            java.lang.Object[] r15 = r0.f25730b
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
            r0.o(r11)
        L79:
            return r2
        L7a:
            int r6 = r6 + 8
            int r3 = r3 + r6
            r3 = r3 & r5
            goto L1c
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.S0.n(java.lang.Object):boolean");
    }

    @kotlin.X
    public final void o(int i12) {
        this.f25732d--;
        long[] jArr = this.f25729a;
        int i13 = this.f25731c;
        int i14 = i12 >> 3;
        int i15 = (i12 & 7) << 3;
        long j12 = (jArr[i14] & (~(255 << i15))) | (254 << i15);
        jArr[i14] = j12;
        jArr[(((i12 - 7) & i13) + (i13 & 7)) >> 3] = j12;
        this.f25730b[i12] = null;
    }

    public S0(int i12) {
        super(null);
        if (i12 >= 0) {
            j(i1.e(i12));
        } else {
            K.f.a("Capacity must be a positive value.");
            throw null;
        }
    }

    public /* synthetic */ S0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 6 : i12);
    }
}
