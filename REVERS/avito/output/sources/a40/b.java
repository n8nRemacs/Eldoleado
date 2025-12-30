package A40;

import Y61.k;
import Y61.l;
import com.avito.android.analytics.InterfaceC28373a;
import com.avito.android.onboarding.dialog.analytics.OnboardingCloseType;
import java.util.ArrayList;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: OnboardingAnalyticsInteractor.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA40/b;", "LA40/a;", "_avito_onboarding_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final class b implements a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final InterfaceC28373a f87a;

    /* renamed from: b, reason: collision with root package name */
    @l
    public final String f88b;

    /* renamed from: d, reason: collision with root package name */
    @l
    public String f90d;

    /* renamed from: e, reason: collision with root package name */
    @l
    public String f91e;

    /* renamed from: g, reason: collision with root package name */
    @l
    public Integer f93g;

    /* renamed from: c, reason: collision with root package name */
    public boolean f89c = true;

    /* renamed from: f, reason: collision with root package name */
    @k
    public ArrayList f92f = new ArrayList();

    @Inject
    public b(@k InterfaceC28373a interfaceC28373a, @com.avito.android.onboarding.dialog.di.b @l String str) {
        this.f87a = interfaceC28373a;
        this.f88b = str;
    }

    @Override // A40.a
    public final void a(@l String str) {
        this.f89c = true;
        this.f90d = str;
        this.f92f = new ArrayList();
    }

    @Override // A40.a
    public final void b(@l String str, @l ArrayList arrayList, boolean z12) {
        this.f87a.c(new B40.a(this.f90d, str, arrayList, this.f88b, this.f91e, this.f93g));
        this.f89c = z12;
    }

    @Override // A40.a
    public final void c(@l Integer num, @l String str) {
        this.f91e = str;
        this.f93g = num;
        if (this.f92f.contains(num)) {
            return;
        }
        this.f87a.c(new B40.c(this.f90d, str, this.f88b, num));
        this.f92f.add(num);
    }

    @Override // A40.a
    public final void d(@l OnboardingCloseType onboardingCloseType) {
        String str;
        if (this.f89c) {
            int iOrdinal = onboardingCloseType.ordinal();
            if (iOrdinal == 0) {
                str = "swipe";
            } else if (iOrdinal == 1) {
                str = "close_with_cross";
            } else if (iOrdinal == 2) {
                str = "close_outside";
            } else {
                if (iOrdinal != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                str = "close_with_error";
            }
            String str2 = str;
            this.f87a.c(new B40.b(str2, this.f90d, this.f88b, this.f91e, this.f93g));
            if (onboardingCloseType == OnboardingCloseType.f208742d) {
                this.f89c = false;
            }
        }
    }
}
