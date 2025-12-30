package androidx.collection;

import com.huawei.hms.framework.common.ContainerUtils;
import java.lang.reflect.Array;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* compiled from: ArrayMap.java */
/* renamed from: androidx.collection.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C20199a<K, V> extends o1<K, V> implements Map<K, V> {

    /* renamed from: e, reason: collision with root package name */
    @j.P
    public C20199a<K, V>.C1545a f25662e;

    /* renamed from: f, reason: collision with root package name */
    @j.P
    public C20199a<K, V>.c f25663f;

    /* renamed from: g, reason: collision with root package name */
    @j.P
    public C20199a<K, V>.e f25664g;

    /* compiled from: ArrayMap.java */
    /* renamed from: androidx.collection.a$a, reason: collision with other inner class name */
    public final class C1545a extends AbstractSet<Map.Entry<K, V>> {
        public C1545a() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        @j.N
        public final Iterator<Map.Entry<K, V>> iterator() {
            return new d();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return C20199a.this.f25790d;
        }
    }

    /* compiled from: ArrayMap.java */
    /* renamed from: androidx.collection.a$b */
    public final class b extends AbstractC20248z<K> {
        public b() {
            super(C20199a.this.f25790d);
        }

        @Override // androidx.collection.AbstractC20248z
        public final K a(int i12) {
            return C20199a.this.f(i12);
        }

        @Override // androidx.collection.AbstractC20248z
        public final void b(int i12) {
            C20199a.this.h(i12);
        }
    }

    /* compiled from: ArrayMap.java */
    /* renamed from: androidx.collection.a$d */
    public final class d implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {

        /* renamed from: b, reason: collision with root package name */
        public int f25668b;

        /* renamed from: c, reason: collision with root package name */
        public int f25669c = -1;

        /* renamed from: d, reason: collision with root package name */
        public boolean f25670d;

        public d() {
            this.f25668b = C20199a.this.f25790d - 1;
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            if (!this.f25670d) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            int i12 = this.f25669c;
            C20199a c20199a = C20199a.this;
            return kotlin.jvm.internal.L.f(key, c20199a.f(i12)) && kotlin.jvm.internal.L.f(entry.getValue(), c20199a.k(this.f25669c));
        }

        @Override // java.util.Map.Entry
        public final K getKey() {
            if (this.f25670d) {
                return C20199a.this.f(this.f25669c);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public final V getValue() {
            if (this.f25670d) {
                return C20199a.this.k(this.f25669c);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.f25669c < this.f25668b;
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            if (!this.f25670d) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            int i12 = this.f25669c;
            C20199a c20199a = C20199a.this;
            K kF2 = c20199a.f(i12);
            V vK2 = c20199a.k(this.f25669c);
            return (kF2 == null ? 0 : kF2.hashCode()) ^ (vK2 != null ? vK2.hashCode() : 0);
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.f25669c++;
            this.f25670d = true;
            return this;
        }

        @Override // java.util.Iterator
        public final void remove() {
            if (!this.f25670d) {
                throw new IllegalStateException();
            }
            C20199a.this.h(this.f25669c);
            this.f25669c--;
            this.f25668b--;
            this.f25670d = false;
        }

        @Override // java.util.Map.Entry
        public final V setValue(V v12) {
            if (this.f25670d) {
                return C20199a.this.j(this.f25669c, v12);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final String toString() {
            return getKey() + ContainerUtils.KEY_VALUE_DELIMITER + getValue();
        }
    }

    /* compiled from: ArrayMap.java */
    /* renamed from: androidx.collection.a$f */
    public final class f extends AbstractC20248z<V> {
        public f() {
            super(C20199a.this.f25790d);
        }

        @Override // androidx.collection.AbstractC20248z
        public final V a(int i12) {
            return C20199a.this.k(i12);
        }

        @Override // androidx.collection.AbstractC20248z
        public final void b(int i12) {
            C20199a.this.h(i12);
        }
    }

    public C20199a() {
    }

    @Override // java.util.Map
    @j.N
    public final Set<Map.Entry<K, V>> entrySet() {
        C20199a<K, V>.C1545a c1545a = this.f25662e;
        if (c1545a != null) {
            return c1545a;
        }
        C20199a<K, V>.C1545a c1545a2 = new C1545a();
        this.f25662e = c1545a2;
        return c1545a2;
    }

    @Override // java.util.Map
    @j.N
    public final Set<K> keySet() {
        C20199a<K, V>.c cVar = this.f25663f;
        if (cVar != null) {
            return cVar;
        }
        C20199a<K, V>.c cVar2 = new c();
        this.f25663f = cVar2;
        return cVar2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean l(@j.N Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!super.containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean m(@j.N Collection<?> collection) {
        int i12 = this.f25790d;
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            super.remove(it.next());
        }
        return i12 != this.f25790d;
    }

    public final boolean n(@j.N Collection<?> collection) {
        int i12 = this.f25790d;
        for (int i13 = i12 - 1; i13 >= 0; i13--) {
            if (!collection.contains(f(i13))) {
                h(i13);
            }
        }
        return i12 != this.f25790d;
    }

    @Override // java.util.Map
    public final void putAll(@j.N Map<? extends K, ? extends V> map) {
        b(map.size() + this.f25790d);
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    @j.N
    public final Collection<V> values() {
        C20199a<K, V>.e eVar = this.f25664g;
        if (eVar != null) {
            return eVar;
        }
        C20199a<K, V>.e eVar2 = new e();
        this.f25664g = eVar2;
        return eVar2;
    }

    public C20199a(@j.P C20199a c20199a) {
        super(0, 1, null);
        if (c20199a != null) {
            g(c20199a);
        }
    }

    /* compiled from: ArrayMap.java */
    /* renamed from: androidx.collection.a$c */
    public final class c implements Set<K> {
        public c() {
        }

        @Override // java.util.Set, java.util.Collection
        public final boolean add(K k12) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public final boolean addAll(@j.N Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public final void clear() {
            C20199a.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public final boolean contains(Object obj) {
            return C20199a.this.containsKey(obj);
        }

        @Override // java.util.Set, java.util.Collection
        public final boolean containsAll(@j.N Collection<?> collection) {
            return C20199a.this.l(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Set) {
                Set set = (Set) obj;
                C20199a c20199a = C20199a.this;
                try {
                    if (c20199a.f25790d == set.size()) {
                        if (c20199a.l(set)) {
                            return true;
                        }
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return false;
        }

        @Override // java.util.Set, java.util.Collection
        public final int hashCode() {
            C20199a c20199a = C20199a.this;
            int iHashCode = 0;
            for (int i12 = c20199a.f25790d - 1; i12 >= 0; i12--) {
                K kF2 = c20199a.f(i12);
                iHashCode += kF2 == null ? 0 : kF2.hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public final boolean isEmpty() {
            return C20199a.this.isEmpty();
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        @j.N
        public final Iterator<K> iterator() {
            return new b();
        }

        @Override // java.util.Set, java.util.Collection
        public final boolean remove(Object obj) {
            C20199a c20199a = C20199a.this;
            int iD2 = c20199a.d(obj);
            if (iD2 < 0) {
                return false;
            }
            c20199a.h(iD2);
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public final boolean removeAll(@j.N Collection<?> collection) {
            return C20199a.this.m(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public final boolean retainAll(@j.N Collection<?> collection) {
            return C20199a.this.n(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public final int size() {
            return C20199a.this.f25790d;
        }

        @Override // java.util.Set, java.util.Collection
        @j.N
        public final Object[] toArray() {
            C20199a c20199a = C20199a.this;
            int i12 = c20199a.f25790d;
            Object[] objArr = new Object[i12];
            for (int i13 = 0; i13 < i12; i13++) {
                objArr[i13] = c20199a.f(i13);
            }
            return objArr;
        }

        @Override // java.util.Set, java.util.Collection
        @j.N
        public final <T> T[] toArray(@j.N T[] tArr) {
            C20199a c20199a = C20199a.this;
            int i12 = c20199a.f25790d;
            if (tArr.length < i12) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i12));
            }
            for (int i13 = 0; i13 < i12; i13++) {
                tArr[i13] = c20199a.f(i13);
            }
            if (tArr.length > i12) {
                tArr[i12] = null;
            }
            return tArr;
        }
    }

    /* compiled from: ArrayMap.java */
    /* renamed from: androidx.collection.a$e */
    public final class e implements Collection<V> {
        public e() {
        }

        @Override // java.util.Collection
        public final boolean add(V v12) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public final boolean addAll(@j.N Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public final void clear() {
            C20199a.this.clear();
        }

        @Override // java.util.Collection
        public final boolean contains(Object obj) {
            return C20199a.this.a(obj) >= 0;
        }

        @Override // java.util.Collection
        public final boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public final boolean isEmpty() {
            return C20199a.this.isEmpty();
        }

        @Override // java.util.Collection, java.lang.Iterable
        @j.N
        public final Iterator<V> iterator() {
            return new f();
        }

        @Override // java.util.Collection
        public final boolean remove(Object obj) {
            C20199a c20199a = C20199a.this;
            int iA2 = c20199a.a(obj);
            if (iA2 < 0) {
                return false;
            }
            c20199a.h(iA2);
            return true;
        }

        @Override // java.util.Collection
        public final boolean removeAll(@j.N Collection<?> collection) {
            C20199a c20199a = C20199a.this;
            int i12 = c20199a.f25790d;
            int i13 = 0;
            boolean z12 = false;
            while (i13 < i12) {
                if (collection.contains(c20199a.k(i13))) {
                    c20199a.h(i13);
                    i13--;
                    i12--;
                    z12 = true;
                }
                i13++;
            }
            return z12;
        }

        @Override // java.util.Collection
        public final boolean retainAll(@j.N Collection<?> collection) {
            C20199a c20199a = C20199a.this;
            int i12 = c20199a.f25790d;
            int i13 = 0;
            boolean z12 = false;
            while (i13 < i12) {
                if (!collection.contains(c20199a.k(i13))) {
                    c20199a.h(i13);
                    i13--;
                    i12--;
                    z12 = true;
                }
                i13++;
            }
            return z12;
        }

        @Override // java.util.Collection
        public final int size() {
            return C20199a.this.f25790d;
        }

        @Override // java.util.Collection
        @j.N
        public final Object[] toArray() {
            C20199a c20199a = C20199a.this;
            int i12 = c20199a.f25790d;
            Object[] objArr = new Object[i12];
            for (int i13 = 0; i13 < i12; i13++) {
                objArr[i13] = c20199a.k(i13);
            }
            return objArr;
        }

        @Override // java.util.Collection
        @j.N
        public final <T> T[] toArray(@j.N T[] tArr) {
            C20199a c20199a = C20199a.this;
            int i12 = c20199a.f25790d;
            if (tArr.length < i12) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i12));
            }
            for (int i13 = 0; i13 < i12; i13++) {
                tArr[i13] = c20199a.k(i13);
            }
            if (tArr.length > i12) {
                tArr[i12] = null;
            }
            return tArr;
        }
    }
}
