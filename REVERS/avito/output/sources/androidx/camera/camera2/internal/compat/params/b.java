package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.DynamicRangeProfiles;
import android.os.Build;
import androidx.appcompat.app.x;
import androidx.camera.camera2.internal.compat.n;
import androidx.camera.core.F;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;
import java.util.Set;

/* compiled from: DynamicRangesCompat.java */
@X
/* loaded from: classes.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    public final a f23182a;

    /* compiled from: DynamicRangesCompat.java */
    public interface a {
        @N
        Set<F> a();

        @N
        Set<F> b(@N F f12);

        @P
        DynamicRangeProfiles c();
    }

    public b(@N a aVar) {
        this.f23182a = aVar;
    }

    @N
    public static b a(@N n nVar) {
        DynamicRangeProfiles dynamicRangeProfilesF;
        int i12 = Build.VERSION.SDK_INT;
        b bVar = null;
        if (i12 >= 33 && (dynamicRangeProfilesF = x.f(nVar.a(CameraCharacteristics.REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES))) != null) {
            z.g("DynamicRangeProfiles can only be converted to DynamicRangesCompat on API 33 or higher.", i12 >= 33);
            bVar = new b(new c(dynamicRangeProfilesF));
        }
        return bVar == null ? d.f23184a : bVar;
    }

    @N
    public final Set<F> b(@N F f12) {
        return this.f23182a.b(f12);
    }

    @N
    public final Set<F> c() {
        return this.f23182a.a();
    }

    @P
    @X
    public final DynamicRangeProfiles d() {
        z.g("DynamicRangesCompat can only be converted to DynamicRangeProfiles on API 33 or higher.", Build.VERSION.SDK_INT >= 33);
        return this.f23182a.c();
    }
}
