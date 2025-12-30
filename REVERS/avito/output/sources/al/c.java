package Al;

import com.avito.android.brand_global_snippet.f;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;
import gk.InterfaceC40694a;
import javax.inject.Provider;

/* compiled from: BrandGlobalSnippetModule_ProvideBlueprintFactory.java */
@e
@y
@x
/* loaded from: classes12.dex */
public final class c implements h<TV0.b<?, ?>> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<InterfaceC40694a> f614a;

    /* renamed from: b, reason: collision with root package name */
    public final d f615b;

    public c(Provider provider, d dVar) {
        this.f614a = provider;
        this.f615b = dVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        InterfaceC40694a interfaceC40694a = this.f614a.get();
        f fVar = (f) this.f615b.get();
        C13049b.f613a.getClass();
        return interfaceC40694a.a(fVar);
    }
}
