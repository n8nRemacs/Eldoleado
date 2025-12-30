package a91;

import com.avito.android.remote.model.SearchParamsConverterKt;
import com.avito.android.remote.model.messenger.context.ChannelContext;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b%\u0018\u00002\u00020\u0001B£\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\u0011\u001a\u00020\u0002\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0015\u001a\u00020\u000b\u0012\u0006\u0010\u0016\u001a\u00020\u000b\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u000b¢\u0006\u0004\b\u0018\u0010\u0019R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010 \u001a\u0004\b!\u0010\"R\u001a\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010 \u001a\u0004\b#\u0010\"R\u001a\u0010\b\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u001a\u001a\u0004\b$\u0010\u001cR\u001c\u0010\n\u001a\u0004\u0018\u00010\t8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010%\u001a\u0004\b&\u0010'R\u001c\u0010\f\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\f\u0010(\u001a\u0004\b)\u0010*R\u001c\u0010\r\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\r\u0010\u001d\u001a\u0004\b+\u0010\u001fR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000e\u0010\u001d\u001a\u0004\b,\u0010\u001fR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000f\u0010\u001d\u001a\u0004\b-\u0010\u001fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0010\u0010(\u001a\u0004\b.\u0010*R\u001a\u0010\u0011\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0011\u0010\u001a\u001a\u0004\b/\u0010\u001cR\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u00128\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0013\u00100\u001a\u0004\b1\u00102R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0014\u0010\u001d\u001a\u0004\b3\u0010\u001fR\u001a\u0010\u0015\u001a\u00020\u000b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0015\u0010(\u001a\u0004\b4\u0010*R\u001a\u0010\u0016\u001a\u00020\u000b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0016\u0010(\u001a\u0004\b5\u0010*R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0017\u0010(\u001a\u0004\b6\u0010*¨\u00067"}, d2 = {"La91/F;", "", "", "categoryId", "datetime", "", "deleted", ChannelContext.Item.HIDE_PHONE, "id", "La91/D;", "images", "", "location", SearchParamsConverterKt.LOCATION_ID, "microcatId", "price", "priceString", "rootCategoryId", "La91/l0;", ChannelContext.SHARED_LOCATION, "statusId", "title", "urlPath", ChannelContext.Item.USER_ID, "<init>", "(JLjava/lang/Long;ZZJLa91/D;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/String;JLa91/l0;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "J", "a", "()J", "Ljava/lang/Long;", "getDatetime", "()Ljava/lang/Long;", "Z", "b", "()Z", "c", "d", "La91/D;", "e", "()La91/D;", "Ljava/lang/String;", "getLocation", "()Ljava/lang/String;", "getLocationId", "getMicrocatId", "getPrice", "f", "getRootCategoryId", "La91/l0;", "g", "()La91/l0;", "getStatusId", "h", "getUrlPath", "getUserId", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.F, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19707F {

    @com.google.gson.annotations.c("categoryId")
    private final long categoryId;

    @com.google.gson.annotations.c("datetime")
    @Y61.l
    private final Long datetime;

    @com.google.gson.annotations.c("deleted")
    private final boolean deleted;

    @com.google.gson.annotations.c(ChannelContext.Item.HIDE_PHONE)
    private final boolean hidePhone;

    @com.google.gson.annotations.c("id")
    private final long id;

    @com.google.gson.annotations.c("images")
    @Y61.l
    private final C19705D images;

    @com.google.gson.annotations.c("location")
    @Y61.l
    private final String location;

    @com.google.gson.annotations.c(SearchParamsConverterKt.LOCATION_ID)
    @Y61.l
    private final Long locationId;

    @com.google.gson.annotations.c("microcatId")
    @Y61.l
    private final Long microcatId;

    @com.google.gson.annotations.c("price")
    @Y61.l
    private final Long price;

    @com.google.gson.annotations.c("priceString")
    @Y61.l
    private final String priceString;

    @com.google.gson.annotations.c("rootCategoryId")
    private final long rootCategoryId;

    @com.google.gson.annotations.c(ChannelContext.SHARED_LOCATION)
    @Y61.l
    private final l0 sharedLocation;

    @com.google.gson.annotations.c("statusId")
    @Y61.l
    private final Long statusId;

    @com.google.gson.annotations.c("title")
    @Y61.k
    private final String title;

    @com.google.gson.annotations.c("urlPath")
    @Y61.k
    private final String urlPath;

    @com.google.gson.annotations.c(ChannelContext.Item.USER_ID)
    @Y61.l
    private final String userId;

    public C19707F(long j12, @Y61.l Long l12, boolean z12, boolean z13, long j13, @Y61.l C19705D c19705d, @Y61.l String str, @Y61.l Long l13, @Y61.l Long l14, @Y61.l Long l15, @Y61.l String str2, long j14, @Y61.l l0 l0Var, @Y61.l Long l16, @Y61.k String str3, @Y61.k String str4, @Y61.l String str5) {
        this.categoryId = j12;
        this.datetime = l12;
        this.deleted = z12;
        this.hidePhone = z13;
        this.id = j13;
        this.images = c19705d;
        this.location = str;
        this.locationId = l13;
        this.microcatId = l14;
        this.price = l15;
        this.priceString = str2;
        this.rootCategoryId = j14;
        this.sharedLocation = l0Var;
        this.statusId = l16;
        this.title = str3;
        this.urlPath = str4;
        this.userId = str5;
    }

    /* renamed from: a, reason: from getter */
    public final long getCategoryId() {
        return this.categoryId;
    }

    /* renamed from: b, reason: from getter */
    public final boolean getDeleted() {
        return this.deleted;
    }

    /* renamed from: c, reason: from getter */
    public final boolean getHidePhone() {
        return this.hidePhone;
    }

    /* renamed from: d, reason: from getter */
    public final long getId() {
        return this.id;
    }

    @Y61.l
    /* renamed from: e, reason: from getter */
    public final C19705D getImages() {
        return this.images;
    }

    @Y61.l
    /* renamed from: f, reason: from getter */
    public final String getPriceString() {
        return this.priceString;
    }

    @Y61.l
    /* renamed from: g, reason: from getter */
    public final l0 getSharedLocation() {
        return this.sharedLocation;
    }

    @Y61.k
    /* renamed from: h, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
