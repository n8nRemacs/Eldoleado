package androidx.compose.animation.core;

import androidx.compose.runtime.C22128n0;
import androidx.compose.runtime.InterfaceC22118l0;
import kotlin.Metadata;

/* compiled from: Transition.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"S", "T", "Landroidx/compose/runtime/n0;", "Landroidx/compose/runtime/l0;", "invoke", "(Landroidx/compose/runtime/n0;)Landroidx/compose/runtime/l0;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* renamed from: androidx.compose.animation.core.x1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20332x1 extends kotlin.jvm.internal.N implements Y41.l<C22128n0, InterfaceC22118l0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C20288i1<Object> f26435l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ C20288i1<Object> f26436m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20332x1(C20288i1<Object> c20288i1, C20288i1<Object> c20288i12) {
        super(1);
        this.f26435l = c20288i1;
        this.f26436m = c20288i12;
    }

    @Override // Y41.l
    public final InterfaceC22118l0 invoke(C22128n0 c22128n0) {
        C20288i1<Object> c20288i1 = this.f26435l;
        androidx.compose.runtime.snapshots.D<C20288i1<?>> d12 = c20288i1.f26326j;
        C20288i1<?> c20288i12 = this.f26436m;
        d12.add(c20288i12);
        return new C20329w1(c20288i1, c20288i12);
    }
}
