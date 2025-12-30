package A50;

import Y61.k;
import Y61.l;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;
import kotlin.jvm.internal.L;
import kotlin.reflect.d;

/* compiled from: PaidServiceMapping.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA50/a;", "", "_avito_paid-services_abstract"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final /* data */ class a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final d<? extends DeepLink> f98a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final b<? extends DeepLink> f99b;

    public a(@k d<? extends DeepLink> dVar, @k b<? extends DeepLink> bVar) {
        this.f98a = dVar;
        this.f99b = bVar;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return L.f(this.f98a, aVar.f98a) && L.f(this.f99b, aVar.f99b);
    }

    public final int hashCode() {
        return this.f99b.hashCode() + (this.f98a.hashCode() * 31);
    }

    @k
    public final String toString() {
        return "PaidServiceMapping(deeplinkClass=" + this.f98a + ", factory=" + this.f99b + ')';
    }
}
