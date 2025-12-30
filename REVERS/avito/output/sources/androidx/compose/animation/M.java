package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.runtime.C22066f2;
import kotlin.Metadata;

/* compiled from: AnimatedVisibility.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class M extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C20288i1<Object> f25916l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ Y41.l<Object, Boolean> f25917m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.v f25918n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ AbstractC20359j1 f25919o;

    /* renamed from: p, reason: collision with root package name */
    public final /* synthetic */ AbstractC20365l1 f25920p;

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ Y41.p<EnterExitState, EnterExitState, Boolean> f25921q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ Y41.q<InterfaceC20343e0, androidx.compose.runtime.A, Integer, kotlin.G0> f25922r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ int f25923s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public M(C20288i1 c20288i1, Y41.l lVar, androidx.compose.ui.v vVar, AbstractC20359j1 abstractC20359j1, AbstractC20365l1 abstractC20365l1, Y41.p pVar, Y41.q qVar, int i12) {
        super(2);
        this.f25916l = c20288i1;
        this.f25917m = lVar;
        this.f25918n = vVar;
        this.f25919o = abstractC20359j1;
        this.f25920p = abstractC20365l1;
        this.f25921q = pVar;
        this.f25922r = qVar;
        this.f25923s = i12;
    }

    @Override // Y41.p
    public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
        num.intValue();
        int iA2 = C22066f2.a(this.f25923s | 1);
        AbstractC20365l1 abstractC20365l1 = this.f25920p;
        Y41.p<EnterExitState, EnterExitState, Boolean> pVar = this.f25921q;
        C20340d0.a(this.f25916l, this.f25917m, this.f25918n, this.f25919o, abstractC20365l1, pVar, this.f25922r, a12, iA2);
        return kotlin.G0.f406611a;
    }
}
