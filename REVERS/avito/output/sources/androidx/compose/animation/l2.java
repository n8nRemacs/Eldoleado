package androidx.compose.animation;

import androidx.compose.animation.C20390u0;
import androidx.compose.animation.core.C20268c;
import androidx.compose.animation.core.C20310q;
import androidx.compose.animation.core.C20327w;
import androidx.compose.ui.graphics.T;
import kotlin.Metadata;

/* compiled from: SingleValueAnimation.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0002\n\u0000¨\u0006\u0000"}, d2 = {"animation_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class l2 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public static final androidx.compose.animation.core.R0<androidx.compose.ui.graphics.T> f26509a = C20310q.d(0.0f, 7, null);

    @Y61.k
    public static final C20268c<androidx.compose.ui.graphics.T, C20327w> a(long j12) {
        androidx.compose.ui.graphics.T tA2 = androidx.compose.ui.graphics.T.a(j12);
        T.a aVar = androidx.compose.ui.graphics.T.f39320b;
        return new C20268c<>(tA2, ((C20390u0.a) C20390u0.f26594a).invoke(androidx.compose.ui.graphics.T.g(j12)), null, null, 12, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0029  */
    @androidx.compose.runtime.InterfaceC22132o
    @Y61.k
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final androidx.compose.runtime.I3 b(long r8, @Y61.l androidx.compose.animation.core.V r10, @Y61.l androidx.compose.runtime.A r11, int r12, int r13) {
        /*
            r0 = r13 & 2
            if (r0 == 0) goto L6
            androidx.compose.animation.core.R0<androidx.compose.ui.graphics.T> r10 = androidx.compose.animation.l2.f26509a
        L6:
            r2 = r10
            r10 = r13 & 4
            if (r10 == 0) goto Lf
            java.lang.String r10 = "ColorAnimation"
        Ld:
            r4 = r10
            goto L12
        Lf:
            java.lang.String r10 = "AnimatedBorderColor"
            goto Ld
        L12:
            androidx.compose.ui.graphics.colorspace.c r10 = androidx.compose.ui.graphics.T.g(r8)
            boolean r10 = r11.B(r10)
            java.lang.Object r13 = r11.t()
            if (r10 != 0) goto L29
            androidx.compose.runtime.A$a r10 = androidx.compose.runtime.A.f37866a
            r10.getClass()
            androidx.compose.runtime.A$a$a r10 = androidx.compose.runtime.A.a.f37868b
            if (r13 != r10) goto L3b
        L29:
            Y41.l<androidx.compose.ui.graphics.colorspace.c, androidx.compose.animation.core.H1<androidx.compose.ui.graphics.T, androidx.compose.animation.core.w>> r10 = androidx.compose.animation.C20390u0.f26594a
            androidx.compose.ui.graphics.colorspace.c r13 = androidx.compose.ui.graphics.T.g(r8)
            androidx.compose.animation.u0$a r10 = (androidx.compose.animation.C20390u0.a) r10
            java.lang.Object r10 = r10.invoke(r13)
            r13 = r10
            androidx.compose.animation.core.H1 r13 = (androidx.compose.animation.core.H1) r13
            r11.H(r13)
        L3b:
            r1 = r13
            androidx.compose.animation.core.H1 r1 = (androidx.compose.animation.core.H1) r1
            androidx.compose.ui.graphics.T r0 = androidx.compose.ui.graphics.T.a(r8)
            int r8 = r12 << 3
            r8 = r8 & 896(0x380, float:1.256E-42)
            int r9 = r12 << 6
            r10 = 57344(0xe000, float:8.0356E-41)
            r9 = r9 & r10
            r6 = r8 | r9
            r7 = 8
            r3 = 0
            r5 = r11
            androidx.compose.runtime.I3 r8 = androidx.compose.animation.core.C20283h.c(r0, r1, r2, r3, r4, r5, r6, r7)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.l2.b(long, androidx.compose.animation.core.V, androidx.compose.runtime.A, int, int):androidx.compose.runtime.I3");
    }
}
