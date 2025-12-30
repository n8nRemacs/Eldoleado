package AN0;

import Y61.k;
import com.avito.android.publish.drafts.LocalPublishState;
import com.avito.android.virtual_deal_room.client_edit.analytics.FieldChange;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Q;
import kotlin.collections.C42756l;
import kotlin.collections.P0;

/* compiled from: UserDataChangeEvent.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAN0/a;", "Lcom/avito/android/analytics/provider/clickstream/a;", "_avito_virtual-deal-room_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class a implements com.avito.android.analytics.provider.clickstream.a {

    /* renamed from: b, reason: collision with root package name */
    @k
    public final Object f319b;

    public a(@k String str, @k FieldChange fieldChange, @k FieldChange fieldChange2, @k FieldChange fieldChange3, @k FieldChange fieldChange4, @k FieldChange fieldChange5) {
        this.f319b = P0.g(new Q("from_page", str), new Q(LocalPublishState.FIELDS, C42756l.P(new String[]{"surname-".concat(fieldChange2.f326137b), "name-".concat(fieldChange.f326137b), "patronymic-".concat(fieldChange3.f326137b), "phone-".concat(fieldChange4.f326137b), "email-".concat(fieldChange5.f326137b)}, ", ", "[", "]", null, 56)));
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getEventId */
    public final int getF75431b() {
        return 12964;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object, java.util.Map<java.lang.String, java.lang.Object>] */
    @Override // com.avito.android.analytics.provider.clickstream.a
    @k
    public final Map<String, Object> getParams() {
        return this.f319b;
    }

    @Override // com.avito.android.analytics.provider.clickstream.a
    /* renamed from: getVersion */
    public final int getF75432c() {
        return 2;
    }
}
