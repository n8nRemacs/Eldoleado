package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: ObjectIntMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/M0;", "K", "Landroidx/collection/X0;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class M0<K> extends X0<K> {

    /* renamed from: f, reason: collision with root package name */
    public int f25565f;

    public M0() {
        this(0, 1, null);
    }

    public final void c() {
        this.f25651e = 0;
        long[] jArr = this.f25647a;
        if (jArr != i1.f25727a) {
            C42756l.A(jArr, -9187201950435737472L);
            long[] jArr2 = this.f25647a;
            int i12 = this.f25650d;
            int i13 = i12 >> 3;
            long j12 = 255 << ((i12 & 7) << 3);
            jArr2[i13] = (jArr2[i13] & (~j12)) | j12;
        }
        Arrays.fill(this.f25648b, 0, this.f25650d, (Object) null);
        this.f25565f = i1.a(this.f25650d) - this.f25651e;
    }

    public final int d(int i12) {
        int i13 = this.f25650d;
        int i14 = i12 & i13;
        int i15 = 0;
        while (true) {
            long[] jArr = this.f25647a;
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

    public final int e(K k12) {
        long j12;
        int i12;
        int i13;
        int i14;
        long[] jArr;
        Object[] objArr;
        int i15;
        int i16;
        int i17;
        int i18 = 1;
        int iHashCode = (k12 != null ? k12.hashCode() : 0) * (-862048943);
        int i19 = iHashCode ^ (iHashCode << 16);
        int i22 = i19 >>> 7;
        int i23 = i19 & 127;
        int i24 = this.f25650d;
        int i25 = i22 & i24;
        int i26 = 0;
        while (true) {
            long[] jArr2 = this.f25647a;
            int i27 = i25 >> 3;
            int i28 = (i25 & 7) << 3;
            long j13 = (((-i28) >> 63) & (jArr2[i27 + i18] << (64 - i28))) | (jArr2[i27] >>> i28);
            long j14 = i23;
            int i29 = i23;
            long j15 = j13 ^ (j14 * 72340172838076673L);
            long j16 = -9187201950435737472L;
            for (long j17 = (~j15) & (j15 - 72340172838076673L) & (-9187201950435737472L); j17 != 0; j17 &= j17 - 1) {
                int iNumberOfTrailingZeros = (i25 + (Long.numberOfTrailingZeros(j17) >> 3)) & i24;
                if (kotlin.jvm.internal.L.f(this.f25648b[iNumberOfTrailingZeros], k12)) {
                    return iNumberOfTrailingZeros;
                }
            }
            if ((j13 & ((~j13) << 6) & (-9187201950435737472L)) != 0) {
                int iD2 = d(i22);
                if (this.f25565f != 0 || ((this.f25647a[iD2 >> 3] >> ((iD2 & 7) << 3)) & 255) == 254) {
                    j12 = j14;
                    i12 = 1;
                } else {
                    int i32 = this.f25650d;
                    if (i32 > 8) {
                        i13 = i22;
                        long j18 = this.f25651e;
                        int i33 = kotlin.w0.f410662c;
                        if (Long.compareUnsigned(j18 * 32, i32 * 25) <= 0) {
                            long[] jArr3 = this.f25647a;
                            int i34 = this.f25650d;
                            Object[] objArr2 = this.f25648b;
                            int[] iArr = this.f25649c;
                            char c12 = 7;
                            int i35 = (i34 + 7) >> 3;
                            int i36 = 0;
                            while (i36 < i35) {
                                long j19 = jArr3[i36] & j16;
                                jArr3[i36] = (-72340172838076674L) & ((~j19) + (j19 >>> c12));
                                i36++;
                                c12 = 7;
                                j16 = -9187201950435737472L;
                            }
                            int length = jArr3.length;
                            int i37 = length - 1;
                            int i38 = length - 2;
                            jArr3[i38] = (jArr3[i38] & 72057594037927935L) | (-72057594037927936L);
                            jArr3[i37] = jArr3[0];
                            int i39 = 0;
                            while (i39 != i34) {
                                int i42 = i39 >> 3;
                                int i43 = (i39 & 7) << 3;
                                long j22 = (jArr3[i42] >> i43) & 255;
                                if (j22 == 128) {
                                    i15 = 1;
                                } else {
                                    i15 = 1;
                                    if (j22 == 254) {
                                        Object obj = objArr2[i39];
                                        int iHashCode2 = (obj != null ? obj.hashCode() : 0) * (-862048943);
                                        int i44 = (iHashCode2 ^ (iHashCode2 << 16)) >>> 7;
                                        int iD3 = d(i44);
                                        int i45 = i44 & i34;
                                        if (((iD3 - i45) & i34) / 8 == ((i39 - i45) & i34) / 8) {
                                            jArr3[i42] = (jArr3[i42] & (~(255 << i43))) | ((r10 & 127) << i43);
                                            jArr3[jArr3.length - 1] = (jArr3[0] & 72057594037927935L) | Long.MIN_VALUE;
                                            i39++;
                                            j14 = j14;
                                        } else {
                                            long j23 = j14;
                                            int i46 = iD3 >> 3;
                                            long j24 = jArr3[i46];
                                            int i47 = (iD3 & 7) << 3;
                                            if (((j24 >> i47) & 255) == 128) {
                                                i16 = i34;
                                                int i48 = i39;
                                                jArr3[i46] = (j24 & (~(255 << i47))) | ((r10 & 127) << i47);
                                                jArr3[i42] = (jArr3[i42] & (~(255 << i43))) | (128 << i43);
                                                objArr2[iD3] = objArr2[i48];
                                                objArr2[i48] = null;
                                                iArr[iD3] = iArr[i48];
                                                iArr[i48] = 0;
                                                i17 = i48;
                                            } else {
                                                i16 = i34;
                                                int i49 = i39;
                                                jArr3[i46] = ((r10 & 127) << i47) | (j24 & (~(255 << i47)));
                                                Object obj2 = objArr2[iD3];
                                                objArr2[iD3] = objArr2[i49];
                                                objArr2[i49] = obj2;
                                                int i52 = iArr[iD3];
                                                iArr[iD3] = iArr[i49];
                                                iArr[i49] = i52;
                                                i17 = i49 - 1;
                                            }
                                            jArr3[jArr3.length - 1] = (jArr3[0] & 72057594037927935L) | Long.MIN_VALUE;
                                            i39 = i17 + 1;
                                            j14 = j23;
                                            i34 = i16;
                                        }
                                    }
                                }
                                i39 += i15;
                            }
                            j12 = j14;
                            this.f25565f = i1.a(this.f25650d) - this.f25651e;
                            i14 = i13;
                            i12 = 1;
                        }
                        iD2 = d(i14);
                    } else {
                        i13 = i22;
                    }
                    j12 = j14;
                    int i53 = 0;
                    int iC2 = i1.c(this.f25650d);
                    long[] jArr4 = this.f25647a;
                    Object[] objArr3 = this.f25648b;
                    int[] iArr2 = this.f25649c;
                    int i54 = this.f25650d;
                    f(iC2);
                    long[] jArr5 = this.f25647a;
                    Object[] objArr4 = this.f25648b;
                    int[] iArr3 = this.f25649c;
                    int i55 = this.f25650d;
                    int i56 = 0;
                    while (i56 < i54) {
                        if (((jArr4[i56 >> 3] >> ((i56 & 7) << 3)) & 255) < 128) {
                            Object obj3 = objArr3[i56];
                            int iHashCode3 = (obj3 != null ? obj3.hashCode() : i53) * (-862048943);
                            int iD4 = d((iHashCode3 ^ (iHashCode3 << 16)) >>> 7);
                            int i57 = iD4 >> 3;
                            int i58 = (iD4 & 7) << 3;
                            jArr = jArr4;
                            objArr = objArr3;
                            long j25 = (jArr5[i57] & (~(255 << i58))) | ((r15 & 127) << i58);
                            jArr5[i57] = j25;
                            jArr5[(((iD4 - 7) & i55) + (i55 & 7)) >> 3] = j25;
                            objArr4[iD4] = obj3;
                            iArr3[iD4] = iArr2[i56];
                        } else {
                            jArr = jArr4;
                            objArr = objArr3;
                        }
                        i56++;
                        jArr4 = jArr;
                        objArr3 = objArr;
                        i53 = 0;
                    }
                    i12 = 1;
                    i14 = i13;
                    iD2 = d(i14);
                }
                this.f25651e += i12;
                int i59 = this.f25565f;
                long[] jArr6 = this.f25647a;
                int i62 = iD2 >> 3;
                long j26 = jArr6[i62];
                int i63 = (iD2 & 7) << 3;
                if (((j26 >> i63) & 255) != 128) {
                    i12 = 0;
                }
                this.f25565f = i59 - i12;
                int i64 = this.f25650d;
                long j27 = (j26 & (~(255 << i63))) | (j12 << i63);
                jArr6[i62] = j27;
                jArr6[(((iD2 - 7) & i64) + (i64 & 7)) >> 3] = j27;
                return ~iD2;
            }
            i18 = 1;
            i26 += 8;
            i25 = (i25 + i26) & i24;
            i23 = i29;
        }
    }

    public final void f(int i12) {
        long[] jArr;
        int iMax = i12 > 0 ? Math.max(7, i1.d(i12)) : 0;
        this.f25650d = iMax;
        if (iMax == 0) {
            jArr = i1.f25727a;
        } else {
            int i13 = ((iMax + 15) & (-8)) >> 3;
            long[] jArr2 = new long[i13];
            Arrays.fill(jArr2, 0, i13, -9187201950435737472L);
            jArr = jArr2;
        }
        this.f25647a = jArr;
        int i14 = iMax >> 3;
        long j12 = 255 << ((iMax & 7) << 3);
        jArr[i14] = (jArr[i14] & (~j12)) | j12;
        this.f25565f = i1.a(this.f25650d) - this.f25651e;
        this.f25648b = new Object[iMax];
        this.f25649c = new int[iMax];
    }

    @kotlin.X
    public final void g(int i12) {
        this.f25651e--;
        long[] jArr = this.f25647a;
        int i13 = this.f25650d;
        int i14 = i12 >> 3;
        int i15 = (i12 & 7) << 3;
        long j12 = (jArr[i14] & (~(255 << i15))) | (254 << i15);
        jArr[i14] = j12;
        jArr[(((i12 - 7) & i13) + (i13 & 7)) >> 3] = j12;
        this.f25648b[i12] = null;
    }

    public final void h(int i12, Object obj) {
        int iE2 = e(obj);
        if (iE2 < 0) {
            iE2 = ~iE2;
        }
        this.f25648b[iE2] = obj;
        this.f25649c[iE2] = i12;
    }

    public /* synthetic */ M0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 6 : i12);
    }

    public M0(int i12) {
        super(null);
        if (i12 >= 0) {
            f(i1.e(i12));
        } else {
            K.f.a("Capacity must be a positive value.");
            throw null;
        }
    }
}
