package androidx.collection;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: SimpleArrayMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003¨\u0006\u0004"}, d2 = {"Landroidx/collection/o1;", "K", "V", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public class o1<K, V> {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public int[] f25788b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public Object[] f25789c;

    /* renamed from: d, reason: collision with root package name */
    public int f25790d;

    @X41.j
    public o1() {
        this(0, 1, null);
    }

    @X41.i
    public final int a(V v12) {
        int i12 = this.f25790d * 2;
        Object[] objArr = this.f25789c;
        if (v12 == null) {
            for (int i13 = 1; i13 < i12; i13 += 2) {
                if (objArr[i13] == null) {
                    return i13 >> 1;
                }
            }
            return -1;
        }
        for (int i14 = 1; i14 < i12; i14 += 2) {
            if (v12.equals(objArr[i14])) {
                return i14 >> 1;
            }
        }
        return -1;
    }

    public final void b(int i12) {
        int i13 = this.f25790d;
        int[] iArr = this.f25788b;
        if (iArr.length < i12) {
            this.f25788b = Arrays.copyOf(iArr, i12);
            this.f25789c = Arrays.copyOf(this.f25789c, i12 * 2);
        }
        if (this.f25790d != i13) {
            throw new ConcurrentModificationException();
        }
    }

    public final int c(int i12, Object obj) {
        int i13 = this.f25790d;
        if (i13 == 0) {
            return -1;
        }
        int iA2 = K.a.a(i13, i12, this.f25788b);
        if (iA2 < 0 || kotlin.jvm.internal.L.f(obj, this.f25789c[iA2 << 1])) {
            return iA2;
        }
        int i14 = iA2 + 1;
        while (i14 < i13 && this.f25788b[i14] == i12) {
            if (kotlin.jvm.internal.L.f(obj, this.f25789c[i14 << 1])) {
                return i14;
            }
            i14++;
        }
        for (int i15 = iA2 - 1; i15 >= 0 && this.f25788b[i15] == i12; i15--) {
            if (kotlin.jvm.internal.L.f(obj, this.f25789c[i15 << 1])) {
                return i15;
            }
        }
        return ~i14;
    }

    public void clear() {
        if (this.f25790d > 0) {
            this.f25788b = K.a.f9204a;
            this.f25789c = K.a.f9206c;
            this.f25790d = 0;
        }
        if (this.f25790d > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(K k12) {
        return d(k12) >= 0;
    }

    public boolean containsValue(V v12) {
        return a(v12) >= 0;
    }

    public final int d(K k12) {
        return k12 == null ? e() : c(k12.hashCode(), k12);
    }

    public final int e() {
        int i12 = this.f25790d;
        if (i12 == 0) {
            return -1;
        }
        int iA2 = K.a.a(i12, 0, this.f25788b);
        if (iA2 < 0 || this.f25789c[iA2 << 1] == null) {
            return iA2;
        }
        int i13 = iA2 + 1;
        while (i13 < i12 && this.f25788b[i13] == 0) {
            if (this.f25789c[i13 << 1] == null) {
                return i13;
            }
            i13++;
        }
        for (int i14 = iA2 - 1; i14 >= 0 && this.f25788b[i14] == 0; i14--) {
            if (this.f25789c[i14 << 1] == null) {
                return i14;
            }
        }
        return ~i13;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        try {
            if (obj instanceof o1) {
                int i12 = this.f25790d;
                if (i12 != ((o1) obj).f25790d) {
                    return false;
                }
                o1 o1Var = (o1) obj;
                for (int i13 = 0; i13 < i12; i13++) {
                    K kF2 = f(i13);
                    V vK2 = k(i13);
                    Object obj2 = o1Var.get(kF2);
                    if (vK2 == null) {
                        if (obj2 != null || !o1Var.containsKey(kF2)) {
                            return false;
                        }
                    } else if (!vK2.equals(obj2)) {
                        return false;
                    }
                }
                return true;
            }
            if (!(obj instanceof Map) || this.f25790d != ((Map) obj).size()) {
                return false;
            }
            int i14 = this.f25790d;
            for (int i15 = 0; i15 < i14; i15++) {
                K kF3 = f(i15);
                V vK3 = k(i15);
                Object obj3 = ((Map) obj).get(kF3);
                if (vK3 == null) {
                    if (obj3 != null || !((Map) obj).containsKey(kF3)) {
                        return false;
                    }
                } else if (!vK3.equals(obj3)) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException | NullPointerException unused) {
        }
        return false;
    }

    public final K f(int i12) {
        boolean z12 = false;
        if (i12 >= 0 && i12 < this.f25790d) {
            z12 = true;
        }
        if (z12) {
            return (K) this.f25789c[i12 << 1];
        }
        K.f.a("Expected index to be within 0..size()-1, but was " + i12);
        throw null;
    }

    public void g(@Y61.k C20199a c20199a) {
        int i12 = c20199a.f25790d;
        b(this.f25790d + i12);
        if (this.f25790d != 0) {
            for (int i13 = 0; i13 < i12; i13++) {
                put(c20199a.f(i13), c20199a.k(i13));
            }
        } else if (i12 > 0) {
            C42756l.k(0, 0, i12, c20199a.f25788b, this.f25788b);
            C42756l.o(c20199a.f25789c, 0, this.f25789c, 0, i12 << 1);
            this.f25790d = i12;
        }
    }

    @Y61.l
    public V get(K k12) {
        int iD2 = d(k12);
        if (iD2 >= 0) {
            return (V) this.f25789c[(iD2 << 1) + 1];
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final V getOrDefault(@Y61.l Object obj, V v12) {
        int iD2 = d(obj);
        return iD2 >= 0 ? (V) this.f25789c[(iD2 << 1) + 1] : v12;
    }

    public V h(int i12) {
        if (!(i12 >= 0 && i12 < this.f25790d)) {
            K.f.a("Expected index to be within 0..size()-1, but was " + i12);
            throw null;
        }
        Object[] objArr = this.f25789c;
        int i13 = i12 << 1;
        V v12 = (V) objArr[i13 + 1];
        int i14 = this.f25790d;
        if (i14 <= 1) {
            clear();
        } else {
            int i15 = i14 - 1;
            int[] iArr = this.f25788b;
            if (iArr.length <= 8 || i14 >= iArr.length / 3) {
                if (i12 < i15) {
                    int i16 = i12 + 1;
                    C42756l.k(i12, i16, i14, iArr, iArr);
                    Object[] objArr2 = this.f25789c;
                    C42756l.o(objArr2, i13, objArr2, i16 << 1, i14 << 1);
                }
                Object[] objArr3 = this.f25789c;
                int i17 = i15 << 1;
                objArr3[i17] = null;
                objArr3[i17 + 1] = null;
            } else {
                int i18 = i14 > 8 ? i14 + (i14 >> 1) : 8;
                this.f25788b = Arrays.copyOf(iArr, i18);
                this.f25789c = Arrays.copyOf(this.f25789c, i18 << 1);
                if (i14 != this.f25790d) {
                    throw new ConcurrentModificationException();
                }
                if (i12 > 0) {
                    C42756l.k(0, 0, i12, iArr, this.f25788b);
                    C42756l.o(objArr, 0, this.f25789c, 0, i13);
                }
                if (i12 < i15) {
                    int i19 = i12 + 1;
                    C42756l.k(i12, i19, i14, iArr, this.f25788b);
                    C42756l.o(objArr, i13, this.f25789c, i19 << 1, i14 << 1);
                }
            }
            if (i14 != this.f25790d) {
                throw new ConcurrentModificationException();
            }
            this.f25790d = i15;
        }
        return v12;
    }

    public int hashCode() {
        int[] iArr = this.f25788b;
        Object[] objArr = this.f25789c;
        int i12 = this.f25790d;
        int i13 = 1;
        int i14 = 0;
        int iHashCode = 0;
        while (i14 < i12) {
            Object obj = objArr[i13];
            iHashCode += (obj != null ? obj.hashCode() : 0) ^ iArr[i14];
            i14++;
            i13 += 2;
        }
        return iHashCode;
    }

    public final boolean isEmpty() {
        return this.f25790d <= 0;
    }

    public V j(int i12, V v12) {
        boolean z12 = false;
        if (i12 >= 0 && i12 < this.f25790d) {
            z12 = true;
        }
        if (!z12) {
            K.f.a("Expected index to be within 0..size()-1, but was " + i12);
            throw null;
        }
        int i13 = (i12 << 1) + 1;
        Object[] objArr = this.f25789c;
        V v13 = (V) objArr[i13];
        objArr[i13] = v12;
        return v13;
    }

    public final V k(int i12) {
        boolean z12 = false;
        if (i12 >= 0 && i12 < this.f25790d) {
            z12 = true;
        }
        if (z12) {
            return (V) this.f25789c[(i12 << 1) + 1];
        }
        K.f.a("Expected index to be within 0..size()-1, but was " + i12);
        throw null;
    }

    @Y61.l
    public V put(K k12, V v12) {
        int i12 = this.f25790d;
        int iHashCode = k12 != null ? k12.hashCode() : 0;
        int iC2 = k12 != null ? c(iHashCode, k12) : e();
        if (iC2 >= 0) {
            int i13 = (iC2 << 1) + 1;
            Object[] objArr = this.f25789c;
            V v13 = (V) objArr[i13];
            objArr[i13] = v12;
            return v13;
        }
        int i14 = ~iC2;
        int[] iArr = this.f25788b;
        if (i12 >= iArr.length) {
            int i15 = 8;
            if (i12 >= 8) {
                i15 = (i12 >> 1) + i12;
            } else if (i12 < 4) {
                i15 = 4;
            }
            this.f25788b = Arrays.copyOf(iArr, i15);
            this.f25789c = Arrays.copyOf(this.f25789c, i15 << 1);
            if (i12 != this.f25790d) {
                throw new ConcurrentModificationException();
            }
        }
        if (i14 < i12) {
            int[] iArr2 = this.f25788b;
            int i16 = i14 + 1;
            C42756l.k(i16, i14, i12, iArr2, iArr2);
            Object[] objArr2 = this.f25789c;
            C42756l.o(objArr2, i16 << 1, objArr2, i14 << 1, this.f25790d << 1);
        }
        int i17 = this.f25790d;
        if (i12 == i17) {
            int[] iArr3 = this.f25788b;
            if (i14 < iArr3.length) {
                iArr3[i14] = iHashCode;
                Object[] objArr3 = this.f25789c;
                int i18 = i14 << 1;
                objArr3[i18] = k12;
                objArr3[i18 + 1] = v12;
                this.f25790d = i17 + 1;
                return null;
            }
        }
        throw new ConcurrentModificationException();
    }

    @Y61.l
    public final V putIfAbsent(K k12, V v12) {
        V v13 = get(k12);
        return v13 == null ? put(k12, v12) : v13;
    }

    @Y61.l
    public V remove(K k12) {
        int iD2 = d(k12);
        if (iD2 >= 0) {
            return h(iD2);
        }
        return null;
    }

    @Y61.l
    public final V replace(K k12, V v12) {
        int iD2 = d(k12);
        if (iD2 >= 0) {
            return j(iD2, v12);
        }
        return null;
    }

    /* renamed from: size, reason: from getter */
    public final int getF25790d() {
        return this.f25790d;
    }

    @Y61.k
    public final String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb2 = new StringBuilder(this.f25790d * 28);
        sb2.append('{');
        int i12 = this.f25790d;
        for (int i13 = 0; i13 < i12; i13++) {
            if (i13 > 0) {
                sb2.append(", ");
            }
            K kF2 = f(i13);
            if (kF2 != sb2) {
                sb2.append(kF2);
            } else {
                sb2.append("(this Map)");
            }
            sb2.append('=');
            V vK2 = k(i13);
            if (vK2 != sb2) {
                sb2.append(vK2);
            } else {
                sb2.append("(this Map)");
            }
        }
        sb2.append('}');
        return sb2.toString();
    }

    @X41.j
    public o1(int i12) {
        this.f25788b = i12 == 0 ? K.a.f9204a : new int[i12];
        this.f25789c = i12 == 0 ? K.a.f9206c : new Object[i12 << 1];
    }

    public final boolean remove(K k12, V v12) {
        int iD2 = d(k12);
        if (iD2 < 0 || !kotlin.jvm.internal.L.f(v12, k(iD2))) {
            return false;
        }
        h(iD2);
        return true;
    }

    public final boolean replace(K k12, V v12, V v13) {
        int iD2 = d(k12);
        if (iD2 < 0 || !kotlin.jvm.internal.L.f(v12, k(iD2))) {
            return false;
        }
        j(iD2, v13);
        return true;
    }

    public /* synthetic */ o1(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 0 : i12);
    }
}
