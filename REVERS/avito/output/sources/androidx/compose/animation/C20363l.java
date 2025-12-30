package androidx.compose.animation;

import androidx.compose.ui.layout.InterfaceC22363h0;
import androidx.compose.ui.layout.InterfaceC22367j0;
import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.unit.C22712b;
import kotlin.Metadata;

/* compiled from: AnimatedContent.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\t\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0000*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {"S", "Landroidx/compose/ui/layout/k0;", "Landroidx/compose/ui/layout/h0;", "measurable", "Landroidx/compose/ui/unit/b;", "constraints", "Landroidx/compose/ui/layout/j0;", "invoke-3p2s80s", "(Landroidx/compose/ui/layout/k0;Landroidx/compose/ui/layout/h0;J)Landroidx/compose/ui/layout/j0;", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* renamed from: androidx.compose.animation.l, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20363l extends kotlin.jvm.internal.N implements Y41.q<InterfaceC22369k0, InterfaceC22363h0, C22712b, InterfaceC22367j0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C20396w0 f26505l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20363l(C20396w0 c20396w0) {
        super(3);
        this.f26505l = c20396w0;
    }

    @Override // Y41.q
    public final InterfaceC22367j0 invoke(InterfaceC22369k0 interfaceC22369k0, InterfaceC22363h0 interfaceC22363h0, C22712b c22712b) {
        androidx.compose.ui.layout.K0 k0I = interfaceC22363h0.I(c22712b.f42843a);
        return interfaceC22369k0.L0(k0I.f40345b, k0I.f40346c, kotlin.collections.P0.c(), new C20360k(k0I, this.f26505l));
    }
}
