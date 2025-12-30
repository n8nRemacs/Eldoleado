package a91;

import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0002¢\u0006\u0004\b\u0007\u0010\bR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0006\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\t\u001a\u0004\b\u000f\u0010\u000b¨\u0006\u0010"}, d2 = {"La91/E;", "", "", "description", "", "isDisabled", "reason", "<init>", "(Ljava/lang/String;ZLjava/lang/String;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "Z", "c", "()Z", "b", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.E, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19706E {

    @com.google.gson.annotations.c("description")
    @Y61.l
    private final String description;

    @com.google.gson.annotations.c("isDisabled")
    private final boolean isDisabled;

    @com.google.gson.annotations.c("reason")
    @Y61.k
    private final String reason;

    public C19706E(@Y61.l String str, boolean z12, @Y61.k String str2) {
        this.description = str;
        this.isDisabled = z12;
        this.reason = str2;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Y61.k
    /* renamed from: b, reason: from getter */
    public final String getReason() {
        return this.reason;
    }

    /* renamed from: c, reason: from getter */
    public final boolean getIsDisabled() {
        return this.isDisabled;
    }
}
