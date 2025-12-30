package androidx.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.C42745f0;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.C42822w;

/* compiled from: ArraySet.jvm.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0010#\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003:\u0001\u0004¨\u0006\u0005"}, d2 = {"Landroidx/collection/c;", "E", "", "", "a", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20203c<E> implements Collection<E>, Set<E>, Z41.b, Z41.h {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public int[] f25685b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public Object[] f25686c;

    /* renamed from: d, reason: collision with root package name */
    public int f25687d;

    /* compiled from: ArraySet.jvm.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001¨\u0006\u0002"}, d2 = {"Landroidx/collection/c$a;", "Landroidx/collection/z;", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.collection.c$a */
    public final class a extends AbstractC20248z<E> {
        public a() {
            super(C20203c.this.f25687d);
        }

        @Override // androidx.collection.AbstractC20248z
        public final E a(int i12) {
            return (E) C20203c.this.f25686c[i12];
        }

        @Override // androidx.collection.AbstractC20248z
        public final void b(int i12) {
            C20203c.this.a(i12);
        }
    }

    @X41.j
    public C20203c() {
        this(0, 1, null);
    }

    public final E a(int i12) {
        int i13 = this.f25687d;
        Object[] objArr = this.f25686c;
        E e12 = (E) objArr[i12];
        if (i13 <= 1) {
            clear();
        } else {
            int i14 = i13 - 1;
            int[] iArr = this.f25685b;
            if (iArr.length <= 8 || i13 >= iArr.length / 3) {
                if (i12 < i14) {
                    int i15 = i12 + 1;
                    C42756l.k(i12, i15, i13, iArr, iArr);
                    Object[] objArr2 = this.f25686c;
                    C42756l.o(objArr2, i12, objArr2, i15, i13);
                }
                this.f25686c[i14] = null;
            } else {
                int i16 = i13 > 8 ? i13 + (i13 >> 1) : 8;
                int[] iArr2 = new int[i16];
                this.f25685b = iArr2;
                this.f25686c = new Object[i16];
                if (i12 > 0) {
                    C42756l.p(0, i12, 6, iArr, iArr2);
                    C42756l.s(objArr, 0, this.f25686c, i12, 6);
                }
                if (i12 < i14) {
                    int i17 = i12 + 1;
                    C42756l.k(i12, i17, i13, iArr, this.f25685b);
                    C42756l.o(objArr, i12, this.f25686c, i17, i13);
                }
            }
            if (i13 != this.f25687d) {
                throw new ConcurrentModificationException();
            }
            this.f25687d = i14;
        }
        return e12;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean add(E e12) {
        int i12;
        int iA2;
        int i13 = this.f25687d;
        if (e12 == null) {
            iA2 = C20207e.a(this, null, 0);
            i12 = 0;
        } else {
            int iHashCode = e12.hashCode();
            i12 = iHashCode;
            iA2 = C20207e.a(this, e12, iHashCode);
        }
        if (iA2 >= 0) {
            return false;
        }
        int i14 = ~iA2;
        int[] iArr = this.f25685b;
        if (i13 >= iArr.length) {
            int i15 = 8;
            if (i13 >= 8) {
                i15 = (i13 >> 1) + i13;
            } else if (i13 < 4) {
                i15 = 4;
            }
            Object[] objArr = this.f25686c;
            int[] iArr2 = new int[i15];
            this.f25685b = iArr2;
            this.f25686c = new Object[i15];
            if (i13 != this.f25687d) {
                throw new ConcurrentModificationException();
            }
            if (iArr2.length != 0) {
                C42756l.p(0, iArr.length, 6, iArr, iArr2);
                C42756l.s(objArr, 0, this.f25686c, objArr.length, 6);
            }
        }
        if (i14 < i13) {
            int[] iArr3 = this.f25685b;
            int i16 = i14 + 1;
            C42756l.k(i16, i14, i13, iArr3, iArr3);
            Object[] objArr2 = this.f25686c;
            C42756l.o(objArr2, i16, objArr2, i14, i13);
        }
        int i17 = this.f25687d;
        if (i13 == i17) {
            int[] iArr4 = this.f25685b;
            if (i14 < iArr4.length) {
                iArr4[i14] = i12;
                this.f25686c[i14] = e12;
                this.f25687d = i17 + 1;
                return true;
            }
        }
        throw new ConcurrentModificationException();
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean addAll(@Y61.k Collection<? extends E> collection) {
        int size = collection.size() + this.f25687d;
        int i12 = this.f25687d;
        int[] iArr = this.f25685b;
        boolean zAdd = false;
        if (iArr.length < size) {
            Object[] objArr = this.f25686c;
            int[] iArr2 = new int[size];
            this.f25685b = iArr2;
            this.f25686c = new Object[size];
            if (i12 > 0) {
                C42756l.p(0, i12, 6, iArr, iArr2);
                C42756l.s(objArr, 0, this.f25686c, this.f25687d, 6);
            }
        }
        if (this.f25687d != i12) {
            throw new ConcurrentModificationException();
        }
        Iterator<? extends E> it = collection.iterator();
        while (it.hasNext()) {
            zAdd |= add(it.next());
        }
        return zAdd;
    }

    @Override // java.util.Collection, java.util.Set
    public final void clear() {
        if (this.f25687d != 0) {
            this.f25685b = K.a.f9204a;
            this.f25686c = K.a.f9206c;
            this.f25687d = 0;
        }
        if (this.f25687d != 0) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return (obj == null ? C20207e.a(this, null, 0) : C20207e.a(this, obj, obj.hashCode())) >= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean containsAll(@Y61.k Collection<? extends Object> collection) {
        Iterator<? extends Object> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof Set) && this.f25687d == ((Set) obj).size()) {
            try {
                int i12 = this.f25687d;
                for (int i13 = 0; i13 < i12; i13++) {
                    if (((Set) obj).contains(this.f25686c[i13])) {
                    }
                }
                return true;
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public final int hashCode() {
        int[] iArr = this.f25685b;
        int i12 = this.f25687d;
        int i13 = 0;
        for (int i14 = 0; i14 < i12; i14++) {
            i13 += iArr[i14];
        }
        return i13;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.f25687d <= 0;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    @Y61.k
    public final Iterator<E> iterator() {
        return new a();
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        int iA2 = obj == null ? C20207e.a(this, null, 0) : C20207e.a(this, obj, obj.hashCode());
        if (iA2 < 0) {
            return false;
        }
        a(iA2);
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean removeAll(@Y61.k Collection<? extends Object> collection) {
        Iterator<? extends Object> it = collection.iterator();
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= remove(it.next());
        }
        return zRemove;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean retainAll(@Y61.k Collection<? extends Object> collection) {
        boolean z12 = false;
        for (int i12 = this.f25687d - 1; -1 < i12; i12--) {
            if (!C42745f0.r(collection, this.f25686c[i12])) {
                a(i12);
                z12 = true;
            }
        }
        return z12;
    }

    @Override // java.util.Collection, java.util.Set
    public final int size() {
        return this.f25687d;
    }

    @Override // java.util.Collection, java.util.Set
    @Y61.k
    public final Object[] toArray() {
        return C42756l.w(0, this.f25687d, this.f25686c);
    }

    @Y61.k
    public final String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb2 = new StringBuilder(this.f25687d * 14);
        sb2.append('{');
        int i12 = this.f25687d;
        for (int i13 = 0; i13 < i12; i13++) {
            if (i13 > 0) {
                sb2.append(", ");
            }
            Object obj = this.f25686c[i13];
            if (obj != this) {
                sb2.append(obj);
            } else {
                sb2.append("(this Set)");
            }
        }
        sb2.append('}');
        return sb2.toString();
    }

    @X41.j
    public C20203c(int i12) {
        this.f25685b = K.a.f9204a;
        this.f25686c = K.a.f9206c;
        if (i12 > 0) {
            this.f25685b = new int[i12];
            this.f25686c = new Object[i12];
        }
    }

    @Override // java.util.Collection, java.util.Set
    @Y61.k
    public final <T> T[] toArray(@Y61.k T[] tArr) {
        int i12 = this.f25687d;
        if (tArr.length < i12) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i12));
        } else if (tArr.length > i12) {
            tArr[i12] = null;
        }
        C42756l.o(this.f25686c, 0, tArr, 0, this.f25687d);
        return tArr;
    }

    public /* synthetic */ C20203c(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 0 : i12);
    }
}
