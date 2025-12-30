package a91;

import com.avito.android.remote.model.messenger.attach_menu.AttachMenuItem;
import java.util.List;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0014\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\n\u001a\u00020\u0006¢\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001c\u0010\t\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\n\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001c"}, d2 = {"La91/d;", "", "", AttachMenuItem.ENABLE_FOR_UNANSWERED, "enableOnboarding", "", "", AttachMenuItem.File.EXTENSIONS, "", "maxSizeBytes", "title", "<init>", "(ZLjava/lang/Boolean;Ljava/util/List;Ljava/lang/Long;Ljava/lang/String;)V", "Z", "a", "()Z", "Ljava/lang/Boolean;", "getEnableOnboarding", "()Ljava/lang/Boolean;", "Ljava/util/List;", "b", "()Ljava/util/List;", "Ljava/lang/Long;", "c", "()Ljava/lang/Long;", "Ljava/lang/String;", "d", "()Ljava/lang/String;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.d, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19723d {

    @com.google.gson.annotations.c(AttachMenuItem.ENABLE_FOR_UNANSWERED)
    private final boolean enableForUnanswered;

    @com.google.gson.annotations.c("enableOnboarding")
    @Y61.l
    private final Boolean enableOnboarding;

    @com.google.gson.annotations.c(AttachMenuItem.File.EXTENSIONS)
    @Y61.k
    private final List<String> extensions;

    @com.google.gson.annotations.c("maxSizeBytes")
    @Y61.l
    private final Long maxSizeBytes;

    @com.google.gson.annotations.c("title")
    @Y61.k
    private final String title;

    public C19723d(boolean z12, @Y61.l Boolean bool, @Y61.k List<String> list, @Y61.l Long l12, @Y61.k String str) {
        this.enableForUnanswered = z12;
        this.enableOnboarding = bool;
        this.extensions = list;
        this.maxSizeBytes = l12;
        this.title = str;
    }

    /* renamed from: a, reason: from getter */
    public final boolean getEnableForUnanswered() {
        return this.enableForUnanswered;
    }

    @Y61.k
    public final List<String> b() {
        return this.extensions;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final Long getMaxSizeBytes() {
        return this.maxSizeBytes;
    }

    @Y61.k
    /* renamed from: d, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
