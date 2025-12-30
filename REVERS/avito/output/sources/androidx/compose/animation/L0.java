package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import kotlin.Metadata;

/* compiled from: EnterExitTransition.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroidx/compose/animation/core/i1$b;", "Landroidx/compose/animation/EnterExitState;", "Landroidx/compose/animation/core/V;", "", "invoke", "(Landroidx/compose/animation/core/i1$b;)Landroidx/compose/animation/core/V;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class L0 extends kotlin.jvm.internal.N implements Y41.l<C20288i1.b<EnterExitState>, androidx.compose.animation.core.V<Float>> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ AbstractC20359j1 f25914l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ AbstractC20365l1 f25915m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public L0(AbstractC20359j1 abstractC20359j1, AbstractC20365l1 abstractC20365l1) {
        super(1);
        this.f25914l = abstractC20359j1;
        this.f25915m = abstractC20365l1;
    }

    @Override // Y41.l
    public final androidx.compose.animation.core.V<Float> invoke(C20288i1.b<EnterExitState> bVar) {
        androidx.compose.animation.core.V<Float> v12;
        androidx.compose.animation.core.V<Float> v13;
        C20288i1.b<EnterExitState> bVar2 = bVar;
        EnterExitState enterExitState = EnterExitState.f25872b;
        EnterExitState enterExitState2 = EnterExitState.f25873c;
        if (bVar2.b(enterExitState, enterExitState2)) {
            A1 a12 = this.f25914l.getF26503c().f26628d;
            return (a12 == null || (v13 = a12.f25840c) == null) ? H0.f25895b : v13;
        }
        if (!bVar2.b(enterExitState2, EnterExitState.f25874d)) {
            return H0.f25895b;
        }
        A1 a13 = this.f25915m.getF26511d().f26628d;
        return (a13 == null || (v12 = a13.f25840c) == null) ? H0.f25895b : v12;
    }
}
