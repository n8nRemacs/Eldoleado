package androidx.compose.animation.core;

import androidx.compose.runtime.C22128n0;
import androidx.compose.runtime.InterfaceC22118l0;
import kotlin.Metadata;

/* compiled from: Transition.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\b\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001\"\b\b\u0002\u0010\u0003*\u00020\u0002*\u00020\u0004H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"S", "T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/runtime/n0;", "Landroidx/compose/runtime/l0;", "invoke", "(Landroidx/compose/runtime/n0;)Landroidx/compose/runtime/l0;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class A1 extends kotlin.jvm.internal.N implements Y41.l<C22128n0, InterfaceC22118l0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C20288i1<Object> f26012l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ C20288i1<Object>.d<Object, Object> f26013m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public A1(C20288i1<Object> c20288i1, C20288i1<Object>.d<Object, Object> dVar) {
        super(1);
        this.f26012l = c20288i1;
        this.f26013m = dVar;
    }

    @Override // Y41.l
    public final InterfaceC22118l0 invoke(C22128n0 c22128n0) {
        C20288i1<Object> c20288i1 = this.f26012l;
        androidx.compose.runtime.snapshots.D<C20288i1<Object>.d<?, ?>> d12 = c20288i1.f26325i;
        C20288i1<Object>.d<?, ?> dVar = this.f26013m;
        d12.add(dVar);
        return new C20338z1(c20288i1, dVar);
    }
}
