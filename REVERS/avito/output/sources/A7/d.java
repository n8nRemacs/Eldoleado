package A7;

import androidx.view.InterfaceC22983P;
import com.avito.android.analytics.InterfaceC28373a;
import com.avito.android.analytics.screens.G;
import com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker;
import dagger.internal.h;
import dagger.internal.l;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: AdvertDetailsTrackerImpl_Factory.java */
@dagger.internal.e
@y
@x
/* loaded from: classes10.dex */
public final class d implements h<c> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<com.avito.android.analytics.screens.tracker.degrade.a> f113a;

    /* renamed from: b, reason: collision with root package name */
    public final l f114b;

    /* renamed from: c, reason: collision with root package name */
    public final l f115c;

    /* renamed from: d, reason: collision with root package name */
    public final Provider<G> f116d;

    /* renamed from: e, reason: collision with root package name */
    public final Provider<InterfaceC28373a> f117e;

    public d(l lVar, l lVar2, Provider provider, Provider provider2, Provider provider3) {
        this.f113a = provider;
        this.f114b = lVar;
        this.f115c = lVar2;
        this.f116d = provider2;
        this.f117e = provider3;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new c(this.f113a.get(), (ScreenPerformanceTracker) this.f114b.f393949a, (InterfaceC22983P) this.f115c.f393949a, this.f116d.get(), this.f117e.get());
    }
}
