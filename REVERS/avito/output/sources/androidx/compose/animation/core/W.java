package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: FloatAnimationSpec.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0003À\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/W;", "Landroidx/compose/animation/core/p;", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public interface W extends InterfaceC20307p<Float> {

    /* compiled from: FloatAnimationSpec.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class a {
    }

    @Override // androidx.compose.animation.core.InterfaceC20307p
    /* bridge */ /* synthetic */ default K1 a(H1 h12) {
        return b();
    }

    @Y61.k
    default R1 b() {
        return new R1(this);
    }

    float c(long j12, float f12, float f13, float f14);

    long d(float f12, float f13, float f14);

    default float e(float f12, float f13, float f14) {
        return c(d(f12, f13, f14), f12, f13, f14);
    }

    float f(long j12, float f12, float f13, float f14);
}
