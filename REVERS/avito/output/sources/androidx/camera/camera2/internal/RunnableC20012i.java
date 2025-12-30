package androidx.camera.camera2.internal;

/* compiled from: R8$$SyntheticClass */
/* renamed from: androidx.camera.camera2.internal.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC20012i implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23341b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C20026p f23342c;

    public /* synthetic */ RunnableC20012i(C20026p c20026p, int i12) {
        this.f23341b = i12;
        this.f23342c = c20026p;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23341b) {
            case 0:
                C20026p c20026p = this.f23342c;
                c20026p.m(c20026p.f23399m.f2837h);
                break;
            default:
                this.f23342c.w();
                break;
        }
    }
}
