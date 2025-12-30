package Ab;

import com.avito.android.C30828i;
import com.avito.android.advertising.loaders.j;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.u;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;
import ub.InterfaceC49012a;

/* compiled from: BannerViewFilter_Factory.java */
@e
@y
@x
/* loaded from: classes11.dex */
public final class d implements h<c> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<j> f455a;

    /* renamed from: b, reason: collision with root package name */
    public final u f456b;

    /* renamed from: c, reason: collision with root package name */
    public final Provider<com.avito.android.advertising.loaders.event_service.c> f457c;

    /* renamed from: d, reason: collision with root package name */
    public final Provider<C30828i> f458d;

    public d(u uVar, Provider provider, Provider provider2, Provider provider3) {
        this.f455a = provider;
        this.f456b = uVar;
        this.f457c = provider2;
        this.f458d = provider3;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new c(this.f455a.get(), (InterfaceC49012a) this.f456b.get(), this.f457c.get(), this.f458d.get());
    }
}
