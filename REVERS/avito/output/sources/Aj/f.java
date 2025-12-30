package aJ;

import Y61.k;
import Y61.l;
import com.avito.android.remote.model.text.AttributedText;
import kotlin.Metadata;

/* compiled from: Api1HotelByItemIdItemPageOffersGetResponse.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000b\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"LaJ/f;", "", "Lcom/avito/android/remote/model/text/AttributedText;", "attributedText", "", "highlightType", "<init>", "(Lcom/avito/android/remote/model/text/AttributedText;Ljava/lang/String;)V", "Lcom/avito/android/remote/model/text/AttributedText;", "a", "()Lcom/avito/android/remote/model/text/AttributedText;", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "_avito_hotel_public"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes13.dex */
public final class f {

    @com.google.gson.annotations.c("attributedText")
    @k
    private final AttributedText attributedText;

    @com.google.gson.annotations.c("highlightType")
    @l
    private final String highlightType;

    public f(@k AttributedText attributedText, @l String str) {
        this.attributedText = attributedText;
        this.highlightType = str;
    }

    @k
    /* renamed from: a, reason: from getter */
    public final AttributedText getAttributedText() {
        return this.attributedText;
    }

    @l
    /* renamed from: b, reason: from getter */
    public final String getHighlightType() {
        return this.highlightType;
    }
}
