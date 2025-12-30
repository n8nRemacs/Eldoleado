package an0;

import android.content.Context;
import com.avito.android.di.module.C30102l3;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: ComfortableDealReviewsIntentFactoryImpl_Factory.java */
@e
@y
@x
/* loaded from: classes12.dex */
public final class d implements h<C19916b> {

    /* renamed from: a, reason: collision with root package name */
    public final C30102l3 f21165a;

    public d(C30102l3 c30102l3) {
        this.f21165a = c30102l3;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new C19916b((Context) this.f21165a.get());
    }
}
