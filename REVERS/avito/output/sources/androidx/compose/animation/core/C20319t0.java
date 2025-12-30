package androidx.compose.animation.core;

import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.InterfaceC22204y1;
import kotlin.Metadata;

/* compiled from: Transition.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002¨\u0006\u0003"}, d2 = {"Landroidx/compose/animation/core/t0;", "S", "Landroidx/compose/animation/core/F1;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.t0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20319t0<S> extends F1<S> {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26405b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26406c;

    public C20319t0(S s5) {
        super(null);
        this.f26405b = C22126m3.g(s5);
        this.f26406c = C22126m3.g(s5);
    }

    @Override // androidx.compose.animation.core.F1
    public final S a() {
        return (S) ((C22082i3) this.f26405b).getF42167b();
    }

    @Override // androidx.compose.animation.core.F1
    public final void b(S s5) {
        ((C22082i3) this.f26405b).setValue(s5);
    }

    @Override // androidx.compose.animation.core.F1
    public final void d() {
    }

    @Override // androidx.compose.animation.core.F1
    public final void c(@Y61.k C20288i1<S> c20288i1) {
    }
}
