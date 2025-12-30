package androidx.compose.animation.core;

import kotlin.InterfaceC42830m;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimationSpec.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B)\b\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\t\u0010\nB1\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b¢\u0006\u0004\b\t\u0010\r¨\u0006\u000e"}, d2 = {"Landroidx/compose/animation/core/B0;", "T", "Landroidx/compose/animation/core/V;", "", "iterations", "Landroidx/compose/animation/core/M;", "animation", "Landroidx/compose/animation/core/RepeatMode;", "repeatMode", "<init>", "(ILandroidx/compose/animation/core/M;Landroidx/compose/animation/core/RepeatMode;)V", "Landroidx/compose/animation/core/U0;", "initialStartOffset", "(ILandroidx/compose/animation/core/M;Landroidx/compose/animation/core/RepeatMode;JLkotlin/jvm/internal/w;)V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class B0<T> implements V<T> {

    /* renamed from: a, reason: collision with root package name */
    public final int f26021a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final M<T> f26022b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final RepeatMode f26023c;

    /* renamed from: d, reason: collision with root package name */
    public final long f26024d;

    public B0() {
        throw null;
    }

    public B0(int i12, M m12, RepeatMode repeatMode, long j12, C42822w c42822w) {
        this.f26021a = i12;
        this.f26022b = m12;
        this.f26023c = repeatMode;
        this.f26024d = j12;
    }

    @Override // androidx.compose.animation.core.InterfaceC20307p
    public final K1 a(H1 h12) {
        return new X1(this.f26021a, this.f26022b.a(h12), this.f26023c, this.f26024d, (C42822w) null);
    }

    public final boolean equals(@Y61.l Object obj) {
        if (!(obj instanceof B0)) {
            return false;
        }
        B0 b02 = (B0) obj;
        return b02.f26021a == this.f26021a && kotlin.jvm.internal.L.f(b02.f26022b, this.f26022b) && b02.f26023c == this.f26023c && b02.f26024d == this.f26024d;
    }

    public final int hashCode() {
        return Long.hashCode(this.f26024d) + ((this.f26023c.hashCode() + ((this.f26022b.hashCode() + (this.f26021a * 31)) * 31)) * 31);
    }

    public /* synthetic */ B0(int i12, M m12, RepeatMode repeatMode, long j12, int i13, C42822w c42822w) {
        this(i12, m12, (i13 & 4) != 0 ? RepeatMode.f26133b : repeatMode, (i13 & 8) != 0 ? U0.a(0) : j12, (C42822w) null);
    }

    public /* synthetic */ B0(int i12, M m12, RepeatMode repeatMode, int i13, C42822w c42822w) {
        this(i12, m12, (i13 & 4) != 0 ? RepeatMode.f26133b : repeatMode);
    }

    @InterfaceC42830m
    public /* synthetic */ B0(int i12, M m12, RepeatMode repeatMode) {
        this(i12, m12, repeatMode, U0.a(0), (C42822w) null);
    }
}
