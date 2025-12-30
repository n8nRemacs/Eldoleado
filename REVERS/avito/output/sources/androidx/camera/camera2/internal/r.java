package androidx.camera.camera2.internal;

import androidx.camera.camera2.internal.A0;
import androidx.concurrent.futures.b;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class r implements A0.c, b.c {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C20039w f23429b;

    public /* synthetic */ r(C20039w c20039w) {
        this.f23429b = c20039w;
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        C20039w c20039w = this.f23429b;
        c20039w.getClass();
        try {
            c20039w.f23468d.execute(new RunnableC20016k(2, c20039w, aVar));
            return "isMeteringRepeatingAttached";
        } catch (RejectedExecutionException unused) {
            aVar.d(new RuntimeException("Unable to check if MeteringRepeating is attached. Camera executor shut down."));
            return "isMeteringRepeatingAttached";
        }
    }
}
