package Ah;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.foundation.H;
import androidx.compose.runtime.internal.P;
import com.akita.compose.component.accordion.s;
import com.avito.android.analytics.screens.mvi.q;
import com.avito.android.bbl.screens.configure.v2.ui.items.cards.C28729a;
import com.avito.android.remote.model.text.AttributedText;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.C42784z0;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;
import wZ.C49582f;
import wZ.g;
import wZ.i;

/* compiled from: BblConfigureV2State.kt */
@P
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0080\b\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"LAh/c;", "Lcom/avito/android/analytics/screens/mvi/q;", "a", "_avito_bbl_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final /* data */ class c extends q {

    /* renamed from: n, reason: collision with root package name */
    @k
    public static final a f570n = new a(null);

    /* renamed from: o, reason: collision with root package name */
    @k
    public static final c f571o = new c(null, null, null, null, null, null, null, C42784z0.f406748b, null, false, false, null);

    /* renamed from: b, reason: collision with root package name */
    @l
    public final i f572b;

    /* renamed from: c, reason: collision with root package name */
    @l
    public final g f573c;

    /* renamed from: d, reason: collision with root package name */
    @l
    public final C49582f f574d;

    /* renamed from: e, reason: collision with root package name */
    @l
    public final C49582f f575e;

    /* renamed from: f, reason: collision with root package name */
    @l
    public final Long f576f;

    /* renamed from: g, reason: collision with root package name */
    @l
    public final wZ.l f577g;

    /* renamed from: h, reason: collision with root package name */
    @l
    public final AttributedText f578h;

    /* renamed from: i, reason: collision with root package name */
    @k
    public final List<C28729a> f579i;

    /* renamed from: j, reason: collision with root package name */
    @l
    public final Throwable f580j;

    /* renamed from: k, reason: collision with root package name */
    public final boolean f581k;

    /* renamed from: l, reason: collision with root package name */
    public final boolean f582l;

    /* renamed from: m, reason: collision with root package name */
    @l
    public final Integer f583m;

    /* compiled from: BblConfigureV2State.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAh/c$a;", "", "<init>", "()V", "_avito_bbl_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public c(@l i iVar, @l g gVar, @l C49582f c49582f, @l C49582f c49582f2, @l Long l12, @l wZ.l lVar, @l AttributedText attributedText, @k List<C28729a> list, @l Throwable th2, boolean z12, boolean z13, @l Integer num) {
        this.f572b = iVar;
        this.f573c = gVar;
        this.f574d = c49582f;
        this.f575e = c49582f2;
        this.f576f = l12;
        this.f577g = lVar;
        this.f578h = attributedText;
        this.f579i = list;
        this.f580j = th2;
        this.f581k = z12;
        this.f582l = z13;
        this.f583m = num;
    }

    public static c a(c cVar, C49582f c49582f, Long l12, ArrayList arrayList, Throwable th2, boolean z12, boolean z13, Integer num, int i12) {
        i iVar = cVar.f572b;
        g gVar = cVar.f573c;
        C49582f c49582f2 = cVar.f574d;
        C49582f c49582f3 = (i12 & 8) != 0 ? cVar.f575e : c49582f;
        Long l13 = (i12 & 16) != 0 ? cVar.f576f : l12;
        wZ.l lVar = cVar.f577g;
        AttributedText attributedText = cVar.f578h;
        List<C28729a> list = (i12 & 128) != 0 ? cVar.f579i : arrayList;
        Throwable th3 = (i12 & 256) != 0 ? cVar.f580j : th2;
        boolean z14 = (i12 & 512) != 0 ? cVar.f581k : z12;
        boolean z15 = (i12 & 1024) != 0 ? cVar.f582l : z13;
        Integer num2 = (i12 & 2048) != 0 ? cVar.f583m : num;
        cVar.getClass();
        return new c(iVar, gVar, c49582f2, c49582f3, l13, lVar, attributedText, list, th3, z14, z15, num2);
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return L.f(this.f572b, cVar.f572b) && L.f(this.f573c, cVar.f573c) && L.f(this.f574d, cVar.f574d) && L.f(this.f575e, cVar.f575e) && L.f(this.f576f, cVar.f576f) && L.f(this.f577g, cVar.f577g) && L.f(this.f578h, cVar.f578h) && L.f(this.f579i, cVar.f579i) && L.f(this.f580j, cVar.f580j) && this.f581k == cVar.f581k && this.f582l == cVar.f582l && L.f(this.f583m, cVar.f583m);
    }

    public final int hashCode() {
        i iVar = this.f572b;
        int iHashCode = (iVar == null ? 0 : iVar.hashCode()) * 31;
        g gVar = this.f573c;
        int iHashCode2 = (iHashCode + (gVar == null ? 0 : gVar.hashCode())) * 31;
        C49582f c49582f = this.f574d;
        int iHashCode3 = (iHashCode2 + (c49582f == null ? 0 : c49582f.hashCode())) * 31;
        C49582f c49582f2 = this.f575e;
        int iHashCode4 = (iHashCode3 + (c49582f2 == null ? 0 : c49582f2.hashCode())) * 31;
        Long l12 = this.f576f;
        int iHashCode5 = (iHashCode4 + (l12 == null ? 0 : l12.hashCode())) * 31;
        wZ.l lVar = this.f577g;
        int iHashCode6 = (iHashCode5 + (lVar == null ? 0 : lVar.hashCode())) * 31;
        AttributedText attributedText = this.f578h;
        int iE2 = H.e((iHashCode6 + (attributedText == null ? 0 : attributedText.hashCode())) * 31, 31, this.f579i);
        Throwable th2 = this.f580j;
        int i12 = r.i(r.i((iE2 + (th2 == null ? 0 : th2.hashCode())) * 31, 31, this.f581k), 31, this.f582l);
        Integer num = this.f583m;
        return i12 + (num != null ? num.hashCode() : 0);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("BblConfigureV2State(navbar=");
        sb2.append(this.f572b);
        sb2.append(", footer=");
        sb2.append(this.f573c);
        sb2.append(", initialContact=");
        sb2.append(this.f574d);
        sb2.append(", currentContact=");
        sb2.append(this.f575e);
        sb2.append(", configId=");
        sb2.append(this.f576f);
        sb2.append(", image=");
        sb2.append(this.f577g);
        sb2.append(", title=");
        sb2.append(this.f578h);
        sb2.append(", configs=");
        sb2.append(this.f579i);
        sb2.append(", error=");
        sb2.append(this.f580j);
        sb2.append(", isLoading=");
        sb2.append(this.f581k);
        sb2.append(", isOnScreenLoading=");
        sb2.append(this.f582l);
        sb2.append(", clickedButtonId=");
        return s.j(sb2, this.f583m, ')');
    }
}
