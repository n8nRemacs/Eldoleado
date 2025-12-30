package A7;

import Y61.k;
import Y61.l;
import android.net.Uri;
import android.os.SystemClock;
import androidx.compose.runtime.internal.P;
import androidx.recyclerview.widget.RecyclerView;
import androidx.view.InterfaceC22983P;
import arrow.core.AbstractC23662a;
import com.avito.android.analytics.InterfaceC28373a;
import com.avito.android.analytics.screens.AdvertScreen;
import com.avito.android.analytics.screens.G;
import com.avito.android.analytics.screens.J;
import com.avito.android.analytics.screens.tracker.C28493o;
import com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker;
import com.avito.android.analytics.screens.tracker.fps.h;
import com.avito.android.analytics.statsd.y;
import com.avito.android.memory.consumption.f;
import com.avito.android.remote.analytics.C34244a;
import com.avito.android.remote.error.ApiError;
import com.avito.android.remote.model.SerpResultCategoryDetails;
import java.util.Set;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: AdvertDetailsTracker.kt */
@P
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002¨\u0006\u0003"}, d2 = {"LA7/c;", "LA7/b;", "Lcom/avito/android/analytics/screens/tracker/ScreenPerformanceTracker;", "_avito_advert-details_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class c implements b, ScreenPerformanceTracker {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final com.avito.android.analytics.screens.tracker.degrade.a f109a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final ScreenPerformanceTracker f110b;

    /* renamed from: c, reason: collision with root package name */
    @k
    public final a f111c;

    /* renamed from: d, reason: collision with root package name */
    @k
    public final f f112d;

    @Inject
    public c(@k com.avito.android.analytics.screens.tracker.degrade.a aVar, @k ScreenPerformanceTracker screenPerformanceTracker, @k InterfaceC22983P interfaceC22983P, @k G g12, @k InterfaceC28373a interfaceC28373a) {
        this.f109a = aVar;
        this.f110b = screenPerformanceTracker;
        a aVar2 = new a();
        this.f111c = aVar2;
        this.f112d = new f(interfaceC28373a, g12, AdvertScreen.f90278d.f90471b);
        screenPerformanceTracker.B(aVar2, interfaceC22983P);
    }

    public static String g(String str, boolean z12) {
        AdvertScreen.f90278d.getClass();
        StringBuilder sb2 = new StringBuilder(AdvertScreen.f90280f);
        sb2.append('-');
        sb2.append(str);
        if (z12) {
            sb2.append("-shutter");
        }
        return sb2.toString();
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void A(@k String str, boolean z12) {
        this.f110b.A(str, z12);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void B(@k com.avito.android.analytics.screens.image.c cVar, @k InterfaceC22983P interfaceC22983P) {
        this.f110b.B(cVar, interfaceC22983P);
    }

    @Override // A7.b
    public final void C(@k Set<? extends Uri> set) {
        this.f111c.f108a = set;
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void D(@k String str, boolean z12) {
        this.f110b.D(str, z12);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void E(@k String str, @k ScreenPerformanceTracker.LoadingType loadingType, @k J j12, long j13) {
        this.f110b.E(str, loadingType, j12, j13);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    @l
    public final C34244a F(@k String str, @l AbstractC23662a<? extends Throwable, ? extends ApiError> abstractC23662a, boolean z12) {
        return this.f110b.F(str, abstractC23662a, z12);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void G(@l SerpResultCategoryDetails serpResultCategoryDetails) {
        this.f110b.G(serpResultCategoryDetails);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    @l
    public final Long H(@k String str) {
        return this.f110b.H(str);
    }

    @Override // A7.b
    public final void I(@k String str, boolean z12) {
        ScreenPerformanceTracker.a.b(this.f110b, g(str, z12), 2);
    }

    @Override // A7.b
    public final void J(@k String str, boolean z12) {
        this.f110b.m(g(str, z12));
    }

    @Override // A7.b
    public final void K(long j12) {
        f fVar = this.f112d;
        fVar.getClass();
        long jElapsedRealtime = SystemClock.elapsedRealtime() - j12;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(fVar.f119b.getF90370a());
        sb2.append(".absolute.");
        fVar.f118a.c(new y.c(AK.c.s(sb2, fVar.f120c, ".$-.from-click-to-drawing"), Long.valueOf(jElapsedRealtime), null));
    }

    @Override // A7.b
    public final void M(@k String str, boolean z12) {
        ScreenPerformanceTracker.a.c(this.f110b, g(str, z12), J.b.f90385a, null, 4);
    }

    @Override // A7.b
    public final void N(@k String str, boolean z12) {
        ScreenPerformanceTracker.a.d(this.f110b, g(str, z12), null, J.b.f90385a, null, 10);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    @k
    /* renamed from: O */
    public final String getF305951e() {
        return this.f110b.getF305951e();
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void P(@k String str, @k J j12, @l Integer num) {
        this.f110b.P(str, j12, num);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void a(long j12) {
        this.f110b.a(j12);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void b(@k RecyclerView recyclerView) {
        this.f110b.b(recyclerView);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void c(@k InterfaceC22983P interfaceC22983P, @k f.a aVar) {
        this.f110b.c(interfaceC22983P, aVar);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void d() {
        this.f110b.d();
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void e() {
        this.f110b.e();
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void f() {
        this.f110b.f();
    }

    @Override // A7.b
    public final void k() {
        AdvertScreen.f90278d.getClass();
        ScreenPerformanceTracker.a.c(this.f110b, AdvertScreen.f90279e, J.b.f90385a, null, 4);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void l() {
        this.f110b.l();
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void m(@k String str) {
        this.f110b.m(str);
    }

    @Override // A7.b
    public final void n(@k Throwable th2) {
        AdvertScreen.f90278d.getClass();
        String str = AdvertScreen.f90279e;
        ScreenPerformanceTracker.a.d(this.f110b, str, null, new J.a(th2), null, 10);
        this.f110b.m(str);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    @k
    public final C28493o o(@l AbstractC23662a.b bVar) {
        return this.f110b.o(bVar);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void p(@k h hVar) {
        this.f110b.p(hVar);
    }

    @Override // A7.b
    public final void q() {
        AdvertScreen.f90278d.getClass();
        ScreenPerformanceTracker.a.b(this.f110b, AdvertScreen.f90279e, 2);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void r(@k String str, @k ScreenPerformanceTracker.LoadingType loadingType, @k J j12, @l Integer num) {
        this.f110b.r(str, loadingType, j12, num);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void s(@k String str, @k ScreenPerformanceTracker.LoadingType loadingType) {
        this.f110b.s(str, loadingType);
    }

    @Override // com.avito.android.analytics.screens.tracker.ScreenPerformanceTracker
    public final void t(long j12) {
        this.f110b.t(j12);
    }

    @Override // A7.b
    public final void u(@k Throwable th2) {
        AdvertScreen.f90278d.getClass();
        ScreenPerformanceTracker.a.c(this.f110b, AdvertScreen.f90279e, new J.a(th2), null, 4);
    }

    @Override // A7.b
    public final void v(@k String str, @k Throwable th2, boolean z12) {
        ScreenPerformanceTracker.a.c(this.f110b, g(str, z12), new J.a(th2), null, 4);
    }

    @Override // A7.b
    @k
    public final com.avito.android.analytics.screens.tracker.degrade.b w() {
        return new com.avito.android.analytics.screens.tracker.degrade.b(this.f109a.f90806b.f90808b);
    }

    @Override // A7.b
    public final void y(@k String str, @k Throwable th2, boolean z12) {
        ScreenPerformanceTracker.a.d(this.f110b, g(str, z12), null, new J.a(th2), null, 10);
    }

    @Override // A7.b
    public final void z(@l SerpResultCategoryDetails serpResultCategoryDetails) {
        AdvertScreen.f90278d.getClass();
        String str = AdvertScreen.f90279e;
        ScreenPerformanceTracker.a.d(this.f110b, str, null, J.b.f90385a, null, 10);
        ScreenPerformanceTracker screenPerformanceTracker = this.f110b;
        screenPerformanceTracker.m(str);
        screenPerformanceTracker.G(serpResultCategoryDetails);
    }
}
