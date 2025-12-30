package androidx.compose.animation;

import androidx.compose.runtime.A;
import androidx.compose.runtime.C22187u0;
import androidx.compose.runtime.internal.C22096n;
import androidx.compose.ui.v;
import kotlin.Metadata;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: SharedTransitionScope.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\u000b¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/Z;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/Z;Landroidx/compose/runtime/A;I)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class j2 extends kotlin.jvm.internal.N implements Y41.q<androidx.compose.ui.layout.Z, androidx.compose.runtime.A, Integer, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C22096n f26500l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public j2(C22096n c22096n) {
        super(3);
        this.f26500l = c22096n;
    }

    @Override // Y41.q
    public final kotlin.G0 invoke(androidx.compose.ui.layout.Z z12, androidx.compose.runtime.A a12, Integer num) {
        androidx.compose.ui.layout.Z z13 = z12;
        androidx.compose.runtime.A a13 = a12;
        num.intValue();
        Object objT = a13.t();
        androidx.compose.runtime.A.f37866a.getClass();
        A.a.C1651a c1651a = A.a.f37868b;
        if (objT == c1651a) {
            objT = C22187u0.h(EmptyCoroutineContext.INSTANCE, a13);
            a13.H(objT);
        }
        kotlinx.coroutines.T t12 = (kotlinx.coroutines.T) objT;
        Object objT2 = a13.t();
        if (objT2 == c1651a) {
            objT2 = new Q1(z13, t12);
            a13.H(objT2);
        }
        Q1 q12 = (Q1) objT2;
        v.a aVar = androidx.compose.ui.v.f42878y1;
        Object objT3 = a13.t();
        if (objT3 == c1651a) {
            objT3 = new f2(q12);
            a13.H(objT3);
        }
        androidx.compose.ui.v vVarA = androidx.compose.ui.layout.P.a(aVar, (Y41.q) objT3);
        Object objT4 = a13.t();
        if (objT4 == c1651a) {
            objT4 = new g2(q12);
            a13.H(objT4);
        }
        this.f26500l.invoke(q12, androidx.compose.ui.draw.o.d(vVarA, (Y41.l) objT4), a13, 6);
        kotlin.G0 g02 = kotlin.G0.f406611a;
        Object objT5 = a13.t();
        if (objT5 == c1651a) {
            objT5 = new i2(q12);
            a13.H(objT5);
        }
        C22187u0.a(g02, (Y41.l) objT5, a13);
        return g02;
    }
}
