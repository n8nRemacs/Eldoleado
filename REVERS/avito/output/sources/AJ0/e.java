package aj0;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.foundation.H;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.registration_self_employment_redirect_screen.models.ButtonAction;
import com.avito.android.registration_self_employment_redirect_screen.steps_list.SelfEmploymentStepItem;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.C42784z0;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: SelfEmploymentRedirectUiState.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Laj0/e;", "", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes16.dex */
public final /* data */ class e {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final String f21139a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final String f21140b;

    /* renamed from: c, reason: collision with root package name */
    @k
    public final List<SelfEmploymentStepItem> f21141c;

    /* renamed from: d, reason: collision with root package name */
    @k
    public final List<ButtonAction> f21142d;

    /* renamed from: e, reason: collision with root package name */
    @l
    public final DeepLink f21143e;

    /* renamed from: f, reason: collision with root package name */
    @k
    public final com.avito.android.lib.design.nav_bar.a f21144f;

    /* renamed from: g, reason: collision with root package name */
    public final boolean f21145g;

    /* renamed from: h, reason: collision with root package name */
    public final boolean f21146h;

    /* renamed from: i, reason: collision with root package name */
    public final boolean f21147i;

    /* renamed from: j, reason: collision with root package name */
    public final boolean f21148j;

    public e(@k String str, @k String str2, @k List<SelfEmploymentStepItem> list, @k List<ButtonAction> list2, @l DeepLink deepLink, @k com.avito.android.lib.design.nav_bar.a aVar, boolean z12, boolean z13, boolean z14, boolean z15) {
        this.f21139a = str;
        this.f21140b = str2;
        this.f21141c = list;
        this.f21142d = list2;
        this.f21143e = deepLink;
        this.f21144f = aVar;
        this.f21145g = z12;
        this.f21146h = z13;
        this.f21147i = z14;
        this.f21148j = z15;
    }

    public static e a(e eVar, boolean z12, boolean z13) {
        String str = eVar.f21139a;
        String str2 = eVar.f21140b;
        List<SelfEmploymentStepItem> list = eVar.f21141c;
        List<ButtonAction> list2 = eVar.f21142d;
        DeepLink deepLink = eVar.f21143e;
        com.avito.android.lib.design.nav_bar.a aVar = eVar.f21144f;
        eVar.getClass();
        return new e(str, str2, list, list2, deepLink, aVar, false, false, z12, z13);
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return L.f(this.f21139a, eVar.f21139a) && L.f(this.f21140b, eVar.f21140b) && L.f(this.f21141c, eVar.f21141c) && L.f(this.f21142d, eVar.f21142d) && L.f(this.f21143e, eVar.f21143e) && L.f(this.f21144f, eVar.f21144f) && this.f21145g == eVar.f21145g && this.f21146h == eVar.f21146h && this.f21147i == eVar.f21147i && this.f21148j == eVar.f21148j;
    }

    public final int hashCode() {
        int iE2 = H.e(H.e(H.d(this.f21139a.hashCode() * 31, 31, this.f21140b), 31, this.f21141c), 31, this.f21142d);
        DeepLink deepLink = this.f21143e;
        return Boolean.hashCode(this.f21148j) + r.i(r.i(r.i((this.f21144f.hashCode() + ((iE2 + (deepLink == null ? 0 : deepLink.hashCode())) * 31)) * 31, 31, this.f21145g), 31, this.f21146h), 31, this.f21147i);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("SelfEmploymentRedirectUiState(title=");
        sb2.append(this.f21139a);
        sb2.append(", description=");
        sb2.append(this.f21140b);
        sb2.append(", steps=");
        sb2.append(this.f21141c);
        sb2.append(", actions=");
        sb2.append(this.f21142d);
        sb2.append(", supportButtonDeepLink=");
        sb2.append(this.f21143e);
        sb2.append(", navBarState=");
        sb2.append(this.f21144f);
        sb2.append(", needShowNavBarRightButtons=");
        sb2.append(this.f21145g);
        sb2.append(", isContent=");
        sb2.append(this.f21146h);
        sb2.append(", isError=");
        sb2.append(this.f21147i);
        sb2.append(", isLoading=");
        return r.x(sb2, this.f21148j, ')');
    }

    public e(String str, String str2, List list, List list2, DeepLink deepLink, com.avito.android.lib.design.nav_bar.a aVar, boolean z12, boolean z13, boolean z14, boolean z15, int i12, C42822w c42822w) {
        this((i12 & 1) != 0 ? "" : str, (i12 & 2) != 0 ? "" : str2, (i12 & 4) != 0 ? C42784z0.f406748b : list, (i12 & 8) != 0 ? C42784z0.f406748b : list2, (i12 & 16) != 0 ? null : deepLink, aVar, (i12 & 64) != 0 ? false : z12, (i12 & 128) != 0 ? false : z13, (i12 & 256) != 0 ? false : z14, (i12 & 512) != 0 ? true : z15);
    }
}
