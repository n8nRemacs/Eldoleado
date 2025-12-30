package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: VectorizedAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/core/a2;", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/P1;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class a2<V extends AbstractC20330x> implements P1<V> {

    /* renamed from: a, reason: collision with root package name */
    public final int f26199a;

    /* renamed from: b, reason: collision with root package name */
    public final int f26200b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final N f26201c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final R1<V> f26202d;

    public a2() {
        this(0, 0, null, 7, null);
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V c(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return (V) this.f26202d.c(j12, v12, v13, v14);
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V e(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return (V) this.f26202d.e(j12, v12, v13, v14);
    }

    @Override // androidx.compose.animation.core.P1
    /* renamed from: f, reason: from getter */
    public final int getF26185a() {
        return this.f26200b;
    }

    @Override // androidx.compose.animation.core.P1
    /* renamed from: g, reason: from getter */
    public final int getF26199a() {
        return this.f26199a;
    }

    public a2(int i12, int i13, N n12, int i14, C42822w c42822w) {
        this((i14 & 1) != 0 ? 300 : i12, (i14 & 2) != 0 ? 0 : i13, (i14 & 4) != 0 ? Q.f26121a : n12);
    }

    public a2(int i12, int i13, @Y61.k N n12) {
        this.f26199a = i12;
        this.f26200b = i13;
        this.f26201c = n12;
        this.f26202d = new R1<>(new C20266b0(i12, i13, n12));
    }
}
