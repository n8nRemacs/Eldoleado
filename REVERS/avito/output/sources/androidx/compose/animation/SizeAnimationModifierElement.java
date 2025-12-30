package androidx.compose.animation;

import androidx.compose.ui.node.AbstractC22430p0;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: AnimationModifier.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/SizeAnimationModifierElement;", "Landroidx/compose/ui/node/p0;", "Landroidx/compose/animation/m2;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final /* data */ class SizeAnimationModifierElement extends AbstractC22430p0<m2> {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.animation.core.R0 f25962b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.ui.i f25963c;

    public SizeAnimationModifierElement(@Y61.k androidx.compose.animation.core.R0 r02, @Y61.k androidx.compose.ui.i iVar) {
        this.f25962b = r02;
        this.f25963c = iVar;
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final v.d a() {
        return new m2(this.f25962b, this.f25963c, null);
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final void b(v.d dVar) {
        m2 m2Var = (m2) dVar;
        m2Var.f26512p = this.f25962b;
        m2Var.f26514r = null;
        m2Var.f26513q = this.f25963c;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SizeAnimationModifierElement)) {
            return false;
        }
        SizeAnimationModifierElement sizeAnimationModifierElement = (SizeAnimationModifierElement) obj;
        return kotlin.jvm.internal.L.f(this.f25962b, sizeAnimationModifierElement.f25962b) && kotlin.jvm.internal.L.f(this.f25963c, sizeAnimationModifierElement.f25963c) && kotlin.jvm.internal.L.f(null, null);
    }

    public final int hashCode() {
        return (this.f25963c.hashCode() + (this.f25962b.hashCode() * 31)) * 31;
    }

    @Y61.k
    public final String toString() {
        return "SizeAnimationModifierElement(animationSpec=" + this.f25962b + ", alignment=" + this.f25963c + ", finishedListener=null)";
    }
}
