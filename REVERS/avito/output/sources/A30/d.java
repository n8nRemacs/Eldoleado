package A30;

import android.app.Application;
import dagger.internal.h;
import dagger.internal.l;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: NotificationCenterIntentFactoryImpl_Factory.java */
@dagger.internal.e
@y
@x
/* loaded from: classes15.dex */
public final class d implements h<c> {

    /* renamed from: a, reason: collision with root package name */
    public final l f77a;

    public d(l lVar) {
        this.f77a = lVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.inject.Provider
    public final Object get() {
        return new c((Application) this.f77a.f393949a);
    }
}
