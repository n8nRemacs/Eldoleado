package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.ui.graphics.InterfaceC22276o0;
import kotlin.Metadata;

/* compiled from: Crossfade.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u0002\"\u0004\b\u0000\u0010\u0000*\u00020\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"T", "Landroidx/compose/ui/graphics/o0;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/graphics/o0;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class B0 extends kotlin.jvm.internal.N implements Y41.l<InterfaceC22276o0, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C20288i1.d f25860l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public B0(C20288i1.d dVar) {
        super(1);
        this.f25860l = dVar;
    }

    @Override // Y41.l
    public final kotlin.G0 invoke(InterfaceC22276o0 interfaceC22276o0) {
        interfaceC22276o0.setAlpha(((Number) this.f25860l.getF42167b()).floatValue());
        return kotlin.G0.f406611a;
    }
}
