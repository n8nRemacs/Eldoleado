package androidx.compose.animation.core;

import androidx.compose.animation.core.C20281g0;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22128n0;
import androidx.compose.runtime.InterfaceC22118l0;
import kotlin.Metadata;

/* compiled from: InfiniteTransition.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0007\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001*\u00020\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"T", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/runtime/n0;", "Landroidx/compose/runtime/l0;", "invoke", "(Landroidx/compose/runtime/n0;)Landroidx/compose/runtime/l0;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* renamed from: androidx.compose.animation.core.j0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20290j0 extends kotlin.jvm.internal.N implements Y41.l<C22128n0, InterfaceC22118l0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C20281g0 f26357l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ C20281g0.a<Object, Object> f26358m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20290j0(C20281g0 c20281g0, C20281g0.a<Object, Object> aVar) {
        super(1);
        this.f26357l = c20281g0;
        this.f26358m = aVar;
    }

    @Override // Y41.l
    public final InterfaceC22118l0 invoke(C22128n0 c22128n0) {
        C20281g0 c20281g0 = this.f26357l;
        androidx.compose.runtime.collection.e<C20281g0.a<?, ?>> eVar = c20281g0.f26273a;
        C20281g0.a<Object, Object> aVar = this.f26358m;
        eVar.b(aVar);
        ((C22082i3) c20281g0.f26274b).setValue(Boolean.TRUE);
        return new C20287i0(c20281g0, aVar);
    }
}
