package androidx.compose.animation;

import androidx.compose.foundation.layout.InterfaceC20572g2;
import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.internal.C22096n;
import androidx.compose.ui.v;
import kotlin.Metadata;

/* compiled from: AnimatedVisibility.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class W extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ InterfaceC20572g2 f25978l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ boolean f25979m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ v.a f25980n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ AbstractC20359j1 f25981o;

    /* renamed from: p, reason: collision with root package name */
    public final /* synthetic */ AbstractC20365l1 f25982p;

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ String f25983q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ C22096n f25984r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ int f25985s;

    /* renamed from: t, reason: collision with root package name */
    public final /* synthetic */ int f25986t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public W(InterfaceC20572g2 interfaceC20572g2, boolean z12, v.a aVar, AbstractC20359j1 abstractC20359j1, AbstractC20365l1 abstractC20365l1, String str, C22096n c22096n, int i12, int i13) {
        super(2);
        this.f25978l = interfaceC20572g2;
        this.f25979m = z12;
        this.f25980n = aVar;
        this.f25981o = abstractC20359j1;
        this.f25982p = abstractC20365l1;
        this.f25983q = str;
        this.f25984r = c22096n;
        this.f25985s = i12;
        this.f25986t = i13;
    }

    @Override // Y41.p
    public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
        num.intValue();
        int iA2 = C22066f2.a(this.f25985s | 1);
        C22096n c22096n = this.f25984r;
        AbstractC20359j1 abstractC20359j1 = this.f25981o;
        AbstractC20365l1 abstractC20365l1 = this.f25982p;
        C20340d0.c(this.f25978l, this.f25979m, this.f25980n, abstractC20359j1, abstractC20365l1, this.f25983q, c22096n, a12, iA2, this.f25986t);
        return kotlin.G0.f406611a;
    }
}
