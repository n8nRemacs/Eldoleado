package androidx.compose.animation;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: EnterExitTransition.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Landroidx/compose/animation/EnterExitState;", "it", "Landroidx/compose/ui/graphics/r1;", "invoke-LIALnN8", "(Landroidx/compose/animation/EnterExitState;)J", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* loaded from: classes.dex */
final class O0 extends kotlin.jvm.internal.N implements Y41.l<EnterExitState, androidx.compose.ui.graphics.r1> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.graphics.r1 f25937l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ AbstractC20359j1 f25938m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ AbstractC20365l1 f25939n;

    /* compiled from: EnterExitTransition.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class a {
        static {
            int[] iArr = new int[EnterExitState.values().length];
            try {
                iArr[1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                EnterExitState enterExitState = EnterExitState.f25872b;
                iArr[0] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                EnterExitState enterExitState2 = EnterExitState.f25872b;
                iArr[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public O0(androidx.compose.ui.graphics.r1 r1Var, AbstractC20359j1 abstractC20359j1, AbstractC20365l1 abstractC20365l1) {
        super(1);
        this.f25937l = r1Var;
        this.f25938m = abstractC20359j1;
        this.f25939n = abstractC20365l1;
    }

    @Override // Y41.l
    public final androidx.compose.ui.graphics.r1 invoke(EnterExitState enterExitState) {
        long j12;
        int iOrdinal = enterExitState.ordinal();
        AbstractC20359j1 abstractC20359j1 = this.f25938m;
        androidx.compose.ui.graphics.r1 r1VarA = null;
        AbstractC20365l1 abstractC20365l1 = this.f25939n;
        if (iOrdinal == 0) {
            A1 a12 = abstractC20359j1.getF26503c().f26628d;
            if (a12 != null || (a12 = abstractC20365l1.getF26511d().f26628d) != null) {
                r1VarA = androidx.compose.ui.graphics.r1.a(a12.f25839b);
            }
        } else if (iOrdinal == 1) {
            r1VarA = this.f25937l;
        } else {
            if (iOrdinal != 2) {
                throw new NoWhenBranchMatchedException();
            }
            A1 a13 = abstractC20365l1.getF26511d().f26628d;
            if (a13 != null || (a13 = abstractC20359j1.getF26503c().f26628d) != null) {
                r1VarA = androidx.compose.ui.graphics.r1.a(a13.f25839b);
            }
        }
        if (r1VarA != null) {
            j12 = r1VarA.f39757a;
        } else {
            androidx.compose.ui.graphics.r1.f39755b.getClass();
            j12 = androidx.compose.ui.graphics.r1.f39756c;
        }
        return androidx.compose.ui.graphics.r1.a(j12);
    }
}
