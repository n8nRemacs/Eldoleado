package androidx.compose.foundation;

import androidx.compose.ui.node.C22440v;
import androidx.compose.ui.node.InterfaceC22438u;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.v;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import okhttp3.internal.ws.WebSocketProtocol;

/* compiled from: Background.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B)\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Landroidx/compose/foundation/C;", "Landroidx/compose/ui/node/u;", "Landroidx/compose/ui/v$d;", "Landroidx/compose/ui/node/D0;", "Landroidx/compose/ui/graphics/T;", "color", "Landroidx/compose/ui/graphics/J;", "brush", "", "alpha", "Landroidx/compose/ui/graphics/h1;", "shape", "<init>", "(JLandroidx/compose/ui/graphics/J;FLandroidx/compose/ui/graphics/h1;Lkotlin/jvm/internal/w;)V", "foundation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class C extends v.d implements InterfaceC22438u, androidx.compose.ui.node.D0 {

    /* renamed from: p, reason: collision with root package name */
    public long f26661p;

    /* renamed from: q, reason: collision with root package name */
    @Y61.l
    public androidx.compose.ui.graphics.J f26662q;

    /* renamed from: r, reason: collision with root package name */
    public float f26663r;

    /* renamed from: s, reason: collision with root package name */
    @Y61.k
    public androidx.compose.ui.graphics.h1 f26664s;

    /* renamed from: t, reason: collision with root package name */
    public long f26665t;

    /* renamed from: u, reason: collision with root package name */
    @Y61.l
    public LayoutDirection f26666u;

    /* renamed from: v, reason: collision with root package name */
    @Y61.l
    public androidx.compose.ui.graphics.D0 f26667v;

    /* renamed from: w, reason: collision with root package name */
    @Y61.l
    public androidx.compose.ui.graphics.h1 f26668w;

    /* renamed from: x, reason: collision with root package name */
    @Y61.l
    public androidx.compose.ui.graphics.D0 f26669x;

    public C(long j12, androidx.compose.ui.graphics.J j13, float f12, androidx.compose.ui.graphics.h1 h1Var, C42822w c42822w) {
        this.f26661p = j12;
        this.f26662q = j13;
        this.f26663r = f12;
        this.f26664s = h1Var;
        l0.n.f413402b.getClass();
        this.f26665t = l0.n.f413403c;
    }

    @Override // androidx.compose.ui.node.D0
    public final void K1() {
        l0.n.f413402b.getClass();
        this.f26665t = l0.n.f413403c;
        this.f26666u = null;
        this.f26667v = null;
        this.f26668w = null;
        C22440v.a(this);
    }

    @Override // androidx.compose.ui.node.InterfaceC22438u
    public final void t(@Y61.k androidx.compose.ui.node.P p12) {
        androidx.compose.ui.graphics.D0 d02;
        if (this.f26664s == androidx.compose.ui.graphics.Y0.f39346a) {
            long j12 = this.f26661p;
            androidx.compose.ui.graphics.T.f39320b.getClass();
            if (!androidx.compose.ui.graphics.T.d(j12, androidx.compose.ui.graphics.T.f39330l)) {
                androidx.compose.ui.graphics.drawscope.g.c0(p12, this.f26661p, 0L, 0L, 0.0f, null, WebSocketProtocol.PAYLOAD_SHORT);
            }
            androidx.compose.ui.graphics.J j13 = this.f26662q;
            if (j13 != null) {
                androidx.compose.ui.graphics.drawscope.g.e0(p12, j13, 0L, 0L, this.f26663r, null, null, 118);
            }
        } else {
            androidx.compose.ui.graphics.drawscope.a aVar = p12.f40671b;
            if (l0.n.b(aVar.i(), this.f26665t) && p12.getLayoutDirection() == this.f26666u && kotlin.jvm.internal.L.f(this.f26668w, this.f26664s)) {
                d02 = this.f26667v;
            } else {
                androidx.compose.ui.node.E0.a(this, new B(this, p12));
                d02 = this.f26669x;
                this.f26669x = null;
            }
            androidx.compose.ui.graphics.D0 d03 = d02;
            this.f26667v = d03;
            this.f26665t = aVar.i();
            this.f26666u = p12.getLayoutDirection();
            this.f26668w = this.f26664s;
            long j14 = this.f26661p;
            androidx.compose.ui.graphics.T.f39320b.getClass();
            if (!androidx.compose.ui.graphics.T.d(j14, androidx.compose.ui.graphics.T.f39330l)) {
                androidx.compose.ui.graphics.E0.b(p12, d03, this.f26661p, null, 60);
            }
            androidx.compose.ui.graphics.J j15 = this.f26662q;
            if (j15 != null) {
                androidx.compose.ui.graphics.E0.a(p12, d03, j15, this.f26663r);
            }
        }
        p12.y1();
    }
}
