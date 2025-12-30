package aH0;

import androidx.appcompat.app.r;
import androidx.compose.foundation.H;
import com.avito.android.geo_common.model.location_picker.LocationPickerAddressType;
import com.avito.android.remote.model.category_parameters.AddressParameter;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: AddNewAddressResultV2.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0004\b\n\u0010\u000bR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0006\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\f\u001a\u0004\b\u0012\u0010\u000eR\u001a\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\t\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0013\u001a\u0004\b\u0016\u0010\u0015¨\u0006\u0017"}, d2 = {"LaH0/b;", "", "", AddressParameter.TYPE, "Lcom/avito/android/geo_common/model/location_picker/LocationPickerAddressType;", "addressKind", "geoSessionId", "", "addressesCount", "addressId", "<init>", "(Ljava/lang/String;Lcom/avito/android/geo_common/model/location_picker/LocationPickerAddressType;Ljava/lang/String;II)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "Lcom/avito/android/geo_common/model/location_picker/LocationPickerAddressType;", "c", "()Lcom/avito/android/geo_common/model/location_picker/LocationPickerAddressType;", "getGeoSessionId", "I", "getAddressesCount", "()I", "b", "_avito_user-address_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class b {

    @com.google.gson.annotations.c("saveAddress")
    @Y61.k
    private final String address;

    @com.google.gson.annotations.c("saveAddressId")
    private final int addressId;

    @com.google.gson.annotations.c("saveAddressKind")
    @Y61.k
    private final LocationPickerAddressType addressKind;

    @com.google.gson.annotations.c("addressesCount")
    private final int addressesCount;

    @com.google.gson.annotations.c("geoSessionId")
    @Y61.k
    private final String geoSessionId;

    public b(@Y61.k String str, @Y61.k LocationPickerAddressType locationPickerAddressType, @Y61.k String str2, int i12, int i13) {
        this.address = str;
        this.addressKind = locationPickerAddressType;
        this.geoSessionId = str2;
        this.addressesCount = i12;
        this.addressId = i13;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final String getAddress() {
        return this.address;
    }

    /* renamed from: b, reason: from getter */
    public final int getAddressId() {
        return this.addressId;
    }

    @Y61.k
    /* renamed from: c, reason: from getter */
    public final LocationPickerAddressType getAddressKind() {
        return this.addressKind;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return L.f(this.address, bVar.address) && this.addressKind == bVar.addressKind && L.f(this.geoSessionId, bVar.geoSessionId) && this.addressesCount == bVar.addressesCount && this.addressId == bVar.addressId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.addressId) + r.e(this.addressesCount, H.d((this.addressKind.hashCode() + (this.address.hashCode() * 31)) * 31, 31, this.geoSessionId), 31);
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("AddNewAddressResultV2(address=");
        sb2.append(this.address);
        sb2.append(", addressKind=");
        sb2.append(this.addressKind);
        sb2.append(", geoSessionId=");
        sb2.append(this.geoSessionId);
        sb2.append(", addressesCount=");
        sb2.append(this.addressesCount);
        sb2.append(", addressId=");
        return r.t(sb2, this.addressId, ')');
    }
}
