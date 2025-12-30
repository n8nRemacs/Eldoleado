package ae0;

import Y61.k;
import com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent;
import java.util.Collections;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Q;

/* compiled from: PublishDraftDialogShownEvent.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lae0/c;", "Lcom/avito/android/analytics/provider/clickstream/a;", "_avito_publish-drafts_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ae0.c, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C19878c implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    @k
    public final Q<String, String> f21108b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ ParametrizedClickStreamEvent f21109c;

    public C19878c(@k Q<String, String> q12) {
        this.f21108b = q12;
        this.f21109c = new ParametrizedClickStreamEvent(2589, 0, Collections.singletonMap(q12.f406619b, q12.f406620c), null, 8, null);
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getEventId */
    public final int getF19127b() {
        return this.f21109c.f90247b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    @k
    public final Map<String, Object> getParams() {
        return this.f21109c.f90249d;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getVersion */
    public final int getF11770c() {
        return this.f21109c.f90248c;
    }

    @k
    public final String toString() {
        return "PublishDraftDialogShownEvent(2589) " + this.f21108b;
    }
}
