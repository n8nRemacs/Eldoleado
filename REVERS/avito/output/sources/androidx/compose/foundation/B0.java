package androidx.compose.foundation;

import androidx.compose.runtime.A;
import androidx.compose.runtime.J3;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: Clickable.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0010\u0004\u001a\u00020\u0000*\u00020\u0000H\u000b¢\u0006\u0004\b\u0001\u0010\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/ui/v;", "invoke", "(Landroidx/compose/ui/v;Landroidx/compose/runtime/A;I)Landroidx/compose/ui/v;", "androidx/compose/foundation/y0", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
public final class B0 extends kotlin.jvm.internal.N implements Y41.q<androidx.compose.ui.v, androidx.compose.runtime.A, Integer, androidx.compose.ui.v> {
    @Override // Y41.q
    public final androidx.compose.ui.v invoke(androidx.compose.ui.v vVar, androidx.compose.runtime.A a12, Integer num) {
        androidx.compose.runtime.A a13 = a12;
        num.intValue();
        a13.C(-1525724089);
        Object objT = a13.t();
        androidx.compose.runtime.A.f37866a.getClass();
        if (objT == A.a.f37868b) {
            objT = androidx.compose.foundation.interaction.l.a();
            a13.H(objT);
        }
        androidx.compose.foundation.interaction.m mVar = (androidx.compose.foundation.interaction.m) objT;
        v.a aVar = androidx.compose.ui.v.f42878y1;
        J3 j32 = C20839s1.f30098a;
        CombinedClickableElement combinedClickableElement = new CombinedClickableElement(mVar, null, false, null, null, null, null, null, null, true, null);
        aVar.getClass();
        a13.h();
        return combinedClickableElement;
    }
}
