package androidx.compose.animation;

import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.internal.C22096n;
import kotlin.Metadata;

/* compiled from: Crossfade.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.x0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20399x0 extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ Object f26609l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.v f26610m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.animation.core.V<Float> f26611n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ String f26612o;

    /* renamed from: p, reason: collision with root package name */
    public final /* synthetic */ C22096n f26613p;

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ int f26614q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ int f26615r;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20399x0(Object obj, androidx.compose.ui.v vVar, androidx.compose.animation.core.V v12, String str, C22096n c22096n, int i12, int i13) {
        super(2);
        this.f26609l = obj;
        this.f26610m = vVar;
        this.f26611n = v12;
        this.f26612o = str;
        this.f26613p = c22096n;
        this.f26614q = i12;
        this.f26615r = i13;
    }

    @Override // Y41.p
    public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
        num.intValue();
        int iA2 = C22066f2.a(this.f26614q | 1);
        C22096n c22096n = this.f26613p;
        androidx.compose.animation.core.V<Float> v12 = this.f26611n;
        F0.b(this.f26609l, this.f26610m, v12, this.f26612o, c22096n, a12, iA2, this.f26615r);
        return kotlin.G0.f406611a;
    }
}
