package androidx.compose.animation;

import androidx.compose.animation.core.C20288i1;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimatedContent.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002:\u0001\u0003\u0082\u0001\u0001\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0005À\u0006\u0001"}, d2 = {"Landroidx/compose/animation/y;", "S", "Landroidx/compose/animation/core/i1$b;", "a", "Landroidx/compose/animation/AnimatedContentTransitionScopeImpl;", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.y, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public interface InterfaceC20401y<S> extends C20288i1.b<S> {

    /* compiled from: AnimatedContent.kt */
    @androidx.compose.runtime.H0
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0087@\u0018\u00002\u00020\u0001:\u0001\u0002\u0088\u0001\u0003\u0092\u0001\u00020\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/y$a;", "", "a", "value", "", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    @X41.g
    /* renamed from: androidx.compose.animation.y$a */
    public static final class a {

        /* renamed from: b, reason: collision with root package name */
        public static final int f26618b;

        /* renamed from: c, reason: collision with root package name */
        public static final int f26619c;

        /* renamed from: d, reason: collision with root package name */
        public static final int f26620d;

        /* renamed from: e, reason: collision with root package name */
        public static final int f26621e;

        /* renamed from: f, reason: collision with root package name */
        public static final int f26622f;

        /* renamed from: a, reason: collision with root package name */
        public final int f26623a;

        /* compiled from: AnimatedContent.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/y$a$a;", "", "<init>", "()V", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* renamed from: androidx.compose.animation.y$a$a, reason: collision with other inner class name */
        public static final class C1553a {
            public /* synthetic */ C1553a(C42822w c42822w) {
                this();
            }

            public C1553a() {
            }
        }

        static {
            new C1553a(null);
            f26618b = 1;
            f26619c = 2;
            f26620d = 3;
            f26621e = 4;
            f26622f = 5;
        }

        public static final boolean a(int i12, int i13) {
            return i12 == i13;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof a) {
                return this.f26623a == ((a) obj).f26623a;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.f26623a);
        }

        @Y61.k
        public final String toString() {
            int i12 = this.f26623a;
            return a(i12, 0) ? "Left" : a(i12, f26618b) ? "Right" : a(i12, f26619c) ? "Up" : a(i12, f26620d) ? "Down" : a(i12, f26621e) ? "Start" : a(i12, f26622f) ? "End" : "Invalid";
        }
    }

    @Y61.k
    C20396w0 c(@Y61.k C20396w0 c20396w0, @Y61.l o2 o2Var);
}
