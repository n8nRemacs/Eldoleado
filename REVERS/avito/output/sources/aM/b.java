package AM;

import Y61.l;
import kotlin.Metadata;

/* compiled from: ApiImageScoreV1Response.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u0001B%\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0007\u0010\bR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0004\u0010\t\u001a\u0004\b\f\u0010\u000bR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\r\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"LAM/b;", "", "LAM/c;", "value", "valueDark", "", "valueName", "<init>", "(LAM/c;LAM/c;Ljava/lang/String;)V", "LAM/c;", "a", "()LAM/c;", "b", "Ljava/lang/String;", "c", "()Ljava/lang/String;", "_avito_image-perception_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes14.dex */
public final class b {

    @com.google.gson.annotations.c("value")
    @l
    private final c value;

    @com.google.gson.annotations.c("valueDark")
    @l
    private final c valueDark;

    @com.google.gson.annotations.c("valueName")
    @l
    private final String valueName;

    public b(@l c cVar, @l c cVar2, @l String str) {
        this.value = cVar;
        this.valueDark = cVar2;
        this.valueName = str;
    }

    @l
    /* renamed from: a, reason: from getter */
    public final c getValue() {
        return this.value;
    }

    @l
    /* renamed from: b, reason: from getter */
    public final c getValueDark() {
        return this.valueDark;
    }

    @l
    /* renamed from: c, reason: from getter */
    public final String getValueName() {
        return this.valueName;
    }
}
