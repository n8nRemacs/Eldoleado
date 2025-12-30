package androidx.compose.animation;

import androidx.compose.runtime.InterfaceC22192v1;
import androidx.compose.ui.node.C22421l;
import androidx.compose.ui.node.InterfaceC22438u;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: RenderInTransitionOverlayNodeElement.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003:\u0001\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/y1;", "Landroidx/compose/ui/v$d;", "Landroidx/compose/ui/node/u;", "Landroidx/compose/ui/modifier/k;", "a", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.y1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20403y1 extends v.d implements InterfaceC22438u, androidx.compose.ui.modifier.k {

    /* renamed from: p, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22192v1 f26624p = androidx.compose.runtime.Q1.a(0.0f);

    /* compiled from: RenderInTransitionOverlayNodeElement.kt */
    @kotlin.jvm.internal.s0
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/y1$a;", "Landroidx/compose/animation/t1;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.y1$a */
    public final class a implements InterfaceC20388t1 {
        @Override // androidx.compose.animation.InterfaceC20388t1
        public final void a(@Y61.k androidx.compose.ui.graphics.drawscope.d dVar) {
            throw null;
        }

        @Override // androidx.compose.animation.InterfaceC20388t1
        public final float getZIndex() {
            throw null;
        }
    }

    @Override // androidx.compose.ui.v.d
    public final void c2() {
        C22421l.h(this).getGraphicsContext().a();
        throw null;
    }

    @Override // androidx.compose.ui.node.InterfaceC22438u
    public final void t(@Y61.k androidx.compose.ui.node.P p12) {
        throw new IllegalArgumentException("Error: layer never initialized");
    }

    @Override // androidx.compose.ui.v.d
    public final void d2() {
    }
}
