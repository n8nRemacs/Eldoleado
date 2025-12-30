package ae0;

import Y61.k;
import Y61.l;
import com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent;
import com.avito.android.util.C35755b0;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Q;
import kotlin.collections.P0;
import yc.C50213a;

/* compiled from: PublishDraftAcceptedEvent.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lae0/b;", "Lcom/avito/android/analytics/provider/clickstream/a;", "_avito_publish-drafts_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ae0.b, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C19877b implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ParametrizedClickStreamEvent f21107b;

    public C19877b(@k C50213a c50213a, @l Integer num, @l String str, @k String str2) {
        this.f21107b = new ParametrizedClickStreamEvent(2591, 1, C35755b0.c(P0.g(new Q("item_draft_id", str2), c50213a.c(), new Q("cid", num), new Q("infm_raw_params", str))), null, 8, null);
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getEventId */
    public final int getF327516b() {
        return this.f21107b.f90247b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    @k
    public final Map<String, Object> getParams() {
        return this.f21107b.f90249d;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getVersion */
    public final int getF327517c() {
        return this.f21107b.f90248c;
    }
}
