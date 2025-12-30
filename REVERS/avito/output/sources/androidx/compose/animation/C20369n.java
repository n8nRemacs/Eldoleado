package androidx.compose.animation;

import kotlin.Metadata;

/* compiled from: AnimatedContent.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"<anonymous>", "", "S", "currentState", "Landroidx/compose/animation/EnterExitState;", "targetState", "invoke", "(Landroidx/compose/animation/EnterExitState;Landroidx/compose/animation/EnterExitState;)Ljava/lang/Boolean;"}, k = 3, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.n, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20369n extends kotlin.jvm.internal.N implements Y41.p<EnterExitState, EnterExitState, Boolean> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ AbstractC20365l1 f26527l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20369n(AbstractC20365l1 abstractC20365l1) {
        super(2);
        this.f26527l = abstractC20365l1;
    }

    @Override // Y41.p
    public final Boolean invoke(EnterExitState enterExitState, EnterExitState enterExitState2) {
        EnterExitState enterExitState3 = enterExitState;
        EnterExitState enterExitState4 = enterExitState2;
        EnterExitState enterExitState5 = EnterExitState.f25874d;
        return Boolean.valueOf(enterExitState3 == enterExitState5 && enterExitState4 == enterExitState5 && !this.f26527l.getF26511d().f26629e);
    }
}
