package AG0;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import kotlin.Metadata;

/* compiled from: Api1TransactionPageGetResponse.kt */
@P
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0007\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"LAG0/a;", "", "LAG0/b;", "debug", "LAG0/c;", "result", "", "status", "<init>", "(LAG0/b;LAG0/c;Ljava/lang/String;)V", "LAG0/b;", "getDebug", "()LAG0/b;", "LAG0/c;", "a", "()LAG0/c;", "Ljava/lang/String;", "getStatus", "()Ljava/lang/String;", "_avito_universal-transaction_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class a {

    @com.google.gson.annotations.c("debug")
    @l
    private final b debug;

    @com.google.gson.annotations.c("result")
    @k
    private final c result;

    @com.google.gson.annotations.c("status")
    @k
    private final String status;

    public a(@l b bVar, @k c cVar, @k String str) {
        this.debug = bVar;
        this.result = cVar;
        this.status = str;
    }

    @k
    /* renamed from: a, reason: from getter */
    public final c getResult() {
        return this.result;
    }
}
