package a91;

import com.avito.android.remote.model.text.AttributedText;
import java.util.List;
import kotlin.Metadata;
import ru.avito.messenger.generated.api.create_item_channel.ItemTextField;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B7\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\u0004\b\u000b\u0010\fR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\r\u001a\u0004\b\u000e\u0010\u000fR \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R \u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u0010\u001a\u0004\b\u0016\u0010\u0012¨\u0006\u0017"}, d2 = {"La91/I;", "", "La91/G;", "actions", "", "La91/B;", "images", "Lcom/avito/android/remote/model/text/AttributedText;", "price", "Lru/avito/messenger/generated/api/create_item_channel/ItemTextField;", "textFields", "<init>", "(La91/G;Ljava/util/List;Lcom/avito/android/remote/model/text/AttributedText;Ljava/util/List;)V", "La91/G;", "a", "()La91/G;", "Ljava/util/List;", "b", "()Ljava/util/List;", "Lcom/avito/android/remote/model/text/AttributedText;", "c", "()Lcom/avito/android/remote/model/text/AttributedText;", "d", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.I, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19710I {

    @com.google.gson.annotations.c("actions")
    @Y61.l
    private final C19708G actions;

    @com.google.gson.annotations.c("images")
    @Y61.k
    private final List<C19703B> images;

    @com.google.gson.annotations.c("price")
    @Y61.l
    private final AttributedText price;

    @com.google.gson.annotations.c("textFields")
    @Y61.k
    private final List<ItemTextField> textFields;

    public C19710I(@Y61.l C19708G c19708g, @Y61.k List<C19703B> list, @Y61.l AttributedText attributedText, @Y61.k List<ItemTextField> list2) {
        this.actions = c19708g;
        this.images = list;
        this.price = attributedText;
        this.textFields = list2;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final C19708G getActions() {
        return this.actions;
    }

    @Y61.k
    public final List<C19703B> b() {
        return this.images;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final AttributedText getPrice() {
        return this.price;
    }

    @Y61.k
    public final List<ItemTextField> d() {
        return this.textFields;
    }
}
