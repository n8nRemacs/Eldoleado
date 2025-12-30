package af;

import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.u;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: AutoSelectInteractorImpl_Factory.java */
@e
@y
@x
/* renamed from: af.c, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C19881c implements h<C19880b> {

    /* renamed from: a, reason: collision with root package name */
    public final u f21111a;

    public C19881c(u uVar) {
        this.f21111a = uVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new C19880b((com.avito.android.auto_select.booking.data.a) this.f21111a.get());
    }
}
