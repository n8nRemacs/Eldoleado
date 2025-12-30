package androidx.compose.animation;

import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.internal.InterfaceC22085c;
import kotlin.Metadata;

/* compiled from: AnimatedContent.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20351h extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ Object f26457l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.v f26458m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ Y41.l<InterfaceC20401y<Object>, C20396w0> f26459n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.i f26460o;

    /* renamed from: p, reason: collision with root package name */
    public final /* synthetic */ String f26461p;

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ Y41.l<Object, Object> f26462q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ InterfaceC22085c f26463r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ int f26464s;

    /* renamed from: t, reason: collision with root package name */
    public final /* synthetic */ int f26465t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20351h(Object obj, androidx.compose.ui.v vVar, Y41.l lVar, androidx.compose.ui.i iVar, String str, Y41.l lVar2, InterfaceC22085c interfaceC22085c, int i12, int i13) {
        super(2);
        this.f26457l = obj;
        this.f26458m = vVar;
        this.f26459n = lVar;
        this.f26460o = iVar;
        this.f26461p = str;
        this.f26462q = lVar2;
        this.f26463r = interfaceC22085c;
        this.f26464s = i12;
        this.f26465t = i13;
    }

    @Override // Y41.p
    public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
        num.intValue();
        int iA2 = C22066f2.a(this.f26464s | 1);
        Y41.l<Object, Object> lVar = this.f26462q;
        String str = this.f26461p;
        C20389u.b(this.f26457l, this.f26458m, this.f26459n, this.f26460o, str, lVar, this.f26463r, a12, iA2, this.f26465t);
        return kotlin.G0.f406611a;
    }
}
