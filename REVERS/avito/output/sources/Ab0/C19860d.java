package ab0;

import Y61.l;
import kotlin.Metadata;

/* compiled from: ProfileBasicInfoEditV2Response.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u00002\u00020\u0001B\u001b\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0006\u0010\u0007R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000b\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lab0/d;", "", "Lab0/c;", "error", "", "message", "<init>", "(Lab0/c;Ljava/lang/String;)V", "Lab0/c;", "a", "()Lab0/c;", "Ljava/lang/String;", "b", "()Ljava/lang/String;", "_avito-discouraged_avito-api_profile-management"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: ab0.d, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C19860d {

    @com.google.gson.annotations.c("error")
    @l
    private final C19859c error;

    @com.google.gson.annotations.c("message")
    @l
    private final String message;

    public C19860d(@l C19859c c19859c, @l String str) {
        this.error = c19859c;
        this.message = str;
    }

    @l
    /* renamed from: a, reason: from getter */
    public final C19859c getError() {
        return this.error;
    }

    @l
    /* renamed from: b, reason: from getter */
    public final String getMessage() {
        return this.message;
    }
}
