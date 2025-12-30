package AB0;

import Ju.AbstractC14250d;
import Ju.InterfaceC14249c;
import Y61.k;
import android.os.Bundle;
import androidx.compose.runtime.internal.P;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.deeplink_handler.view.a;
import com.avito.android.tariff.cpx.info.sf.terms.TariffCpxInfoSfTermsDialogFragment;
import com.avito.android.tariff.cpx.info.sf.terms.domain.TariffCpxInfoSfTermsContent;
import com.avito.android.tariff.deeplink.TariffCpxInfoSfTermsLink;
import ev.AbstractC40162b;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: TariffCpxInfoSfTermsDeeplinkHandler.kt */
@P
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"}, d2 = {"LAB0/a;", "Lev/b;", "Lcom/avito/android/tariff/deeplink/TariffCpxInfoSfTermsLink;", "_avito_tariff_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class a extends AbstractC40162b<TariffCpxInfoSfTermsLink> {

    /* renamed from: d, reason: collision with root package name */
    @k
    public final a.d f192d;

    @Inject
    public a(@k a.d dVar) {
        this.f192d = dVar;
    }

    @Override // ev.AbstractC40162b
    public final InterfaceC14249c.b c(Bundle bundle, DeepLink deepLink, String str) {
        TariffCpxInfoSfTermsDialogFragment.a aVar = TariffCpxInfoSfTermsDialogFragment.f296814n0;
        TariffCpxInfoSfTermsContent tariffCpxInfoSfTermsContent = new TariffCpxInfoSfTermsContent(((TariffCpxInfoSfTermsLink) deepLink).f297387b);
        aVar.getClass();
        TariffCpxInfoSfTermsDialogFragment tariffCpxInfoSfTermsDialogFragment = new TariffCpxInfoSfTermsDialogFragment();
        tariffCpxInfoSfTermsDialogFragment.f296821m0.setValue(tariffCpxInfoSfTermsDialogFragment, TariffCpxInfoSfTermsDialogFragment.f296815o0[0], tariffCpxInfoSfTermsContent);
        this.f192d.w1(tariffCpxInfoSfTermsDialogFragment, "TariffCpxInfoSfTermsDialogFragment");
        return AbstractC14250d.c.f9171c;
    }
}
