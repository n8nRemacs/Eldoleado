package androidx.compose.foundation;

import androidx.compose.runtime.A;
import kotlin.Metadata;

/* compiled from: Clickable.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0010\u0004\u001a\u00020\u0000*\u00020\u0000H\u000b¢\u0006\u0004\b\u0001\u0010\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/ui/v;", "invoke", "(Landroidx/compose/ui/v;Landroidx/compose/runtime/A;I)Landroidx/compose/ui/v;", "androidx/compose/foundation/y0", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
public final class C0 extends kotlin.jvm.internal.N implements Y41.q<androidx.compose.ui.v, androidx.compose.runtime.A, Integer, androidx.compose.ui.v> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20834q1 f26670l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ boolean f26671m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ Y41.a f26672n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ Y41.a f26673o;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0(InterfaceC20834q1 interfaceC20834q1, boolean z12, Y41.a aVar, Y41.a aVar2) {
        super(3);
        this.f26670l = interfaceC20834q1;
        this.f26671m = z12;
        this.f26672n = aVar;
        this.f26673o = aVar2;
    }

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
        androidx.compose.ui.v vVarD0 = C20839s1.a(androidx.compose.ui.v.f42878y1, mVar, this.f26670l).d0(new CombinedClickableElement(mVar, null, this.f26671m, null, null, this.f26672n, null, this.f26673o, null, true, null));
        a13.h();
        return vVarD0;
    }
}
