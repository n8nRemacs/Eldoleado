package androidx.compose.animation.core;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimationSpec.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/M0;", "T", "Landroidx/compose/animation/core/M;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class M0<T> implements M<T> {

    /* renamed from: a, reason: collision with root package name */
    public final int f26107a;

    public M0() {
        this(0, 1, null);
    }

    public final boolean equals(@Y61.l Object obj) {
        return (obj instanceof M0) && ((M0) obj).f26107a == this.f26107a;
    }

    /* renamed from: hashCode, reason: from getter */
    public final int getF26107a() {
        return this.f26107a;
    }

    public M0(int i12) {
        this.f26107a = i12;
    }

    @Override // androidx.compose.animation.core.InterfaceC20307p
    @Y61.k
    public final <V extends AbstractC20330x> P1<V> a(@Y61.k H1<T, V> h12) {
        return new Y1(this.f26107a);
    }

    public /* synthetic */ M0(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 0 : i12);
    }
}
