package androidx.compose.animation.core;

import androidx.compose.ui.unit.h;
import androidx.compose.ui.unit.j;
import androidx.compose.ui.unit.q;
import androidx.compose.ui.unit.u;
import kotlin.Metadata;
import l0.g;
import l0.n;

/* compiled from: VisibilityThresholds.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation-core_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class b2 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public static final l0.j f26220a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final Object f26221b;

    static {
        Float fValueOf = Float.valueOf(0.5f);
        f26220a = new l0.j(0.5f, 0.5f, 0.5f, 0.5f);
        int i12 = kotlin.jvm.internal.J.f406821a;
        kotlin.Q q12 = new kotlin.Q(J1.f26076b, Float.valueOf(1.0f));
        u.a aVar = androidx.compose.ui.unit.u.f42871b;
        kotlin.Q q13 = new kotlin.Q(J1.f26082h, Float.valueOf(1.0f));
        q.a aVar2 = androidx.compose.ui.unit.q.f42862b;
        kotlin.Q q14 = new kotlin.Q(J1.f26081g, Float.valueOf(1.0f));
        int i13 = kotlin.jvm.internal.A.f406820a;
        kotlin.Q q15 = new kotlin.Q(J1.f26075a, Float.valueOf(0.01f));
        kotlin.Q q16 = new kotlin.Q(J1.f26083i, fValueOf);
        n.a aVar3 = l0.n.f413402b;
        kotlin.Q q17 = new kotlin.Q(J1.f26079e, fValueOf);
        g.a aVar4 = l0.g.f413384b;
        kotlin.Q q18 = new kotlin.Q(J1.f26080f, fValueOf);
        h.a aVar5 = androidx.compose.ui.unit.h.f42849c;
        kotlin.Q q19 = new kotlin.Q(J1.f26077c, Float.valueOf(0.1f));
        j.a aVar6 = androidx.compose.ui.unit.j.f42853b;
        f26221b = kotlin.collections.P0.g(q12, q13, q14, q15, q16, q17, q18, q19, new kotlin.Q(J1.f26078d, Float.valueOf(0.1f)));
    }

    public static final long a() {
        long j12 = 1;
        long j13 = (j12 & 4294967295L) | (j12 << 32);
        q.a aVar = androidx.compose.ui.unit.q.f42862b;
        return j13;
    }

    public static final long b() {
        long j12 = 1;
        long j13 = (j12 & 4294967295L) | (j12 << 32);
        u.a aVar = androidx.compose.ui.unit.u.f42871b;
        return j13;
    }
}
