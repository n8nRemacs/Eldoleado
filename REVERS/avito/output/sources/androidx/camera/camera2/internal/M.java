package androidx.camera.camera2.internal;

import android.media.CamcorderProfile;
import android.media.EncoderProfiles;
import androidx.camera.core.C20140q0;
import j.InterfaceC42164u;
import java.util.HashMap;

/* compiled from: Camera2EncoderProfilesProvider.java */
@j.X
/* loaded from: classes.dex */
public class M implements androidx.camera.core.impl.Q {

    /* renamed from: a, reason: collision with root package name */
    public final boolean f23014a;

    /* renamed from: b, reason: collision with root package name */
    public final String f23015b;

    /* renamed from: c, reason: collision with root package name */
    public final int f23016c;

    /* renamed from: d, reason: collision with root package name */
    public final HashMap f23017d = new HashMap();

    /* compiled from: Camera2EncoderProfilesProvider.java */
    @j.X
    public static class a {
        @InterfaceC42164u
        public static EncoderProfiles a(String str, int i12) {
            return CamcorderProfile.getAll(str, i12);
        }
    }

    public M(@j.N String str) throws NumberFormatException {
        boolean z12;
        int i12;
        this.f23015b = str;
        try {
            i12 = Integer.parseInt(str);
            z12 = true;
        } catch (NumberFormatException unused) {
            C20140q0.d(5, "Camera2EncoderProfilesProvider");
            z12 = false;
            i12 = -1;
        }
        this.f23014a = z12;
        this.f23016c = i12;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0060  */
    @Override // androidx.camera.core.impl.Q
    @j.P
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final androidx.camera.core.impl.S a(int r20) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.internal.M.a(int):androidx.camera.core.impl.S");
    }

    @Override // androidx.camera.core.impl.Q
    public final boolean b(int i12) {
        if (this.f23014a) {
            return CamcorderProfile.hasProfile(this.f23016c, i12);
        }
        return false;
    }
}
