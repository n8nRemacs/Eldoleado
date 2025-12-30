package androidx.compose.foundation;

import androidx.compose.ui.node.AbstractC22430p0;
import androidx.compose.ui.unit.h;
import androidx.compose.ui.v;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: Border.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0080\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Landroidx/compose/foundation/BorderModifierNodeElement;", "Landroidx/compose/ui/node/p0;", "Landroidx/compose/foundation/o0;", "Landroidx/compose/ui/unit/h;", "width", "Landroidx/compose/ui/graphics/J;", "brush", "Landroidx/compose/ui/graphics/h1;", "shape", "<init>", "(FLandroidx/compose/ui/graphics/J;Landroidx/compose/ui/graphics/h1;Lkotlin/jvm/internal/w;)V", "foundation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class BorderModifierNodeElement extends AbstractC22430p0<C20802o0> {

    /* renamed from: b, reason: collision with root package name */
    public final float f26658b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.ui.graphics.J f26659c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.ui.graphics.h1 f26660d;

    public BorderModifierNodeElement(float f12, androidx.compose.ui.graphics.J j12, androidx.compose.ui.graphics.h1 h1Var, C42822w c42822w) {
        this.f26658b = f12;
        this.f26659c = j12;
        this.f26660d = h1Var;
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final v.d a() {
        return new C20802o0(this.f26658b, this.f26659c, this.f26660d, null);
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final void b(v.d dVar) {
        C20802o0 c20802o0 = (C20802o0) dVar;
        float f12 = c20802o0.f29779s;
        float f13 = this.f26658b;
        boolean zB2 = androidx.compose.ui.unit.h.b(f12, f13);
        androidx.compose.ui.draw.f fVar = c20802o0.f29782v;
        if (!zB2) {
            c20802o0.f29779s = f13;
            fVar.D0();
        }
        androidx.compose.ui.graphics.J j12 = c20802o0.f29780t;
        androidx.compose.ui.graphics.J j13 = this.f26659c;
        if (!kotlin.jvm.internal.L.f(j12, j13)) {
            c20802o0.f29780t = j13;
            fVar.D0();
        }
        androidx.compose.ui.graphics.h1 h1Var = c20802o0.f29781u;
        androidx.compose.ui.graphics.h1 h1Var2 = this.f26660d;
        if (kotlin.jvm.internal.L.f(h1Var, h1Var2)) {
            return;
        }
        c20802o0.f29781u = h1Var2;
        fVar.D0();
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BorderModifierNodeElement)) {
            return false;
        }
        BorderModifierNodeElement borderModifierNodeElement = (BorderModifierNodeElement) obj;
        return androidx.compose.ui.unit.h.b(this.f26658b, borderModifierNodeElement.f26658b) && kotlin.jvm.internal.L.f(this.f26659c, borderModifierNodeElement.f26659c) && kotlin.jvm.internal.L.f(this.f26660d, borderModifierNodeElement.f26660d);
    }

    public final int hashCode() {
        h.a aVar = androidx.compose.ui.unit.h.f42849c;
        return this.f26660d.hashCode() + ((this.f26659c.hashCode() + (Float.hashCode(this.f26658b) * 31)) * 31);
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("BorderModifierNodeElement(width=");
        H.u(this.f26658b, ", brush=", sb2);
        sb2.append(this.f26659c);
        sb2.append(", shape=");
        sb2.append(this.f26660d);
        sb2.append(')');
        return sb2.toString();
    }
}
