package androidx.camera.core.internal.utils;

import androidx.camera.core.InterfaceC20060g0;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.camera.core.impl.r;
import j.N;
import j.X;

/* compiled from: ZslRingBuffer.java */
@X
/* loaded from: classes.dex */
public final class f extends a<InterfaceC20120j0> {
    public f() {
        throw null;
    }

    @Override // androidx.camera.core.internal.utils.a
    /* renamed from: d, reason: merged with bridge method [inline-methods] */
    public final void b(@N InterfaceC20120j0 interfaceC20120j0) throws Exception {
        InterfaceC20060g0 interfaceC20060g0R1 = interfaceC20120j0.r1();
        r rVar = interfaceC20060g0R1 instanceof androidx.camera.core.internal.c ? ((androidx.camera.core.internal.c) interfaceC20060g0R1).f24335a : null;
        if ((rVar.e() == CameraCaptureMetaData.AfState.f23894g || rVar.e() == CameraCaptureMetaData.AfState.f23892e) && rVar.g() == CameraCaptureMetaData.AeState.f23881f && rVar.f() == CameraCaptureMetaData.AwbState.f23900e) {
            super.b(interfaceC20120j0);
        } else {
            this.f24369d.getClass();
            interfaceC20120j0.close();
        }
    }
}
