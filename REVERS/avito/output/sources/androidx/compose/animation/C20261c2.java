package androidx.compose.animation;

import kotlin.Metadata;

/* compiled from: SharedTransitionScope.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u000b¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroidx/compose/animation/N1;", "Landroidx/compose/ui/v;", "sharedTransitionModifier", "Lkotlin/G0;", "invoke", "(Landroidx/compose/animation/N1;Landroidx/compose/ui/v;Landroidx/compose/runtime/A;I)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* renamed from: androidx.compose.animation.c2, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20261c2 extends kotlin.jvm.internal.N implements Y41.r<N1, androidx.compose.ui.v, androidx.compose.runtime.A, Integer, kotlin.G0> {
    public C20261c2() {
        throw null;
    }

    @Override // Y41.r
    public final kotlin.G0 invoke(N1 n12, androidx.compose.ui.v vVar, androidx.compose.runtime.A a12, Integer num) {
        int i12;
        N1 n13 = n12;
        androidx.compose.ui.v vVar2 = vVar;
        androidx.compose.runtime.A a13 = a12;
        int iIntValue = num.intValue();
        if ((iIntValue & 6) == 0) {
            i12 = (a13.B(n13) ? 4 : 2) | iIntValue;
        } else {
            i12 = iIntValue;
        }
        if ((iIntValue & 48) == 0) {
            i12 |= a13.B(vVar2) ? 32 : 16;
        }
        if (a13.p(i12 & 1, (i12 & 147) != 146)) {
            throw null;
        }
        a13.f();
        return kotlin.G0.f406611a;
    }
}
