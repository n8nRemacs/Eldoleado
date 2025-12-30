package AF;

import Y61.l;
import kotlin.Metadata;

/* compiled from: ShiftApplyV2Response.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\u0018\u00002\u00020\u0001B%\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\b\u0010\tR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"LAF/c;", "", "LAF/a;", "actionButton", "LAF/b;", "sheet", "", "successUri", "<init>", "(LAF/a;LAF/b;Ljava/lang/String;)V", "LAF/a;", "a", "()LAF/a;", "LAF/b;", "b", "()LAF/b;", "Ljava/lang/String;", "c", "()Ljava/lang/String;", "_avito_gig_apply-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes13.dex */
public final class c {

    @com.google.gson.annotations.c("actionButton")
    @l
    private final a actionButton;

    @com.google.gson.annotations.c("sheet")
    @l
    private final b sheet;

    @com.google.gson.annotations.c("successUri")
    @l
    private final String successUri;

    public c(@l a aVar, @l b bVar, @l String str) {
        this.actionButton = aVar;
        this.sheet = bVar;
        this.successUri = str;
    }

    @l
    /* renamed from: a, reason: from getter */
    public final a getActionButton() {
        return this.actionButton;
    }

    @l
    /* renamed from: b, reason: from getter */
    public final b getSheet() {
        return this.sheet;
    }

    @l
    /* renamed from: c, reason: from getter */
    public final String getSuccessUri() {
        return this.successUri;
    }
}
