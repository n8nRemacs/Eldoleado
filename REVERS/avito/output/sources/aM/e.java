package AM;

import Y61.k;
import Y61.l;
import com.avito.android.remote.model.text.TooltipAttribute;
import kotlin.Metadata;

/* compiled from: ApiImageScoreV1Response.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\t\u0010\nR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u000b\u001a\u0004\b\u0011\u0010\rR\u001c\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"LAM/e;", "", "", "badgeID", "LAM/b;", "color", "title", "LAM/f;", TooltipAttribute.ATTRIBUTE_TYPE, "<init>", "(Ljava/lang/String;LAM/b;Ljava/lang/String;LAM/f;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "LAM/b;", "b", "()LAM/b;", "c", "LAM/f;", "d", "()LAM/f;", "_avito_image-perception_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes14.dex */
public final class e {

    @com.google.gson.annotations.c("badgeID")
    @k
    private final String badgeID;

    @com.google.gson.annotations.c("color")
    @k
    private final b color;

    @com.google.gson.annotations.c("title")
    @k
    private final String title;

    @com.google.gson.annotations.c(TooltipAttribute.ATTRIBUTE_TYPE)
    @l
    private final f tooltip;

    public e(@k String str, @k b bVar, @k String str2, @l f fVar) {
        this.badgeID = str;
        this.color = bVar;
        this.title = str2;
        this.tooltip = fVar;
    }

    @k
    /* renamed from: a, reason: from getter */
    public final String getBadgeID() {
        return this.badgeID;
    }

    @k
    /* renamed from: b, reason: from getter */
    public final b getColor() {
        return this.color;
    }

    @k
    /* renamed from: c, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @l
    /* renamed from: d, reason: from getter */
    public final f getTooltip() {
        return this.tooltip;
    }
}
