package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.DynamicRangeProfiles;
import androidx.camera.camera2.internal.compat.params.b;
import androidx.camera.core.F;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: DynamicRangesCompatApi33Impl.java */
@X
/* loaded from: classes.dex */
class c implements b.a {

    /* renamed from: a, reason: collision with root package name */
    public final DynamicRangeProfiles f23183a;

    public c(@N Object obj) {
        this.f23183a = (DynamicRangeProfiles) obj;
    }

    @N
    public static Set<F> d(@N Set<Long> set) {
        if (set.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(set.size());
        Iterator<Long> it = set.iterator();
        while (it.hasNext()) {
            long jLongValue = it.next().longValue();
            F fB2 = a.b(jLongValue);
            z.f(fB2, "Dynamic range profile cannot be converted to a DynamicRange object: " + jLongValue);
            hashSet.add(fB2);
        }
        return Collections.unmodifiableSet(hashSet);
    }

    @Override // androidx.camera.camera2.internal.compat.params.b.a
    @N
    public final Set<F> a() {
        return d(this.f23183a.getSupportedProfiles());
    }

    @Override // androidx.camera.camera2.internal.compat.params.b.a
    @N
    public final Set<F> b(@N F f12) {
        Long lA2 = a.a(f12, this.f23183a);
        z.a("DynamicRange is not supported: " + f12, lA2 != null);
        return d(this.f23183a.getProfileCaptureRequestConstraints(lA2.longValue()));
    }

    @Override // androidx.camera.camera2.internal.compat.params.b.a
    @P
    public final DynamicRangeProfiles c() {
        return this.f23183a;
    }
}
