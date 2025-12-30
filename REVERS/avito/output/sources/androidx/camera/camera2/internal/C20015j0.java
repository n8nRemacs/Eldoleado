package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.text.TextUtils;
import androidx.camera.core.C20140q0;
import j.InterfaceC42164u;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/* compiled from: DynamicRangeResolver.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.j0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20015j0 {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.n f23352a;

    /* renamed from: b, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.params.b f23353b;

    /* renamed from: c, reason: collision with root package name */
    public final boolean f23354c;

    /* compiled from: DynamicRangeResolver.java */
    @j.X
    /* renamed from: androidx.camera.camera2.internal.j0$a */
    public static final class a {
        @InterfaceC42164u
        @j.P
        public static androidx.camera.core.F a(@j.N androidx.camera.camera2.internal.compat.n nVar) {
            Long l12 = (Long) nVar.a(CameraCharacteristics.REQUEST_RECOMMENDED_TEN_BIT_DYNAMIC_RANGE_PROFILE);
            if (l12 != null) {
                return androidx.camera.camera2.internal.compat.params.a.b(l12.longValue());
            }
            return null;
        }
    }

    public C20015j0(@j.N androidx.camera.camera2.internal.compat.n nVar) {
        this.f23352a = nVar;
        this.f23353b = androidx.camera.camera2.internal.compat.params.b.a(nVar);
        int[] iArr = (int[]) nVar.a(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        boolean z12 = false;
        if (iArr != null) {
            int length = iArr.length;
            int i12 = 0;
            while (true) {
                if (i12 >= length) {
                    break;
                }
                if (iArr[i12] == 18) {
                    z12 = true;
                    break;
                }
                i12++;
            }
        }
        this.f23354c = z12;
    }

    public static boolean a(@j.N androidx.camera.core.F f12, @j.N androidx.camera.core.F f13) {
        androidx.core.util.z.g("Fully specified range is not actually fully specified.", f13.b());
        int i12 = f12.f23568a;
        int i13 = f13.f23568a;
        if (i12 == 2 && i13 == 1) {
            return false;
        }
        if (i12 != 2 && i12 != 0 && i12 != i13) {
            return false;
        }
        int i14 = f12.f23569b;
        return i14 == 0 || i14 == f13.f23569b;
    }

    public static boolean b(@j.N androidx.camera.core.F f12, @j.N androidx.camera.core.F f13, @j.N HashSet hashSet) {
        if (hashSet.contains(f13)) {
            return a(f12, f13);
        }
        f12.toString();
        f13.toString();
        C20140q0.d(3, "DynamicRangeResolver");
        return false;
    }

    @j.P
    public static androidx.camera.core.F c(@j.N androidx.camera.core.F f12, @j.N LinkedHashSet linkedHashSet, @j.N HashSet hashSet) {
        if (f12.f23568a == 1) {
            return null;
        }
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            androidx.camera.core.F f13 = (androidx.camera.core.F) it.next();
            androidx.core.util.z.f(f13, "Fully specified DynamicRange cannot be null.");
            androidx.core.util.z.g("Fully specified DynamicRange must have fully defined encoding.", f13.b());
            if (f13.f23568a != 1 && b(f12, f13, hashSet)) {
                return f13;
            }
        }
        return null;
    }

    public static void d(@j.N HashSet hashSet, @j.N androidx.camera.core.F f12, @j.N androidx.camera.camera2.internal.compat.params.b bVar) {
        androidx.core.util.z.g("Cannot update already-empty constraints.", !hashSet.isEmpty());
        Set<androidx.camera.core.F> setB = bVar.b(f12);
        if (setB.isEmpty()) {
            return;
        }
        HashSet hashSet2 = new HashSet(hashSet);
        hashSet.retainAll(setB);
        if (hashSet.isEmpty()) {
            throw new IllegalArgumentException("Constraints of dynamic range cannot be combined with existing constraints.\nDynamic range:\n  " + f12 + "\nConstraints:\n  " + TextUtils.join("\n  ", setB) + "\nExisting constraints:\n  " + TextUtils.join("\n  ", hashSet2));
        }
    }
}
