package androidx.compose.foundation;

import kotlin.Metadata;

/* compiled from: Background.kt */
@Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0003\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Lkotlin/G0;", "invoke", "()V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class B extends kotlin.jvm.internal.N implements Y41.a<kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C f26633l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.node.P f26634m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public B(C c12, androidx.compose.ui.node.P p12) {
        super(0);
        this.f26633l = c12;
        this.f26634m = p12;
    }

    @Override // Y41.a
    public final kotlin.G0 invoke() {
        C c12 = this.f26633l;
        androidx.compose.ui.graphics.h1 h1Var = c12.f26664s;
        androidx.compose.ui.node.P p12 = this.f26634m;
        c12.f26669x = h1Var.a(p12.f40671b.i(), p12.getLayoutDirection(), p12);
        return kotlin.G0.f406611a;
    }
}
