package androidx.camera.core.processing;

import androidx.camera.core.impl.DeferrableSurface;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class u implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24478b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ DeferrableSurface f24479c;

    public /* synthetic */ u(DeferrableSurface deferrableSurface, int i12) {
        this.f24478b = i12;
        this.f24479c = deferrableSurface;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f24478b) {
            case 0:
                this.f24479c.a();
                break;
            default:
                this.f24479c.b();
                break;
        }
    }
}
