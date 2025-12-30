package a91;

import kotlin.Metadata;
import ru.avito.messenger.generated.api.create_item_channel.Channel;
import ru.avito.messenger.generated.api.create_item_channel.DomainError;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u001b\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0006\u0010\u0007R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000b\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"La91/w;", "", "Lru/avito/messenger/generated/api/create_item_channel/Channel;", "channel", "Lru/avito/messenger/generated/api/create_item_channel/DomainError;", "error", "<init>", "(Lru/avito/messenger/generated/api/create_item_channel/Channel;Lru/avito/messenger/generated/api/create_item_channel/DomainError;)V", "Lru/avito/messenger/generated/api/create_item_channel/Channel;", "a", "()Lru/avito/messenger/generated/api/create_item_channel/Channel;", "Lru/avito/messenger/generated/api/create_item_channel/DomainError;", "b", "()Lru/avito/messenger/generated/api/create_item_channel/DomainError;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.w, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19742w {

    @com.google.gson.annotations.c("channel")
    @Y61.l
    private final Channel channel;

    @com.google.gson.annotations.c("error")
    @Y61.l
    private final DomainError error;

    public C19742w(@Y61.l Channel channel, @Y61.l DomainError domainError) {
        this.channel = channel;
        this.error = domainError;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final Channel getChannel() {
        return this.channel;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final DomainError getError() {
        return this.error;
    }
}
