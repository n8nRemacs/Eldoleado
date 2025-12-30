package AI;

import com.avito.konveyor.a;
import com.avito.konveyor.exception.BlueprintCollisionException;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: SectionTagsModule_ProvideSectionTagsItemBinderFactory.java */
@dagger.internal.e
@y
@x
/* loaded from: classes13.dex */
public final class g implements dagger.internal.h<com.avito.konveyor.a> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<com.avito.android.home.tags_item.tag.b> f239a;

    public g(Provider<com.avito.android.home.tags_item.tag.b> provider) {
        this.f239a = provider;
    }

    @Override // javax.inject.Provider
    public final Object get() throws BlueprintCollisionException {
        com.avito.android.home.tags_item.tag.b bVar = this.f239a.get();
        d.f235a.getClass();
        a.C10493a c10493a = new a.C10493a();
        c10493a.b(bVar);
        return c10493a.a();
    }
}
