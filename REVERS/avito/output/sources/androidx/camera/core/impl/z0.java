package androidx.camera.core.impl;

import android.util.Size;
import androidx.camera.core.impl.S;
import java.util.ArrayList;

/* compiled from: ResolutionValidatedEncoderProfilesProvider.java */
@j.X
/* loaded from: classes.dex */
public class z0 implements Q {

    /* renamed from: a, reason: collision with root package name */
    public final Q f24309a;

    /* renamed from: b, reason: collision with root package name */
    public final T f24310b;

    public z0(@j.N Q q12, @j.N w0 w0Var) {
        this.f24309a = q12;
        this.f24310b = new T(w0Var.c(F.a.class));
    }

    @Override // androidx.camera.core.impl.Q
    @j.P
    public final S a(int i12) {
        Q q12 = this.f24309a;
        S sE2 = null;
        if (!q12.b(i12)) {
            return null;
        }
        S sA2 = q12.a(i12);
        T t12 = this.f24310b;
        if (t12.f24046a.isEmpty()) {
            return sA2;
        }
        if (sA2 != null) {
            if (t12.f24046a.isEmpty()) {
                sE2 = sA2;
            } else {
                ArrayList arrayList = new ArrayList();
                for (S.c cVar : sA2.d()) {
                    if (t12.f24047b.contains(new Size(cVar.k(), cVar.h()))) {
                        arrayList.add(cVar);
                    }
                }
                if (!arrayList.isEmpty()) {
                    sE2 = S.b.e(sA2.c(), sA2.a(), arrayList, sA2.b());
                }
            }
        }
        return sE2;
    }

    @Override // androidx.camera.core.impl.Q
    public final boolean b(int i12) {
        Q q12 = this.f24309a;
        if (!q12.b(i12)) {
            return false;
        }
        T t12 = this.f24310b;
        if (t12.f24046a.isEmpty()) {
            return true;
        }
        S sA2 = q12.a(i12);
        if (sA2 == null) {
            return false;
        }
        if (t12.f24046a.isEmpty()) {
            return !sA2.d().isEmpty();
        }
        for (S.c cVar : sA2.d()) {
            if (t12.f24047b.contains(new Size(cVar.k(), cVar.h()))) {
                return true;
            }
        }
        return false;
    }
}
