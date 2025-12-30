package AC0;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.remote.model.text.AttributedText;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: CptInfoCardContentTextItem.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAC0/d;", "", "_avito_tariff-cpt_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class d {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final AttributedText f201a;

    /* renamed from: b, reason: collision with root package name */
    @l
    public final DeepLink f202b;

    /* renamed from: c, reason: collision with root package name */
    @l
    public final e f203c;

    public d(@k AttributedText attributedText, @l DeepLink deepLink, @l e eVar) {
        this.f201a = attributedText;
        this.f202b = deepLink;
        this.f203c = eVar;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return L.f(this.f201a, dVar.f201a) && L.f(this.f202b, dVar.f202b) && L.f(this.f203c, dVar.f203c);
    }

    public final int hashCode() {
        int iHashCode = this.f201a.hashCode() * 31;
        DeepLink deepLink = this.f202b;
        int iHashCode2 = (iHashCode + (deepLink == null ? 0 : deepLink.hashCode())) * 31;
        e eVar = this.f203c;
        return iHashCode2 + (eVar != null ? eVar.hashCode() : 0);
    }

    @k
    public final String toString() {
        return "CptInfoCardContentTextItem(text=" + this.f201a + ", deepLink=" + this.f202b + ", icon=" + this.f203c + ')';
    }
}
