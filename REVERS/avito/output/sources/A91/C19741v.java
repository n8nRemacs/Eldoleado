package a91;

import com.avito.android.remote.model.SearchParamsConverterKt;
import kotlin.Metadata;

/* compiled from: CreateItemChannelRequest.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u000e\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\b\u0010\tR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\n\u001a\u0004\b\u0010\u0010\fR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\n\u001a\u0004\b\u0011\u0010\f¨\u0006\u0012"}, d2 = {"La91/v;", "", "", "extra", "", "itemId", SearchParamsConverterKt.SOURCE, "xHash", "<init>", "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;)V", "Ljava/lang/String;", "getExtra", "()Ljava/lang/String;", "J", "getItemId", "()J", "getSource", "getXHash", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.v, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19741v {

    @com.google.gson.annotations.c("extra")
    @Y61.l
    private final String extra;

    @com.google.gson.annotations.c("itemId")
    private final long itemId;

    @com.google.gson.annotations.c(SearchParamsConverterKt.SOURCE)
    @Y61.l
    private final String source;

    @com.google.gson.annotations.c("xHash")
    @Y61.l
    private final String xHash;

    public C19741v(@Y61.l String str, long j12, @Y61.l String str2, @Y61.l String str3) {
        this.extra = str;
        this.itemId = j12;
        this.source = str2;
        this.xHash = str3;
    }
}
