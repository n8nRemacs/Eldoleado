package Am;

import FP0.g;
import Y61.k;
import android.net.Uri;
import com.avito.android.analytics.InterfaceC28373a;
import java.util.LinkedHashMap;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: CarNavigatorAnalyticsInteractor.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAm/b;", "LAm/a;", "_avito_car-navigator_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Am.b, reason: case insensitive filesystem */
/* loaded from: classes12.dex */
public final class C13052b implements InterfaceC13051a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final InterfaceC28373a f618a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final LinkedHashMap f619b = new LinkedHashMap();

    @Inject
    public C13052b(@k InterfaceC28373a interfaceC28373a) {
        this.f618a = interfaceC28373a;
    }

    @Override // Am.InterfaceC13051a
    public final void a(@k Uri uri) {
        String queryParameter = uri.getQueryParameter("landing_slug");
        if (queryParameter == null) {
            return;
        }
        String queryParameter2 = uri.getQueryParameter("iid");
        String queryParameter3 = uri.getQueryParameter("from_page");
        LinkedHashMap linkedHashMap = this.f619b;
        Object obj = linkedHashMap.get(queryParameter);
        Boolean bool = Boolean.TRUE;
        if (L.f(obj, bool)) {
            return;
        }
        this.f618a.c(new g(uri.toString(), queryParameter, queryParameter2, queryParameter3));
        linkedHashMap.put(queryParameter, bool);
    }

    @Override // Am.InterfaceC13051a
    public final void b(@k HP0.b bVar) {
        this.f618a.c(bVar);
    }
}
