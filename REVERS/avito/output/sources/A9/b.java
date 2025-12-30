package a9;

import Y61.k;
import Y61.l;
import com.avito.android.analytics.InterfaceC28373a;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: ServiceEducationAnalyticsInteractor.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"La9/b;", "La9/a;", "_avito_advert-core_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class b implements InterfaceC19700a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final InterfaceC28373a f20753a;

    @Inject
    public b(@k InterfaceC28373a interfaceC28373a) {
        this.f20753a = interfaceC28373a;
    }

    @Override // a9.InterfaceC19700a
    public final void a(@l String str, @l String str2, boolean z12) {
        this.f20753a.c(new e(str, str2, z12));
    }

    @Override // a9.InterfaceC19700a
    public final void b(@l String str, @l String str2, boolean z12) {
        this.f20753a.c(new d(str, str2, z12));
    }
}
