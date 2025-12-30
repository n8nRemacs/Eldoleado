package androidx.camera.camera2.internal;

import androidx.concurrent.futures.b;
import java.util.concurrent.ScheduledFuture;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.camera2.internal.q0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20029q0 implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23426b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C20034t0 f23427c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ long f23428d;

    public /* synthetic */ RunnableC20029q0(C20034t0 c20034t0, long j12, int i12) {
        this.f23426b = i12;
        this.f23427c = c20034t0;
        this.f23428d = j12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23426b) {
            case 0:
                C20034t0 c20034t0 = this.f23427c;
                c20034t0.getClass();
                c20034t0.f23441b.execute(new RunnableC20029q0(c20034t0, this.f23428d, 3));
                break;
            case 1:
                C20034t0 c20034t02 = this.f23427c;
                c20034t02.getClass();
                c20034t02.f23441b.execute(new RunnableC20029q0(c20034t02, this.f23428d, 2));
                break;
            case 2:
                C20034t0 c20034t03 = this.f23427c;
                if (this.f23428d == c20034t03.f23450k) {
                    c20034t03.b();
                    break;
                }
                break;
            default:
                C20034t0 c20034t04 = this.f23427c;
                if (this.f23428d == c20034t04.f23450k) {
                    c20034t04.f23452m = false;
                    ScheduledFuture<?> scheduledFuture = c20034t04.f23449j;
                    if (scheduledFuture != null) {
                        scheduledFuture.cancel(true);
                        c20034t04.f23449j = null;
                    }
                    b.a<androidx.camera.core.N> aVar = c20034t04.f23458s;
                    if (aVar != null) {
                        aVar.b(new androidx.camera.core.N(false));
                        c20034t04.f23458s = null;
                        break;
                    }
                }
                break;
        }
    }
}
