package ag;

import Y61.k;
import Y61.l;
import com.avito.android.remote.model.text.TooltipAttribute;
import com.google.gson.annotations.c;
import kotlin.Metadata;

/* compiled from: CreateIcebreakerResponse.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\b\u0010\tR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\r\u001a\u0004\b\u0010\u0010\u000fR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\r\u001a\u0004\b\u0011\u0010\u000f¨\u0006\u0012"}, d2 = {"Lag/a;", "", "", "id", "", "messageText", "previewText", TooltipAttribute.PARAM_DEEP_LINK, "<init>", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "J", "a", "()J", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "c", "d", "_avito-discouraged_avito-api_advert-details"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ag.a, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C19885a {

    @c("id")
    private final long id;

    @c("messageText")
    @k
    private final String messageText;

    @c("previewText")
    @l
    private final String previewText;

    @c(TooltipAttribute.PARAM_DEEP_LINK)
    @l
    private final String uri;

    public C19885a(long j12, @k String str, @l String str2, @l String str3) {
        this.id = j12;
        this.messageText = str;
        this.previewText = str2;
        this.uri = str3;
    }

    /* renamed from: a, reason: from getter */
    public final long getId() {
        return this.id;
    }

    @k
    /* renamed from: b, reason: from getter */
    public final String getMessageText() {
        return this.messageText;
    }

    @l
    /* renamed from: c, reason: from getter */
    public final String getPreviewText() {
        return this.previewText;
    }

    @l
    /* renamed from: d, reason: from getter */
    public final String getUri() {
        return this.uri;
    }
}
