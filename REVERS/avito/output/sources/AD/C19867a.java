package ad;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.C22026a;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: CpuInfo.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\b\u0010\tR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\r\u001a\u0004\b\u0010\u0010\u000fR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\r\u001a\u0004\b\u0011\u0010\u000f¨\u0006\u0012"}, d2 = {"Lad/a;", "", "", "coresCount", "", "architecture", "manufacturer", "model", "<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "I", "b", "()I", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "c", "d", "_common_antifraud_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ad.a, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final /* data */ class C19867a {

    @com.google.gson.annotations.c("architecture")
    @l
    private final String architecture;

    @com.google.gson.annotations.c("coresCount")
    private final int coresCount;

    @com.google.gson.annotations.c("manufacturer")
    @l
    private final String manufacturer;

    @com.google.gson.annotations.c("model")
    @l
    private final String model;

    public C19867a(int i12, @l String str, @l String str2, @l String str3) {
        this.coresCount = i12;
        this.architecture = str;
        this.manufacturer = str2;
        this.model = str3;
    }

    @l
    /* renamed from: a, reason: from getter */
    public final String getArchitecture() {
        return this.architecture;
    }

    /* renamed from: b, reason: from getter */
    public final int getCoresCount() {
        return this.coresCount;
    }

    @l
    /* renamed from: c, reason: from getter */
    public final String getManufacturer() {
        return this.manufacturer;
    }

    @l
    /* renamed from: d, reason: from getter */
    public final String getModel() {
        return this.model;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C19867a)) {
            return false;
        }
        C19867a c19867a = (C19867a) obj;
        return this.coresCount == c19867a.coresCount && L.f(this.architecture, c19867a.architecture) && L.f(this.manufacturer, c19867a.manufacturer) && L.f(this.model, c19867a.model);
    }

    public final int hashCode() {
        int iHashCode = Integer.hashCode(this.coresCount) * 31;
        String str = this.architecture;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.manufacturer;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.model;
        return iHashCode3 + (str3 != null ? str3.hashCode() : 0);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("CpuInfo(coresCount=");
        sb2.append(this.coresCount);
        sb2.append(", architecture=");
        sb2.append(this.architecture);
        sb2.append(", manufacturer=");
        sb2.append(this.manufacturer);
        sb2.append(", model=");
        return C22026a.c(sb2, this.model, ')');
    }
}
