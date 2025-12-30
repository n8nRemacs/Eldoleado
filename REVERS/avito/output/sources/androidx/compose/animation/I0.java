package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import kotlin.Metadata;

/* compiled from: EnterExitTransition.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroidx/compose/animation/core/i1$b;", "Landroidx/compose/animation/EnterExitState;", "Landroidx/compose/animation/core/V;", "", "invoke", "(Landroidx/compose/animation/core/i1$b;)Landroidx/compose/animation/core/V;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class I0 extends kotlin.jvm.internal.N implements Y41.l<C20288i1.b<EnterExitState>, androidx.compose.animation.core.V<Float>> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ AbstractC20359j1 f25903l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ AbstractC20365l1 f25904m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public I0(AbstractC20359j1 abstractC20359j1, AbstractC20365l1 abstractC20365l1) {
        super(1);
        this.f25903l = abstractC20359j1;
        this.f25904m = abstractC20365l1;
    }

    @Override // Y41.l
    public final androidx.compose.animation.core.V<Float> invoke(C20288i1.b<EnterExitState> bVar) {
        androidx.compose.animation.core.V<Float> v12;
        androidx.compose.animation.core.V<Float> v13;
        C20288i1.b<EnterExitState> bVar2 = bVar;
        EnterExitState enterExitState = EnterExitState.f25872b;
        EnterExitState enterExitState2 = EnterExitState.f25873c;
        if (bVar2.b(enterExitState, enterExitState2)) {
            C20377p1 c20377p1 = this.f25903l.getF26503c().f26625a;
            return (c20377p1 == null || (v13 = c20377p1.f26543b) == null) ? H0.f25895b : v13;
        }
        if (!bVar2.b(enterExitState2, EnterExitState.f25874d)) {
            return H0.f25895b;
        }
        C20377p1 c20377p12 = this.f25904m.getF26511d().f26625a;
        return (c20377p12 == null || (v12 = c20377p12.f26543b) == null) ? H0.f25895b : v12;
    }
}
