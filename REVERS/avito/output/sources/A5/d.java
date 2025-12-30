package a5;

import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: CreditBannerImpressionFilter_Factory.java */
@e
@y
@x
/* loaded from: classes10.dex */
public final class d implements h<c> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<com.avito.android.advert_core.analytics.a> f20739a;

    /* renamed from: b, reason: collision with root package name */
    public final Provider<com.avito.android.advertising.loaders.event_service.c> f20740b;

    public d(Provider<com.avito.android.advert_core.analytics.a> provider, Provider<com.avito.android.advertising.loaders.event_service.c> provider2) {
        this.f20739a = provider;
        this.f20740b = provider2;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new c(this.f20739a.get(), this.f20740b.get());
    }
}
