package a5;

import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: CreditBannerFilter_Factory.java */
@e
@y
@x
/* loaded from: classes10.dex */
public final class b implements h<C19682a> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<com.avito.android.advert_core.analytics.a> f20735a;

    /* renamed from: b, reason: collision with root package name */
    public final Provider<com.avito.android.advertising.loaders.event_service.c> f20736b;

    public b(Provider<com.avito.android.advert_core.analytics.a> provider, Provider<com.avito.android.advertising.loaders.event_service.c> provider2) {
        this.f20735a = provider;
        this.f20736b = provider2;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new C19682a(this.f20735a.get(), this.f20736b.get());
    }
}
