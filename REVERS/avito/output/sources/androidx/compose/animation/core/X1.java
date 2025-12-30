package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.InterfaceC42830m;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: VectorizedAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B)\b\u0017\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\b¢\u0006\u0004\b\n\u0010\u000bB1\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\r\u001a\u00020\f¢\u0006\u0004\b\n\u0010\u000e¨\u0006\u000f"}, d2 = {"Landroidx/compose/animation/core/X1;", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/Q1;", "", "iterations", "Landroidx/compose/animation/core/P1;", "animation", "Landroidx/compose/animation/core/RepeatMode;", "repeatMode", "<init>", "(ILandroidx/compose/animation/core/P1;Landroidx/compose/animation/core/RepeatMode;)V", "Landroidx/compose/animation/core/U0;", "initialStartOffset", "(ILandroidx/compose/animation/core/P1;Landroidx/compose/animation/core/RepeatMode;JLkotlin/jvm/internal/w;)V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class X1<V extends AbstractC20330x> implements Q1<V> {

    /* renamed from: a, reason: collision with root package name */
    public final int f26180a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final P1<V> f26181b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final RepeatMode f26182c;

    /* renamed from: d, reason: collision with root package name */
    public final long f26183d;

    /* renamed from: e, reason: collision with root package name */
    public final long f26184e;

    public X1() {
        throw null;
    }

    public X1(int i12, P1 p12, RepeatMode repeatMode, long j12, C42822w c42822w) {
        this.f26180a = i12;
        this.f26181b = p12;
        this.f26182c = repeatMode;
        if (i12 < 1) {
            throw new IllegalArgumentException("Iterations count can't be less than 1");
        }
        this.f26183d = (p12.getF26199a() + p12.getF26200b()) * 1000000;
        this.f26184e = j12 * 1000000;
    }

    @Override // androidx.compose.animation.core.K1
    public final long b(@Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return (this.f26180a * this.f26183d) - this.f26184e;
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V c(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return this.f26181b.c(h(j12), v12, v13, i(j12, v12, v14, v13));
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V e(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return this.f26181b.e(h(j12), v12, v13, i(j12, v12, v14, v13));
    }

    public final long h(long j12) {
        long j13 = this.f26184e;
        if (j12 + j13 <= 0) {
            return 0L;
        }
        long j14 = j12 + j13;
        long j15 = this.f26183d;
        long jMin = Math.min(j14 / j15, this.f26180a - 1);
        return (this.f26182c == RepeatMode.f26133b || jMin % ((long) 2) == 0) ? j14 - (jMin * j15) : ((jMin + 1) * j15) - j14;
    }

    public final V i(long j12, V v12, V v13, V v14) {
        long j13 = this.f26184e;
        long j14 = j12 + j13;
        long j15 = this.f26183d;
        return j14 > j15 ? (V) c(j15 - j13, v12, v13, v14) : v13;
    }

    public /* synthetic */ X1(int i12, P1 p12, RepeatMode repeatMode, long j12, int i13, C42822w c42822w) {
        this(i12, p12, (i13 & 4) != 0 ? RepeatMode.f26133b : repeatMode, (i13 & 8) != 0 ? U0.a(0) : j12, (C42822w) null);
    }

    public /* synthetic */ X1(int i12, P1 p12, RepeatMode repeatMode, int i13, C42822w c42822w) {
        this(i12, p12, (i13 & 4) != 0 ? RepeatMode.f26133b : repeatMode);
    }

    @InterfaceC42830m
    public /* synthetic */ X1(int i12, P1 p12, RepeatMode repeatMode) {
        this(i12, p12, repeatMode, U0.a(0), (C42822w) null);
    }
}
