package androidx.camera.video.internal;

import androidx.camera.core.F;
import androidx.camera.core.impl.Q;
import androidx.camera.core.impl.S;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* compiled from: DynamicRangeMatchedEncoderProfilesProvider.java */
@X
/* loaded from: classes.dex */
public class d implements Q {

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.video.internal.workaround.c f25104a;

    /* renamed from: b, reason: collision with root package name */
    public final F f25105b;

    /* renamed from: c, reason: collision with root package name */
    public final HashMap f25106c = new HashMap();

    public d(@N androidx.camera.video.internal.workaround.c cVar, @N F f12) {
        this.f25104a = cVar;
        this.f25105b = f12;
    }

    @Override // androidx.camera.core.impl.Q
    @P
    public final S a(int i12) {
        return c(i12);
    }

    @Override // androidx.camera.core.impl.Q
    public final boolean b(int i12) {
        return this.f25104a.b(i12) && c(i12) != null;
    }

    @P
    public final S c(int i12) {
        Set set;
        HashMap map = this.f25106c;
        if (map.containsKey(Integer.valueOf(i12))) {
            return (S) map.get(Integer.valueOf(i12));
        }
        androidx.camera.video.internal.workaround.c cVar = this.f25104a;
        S.b bVarE = null;
        if (cVar.b(i12)) {
            S sA2 = cVar.a(i12);
            if (sA2 != null) {
                ArrayList arrayList = new ArrayList();
                for (S.c cVar2 : sA2.d()) {
                    HashMap map2 = androidx.camera.video.internal.utils.a.f25266a;
                    F f12 = this.f25105b;
                    Set set2 = (Set) map2.get(Integer.valueOf(f12.f23569b));
                    if (set2 != null && set2.contains(Integer.valueOf(cVar2.b())) && (set = (Set) androidx.camera.video.internal.utils.a.f25267b.get(Integer.valueOf(f12.f23568a))) != null && set.contains(Integer.valueOf(cVar2.g()))) {
                        arrayList.add(cVar2);
                    }
                }
                if (!arrayList.isEmpty()) {
                    bVarE = S.b.e(sA2.c(), sA2.a(), arrayList, sA2.b());
                }
            }
            map.put(Integer.valueOf(i12), bVarE);
        }
        return bVarE;
    }
}
