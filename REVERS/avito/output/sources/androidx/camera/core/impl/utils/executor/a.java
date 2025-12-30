package androidx.camera.core.impl.utils.executor;

import android.os.Process;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class a implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24207b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Runnable f24208c;

    public /* synthetic */ a(int i12, Runnable runnable) {
        this.f24207b = i12;
        this.f24208c = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() throws SecurityException, IllegalArgumentException {
        switch (this.f24207b) {
            case 0:
                Process.setThreadPriority(-16);
                this.f24208c.run();
                break;
            default:
                com.avito.android.lib.design.gallery.h.setData$lambda$10(this.f24208c);
                break;
        }
    }
}
