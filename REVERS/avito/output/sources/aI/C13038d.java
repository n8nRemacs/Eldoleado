package Ai;

import Y61.k;
import androidx.fragment.app.ActivityC22955m;
import kotlin.Metadata;

/* compiled from: DefaultCloseHandler.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAi/d;", "LAi/c;", "_avito_beduin_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ai.d, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C13038d implements InterfaceC13037c {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final ActivityC22955m f588a;

    public C13038d(@k ActivityC22955m activityC22955m) {
        this.f588a = activityC22955m;
    }

    @Override // Ai.InterfaceC13037c
    public final void close() {
        this.f588a.finish();
    }
}
