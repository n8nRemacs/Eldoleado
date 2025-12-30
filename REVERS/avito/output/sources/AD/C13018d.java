package Ad;

import com.avito.android.di.module.C30214v6;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: InstalledAppsTrackExpirationInteractorImpl_Factory.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0003¨\u0006\u0004"}, d2 = {"LAd/d;", "Ldagger/internal/h;", "LAd/c;", "a", "_avito_apps-tracking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ad.d, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C13018d implements dagger.internal.h<C13017c> {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final a f520b = new a(null);

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final C30214v6 f521a;

    /* compiled from: InstalledAppsTrackExpirationInteractorImpl_Factory.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAd/d$a;", "", "<init>", "()V", "_avito_apps-tracking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ad.d$a */
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public C13018d(@Y61.k C30214v6 c30214v6) {
        this.f521a = c30214v6;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        AK0.l lVar = (AK0.l) this.f521a.get();
        f520b.getClass();
        return new C13017c(lVar);
    }
}
