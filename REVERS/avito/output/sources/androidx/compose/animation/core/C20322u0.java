package androidx.compose.animation.core;

import androidx.compose.runtime.F3;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

/* compiled from: InternalMutatorMutex.kt */
@F3
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0001\u0018\u00002\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/u0;", "", "<init>", "()V", "a", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.u0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20322u0 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final AtomicReference<a> f26410a = new AtomicReference<>(null);

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final kotlinx.coroutines.sync.d f26411b = new kotlinx.coroutines.sync.d();

    /* compiled from: InternalMutatorMutex.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0002\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/u0$a;", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.core.u0$a */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        @Y61.k
        public final kotlinx.coroutines.N0 f26412a;

        public a(@Y61.k kotlinx.coroutines.N0 n02) {
            MutatePriority mutatePriority = MutatePriority.f26109b;
            this.f26412a = n02;
        }
    }

    public static Object a(C20322u0 c20322u0, Y41.l lVar, Continuation continuation) {
        MutatePriority mutatePriority = MutatePriority.f26109b;
        c20322u0.getClass();
        return kotlinx.coroutines.U.c(new C20325v0(c20322u0, lVar, null), continuation);
    }
}
