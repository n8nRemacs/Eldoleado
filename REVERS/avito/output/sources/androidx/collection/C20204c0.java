package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: LongSparseArray.jvm.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001a\n\u0000\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/c0;", "E", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.c0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C20204c0<E> implements Cloneable {

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    public /* synthetic */ boolean f25689b;

    /* renamed from: c, reason: collision with root package name */
    @X41.f
    public /* synthetic */ long[] f25690c;

    /* renamed from: d, reason: collision with root package name */
    @X41.f
    public /* synthetic */ Object[] f25691d;

    /* renamed from: e, reason: collision with root package name */
    @X41.f
    public /* synthetic */ int f25692e;

    @X41.j
    public C20204c0() {
        this(0, 1, null);
    }

    public final void a() {
        int i12 = this.f25692e;
        Object[] objArr = this.f25691d;
        for (int i13 = 0; i13 < i12; i13++) {
            objArr[i13] = null;
        }
        this.f25692e = 0;
        this.f25689b = false;
    }

    @Y61.k
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public final C20204c0<E> clone() {
        C20204c0<E> c20204c0 = (C20204c0) super.clone();
        c20204c0.f25690c = (long[]) this.f25690c.clone();
        c20204c0.f25691d = (Object[]) this.f25691d.clone();
        return c20204c0;
    }

    @Y61.l
    public final E f(long j12) {
        E e12;
        int iB2 = K.a.b(this.f25692e, j12, this.f25690c);
        if (iB2 < 0 || (e12 = (E) this.f25691d[iB2]) == C20210f0.f25703a) {
            return null;
        }
        return e12;
    }

    public final Object g(long j12) {
        Object obj;
        int iB2 = K.a.b(this.f25692e, j12, this.f25690c);
        if (iB2 < 0 || (obj = this.f25691d[iB2]) == C20210f0.f25703a) {
            return -1L;
        }
        return obj;
    }

    public final int i(long j12) {
        if (this.f25689b) {
            int i12 = this.f25692e;
            long[] jArr = this.f25690c;
            Object[] objArr = this.f25691d;
            int i13 = 0;
            for (int i14 = 0; i14 < i12; i14++) {
                Object obj = objArr[i14];
                if (obj != C20210f0.f25703a) {
                    if (i14 != i13) {
                        jArr[i13] = jArr[i14];
                        objArr[i13] = obj;
                        objArr[i14] = null;
                    }
                    i13++;
                }
            }
            this.f25689b = false;
            this.f25692e = i13;
        }
        return K.a.b(this.f25692e, j12, this.f25690c);
    }

    public final boolean j() {
        return n() == 0;
    }

    public final long k(int i12) {
        if (!(i12 >= 0 && i12 < this.f25692e)) {
            K.f.a("Expected index to be within 0..size()-1, but was " + i12);
            throw null;
        }
        if (this.f25689b) {
            int i13 = this.f25692e;
            long[] jArr = this.f25690c;
            Object[] objArr = this.f25691d;
            int i14 = 0;
            for (int i15 = 0; i15 < i13; i15++) {
                Object obj = objArr[i15];
                if (obj != C20210f0.f25703a) {
                    if (i15 != i14) {
                        jArr[i14] = jArr[i15];
                        objArr[i14] = obj;
                        objArr[i15] = null;
                    }
                    i14++;
                }
            }
            this.f25689b = false;
            this.f25692e = i14;
        }
        return this.f25690c[i12];
    }

    public final void l(long j12, E e12) {
        int iB2 = K.a.b(this.f25692e, j12, this.f25690c);
        if (iB2 >= 0) {
            this.f25691d[iB2] = e12;
            return;
        }
        int i12 = ~iB2;
        int i13 = this.f25692e;
        Object obj = C20210f0.f25703a;
        if (i12 < i13) {
            Object[] objArr = this.f25691d;
            if (objArr[i12] == obj) {
                this.f25690c[i12] = j12;
                objArr[i12] = e12;
                return;
            }
        }
        if (this.f25689b) {
            long[] jArr = this.f25690c;
            if (i13 >= jArr.length) {
                Object[] objArr2 = this.f25691d;
                int i14 = 0;
                for (int i15 = 0; i15 < i13; i15++) {
                    Object obj2 = objArr2[i15];
                    if (obj2 != obj) {
                        if (i15 != i14) {
                            jArr[i14] = jArr[i15];
                            objArr2[i14] = obj2;
                            objArr2[i15] = null;
                        }
                        i14++;
                    }
                }
                this.f25689b = false;
                this.f25692e = i14;
                i12 = ~K.a.b(i14, j12, this.f25690c);
            }
        }
        int i16 = this.f25692e;
        if (i16 >= this.f25690c.length) {
            int i17 = (i16 + 1) * 8;
            int i18 = 4;
            while (true) {
                if (i18 >= 32) {
                    break;
                }
                int i19 = (1 << i18) - 12;
                if (i17 <= i19) {
                    i17 = i19;
                    break;
                }
                i18++;
            }
            int i22 = i17 / 8;
            this.f25690c = Arrays.copyOf(this.f25690c, i22);
            this.f25691d = Arrays.copyOf(this.f25691d, i22);
        }
        int i23 = this.f25692e;
        if (i23 - i12 != 0) {
            long[] jArr2 = this.f25690c;
            int i24 = i12 + 1;
            C42756l.n(jArr2, jArr2, i24, i12, i23);
            Object[] objArr3 = this.f25691d;
            C42756l.o(objArr3, i24, objArr3, i12, this.f25692e);
        }
        this.f25690c[i12] = j12;
        this.f25691d[i12] = e12;
        this.f25692e++;
    }

    public final void m(long j12) {
        int iB2 = K.a.b(this.f25692e, j12, this.f25690c);
        if (iB2 >= 0) {
            Object[] objArr = this.f25691d;
            Object obj = objArr[iB2];
            Object obj2 = C20210f0.f25703a;
            if (obj != obj2) {
                objArr[iB2] = obj2;
                this.f25689b = true;
            }
        }
    }

    public final int n() {
        if (this.f25689b) {
            int i12 = this.f25692e;
            long[] jArr = this.f25690c;
            Object[] objArr = this.f25691d;
            int i13 = 0;
            for (int i14 = 0; i14 < i12; i14++) {
                Object obj = objArr[i14];
                if (obj != C20210f0.f25703a) {
                    if (i14 != i13) {
                        jArr[i13] = jArr[i14];
                        objArr[i13] = obj;
                        objArr[i14] = null;
                    }
                    i13++;
                }
            }
            this.f25689b = false;
            this.f25692e = i13;
        }
        return this.f25692e;
    }

    public final E o(int i12) {
        if (!(i12 >= 0 && i12 < this.f25692e)) {
            K.f.a("Expected index to be within 0..size()-1, but was " + i12);
            throw null;
        }
        if (this.f25689b) {
            int i13 = this.f25692e;
            long[] jArr = this.f25690c;
            Object[] objArr = this.f25691d;
            int i14 = 0;
            for (int i15 = 0; i15 < i13; i15++) {
                Object obj = objArr[i15];
                if (obj != C20210f0.f25703a) {
                    if (i15 != i14) {
                        jArr[i14] = jArr[i15];
                        objArr[i14] = obj;
                        objArr[i15] = null;
                    }
                    i14++;
                }
            }
            this.f25689b = false;
            this.f25692e = i14;
        }
        return (E) this.f25691d[i12];
    }

    @Y61.k
    public final String toString() {
        if (n() <= 0) {
            return "{}";
        }
        StringBuilder sb2 = new StringBuilder(this.f25692e * 28);
        sb2.append('{');
        int i12 = this.f25692e;
        for (int i13 = 0; i13 < i12; i13++) {
            if (i13 > 0) {
                sb2.append(", ");
            }
            sb2.append(k(i13));
            sb2.append('=');
            E eO2 = o(i13);
            if (eO2 != sb2) {
                sb2.append(eO2);
            } else {
                sb2.append("(this Map)");
            }
        }
        sb2.append('}');
        return sb2.toString();
    }

    @X41.j
    public C20204c0(int i12) {
        if (i12 == 0) {
            this.f25690c = K.a.f9205b;
            this.f25691d = K.a.f9206c;
            return;
        }
        int i13 = i12 * 8;
        int i14 = 4;
        while (true) {
            if (i14 >= 32) {
                break;
            }
            int i15 = (1 << i14) - 12;
            if (i13 <= i15) {
                i13 = i15;
                break;
            }
            i14++;
        }
        int i16 = i13 / 8;
        this.f25690c = new long[i16];
        this.f25691d = new Object[i16];
    }

    public /* synthetic */ C20204c0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 10 : i12);
    }
}
