package androidx.compose.animation;

import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.internal.C22096n;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: AnimatedVisibility.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class Y extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.foundation.layout.K f25990l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ boolean f25991m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ v.a f25992n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ AbstractC20359j1 f25993o;

    /* renamed from: p, reason: collision with root package name */
    public final /* synthetic */ AbstractC20365l1 f25994p;

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ String f25995q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ C22096n f25996r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ int f25997s;

    /* renamed from: t, reason: collision with root package name */
    public final /* synthetic */ int f25998t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Y(androidx.compose.foundation.layout.K k12, boolean z12, v.a aVar, AbstractC20359j1 abstractC20359j1, AbstractC20365l1 abstractC20365l1, String str, C22096n c22096n, int i12, int i13) {
        super(2);
        this.f25990l = k12;
        this.f25991m = z12;
        this.f25992n = aVar;
        this.f25993o = abstractC20359j1;
        this.f25994p = abstractC20365l1;
        this.f25995q = str;
        this.f25996r = c22096n;
        this.f25997s = i12;
        this.f25998t = i13;
    }

    @Override // Y41.p
    public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
        num.intValue();
        int iA2 = C22066f2.a(this.f25997s | 1);
        C22096n c22096n = this.f25996r;
        AbstractC20359j1 abstractC20359j1 = this.f25993o;
        AbstractC20365l1 abstractC20365l1 = this.f25994p;
        C20340d0.b(this.f25990l, this.f25991m, this.f25992n, abstractC20359j1, abstractC20365l1, this.f25995q, c22096n, a12, iA2, this.f25998t);
        return kotlin.G0.f406611a;
    }
}
