package aG0;

import RF0.h;
import RF0.m;
import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import com.avito.android.analytics.screens.mvi.q;
import com.avito.android.remote.model.text.AttributedText;
import com.avito.android.trx_promo_public.data.TrxPromoStatus;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: TrxPromoStatusState.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0080\b\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"LaG0/c;", "Lcom/avito/android/analytics/screens/mvi/q;", "a", "_avito_trx-promo_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aG0.c, reason: case insensitive filesystem */
/* loaded from: classes4.dex */
public final /* data */ class C19772c extends q {

    /* renamed from: j, reason: collision with root package name */
    @k
    public static final a f20848j = new a(null);

    /* renamed from: k, reason: collision with root package name */
    @k
    public static final C19772c f20849k = new C19772c(false, null, null, null, null, null, null, true);

    /* renamed from: b, reason: collision with root package name */
    public final boolean f20850b;

    /* renamed from: c, reason: collision with root package name */
    @l
    public final TrxPromoStatus f20851c;

    /* renamed from: d, reason: collision with root package name */
    @l
    public final m f20852d;

    /* renamed from: e, reason: collision with root package name */
    @l
    public final AttributedText f20853e;

    /* renamed from: f, reason: collision with root package name */
    @l
    public final AttributedText f20854f;

    /* renamed from: g, reason: collision with root package name */
    @l
    public final h f20855g;

    /* renamed from: h, reason: collision with root package name */
    @l
    public final String f20856h;

    /* renamed from: i, reason: collision with root package name */
    public final boolean f20857i;

    /* compiled from: TrxPromoStatusState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaG0/c$a;", "", "<init>", "()V", "_avito_trx-promo_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aG0.c$a */
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public C19772c(boolean z12, @l TrxPromoStatus trxPromoStatus, @l m mVar, @l AttributedText attributedText, @l AttributedText attributedText2, @l h hVar, @l String str, boolean z13) {
        this.f20850b = z12;
        this.f20851c = trxPromoStatus;
        this.f20852d = mVar;
        this.f20853e = attributedText;
        this.f20854f = attributedText2;
        this.f20855g = hVar;
        this.f20856h = str;
        this.f20857i = z13;
    }

    public static C19772c a(C19772c c19772c, boolean z12, TrxPromoStatus trxPromoStatus, m mVar, AttributedText attributedText, AttributedText attributedText2, h hVar, String str, boolean z13, int i12) {
        boolean z14 = (i12 & 1) != 0 ? c19772c.f20850b : z12;
        TrxPromoStatus trxPromoStatus2 = (i12 & 2) != 0 ? c19772c.f20851c : trxPromoStatus;
        m mVar2 = (i12 & 4) != 0 ? c19772c.f20852d : mVar;
        AttributedText attributedText3 = (i12 & 8) != 0 ? c19772c.f20853e : attributedText;
        AttributedText attributedText4 = (i12 & 16) != 0 ? c19772c.f20854f : attributedText2;
        h hVar2 = (i12 & 32) != 0 ? c19772c.f20855g : hVar;
        String str2 = (i12 & 64) != 0 ? c19772c.f20856h : str;
        boolean z15 = (i12 & 128) != 0 ? c19772c.f20857i : z13;
        c19772c.getClass();
        return new C19772c(z14, trxPromoStatus2, mVar2, attributedText3, attributedText4, hVar2, str2, z15);
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C19772c)) {
            return false;
        }
        C19772c c19772c = (C19772c) obj;
        return this.f20850b == c19772c.f20850b && this.f20851c == c19772c.f20851c && L.f(this.f20852d, c19772c.f20852d) && L.f(this.f20853e, c19772c.f20853e) && L.f(this.f20854f, c19772c.f20854f) && L.f(this.f20855g, c19772c.f20855g) && L.f(this.f20856h, c19772c.f20856h) && this.f20857i == c19772c.f20857i;
    }

    public final int hashCode() {
        int iHashCode = Boolean.hashCode(this.f20850b) * 31;
        TrxPromoStatus trxPromoStatus = this.f20851c;
        int iHashCode2 = (iHashCode + (trxPromoStatus == null ? 0 : trxPromoStatus.hashCode())) * 31;
        m mVar = this.f20852d;
        int iHashCode3 = (iHashCode2 + (mVar == null ? 0 : mVar.hashCode())) * 31;
        AttributedText attributedText = this.f20853e;
        int iHashCode4 = (iHashCode3 + (attributedText == null ? 0 : attributedText.hashCode())) * 31;
        AttributedText attributedText2 = this.f20854f;
        int iHashCode5 = (iHashCode4 + (attributedText2 == null ? 0 : attributedText2.hashCode())) * 31;
        h hVar = this.f20855g;
        int iHashCode6 = (iHashCode5 + (hVar == null ? 0 : hVar.hashCode())) * 31;
        String str = this.f20856h;
        return Boolean.hashCode(this.f20857i) + ((iHashCode6 + (str != null ? str.hashCode() : 0)) * 31);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("TrxPromoStatusState(closable=");
        sb2.append(this.f20850b);
        sb2.append(", status=");
        sb2.append(this.f20851c);
        sb2.append(", image=");
        sb2.append(this.f20852d);
        sb2.append(", title=");
        sb2.append(this.f20853e);
        sb2.append(", subtitle=");
        sb2.append(this.f20854f);
        sb2.append(", action=");
        sb2.append(this.f20855g);
        sb2.append(", error=");
        sb2.append(this.f20856h);
        sb2.append(", isLoading=");
        return r.x(sb2, this.f20857i, ')');
    }
}
