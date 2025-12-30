package androidx.camera.video.internal;

import androidx.camera.core.impl.S;
import androidx.core.util.z;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: VideoValidatedEncoderProfilesProxy.java */
@VY0.c
@X
/* loaded from: classes.dex */
public abstract class f implements S {
    @N
    public static f e(@N S s5) {
        int iC2 = s5.c();
        int iA2 = s5.a();
        List<S.a> listB = s5.b();
        List<S.c> listD = s5.d();
        z.a("Should contain at least one VideoProfile.", !listD.isEmpty());
        return new a(iC2, iA2, Collections.unmodifiableList(new ArrayList(listB)), Collections.unmodifiableList(new ArrayList(listD)), !listB.isEmpty() ? listB.get(0) : null, listD.get(0));
    }

    @P
    public abstract S.a f();

    @N
    public abstract S.c g();
}
