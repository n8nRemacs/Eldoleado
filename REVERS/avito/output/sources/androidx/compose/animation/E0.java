package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.internal.C22096n;
import kotlin.Metadata;

/* compiled from: Crossfade.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class E0 extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ C20288i1<Object> f25866l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.v f25867m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.animation.core.V<Float> f25868n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ Y41.l<Object, Object> f25869o;

    /* renamed from: p, reason: collision with root package name */
    public final /* synthetic */ C22096n f25870p;

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ int f25871q;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public E0(C20288i1 c20288i1, androidx.compose.ui.v vVar, androidx.compose.animation.core.V v12, Y41.l lVar, C22096n c22096n, int i12) {
        super(2);
        this.f25866l = c20288i1;
        this.f25867m = vVar;
        this.f25868n = v12;
        this.f25869o = lVar;
        this.f25870p = c22096n;
        this.f25871q = i12;
    }

    @Override // Y41.p
    public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
        num.intValue();
        int iA2 = C22066f2.a(this.f25871q | 1);
        Y41.l<Object, Object> lVar = this.f25869o;
        C22096n c22096n = this.f25870p;
        F0.a(this.f25866l, this.f25867m, this.f25868n, lVar, c22096n, a12, iA2);
        return kotlin.G0.f406611a;
    }
}
