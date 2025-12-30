package androidx.camera.camera2.internal;

import android.content.Context;
import androidx.camera.core.impl.UseCaseConfigFactory;

/* compiled from: Camera2UseCaseConfigFactory.java */
@j.X
/* loaded from: classes.dex */
public final class P implements UseCaseConfigFactory {

    /* renamed from: b, reason: collision with root package name */
    public final C20013i0 f23047b;

    public P(@j.N Context context) {
        this.f23047b = C20013i0.b(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0018  */
    @Override // androidx.camera.core.impl.UseCaseConfigFactory
    @j.N
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final androidx.camera.core.impl.Config a(@j.N androidx.camera.core.impl.UseCaseConfigFactory.CaptureType r9, int r10) {
        /*
            r8 = this;
            androidx.camera.core.impl.n0 r0 = androidx.camera.core.impl.n0.R()
            androidx.camera.core.impl.SessionConfig$b r1 = new androidx.camera.core.impl.SessionConfig$b
            r1.<init>()
            int r2 = r9.ordinal()
            r3 = 5
            r4 = 2
            r5 = 4
            r6 = 3
            r7 = 1
            if (r2 == 0) goto L1c
            if (r2 == r6) goto L1a
            if (r2 == r5) goto L1a
        L18:
            r2 = r7
            goto L1f
        L1a:
            r2 = r6
            goto L1f
        L1c:
            if (r10 != r4) goto L18
            r2 = r3
        L1f:
            r1.s(r2)
            androidx.camera.core.impl.Config$a<androidx.camera.core.impl.SessionConfig> r2 = androidx.camera.core.impl.L0.f23997q
            androidx.camera.core.impl.SessionConfig r1 = r1.k()
            r0.K(r2, r1)
            androidx.camera.core.impl.Config$a<androidx.camera.core.impl.SessionConfig$d> r1 = androidx.camera.core.impl.L0.f23999s
            androidx.camera.camera2.internal.O r2 = androidx.camera.camera2.internal.O.f23045a
            r0.K(r1, r2)
            androidx.camera.core.impl.H$a r1 = new androidx.camera.core.impl.H$a
            r1.<init>()
            int r2 = r9.ordinal()
            if (r2 == 0) goto L43
            if (r2 == r6) goto L48
            if (r2 == r5) goto L48
            r6 = r7
            goto L48
        L43:
            if (r10 != r4) goto L46
            goto L47
        L46:
            r3 = r4
        L47:
            r6 = r3
        L48:
            r1.f23978c = r6
            androidx.camera.core.impl.Config$a<androidx.camera.core.impl.H> r10 = androidx.camera.core.impl.L0.f23998r
            androidx.camera.core.impl.H r1 = r1.e()
            r0.K(r10, r1)
            androidx.camera.core.impl.Config$a<androidx.camera.core.impl.H$b> r10 = androidx.camera.core.impl.L0.f24000t
            androidx.camera.core.impl.UseCaseConfigFactory$CaptureType r1 = androidx.camera.core.impl.UseCaseConfigFactory.CaptureType.f24054b
            if (r9 != r1) goto L5c
            androidx.camera.camera2.internal.w0 r1 = androidx.camera.camera2.internal.C20040w0.f23519c
            goto L5e
        L5c:
            androidx.camera.camera2.internal.B r1 = androidx.camera.camera2.internal.B.f22908a
        L5e:
            r0.K(r10, r1)
            androidx.camera.core.impl.UseCaseConfigFactory$CaptureType r10 = androidx.camera.core.impl.UseCaseConfigFactory.CaptureType.f24055c
            androidx.camera.camera2.internal.i0 r1 = r8.f23047b
            if (r9 != r10) goto L70
            android.util.Size r10 = r1.e()
            androidx.camera.core.impl.Config$a<android.util.Size> r2 = androidx.camera.core.impl.InterfaceC20088d0.f24099m
            r0.K(r2, r10)
        L70:
            android.view.Display r10 = r1.c(r7)
            int r10 = r10.getRotation()
            androidx.camera.core.impl.Config$a<java.lang.Integer> r1 = androidx.camera.core.impl.InterfaceC20088d0.f24094h
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r0.K(r1, r10)
            androidx.camera.core.impl.UseCaseConfigFactory$CaptureType r10 = androidx.camera.core.impl.UseCaseConfigFactory.CaptureType.f24057e
            if (r9 == r10) goto L89
            androidx.camera.core.impl.UseCaseConfigFactory$CaptureType r10 = androidx.camera.core.impl.UseCaseConfigFactory.CaptureType.f24058f
            if (r9 != r10) goto L90
        L89:
            androidx.camera.core.impl.Config$a<java.lang.Boolean> r9 = androidx.camera.core.impl.L0.f24004x
            java.lang.Boolean r10 = java.lang.Boolean.TRUE
            r0.K(r9, r10)
        L90:
            androidx.camera.core.impl.s0 r9 = androidx.camera.core.impl.s0.Q(r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.internal.P.a(androidx.camera.core.impl.UseCaseConfigFactory$CaptureType, int):androidx.camera.core.impl.Config");
    }
}
