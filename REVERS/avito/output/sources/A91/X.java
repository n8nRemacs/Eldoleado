package a91;

import com.avito.android.remote.model.messenger.context_actions.ContextActionHandler;
import com.avito.android.remote.model.messenger.message_suggests.MessageSuggest;
import kotlin.Metadata;
import ru.avito.messenger.generated.api.create_item_channel.Preview;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006¢\u0006\u0004\b\t\u0010\nR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\b\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0011\u001a\u0004\b\u0014\u0010\u0013¨\u0006\u0015"}, d2 = {"La91/X;", "", "", "isTrusted", "Lru/avito/messenger/generated/api/create_item_channel/Preview;", MessageSuggest.PREVIEW, "", "text", ContextActionHandler.Link.URL, "<init>", "(ZLru/avito/messenger/generated/api/create_item_channel/Preview;Ljava/lang/String;Ljava/lang/String;)V", "Z", "c", "()Z", "Lru/avito/messenger/generated/api/create_item_channel/Preview;", "a", "()Lru/avito/messenger/generated/api/create_item_channel/Preview;", "Ljava/lang/String;", "getText", "()Ljava/lang/String;", "b", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class X {

    @com.google.gson.annotations.c("isTrusted")
    private final boolean isTrusted;

    @com.google.gson.annotations.c(MessageSuggest.PREVIEW)
    @Y61.l
    private final Preview preview;

    @com.google.gson.annotations.c("text")
    @Y61.k
    private final String text;

    @com.google.gson.annotations.c(ContextActionHandler.Link.URL)
    @Y61.k
    private final String url;

    public X(boolean z12, @Y61.l Preview preview, @Y61.k String str, @Y61.k String str2) {
        this.isTrusted = z12;
        this.preview = preview;
        this.text = str;
        this.url = str2;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final Preview getPreview() {
        return this.preview;
    }

    @Y61.k
    /* renamed from: b, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    /* renamed from: c, reason: from getter */
    public final boolean getIsTrusted() {
        return this.isTrusted;
    }
}
