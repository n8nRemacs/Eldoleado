package AM;

import Y61.l;
import kotlin.Metadata;

/* compiled from: ApiImageScoreV1Response.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\r\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"LAM/g;", "", "", "errorMessage", "", "pass", "", "score", "<init>", "(Ljava/lang/String;ZD)V", "Ljava/lang/String;", "getErrorMessage", "()Ljava/lang/String;", "Z", "getPass", "()Z", "D", "getScore", "()D", "_avito_image-perception_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes14.dex */
public final class g {

    @com.google.gson.annotations.c("errorMessage")
    @l
    private final String errorMessage;

    @com.google.gson.annotations.c("pass")
    private final boolean pass;

    @com.google.gson.annotations.c("score")
    private final double score;

    public g(@l String str, boolean z12, double d12) {
        this.errorMessage = str;
        this.pass = z12;
        this.score = d12;
    }
}
