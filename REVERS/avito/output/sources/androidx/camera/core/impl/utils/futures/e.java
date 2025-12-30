package androidx.camera.core.impl.utils.futures;

import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.AbstractC37532c;
import com.google.common.util.concurrent.D0;
import com.google.common.util.concurrent.Q0;
import io.reactivex.rxjava3.core.K;
import io.reactivex.rxjava3.core.M;
import java.util.concurrent.Executor;
import q.InterfaceC47174a;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class e implements b.c, M {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ D0 f24253b;

    public /* synthetic */ e(D0 d02) {
        this.f24253b = d02;
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        InterfaceC47174a<?, ?> interfaceC47174a = f.f24254a;
        Executor executorA = androidx.camera.core.impl.utils.executor.c.a();
        D0 d02 = this.f24253b;
        f.j(false, d02, interfaceC47174a, aVar, executorA);
        return "nonCancellationPropagating[" + d02 + "]";
    }

    @Override // io.reactivex.rxjava3.core.M
    public void j(final K k12) {
        final D0 d02 = this.f24253b;
        ((AbstractC37532c) d02).N(new Runnable() { // from class: com.avito.android.messenger.conversation.mvi.voice.S
            @Override // java.lang.Runnable
            public final void run() {
                io.reactivex.rxjava3.core.K k13 = k12;
                try {
                    k13.onSuccess(d02.get());
                } catch (Throwable th2) {
                    k13.onError(th2);
                }
            }
        }, Q0.a());
    }
}
