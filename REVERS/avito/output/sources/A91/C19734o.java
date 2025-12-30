package a91;

import com.avito.android.beduin.common.container.promo_block.BeduinPromoBlockModel;
import com.avito.android.remote.model.text.AttributedText;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B)\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\n\u001a\u0004\b\r\u0010\fR\u001a\u0010\u0005\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\n\u001a\u0004\b\u000e\u0010\fR\u001a\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"La91/o;", "", "", "onTapDeepLink", BeduinPromoBlockModel.SERIALIZED_NAME_STYLE, BeduinPromoBlockModel.SERIALIZED_NAME_THEME, "Lcom/avito/android/remote/model/text/AttributedText;", "title", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/avito/android/remote/model/text/AttributedText;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "b", "c", "Lcom/avito/android/remote/model/text/AttributedText;", "d", "()Lcom/avito/android/remote/model/text/AttributedText;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.o, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19734o {

    @com.google.gson.annotations.c("onTapDeepLink")
    @Y61.l
    private final String onTapDeepLink;

    @com.google.gson.annotations.c(BeduinPromoBlockModel.SERIALIZED_NAME_STYLE)
    @Y61.k
    private final String style;

    @com.google.gson.annotations.c(BeduinPromoBlockModel.SERIALIZED_NAME_THEME)
    @Y61.k
    private final String theme;

    @com.google.gson.annotations.c("title")
    @Y61.k
    private final AttributedText title;

    public C19734o(@Y61.l String str, @Y61.k String str2, @Y61.k String str3, @Y61.k AttributedText attributedText) {
        this.onTapDeepLink = str;
        this.style = str2;
        this.theme = str3;
        this.title = attributedText;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final String getOnTapDeepLink() {
        return this.onTapDeepLink;
    }

    @Y61.k
    /* renamed from: b, reason: from getter */
    public final String getStyle() {
        return this.style;
    }

    @Y61.k
    /* renamed from: c, reason: from getter */
    public final String getTheme() {
        return this.theme;
    }

    @Y61.k
    /* renamed from: d, reason: from getter */
    public final AttributedText getTitle() {
        return this.title;
    }
}
