package Am;

import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: CarNavigatorViewModel_Factory.java */
@dagger.internal.e
@y
@x
/* renamed from: Am.f, reason: case insensitive filesystem */
/* loaded from: classes12.dex */
public final class C13056f implements h<C13055e> {

    /* renamed from: a, reason: collision with root package name */
    public final com.avito.android.car_navigator.mvi.e f621a;

    public C13056f(com.avito.android.car_navigator.mvi.e eVar) {
        this.f621a = eVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new C13055e((com.avito.android.car_navigator.mvi.d) this.f621a.get(), null, 2, null);
    }
}
