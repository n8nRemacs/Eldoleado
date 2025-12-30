package Ad;

import com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Q;
import kotlin.collections.P0;

/* compiled from: InstalledBankAppsTrackEvent.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAd/j;", "Lcom/avito/android/analytics/provider/clickstream/a;", "_avito_apps-tracking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class j implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ParametrizedClickStreamEvent f535b;

    public j(@Y61.l String str, @Y61.k List<String> list) {
        this.f535b = new ParametrizedClickStreamEvent(9653, 1, P0.g(new Q("uid", str == null ? "" : str), new Q("apps_list", list)), null, 8, null);
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getEventId */
    public final int getF395375b() {
        return this.f535b.f90247b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    @Y61.k
    public final Map<String, Object> getParams() {
        return this.f535b.f90249d;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getVersion */
    public final int getF395376c() {
        return this.f535b.f90248c;
    }
}
