package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.internal.InterfaceC22085c;
import androidx.compose.ui.InterfaceC22215f;
import kotlin.Metadata;

/* compiled from: AnimatedContent.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.s, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20383s extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C20288i1<Object> f26581l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.v f26582m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ Y41.l<InterfaceC20401y<Object>, C20396w0> f26583n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ InterfaceC22215f f26584o;

    /* renamed from: p, reason: collision with root package name */
    public final /* synthetic */ Y41.l<Object, Object> f26585p;

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ InterfaceC22085c f26586q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ int f26587r;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20383s(C20288i1 c20288i1, androidx.compose.ui.v vVar, Y41.l lVar, InterfaceC22215f interfaceC22215f, Y41.l lVar2, InterfaceC22085c interfaceC22085c, int i12) {
        super(2);
        this.f26581l = c20288i1;
        this.f26582m = vVar;
        this.f26583n = lVar;
        this.f26584o = interfaceC22215f;
        this.f26585p = lVar2;
        this.f26586q = interfaceC22085c;
        this.f26587r = i12;
    }

    @Override // Y41.p
    public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
        num.intValue();
        int iA2 = C22066f2.a(this.f26587r | 1);
        C20288i1<Object> c20288i1 = this.f26581l;
        Y41.l<InterfaceC20401y<Object>, C20396w0> lVar = this.f26583n;
        InterfaceC22215f interfaceC22215f = this.f26584o;
        C20389u.a(c20288i1, this.f26582m, lVar, interfaceC22215f, this.f26585p, this.f26586q, a12, iA2);
        return kotlin.G0.f406611a;
    }
}
