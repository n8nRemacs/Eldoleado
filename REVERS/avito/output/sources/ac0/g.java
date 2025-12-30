package AC0;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.runtime.internal.P;
import kotlin.Metadata;

/* compiled from: CptInfoCardContentTextItem.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAC0/g;", "", "_avito_tariff-cpt_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class g {

    /* renamed from: a, reason: collision with root package name */
    public final int f212a;

    /* renamed from: b, reason: collision with root package name */
    public final int f213b;

    /* renamed from: c, reason: collision with root package name */
    public final int f214c;

    /* renamed from: d, reason: collision with root package name */
    public final int f215d;

    public g(int i12, int i13, int i14, int i15) {
        this.f212a = i12;
        this.f213b = i13;
        this.f214c = i14;
        this.f215d = i15;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof g)) {
            return false;
        }
        g gVar = (g) obj;
        return this.f212a == gVar.f212a && this.f213b == gVar.f213b && this.f214c == gVar.f214c && this.f215d == gVar.f215d;
    }

    public final int hashCode() {
        return Integer.hashCode(this.f215d) + r.e(this.f214c, r.e(this.f213b, Integer.hashCode(this.f212a) * 31, 31), 31);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("CptInfoItemIconPadding(left=");
        sb2.append(this.f212a);
        sb2.append(", right=");
        sb2.append(this.f213b);
        sb2.append(", top=");
        sb2.append(this.f214c);
        sb2.append(", bottom=");
        return r.t(sb2, this.f215d, ')');
    }
}
