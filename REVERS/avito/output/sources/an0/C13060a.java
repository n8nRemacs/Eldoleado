package An0;

import Y61.k;
import Y61.l;
import com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent;
import com.avito.android.util.C35755b0;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Q;
import kotlin.collections.P0;
import kotlin.jvm.internal.C42822w;

/* compiled from: OrderCancellationEvent.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAn0/a;", "Lcom/avito/android/analytics/provider/clickstream/a;", "_avito_safedeal_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: An0.a, reason: case insensitive filesystem */
/* loaded from: classes3.dex */
public final class C13060a implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ParametrizedClickStreamEvent f622b;

    public C13060a(@k String str, @k String str2, @k String str3, @k String str4, @l String str5, @l String str6, @l String str7) {
        this.f622b = new ParametrizedClickStreamEvent(2828, 6, C35755b0.c(P0.g(new Q("orderid", str), new Q("uid", str2), new Q("order_cancel_cause", str3), new Q("order_cancel_cause_info", str4), new Q("order_cancel_cause_txt", str7), new Q("order_cancel_cause_info_details_id", str5), new Q("order_cancel_cause_info_details", str6))), null, 8, null);
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getEventId */
    public final int getF328428b() {
        return this.f622b.f90247b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    @k
    public final Map<String, Object> getParams() {
        return this.f622b.f90249d;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getVersion */
    public final int getF328429c() {
        return this.f622b.f90248c;
    }

    public /* synthetic */ C13060a(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i12, C42822w c42822w) {
        this(str, str2, str3, str4, str5, str6, (i12 & 64) != 0 ? null : str7);
    }
}
