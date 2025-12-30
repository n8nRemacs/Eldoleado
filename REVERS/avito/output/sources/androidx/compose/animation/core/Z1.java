package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: VectorizedAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/core/Z1;", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/Q1;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class Z1<V extends AbstractC20330x> implements Q1<V> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ R1<V> f26189a;

    public /* synthetic */ Z1(float f12, float f13, AbstractC20330x abstractC20330x, int i12, C42822w c42822w) {
        this((i12 & 1) != 0 ? 1.0f : f12, (i12 & 2) != 0 ? 1500.0f : f13, (i12 & 4) != 0 ? null : abstractC20330x);
    }

    @Override // androidx.compose.animation.core.Q1, androidx.compose.animation.core.K1
    public final boolean a() {
        this.f26189a.getClass();
        return false;
    }

    @Override // androidx.compose.animation.core.K1
    public final long b(@Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return this.f26189a.b(v12, v13, v14);
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V c(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return (V) this.f26189a.c(j12, v12, v13, v14);
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V d(@Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return (V) this.f26189a.d(v12, v13, v14);
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V e(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        return (V) this.f26189a.e(j12, v12, v13, v14);
    }

    public Z1(float f12, float f13, @Y61.l V v12) {
        int[] iArr = N1.f26112a;
        this.f26189a = new R1<>(v12 != null ? new L1(f12, f13, v12) : new M1(f12, f13));
    }
}
