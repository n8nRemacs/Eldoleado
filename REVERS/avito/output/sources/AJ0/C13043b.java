package Aj0;

import Y61.k;
import Y61.l;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.remote.model.messenger.context_actions.ContextActionHandler;
import com.google.gson.annotations.c;
import kotlin.Metadata;

/* compiled from: GetStepOrderStatusApiResponse.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0007\u0010\bR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\f\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u0010"}, d2 = {"LAj0/b;", "", "Lcom/avito/android/deep_linking/links/DeepLink;", ContextActionHandler.Link.DEEPLINK, "", "text", ContextActionHandler.Link.URL, "<init>", "(Lcom/avito/android/deep_linking/links/DeepLink;Ljava/lang/String;Ljava/lang/String;)V", "Lcom/avito/android/deep_linking/links/DeepLink;", "a", "()Lcom/avito/android/deep_linking/links/DeepLink;", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "getUrl", "_avito-discouraged_avito-api_autoteka"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Aj0.b, reason: case insensitive filesystem */
/* loaded from: classes17.dex */
public final class C13043b {

    @c(ContextActionHandler.Link.DEEPLINK)
    @l
    private final DeepLink deepLink;

    @c("text")
    @k
    private final String text;

    @c(ContextActionHandler.Link.URL)
    @l
    private final String url;

    public C13043b(@l DeepLink deepLink, @k String str, @l String str2) {
        this.deepLink = deepLink;
        this.text = str;
        this.url = str2;
    }

    @l
    /* renamed from: a, reason: from getter */
    public final DeepLink getDeepLink() {
        return this.deepLink;
    }

    @k
    /* renamed from: b, reason: from getter */
    public final String getText() {
        return this.text;
    }
}
