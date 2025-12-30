package androidx.compose.animation;

import androidx.compose.ui.node.AbstractC22430p0;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: AnimateBoundsModifier.kt */
@androidx.compose.runtime.internal.P
@InterfaceC20374o1
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0081\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/BoundsAnimationElement;", "Landroidx/compose/ui/node/p0;", "Landroidx/compose/animation/n0;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class BoundsAnimationElement extends AbstractC22430p0<C20370n0> {
    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final v.d a() {
        return new C20370n0();
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final void b(v.d dVar) {
        ((C20370n0) dVar).getClass();
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BoundsAnimationElement)) {
            return false;
        }
        ((BoundsAnimationElement) obj).getClass();
        return kotlin.jvm.internal.L.f(null, null) && kotlin.jvm.internal.L.f(null, null) && kotlin.jvm.internal.L.f(null, null);
    }

    public final int hashCode() {
        throw null;
    }

    @Y61.k
    public final String toString() {
        return "BoundsAnimationElement(lookaheadScope=null, boundsTransform=null, resolveMeasureConstraints=null, animateMotionFrameOfReference=false)";
    }
}
