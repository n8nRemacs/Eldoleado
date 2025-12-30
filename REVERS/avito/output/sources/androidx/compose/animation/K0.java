package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.ui.graphics.InterfaceC22276o0;
import kotlin.Metadata;

/* compiled from: EnterExitTransition.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/graphics/o0;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/graphics/o0;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class K0 extends kotlin.jvm.internal.N implements Y41.l<InterfaceC22276o0, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C20288i1.a.C1552a f25911l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ C20288i1.a.C1552a f25912m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ C20288i1.a.C1552a f25913n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public K0(C20288i1.a.C1552a c1552a, C20288i1.a.C1552a c1552a2, C20288i1.a.C1552a c1552a3) {
        super(1);
        this.f25911l = c1552a;
        this.f25912m = c1552a2;
        this.f25913n = c1552a3;
    }

    @Override // Y41.l
    public final kotlin.G0 invoke(InterfaceC22276o0 interfaceC22276o0) {
        long j12;
        InterfaceC22276o0 interfaceC22276o02 = interfaceC22276o0;
        C20288i1.a.C1552a c1552a = this.f25911l;
        interfaceC22276o02.setAlpha(c1552a != null ? ((Number) c1552a.getF42167b()).floatValue() : 1.0f);
        C20288i1.a.C1552a c1552a2 = this.f25912m;
        interfaceC22276o02.j(c1552a2 != null ? ((Number) c1552a2.getF42167b()).floatValue() : 1.0f);
        interfaceC22276o02.k(c1552a2 != null ? ((Number) c1552a2.getF42167b()).floatValue() : 1.0f);
        C20288i1.a.C1552a c1552a3 = this.f25913n;
        if (c1552a3 != null) {
            j12 = ((androidx.compose.ui.graphics.r1) c1552a3.getF42167b()).f39757a;
        } else {
            androidx.compose.ui.graphics.r1.f39755b.getClass();
            j12 = androidx.compose.ui.graphics.r1.f39756c;
        }
        interfaceC22276o02.i1(j12);
        return kotlin.G0.f406611a;
    }
}
