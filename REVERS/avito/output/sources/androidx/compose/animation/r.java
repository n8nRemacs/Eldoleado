package androidx.compose.animation;

import androidx.compose.animation.AnimatedContentTransitionScopeImpl;
import androidx.compose.animation.core.C20288i1;
import androidx.compose.runtime.A;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.InterfaceC22204y1;
import androidx.compose.runtime.internal.InterfaceC22085c;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: AnimatedContent.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0000H\u000b¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"S", "Lkotlin/G0;", "invoke", "(Landroidx/compose/runtime/A;I)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class r extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C20288i1<Object> f26561l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ Object f26562m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ Y41.l<InterfaceC20401y<Object>, C20396w0> f26563n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ AnimatedContentTransitionScopeImpl<Object> f26564o;

    /* renamed from: p, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.runtime.snapshots.D<Object> f26565p;

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ InterfaceC22085c f26566q;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public r(C20288i1 c20288i1, Object obj, Y41.l lVar, AnimatedContentTransitionScopeImpl animatedContentTransitionScopeImpl, androidx.compose.runtime.snapshots.D d12, InterfaceC22085c interfaceC22085c) {
        super(2);
        this.f26561l = c20288i1;
        this.f26562m = obj;
        this.f26563n = lVar;
        this.f26564o = animatedContentTransitionScopeImpl;
        this.f26565p = d12;
        this.f26566q = interfaceC22085c;
    }

    @Override // Y41.p
    public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
        Object obj;
        androidx.compose.runtime.A a13 = a12;
        int iIntValue = num.intValue();
        if (a13.p(iIntValue & 1, (iIntValue & 3) != 2)) {
            Object objT = a13.t();
            androidx.compose.runtime.A.f37866a.getClass();
            Object obj2 = A.a.f37868b;
            Y41.l<InterfaceC20401y<Object>, C20396w0> lVar = this.f26563n;
            AnimatedContentTransitionScopeImpl<Object> animatedContentTransitionScopeImpl = this.f26564o;
            if (objT == obj2) {
                objT = (C20396w0) lVar.invoke(animatedContentTransitionScopeImpl);
                a13.H(objT);
            }
            C20396w0 c20396w0 = (C20396w0) objT;
            C20288i1<Object> c20288i1 = this.f26561l;
            Object objD = c20288i1.f().d();
            Object obj3 = this.f26562m;
            boolean zJ2 = a13.j(kotlin.jvm.internal.L.f(objD, obj3));
            Object objT2 = a13.t();
            if (zJ2 || objT2 == obj2) {
                if (kotlin.jvm.internal.L.f(c20288i1.f().d(), obj3)) {
                    AbstractC20365l1.f26506a.getClass();
                    obj = AbstractC20365l1.f26507b;
                } else {
                    obj = lVar.invoke(animatedContentTransitionScopeImpl).f26604b;
                }
                objT2 = obj;
                a13.H(objT2);
            }
            AbstractC20365l1 abstractC20365l1 = (AbstractC20365l1) objT2;
            Object objT3 = a13.t();
            InterfaceC22204y1 interfaceC22204y1 = c20288i1.f26320d;
            if (objT3 == obj2) {
                objT3 = new AnimatedContentTransitionScopeImpl.a(kotlin.jvm.internal.L.f(obj3, ((C22082i3) interfaceC22204y1).getF42167b()));
                a13.H(objT3);
            }
            AnimatedContentTransitionScopeImpl.a aVar = (AnimatedContentTransitionScopeImpl.a) objT3;
            AbstractC20359j1 abstractC20359j1 = c20396w0.f26603a;
            v.a aVar2 = androidx.compose.ui.v.f42878y1;
            boolean zU2 = a13.u(c20396w0);
            Object objT4 = a13.t();
            if (zU2 || objT4 == obj2) {
                objT4 = new C20363l(c20396w0);
                a13.H(objT4);
            }
            androidx.compose.ui.v vVarA = androidx.compose.ui.layout.P.a(aVar2, (Y41.q) objT4);
            ((C22082i3) aVar.f25848b).setValue(Boolean.valueOf(kotlin.jvm.internal.L.f(obj3, ((C22082i3) interfaceC22204y1).getF42167b())));
            androidx.compose.ui.v vVarD0 = vVarA.d0(aVar);
            boolean zU3 = a13.u(obj3);
            Object objT5 = a13.t();
            if (zU3 || objT5 == obj2) {
                objT5 = new C20366m(obj3);
                a13.H(objT5);
            }
            Y41.l lVar2 = (Y41.l) objT5;
            boolean zB2 = a13.B(abstractC20365l1);
            Object objT6 = a13.t();
            if (zB2 || objT6 == obj2) {
                objT6 = new C20369n(abstractC20365l1);
                a13.H(objT6);
            }
            C20340d0.a(c20288i1, lVar2, vVarD0, abstractC20359j1, abstractC20365l1, (Y41.p) objT6, androidx.compose.runtime.internal.r.c(-616195562, new C20378q(this.f26565p, obj3, animatedContentTransitionScopeImpl, this.f26566q), a13), a13, 12582912);
        } else {
            a13.f();
        }
        return kotlin.G0.f406611a;
    }
}
