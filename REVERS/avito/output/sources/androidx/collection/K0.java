package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;

/* compiled from: ScatterMap.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00032\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004¨\u0006\u0005"}, d2 = {"Landroidx/collection/K0;", "K", "V", "Landroidx/collection/n0;", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class K0<K, V> extends C20226n0<K, V> implements Map<K, V>, Z41.g {
    @Override // androidx.collection.C20226n0
    @Y61.k
    public final Set<Map.Entry<K, V>> a() {
        return null;
    }

    @Override // androidx.collection.C20226n0
    @Y61.k
    public final Set<K> b() {
        return null;
    }

    @Override // androidx.collection.C20226n0
    @Y61.k
    public final Collection<V> c() {
        return null;
    }

    @Override // androidx.collection.C20226n0, java.util.Map
    public final void clear() {
        throw null;
    }

    @Override // androidx.collection.C20226n0, java.util.Map
    @Y61.l
    public final V put(K k12, V v12) {
        throw null;
    }

    @Override // androidx.collection.C20226n0, java.util.Map
    public final void putAll(@Y61.k Map<? extends K, ? extends V> map) {
        Iterator<Map.Entry<? extends K, ? extends V>> it = map.entrySet().iterator();
        if (it.hasNext()) {
            Map.Entry<? extends K, ? extends V> next = it.next();
            next.getKey();
            next.getValue();
            throw null;
        }
    }

    @Override // androidx.collection.C20226n0, java.util.Map
    @Y61.l
    public final V remove(Object obj) {
        throw null;
    }
}
