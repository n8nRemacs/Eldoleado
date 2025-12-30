package androidx.camera.video.internal;

import androidx.camera.core.impl.Q;
import androidx.camera.core.impl.S;
import androidx.camera.core.processing.j;
import j.N;
import j.P;
import j.X;
import java.util.HashMap;

/* compiled from: BackupHdrProfileEncoderProfilesProvider.java */
@X
/* loaded from: classes.dex */
public class b implements Q {

    /* renamed from: d, reason: collision with root package name */
    public static final j f25051d = new j(2);

    /* renamed from: a, reason: collision with root package name */
    public final Q f25052a;

    /* renamed from: b, reason: collision with root package name */
    public final j f25053b;

    /* renamed from: c, reason: collision with root package name */
    public final HashMap f25054c;

    public b(@N Q q12) {
        j jVar = f25051d;
        this.f25054c = new HashMap();
        this.f25052a = q12;
        this.f25053b = jVar;
    }

    @Override // androidx.camera.core.impl.Q
    @P
    public final S a(int i12) {
        return c(i12);
    }

    @Override // androidx.camera.core.impl.Q
    public final boolean b(int i12) {
        return this.f25052a.b(i12) && c(i12) != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0029  */
    @j.P
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final androidx.camera.core.impl.S c(int r22) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            java.util.HashMap r2 = r0.f25054c
            java.lang.Integer r3 = java.lang.Integer.valueOf(r22)
            boolean r3 = r2.containsKey(r3)
            if (r3 == 0) goto L1b
            java.lang.Integer r1 = java.lang.Integer.valueOf(r22)
            java.lang.Object r1 = r2.get(r1)
            androidx.camera.core.impl.S r1 = (androidx.camera.core.impl.S) r1
            return r1
        L1b:
            androidx.camera.core.impl.Q r3 = r0.f25052a
            boolean r4 = r3.b(r1)
            if (r4 == 0) goto Lfa
            androidx.camera.core.impl.S r3 = r3.a(r1)
            if (r3 != 0) goto L2c
        L29:
            r5 = 0
            goto Lf2
        L2c:
            java.util.ArrayList r4 = new java.util.ArrayList
            java.util.List r6 = r3.d()
            r4.<init>(r6)
            java.util.List r6 = r3.d()
            java.util.Iterator r6 = r6.iterator()
        L3d:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L50
            java.lang.Object r7 = r6.next()
            androidx.camera.core.impl.S$c r7 = (androidx.camera.core.impl.S.c) r7
            int r8 = r7.g()
            if (r8 != 0) goto L3d
            goto L51
        L50:
            r7 = 0
        L51:
            if (r7 != 0) goto L56
            r5 = 0
            goto Lcd
        L56:
            int r6 = r7.e()
            java.lang.String r8 = r7.i()
            int r9 = r7.j()
            int r10 = r7.g()
            r15 = 1
            if (r15 == r10) goto L6d
            r6 = 5
            java.lang.String r8 = "video/hevc"
            r9 = 2
        L6d:
            r11 = r6
            r12 = r8
            r17 = r9
            int r6 = r7.c()
            int r8 = r7.b()
            r9 = 10
            if (r9 != r8) goto L7f
            r13 = r6
            goto Lb2
        L7f:
            android.util.Rational r10 = new android.util.Rational
            r10.<init>(r9, r8)
            double r13 = (double) r6
            double r18 = r10.doubleValue()
            double r13 = r13 * r18
            int r10 = (int) r13
            java.lang.String r13 = "BackupHdrProfileEncoderProfilesProvider"
            r14 = 3
            boolean r16 = androidx.camera.core.C20140q0.d(r14, r13)
            if (r16 == 0) goto Lb1
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r9)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            java.lang.Integer r15 = java.lang.Integer.valueOf(r10)
            java.lang.Object[] r5 = new java.lang.Object[]{r6, r5, r8, r15}
            java.lang.String r6 = "Base Bitrate(%dbps) * Bit Depth Ratio (%d / %d) = %d"
            java.lang.String.format(r6, r5)
            androidx.camera.core.C20140q0.d(r14, r13)
        Lb1:
            r13 = r10
        Lb2:
            int r14 = r7.f()
            int r15 = r7.k()
            int r5 = r7.h()
            int r19 = r7.d()
            r6 = 1
            r16 = r5
            r18 = r9
            r20 = r6
            androidx.camera.core.impl.S$c r5 = androidx.camera.core.impl.S.c.a(r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
        Lcd:
            androidx.camera.core.processing.j r6 = r0.f25053b
            java.lang.Object r5 = r6.apply(r5)
            androidx.camera.core.impl.S$c r5 = (androidx.camera.core.impl.S.c) r5
            if (r5 == 0) goto Lda
            r4.add(r5)
        Lda:
            boolean r5 = r4.isEmpty()
            if (r5 == 0) goto Le2
            goto L29
        Le2:
            int r5 = r3.c()
            int r6 = r3.a()
            java.util.List r3 = r3.b()
            androidx.camera.core.impl.S$b r5 = androidx.camera.core.impl.S.b.e(r5, r6, r4, r3)
        Lf2:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r22)
            r2.put(r1, r5)
            goto Lfb
        Lfa:
            r5 = 0
        Lfb:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.internal.b.c(int):androidx.camera.core.impl.S");
    }
}
