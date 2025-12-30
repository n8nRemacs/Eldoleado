package am;

import Y61.k;
import com.avito.android.remote.R0;
import dagger.internal.f;
import dagger.internal.h;
import dagger.internal.t;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: CalltrackingApi_Module_ProvideCalltrackingApiFactory.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0003¨\u0006\u0004"}, d2 = {"Lam/c;", "Ldagger/internal/h;", "Lam/a;", "a", "_avito-discouraged_avito-api_calltracking"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: am.c, reason: case insensitive filesystem */
/* loaded from: classes12.dex */
public final class C19909c implements h<InterfaceC19907a> {

    /* renamed from: b, reason: collision with root package name */
    @k
    public static final a f21152b = new a(null);

    /* renamed from: a, reason: collision with root package name */
    @k
    public final f f21153a;

    /* compiled from: CalltrackingApi_Module_ProvideCalltrackingApiFactory.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lam/c$a;", "", "<init>", "()V", "_avito-discouraged_avito-api_calltracking"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: am.c$a */
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public C19909c(@k f fVar) {
        this.f21153a = fVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        R0 r02 = (R0) this.f21153a.get();
        f21152b.getClass();
        C19908b.f21151a.getClass();
        InterfaceC19907a interfaceC19907a = (InterfaceC19907a) r02.create(InterfaceC19907a.class);
        t.b(interfaceC19907a, "Cannot return null from a non-@Nullable @Provides method");
        return interfaceC19907a;
    }
}
