package AJ0;

import com.avito.android.user_favorites.adapter.FavoritesTab;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;
import zJ0.C50477a;

/* compiled from: CollectionListTabModule_ProvideCollectionListTabBlueprintFactory.java */
@e
@y
@x
/* loaded from: classes5.dex */
public final class b implements h<com.avito.android.lib.deprecated_design.tab.b<? extends FavoritesTab>> {

    /* renamed from: a, reason: collision with root package name */
    public final a f284a;

    public b(a aVar) {
        this.f284a = aVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        this.f284a.getClass();
        return new C50477a();
    }
}
