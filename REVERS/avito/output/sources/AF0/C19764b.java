package aF0;

import bv.C25721c;
import com.avito.android.deeplink_handler.view.a;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: TravelOnboardingDeeplinkHandler_Factory.java */
@dagger.internal.e
@y
@x
/* renamed from: aF0.b, reason: case insensitive filesystem */
/* loaded from: classes4.dex */
public final class C19764b implements h<C19763a> {

    /* renamed from: a, reason: collision with root package name */
    public final dv.b f20835a;

    /* renamed from: b, reason: collision with root package name */
    public final C25721c f20836b;

    public C19764b(dv.b bVar, C25721c c25721c) {
        this.f20835a = bVar;
        this.f20836b = c25721c;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        a.d dVar = (a.d) this.f20835a.get();
        this.f20836b.get();
        return new C19763a(dVar);
    }
}
