package androidx.compose.animation;

import androidx.compose.runtime.C22128n0;
import androidx.compose.runtime.InterfaceC22118l0;
import kotlin.Metadata;

/* compiled from: AnimatedContent.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"S", "Landroidx/compose/runtime/n0;", "Landroidx/compose/runtime/l0;", "invoke", "(Landroidx/compose/runtime/n0;)Landroidx/compose/runtime/l0;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* renamed from: androidx.compose.animation.p, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20375p extends kotlin.jvm.internal.N implements Y41.l<C22128n0, InterfaceC22118l0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.runtime.snapshots.D<Object> f26536l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ Object f26537m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ AnimatedContentTransitionScopeImpl<Object> f26538n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20375p(androidx.compose.runtime.snapshots.D<Object> d12, Object obj, AnimatedContentTransitionScopeImpl<Object> animatedContentTransitionScopeImpl) {
        super(1);
        this.f26536l = d12;
        this.f26537m = obj;
        this.f26538n = animatedContentTransitionScopeImpl;
    }

    @Override // Y41.l
    public final InterfaceC22118l0 invoke(C22128n0 c22128n0) {
        return new C20372o(this.f26536l, this.f26537m, this.f26538n);
    }
}
