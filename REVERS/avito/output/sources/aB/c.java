package Ab;

import Y61.k;
import com.avito.android.C30828i;
import com.avito.android.advertising.adapter.items.buzzoola.CommercialHtmlBannerItem;
import com.avito.android.advertising.adapter.items.buzzoola.profile_promo.ProfilePromoGalleryBannerItem;
import com.avito.android.advertising.analytics.events.BannerEvent;
import com.avito.android.advertising.h;
import com.avito.android.advertising.loaders.InterfaceC28345a;
import com.avito.android.advertising.loaders.avito_targeting.AvitoNetworkBanner;
import com.avito.android.advertising.loaders.buzzoola.BuzzoolaBanner;
import com.avito.android.advertising.loaders.buzzoola.BuzzoolaPremiumConfig;
import com.avito.android.advertising.loaders.buzzoola.p;
import com.avito.android.advertising.loaders.j;
import com.avito.android.di.B;
import com.avito.android.remote.model.advertising.PremiumBannerType;
import com.avito.konveyor.item_visibility_tracker.b;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.s0;
import kotlin.reflect.n;
import kotlin.text.C43066x;
import ub.InterfaceC49012a;

/* compiled from: BannerViewFilter.kt */
@B
@s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAb/c;", "LWV0/a;", "_avito_advertising_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class c extends WV0.a {

    /* renamed from: e, reason: collision with root package name */
    @k
    public final j f452e;

    /* renamed from: f, reason: collision with root package name */
    @k
    public final InterfaceC49012a f453f;

    /* renamed from: g, reason: collision with root package name */
    @k
    public final com.avito.android.advertising.loaders.event_service.c f454g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @Inject
    public c(@k j jVar, @k InterfaceC49012a interfaceC49012a, @k com.avito.android.advertising.loaders.event_service.c cVar, @k C30828i c30828i) {
        super(true, true, 0.0f, ((Boolean) c30828i.f164263h.a().invoke()).booleanValue() ? 1000L : 2000L, 4, null);
        n<Object> nVar = C30828i.f164256k[6];
        this.f452e = jVar;
        this.f453f = interfaceC49012a;
        this.f454g = cVar;
    }

    @Override // WV0.a
    public final boolean a(@k b.InterfaceC10495b interfaceC10495b) {
        if ((!(interfaceC10495b instanceof CommercialHtmlBannerItem) || ((CommercialHtmlBannerItem) interfaceC10495b).getBanner().getF88276g()) && !(interfaceC10495b instanceof ProfilePromoGalleryBannerItem)) {
            return interfaceC10495b instanceof InterfaceC28345a;
        }
        return false;
    }

    @Override // WV0.a
    public final void b(int i12, @k b.InterfaceC10495b interfaceC10495b) {
        com.avito.android.advertising.b banner;
        InterfaceC28345a interfaceC28345a = (InterfaceC28345a) interfaceC10495b;
        this.f452e.M6(interfaceC28345a.getF74925c(), i12, BannerEvent.Type.f87773b);
        h hVar = interfaceC10495b instanceof h ? (h) interfaceC10495b : null;
        if (hVar == null || (banner = hVar.getBanner()) == null) {
            return;
        }
        boolean z12 = banner instanceof AvitoNetworkBanner.Video;
        com.avito.android.advertising.loaders.event_service.c cVar = this.f454g;
        if (z12) {
            cVar.h(((AvitoNetworkBanner.Video) banner).f88027o);
        }
        if (banner instanceof BuzzoolaBanner) {
            cVar.i(((BuzzoolaBanner) banner).getF88278i());
            p pVar = banner instanceof p ? (p) banner : null;
            BuzzoolaPremiumConfig f88259d = pVar != null ? pVar.getF88259d() : null;
            if ((f88259d != null ? f88259d.f88306d : null) == PremiumBannerType.BRAND_SPACE) {
                String str = f88259d.f88307e;
                String bannerCode = interfaceC28345a.getF74925c().getBannerCode();
                if (str == null || !(!C43066x.K(str)) || bannerCode == null) {
                    return;
                }
                this.f453f.a(bannerCode, str);
            }
        }
    }
}
