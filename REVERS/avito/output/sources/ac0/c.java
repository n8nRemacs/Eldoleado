package AC0;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: CptInfoCardContentProgressItem.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAC0/c;", "", "_avito_tariff-cpt_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class c {

    /* renamed from: a, reason: collision with root package name */
    public final float f198a;

    /* renamed from: b, reason: collision with root package name */
    @l
    public final iC0.e f199b;

    /* renamed from: c, reason: collision with root package name */
    @l
    public final iC0.e f200c;

    public c(float f12, @l iC0.e eVar, @l iC0.e eVar2) {
        this.f198a = f12;
        this.f199b = eVar;
        this.f200c = eVar2;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return Float.compare(this.f198a, cVar.f198a) == 0 && L.f(this.f199b, cVar.f199b) && L.f(this.f200c, cVar.f200c);
    }

    public final int hashCode() {
        int iHashCode = Float.hashCode(this.f198a) * 31;
        iC0.e eVar = this.f199b;
        int iHashCode2 = (iHashCode + (eVar == null ? 0 : eVar.hashCode())) * 31;
        iC0.e eVar2 = this.f200c;
        return iHashCode2 + (eVar2 != null ? eVar2.hashCode() : 0);
    }

    @k
    public final String toString() {
        return "CptInfoCardContentProgressItem(value=" + this.f198a + ", fillColor=" + this.f199b + ", emptyColor=" + this.f200c + ')';
    }
}
