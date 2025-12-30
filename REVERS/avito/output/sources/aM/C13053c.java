package Am;

import com.avito.android.analytics.InterfaceC28373a;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: CarNavigatorAnalyticsInteractorImpl_Factory.java */
@dagger.internal.e
@y
@x
/* renamed from: Am.c, reason: case insensitive filesystem */
/* loaded from: classes12.dex */
public final class C13053c implements h<C13052b> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<InterfaceC28373a> f620a;

    public C13053c(Provider<InterfaceC28373a> provider) {
        this.f620a = provider;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new C13052b(this.f620a.get());
    }
}
