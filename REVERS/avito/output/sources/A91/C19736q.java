package a91;

import com.adjust.sdk.Constants;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\u0018\u00002\u00020\u0001B%\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0007\u0010\bR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\f\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u0010"}, d2 = {"La91/q;", "", "Lcom/avito/android/deep_linking/links/DeepLink;", Constants.DEEPLINK, "", "rawDeeplink", "title", "<init>", "(Lcom/avito/android/deep_linking/links/DeepLink;Ljava/lang/String;Ljava/lang/String;)V", "Lcom/avito/android/deep_linking/links/DeepLink;", "getDeeplink", "()Lcom/avito/android/deep_linking/links/DeepLink;", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "b", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.q, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19736q {

    @com.google.gson.annotations.c(Constants.DEEPLINK)
    @Y61.l
    private final DeepLink deeplink;

    @com.google.gson.annotations.c("rawDeeplink")
    @Y61.l
    private final String rawDeeplink;

    @com.google.gson.annotations.c("title")
    @Y61.l
    private final String title;

    public C19736q(@Y61.l DeepLink deepLink, @Y61.l String str, @Y61.l String str2) {
        this.deeplink = deepLink;
        this.rawDeeplink = str;
        this.title = str2;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final String getRawDeeplink() {
        return this.rawDeeplink;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
