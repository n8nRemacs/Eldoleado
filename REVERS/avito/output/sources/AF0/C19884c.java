package af0;

import com.avito.android.publish.InterfaceC33544d;
import com.avito.android.util.S3;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.u;
import dagger.internal.x;
import dagger.internal.y;
import javax.inject.Provider;

/* compiled from: PhoneVerificationDataProviderImpl_Factory.java */
@e
@y
@x
/* renamed from: af0.c, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C19884c implements h<C19883b> {

    /* renamed from: a, reason: collision with root package name */
    public final u f21115a;

    /* renamed from: b, reason: collision with root package name */
    public final Provider<com.avito.android.details.a> f21116b;

    /* renamed from: c, reason: collision with root package name */
    public final S3 f21117c;

    public C19884c(u uVar, Provider provider, S3 s32) {
        this.f21115a = uVar;
        this.f21116b = provider;
        this.f21117c = s32;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        InterfaceC33544d interfaceC33544d = (InterfaceC33544d) this.f21115a.get();
        com.avito.android.details.a aVar = this.f21116b.get();
        this.f21117c.getClass();
        return new C19883b(interfaceC33544d, aVar, S3.a());
    }
}
