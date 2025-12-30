package AI;

import dagger.internal.u;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: SectionTagsModule_ProvideSectionTagsAdapterPresenterFactory.java */
@dagger.internal.e
@y
@x
/* loaded from: classes13.dex */
public final class f implements dagger.internal.h<com.avito.konveyor.adapter.h> {

    /* renamed from: a, reason: collision with root package name */
    public final u f238a;

    public f(u uVar) {
        this.f238a = uVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.inject.Provider
    public final Object get() {
        com.avito.konveyor.a aVar = (com.avito.konveyor.a) this.f238a.get();
        d.f235a.getClass();
        return new com.avito.konveyor.adapter.h(aVar, aVar, null, 4, null);
    }
}
