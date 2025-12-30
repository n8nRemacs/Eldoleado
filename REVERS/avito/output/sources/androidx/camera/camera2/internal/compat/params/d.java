package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.DynamicRangeProfiles;
import androidx.camera.camera2.internal.compat.params.b;
import androidx.camera.core.F;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;
import java.util.Collections;
import java.util.Set;

/* compiled from: DynamicRangesCompatBaseImpl.java */
@X
/* loaded from: classes.dex */
class d implements b.a {

    /* renamed from: a, reason: collision with root package name */
    public static final b f23184a = new b(new d());

    /* renamed from: b, reason: collision with root package name */
    public static final Set<F> f23185b = Collections.singleton(F.f23562d);

    @Override // androidx.camera.camera2.internal.compat.params.b.a
    @N
    public final Set<F> a() {
        return f23185b;
    }

    @Override // androidx.camera.camera2.internal.compat.params.b.a
    @N
    public final Set<F> b(@N F f12) {
        z.a("DynamicRange is not supported: " + f12, F.f23562d.equals(f12));
        return f23185b;
    }

    @Override // androidx.camera.camera2.internal.compat.params.b.a
    @P
    public final DynamicRangeProfiles c() {
        return null;
    }
}
