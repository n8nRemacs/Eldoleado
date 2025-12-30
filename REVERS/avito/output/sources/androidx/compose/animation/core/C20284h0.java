package androidx.compose.animation.core;

import androidx.compose.animation.core.C20281g0;
import androidx.compose.runtime.C22082i3;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: InfiniteTransition.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\b\b\u0001\u0010\u0002*\u00020\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"T", "Landroidx/compose/animation/core/x;", "V", "Lkotlin/G0;", "invoke", "()V", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* renamed from: androidx.compose.animation.core.h0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20284h0 extends kotlin.jvm.internal.N implements Y41.a<kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ Number f26302l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ C20281g0.a<Object, Object> f26303m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ Number f26304n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ C20278f0<Object> f26305o;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20284h0(Number number, C20281g0.a aVar, Number number2, C20278f0 c20278f0) {
        super(0);
        this.f26302l = number;
        this.f26303m = aVar;
        this.f26304n = number2;
        this.f26305o = c20278f0;
    }

    @Override // Y41.a
    public final kotlin.G0 invoke() {
        C20281g0.a<Object, Object> aVar = this.f26303m;
        Number number = aVar.f26277b;
        Number number2 = this.f26302l;
        boolean zEquals = number2.equals(number);
        Number number3 = this.f26304n;
        if (!zEquals || !number3.equals(aVar.f26278c)) {
            aVar.f26277b = number2;
            aVar.f26278c = number3;
            C20278f0<Object> c20278f0 = this.f26305o;
            aVar.f26281f = c20278f0;
            aVar.f26282g = new C20285h1<>(c20278f0, aVar.f26279d, number2, number3, (AbstractC20330x) null, 16, (C42822w) null);
            ((C22082i3) C20281g0.this.f26274b).setValue(Boolean.TRUE);
            aVar.f26283h = false;
            aVar.f26284i = true;
        }
        return kotlin.G0.f406611a;
    }
}
