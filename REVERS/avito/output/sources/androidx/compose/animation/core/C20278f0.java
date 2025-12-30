package androidx.compose.animation.core;

import kotlin.InterfaceC42830m;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimationSpec.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B!\b\u0017\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bB)\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\t¢\u0006\u0004\b\u0007\u0010\u000b¨\u0006\f"}, d2 = {"Landroidx/compose/animation/core/f0;", "T", "Landroidx/compose/animation/core/p;", "Landroidx/compose/animation/core/M;", "animation", "Landroidx/compose/animation/core/RepeatMode;", "repeatMode", "<init>", "(Landroidx/compose/animation/core/M;Landroidx/compose/animation/core/RepeatMode;)V", "Landroidx/compose/animation/core/U0;", "initialStartOffset", "(Landroidx/compose/animation/core/M;Landroidx/compose/animation/core/RepeatMode;JLkotlin/jvm/internal/w;)V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.f0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20278f0<T> implements InterfaceC20307p<T> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final M<T> f26257a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final RepeatMode f26258b;

    /* renamed from: c, reason: collision with root package name */
    public final long f26259c;

    public C20278f0() {
        throw null;
    }

    public C20278f0(M m12, RepeatMode repeatMode, long j12, C42822w c42822w) {
        this.f26257a = m12;
        this.f26258b = repeatMode;
        this.f26259c = j12;
    }

    @Override // androidx.compose.animation.core.InterfaceC20307p
    @Y61.k
    public final <V extends AbstractC20330x> K1<V> a(@Y61.k H1<T, V> h12) {
        return new T1(this.f26257a.a((H1) h12), this.f26258b, this.f26259c, (C42822w) null);
    }

    public final boolean equals(@Y61.l Object obj) {
        if (!(obj instanceof C20278f0)) {
            return false;
        }
        C20278f0 c20278f0 = (C20278f0) obj;
        return kotlin.jvm.internal.L.f(c20278f0.f26257a, this.f26257a) && c20278f0.f26258b == this.f26258b && c20278f0.f26259c == this.f26259c;
    }

    public final int hashCode() {
        return Long.hashCode(this.f26259c) + ((this.f26258b.hashCode() + (this.f26257a.hashCode() * 31)) * 31);
    }

    public /* synthetic */ C20278f0(M m12, RepeatMode repeatMode, long j12, int i12, C42822w c42822w) {
        this(m12, (i12 & 2) != 0 ? RepeatMode.f26133b : repeatMode, (i12 & 4) != 0 ? U0.a(0) : j12, (C42822w) null);
    }

    public /* synthetic */ C20278f0(M m12, RepeatMode repeatMode, int i12, C42822w c42822w) {
        this(m12, (i12 & 2) != 0 ? RepeatMode.f26133b : repeatMode);
    }

    @InterfaceC42830m
    public /* synthetic */ C20278f0(M m12, RepeatMode repeatMode) {
        this(m12, repeatMode, U0.a(0), (C42822w) null);
    }
}
