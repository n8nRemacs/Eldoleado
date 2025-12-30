package AI;

import Oi0.C14701e;
import Oi0.InterfaceC14698b;
import com.avito.android.util.C;
import dagger.internal.u;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: SectionTagsModule_ProvideSafeRecyclerAdapterFactoryFactory.java */
@dagger.internal.e
@y
@x
/* loaded from: classes13.dex */
public final class e implements dagger.internal.h<C14701e> {

    /* renamed from: a, reason: collision with root package name */
    public final u f236a;

    /* renamed from: b, reason: collision with root package name */
    public final Provider<C> f237b;

    public e(u uVar, Provider provider) {
        this.f236a = uVar;
        this.f237b = provider;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        InterfaceC14698b interfaceC14698b = (InterfaceC14698b) this.f236a.get();
        C c12 = this.f237b.get();
        d.f235a.getClass();
        return new C14701e(interfaceC14698b, c12);
    }
}
