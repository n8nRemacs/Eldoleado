package androidx.camera.camera2.internal.compat;

import androidx.camera.camera2.internal.compat.w;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class u implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f23237b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ w.a f23238c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ String f23239d;

    public /* synthetic */ u(w.a aVar, String str, int i12) {
        this.f23237b = i12;
        this.f23238c = aVar;
        this.f23239d = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f23237b) {
            case 0:
                this.f23238c.f23244b.onCameraAvailable(this.f23239d);
                break;
            default:
                this.f23238c.f23244b.onCameraUnavailable(this.f23239d);
                break;
        }
    }
}
