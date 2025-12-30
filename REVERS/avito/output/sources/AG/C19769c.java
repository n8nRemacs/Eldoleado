package aG;

import com.avito.android.remote.R0;
import dagger.internal.e;
import dagger.internal.f;
import dagger.internal.h;
import dagger.internal.t;
import dagger.internal.x;
import dagger.internal.y;

/* compiled from: MotivationsListApi_Module_ProvideMotivationsListApiFactory.java */
@e
@y
@x
/* renamed from: aG.c, reason: case insensitive filesystem */
/* loaded from: classes13.dex */
public final class C19769c implements h<InterfaceC19767a> {

    /* renamed from: a, reason: collision with root package name */
    public final f f20840a;

    public C19769c(f fVar) {
        this.f20840a = fVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        R0 r02 = (R0) this.f20840a.get();
        C19768b.f20839a.getClass();
        InterfaceC19767a interfaceC19767a = (InterfaceC19767a) r02.create(InterfaceC19767a.class);
        t.d(interfaceC19767a);
        return interfaceC19767a;
    }
}
