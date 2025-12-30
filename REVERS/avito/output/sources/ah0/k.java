package aH0;

import androidx.compose.foundation.H;
import com.avito.android.remote.model.category_parameters.AddressParameter;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: MultiGeoResponse.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0007\u0010\bR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\t\u001a\u0004\b\f\u0010\u000bR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\r\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"LaH0/k;", "", "", "error", AddressParameter.Value.JSON_WEB_TOKEN, "LaH0/h;", AddressParameter.TYPE, "<init>", "(Ljava/lang/String;Ljava/lang/String;LaH0/h;)V", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "c", "LaH0/h;", "a", "()LaH0/h;", "_avito_user-address_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class k {

    @com.google.gson.annotations.c(AddressParameter.TYPE)
    @Y61.l
    private final h address;

    @com.google.gson.annotations.c("error")
    @Y61.l
    private final String error;

    @com.google.gson.annotations.c(AddressParameter.Value.JSON_WEB_TOKEN)
    @Y61.k
    private final String jwt;

    public k(@Y61.l String str, @Y61.k String str2, @Y61.l h hVar) {
        this.error = str;
        this.jwt = str2;
        this.address = hVar;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final h getAddress() {
        return this.address;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final String getError() {
        return this.error;
    }

    @Y61.k
    /* renamed from: c, reason: from getter */
    public final String getJwt() {
        return this.jwt;
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof k)) {
            return false;
        }
        k kVar = (k) obj;
        return L.f(this.error, kVar.error) && L.f(this.jwt, kVar.jwt) && L.f(this.address, kVar.address);
    }

    public final int hashCode() {
        String str = this.error;
        int iD2 = H.d((str == null ? 0 : str.hashCode()) * 31, 31, this.jwt);
        h hVar = this.address;
        return iD2 + (hVar != null ? hVar.hashCode() : 0);
    }

    @Y61.k
    public final String toString() {
        return "MultiGeoResponse(error=" + this.error + ", jwt=" + this.jwt + ", address=" + this.address + ')';
    }
}
