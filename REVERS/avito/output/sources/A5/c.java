package a5;

import Y61.k;
import androidx.compose.runtime.internal.P;
import com.avito.android.advert.item.creditinfo.buzzoola.BuzzoolaCreditBannerItem;
import com.avito.android.advert.item.creditinfo.buzzoola.a;
import com.avito.konveyor.item_visibility_tracker.b;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: CreditBannerImpressionFilter.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"La5/c;", "LWV0/a;", "_avito_advert-details_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class c extends WV0.a {

    /* renamed from: e, reason: collision with root package name */
    @k
    public final com.avito.android.advert_core.analytics.a f20737e;

    /* renamed from: f, reason: collision with root package name */
    @k
    public final com.avito.android.advertising.loaders.event_service.c f20738f;

    @Inject
    public c(@k com.avito.android.advert_core.analytics.a aVar, @k com.avito.android.advertising.loaders.event_service.c cVar) {
        super(true, true, 0.0f, 1000L, 4, null);
        this.f20737e = aVar;
        this.f20738f = cVar;
    }

    @Override // WV0.a
    public final boolean a(@k b.InterfaceC10495b interfaceC10495b) {
        return interfaceC10495b instanceof BuzzoolaCreditBannerItem;
    }

    @Override // WV0.a
    public final void b(int i12, @k b.InterfaceC10495b interfaceC10495b) {
        com.avito.android.advert.item.creditinfo.buzzoola.a aVar = ((BuzzoolaCreditBannerItem) interfaceC10495b).f74920h;
        if (aVar instanceof a.c) {
            a.c cVar = (a.c) aVar;
            this.f20737e.t4(cVar.f74925c, i12);
            this.f20738f.h(cVar.f74924b.f88214h);
        }
    }
}
