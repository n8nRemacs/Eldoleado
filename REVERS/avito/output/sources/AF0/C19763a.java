package aF0;

import Ju.AbstractC14250d;
import Ju.InterfaceC14249c;
import Y61.k;
import android.os.Bundle;
import androidx.compose.runtime.internal.P;
import androidx.core.os.C22777e;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.deeplink_handler.view.a;
import com.avito.android.travel_onboarding.TravelOnboardingFragment;
import com.avito.android.travel_onboarding.navigation.TravelOnboardingDeeplink;
import ev.AbstractC40162b;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.Q;

/* compiled from: TravelOnboardingDeeplinkHandler.kt */
@P
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"}, d2 = {"LaF0/a;", "Lev/b;", "Lcom/avito/android/travel_onboarding/navigation/TravelOnboardingDeeplink;", "_avito_travel-onboarding_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aF0.a, reason: case insensitive filesystem */
/* loaded from: classes4.dex */
public final class C19763a extends AbstractC40162b<TravelOnboardingDeeplink> {

    /* renamed from: d, reason: collision with root package name */
    @k
    public final a.d f20834d;

    @Inject
    public C19763a(@k a.d dVar) {
        this.f20834d = dVar;
    }

    @Override // ev.AbstractC40162b
    public final InterfaceC14249c.b c(Bundle bundle, DeepLink deepLink, String str) {
        TravelOnboardingDeeplink travelOnboardingDeeplink = (TravelOnboardingDeeplink) deepLink;
        TravelOnboardingFragment.f302217m0.getClass();
        TravelOnboardingFragment travelOnboardingFragment = new TravelOnboardingFragment();
        travelOnboardingFragment.setArguments(C22777e.b(new Q("onboarding_id", travelOnboardingDeeplink.f302328b), new Q("onboarding_deeplink_key", travelOnboardingDeeplink)));
        this.f20834d.w1(travelOnboardingFragment, "TravelOnboardingFragment");
        return AbstractC14250d.c.f9171c;
    }
}
