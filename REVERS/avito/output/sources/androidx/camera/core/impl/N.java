package androidx.camera.core.impl;

import android.view.Surface;
import androidx.concurrent.futures.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/* compiled from: DeferrableSurfaces.java */
/* loaded from: classes.dex */
class N implements androidx.camera.core.impl.utils.futures.c<List<Surface>> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ b.a f24010a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ScheduledFuture f24011b;

    public N(b.a aVar, ScheduledFuture scheduledFuture) {
        this.f24010a = aVar;
        this.f24011b = scheduledFuture;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        this.f24010a.b(Collections.unmodifiableList(Collections.emptyList()));
        this.f24011b.cancel(true);
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(@j.P List<Surface> list) {
        this.f24010a.b(new ArrayList(list));
        this.f24011b.cancel(true);
    }
}
