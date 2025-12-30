package Ae;

import Y61.k;
import com.avito.android.remote.R0;
import dagger.internal.f;
import dagger.internal.h;
import dagger.internal.t;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AutoContactsApi_Module_ProvideAutoContactsApiFactory.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0003¨\u0006\u0004"}, d2 = {"LAe/c;", "Ldagger/internal/h;", "LAe/a;", "a", "_avito-discouraged_avito-api_auto-contacts"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ae.c, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C13024c implements h<InterfaceC13022a> {

    /* renamed from: b, reason: collision with root package name */
    @k
    public static final a f540b = new a(null);

    /* renamed from: a, reason: collision with root package name */
    @k
    public final f f541a;

    /* compiled from: AutoContactsApi_Module_ProvideAutoContactsApiFactory.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAe/c$a;", "", "<init>", "()V", "_avito-discouraged_avito-api_auto-contacts"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: Ae.c$a */
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public C13024c(@k f fVar) {
        this.f541a = fVar;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        R0 r02 = (R0) this.f541a.get();
        f540b.getClass();
        C13023b.f539a.getClass();
        InterfaceC13022a interfaceC13022a = (InterfaceC13022a) r02.create(InterfaceC13022a.class);
        t.b(interfaceC13022a, "Cannot return null from a non-@Nullable @Provides method");
        return interfaceC13022a;
    }
}
