package A6;

import Y61.k;
import androidx.compose.runtime.internal.P;
import com.avito.android.analytics.InterfaceC28373a;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: RentAgreementAnalyticsInteractor.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA6/b;", "LA6/a;", "_avito_advert-details_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class b implements a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final InterfaceC28373a f100a;

    @Inject
    public b(@k InterfaceC28373a interfaceC28373a) {
        this.f100a = interfaceC28373a;
    }

    @Override // A6.a
    public final void a(@k String str) {
        this.f100a.c(new d(str));
    }
}
