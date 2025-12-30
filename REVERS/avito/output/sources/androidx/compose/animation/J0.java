package androidx.compose.animation;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: EnterExitTransition.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "Landroidx/compose/animation/EnterExitState;", "invoke", "(Landroidx/compose/animation/EnterExitState;)Ljava/lang/Float;"}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class J0 extends kotlin.jvm.internal.N implements Y41.l<EnterExitState, Float> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ AbstractC20359j1 f25908l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ AbstractC20365l1 f25909m;

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
    public J0(AbstractC20359j1 abstractC20359j1, AbstractC20365l1 abstractC20365l1) {
        super(1);
        this.f25908l = abstractC20359j1;
        this.f25909m = abstractC20365l1;
    }

    @Override // Y41.l
    public final Float invoke(EnterExitState enterExitState) {
        int iOrdinal = enterExitState.ordinal();
        float f12 = 1.0f;
        if (iOrdinal == 0) {
            C20377p1 c20377p1 = this.f25908l.getF26503c().f26625a;
            if (c20377p1 != null) {
                f12 = c20377p1.f26542a;
            }
        } else if (iOrdinal != 1) {
            if (iOrdinal != 2) {
                throw new NoWhenBranchMatchedException();
            }
            C20377p1 c20377p12 = this.f25909m.getF26511d().f26625a;
            if (c20377p12 != null) {
                f12 = c20377p12.f26542a;
            }
        }
        return Float.valueOf(f12);
    }
}
