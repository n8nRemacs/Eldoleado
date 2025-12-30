package androidx.camera.core.streamsharing;

import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.InterfaceC20088d0;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.s0;
import j.N;
import j.X;
import java.util.List;

/* compiled from: StreamSharingConfig.java */
@X
/* loaded from: classes.dex */
public class f implements L0<d>, InterfaceC20088d0, androidx.camera.core.internal.j {

    /* renamed from: G, reason: collision with root package name */
    public static final Config.a<List<UseCaseConfigFactory.CaptureType>> f24548G = Config.a.a(List.class, "camerax.core.streamSharing.captureTypes");

    /* renamed from: F, reason: collision with root package name */
    public final s0 f24549F;

    public f(@N s0 s0Var) {
        this.f24549F = s0Var;
    }

    @Override // androidx.camera.core.impl.x0
    @N
    public final Config getConfig() {
        return this.f24549F;
    }
}
