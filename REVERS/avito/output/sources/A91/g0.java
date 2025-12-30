package a91;

import com.adjust.sdk.Constants;
import com.avito.android.remote.model.messenger.context_actions.ContextActionHandler;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\u0004\b\n\u0010\u000bR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\t\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u0018"}, d2 = {"La91/g0;", "", "La91/n;", "avatar", "La91/y;", Constants.DEEPLINK, "", "itemID", "", ContextActionHandler.Link.URL, "<init>", "(La91/n;La91/y;Ljava/lang/Long;Ljava/lang/String;)V", "La91/n;", "a", "()La91/n;", "La91/y;", "b", "()La91/y;", "Ljava/lang/Long;", "getItemID", "()Ljava/lang/Long;", "Ljava/lang/String;", "getUrl", "()Ljava/lang/String;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class g0 {

    @com.google.gson.annotations.c("avatar")
    @Y61.k
    private final C19733n avatar;

    @com.google.gson.annotations.c(Constants.DEEPLINK)
    @Y61.l
    private final C19744y deeplink;

    @com.google.gson.annotations.c("itemID")
    @Y61.l
    private final Long itemID;

    @com.google.gson.annotations.c(ContextActionHandler.Link.URL)
    @Y61.l
    private final String url;

    public g0(@Y61.k C19733n c19733n, @Y61.l C19744y c19744y, @Y61.l Long l12, @Y61.l String str) {
        this.avatar = c19733n;
        this.deeplink = c19744y;
        this.itemID = l12;
        this.url = str;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final C19733n getAvatar() {
        return this.avatar;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final C19744y getDeeplink() {
        return this.deeplink;
    }
}
