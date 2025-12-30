package a91;

import com.avito.android.remote.model.text.AttributedText;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"La91/d0;", "", "La91/o;", "button", "Lcom/avito/android/remote/model/text/AttributedText;", "description", "La91/n0;", "image", "<init>", "(La91/o;Lcom/avito/android/remote/model/text/AttributedText;La91/n0;)V", "La91/o;", "a", "()La91/o;", "Lcom/avito/android/remote/model/text/AttributedText;", "b", "()Lcom/avito/android/remote/model/text/AttributedText;", "La91/n0;", "c", "()La91/n0;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class d0 {

    @com.google.gson.annotations.c("button")
    @Y61.l
    private final C19734o button;

    @com.google.gson.annotations.c("description")
    @Y61.l
    private final AttributedText description;

    @com.google.gson.annotations.c("image")
    @Y61.k
    private final n0 image;

    public d0(@Y61.l C19734o c19734o, @Y61.l AttributedText attributedText, @Y61.k n0 n0Var) {
        this.button = c19734o;
        this.description = attributedText;
        this.image = n0Var;
    }

    @Y61.l
    /* renamed from: a, reason: from getter */
    public final C19734o getButton() {
        return this.button;
    }

    @Y61.l
    /* renamed from: b, reason: from getter */
    public final AttributedText getDescription() {
        return this.description;
    }

    @Y61.k
    /* renamed from: c, reason: from getter */
    public final n0 getImage() {
        return this.image;
    }
}
