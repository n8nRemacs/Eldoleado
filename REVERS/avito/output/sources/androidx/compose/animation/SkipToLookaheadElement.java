package androidx.compose.animation;

import androidx.compose.runtime.C22082i3;
import androidx.compose.ui.node.AbstractC22430p0;
import androidx.compose.ui.v;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: SkipToLookaheadNode.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0080\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/SkipToLookaheadElement;", "Landroidx/compose/ui/node/p0;", "Landroidx/compose/animation/r2;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class SkipToLookaheadElement extends AbstractC22430p0<r2> {

    /* renamed from: b, reason: collision with root package name */
    @Y61.l
    public final B1 f25964b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final Y41.a<Boolean> f25965c;

    public SkipToLookaheadElement() {
        this(null, null, 3, null);
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final v.d a() {
        return new r2(this.f25964b, this.f25965c);
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final void b(v.d dVar) {
        r2 r2Var = (r2) dVar;
        ((C22082i3) r2Var.f26573p).setValue(this.f25964b);
        ((C22082i3) r2Var.f26574q).setValue(this.f25965c);
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SkipToLookaheadElement)) {
            return false;
        }
        SkipToLookaheadElement skipToLookaheadElement = (SkipToLookaheadElement) obj;
        return kotlin.jvm.internal.L.f(this.f25964b, skipToLookaheadElement.f25964b) && kotlin.jvm.internal.L.f(this.f25965c, skipToLookaheadElement.f25965c);
    }

    public final int hashCode() {
        B1 b12 = this.f25964b;
        return this.f25965c.hashCode() + ((b12 == null ? 0 : b12.hashCode()) * 31);
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("SkipToLookaheadElement(scaleToBounds=");
        sb2.append(this.f25964b);
        sb2.append(", isEnabled=");
        return androidx.appcompat.app.r.v(sb2, this.f25965c, ')');
    }

    public SkipToLookaheadElement(B1 b12, Y41.a aVar, int i12, C42822w c42822w) {
        b12 = (i12 & 1) != 0 ? null : b12;
        aVar = (i12 & 2) != 0 ? s2.f26589a : aVar;
        this.f25964b = b12;
        this.f25965c = aVar;
    }
}
