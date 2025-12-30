package af;

import Y61.k;
import androidx.compose.runtime.internal.P;
import cf.InterfaceC27185b;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlinx.coroutines.flow.InterfaceC43160i;

/* compiled from: AutoSelectInteractor.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Laf/b;", "Laf/a;", "_avito_auto-select_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: af.b, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C19880b implements InterfaceC19879a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final com.avito.android.auto_select.booking.data.a f21110a;

    @Inject
    public C19880b(@k com.avito.android.auto_select.booking.data.a aVar) {
        this.f21110a = aVar;
    }

    @Override // af.InterfaceC19879a
    @k
    public final InterfaceC43160i<InterfaceC27185b> a(@k String str) {
        return this.f21110a.a(str);
    }
}
