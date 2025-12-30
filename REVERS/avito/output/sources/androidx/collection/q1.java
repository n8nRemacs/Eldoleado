package androidx.collection;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: SparseArrayCompat.jvm.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001a\n\u0000\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002¨\u0006\u0003"}, d2 = {"Landroidx/collection/q1;", "E", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public class q1<E> implements Cloneable {

    /* renamed from: b, reason: collision with root package name */
    @X41.f
    public /* synthetic */ boolean f25800b;

    /* renamed from: c, reason: collision with root package name */
    @X41.f
    public /* synthetic */ int[] f25801c;

    /* renamed from: d, reason: collision with root package name */
    @X41.f
    public /* synthetic */ Object[] f25802d;

    /* renamed from: e, reason: collision with root package name */
    @X41.f
    public /* synthetic */ int f25803e;

    @X41.j
    public q1() {
        this(0, 1, null);
    }

    public final void a(int i12, E e12) {
        int i13 = this.f25803e;
        if (i13 != 0 && i12 <= this.f25801c[i13 - 1]) {
            i(i12, e12);
            return;
        }
        if (this.f25800b && i13 >= this.f25801c.length) {
            r1.a(this);
        }
        int i14 = this.f25803e;
        if (i14 >= this.f25801c.length) {
            int i15 = (i14 + 1) * 4;
            int i16 = 4;
            while (true) {
                if (i16 >= 32) {
                    break;
                }
                int i17 = (1 << i16) - 12;
                if (i15 <= i17) {
                    i15 = i17;
                    break;
                }
                i16++;
            }
            int i18 = i15 / 4;
            this.f25801c = Arrays.copyOf(this.f25801c, i18);
            this.f25802d = Arrays.copyOf(this.f25802d, i18);
        }
        this.f25801c[i14] = i12;
        this.f25802d[i14] = e12;
        this.f25803e = i14 + 1;
    }

    @Y61.k
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public final q1<E> clone() {
        q1<E> q1Var = (q1) super.clone();
        q1Var.f25801c = (int[]) this.f25801c.clone();
        q1Var.f25802d = (Object[]) this.f25802d.clone();
        return q1Var;
    }

    @Y61.l
    public final E f(int i12) {
        E e12;
        int iA2 = K.a.a(this.f25803e, i12, this.f25801c);
        if (iA2 < 0 || (e12 = (E) this.f25802d[iA2]) == r1.f25805a) {
            return null;
        }
        return e12;
    }

    public final int g(int i12) {
        if (this.f25800b) {
            r1.a(this);
        }
        return this.f25801c[i12];
    }

    public final void i(int i12, E e12) {
        int iA2 = K.a.a(this.f25803e, i12, this.f25801c);
        if (iA2 >= 0) {
            this.f25802d[iA2] = e12;
            return;
        }
        int i13 = ~iA2;
        int i14 = this.f25803e;
        if (i13 < i14) {
            Object[] objArr = this.f25802d;
            if (objArr[i13] == r1.f25805a) {
                this.f25801c[i13] = i12;
                objArr[i13] = e12;
                return;
            }
        }
        if (this.f25800b && i14 >= this.f25801c.length) {
            r1.a(this);
            i13 = ~K.a.a(this.f25803e, i12, this.f25801c);
        }
        int i15 = this.f25803e;
        if (i15 >= this.f25801c.length) {
            int i16 = (i15 + 1) * 4;
            int i17 = 4;
            while (true) {
                if (i17 >= 32) {
                    break;
                }
                int i18 = (1 << i17) - 12;
                if (i16 <= i18) {
                    i16 = i18;
                    break;
                }
                i17++;
            }
            int i19 = i16 / 4;
            this.f25801c = Arrays.copyOf(this.f25801c, i19);
            this.f25802d = Arrays.copyOf(this.f25802d, i19);
        }
        int i22 = this.f25803e;
        if (i22 - i13 != 0) {
            int[] iArr = this.f25801c;
            int i23 = i13 + 1;
            C42756l.k(i23, i13, i22, iArr, iArr);
            Object[] objArr2 = this.f25802d;
            C42756l.o(objArr2, i23, objArr2, i13, this.f25803e);
        }
        this.f25801c[i13] = i12;
        this.f25802d[i13] = e12;
        this.f25803e++;
    }

    public final int j() {
        if (this.f25800b) {
            r1.a(this);
        }
        return this.f25803e;
    }

    public final E k(int i12) {
        if (this.f25800b) {
            r1.a(this);
        }
        Object[] objArr = this.f25802d;
        if (i12 < objArr.length) {
            return (E) objArr[i12];
        }
        int i13 = C20213h.f25720a;
        throw new ArrayIndexOutOfBoundsException();
    }

    @Y61.k
    public final String toString() {
        if (j() <= 0) {
            return "{}";
        }
        StringBuilder sb2 = new StringBuilder(this.f25803e * 28);
        sb2.append('{');
        int i12 = this.f25803e;
        for (int i13 = 0; i13 < i12; i13++) {
            if (i13 > 0) {
                sb2.append(", ");
            }
            sb2.append(g(i13));
            sb2.append('=');
            E eK2 = k(i13);
            if (eK2 != this) {
                sb2.append(eK2);
            } else {
                sb2.append("(this Map)");
            }
        }
        sb2.append('}');
        return sb2.toString();
    }

    public q1(int i12, int i13, C42822w c42822w) {
        i12 = (i13 & 1) != 0 ? 10 : i12;
        if (i12 == 0) {
            this.f25801c = K.a.f9204a;
            this.f25802d = K.a.f9206c;
            return;
        }
        int i14 = i12 * 4;
        int i15 = 4;
        while (true) {
            if (i15 >= 32) {
                break;
            }
            int i16 = (1 << i15) - 12;
            if (i14 <= i16) {
                i14 = i16;
                break;
            }
            i15++;
        }
        int i17 = i14 / 4;
        this.f25801c = new int[i17];
        this.f25802d = new Object[i17];
    }
}
