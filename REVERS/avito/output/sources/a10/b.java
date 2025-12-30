package A10;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.C22026a;
import androidx.compose.runtime.F3;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: DeeplinkAction.kt */
@F3
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0081\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA10/b;", "", "_avito_mortgage-calculator_util"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final /* data */ class b {

    /* renamed from: a, reason: collision with root package name */
    @l
    public final DeepLink f50a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final String f51b;

    public b(@l DeepLink deepLink, @k String str) {
        this.f50a = deepLink;
        this.f51b = str;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return L.f(this.f50a, bVar.f50a) && L.f(this.f51b, bVar.f51b);
    }

    public final int hashCode() {
        DeepLink deepLink = this.f50a;
        return this.f51b.hashCode() + ((deepLink == null ? 0 : deepLink.hashCode()) * 31);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("DeeplinkAction(deeplink=");
        sb2.append(this.f50a);
        sb2.append(", text=");
        return C22026a.c(sb2, this.f51b, ')');
    }
}
