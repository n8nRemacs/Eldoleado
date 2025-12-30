package ae0;

import Y61.k;
import com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent;
import com.avito.android.remote.model.category_parameters.PhotoParameter;
import java.util.ArrayList;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Q;
import kotlin.collections.P0;
import yc.C50213a;

/* compiled from: PhotosLocalParamsUpdateEvent.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lae0/a;", "Lcom/avito/android/analytics/provider/clickstream/a;", "_avito_publish-drafts_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ae0.a, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C19876a implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ParametrizedClickStreamEvent f21106b;

    public C19876a(@k C50213a c50213a, int i12, @k ArrayList arrayList) {
        this.f21106b = new ParametrizedClickStreamEvent(6888, 1, P0.l(P0.l(c50213a.a(), new Q(PhotoParameter.TYPE, Integer.valueOf(i12))), new Q("photos_ids", arrayList)), null, 8, null);
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getEventId */
    public final int getF328428b() {
        return this.f21106b.f90247b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    @k
    public final Map<String, Object> getParams() {
        return this.f21106b.f90249d;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getVersion */
    public final int getF328429c() {
        return this.f21106b.f90248c;
    }
}
