package androidx.compose.animation;

import kotlin.Metadata;

/* compiled from: EnterExitTransition.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/unit/u;", "it", "invoke-mzRDjE0", "(J)J", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class X0 extends kotlin.jvm.internal.N implements Y41.l<androidx.compose.ui.unit.u, androidx.compose.ui.unit.u> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ Y41.l<Integer, Integer> f25989l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public X0(Y41.l<? super Integer, Integer> lVar) {
        super(1);
        this.f25989l = lVar;
    }

    @Override // Y41.l
    public final androidx.compose.ui.unit.u invoke(androidx.compose.ui.unit.u uVar) {
        long j12 = uVar.f42872a;
        return androidx.compose.ui.unit.u.a((this.f25989l.invoke(Integer.valueOf((int) (j12 >> 32))).intValue() << 32) | (4294967295L & ((int) (j12 & 4294967295L))));
    }
}
