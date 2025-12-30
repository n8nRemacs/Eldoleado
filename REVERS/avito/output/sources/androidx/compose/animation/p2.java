package androidx.compose.animation;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimatedContent.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/p2;", "Landroidx/compose/animation/o2;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class p2 implements o2 {

    /* renamed from: a, reason: collision with root package name */
    public final boolean f26544a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final Y41.p<androidx.compose.ui.unit.u, androidx.compose.ui.unit.u, androidx.compose.animation.core.V<androidx.compose.ui.unit.u>> f26545b;

    /* JADX WARN: Multi-variable type inference failed */
    public p2(boolean z12, @Y61.k Y41.p<? super androidx.compose.ui.unit.u, ? super androidx.compose.ui.unit.u, ? extends androidx.compose.animation.core.V<androidx.compose.ui.unit.u>> pVar) {
        this.f26544a = z12;
        this.f26545b = pVar;
    }

    @Override // androidx.compose.animation.o2
    /* renamed from: a, reason: from getter */
    public final boolean getF26544a() {
        return this.f26544a;
    }

    @Override // androidx.compose.animation.o2
    @Y61.k
    public final androidx.compose.animation.core.V<androidx.compose.ui.unit.u> b(long j12, long j13) {
        return this.f26545b.invoke(androidx.compose.ui.unit.u.a(j12), androidx.compose.ui.unit.u.a(j13));
    }

    public /* synthetic */ p2(boolean z12, Y41.p pVar, int i12, C42822w c42822w) {
        this((i12 & 1) != 0 ? true : z12, pVar);
    }
}
