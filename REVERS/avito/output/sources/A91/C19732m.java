package a91;

import com.avito.android.remote.model.messenger.context_actions.ContextActionHandler;
import java.util.List;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B5\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\t\u0010\nR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u000b\u001a\u0004\b\f\u0010\rR\"\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u000b\u001a\u0004\b\u0011\u0010\rR\u001c\u0010\b\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u000b\u001a\u0004\b\u0012\u0010\r¨\u0006\u0013"}, d2 = {"La91/m;", "", "", ContextActionHandler.Link.DEEPLINK, "", "La91/P;", "list", "title", ContextActionHandler.Link.URL, "<init>", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "Ljava/util/List;", "getList", "()Ljava/util/List;", "b", "c", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.m, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19732m {

    @com.google.gson.annotations.c(ContextActionHandler.Link.DEEPLINK)
    @Y61.l
    private final String deepLink;

    @com.google.gson.annotations.c("list")
    @Y61.l
    private final List<C19717P> list;

    @com.google.gson.annotations.c("title")
    @Y61.l
    private final String title;

    @com.google.gson.annotations.c(ContextActionHandler.Link.URL)
    @Y61.l
    private final String url;

    public C19732m(@Y61.l String str, @Y61.l List<C19717P> list, @Y61.l String str2, @Y61.l String str3) {
        this.deepLink = str;
        this.list = list;
        this.title = str2;
        this.url = str3;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final String getDeepLink() {
        return this.deepLink;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final String getUrl() {
        return this.url;
    }
}
