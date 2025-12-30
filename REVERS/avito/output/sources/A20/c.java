package A20;

import com.avito.android.remote.R0;
import dagger.internal.e;
import dagger.internal.f;
import dagger.internal.h;
import dagger.internal.t;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: MultiMessageSendApi_Module_ProvideMultiMessageSendApiFactory.java */
@e
@y
@x
/* loaded from: classes15.dex */
public final class c implements h<a> {

    /* renamed from: a, reason: collision with root package name */
    public final f f57a;

    public c(f fVar) {
        this.f57a = fVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        R0 r02 = (R0) this.f57a.get();
        b.f56a.getClass();
        a aVar = (a) r02.create(a.class);
        t.d(aVar);
        return aVar;
    }
}
