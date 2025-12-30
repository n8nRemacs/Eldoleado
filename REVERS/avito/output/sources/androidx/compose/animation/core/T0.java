package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;

/* compiled from: AnimationSpec.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/core/T0;", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/K1;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class T0<V extends AbstractC20330x> implements K1<V> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final K1<V> f26144a;

    /* renamed from: b, reason: collision with root package name */
    public final long f26145b;

    public T0(@Y61.k K1<V> k12, long j12) {
        this.f26144a = k12;
        this.f26145b = j12;
    }

    @Override // androidx.compose.animation.core.K1
    public final boolean a() {
        return this.f26144a.a();
    }

    @Override // androidx.compose.animation.core.K1
    public final long b(@Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return this.f26144a.b(v12, v13, v14) + this.f26145b;
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V c(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        long j13 = this.f26145b;
        return j12 < j13 ? v14 : (V) this.f26144a.c(j12 - j13, v12, v13, v14);
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V e(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        long j13 = this.f26145b;
        return j12 < j13 ? v12 : (V) this.f26144a.e(j12 - j13, v12, v13, v14);
    }

    public final boolean equals(@Y61.l Object obj) {
        if (!(obj instanceof T0)) {
            return false;
        }
        T0 t02 = (T0) obj;
        return t02.f26145b == this.f26145b && kotlin.jvm.internal.L.f(t02.f26144a, this.f26144a);
    }

    public final int hashCode() {
        return Long.hashCode(this.f26145b) + (this.f26144a.hashCode() * 31);
    }
}
