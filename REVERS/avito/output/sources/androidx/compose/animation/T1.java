package androidx.compose.animation;

import kotlin.Metadata;

/* compiled from: SharedTransitionScope.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "Landroidx/compose/animation/EnterExitState;", "invoke", "(Landroidx/compose/animation/EnterExitState;)Ljava/lang/Boolean;"}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class T1 extends kotlin.jvm.internal.N implements Y41.l<EnterExitState, Boolean> {
    static {
        new T1();
    }

    public T1() {
        super(1);
    }

    @Override // Y41.l
    public final Boolean invoke(EnterExitState enterExitState) {
        return Boolean.valueOf(enterExitState == EnterExitState.f25873c);
    }
}
