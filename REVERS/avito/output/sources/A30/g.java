package a30;

import AK0.i;
import AK0.l;
import Y61.k;
import android.app.Application;
import dagger.internal.h;
import javax.inject.Provider;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: NavigationConfigStorageModule_ProvideNavigationConfigPreferencesFactory.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0003¨\u0006\u0004"}, d2 = {"La30/g;", "Ldagger/internal/h;", "LAK0/l;", "a", "_avito_navigation-config_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final class g implements h<l> {

    /* renamed from: c, reason: collision with root package name */
    @k
    public static final a f20723c = new a(null);

    /* renamed from: a, reason: collision with root package name */
    @k
    public final dagger.internal.l f20724a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final Provider<i> f20725b;

    /* compiled from: NavigationConfigStorageModule_ProvideNavigationConfigPreferencesFactory.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"La30/g$a;", "", "<init>", "()V", "_avito_navigation-config_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public g(@k C19678f c19678f, @k dagger.internal.l lVar, @k Provider provider) {
        this.f20724a = lVar;
        this.f20725b = provider;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.inject.Provider
    public final Object get() {
        Application application = (Application) this.f20724a.f393949a;
        i iVar = this.f20725b.get();
        f20723c.getClass();
        return iVar.a(application, "navigation_config");
    }
}
