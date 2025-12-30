package aH0;

import androidx.compose.runtime.C22026a;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: LocationPickerDetails.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\r\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0007\u0010\bR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\t\u001a\u0004\b\f\u0010\u000bR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\t\u001a\u0004\b\r\u0010\u000bR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\t\u001a\u0004\b\u000e\u0010\u000b¨\u0006\u000f"}, d2 = {"LaH0/g;", "", "", "apt", "floor", "entrance", "comment", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "d", "c", "b", "_avito_user-address_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class g {

    @com.google.gson.annotations.c("apt")
    @Y61.l
    private final String apt;

    @com.google.gson.annotations.c("comment")
    @Y61.l
    private final String comment;

    @com.google.gson.annotations.c("entrance")
    @Y61.l
    private final String entrance;

    @com.google.gson.annotations.c("floor")
    @Y61.l
    private final String floor;

    public g(@Y61.l String str, @Y61.l String str2, @Y61.l String str3, @Y61.l String str4) {
        this.apt = str;
        this.floor = str2;
        this.entrance = str3;
        this.comment = str4;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final String getApt() {
        return this.apt;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final String getComment() {
        return this.comment;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final String getEntrance() {
        return this.entrance;
    }

    @Y61.l
    /* renamed from: d, reason: from getter */
    public final String getFloor() {
        return this.floor;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof g)) {
            return false;
        }
        g gVar = (g) obj;
        return L.f(this.apt, gVar.apt) && L.f(this.floor, gVar.floor) && L.f(this.entrance, gVar.entrance) && L.f(this.comment, gVar.comment);
    }

    public final int hashCode() {
        String str = this.apt;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.floor;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.entrance;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.comment;
        return iHashCode3 + (str4 != null ? str4.hashCode() : 0);
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("LocationPickerDetails(apt=");
        sb2.append(this.apt);
        sb2.append(", floor=");
        sb2.append(this.floor);
        sb2.append(", entrance=");
        sb2.append(this.entrance);
        sb2.append(", comment=");
        return C22026a.c(sb2, this.comment, ')');
    }
}
