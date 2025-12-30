package androidx.collection;

import java.util.Map;
import kotlin.Metadata;

/* compiled from: ScatterMap.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010&\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/collection/m0;", "K", "V", "", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.collection.m0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20224m0<K, V> implements Map.Entry<K, V>, Z41.a {

    /* renamed from: b, reason: collision with root package name */
    public final K f25762b;

    /* renamed from: c, reason: collision with root package name */
    public final V f25763c;

    public C20224m0(K k12, V v12) {
        this.f25762b = k12;
        this.f25763c = v12;
    }

    @Override // java.util.Map.Entry
    public final K getKey() {
        return this.f25762b;
    }

    @Override // java.util.Map.Entry
    public final V getValue() {
        return this.f25763c;
    }

    @Override // java.util.Map.Entry
    public final V setValue(V v12) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
