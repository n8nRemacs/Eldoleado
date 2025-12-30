package androidx.camera.core.impl.compat;

import android.media.EncoderProfiles;
import android.os.Build;
import androidx.camera.camera2.internal.G;
import androidx.camera.core.impl.S;
import j.N;
import j.X;

/* compiled from: EncoderProfilesProxyCompat.java */
@X
/* loaded from: classes.dex */
public final class a {
    @N
    @X
    public static S a(@N EncoderProfiles encoderProfiles) {
        int i12 = Build.VERSION.SDK_INT;
        if (i12 >= 33) {
            return c.a(encoderProfiles);
        }
        if (i12 >= 31) {
            return b.a(encoderProfiles);
        }
        throw new RuntimeException(G.e(i12, "Unable to call from(EncoderProfiles) on API ", ". Version 31 or higher required."));
    }
}
