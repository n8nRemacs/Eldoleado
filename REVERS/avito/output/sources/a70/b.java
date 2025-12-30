package A70;

import com.avito.android.social.InterfaceC35017i;
import com.avito.android.social.InterfaceC35029v;
import com.avito.android.social.S;
import com.avito.android.social.X;
import dagger.internal.e;
import dagger.internal.h;
import dagger.internal.u;
import dagger.internal.x;
import dagger.internal.y;
import hD.C40806a;
import javax.inject.Provider;

/* compiled from: SocialLogoutManager_Factory.java */
@e
@y
@x
/* loaded from: classes15.dex */
public final class b implements h<a> {

    /* renamed from: a, reason: collision with root package name */
    public final Provider<InterfaceC35017i> f122a;

    /* renamed from: b, reason: collision with root package name */
    public final Provider<InterfaceC35029v> f123b;

    /* renamed from: c, reason: collision with root package name */
    public final Provider<X> f124c;

    /* renamed from: d, reason: collision with root package name */
    public final u f125d;

    /* renamed from: e, reason: collision with root package name */
    public final Provider<C40806a> f126e;

    public b(u uVar, Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.f122a = provider;
        this.f123b = provider2;
        this.f124c = provider3;
        this.f125d = uVar;
        this.f126e = provider4;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new a(this.f122a.get(), this.f123b.get(), this.f124c.get(), (S) this.f125d.get(), this.f126e.get());
    }
}
