package aK0;

import android.app.Application;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.l;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: UserStatsIntentFactoryImpl_Factory.java */
@e
@y
@x
/* renamed from: aK0.c, reason: case insensitive filesystem */
/* loaded from: classes5.dex */
public final class C19785c implements h<C19784b> {

    /* renamed from: a, reason: collision with root package name */
    public final l f20867a;

    public C19785c(l lVar) {
        this.f20867a = lVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.inject.Provider
    public final Object get() {
        return new C19784b((Application) this.f20867a.f393949a);
    }
}
