package a30;

import Y61.k;
import dagger.internal.h;
import dagger.internal.u;
import dagger.internal.w;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: NavigationConfigStorageImpl_Factory.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0003¨\u0006\u0004"}, d2 = {"La30/d;", "Ldagger/internal/h;", "La30/c;", "a", "_avito_navigation-config_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a30.d, reason: case insensitive filesystem */
/* loaded from: classes15.dex */
public final class C19676d implements h<C19675c> {

    /* renamed from: b, reason: collision with root package name */
    @k
    public static final a f20721b = new a(null);

    /* renamed from: a, reason: collision with root package name */
    @k
    public final u f20722a;

    /* compiled from: NavigationConfigStorageImpl_Factory.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"La30/d$a;", "", "<init>", "()V", "_avito_navigation-config_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: a30.d$a */
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public C19676d(@k u uVar) {
        this.f20722a = uVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        h31.e eVarA = dagger.internal.g.a(w.a(this.f20722a));
        f20721b.getClass();
        return new C19675c(eVarA);
    }
}
