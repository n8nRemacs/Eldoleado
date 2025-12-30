package Ab;

import Y61.k;
import com.avito.android.advertising.adapter.items.buzzoola.CommercialHtmlBannerItem;
import com.avito.android.advertising.adapter.items.buzzoola.profile_promo.ProfilePromoGalleryBannerItem;
import com.avito.android.advertising.analytics.events.BannerEvent;
import com.avito.android.advertising.h;
import com.avito.android.advertising.loaders.InterfaceC28345a;
import com.avito.android.advertising.loaders.avito_targeting.AvitoNetworkBanner;
import com.avito.android.advertising.loaders.buzzoola.BuzzoolaBanner;
import com.avito.android.advertising.loaders.j;
import com.avito.android.di.B;
import com.avito.konveyor.item_visibility_tracker.b;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: BannerImpressionFilter.kt */
@B
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAb/a;", "LWV0/a;", "_avito_advertising_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ab.a, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C13005a extends WV0.a {

    /* renamed from: e, reason: collision with root package name */
    @k
    public final j f448e;

    /* renamed from: f, reason: collision with root package name */
    @k
    public final com.avito.android.advertising.loaders.event_service.c f449f;

    @Inject
    public C13005a(@k j jVar, @k com.avito.android.advertising.loaders.event_service.c cVar) {
        super(true, true, 0.0f, 1000L, 4, null);
        this.f448e = jVar;
        this.f449f = cVar;
    }

    @Override // WV0.a
    public final boolean a(@k b.InterfaceC10495b interfaceC10495b) {
        if ((!(interfaceC10495b instanceof CommercialHtmlBannerItem) || ((CommercialHtmlBannerItem) interfaceC10495b).getBanner().getF87973e()) && !(interfaceC10495b instanceof ProfilePromoGalleryBannerItem)) {
            return interfaceC10495b instanceof InterfaceC28345a;
        }
        return false;
    }

    @Override // WV0.a
    public final void b(int i12, @k b.InterfaceC10495b interfaceC10495b) {
        com.avito.android.advertising.b banner;
        this.f448e.r3(((InterfaceC28345a) interfaceC10495b).getBannerInfo(), i12, BannerEvent.Type.f87773b);
        h hVar = interfaceC10495b instanceof h ? (h) interfaceC10495b : null;
        if (hVar == null || (banner = hVar.getBanner()) == null) {
            return;
        }
        boolean z12 = banner instanceof AvitoNetworkBanner;
        com.avito.android.advertising.loaders.event_service.c cVar = this.f449f;
        if (z12 && !(banner instanceof AvitoNetworkBanner.Video)) {
            cVar.h(((AvitoNetworkBanner) banner).getF88027o());
        }
        if (banner instanceof BuzzoolaBanner) {
            cVar.h(((BuzzoolaBanner) banner).getF88278i());
        }
    }
}
