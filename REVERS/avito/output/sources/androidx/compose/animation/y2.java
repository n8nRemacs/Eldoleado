package androidx.compose.animation;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: EnterExitTransition.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0081\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/y2;", "", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class y2 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.l
    public final C20377p1 f26625a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.l
    public final u2 f26626b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.l
    public final C20381r0 f26627c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.l
    public final A1 f26628d;

    /* renamed from: e, reason: collision with root package name */
    public final boolean f26629e;

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public final Map<A2<?>, z2> f26630f;

    public y2() {
        this(null, null, null, null, false, null, 63, null);
    }

    public final boolean equals(@Y61.l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof y2)) {
            return false;
        }
        y2 y2Var = (y2) obj;
        return kotlin.jvm.internal.L.f(this.f26625a, y2Var.f26625a) && kotlin.jvm.internal.L.f(this.f26626b, y2Var.f26626b) && kotlin.jvm.internal.L.f(this.f26627c, y2Var.f26627c) && kotlin.jvm.internal.L.f(this.f26628d, y2Var.f26628d) && this.f26629e == y2Var.f26629e && kotlin.jvm.internal.L.f(this.f26630f, y2Var.f26630f);
    }

    public final int hashCode() {
        C20377p1 c20377p1 = this.f26625a;
        int iHashCode = (c20377p1 == null ? 0 : c20377p1.hashCode()) * 31;
        u2 u2Var = this.f26626b;
        int iHashCode2 = (iHashCode + (u2Var == null ? 0 : u2Var.hashCode())) * 31;
        C20381r0 c20381r0 = this.f26627c;
        int iHashCode3 = (iHashCode2 + (c20381r0 == null ? 0 : c20381r0.hashCode())) * 31;
        A1 a12 = this.f26628d;
        return this.f26630f.hashCode() + androidx.appcompat.app.r.i((iHashCode3 + (a12 != null ? a12.hashCode() : 0)) * 31, 31, this.f26629e);
    }

    @Y61.k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("TransitionData(fade=");
        sb2.append(this.f26625a);
        sb2.append(", slide=");
        sb2.append(this.f26626b);
        sb2.append(", changeSize=");
        sb2.append(this.f26627c);
        sb2.append(", scale=");
        sb2.append(this.f26628d);
        sb2.append(", hold=");
        sb2.append(this.f26629e);
        sb2.append(", effectsMap=");
        return androidx.appcompat.app.r.w(sb2, this.f26630f, ')');
    }

    /* JADX WARN: Multi-variable type inference failed */
    public y2(@Y61.l C20377p1 c20377p1, @Y61.l u2 u2Var, @Y61.l C20381r0 c20381r0, @Y61.l A1 a12, boolean z12, @Y61.k Map<A2<?>, ? extends z2> map) {
        this.f26625a = c20377p1;
        this.f26626b = u2Var;
        this.f26627c = c20381r0;
        this.f26628d = a12;
        this.f26629e = z12;
        this.f26630f = map;
    }

    public /* synthetic */ y2(C20377p1 c20377p1, u2 u2Var, C20381r0 c20381r0, A1 a12, boolean z12, Map map, int i12, C42822w c42822w) {
        this((i12 & 1) != 0 ? null : c20377p1, (i12 & 2) != 0 ? null : u2Var, (i12 & 4) != 0 ? null : c20381r0, (i12 & 8) == 0 ? a12 : null, (i12 & 16) != 0 ? false : z12, (i12 & 32) != 0 ? kotlin.collections.P0.c() : map);
    }
}
