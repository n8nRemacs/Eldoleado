package aB;

import com.avito.android.deeplink_handler.view.a;
import com.avito.android.extended_profile_serp.C30551n;
import com.avito.android.extended_profile_serp.InterfaceC30548k;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: ExtendedProfileSerpLinkHandler_Factory.java */
@e
@y
@x
/* renamed from: aB.b, reason: case insensitive filesystem */
/* loaded from: classes13.dex */
public final class C19749b implements h<C19748a> {

    /* renamed from: a, reason: collision with root package name */
    public final C30551n f20809a;

    /* renamed from: b, reason: collision with root package name */
    public final dv.b f20810b;

    public C19749b(C30551n c30551n, dv.b bVar) {
        this.f20809a = c30551n;
        this.f20810b = bVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new C19748a((InterfaceC30548k) this.f20809a.get(), (a.InterfaceC4053a) this.f20810b.get());
    }
}
