package a91;

import com.avito.android.remote.model.messenger.attach_menu.AttachMenuItem;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\t\u0010\nR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u000e\u001a\u0004\b\u0011\u0010\u0010R\u001a\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"La91/k;", "", "", AttachMenuItem.ENABLE_FOR_UNANSWERED, "", AttachMenuItem.Video.MAX_DURATION_SEC, "maxSizeBytes", "", "title", "<init>", "(ZLjava/lang/Long;Ljava/lang/Long;Ljava/lang/String;)V", "Z", "a", "()Z", "Ljava/lang/Long;", "b", "()Ljava/lang/Long;", "c", "Ljava/lang/String;", "d", "()Ljava/lang/String;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.k, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19730k {

    @com.google.gson.annotations.c(AttachMenuItem.ENABLE_FOR_UNANSWERED)
    private final boolean enableForUnanswered;

    @com.google.gson.annotations.c(AttachMenuItem.Video.MAX_DURATION_SEC)
    @Y61.l
    private final Long maxDurationSec;

    @com.google.gson.annotations.c("maxSizeBytes")
    @Y61.l
    private final Long maxSizeBytes;

    @com.google.gson.annotations.c("title")
    @Y61.k
    private final String title;

    public C19730k(boolean z12, @Y61.l Long l12, @Y61.l Long l13, @Y61.k String str) {
        this.enableForUnanswered = z12;
        this.maxDurationSec = l12;
        this.maxSizeBytes = l13;
        this.title = str;
    }

    /* renamed from: a, reason: from getter */
    public final boolean getEnableForUnanswered() {
        return this.enableForUnanswered;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final Long getMaxDurationSec() {
        return this.maxDurationSec;
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
