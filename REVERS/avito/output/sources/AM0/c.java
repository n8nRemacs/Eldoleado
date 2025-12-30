package aM0;

import com.avito.android.remote.R0;
import dagger.internal.e;
import dagger.internal.f;
import dagger.internal.h;
import dagger.internal.t;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: VasPlanningApi_Module_ProvideVasPlanningApiFactory.java */
@e
@y
@x
/* loaded from: classes5.dex */
public final class c implements h<InterfaceC19791a> {

    /* renamed from: a, reason: collision with root package name */
    public final f f20873a;

    public c(f fVar) {
        this.f20873a = fVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        R0 r02 = (R0) this.f20873a.get();
        C19792b.f20872a.getClass();
        InterfaceC19791a interfaceC19791a = (InterfaceC19791a) r02.create(InterfaceC19791a.class);
        t.d(interfaceC19791a);
        return interfaceC19791a;
    }
}
