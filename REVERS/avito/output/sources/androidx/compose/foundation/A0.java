package androidx.compose.foundation;

import androidx.compose.runtime.A;
import kotlin.Metadata;

/* compiled from: Clickable.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0003\u001a\u00020\u0000*\u00020\u0000H\u000b¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Landroidx/compose/ui/v;", "invoke", "(Landroidx/compose/ui/v;Landroidx/compose/runtime/A;I)Landroidx/compose/ui/v;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class A0 extends kotlin.jvm.internal.N implements Y41.q<androidx.compose.ui.v, androidx.compose.runtime.A, Integer, androidx.compose.ui.v> {
    @Override // Y41.q
    public final androidx.compose.ui.v invoke(androidx.compose.ui.v vVar, androidx.compose.runtime.A a12, Integer num) {
        androidx.compose.foundation.interaction.m mVar;
        androidx.compose.runtime.A a13 = a12;
        num.intValue();
        a13.C(1969174843);
        InterfaceC20834q1 interfaceC20834q1 = (InterfaceC20834q1) a13.o(C20839s1.f30098a);
        if (interfaceC20834q1 instanceof InterfaceC21084v1) {
            a13.C(-1724200443);
            a13.h();
            mVar = null;
        } else {
            a13.C(-1724067825);
            Object objT = a13.t();
            androidx.compose.runtime.A.f37866a.getClass();
            if (objT == A.a.f37868b) {
                objT = androidx.compose.foundation.interaction.l.a();
                a13.H(objT);
            }
            mVar = (androidx.compose.foundation.interaction.m) objT;
            a13.h();
        }
        androidx.compose.ui.v vVarD = C21086w0.d(androidx.compose.ui.v.f42878y1, mVar, interfaceC20834q1, false, null, null);
        a13.h();
        return vVarD;
    }
}
