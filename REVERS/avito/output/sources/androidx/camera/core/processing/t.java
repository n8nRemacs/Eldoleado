package androidx.camera.core.processing;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class t implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24476b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ z f24477c;

    public /* synthetic */ t(z zVar, int i12) {
        this.f24476b = i12;
        this.f24477c = zVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f24476b) {
            case 0:
                z zVar = this.f24477c;
                zVar.getClass();
                androidx.camera.core.impl.utils.executor.c.d().execute(new t(zVar, 2));
                break;
            case 1:
                this.f24477c.d();
                break;
            default:
                z zVar2 = this.f24477c;
                if (!zVar2.f24509o) {
                    zVar2.e();
                    break;
                }
                break;
        }
    }
}
