package a91;

import com.avito.android.remote.model.messenger.message.MessageBody;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\b\u001a\u0004\b\u000b\u0010\nR\u001a\u0010\u0005\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\b\u001a\u0004\b\f\u0010\n¨\u0006\r"}, d2 = {"La91/a0;", "", "", "fileId", "status", MessageBody.Video.VIDEO_ID, "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "getStatus", "b", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class a0 {

    @com.google.gson.annotations.c("fileId")
    @Y61.k
    private final String fileId;

    @com.google.gson.annotations.c("status")
    @Y61.k
    private final String status;

    @com.google.gson.annotations.c(MessageBody.Video.VIDEO_ID)
    @Y61.k
    private final String videoId;

    public a0(@Y61.k String str, @Y61.k String str2, @Y61.k String str3) {
        this.fileId = str;
        this.status = str2;
        this.videoId = str3;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final String getFileId() {
        return this.fileId;
    }

    @Y61.k
    /* renamed from: b, reason: from getter */
    public final String getVideoId() {
        return this.videoId;
    }
}
