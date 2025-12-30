package androidx.arch.core.executor;

import com.avito.android.app.task.BaselineProfileStateReportingTask;
import java.util.concurrent.Executor;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class a implements Executor {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f22692b;

    public /* synthetic */ a(int i12) {
        this.f22692b = i12;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        switch (this.f22692b) {
            case 0:
                b.a().f22695a.f22697b.execute(runnable);
                break;
            case 1:
                runnable.run();
                break;
            default:
                BaselineProfileStateReportingTask.directExecutor$lambda$1(runnable);
                break;
        }
    }
}
