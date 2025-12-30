package Al;

import com.avito.android.brand_global_snippet.f;
import dagger.internal.e;
import dagger.internal.g;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: BrandGlobalSnippetModule_ProvidePresenterFactory.java */
@e
@y
@x
/* loaded from: classes12.dex */
public final class d implements h<f> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<gk.e> f616a;

    /* renamed from: b, reason: collision with root package name */
    public final dagger.internal.f f617b;

    public d(dagger.internal.f fVar, Provider provider) {
        this.f616a = provider;
        this.f617b = fVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        gk.e eVar = this.f616a.get();
        h31.e eVarB = g.b(this.f617b);
        C13049b.f613a.getClass();
        return eVar.a(eVarB);
    }
}
