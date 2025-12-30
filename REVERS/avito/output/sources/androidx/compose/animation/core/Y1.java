package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: VectorizedAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/core/Y1;", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/P1;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class Y1<V extends AbstractC20330x> implements P1<V> {

    /* renamed from: a, reason: collision with root package name */
    public final int f26185a;

    public Y1() {
        this(0, 1, null);
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V e(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return j12 < ((long) this.f26185a) * 1000000 ? v12 : v13;
    }

    @Override // androidx.compose.animation.core.P1
    /* renamed from: f, reason: from getter */
    public final int getF26185a() {
        return this.f26185a;
    }

    @Override // androidx.compose.animation.core.P1
    public final int g() {
        return 0;
    }

    public Y1(int i12) {
        this.f26185a = i12;
    }

    public /* synthetic */ Y1(int i12, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? 0 : i12);
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V c(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return v14;
    }
}
