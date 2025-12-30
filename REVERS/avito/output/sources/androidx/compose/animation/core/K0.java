package androidx.compose.animation.core;

import com.avito.android.beduin.common.component.input.BeduinInputModel;
import kotlin.Metadata;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: Transition.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "androidx.compose.animation.core.SeekableTransitionState", f = "Transition.kt", i = {0, 0, 1, 1}, l = {543, 2142}, m = "waitForComposition", n = {"this", "expectedState", "this", "expectedState"}, s = {"L$0", "L$1", "L$0", "L$1"})
/* loaded from: classes.dex */
final class K0 extends ContinuationImpl {

    /* renamed from: q, reason: collision with root package name */
    public /* synthetic */ Object f26102q;

    /* renamed from: r, reason: collision with root package name */
    public int f26103r;

    public K0() {
        throw null;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Y61.l
    public final Object invokeSuspend(@Y61.k Object obj) {
        this.f26102q = obj;
        this.f26103r |= BeduinInputModel.MIN_TEXT_VERSION;
        throw null;
    }
}
