package Ag0;

import com.avito.android.deeplink_handler.view.a;
import com.avito.android.rating.f;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: UserReviewsDeepLinkHandler_Factory.java */
@e
@y
@x
/* renamed from: Ag0.b, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C13030b implements h<C13029a> {

    /* renamed from: a, reason: collision with root package name */
    public final dv.b f555a;

    /* renamed from: b, reason: collision with root package name */
    public final com.avito.android.rating.h f556b;

    public C13030b(com.avito.android.rating.h hVar, dv.b bVar) {
        this.f555a = bVar;
        this.f556b = hVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new C13029a((a.InterfaceC4053a) this.f555a.get(), (f) this.f556b.get());
    }
}
