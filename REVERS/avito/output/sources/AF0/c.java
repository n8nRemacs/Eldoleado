package AF0;

import Y61.k;
import com.adjust.sdk.Constants;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.remote.model.text.AttributedText;
import kotlin.Metadata;

/* compiled from: Api1StrSellerBannersGetResponse.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\u0006¢\u0006\u0004\b\r\u0010\u000eR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u000b\u001a\u00020\n8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000b\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\f\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\f\u0010\u0015\u001a\u0004\b\u001e\u0010\u0017¨\u0006\u001f"}, d2 = {"LAF0/c;", "", "LAF0/b;", "backgroundColor", "Lcom/avito/android/deep_linking/links/DeepLink;", Constants.DEEPLINK, "", "id", "LAF0/d;", "image", "Lcom/avito/android/remote/model/text/AttributedText;", "subtitle", "title", "<init>", "(LAF0/b;Lcom/avito/android/deep_linking/links/DeepLink;Ljava/lang/String;LAF0/d;Lcom/avito/android/remote/model/text/AttributedText;Ljava/lang/String;)V", "LAF0/b;", "a", "()LAF0/b;", "Lcom/avito/android/deep_linking/links/DeepLink;", "b", "()Lcom/avito/android/deep_linking/links/DeepLink;", "Ljava/lang/String;", "c", "()Ljava/lang/String;", "LAF0/d;", "d", "()LAF0/d;", "Lcom/avito/android/remote/model/text/AttributedText;", "e", "()Lcom/avito/android/remote/model/text/AttributedText;", "f", "_avito_travel-seller-banners_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class c {

    @com.google.gson.annotations.c("backgroundColor")
    @k
    private final b backgroundColor;

    @com.google.gson.annotations.c(Constants.DEEPLINK)
    @k
    private final DeepLink deeplink;

    @com.google.gson.annotations.c("id")
    @k
    private final String id;

    @com.google.gson.annotations.c("image")
    @k
    private final d image;

    @com.google.gson.annotations.c("subtitle")
    @k
    private final AttributedText subtitle;

    @com.google.gson.annotations.c("title")
    @k
    private final String title;

    public c(@k b bVar, @k DeepLink deepLink, @k String str, @k d dVar, @k AttributedText attributedText, @k String str2) {
        this.backgroundColor = bVar;
        this.deeplink = deepLink;
        this.id = str;
        this.image = dVar;
        this.subtitle = attributedText;
        this.title = str2;
    }

    @k
    /* renamed from: a, reason: from getter */
    public final b getBackgroundColor() {
        return this.backgroundColor;
    }

    @k
    /* renamed from: b, reason: from getter */
    public final DeepLink getDeeplink() {
        return this.deeplink;
    }

    @k
    /* renamed from: c, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @k
    /* renamed from: d, reason: from getter */
    public final d getImage() {
        return this.image;
    }

    @k
    /* renamed from: e, reason: from getter */
    public final AttributedText getSubtitle() {
        return this.subtitle;
    }

    @k
    /* renamed from: f, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
