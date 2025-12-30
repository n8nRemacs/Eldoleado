package androidx.camera.video.internal.compat.quirk;

import android.os.Build;
import androidx.camera.core.impl.B;
import androidx.camera.video.A;
import j.N;
import j.X;

/* compiled from: ExcludeStretchedVideoQualityQuirk.java */
@X
/* loaded from: classes.dex */
public class h implements t {
    @Override // androidx.camera.video.internal.compat.quirk.t
    public final boolean b(@N B b12, @N A a12) {
        String str = Build.BRAND;
        return "Samsung".equalsIgnoreCase(str) && "SM-J400G".equalsIgnoreCase(Build.MODEL) ? a12 == A.f24682c || a12 == A.f24683d : (("Samsung".equalsIgnoreCase(str) && "SM-G610M".equalsIgnoreCase(Build.MODEL)) || ("Samsung".equalsIgnoreCase(str) && "SM-J710MN".equalsIgnoreCase(Build.MODEL))) && a12 == A.f24682c;
    }
}
