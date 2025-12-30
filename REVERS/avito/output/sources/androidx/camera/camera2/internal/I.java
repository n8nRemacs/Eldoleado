package androidx.camera.camera2.internal;

import androidx.camera.camera2.internal.D;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.concurrent.futures.b;
import java.util.Set;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class I implements b.c, androidx.camera.core.impl.utils.futures.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ D.f f22985b;

    public /* synthetic */ I(D.f fVar) {
        this.f22985b = fVar;
    }

    @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
    public com.google.common.util.concurrent.D0 apply(Object obj) {
        G g12 = new G(1);
        long j12 = D.f.f22965e;
        C20026p c20026p = this.f22985b.f22967a;
        Set<CameraCaptureMetaData.AfState> set = D.f22931h;
        D.e eVar = new D.e(j12, g12);
        c20026p.m(eVar);
        return eVar.f22961b;
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(b.a aVar) {
        this.f22985b.f22967a.f23396j.a(aVar, true);
        return "TorchOn";
    }
}
