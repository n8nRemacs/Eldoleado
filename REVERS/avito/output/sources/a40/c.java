package A40;

import com.avito.android.analytics.InterfaceC28373a;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.l;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: OnboardingAnalyticsInteractorImpl_Factory.java */
@e
@y
@x
/* loaded from: classes15.dex */
public final class c implements h<b> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<InterfaceC28373a> f94a;

    /* renamed from: b, reason: collision with root package name */
    public final l f95b;

    public c(l lVar, Provider provider) {
        this.f94a = provider;
        this.f95b = lVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.inject.Provider
    public final Object get() {
        return new b(this.f94a.get(), (String) this.f95b.f393949a);
    }
}
