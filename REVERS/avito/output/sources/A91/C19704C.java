package a91;

import com.avito.android.remote.model.messenger.context_actions.ContextActionHandler;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0002¢\u0006\u0004\b\b\u0010\tR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\n\u001a\u0004\b\r\u0010\fR\u001a\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\n\u001a\u0004\b\u0011\u0010\f¨\u0006\u0012"}, d2 = {"La91/C;", "", "", "domain", "imageId", "", "trusted", ContextActionHandler.Link.URL, "<init>", "(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "b", "Z", "c", "()Z", "d", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.C, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19704C {

    @com.google.gson.annotations.c("domain")
    @Y61.k
    private final String domain;

    @com.google.gson.annotations.c("imageId")
    @Y61.k
    private final String imageId;

    @com.google.gson.annotations.c("trusted")
    private final boolean trusted;

    @com.google.gson.annotations.c(ContextActionHandler.Link.URL)
    @Y61.k
    private final String url;

    public C19704C(@Y61.k String str, @Y61.k String str2, boolean z12, @Y61.k String str3) {
        this.domain = str;
        this.imageId = str2;
        this.trusted = z12;
        this.url = str3;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final String getDomain() {
        return this.domain;
    }

    @Y61.k
    /* renamed from: b, reason: from getter */
    public final String getImageId() {
        return this.imageId;
    }

    /* renamed from: c, reason: from getter */
    public final boolean getTrusted() {
        return this.trusted;
    }

    @Y61.k
    /* renamed from: d, reason: from getter */
    public final String getUrl() {
        return this.url;
    }
}
