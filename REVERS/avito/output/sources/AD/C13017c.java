package Ad;

import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: InstalledAppsTrackExpirationInteractor.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAd/c;", "LAd/b;", "_avito_apps-tracking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ad.c, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C13017c implements InterfaceC13016b {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final AK0.l f519a;

    @Inject
    public C13017c(@Y61.k AK0.l lVar) {
        this.f519a = lVar;
    }

    @Override // Ad.InterfaceC13016b
    public final void a() {
        this.f519a.putLong("installed_apps_track_date", System.currentTimeMillis());
    }

    @Override // Ad.InterfaceC13016b
    public final boolean b() {
        long j12 = this.f519a.getLong("installed_apps_track_date", -1L);
        if (j12 == -1) {
            return true;
        }
        return System.currentTimeMillis() - j12 > TimeUnit.DAYS.toMillis(1L);
    }
}
