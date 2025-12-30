package androidx.compose.animation.core;

import androidx.compose.animation.core.C20281g0;
import androidx.compose.runtime.InterfaceC22118l0;
import kotlin.Metadata;

/* compiled from: Effects.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002¸\u0006\u0000"}, d2 = {"androidx/compose/runtime/m0", "Landroidx/compose/runtime/l0;", "runtime_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.i0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20287i0 implements InterfaceC22118l0 {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C20281g0 f26315a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C20281g0.a f26316b;

    public C20287i0(C20281g0 c20281g0, C20281g0.a aVar) {
        this.f26315a = c20281g0;
        this.f26316b = aVar;
    }

    @Override // androidx.compose.runtime.InterfaceC22118l0
    public final void dispose() {
        this.f26315a.f26273a.j(this.f26316b);
    }
}
