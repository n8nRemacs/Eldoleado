package a4;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import com.avito.android.advert.icebreakerseditor.analytics.IcebreakersEditorAnalyticsData;
import com.avito.android.analytics.InterfaceC28373a;
import java.util.ArrayList;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: IcebreakersEditorEventTracker.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"La4/c;", "La4/b;", "_avito_advert-details_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class c implements b {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final InterfaceC28373a f20727a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final IcebreakersEditorAnalyticsData f20728b;

    @Inject
    public c(@k InterfaceC28373a interfaceC28373a, @k IcebreakersEditorAnalyticsData icebreakersEditorAnalyticsData) {
        this.f20727a = interfaceC28373a;
        this.f20728b = icebreakersEditorAnalyticsData;
    }

    @Override // a4.b
    public final void a(@k ArrayList arrayList) {
        IcebreakersEditorAnalyticsData icebreakersEditorAnalyticsData = this.f20728b;
        this.f20727a.c(new f(icebreakersEditorAnalyticsData.f71192b, icebreakersEditorAnalyticsData.f71193c, icebreakersEditorAnalyticsData.f71194d, arrayList));
    }

    @Override // a4.b
    public final void b(@l Long l12, @k String str, @k String str2) {
        IcebreakersEditorAnalyticsData icebreakersEditorAnalyticsData = this.f20728b;
        this.f20727a.c(new e(l12, icebreakersEditorAnalyticsData.f71192b, icebreakersEditorAnalyticsData.f71193c, icebreakersEditorAnalyticsData.f71194d, str, str2));
    }

    @Override // a4.b
    public final void c(@l Long l12, @k String str, @k String str2) {
        IcebreakersEditorAnalyticsData icebreakersEditorAnalyticsData = this.f20728b;
        this.f20727a.c(new C19679a(l12, icebreakersEditorAnalyticsData.f71192b, icebreakersEditorAnalyticsData.f71193c, icebreakersEditorAnalyticsData.f71194d, str, str2));
    }
}
