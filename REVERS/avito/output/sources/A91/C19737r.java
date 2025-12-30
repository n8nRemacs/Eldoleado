package a91;

import com.avito.android.remote.model.Image;
import com.avito.android.remote.model.messenger.context.ChannelContext;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0016\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\n\u001a\u00020\u0004\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\r\u001a\u00020\u0004¢\u0006\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u001c\u0010\t\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u0013\u001a\u0004\b\u001c\u0010\u0015R\u001c\u0010\f\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\f\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010\r\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\r\u0010\u0013\u001a\u0004\b \u0010\u0015¨\u0006!"}, d2 = {"La91/r;", "", "Lcom/avito/android/remote/model/Image;", "avatar", "", "description", "La91/s;", "details", "La91/N;", "link", "name", "", "rating", ChannelContext.Item.USER_ID, "<init>", "(Lcom/avito/android/remote/model/Image;Ljava/lang/String;La91/s;La91/N;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/String;)V", "Lcom/avito/android/remote/model/Image;", "a", "()Lcom/avito/android/remote/model/Image;", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "La91/s;", "c", "()La91/s;", "La91/N;", "d", "()La91/N;", "e", "Ljava/lang/Double;", "f", "()Ljava/lang/Double;", "g", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.r, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19737r {

    @com.google.gson.annotations.c("avatar")
    @Y61.k
    private final Image avatar;

    @com.google.gson.annotations.c("description")
    @Y61.l
    private final String description;

    @com.google.gson.annotations.c("details")
    @Y61.l
    private final C19738s details;

    @com.google.gson.annotations.c("link")
    @Y61.l
    private final C19715N link;

    @com.google.gson.annotations.c("name")
    @Y61.k
    private final String name;

    @com.google.gson.annotations.c("rating")
    @Y61.l
    private final Double rating;

    @com.google.gson.annotations.c(ChannelContext.Item.USER_ID)
    @Y61.k
    private final String userId;

    public C19737r(@Y61.k Image image, @Y61.l String str, @Y61.l C19738s c19738s, @Y61.l C19715N c19715n, @Y61.k String str2, @Y61.l Double d12, @Y61.k String str3) {
        this.avatar = image;
        this.description = str;
        this.details = c19738s;
        this.link = c19715n;
        this.name = str2;
        this.rating = d12;
        this.userId = str3;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final Image getAvatar() {
        return this.avatar;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final C19738s getDetails() {
        return this.details;
    }

    @Y61.l
    /* renamed from: d, reason: from getter */
    public final C19715N getLink() {
        return this.link;
    }

    @Y61.k
    /* renamed from: e, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Y61.l
    /* renamed from: f, reason: from getter */
    public final Double getRating() {
        return this.rating;
    }

    @Y61.k
    /* renamed from: g, reason: from getter */
    public final String getUserId() {
        return this.userId;
    }
}
