package androidx.camera.core;

import android.view.Surface;
import androidx.camera.core.K0;
import androidx.concurrent.futures.b;
import java.util.concurrent.CancellationException;

/* compiled from: SurfaceRequest.java */
/* loaded from: classes.dex */
class J0 implements androidx.camera.core.impl.utils.futures.c<Surface> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ com.google.common.util.concurrent.D0 f23585a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ b.a f23586b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f23587c;

    public J0(com.google.common.util.concurrent.D0 d02, b.a aVar, String str) {
        this.f23585a = d02;
        this.f23586b = aVar;
        this.f23587c = str;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        boolean z12 = th2 instanceof CancellationException;
        b.a aVar = this.f23586b;
        if (z12) {
            androidx.core.util.z.g(null, aVar.d(new K0.b(AK.c.s(new StringBuilder(), this.f23587c, " cancelled."), th2)));
        } else {
            aVar.b(null);
        }
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(@j.P Surface surface) {
        androidx.camera.core.impl.utils.futures.f.i(this.f23586b, this.f23585a);
    }
}
