package a91;

import com.avito.android.remote.model.messenger.context_actions.ContextActionHandler;
import java.util.Map;
import kotlin.Metadata;

/* compiled from: CreateItemChannelResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B4\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0019\u0010\u0006\u001a\u0015\u0012\u0004\u0012\u00020\u0002\u0012\t\u0012\u00070\u0001¢\u0006\u0002\b\u0005\u0018\u00010\u0004\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\t\u0010\nR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u000b\u001a\u0004\b\f\u0010\rR-\u0010\u0006\u001a\u0015\u0012\u0004\u0012\u00020\u0002\u0012\t\u0012\u00070\u0001¢\u0006\u0002\b\u0005\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\b\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"La91/c0;", "", "", "method", "", "LX41/o;", "params", "La91/i0;", ContextActionHandler.MethodCall.REACTION, "<init>", "(Ljava/lang/String;Ljava/util/Map;La91/i0;)V", "Ljava/lang/String;", "a", "()Ljava/lang/String;", "Ljava/util/Map;", "b", "()Ljava/util/Map;", "La91/i0;", "c", "()La91/i0;", "_avito-discouraged_messenger"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class c0 {

    @com.google.gson.annotations.c("method")
    @Y61.k
    private final String method;

    @com.google.gson.annotations.c("params")
    @Y61.l
    private final Map<String, Object> params;

    @com.google.gson.annotations.c(ContextActionHandler.MethodCall.REACTION)
    @Y61.l
    private final i0 reaction;

    public c0(@Y61.k String str, @Y61.l Map<String, Object> map, @Y61.l i0 i0Var) {
        this.method = str;
        this.params = map;
        this.reaction = i0Var;
    }

    @Y61.k
    /* renamed from: a, reason: from getter */
    public final String getMethod() {
        return this.method;
    }

    @Y61.l
    public final Map<String, Object> b() {
        return this.params;
    }

    @Y61.l
    /* renamed from: c, reason: from getter */
    public final i0 getReaction() {
        return this.reaction;
    }
}
