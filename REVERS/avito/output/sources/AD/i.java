package Ad;

import android.content.Context;
import com.avito.android.T2;
import com.avito.android.V2;
import com.avito.android.account.E;
import com.avito.android.analytics.InterfaceC28373a;
import com.avito.android.apps_tracking.InstalledAppsTrackingTask;
import com.avito.android.di.module.C30102l3;
import javax.inject.Provider;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import lD.C43617a;
import lD.C43619c;

/* compiled from: InstalledAppsTrackingTask_Factory.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0003¨\u0006\u0004"}, d2 = {"LAd/i;", "Ldagger/internal/h;", "Lcom/avito/android/apps_tracking/InstalledAppsTrackingTask;", "a", "_avito_apps-tracking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class i implements dagger.internal.h<InstalledAppsTrackingTask> {

    /* renamed from: j, reason: collision with root package name */
    @Y61.k
    public static final a f525j = new a(null);

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final C30102l3 f526a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final C13018d f527b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final h f528c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final n f529d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.k
    public final dagger.internal.f f530e;

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public final C43619c f531f;

    /* renamed from: g, reason: collision with root package name */
    @Y61.k
    public final V2 f532g;

    /* renamed from: h, reason: collision with root package name */
    @Y61.k
    public final Provider<com.avito.android.analytics.task.a> f533h;

    /* renamed from: i, reason: collision with root package name */
    @Y61.k
    public final Provider<E> f534i;

    /* compiled from: InstalledAppsTrackingTask_Factory.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAd/i$a;", "", "<init>", "()V", "_avito_apps-tracking_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public i(@Y61.k C30102l3 c30102l3, @Y61.k C13018d c13018d, @Y61.k h hVar, @Y61.k n nVar, @Y61.k dagger.internal.f fVar, @Y61.k C43619c c43619c, @Y61.k V2 v22, @Y61.k Provider provider, @Y61.k Provider provider2) {
        this.f526a = c30102l3;
        this.f527b = c13018d;
        this.f528c = hVar;
        this.f529d = nVar;
        this.f530e = fVar;
        this.f531f = c43619c;
        this.f532g = v22;
        this.f533h = provider;
        this.f534i = provider2;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Context context = (Context) this.f526a.get();
        InterfaceC13016b interfaceC13016b = (InterfaceC13016b) this.f527b.get();
        InterfaceC13020f interfaceC13020f = (InterfaceC13020f) this.f528c.get();
        k kVar = (k) this.f529d.get();
        InterfaceC28373a interfaceC28373a = (InterfaceC28373a) this.f530e.get();
        C43617a c43617a = (C43617a) this.f531f.get();
        T2 t22 = (T2) this.f532g.get();
        com.avito.android.analytics.task.a aVar = this.f533h.get();
        E e12 = this.f534i.get();
        f525j.getClass();
        return new InstalledAppsTrackingTask(context, interfaceC13016b, interfaceC13020f, kVar, interfaceC28373a, c43617a, t22, aVar, e12);
    }
}
