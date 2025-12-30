package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Size;
import androidx.camera.camera2.internal.compat.B;
import j.InterfaceC42164u;
import j.N;
import j.X;

/* compiled from: StreamConfigurationMapCompatBaseImpl.java */
@X
/* loaded from: classes.dex */
class D implements B.a {

    /* renamed from: a, reason: collision with root package name */
    public final StreamConfigurationMap f23131a;

    /* compiled from: StreamConfigurationMapCompatBaseImpl.java */
    @X
    public static class a {
        @InterfaceC42164u
        public static Size[] a(StreamConfigurationMap streamConfigurationMap, int i12) {
            return streamConfigurationMap.getHighResolutionOutputSizes(i12);
        }
    }

    public D(@N StreamConfigurationMap streamConfigurationMap) {
        this.f23131a = streamConfigurationMap;
    }
}
