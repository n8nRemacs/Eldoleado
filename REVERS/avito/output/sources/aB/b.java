package Ab;

import com.avito.android.advertising.loaders.j;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: BannerImpressionFilter_Factory.java */
@e
@y
@x
/* loaded from: classes11.dex */
public final class b implements h<C13005a> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<j> f450a;

    /* renamed from: b, reason: collision with root package name */
    public final Provider<com.avito.android.advertising.loaders.event_service.c> f451b;

    public b(Provider<j> provider, Provider<com.avito.android.advertising.loaders.event_service.c> provider2) {
        this.f450a = provider;
        this.f451b = provider2;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new C13005a(this.f450a.get(), this.f451b.get());
    }
}
