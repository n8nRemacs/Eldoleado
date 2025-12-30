package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: AnimationSpec.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/S0;", "T", "Landroidx/compose/animation/core/p;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class S0<T> implements InterfaceC20307p<T> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final V f26137a;

    /* renamed from: b, reason: collision with root package name */
    public final long f26138b;

    public S0(@Y61.k V v12, long j12) {
        this.f26137a = v12;
        this.f26138b = j12;
    }

    @Override // androidx.compose.animation.core.InterfaceC20307p
    @Y61.k
    public final <V extends AbstractC20330x> K1<V> a(@Y61.k H1<T, V> h12) {
        return new T0(this.f26137a.a(h12), this.f26138b);
    }

    public final boolean equals(@Y61.l Object obj) {
        if (!(obj instanceof S0)) {
            return false;
        }
        S0 s02 = (S0) obj;
        return s02.f26138b == this.f26138b && kotlin.jvm.internal.L.f(s02.f26137a, this.f26137a);
    }

    public final int hashCode() {
        return Long.hashCode(this.f26138b) + (this.f26137a.hashCode() * 31);
    }
}
