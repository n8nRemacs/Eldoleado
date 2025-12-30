package androidx.compose.animation;

import androidx.compose.runtime.F3;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: EnterExitTransition.kt */
@androidx.compose.runtime.H0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0001\u0004B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0001\u0005¨\u0006\u0006"}, d2 = {"Landroidx/compose/animation/l1;", "", "<init>", "()V", "a", "Landroidx/compose/animation/m1;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.l1, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20365l1 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public static final a f26506a = new a(null);

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final AbstractC20365l1 f26507b = new C20368m1(new y2(null, null, null, null, false, null, 63, null));

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public static final AbstractC20365l1 f26508c = new C20368m1(new y2(null, null, null, null, true, null, 47, null));

    /* compiled from: EnterExitTransition.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/l1$a;", "", "<init>", "()V", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: androidx.compose.animation.l1$a */
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public /* synthetic */ AbstractC20365l1(C42822w c42822w) {
        this();
    }

    @Y61.k
    public abstract y2 a();

    @F3
    @Y61.k
    public final AbstractC20365l1 b(@Y61.k AbstractC20365l1 abstractC20365l1) {
        C20377p1 c20377p1 = abstractC20365l1.a().f26625a;
        if (c20377p1 == null) {
            c20377p1 = a().f26625a;
        }
        C20377p1 c20377p12 = c20377p1;
        u2 u2Var = abstractC20365l1.a().f26626b;
        if (u2Var == null) {
            u2Var = a().f26626b;
        }
        u2 u2Var2 = u2Var;
        C20381r0 c20381r0 = abstractC20365l1.a().f26627c;
        if (c20381r0 == null) {
            c20381r0 = a().f26627c;
        }
        C20381r0 c20381r02 = c20381r0;
        A1 a12 = abstractC20365l1.a().f26628d;
        if (a12 == null) {
            a12 = a().f26628d;
        }
        return new C20368m1(new y2(c20377p12, u2Var2, c20381r02, a12, abstractC20365l1.a().f26629e || a().f26629e, kotlin.collections.P0.k(a().f26630f, abstractC20365l1.a().f26630f)));
    }

    public final boolean equals(@Y61.l Object obj) {
        return (obj instanceof AbstractC20365l1) && kotlin.jvm.internal.L.f(((AbstractC20365l1) obj).a(), a());
    }

    public final int hashCode() {
        return a().hashCode();
    }

    @Y61.k
    public final String toString() {
        if (equals(f26507b)) {
            return "ExitTransition.None";
        }
        if (equals(f26508c)) {
            return "ExitTransition.KeepUntilTransitionsFinished";
        }
        y2 y2VarA = a();
        StringBuilder sb2 = new StringBuilder("ExitTransition: \nFade - ");
        C20377p1 c20377p1 = y2VarA.f26625a;
        sb2.append(c20377p1 != null ? c20377p1.toString() : null);
        sb2.append(",\nSlide - ");
        u2 u2Var = y2VarA.f26626b;
        sb2.append(u2Var != null ? u2Var.toString() : null);
        sb2.append(",\nShrink - ");
        C20381r0 c20381r0 = y2VarA.f26627c;
        sb2.append(c20381r0 != null ? c20381r0.toString() : null);
        sb2.append(",\nScale - ");
        A1 a12 = y2VarA.f26628d;
        sb2.append(a12 != null ? a12.toString() : null);
        sb2.append(",\nKeepUntilTransitionsFinished - ");
        sb2.append(y2VarA.f26629e);
        return sb2.toString();
    }

    public AbstractC20365l1() {
    }
}
