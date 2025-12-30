package AG0;

import Y61.k;
import androidx.compose.runtime.internal.P;
import kotlin.Metadata;

/* compiled from: Api1TransactionPageGetResponse.kt */
@P
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\b\u001a\u0004\b\u000b\u0010\nR\u001a\u0010\u0005\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\b\u001a\u0004\b\f\u0010\n¨\u0006\r"}, d2 = {"LAG0/d;", "", "", "value", "valueDark", "valueName", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "b", "c", "_avito_universal-transaction_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class d {

    @com.google.gson.annotations.c("value")
    @k
    private final String value;

    @com.google.gson.annotations.c("valueDark")
    @k
    private final String valueDark;

    @com.google.gson.annotations.c("valueName")
    @k
    private final String valueName;

    public d(@k String str, @k String str2, @k String str3) {
        this.value = str;
        this.valueDark = str2;
        this.valueName = str3;
    }

    @k
    /* renamed from: a, reason: from getter */
    public final String getValue() {
        return this.value;
    }

    @k
    /* renamed from: b, reason: from getter */
    public final String getValueDark() {
        return this.valueDark;
    }

    @k
    /* renamed from: c, reason: from getter */
    public final String getValueName() {
        return this.valueName;
    }
}
