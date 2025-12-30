package a91;

import com.avito.android.remote.model.text.AttributedText;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000b\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"La91/s;", "", "Lcom/avito/android/remote/model/text/AttributedText;", "text", "", "title", "<init>", "(Lcom/avito/android/remote/model/text/AttributedText;Ljava/lang/String;)V", "Lcom/avito/android/remote/model/text/AttributedText;", "a", "()Lcom/avito/android/remote/model/text/AttributedText;", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: a91.s, reason: case insensitive filesystem */
/* loaded from: classes9.dex */
public final class C19738s {

    @com.google.gson.annotations.c("text")
    @Y61.k
    private final AttributedText text;

    @com.google.gson.annotations.c("title")
    @Y61.k
    private final String title;

    public C19738s(@Y61.k AttributedText attributedText, @Y61.k String str) {
        this.text = attributedText;
        this.title = str;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final AttributedText getText() {
        return this.text;
    }

    @Y61.k
    /* renamed from: b, reason: from getter */
    public final String getTitle() {
        return this.title;
    }
}
