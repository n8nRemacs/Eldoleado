package androidx.compose.animation.core;

import kotlin.Metadata;
import kotlin.jvm.internal.l0;

/* compiled from: SuspendAnimation.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\b\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0003H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"T", "Landroidx/compose/animation/core/x;", "V", "", "it", "Lkotlin/G0;", "invoke", "(J)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* renamed from: androidx.compose.animation.core.a1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20264a1 extends kotlin.jvm.internal.N implements Y41.l<Long, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ l0.h<C20304o<Object, Object>> f26192l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ Object f26193m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20286i<Object, Object> f26194n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ Object f26195o;

    /* renamed from: p, reason: collision with root package name */
    public final /* synthetic */ r<Object, Object> f26196p;

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ float f26197q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ Y41.l<C20304o<Object, Object>, kotlin.G0> f26198r;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public C20264a1(l0.h<C20304o<Object, Object>> hVar, Object obj, InterfaceC20286i<Object, Object> interfaceC20286i, Object obj2, r<Object, Object> rVar, float f12, Y41.l<? super C20304o<Object, Object>, kotlin.G0> lVar) {
        super(1);
        this.f26192l = hVar;
        this.f26193m = obj;
        this.f26194n = interfaceC20286i;
        this.f26195o = obj2;
        this.f26196p = rVar;
        this.f26197q = f12;
        this.f26198r = lVar;
    }

    /* JADX WARN: Type inference failed for: r13v2, types: [T, androidx.compose.animation.core.o] */
    /* JADX WARN: Type inference failed for: r3v0, types: [androidx.compose.animation.core.x, java.lang.Object] */
    @Override // Y41.l
    public final kotlin.G0 invoke(Long l12) {
        long jLongValue = l12.longValue();
        InterfaceC20286i<Object, Object> interfaceC20286i = this.f26194n;
        H1<Object, V> h1D = interfaceC20286i.d();
        Object objF = interfaceC20286i.f();
        Z0 z02 = new Z0(this.f26196p);
        ?? c20304o = new C20304o(this.f26193m, h1D, this.f26195o, jLongValue, objF, jLongValue, z02);
        C20273d1.h(c20304o, jLongValue, this.f26197q, this.f26194n, this.f26196p, this.f26198r);
        this.f26192l.f406842b = c20304o;
        return kotlin.G0.f406611a;
    }
}
