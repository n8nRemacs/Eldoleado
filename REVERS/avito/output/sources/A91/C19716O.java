package a91;

import com.avito.android.remote.model.messenger.context_actions.ContextActionHandler;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0010\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u0002¢\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0004\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\r\u001a\u0004\b\u0010\u0010\u000fR\u001a\u0010\u0005\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\r\u001a\u0004\b\u0011\u0010\u000fR\u001a\u0010\u0006\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\r\u001a\u0004\b\u0012\u0010\u000fR\u001a\u0010\u0007\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\r\u001a\u0004\b\u0013\u0010\u000fR\u001a\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\n\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\r\u001a\u0004\b\u0017\u0010\u000f¨\u0006\u0018"}, d2 = {"La91/O;", "", "", "description", "domain", "imageId", "subtitle", "title", "", "trusted", ContextActionHandler.Link.URL, "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "b", "c", "d", "e", "Z", "f", "()Z", "g", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.O, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19716O {

    @com.google.gson.annotations.c("description")
    @Y61.k
    private final String description;

    @com.google.gson.annotations.c("domain")
    @Y61.k
    private final String domain;

    @com.google.gson.annotations.c("imageId")
    @Y61.k
    private final String imageId;

    @com.google.gson.annotations.c("subtitle")
    @Y61.k
    private final String subtitle;

    @com.google.gson.annotations.c("title")
    @Y61.k
    private final String title;

    @com.google.gson.annotations.c("trusted")
    private final boolean trusted;

    @com.google.gson.annotations.c(ContextActionHandler.Link.URL)
    @Y61.k
    private final String url;

    public C19716O(@Y61.k String str, @Y61.k String str2, @Y61.k String str3, @Y61.k String str4, @Y61.k String str5, boolean z12, @Y61.k String str6) {
        this.description = str;
        this.domain = str2;
        this.imageId = str3;
        this.subtitle = str4;
        this.title = str5;
        this.trusted = z12;
        this.url = str6;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Y61.k
    /* renamed from: b, reason: from getter */
    public final String getDomain() {
        return this.domain;
    }

    @Y61.k
    /* renamed from: c, reason: from getter */
    public final String getImageId() {
        return this.imageId;
    }

    @Y61.k
    /* renamed from: d, reason: from getter */
    public final String getSubtitle() {
        return this.subtitle;
    }

    @Y61.k
    /* renamed from: e, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: f, reason: from getter */
    public final boolean getTrusted() {
        return this.trusted;
    }

    @Y61.k
    /* renamed from: g, reason: from getter */
    public final String getUrl() {
        return this.url;
    }
}
