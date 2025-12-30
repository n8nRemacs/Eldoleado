package aH0;

import androidx.compose.runtime.C22026a;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: MultiGeoAddress.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u0007\u001a\u0004\b\b\u0010\tR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\u0007\u001a\u0004\b\n\u0010\t¨\u0006\u000b"}, d2 = {"LaH0/j;", "", "", "locality", "house", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "a", "_avito_user-address_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class j {

    @com.google.gson.annotations.c("house")
    @Y61.l
    private final String house;

    @com.google.gson.annotations.c("locality")
    @Y61.l
    private final String locality;

    public j(@Y61.l String str, @Y61.l String str2) {
        this.locality = str;
        this.house = str2;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final String getHouse() {
        return this.house;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final String getLocality() {
        return this.locality;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof j)) {
            return false;
        }
        j jVar = (j) obj;
        return L.f(this.locality, jVar.locality) && L.f(this.house, jVar.house);
    }

    public final int hashCode() {
        String str = this.locality;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.house;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("MultiGeoDisplayAddress(locality=");
        sb2.append(this.locality);
        sb2.append(", house=");
        return C22026a.c(sb2, this.house, ')');
    }
}
