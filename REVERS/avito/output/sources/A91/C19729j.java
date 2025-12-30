package a91;

import com.avito.android.remote.model.messenger.attach_menu.AttachMenuItem;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004¢\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\f\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u0010"}, d2 = {"La91/j;", "", "", AttachMenuItem.ENABLE_FOR_UNANSWERED, "", "message", "title", "<init>", "(ZLjava/lang/String;Ljava/lang/String;)V", "Z", "a", "()Z", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "c", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.j, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19729j {

    @com.google.gson.annotations.c(AttachMenuItem.ENABLE_FOR_UNANSWERED)
    private final boolean enableForUnanswered;

    @com.google.gson.annotations.c("message")
    @Y61.k
    private final String message;

    @com.google.gson.annotations.c("title")
    @Y61.k
    private final String title;

    public C19729j(boolean z12, @Y61.k String str, @Y61.k String str2) {
        this.enableForUnanswered = z12;
        this.message = str;
        this.title = str2;
    }

    /* renamed from: a, reason: from getter */
    public final boolean getEnableForUnanswered() {
        return this.enableForUnanswered;
    }

    @Y61.k
    /* renamed from: b, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    @Y61.k
    /* renamed from: c, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
