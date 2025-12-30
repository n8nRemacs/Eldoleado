package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.InterfaceC42830m;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: VectorizedAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B!\b\u0017\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tB)\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\b\u0010\f¨\u0006\r"}, d2 = {"Landroidx/compose/animation/core/T1;", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/K1;", "Landroidx/compose/animation/core/P1;", "animation", "Landroidx/compose/animation/core/RepeatMode;", "repeatMode", "<init>", "(Landroidx/compose/animation/core/P1;Landroidx/compose/animation/core/RepeatMode;)V", "Landroidx/compose/animation/core/U0;", "initialStartOffset", "(Landroidx/compose/animation/core/P1;Landroidx/compose/animation/core/RepeatMode;JLkotlin/jvm/internal/w;)V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class T1<V extends AbstractC20330x> implements K1<V> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final P1<V> f26146a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final RepeatMode f26147b;

    /* renamed from: c, reason: collision with root package name */
    public final long f26148c;

    /* renamed from: d, reason: collision with root package name */
    public final long f26149d;

    public T1() {
        throw null;
    }

    public T1(P1 p12, RepeatMode repeatMode, long j12, C42822w c42822w) {
        this.f26146a = p12;
        this.f26147b = repeatMode;
        this.f26148c = (p12.getF26199a() + p12.getF26200b()) * 1000000;
        this.f26149d = j12 * 1000000;
    }

    @Override // androidx.compose.animation.core.K1
    public final boolean a() {
        return true;
    }

    @Override // androidx.compose.animation.core.K1
    public final long b(@Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return Long.MAX_VALUE;
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V c(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return this.f26146a.c(h(j12), v12, v13, i(j12, v12, v14, v13));
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V e(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return this.f26146a.e(h(j12), v12, v13, i(j12, v12, v14, v13));
    }

    public final long h(long j12) {
        long j13 = this.f26149d;
        if (j12 + j13 <= 0) {
            return 0L;
        }
        long j14 = j12 + j13;
        long j15 = this.f26148c;
        long j16 = j14 / j15;
        return (this.f26147b == RepeatMode.f26133b || j16 % ((long) 2) == 0) ? j14 - (j16 * j15) : ((j16 + 1) * j15) - j14;
    }

    public final V i(long j12, V v12, V v13, V v14) {
        long j13 = this.f26149d;
        long j14 = j12 + j13;
        long j15 = this.f26148c;
        return j14 > j15 ? this.f26146a.c(j15 - j13, v12, v14, v13) : v13;
    }

    public /* synthetic */ T1(P1 p12, RepeatMode repeatMode, long j12, int i12, C42822w c42822w) {
        this(p12, (i12 & 2) != 0 ? RepeatMode.f26133b : repeatMode, (i12 & 4) != 0 ? U0.a(0) : j12, (C42822w) null);
    }

    public /* synthetic */ T1(P1 p12, RepeatMode repeatMode, int i12, C42822w c42822w) {
        this(p12, (i12 & 2) != 0 ? RepeatMode.f26133b : repeatMode);
    }

    @InterfaceC42830m
    public /* synthetic */ T1(P1 p12, RepeatMode repeatMode) {
        this(p12, repeatMode, U0.a(0), (C42822w) null);
    }
}
