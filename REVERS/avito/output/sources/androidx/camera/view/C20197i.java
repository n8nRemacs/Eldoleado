package androidx.camera.view;

import com.avito.android.photo_picker.camera_mvi.CameraFragment;
import j.P;
import j.X;

/* compiled from: LifecycleCameraController.java */
@X
/* renamed from: androidx.camera.view.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20197i extends AbstractC20193e {

    /* renamed from: w, reason: collision with root package name */
    @P
    public CameraFragment f25439w;

    /* JADX WARN: Removed duplicated region for block: B:42:0x009e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x009f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // androidx.camera.view.AbstractC20193e
    @j.P
    @j.a0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final androidx.camera.core.InterfaceC20127n g() {
        /*
            r7 = this;
            r0 = 1
            r1 = 0
            com.avito.android.photo_picker.camera_mvi.CameraFragment r2 = r7.f25439w
            r3 = 0
            if (r2 != 0) goto L8
            return r3
        L8:
            androidx.camera.view.w r2 = r7.f25422h
            if (r2 != 0) goto Ld
            return r3
        Ld:
            if (r2 == 0) goto L11
            r2 = r0
            goto L12
        L11:
            r2 = r1
        L12:
            r4 = 3
            java.lang.String r5 = "CameraController"
            if (r2 != 0) goto L1d
            androidx.camera.core.C20140q0.d(r4, r5)
        L1a:
            r0 = r3
            goto L9c
        L1d:
            androidx.camera.core.v0$c r2 = r7.f25424j
            if (r2 == 0) goto L97
            androidx.camera.core.P0 r2 = r7.f25423i
            if (r2 == 0) goto L97
            androidx.camera.core.O0$a r2 = new androidx.camera.core.O0$a
            r2.<init>()
            androidx.camera.core.v0 r4 = r7.f25417c
            r2.a(r4)
            androidx.camera.core.impl.utils.t.a()
            int r4 = r7.f25416b
            r4 = r4 & r0
            if (r4 == 0) goto L39
            r4 = r0
            goto L3a
        L39:
            r4 = r1
        L3a:
            androidx.camera.core.e0 r5 = r7.f25418d
            if (r4 == 0) goto L42
            r2.a(r5)
            goto L4b
        L42:
            androidx.camera.view.w r4 = r7.f25422h
            androidx.camera.core.N0[] r6 = new androidx.camera.core.N0[r0]
            r6[r1] = r5
            r4.b(r6)
        L4b:
            androidx.camera.core.impl.utils.t.a()
            int r4 = r7.f25416b
            r4 = r4 & 2
            if (r4 == 0) goto L56
            r4 = r0
            goto L57
        L56:
            r4 = r1
        L57:
            androidx.camera.core.T r5 = r7.f25419e
            if (r4 == 0) goto L5f
            r2.a(r5)
            goto L68
        L5f:
            androidx.camera.view.w r4 = r7.f25422h
            androidx.camera.core.N0[] r6 = new androidx.camera.core.N0[r0]
            r6[r1] = r5
            r4.b(r6)
        L68:
            androidx.camera.core.impl.utils.t.a()
            androidx.camera.video.b0<androidx.camera.video.O> r4 = r7.f25420f
            androidx.camera.view.w r5 = r7.f25422h
            androidx.camera.core.N0[] r0 = new androidx.camera.core.N0[r0]
            r0[r1] = r4
            r5.b(r0)
            androidx.camera.core.P0 r0 = r7.f25423i
            r2.f23639a = r0
            java.util.HashSet r0 = r7.f25435u
            java.util.Iterator r0 = r0.iterator()
        L80:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L92
            java.lang.Object r1 = r0.next()
            androidx.camera.core.p r1 = (androidx.camera.core.AbstractC20131p) r1
            java.util.ArrayList r4 = r2.f23641c
            r4.add(r1)
            goto L80
        L92:
            androidx.camera.core.O0 r0 = r2.b()
            goto L9c
        L97:
            androidx.camera.core.C20140q0.d(r4, r5)
            goto L1a
        L9c:
            if (r0 != 0) goto L9f
            return r3
        L9f:
            androidx.camera.view.w r1 = r7.f25422h     // Catch: java.lang.IllegalArgumentException -> Laa
            com.avito.android.photo_picker.camera_mvi.CameraFragment r2 = r7.f25439w     // Catch: java.lang.IllegalArgumentException -> Laa
            androidx.camera.core.v r3 = r7.f25415a     // Catch: java.lang.IllegalArgumentException -> Laa
            androidx.camera.core.n r0 = r1.a(r2, r3, r0)     // Catch: java.lang.IllegalArgumentException -> Laa
            return r0
        Laa:
            r0 = move-exception
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "The selected camera does not support the enabled use cases. Please disable use case and/or select a different camera. e.g. #setVideoCaptureEnabled(false)"
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.view.C20197i.g():androidx.camera.core.n");
    }
}
