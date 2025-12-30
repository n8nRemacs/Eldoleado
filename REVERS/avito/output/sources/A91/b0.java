package a91;

import com.avito.android.remote.model.messenger.message.MessageBody;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u0007\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\u0007\u001a\u0004\b\n\u0010\t¨\u0006\u000b"}, d2 = {"La91/b0;", "", "", "fileId", MessageBody.Voice.VOICE_ID, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", "Ljava/lang/String;", "getFileId", "()Ljava/lang/String;", "a", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class b0 {

    @com.google.gson.annotations.c("fileId")
    @Y61.k
    private final String fileId;

    @com.google.gson.annotations.c(MessageBody.Voice.VOICE_ID)
    @Y61.k
    private final String voiceId;

    public b0(@Y61.k String str, @Y61.k String str2) {
        this.fileId = str;
        this.voiceId = str2;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final String getVoiceId() {
        return this.voiceId;
    }
}
