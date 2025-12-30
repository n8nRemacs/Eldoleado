package androidx.camera.core.imagecapture;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class L implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23797b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ N f23798c;

    public /* synthetic */ L(N n12, int i12) {
        this.f23797b = i12;
        this.f23798c = n12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23797b) {
            case 0:
                N n12 = this.f23798c;
                n12.f23804d = null;
                n12.b();
                break;
            default:
                this.f23798c.b();
                break;
        }
    }
}
