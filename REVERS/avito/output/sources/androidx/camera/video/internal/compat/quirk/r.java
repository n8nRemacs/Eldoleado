package androidx.camera.video.internal.compat.quirk;

import android.os.Build;
import androidx.camera.core.impl.B;
import androidx.camera.video.A;
import j.N;
import j.X;

/* compiled from: VideoEncoderCrashQuirk.java */
@X
/* loaded from: classes.dex */
public class r implements t {
    @Override // androidx.camera.video.internal.compat.quirk.t
    public final boolean a() {
        return false;
    }

    @Override // androidx.camera.video.internal.compat.quirk.t
    public final boolean b(@N B b12, @N A a12) {
        return ("positivo".equalsIgnoreCase(Build.BRAND) && "twist 2 pro".equalsIgnoreCase(Build.MODEL)) && b12.b() == 0 && a12 == A.f24680a;
    }
}
