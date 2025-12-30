package a91;

import com.avito.android.remote.model.Navigation;
import com.avito.android.remote.model.text.AttributedText;
import java.util.Map;
import kotlin.Metadata;
import ru.avito.messenger.generated.api.create_item_channel.Attribute;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\u0018\u00002\u00020\u0001B9\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\n\u001a\u00020\u0005¢\u0006\u0004\b\u000b\u0010\fR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\r\u001a\u0004\b\u000e\u0010\u000fR(\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\t\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\n\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"La91/Z;", "", "Lcom/avito/android/remote/model/text/AttributedText;", "attributedText", "", "", "Lru/avito/messenger/generated/api/create_item_channel/Attribute;", Navigation.ATTRIBUTES, "", "hasTrustedUrls", "text", "<init>", "(Lcom/avito/android/remote/model/text/AttributedText;Ljava/util/Map;Ljava/lang/Boolean;Ljava/lang/String;)V", "Lcom/avito/android/remote/model/text/AttributedText;", "a", "()Lcom/avito/android/remote/model/text/AttributedText;", "Ljava/util/Map;", "b", "()Ljava/util/Map;", "Ljava/lang/Boolean;", "c", "()Ljava/lang/Boolean;", "Ljava/lang/String;", "d", "()Ljava/lang/String;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class Z {

    @com.google.gson.annotations.c("attributedText")
    @Y61.l
    private final AttributedText attributedText;

    @com.google.gson.annotations.c(Navigation.ATTRIBUTES)
    @Y61.l
    private final Map<String, Attribute> attributes;

    @com.google.gson.annotations.c("hasTrustedUrls")
    @Y61.l
    private final Boolean hasTrustedUrls;

    @com.google.gson.annotations.c("text")
    @Y61.k
    private final String text;

    public Z(@Y61.l AttributedText attributedText, @Y61.l Map<String, Attribute> map, @Y61.l Boolean bool, @Y61.k String str) {
        this.attributedText = attributedText;
        this.attributes = map;
        this.hasTrustedUrls = bool;
        this.text = str;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final AttributedText getAttributedText() {
        return this.attributedText;
    }

    @Y61.l
    public final Map<String, Attribute> b() {
        return this.attributes;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final Boolean getHasTrustedUrls() {
        return this.hasTrustedUrls;
    }

    @Y61.k
    /* renamed from: d, reason: from getter */
    public final String getText() {
        return this.text;
    }
}
