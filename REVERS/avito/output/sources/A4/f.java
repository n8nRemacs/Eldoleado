package a4;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent;
import com.avito.android.util.C35755b0;
import java.util.ArrayList;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Q;
import kotlin.collections.P0;

/* compiled from: ShowIcebreakerEditorScreenEvent.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"La4/f;", "Lcom/avito/android/analytics/provider/clickstream/a;", "_avito_advert-details_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class f implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ParametrizedClickStreamEvent f20732b;

    public f(@k String str, @l String str2, @l String str3, @k ArrayList arrayList) {
        this.f20732b = new ParametrizedClickStreamEvent(16267, 1, C35755b0.c(P0.j(new Q("iid", str), new Q("cid", str2 == null ? "" : str2), new Q("mcid", str3 == null ? "" : str3), new Q("ice_breakers_ids", arrayList), new Q("s", "item"))), null, 8, null);
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getEventId */
    public final int getF224266b() {
        return this.f20732b.f90247b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    @k
    public final Map<String, Object> getParams() {
        return this.f20732b.f90249d;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getVersion */
    public final int getF224267c() {
        return this.f20732b.f90248c;
    }
}
