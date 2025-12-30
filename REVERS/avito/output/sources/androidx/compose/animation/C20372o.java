package androidx.compose.animation;

import androidx.compose.runtime.InterfaceC22118l0;
import kotlin.Metadata;

/* compiled from: Effects.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002¸\u0006\u0000"}, d2 = {"androidx/compose/runtime/m0", "Landroidx/compose/runtime/l0;", "runtime_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.o, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20372o implements InterfaceC22118l0 {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.runtime.snapshots.D f26533a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f26534b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ AnimatedContentTransitionScopeImpl f26535c;

    public C20372o(androidx.compose.runtime.snapshots.D d12, Object obj, AnimatedContentTransitionScopeImpl animatedContentTransitionScopeImpl) {
        this.f26533a = d12;
        this.f26534b = obj;
        this.f26535c = animatedContentTransitionScopeImpl;
    }

    @Override // androidx.compose.runtime.InterfaceC22118l0
    public final void dispose() {
        androidx.compose.runtime.snapshots.D d12 = this.f26533a;
        Object obj = this.f26534b;
        d12.remove(obj);
        this.f26535c.f25844d.k(obj);
    }
}
