package aa;

import Y61.k;
import androidx.compose.runtime.internal.P;
import com.avito.android.deep_linking.links.DeepLink;
import com.google.gson.annotations.c;
import kotlin.Metadata;

/* compiled from: Api1DeliveryRequestFreeDeliveryPostResponse.kt */
@P
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004¢\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\f\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u0010"}, d2 = {"Laa/b;", "", "Lcom/avito/android/deep_linking/links/DeepLink;", "chatDeeplink", "", "text", "toastMessage", "<init>", "(Lcom/avito/android/deep_linking/links/DeepLink;Ljava/lang/String;Ljava/lang/String;)V", "Lcom/avito/android/deep_linking/links/DeepLink;", "a", "()Lcom/avito/android/deep_linking/links/DeepLink;", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "c", "_avito_advert-details_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aa.b, reason: case insensitive filesystem */
/* loaded from: classes10.dex */
public final class C19849b {

    @c("chatDeeplink")
    @k
    private final DeepLink chatDeeplink;

    @c("text")
    @k
    private final String text;

    @c("toastMessage")
    @k
    private final String toastMessage;

    public C19849b(@k DeepLink deepLink, @k String str, @k String str2) {
        this.chatDeeplink = deepLink;
        this.text = str;
        this.toastMessage = str2;
    }

    @k
    /* renamed from: a, reason: from getter */
    public final DeepLink getChatDeeplink() {
        return this.chatDeeplink;
    }

    @k
    /* renamed from: b, reason: from getter */
    public final String getText() {
        return this.text;
    }

    @k
    /* renamed from: c, reason: from getter */
    public final String getToastMessage() {
        return this.toastMessage;
    }
}
