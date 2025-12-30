package androidx.compose.animation;

import androidx.compose.runtime.InterfaceC22118l0;
import kotlin.Metadata;

/* compiled from: Effects.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002¸\u0006\u0000"}, d2 = {"androidx/compose/runtime/m0", "Landroidx/compose/runtime/l0;", "runtime_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class h2 implements InterfaceC22118l0 {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Q1 f26468a;

    public h2(Q1 q12) {
        this.f26468a = q12;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, kotlin.C] */
    @Override // androidx.compose.runtime.InterfaceC22118l0
    public final void dispose() {
        Q1 q12 = this.f26468a;
        q12.getClass();
        Q1.f25944l.getClass();
        ((androidx.compose.runtime.snapshots.H) Q1.f25945m.getValue()).c(q12);
        q12.f25948d = true;
    }
}
