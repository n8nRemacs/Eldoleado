package a91;

import com.avito.android.remote.model.messenger.attach_menu.AttachMenuItem;
import com.avito.android.remote.model.messenger.context.ChannelContext;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"La91/g;", "", "", AttachMenuItem.ENABLE_FOR_UNANSWERED, "La91/m0;", ChannelContext.SHARED_LOCATION, "", "title", "<init>", "(ZLa91/m0;Ljava/lang/String;)V", "Z", "a", "()Z", "La91/m0;", "getSharedLocation", "()La91/m0;", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.g, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19726g {

    @com.google.gson.annotations.c(AttachMenuItem.ENABLE_FOR_UNANSWERED)
    private final boolean enableForUnanswered;

    @com.google.gson.annotations.c(ChannelContext.SHARED_LOCATION)
    @Y61.l
    private final m0 sharedLocation;

    @com.google.gson.annotations.c("title")
    @Y61.k
    private final String title;

    public C19726g(boolean z12, @Y61.l m0 m0Var, @Y61.k String str) {
        this.enableForUnanswered = z12;
        this.sharedLocation = m0Var;
        this.title = str;
    }

    /* renamed from: a, reason: from getter */
    public final boolean getEnableForUnanswered() {
        return this.enableForUnanswered;
    }

    @Y61.k
    /* renamed from: b, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
