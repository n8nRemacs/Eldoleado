package AC0;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import iC0.C41267a;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: CptInfoCardContentButtonItem.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAC0/a;", "", "_avito_tariff-cpt_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class a {

    /* renamed from: a, reason: collision with root package name */
    @l
    public final C41267a f195a;

    public a(@l C41267a c41267a) {
        this.f195a = c41267a;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof a) && L.f(this.f195a, ((a) obj).f195a);
    }

    public final int hashCode() {
        C41267a c41267a = this.f195a;
        if (c41267a == null) {
            return 0;
        }
        return c41267a.hashCode();
    }

    @k
    public final String toString() {
        return "CptInfoCardContentButtonItem(button=" + this.f195a + ')';
    }
}
