package aF0;

import com.avito.android.travel_onboarding.navigation.TravelOnboardingDeeplink;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;
import lv.C43834a;

/* compiled from: TravelOnboardingDeeplinkModule_ProvideTravelOnboardingLinkMappingFactory.java */
@dagger.internal.e
@y
@x
/* renamed from: aF0.d, reason: case insensitive filesystem */
/* loaded from: classes4.dex */
public final class C19766d implements h<C43834a> {

    /* renamed from: a, reason: collision with root package name */
    public final C19765c f20837a;

    /* renamed from: b, reason: collision with root package name */
    public final C19764b f20838b;

    public C19766d(C19765c c19765c, C19764b c19764b) {
        this.f20837a = c19765c;
        this.f20838b = c19764b;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        C19764b c19764b = this.f20838b;
        this.f20837a.getClass();
        C43834a.C11807a c11807a = C43834a.f414235d;
        return new C43834a(TravelOnboardingDeeplink.class, new e(), new C43834a.b.C11809b(c19764b));
    }
}
