package androidx.compose.animation;

import androidx.compose.runtime.C22040c3;
import androidx.compose.ui.node.AbstractC22430p0;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: RenderInTransitionOverlayNodeElement.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0080\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/RenderInTransitionOverlayNodeElement;", "Landroidx/compose/ui/node/p0;", "Landroidx/compose/animation/y1;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class RenderInTransitionOverlayNodeElement extends AbstractC22430p0<C20403y1> {
    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final v.d a() {
        return new C20403y1();
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final void b(v.d dVar) {
        C20403y1 c20403y1 = (C20403y1) dVar;
        c20403y1.getClass();
        ((C22040c3) c20403y1.f26624p).S6(0.0f);
    }

    public final boolean equals(@Y61.l Object obj) {
        if (!(obj instanceof RenderInTransitionOverlayNodeElement)) {
            return false;
        }
        RenderInTransitionOverlayNodeElement renderInTransitionOverlayNodeElement = (RenderInTransitionOverlayNodeElement) obj;
        renderInTransitionOverlayNodeElement.getClass();
        if (!kotlin.jvm.internal.L.f(null, null)) {
            return false;
        }
        renderInTransitionOverlayNodeElement.getClass();
        renderInTransitionOverlayNodeElement.getClass();
        renderInTransitionOverlayNodeElement.getClass();
        return true;
    }

    public final int hashCode() {
        throw null;
    }

    @Y61.k
    public final String toString() {
        return "RenderInTransitionOverlayNodeElement(sharedTransitionScope=null, renderInOverlay=null, zIndexInOverlay=0.0, clipInOverlay=null)";
    }
}
