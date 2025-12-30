package androidx.compose.animation;

import androidx.compose.runtime.C22066f2;
import androidx.compose.runtime.internal.C22096n;
import kotlin.Metadata;

/* compiled from: AnimatedVisibility.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class U extends kotlin.jvm.internal.N implements Y41.p<androidx.compose.runtime.A, Integer, kotlin.G0> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ boolean f25967l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.v f25968m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ AbstractC20359j1 f25969n;

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ AbstractC20365l1 f25970o;

    /* renamed from: p, reason: collision with root package name */
    public final /* synthetic */ String f25971p;

    /* renamed from: q, reason: collision with root package name */
    public final /* synthetic */ C22096n f25972q;

    /* renamed from: r, reason: collision with root package name */
    public final /* synthetic */ int f25973r;

    /* renamed from: s, reason: collision with root package name */
    public final /* synthetic */ int f25974s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public U(boolean z12, androidx.compose.ui.v vVar, AbstractC20359j1 abstractC20359j1, AbstractC20365l1 abstractC20365l1, String str, C22096n c22096n, int i12, int i13) {
        super(2);
        this.f25967l = z12;
        this.f25968m = vVar;
        this.f25969n = abstractC20359j1;
        this.f25970o = abstractC20365l1;
        this.f25971p = str;
        this.f25972q = c22096n;
        this.f25973r = i12;
        this.f25974s = i13;
    }

    @Override // Y41.p
    public final kotlin.G0 invoke(androidx.compose.runtime.A a12, Integer num) {
        num.intValue();
        int iA2 = C22066f2.a(this.f25973r | 1);
        C22096n c22096n = this.f25972q;
        AbstractC20359j1 abstractC20359j1 = this.f25969n;
        AbstractC20365l1 abstractC20365l1 = this.f25970o;
        C20340d0.d(this.f25967l, this.f25968m, abstractC20359j1, abstractC20365l1, this.f25971p, c22096n, a12, iA2, this.f25974s);
        return kotlin.G0.f406611a;
    }
}
