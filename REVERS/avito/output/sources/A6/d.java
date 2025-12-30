package A6;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.C22026a;
import androidx.compose.runtime.internal.P;
import com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent;
import java.util.Collections;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: RentAgreementClickedEvent.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA6/d;", "Lcom/avito/android/analytics/provider/clickstream/a;", "_avito_advert-details_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final /* data */ class d implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    @k
    public final String f102b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ ParametrizedClickStreamEvent f103c;

    public d(@k String str) {
        this.f102b = str;
        this.f103c = new ParametrizedClickStreamEvent(17508, 1, Collections.singletonMap("iid", str), null, 8, null);
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof d) && L.f(this.f102b, ((d) obj).f102b);
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getEventId */
    public final int getF19677b() {
        return this.f103c.f90247b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    @k
    public final Map<String, Object> getParams() {
        return this.f103c.f90249d;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getVersion */
    public final int getF19678c() {
        return this.f103c.f90248c;
    }

    public final int hashCode() {
        return this.f102b.hashCode();
    }

    @k
    public final String toString() {
        return C22026a.c(new StringBuilder("RentAgreementClickedEvent(advertId="), this.f102b, ')');
    }
}
