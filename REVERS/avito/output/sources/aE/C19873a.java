package ae;

import Y61.k;
import Y61.l;
import com.avito.android.analytics.provider.clickstream.ParametrizedClickStreamEvent;
import com.avito.android.authorization.analytics.CodeConfirmationSource;
import com.avito.android.util.C35755b0;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Q;
import kotlin.collections.P0;

/* compiled from: CodeConfirmationOpenedEvent.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lae/a;", "Lcom/avito/android/analytics/provider/clickstream/a;", "_avito_authorization_util"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ae.a, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C19873a implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ParametrizedClickStreamEvent f21104b;

    public C19873a(@l String str, @k CodeConfirmationSource codeConfirmationSource) {
        this.f21104b = new ParametrizedClickStreamEvent(2077, 3, C35755b0.c(P0.g(new Q("phone", str), new Q("s", codeConfirmationSource.f92770b))), null, 8, null);
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getEventId */
    public final int getF328428b() {
        return this.f21104b.f90247b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    @k
    public final Map<String, Object> getParams() {
        return this.f21104b.f90249d;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getVersion */
    public final int getF328429c() {
        return this.f21104b.f90248c;
    }
}
