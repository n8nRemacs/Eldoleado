package androidx.compose.animation.core;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;

/* compiled from: InternalMotatorMutex.jvm.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/MutationInterruptedException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "<init>", "()V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class MutationInterruptedException extends CancellationException {
    public MutationInterruptedException() {
        super("Mutation interrupted");
    }

    @Override // java.lang.Throwable
    @Y61.k
    public final Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }
}
