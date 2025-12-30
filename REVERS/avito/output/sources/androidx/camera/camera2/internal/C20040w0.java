package androidx.camera.camera2.internal;

import android.hardware.camera2.CaptureRequest;
import androidx.camera.camera2.impl.b;
import androidx.camera.camera2.internal.compat.quirk.C20001l;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.H;

/* compiled from: ImageCaptureOptionUnpacker.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.w0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20040w0 extends B {

    /* renamed from: c, reason: collision with root package name */
    public static final C20040w0 f23519c = new C20040w0(new androidx.camera.camera2.internal.compat.workaround.j());

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final androidx.camera.camera2.internal.compat.workaround.j f23520b;

    public C20040w0(@j.N androidx.camera.camera2.internal.compat.workaround.j jVar) {
        this.f23520b = jVar;
    }

    @Override // androidx.camera.camera2.internal.B, androidx.camera.core.impl.H.b
    public final void a(@j.N androidx.camera.core.impl.Z z12, @j.N H.a aVar) {
        super.a(z12, aVar);
        if (z12 == null) {
            throw new IllegalArgumentException("config is not ImageCaptureConfig");
        }
        b.a aVar2 = new b.a();
        Config.a<Integer> aVar3 = androidx.camera.core.impl.Z.f24070G;
        if (z12.c(aVar3)) {
            int iIntValue = ((Integer) z12.f(aVar3)).intValue();
            this.f23520b.getClass();
            if (((androidx.camera.camera2.internal.compat.quirk.v) C20001l.f23216a.b(androidx.camera.camera2.internal.compat.quirk.v.class)) != null) {
                if (iIntValue == 0) {
                    aVar2.c(CaptureRequest.CONTROL_ENABLE_ZSL, Boolean.TRUE);
                } else if (iIntValue == 1) {
                    aVar2.c(CaptureRequest.CONTROL_ENABLE_ZSL, Boolean.FALSE);
                }
            }
        }
        aVar.c(aVar2.a());
    }
}
