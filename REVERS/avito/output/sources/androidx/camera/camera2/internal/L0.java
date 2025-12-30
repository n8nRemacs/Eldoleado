package androidx.camera.camera2.internal;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class L0 implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23011b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ N0 f23012c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ K0 f23013d;

    public /* synthetic */ L0(N0 n02, K0 k02, int i12) {
        this.f23011b = i12;
        this.f23012c = n02;
        this.f23013d = k02;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23011b) {
            case 0:
                N0 n02 = this.f23012c;
                K0 k02 = this.f23013d;
                C20009g0 c20009g0 = n02.f23031b;
                synchronized (c20009g0.f23328b) {
                    c20009g0.f23329c.remove(n02);
                    c20009g0.f23330d.remove(n02);
                }
                n02.r(k02);
                Objects.requireNonNull(n02.f23035f);
                n02.f23035f.n(k02);
                return;
            default:
                N0 n03 = this.f23012c;
                Objects.requireNonNull(n03.f23035f);
                n03.f23035f.r(this.f23013d);
                return;
        }
    }
}
