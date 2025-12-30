package a9;

import com.avito.android.analytics.InterfaceC28373a;
import dagger.internal.h;
import dagger.internal.u;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: ServiceEducationAnalyticsInteractorImpl_Factory.java */
@dagger.internal.e
@y
@x
/* loaded from: classes10.dex */
public final class c implements h<b> {

    /* renamed from: a, reason: collision with root package name */
    public final u f20754a;

    public c(u uVar) {
        this.f20754a = uVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new b((InterfaceC28373a) this.f20754a.get());
    }
}
