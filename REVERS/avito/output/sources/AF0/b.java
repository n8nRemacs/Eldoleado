package AF0;

import Y61.l;
import kotlin.Metadata;

/* compiled from: Api1StrSellerBannersGetResponse.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u0001B%\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0006\u0010\u0007R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\b\u001a\u0004\b\u000b\u0010\nR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\b\u001a\u0004\b\f\u0010\n¨\u0006\r"}, d2 = {"LAF0/b;", "", "", "value", "valueDark", "valueName", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "b", "c", "_avito_travel-seller-banners_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class b {

    @com.google.gson.annotations.c("value")
    @l
    private final String value;

    @com.google.gson.annotations.c("valueDark")
    @l
    private final String valueDark;

    @com.google.gson.annotations.c("valueName")
    @l
    private final String valueName;

    public b(@l String str, @l String str2, @l String str3) {
        this.value = str;
        this.valueDark = str2;
        this.valueName = str3;
    }

    @l
    /* renamed from: a, reason: from getter */
    public final String getValue() {
        return this.value;
    }

    @l
    /* renamed from: b, reason: from getter */
    public final String getValueDark() {
        return this.valueDark;
    }

    @l
    /* renamed from: c, reason: from getter */
    public final String getValueName() {
        return this.valueName;
    }
}
