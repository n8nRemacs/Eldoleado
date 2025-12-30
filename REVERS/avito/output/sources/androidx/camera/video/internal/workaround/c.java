package androidx.camera.video.internal.workaround;

import androidx.camera.core.impl.B;
import androidx.camera.core.impl.Q;
import androidx.camera.core.impl.S;
import androidx.camera.core.impl.w0;
import androidx.camera.core.impl.z0;
import androidx.camera.video.A;
import androidx.camera.video.internal.compat.quirk.t;
import j.N;
import j.P;
import j.X;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: QualityValidatedEncoderProfilesProvider.java */
@X
/* loaded from: classes.dex */
public class c implements Q {

    /* renamed from: d, reason: collision with root package name */
    public static final HashMap f25270d;

    /* renamed from: a, reason: collision with root package name */
    @N
    public final z0 f25271a;

    /* renamed from: b, reason: collision with root package name */
    @N
    public final B f25272b;

    /* renamed from: c, reason: collision with root package name */
    @N
    public final w0 f25273c;

    static {
        HashMap map = new HashMap();
        f25270d = map;
        map.put(1, A.f24685f);
        map.put(8, A.f24683d);
        map.put(6, A.f24682c);
        map.put(5, A.f24681b);
        map.put(4, A.f24680a);
        map.put(0, A.f24684e);
    }

    public c(@N z0 z0Var, @N B b12, @N w0 w0Var) {
        this.f25271a = z0Var;
        this.f25272b = b12;
        this.f25273c = w0Var;
    }

    @Override // androidx.camera.core.impl.Q
    @P
    public final S a(int i12) {
        if (b(i12)) {
            return this.f25271a.a(i12);
        }
        return null;
    }

    @Override // androidx.camera.core.impl.Q
    public final boolean b(int i12) {
        if (this.f25271a.b(i12)) {
            A a12 = (A) f25270d.get(Integer.valueOf(i12));
            if (a12 != null) {
                Iterator it = this.f25273c.c(t.class).iterator();
                while (it.hasNext()) {
                    t tVar = (t) it.next();
                    if (tVar == null || !tVar.b(this.f25272b, a12) || tVar.a()) {
                    }
                }
            }
            return true;
        }
        return false;
    }
}
