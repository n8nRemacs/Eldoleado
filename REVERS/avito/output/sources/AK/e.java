package AK;

import dagger.internal.g;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: IacMediaServiceDialerMediatorImpl_Factory.java */
@dagger.internal.e
@y
@x
/* loaded from: classes14.dex */
public final class e implements h<d> {

    /* renamed from: a, reason: collision with root package name */
    public final dagger.internal.f f294a;

    public e(dagger.internal.f fVar) {
        this.f294a = fVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new d(g.b(this.f294a));
    }
}
