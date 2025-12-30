package AI;

import Oi0.C14699c;
import Oi0.InterfaceC14698b;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: SectionTagsModule_ProvideSectionTagsViewHolderBuilderFactory.java */
@dagger.internal.e
@y
@x
/* loaded from: classes13.dex */
public final class h implements dagger.internal.h<InterfaceC14698b> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<com.avito.konveyor.a> f240a;

    public h(Provider<com.avito.konveyor.a> provider) {
        this.f240a = provider;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        com.avito.konveyor.a aVar = this.f240a.get();
        d.f235a.getClass();
        return new C14699c(aVar);
    }
}
