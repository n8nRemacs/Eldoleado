package AB0;

import com.avito.android.deeplink_handler.view.a;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: TariffCpxInfoSfTermsDeeplinkHandler_Factory.java */
@dagger.internal.e
@y
@x
/* loaded from: classes4.dex */
public final class b implements h<a> {

    /* renamed from: a, reason: collision with root package name */
    public final dv.b f193a;

    public b(dv.b bVar) {
        this.f193a = bVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new a((a.d) this.f193a.get());
    }
}
