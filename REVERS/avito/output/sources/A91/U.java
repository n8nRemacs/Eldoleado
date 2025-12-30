package a91;

import com.avito.android.remote.model.messenger.message.MessageBody;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\t\u001a\u0004\b\f\u0010\u000bR\u001a\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\r\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"La91/U;", "", "", "fileId", "name", "", MessageBody.File.SIZE_IN_BYTES, "<init>", "(Ljava/lang/String;Ljava/lang/String;J)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "b", "J", "c", "()J", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class U {

    @com.google.gson.annotations.c("fileId")
    @Y61.k
    private final String fileId;

    @com.google.gson.annotations.c("name")
    @Y61.k
    private final String name;

    @com.google.gson.annotations.c(MessageBody.File.SIZE_IN_BYTES)
    private final long sizeBytes;

    public U(@Y61.k String str, @Y61.k String str2, long j12) {
        this.fileId = str;
        this.name = str2;
        this.sizeBytes = j12;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final String getFileId() {
        return this.fileId;
    }

    @Y61.k
    /* renamed from: b, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: c, reason: from getter */
    public final long getSizeBytes() {
        return this.sizeBytes;
    }
}
