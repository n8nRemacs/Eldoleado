package androidx.compose.animation;

import kotlin.Metadata;

/* compiled from: EnterExitTransition.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Landroidx/compose/ui/unit/u;", "it", "Landroidx/compose/ui/unit/q;", "invoke-mHKZG7I", "(J)J", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* renamed from: androidx.compose.animation.h1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20353h1 extends kotlin.jvm.internal.N implements Y41.l<androidx.compose.ui.unit.u, androidx.compose.ui.unit.q> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ kotlin.jvm.internal.N f26467l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public C20353h1(Y41.l<? super Integer, Integer> lVar) {
        super(1);
        this.f26467l = (kotlin.jvm.internal.N) lVar;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [Y41.l, kotlin.jvm.internal.N] */
    @Override // Y41.l
    public final androidx.compose.ui.unit.q invoke(androidx.compose.ui.unit.u uVar) {
        return androidx.compose.ui.unit.q.a((0 << 32) | (4294967295L & ((Number) this.f26467l.invoke(Integer.valueOf((int) (uVar.f42872a & 4294967295L)))).intValue()));
    }
}
