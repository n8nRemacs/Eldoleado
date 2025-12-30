package androidx.camera.core.streamsharing;

import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.camera.core.impl.J0;
import androidx.camera.core.impl.r;
import j.N;
import j.P;
import j.X;

/* compiled from: VirtualCameraCaptureResult.java */
@X
/* loaded from: classes.dex */
public class i implements r {

    /* renamed from: a, reason: collision with root package name */
    @P
    public final r f24558a;

    /* renamed from: b, reason: collision with root package name */
    @N
    public final J0 f24559b;

    /* renamed from: c, reason: collision with root package name */
    public final long f24560c;

    public i(@P r rVar, @N J0 j02, long j12) {
        this.f24558a = rVar;
        this.f24559b = j02;
        this.f24560c = j12;
    }

    @Override // androidx.camera.core.impl.r
    public final long a() {
        r rVar = this.f24558a;
        if (rVar != null) {
            return rVar.a();
        }
        long j12 = this.f24560c;
        if (j12 != -1) {
            return j12;
        }
        throw new IllegalStateException("No timestamp is available.");
    }

    @Override // androidx.camera.core.impl.r
    @N
    public final J0 b() {
        return this.f24559b;
    }

    @Override // androidx.camera.core.impl.r
    @N
    public final CameraCaptureMetaData.FlashState d() {
        r rVar = this.f24558a;
        return rVar != null ? rVar.d() : CameraCaptureMetaData.FlashState.f23903b;
    }

    @Override // androidx.camera.core.impl.r
    @N
    public final CameraCaptureMetaData.AfState e() {
        r rVar = this.f24558a;
        return rVar != null ? rVar.e() : CameraCaptureMetaData.AfState.f23889b;
    }

    @Override // androidx.camera.core.impl.r
    @N
    public final CameraCaptureMetaData.AwbState f() {
        r rVar = this.f24558a;
        return rVar != null ? rVar.f() : CameraCaptureMetaData.AwbState.f23897b;
    }

    @Override // androidx.camera.core.impl.r
    @N
    public final CameraCaptureMetaData.AeState g() {
        r rVar = this.f24558a;
        return rVar != null ? rVar.g() : CameraCaptureMetaData.AeState.f23877b;
    }
}
