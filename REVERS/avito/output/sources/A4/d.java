package a4;

import com.avito.android.advert.icebreakerseditor.analytics.IcebreakersEditorAnalyticsData;
import com.avito.android.analytics.InterfaceC28373a;
import dagger.internal.h;
import dagger.internal.l;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: IcebreakersEditorEventTrackerImpl_Factory.java */
@dagger.internal.e
@y
@x
/* loaded from: classes10.dex */
public final class d implements h<c> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<InterfaceC28373a> f20729a;

    /* renamed from: b, reason: collision with root package name */
    public final l f20730b;

    public d(l lVar, Provider provider) {
        this.f20729a = provider;
        this.f20730b = lVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.inject.Provider
    public final Object get() {
        return new c(this.f20729a.get(), (IcebreakersEditorAnalyticsData) this.f20730b.f393949a);
    }
}
