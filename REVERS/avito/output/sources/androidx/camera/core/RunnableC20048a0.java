package androidx.camera.core;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.core.a0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20048a0 implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23707b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f23708c;

    public /* synthetic */ RunnableC20048a0(Object obj, int i12) {
        this.f23707b = i12;
        this.f23708c = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23707b) {
            case 0:
                Y y12 = (Y) this.f23708c;
                synchronized (y12.f23696v) {
                    try {
                        y12.f23698x = null;
                        InterfaceC20120j0 interfaceC20120j0 = y12.f23697w;
                        if (interfaceC20120j0 != null) {
                            y12.f23697w = null;
                            y12.f(interfaceC20120j0);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                return;
            case 1:
                ((v0) this.f23708c).p();
                return;
            default:
                ((K0) this.f23708c).f23594f.cancel(true);
                return;
        }
    }
}
