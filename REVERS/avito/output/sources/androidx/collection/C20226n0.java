package androidx.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import kotlin.Metadata;

/* compiled from: ScatterMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\b\u0012\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/collection/n0;", "K", "V", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.n0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C20226n0<K, V> implements Map<K, V>, Z41.a {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final h1<K, V> f25782b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.l
    public C20221l<K, V> f25783c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.l
    public N<K, V> f25784d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.l
    public v1<K, V> f25785e;

    public C20226n0(@Y61.k h1<K, V> h1Var) {
        this.f25782b = h1Var;
    }

    @Y61.k
    public Set<Map.Entry<K, V>> a() {
        C20221l<K, V> c20221l = this.f25783c;
        if (c20221l != null) {
            return c20221l;
        }
        C20221l<K, V> c20221l2 = new C20221l<>(this.f25782b);
        this.f25783c = c20221l2;
        return c20221l2;
    }

    @Y61.k
    public Set<K> b() {
        N<K, V> n12 = this.f25784d;
        if (n12 != null) {
            return n12;
        }
        N<K, V> n13 = new N<>(this.f25782b);
        this.f25784d = n13;
        return n13;
    }

    @Y61.k
    public Collection<V> c() {
        v1<K, V> v1Var = this.f25785e;
        if (v1Var != null) {
            return v1Var;
        }
        v1<K, V> v1Var2 = new v1<>(this.f25782b);
        this.f25785e = v1Var2;
        return v1Var2;
    }

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final V compute(K k12, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final V computeIfAbsent(K k12, Function<? super K, ? extends V> function) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final V computeIfPresent(K k12, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.f25782b.c(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.f25782b.d(obj);
    }

    @Override // java.util.Map
    public final /* bridge */ Set<Map.Entry<K, V>> entrySet() {
        return a();
    }

    @Override // java.util.Map
    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return kotlin.jvm.internal.L.f(this.f25782b, ((C20226n0) obj).f25782b);
    }

    @Override // java.util.Map
    @Y61.l
    public final V get(Object obj) {
        return this.f25782b.e(obj);
    }

    @Override // java.util.Map
    public final int hashCode() {
        return this.f25782b.hashCode();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.f25782b.f();
    }

    @Override // java.util.Map
    public final /* bridge */ Set<K> keySet() {
        return b();
    }

    @Override // java.util.Map
    public final V merge(K k12, V v12, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public V put(K k12, V v12) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final V putIfAbsent(K k12, V v12) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final V replace(K k12, V v12) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final int size() {
        return this.f25782b.f25725e;
    }

    @Y61.k
    public final String toString() {
        return this.f25782b.toString();
    }

    @Override // java.util.Map
    public final /* bridge */ Collection<V> values() {
        return c();
    }

    @Override // java.util.Map
    public final boolean remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final boolean replace(K k12, V v12, V v13) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
