package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import com.avito.android.beduin.common.component.input.BeduinInputModel;
import kotlin.Metadata;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.l0;

/* compiled from: SuspendAnimation.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "androidx.compose.animation.core.SuspendAnimationKt", f = "SuspendAnimation.kt", i = {0, 0, 0, 0, 1, 1, 1, 1}, l = {231, 280}, m = "animate", n = {"$this$animate", "animation", "block", "lateInitScope", "$this$animate", "animation", "block", "lateInitScope"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3"})
/* loaded from: classes.dex */
final class X0<T, V extends AbstractC20330x> extends ContinuationImpl {

    /* renamed from: q, reason: collision with root package name */
    public r f26174q;

    /* renamed from: r, reason: collision with root package name */
    public InterfaceC20286i f26175r;

    /* renamed from: s, reason: collision with root package name */
    public Y41.l f26176s;

    /* renamed from: t, reason: collision with root package name */
    public l0.h f26177t;

    /* renamed from: u, reason: collision with root package name */
    public /* synthetic */ Object f26178u;

    /* renamed from: v, reason: collision with root package name */
    public int f26179v;

    public X0() {
        throw null;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Y61.l
    public final Object invokeSuspend(@Y61.k Object obj) {
        this.f26178u = obj;
        this.f26179v |= BeduinInputModel.MIN_TEXT_VERSION;
        return C20273d1.b(null, null, 0L, null, this);
    }
}
