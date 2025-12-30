package A1;

import Y61.k;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Q0;
import kotlinx.coroutines.T;

/* compiled from: CloseableCoroutineScope.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002¨\u0006\u0003"}, d2 = {"LA1/a;", "Ljava/lang/AutoCloseable;", "Lkotlinx/coroutines/T;", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class a implements AutoCloseable, T {

    /* renamed from: b, reason: collision with root package name */
    @k
    public final CoroutineContext f36b;

    public a(@k CoroutineContext coroutineContext) {
        this.f36b = coroutineContext;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        Q0.b(this.f36b, null);
    }

    @Override // kotlinx.coroutines.T
    @k
    /* renamed from: getCoroutineContext, reason: from getter */
    public final CoroutineContext getF36b() {
        return this.f36b;
    }
}
