package androidx.compose.foundation;

import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.F3;
import androidx.compose.runtime.InterfaceC22204y1;
import kotlin.Metadata;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: BasicTooltip.kt */
@F3
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0003\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/foundation/b0;", "Landroidx/compose/foundation/Y;", "foundation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.foundation.b0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20411b0 implements Y {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final G1 f26949a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26950b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.l
    public kotlinx.coroutines.r f26951c;

    public C20411b0(boolean z12, @Y61.k G1 g12) {
        this.f26949a = g12;
        this.f26950b = C22126m3.g(Boolean.valueOf(z12));
    }

    @Override // androidx.compose.foundation.Y
    @Y61.l
    public final Object a(@Y61.k MutatePriority mutatePriority, @Y61.k SuspendLambda suspendLambda) {
        Object objB = this.f26949a.b(mutatePriority, new Z(this, new C20408a0(this, null), null), suspendLambda);
        return objB == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objB : kotlin.G0.f406611a;
    }

    @Override // androidx.compose.foundation.Y
    public final void dismiss() {
        ((C22082i3) this.f26950b).setValue(Boolean.FALSE);
    }

    @Override // androidx.compose.foundation.Y
    public final boolean isVisible() {
        return ((Boolean) ((C22082i3) this.f26950b).getF42167b()).booleanValue();
    }
}
