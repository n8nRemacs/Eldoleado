package aH0;

import androidx.appcompat.app.r;
import androidx.compose.foundation.H;
import com.avito.android.geo_common.model.location_picker.LocationPickerAddressType;
import com.avito.android.remote.model.category_parameters.AddressParameter;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: AddressEditInfoResult.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\b\u0086\b\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\u000b\u001a\u00020\t\u0012\u0006\u0010\r\u001a\u00020\f\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e¢\u0006\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0006\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0012\u001a\u0004\b\u0018\u0010\u0014R\u001a\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0019\u001a\u0004\b\b\u0010\u001aR\u001c\u0010\n\u001a\u0004\u0018\u00010\t8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u000b\u001a\u00020\t8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000b\u0010\u001b\u001a\u0004\b\u001e\u0010\u001dR\u001a\u0010\r\u001a\u00020\f8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\r\u0010\u001f\u001a\u0004\b \u0010!R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u000e8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000f\u0010\"\u001a\u0004\b#\u0010$¨\u0006%"}, d2 = {"LaH0/f;", "", "", "id", "LaH0/d;", "coords", "geoAddressId", "", "isDefault", "", "title", AddressParameter.TYPE, "Lcom/avito/android/geo_common/model/location_picker/LocationPickerAddressType;", "addressType", "LaH0/g;", "details", "<init>", "(ILaH0/d;IZLjava/lang/String;Ljava/lang/String;Lcom/avito/android/geo_common/model/location_picker/LocationPickerAddressType;LaH0/g;)V", "I", "e", "()I", "LaH0/d;", "c", "()LaH0/d;", "getGeoAddressId", "Z", "()Z", "Ljava/lang/String;", "getTitle", "()Ljava/lang/String;", "a", "Lcom/avito/android/geo_common/model/location_picker/LocationPickerAddressType;", "b", "()Lcom/avito/android/geo_common/model/location_picker/LocationPickerAddressType;", "LaH0/g;", "d", "()LaH0/g;", "_avito_user-address_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class f {

    @com.google.gson.annotations.c(AddressParameter.TYPE)
    @Y61.k
    private final String address;

    @com.google.gson.annotations.c("type")
    @Y61.k
    private final LocationPickerAddressType addressType;

    @com.google.gson.annotations.c("coords")
    @Y61.k
    private final d coords;

    @com.google.gson.annotations.c("details")
    @Y61.l
    private final g details;

    @com.google.gson.annotations.c("geoAddressId")
    private final int geoAddressId;

    @com.google.gson.annotations.c("id")
    private final int id;

    @com.google.gson.annotations.c("isDefault")
    private final boolean isDefault;

    @com.google.gson.annotations.c("title")
    @Y61.l
    private final String title;

    public f(int i12, @Y61.k d dVar, int i13, boolean z12, @Y61.l String str, @Y61.k String str2, @Y61.k LocationPickerAddressType locationPickerAddressType, @Y61.l g gVar) {
        this.id = i12;
        this.coords = dVar;
        this.geoAddressId = i13;
        this.isDefault = z12;
        this.title = str;
        this.address = str2;
        this.addressType = locationPickerAddressType;
        this.details = gVar;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final String getAddress() {
        return this.address;
    }

    @Y61.k
    /* renamed from: b, reason: from getter */
    public final LocationPickerAddressType getAddressType() {
        return this.addressType;
    }

    @Y61.k
    /* renamed from: c, reason: from getter */
    public final d getCoords() {
        return this.coords;
    }

    @Y61.l
    /* renamed from: d, reason: from getter */
    public final g getDetails() {
        return this.details;
    }

    /* renamed from: e, reason: from getter */
    public final int getId() {
        return this.id;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return this.id == fVar.id && L.f(this.coords, fVar.coords) && this.geoAddressId == fVar.geoAddressId && this.isDefault == fVar.isDefault && L.f(this.title, fVar.title) && L.f(this.address, fVar.address) && this.addressType == fVar.addressType && L.f(this.details, fVar.details);
    }

    public final int hashCode() {
        int i12 = r.i(r.e(this.geoAddressId, (this.coords.hashCode() + (Integer.hashCode(this.id) * 31)) * 31, 31), 31, this.isDefault);
        String str = this.title;
        int iHashCode = (this.addressType.hashCode() + H.d((i12 + (str == null ? 0 : str.hashCode())) * 31, 31, this.address)) * 31;
        g gVar = this.details;
        return iHashCode + (gVar != null ? gVar.hashCode() : 0);
    }

    @Y61.k
    public final String toString() {
        return "DetailsAddress(id=" + this.id + ", coords=" + this.coords + ", geoAddressId=" + this.geoAddressId + ", isDefault=" + this.isDefault + ", title=" + this.title + ", address=" + this.address + ", addressType=" + this.addressType + ", details=" + this.details + ')';
    }

    public /* synthetic */ f(int i12, d dVar, int i13, boolean z12, String str, String str2, LocationPickerAddressType locationPickerAddressType, g gVar, int i14, C42822w c42822w) {
        this(i12, dVar, i13, z12, (i14 & 16) != 0 ? null : str, str2, locationPickerAddressType, (i14 & 128) != 0 ? null : gVar);
    }
}
