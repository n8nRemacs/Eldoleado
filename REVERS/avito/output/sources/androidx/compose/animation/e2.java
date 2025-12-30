package androidx.compose.animation;

import androidx.compose.ui.layout.InterfaceC22369k0;
import androidx.compose.ui.layout.K0;
import kotlin.Metadata;

/* compiled from: SharedTransitionScope.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0004\u001a\u00020\u0001*\u00020\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Landroidx/compose/ui/layout/K0$a;", "Lkotlin/G0;", "invoke", "(Landroidx/compose/ui/layout/K0$a;)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class e2 extends kotlin.jvm.internal.N implements Y41.l<K0.a, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ InterfaceC22369k0 f26451l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ Q1 f26452m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.layout.K0 f26453n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public e2(InterfaceC22369k0 interfaceC22369k0, Q1 q12, androidx.compose.ui.layout.K0 k02) {
        super(1);
        this.f26451l = interfaceC22369k0;
        this.f26452m = q12;
        this.f26453n = k02;
    }

    @Override // Y41.l
    public final kotlin.G0 invoke(K0.a aVar) {
        K0.a aVar2 = aVar;
        androidx.compose.ui.layout.A aB2 = aVar2.b();
        if (aB2 != null) {
            boolean zI1 = this.f26451l.I1();
            Q1 q12 = this.f26452m;
            if (zI1) {
                q12.f25953i = aB2;
            } else {
                q12.f25952h = aB2;
            }
        }
        aVar2.e(this.f26453n, 0, 0, 0.0f);
        return kotlin.G0.f406611a;
    }
}
