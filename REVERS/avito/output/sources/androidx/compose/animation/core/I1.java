package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;

/* compiled from: VectorConverters.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0003*\u00020\u00022\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/I1;", "T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/H1;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class I1<T, V extends AbstractC20330x> implements H1<T, V> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final kotlin.jvm.internal.N f26073a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final kotlin.jvm.internal.N f26074b;

    /* JADX WARN: Multi-variable type inference failed */
    public I1(@Y61.k Y41.l<? super T, ? extends V> lVar, @Y61.k Y41.l<? super V, ? extends T> lVar2) {
        this.f26073a = (kotlin.jvm.internal.N) lVar;
        this.f26074b = (kotlin.jvm.internal.N) lVar2;
    }

    @Override // androidx.compose.animation.core.H1
    @Y61.k
    public final Y41.l<T, V> a() {
        return (Y41.l<T, V>) this.f26073a;
    }

    @Override // androidx.compose.animation.core.H1
    @Y61.k
    public final Y41.l<V, T> b() {
        return (Y41.l<V, T>) this.f26074b;
    }
}
