package Ai0;

import Y61.k;
import java.util.Collections;
import java.util.Map;
import kotlin.Metadata;

/* compiled from: RecallMeClickEvent.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAi0/a;", "Lcom/avito/android/analytics/provider/clickstream/a;", "_avito-discouraged_avito-libs_recall-me-core"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ai0.a, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C13040a implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    @k
    public final Map<String, Object> f591b;

    public C13040a(@k String str) {
        this.f591b = Collections.singletonMap("iid", str);
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    public final int getEventId() {
        return 6607;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    @k
    public final Map<String, Object> getParams() {
        return this.f591b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    public final int getVersion() {
        return 2;
    }
}
