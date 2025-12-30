package A00;

import dagger.internal.A;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;
import java.util.Set;

/* compiled from: PersonFormDiffCallback_Factory.java */
@e
@y
@x
/* loaded from: classes15.dex */
public final class b implements h<a> {

    /* renamed from: a, reason: collision with root package name */
    public final A f34a;

    public b(A a12) {
        this.f34a = a12;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new a((Set) this.f34a.get());
    }
}
