package androidx.compose.foundation;

import androidx.compose.ui.graphics.T;
import androidx.compose.ui.node.AbstractC22430p0;
import androidx.compose.ui.platform.C22547y1;
import androidx.compose.ui.v;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: Background.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BF\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0017\u0010\u000f\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000e¢\u0006\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Landroidx/compose/foundation/BackgroundElement;", "Landroidx/compose/ui/node/p0;", "Landroidx/compose/foundation/C;", "Landroidx/compose/ui/graphics/T;", "color", "Landroidx/compose/ui/graphics/J;", "brush", "", "alpha", "Landroidx/compose/ui/graphics/h1;", "shape", "Lkotlin/Function1;", "Landroidx/compose/ui/platform/y1;", "Lkotlin/G0;", "Lkotlin/w;", "inspectorInfo", "<init>", "(JLandroidx/compose/ui/graphics/J;FLandroidx/compose/ui/graphics/h1;LY41/l;Lkotlin/jvm/internal/w;)V", "foundation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class BackgroundElement extends AbstractC22430p0<C> {

    /* renamed from: b, reason: collision with root package name */
    public final long f26653b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.l
    public final androidx.compose.ui.graphics.J f26654c;

    /* renamed from: d, reason: collision with root package name */
    public final float f26655d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.k
    public final androidx.compose.ui.graphics.h1 f26656e;

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public final Y41.l<C22547y1, kotlin.G0> f26657f;

    public BackgroundElement() {
        throw null;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public BackgroundElement(long j12, androidx.compose.ui.graphics.J j13, float f12, androidx.compose.ui.graphics.h1 h1Var, Y41.l lVar, int i12, C42822w c42822w) {
        long j14;
        if ((i12 & 1) != 0) {
            androidx.compose.ui.graphics.T.f39320b.getClass();
            j14 = androidx.compose.ui.graphics.T.f39330l;
        } else {
            j14 = j12;
        }
        this(j14, (i12 & 2) != 0 ? null : j13, f12, h1Var, lVar, null);
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final v.d a() {
        return new C(this.f26653b, this.f26654c, this.f26655d, this.f26656e, null);
    }

    @Override // androidx.compose.ui.node.AbstractC22430p0
    public final void b(v.d dVar) {
        C c12 = (C) dVar;
        c12.f26661p = this.f26653b;
        c12.f26662q = this.f26654c;
        c12.f26663r = this.f26655d;
        c12.f26664s = this.f26656e;
    }

    public final boolean equals(@Y61.l Object obj) {
        BackgroundElement backgroundElement = obj instanceof BackgroundElement ? (BackgroundElement) obj : null;
        return backgroundElement != null && androidx.compose.ui.graphics.T.d(this.f26653b, backgroundElement.f26653b) && kotlin.jvm.internal.L.f(this.f26654c, backgroundElement.f26654c) && this.f26655d == backgroundElement.f26655d && kotlin.jvm.internal.L.f(this.f26656e, backgroundElement.f26656e);
    }

    public final int hashCode() {
        T.a aVar = androidx.compose.ui.graphics.T.f39320b;
        int i12 = kotlin.w0.f410662c;
        int iHashCode = Long.hashCode(this.f26653b) * 31;
        androidx.compose.ui.graphics.J j12 = this.f26654c;
        return this.f26656e.hashCode() + androidx.appcompat.app.r.d(this.f26655d, (iHashCode + (j12 != null ? j12.hashCode() : 0)) * 31, 31);
    }

    public BackgroundElement(long j12, androidx.compose.ui.graphics.J j13, float f12, androidx.compose.ui.graphics.h1 h1Var, Y41.l lVar, C42822w c42822w) {
        this.f26653b = j12;
        this.f26654c = j13;
        this.f26655d = f12;
        this.f26656e = h1Var;
        this.f26657f = lVar;
    }
}
