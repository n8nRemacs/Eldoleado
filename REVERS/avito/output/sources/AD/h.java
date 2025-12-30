package Ad;

import android.content.Context;
import com.avito.android.di.module.C30102l3;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: InstalledAppsTrackingInteractorImpl_Factory.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0003¨\u0006\u0004"}, d2 = {"LAd/h;", "Ldagger/internal/h;", "LAd/g;", "a", "_avito_apps-tracking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class h implements dagger.internal.h<g> {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final a f523b = new a(null);

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final C30102l3 f524a;

    /* compiled from: InstalledAppsTrackingInteractorImpl_Factory.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAd/h$a;", "", "<init>", "()V", "_avito_apps-tracking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public h(@Y61.k C30102l3 c30102l3) {
        this.f524a = c30102l3;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Context context = (Context) this.f524a.get();
        f523b.getClass();
        return new g(context);
    }
}
