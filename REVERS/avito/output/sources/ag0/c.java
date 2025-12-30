package AG0;

import Y61.k;
import androidx.compose.runtime.internal.P;
import com.avito.android.remote.model.BeduinV2;
import kotlin.Metadata;

/* compiled from: Api1TransactionPageGetResponse.kt */
@P
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\f\n\u0004\b\u0005\u0010\u000b\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"LAG0/c;", "", "Lcom/avito/android/remote/model/BeduinV2;", "content", "LAG0/d;", "statusBarColor", "<init>", "(Lcom/avito/android/remote/model/BeduinV2;LAG0/d;)V", "Lcom/avito/android/remote/model/BeduinV2;", "a", "()Lcom/avito/android/remote/model/BeduinV2;", "LAG0/d;", "b", "()LAG0/d;", "_avito_universal-transaction_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class c {

    @com.google.gson.annotations.c("content")
    @k
    private final BeduinV2 content;

    @com.google.gson.annotations.c("statusBarColor")
    @k
    private final d statusBarColor;

    public c(@k BeduinV2 beduinV2, @k d dVar) {
        this.content = beduinV2;
        this.statusBarColor = dVar;
    }

    @k
    /* renamed from: a, reason: from getter */
    public final BeduinV2 getContent() {
        return this.content;
    }

    @k
    /* renamed from: b, reason: from getter */
    public final d getStatusBarColor() {
        return this.statusBarColor;
    }
}
