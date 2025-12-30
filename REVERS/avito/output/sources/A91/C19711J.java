package a91;

import com.avito.android.remote.model.Navigation;
import com.avito.android.remote.model.category_parameters.AddressParameter;
import com.avito.android.remote.model.text.AttributedText;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import ru.avito.messenger.generated.api.create_item_channel.Attribute;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0017\u0018\u00002\u00020\u0001Bs\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u0014\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0002\u0012\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010¢\u0006\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R(\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010\t\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\t\u0010\u0014\u001a\u0004\b\u001d\u0010\u0016R\u001c\u0010\n\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\n\u0010\u0014\u001a\u0004\b\u001e\u0010\u0016R\"\u0010\r\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b8\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\r\u0010\u001f\u001a\u0004\b \u0010!R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000e\u0010\u0014\u001a\u0004\b\"\u0010\u0016R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u000f\u0010\u0014\u001a\u0004\b#\u0010\u0016R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u00108\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0011\u0010$\u001a\u0004\b%\u0010&¨\u0006'"}, d2 = {"La91/J;", "", "", AddressParameter.TYPE, "Lcom/avito/android/remote/model/text/AttributedText;", "attributedText", "", "Lru/avito/messenger/generated/api/create_item_channel/Attribute;", Navigation.ATTRIBUTES, "distance", "distanceInTime", "", "La91/L;", "lines", "name", "text", "", "version", "<init>", "(Ljava/lang/String;Lcom/avito/android/remote/model/text/AttributedText;Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "Lcom/avito/android/remote/model/text/AttributedText;", "b", "()Lcom/avito/android/remote/model/text/AttributedText;", "Ljava/util/Map;", "getAttributes", "()Ljava/util/Map;", "c", "d", "Ljava/util/List;", "e", "()Ljava/util/List;", "f", "getText", "Ljava/lang/Long;", "getVersion", "()Ljava/lang/Long;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.J, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19711J {

    @com.google.gson.annotations.c(AddressParameter.TYPE)
    @Y61.l
    private final String address;

    @com.google.gson.annotations.c("attributedText")
    @Y61.l
    private final AttributedText attributedText;

    @com.google.gson.annotations.c(Navigation.ATTRIBUTES)
    @Y61.l
    private final Map<String, Attribute> attributes;

    @com.google.gson.annotations.c("distance")
    @Y61.l
    private final String distance;

    @com.google.gson.annotations.c("distanceInTime")
    @Y61.l
    private final String distanceInTime;

    @com.google.gson.annotations.c("lines")
    @Y61.l
    private final List<C19713L> lines;

    @com.google.gson.annotations.c("name")
    @Y61.l
    private final String name;

    @com.google.gson.annotations.c("text")
    @Y61.l
    private final String text;

    @com.google.gson.annotations.c("version")
    @Y61.l
    private final Long version;

    public C19711J(@Y61.l String str, @Y61.l AttributedText attributedText, @Y61.l Map<String, Attribute> map, @Y61.l String str2, @Y61.l String str3, @Y61.l List<C19713L> list, @Y61.l String str4, @Y61.l String str5, @Y61.l Long l12) {
        this.address = str;
        this.attributedText = attributedText;
        this.attributes = map;
        this.distance = str2;
        this.distanceInTime = str3;
        this.lines = list;
        this.name = str4;
        this.text = str5;
        this.version = l12;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final String getAddress() {
        return this.address;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final AttributedText getAttributedText() {
        return this.attributedText;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final String getDistance() {
        return this.distance;
    }

    @Y61.l
    /* renamed from: d, reason: from getter */
    public final String getDistanceInTime() {
        return this.distanceInTime;
    }

    @Y61.l
    public final List<C19713L> e() {
        return this.lines;
    }

    @Y61.l
    /* renamed from: f, reason: from getter */
    public final String getName() {
        return this.name;
    }
}
