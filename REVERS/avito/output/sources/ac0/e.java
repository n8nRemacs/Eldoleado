package AC0;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: CptInfoCardContentTextItem.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAC0/e;", "", "_avito_tariff-cpt_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class e {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final String f204a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final f f205b;

    /* renamed from: c, reason: collision with root package name */
    @l
    public final g f206c;

    /* renamed from: d, reason: collision with root package name */
    @l
    public final iC0.e f207d;

    /* renamed from: e, reason: collision with root package name */
    @l
    public final DeepLink f208e;

    public e(@k String str, @k f fVar, @l g gVar, @l iC0.e eVar, @l DeepLink deepLink) {
        this.f204a = str;
        this.f205b = fVar;
        this.f206c = gVar;
        this.f207d = eVar;
        this.f208e = deepLink;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return L.f(this.f204a, eVar.f204a) && L.f(this.f205b, eVar.f205b) && L.f(this.f206c, eVar.f206c) && L.f(this.f207d, eVar.f207d) && L.f(this.f208e, eVar.f208e);
    }

    public final int hashCode() {
        int iHashCode = (this.f205b.hashCode() + (this.f204a.hashCode() * 31)) * 31;
        g gVar = this.f206c;
        int iHashCode2 = (iHashCode + (gVar == null ? 0 : gVar.hashCode())) * 31;
        iC0.e eVar = this.f207d;
        int iHashCode3 = (iHashCode2 + (eVar == null ? 0 : eVar.hashCode())) * 31;
        DeepLink deepLink = this.f208e;
        return iHashCode3 + (deepLink != null ? deepLink.hashCode() : 0);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("CptInfoItemIcon(iconName=");
        sb2.append(this.f204a);
        sb2.append(", align=");
        sb2.append(this.f205b);
        sb2.append(", padding=");
        sb2.append(this.f206c);
        sb2.append(", color=");
        sb2.append(this.f207d);
        sb2.append(", deepLink=");
        return com.avito.android.actions_sheet.a.v(sb2, this.f208e, ')');
    }
}
