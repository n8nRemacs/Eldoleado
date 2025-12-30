package androidx.compose.animation.core;

import kotlin.Metadata;
import kotlin.jvm.internal.l0;

/* compiled from: SuspendAnimation.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\b\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0003H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"T", "Landroidx/compose/animation/core/x;", "V", "", "it", "Lkotlin/G0;", "invoke", "(J)V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* renamed from: androidx.compose.animation.core.c1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20270c1 extends kotlin.jvm.internal.N implements Y41.l<Long, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ l0.h<C20304o<Object, Object>> f26238l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ float f26239m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20286i<Object, Object> f26240n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ r<Object, Object> f26241o;

    /* renamed from: p, reason: collision with root package name */
    public final /* synthetic */ Y41.l<C20304o<Object, Object>, kotlin.G0> f26242p;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public C20270c1(l0.h<C20304o<Object, Object>> hVar, float f12, InterfaceC20286i<Object, Object> interfaceC20286i, r<Object, Object> rVar, Y41.l<? super C20304o<Object, Object>, kotlin.G0> lVar) {
        super(1);
        this.f26238l = hVar;
        this.f26239m = f12;
        this.f26240n = interfaceC20286i;
        this.f26241o = rVar;
        this.f26242p = lVar;
    }

    @Override // Y41.l
    public final kotlin.G0 invoke(Long l12) {
        long jLongValue = l12.longValue();
        C20273d1.h(this.f26238l.f406842b, jLongValue, this.f26239m, this.f26240n, this.f26241o, this.f26242p);
        return kotlin.G0.f406611a;
    }
}
