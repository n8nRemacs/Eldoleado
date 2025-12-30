package Ad;

import com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;

/* compiled from: InstalledAppsTrackEvent.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAd/a;", "Lcom/avito/android/analytics/provider/clickstream/a;", "_avito_apps-tracking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ad.a, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C13015a implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ParametrizedClickStreamEvent f518b;

    public C13015a(@Y61.k List<String> list) {
        this.f518b = new ParametrizedClickStreamEvent(5182, 0, Collections.singletonMap("apps_list", list), null, 8, null);
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getEventId */
    public final int getF441796b() {
        return this.f518b.f90247b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    @Y61.k
    public final Map<String, Object> getParams() {
        return this.f518b.f90249d;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getVersion */
    public final int getF441797c() {
        return this.f518b.f90248c;
    }
}
