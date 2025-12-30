package androidx.camera.core.internal;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import androidx.camera.video.m0;
import androidx.core.util.InterfaceC22791e;
import java.io.File;
import ru.cyberity.cbr.core.domain.camera.CameraX;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class d implements InterfaceC22791e {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24347b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24348c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f24349d;

    public /* synthetic */ d(int i12, Object obj, Object obj2) {
        this.f24347b = i12;
        this.f24348c = obj;
        this.f24349d = obj2;
    }

    @Override // androidx.core.util.InterfaceC22791e
    public final void accept(Object obj) {
        switch (this.f24347b) {
            case 0:
                ((Surface) this.f24348c).release();
                ((SurfaceTexture) this.f24349d).release();
                break;
            default:
                CameraX.a((CameraX) this.f24348c, (File) this.f24349d, (m0) obj);
                break;
        }
    }
}
