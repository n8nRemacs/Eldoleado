package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.animation.core.C20321u;
import androidx.compose.ui.node.AbstractC22430p0;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: EnterExitTransition.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/EnterExitTransitionElement;", "Landroidx/compose/ui/node/p0;", "Landroidx/compose/animation/i1;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final /* data */ class EnterExitTransitionElement extends AbstractC22430p0<C20356i1> {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final C20288i1<EnterExitState> f25877b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.l
    public final C20288i1<EnterExitState>.a<androidx.compose.ui.unit.u, C20321u> f25878c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.l
    public final C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> f25879d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.l
    public final C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> f25880e;

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public final AbstractC20359j1 f25881f;

    /* renamed from: g, reason: collision with root package name */
    @Y61.k
    public final AbstractC20365l1 f25882g;

    /* renamed from: h, reason: collision with root package name */
    @Y61.k
    public final Y41.a<Boolean> f25883h;

    /* renamed from: i, reason: collision with root package name */
    @Y61.k
    public final InterfaceC20385s1 f25884i;

    public EnterExitTransitionElement(@Y61.k C20288i1<EnterExitState> c20288i1, @Y61.l C20288i1<EnterExitState>.a<androidx.compose.ui.unit.u, C20321u> aVar, @Y61.l C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> aVar2, @Y61.l C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> aVar3, @Y61.k AbstractC20359j1 abstractC20359j1, @Y61.k AbstractC20365l1 abstractC20365l1, @Y61.k Y41.a<Boolean> aVar4, @Y61.k InterfaceC20385s1 interfaceC20385s1) {
        this.f25877b = c20288i1;
        this.f25878c = aVar;
        this.f25879d = aVar2;
        this.f25880e = aVar3;
        this.f25881f = abstractC20359j1;
        this.f25882g = abstractC20365l1;
        this.f25883h = aVar4;
        this.f25884i = interfaceC20385s1;
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final v.d a() {
        AbstractC20359j1 abstractC20359j1 = this.f25881f;
        AbstractC20365l1 abstractC20365l1 = this.f25882g;
        return new C20356i1(this.f25877b, this.f25878c, this.f25879d, this.f25880e, abstractC20359j1, abstractC20365l1, this.f25883h, this.f25884i);
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final void b(v.d dVar) {
        C20356i1 c20356i1 = (C20356i1) dVar;
        c20356i1.f26471p = this.f25877b;
        c20356i1.f26472q = this.f25878c;
        c20356i1.f26473r = this.f25879d;
        c20356i1.f26474s = this.f25880e;
        c20356i1.f26475t = this.f25881f;
        c20356i1.f26476u = this.f25882g;
        c20356i1.f26477v = this.f25883h;
        c20356i1.f26478w = this.f25884i;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EnterExitTransitionElement)) {
            return false;
        }
        EnterExitTransitionElement enterExitTransitionElement = (EnterExitTransitionElement) obj;
        return kotlin.jvm.internal.L.f(this.f25877b, enterExitTransitionElement.f25877b) && kotlin.jvm.internal.L.f(this.f25878c, enterExitTransitionElement.f25878c) && kotlin.jvm.internal.L.f(this.f25879d, enterExitTransitionElement.f25879d) && kotlin.jvm.internal.L.f(this.f25880e, enterExitTransitionElement.f25880e) && kotlin.jvm.internal.L.f(this.f25881f, enterExitTransitionElement.f25881f) && kotlin.jvm.internal.L.f(this.f25882g, enterExitTransitionElement.f25882g) && kotlin.jvm.internal.L.f(this.f25883h, enterExitTransitionElement.f25883h) && kotlin.jvm.internal.L.f(this.f25884i, enterExitTransitionElement.f25884i);
    }

    public final int hashCode() {
        int iHashCode = this.f25877b.hashCode() * 31;
        C20288i1<EnterExitState>.a<androidx.compose.ui.unit.u, C20321u> aVar = this.f25878c;
        int iHashCode2 = (iHashCode + (aVar == null ? 0 : aVar.hashCode())) * 31;
        C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> aVar2 = this.f25879d;
        int iHashCode3 = (iHashCode2 + (aVar2 == null ? 0 : aVar2.hashCode())) * 31;
        C20288i1<EnterExitState>.a<androidx.compose.ui.unit.q, C20321u> aVar3 = this.f25880e;
        return this.f25884i.hashCode() + androidx.appcompat.app.r.h((this.f25882g.hashCode() + ((this.f25881f.hashCode() + ((iHashCode3 + (aVar3 != null ? aVar3.hashCode() : 0)) * 31)) * 31)) * 31, 31, this.f25883h);
    }

    @Y61.k
    public final String toString() {
        return "EnterExitTransitionElement(transition=" + this.f25877b + ", sizeAnimation=" + this.f25878c + ", offsetAnimation=" + this.f25879d + ", slideAnimation=" + this.f25880e + ", enter=" + this.f25881f + ", exit=" + this.f25882g + ", isEnabled=" + this.f25883h + ", graphicsLayerBlock=" + this.f25884i + ')';
    }
}
