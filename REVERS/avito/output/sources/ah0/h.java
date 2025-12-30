package aH0;

import androidx.compose.foundation.H;
import com.avito.android.remote.model.SearchParamsConverterKt;
import com.avito.android.remote.model.category_parameters.AddressParameter;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: MultiGeoAddress.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0010\u001a\u0004\b\u0013\u0010\u0012R\u001a\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\n\u001a\u00020\t8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"LaH0/h;", "", "LaH0/j;", "displayAddress", "", "addressId", SearchParamsConverterKt.LOCATION_ID, "", AddressParameter.TYPE, "LaH0/i;", "coordinate", "<init>", "(LaH0/j;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;LaH0/i;)V", "LaH0/j;", "d", "()LaH0/j;", "Ljava/lang/Integer;", "b", "()Ljava/lang/Integer;", "e", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "LaH0/i;", "c", "()LaH0/i;", "_avito_user-address_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class h {

    @com.google.gson.annotations.c(AddressParameter.TYPE)
    @Y61.k
    private final String address;

    @com.google.gson.annotations.c("addressId")
    @Y61.l
    private final Integer addressId;

    @com.google.gson.annotations.c("coordinate")
    @Y61.k
    private final i coordinate;

    @com.google.gson.annotations.c("displayAddress")
    @Y61.k
    private final j displayAddress;

    @com.google.gson.annotations.c(SearchParamsConverterKt.LOCATION_ID)
    @Y61.l
    private final Integer locationId;

    public h(@Y61.k j jVar, @Y61.l Integer num, @Y61.l Integer num2, @Y61.k String str, @Y61.k i iVar) {
        this.displayAddress = jVar;
        this.addressId = num;
        this.locationId = num2;
        this.address = str;
        this.coordinate = iVar;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final String getAddress() {
        return this.address;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final Integer getAddressId() {
        return this.addressId;
    }

    @Y61.k
    /* renamed from: c, reason: from getter */
    public final i getCoordinate() {
        return this.coordinate;
    }

    @Y61.k
    /* renamed from: d, reason: from getter */
    public final j getDisplayAddress() {
        return this.displayAddress;
    }

    @Y61.l
    /* renamed from: e, reason: from getter */
    public final Integer getLocationId() {
        return this.locationId;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof h)) {
            return false;
        }
        h hVar = (h) obj;
        return L.f(this.displayAddress, hVar.displayAddress) && L.f(this.addressId, hVar.addressId) && L.f(this.locationId, hVar.locationId) && L.f(this.address, hVar.address) && L.f(this.coordinate, hVar.coordinate);
    }

    public final int hashCode() {
        int iHashCode = this.displayAddress.hashCode() * 31;
        Integer num = this.addressId;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.locationId;
        return this.coordinate.hashCode() + H.d((iHashCode2 + (num2 != null ? num2.hashCode() : 0)) * 31, 31, this.address);
    }

    @Y61.k
    public final String toString() {
        return "MultiGeoAddress(displayAddress=" + this.displayAddress + ", addressId=" + this.addressId + ", locationId=" + this.locationId + ", address=" + this.address + ", coordinate=" + this.coordinate + ')';
    }
}
