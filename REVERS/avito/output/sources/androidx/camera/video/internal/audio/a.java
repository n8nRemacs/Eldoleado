package androidx.camera.video.internal.audio;

import VY0.c;
import android.annotation.SuppressLint;
import androidx.camera.video.internal.audio.n;
import androidx.core.util.z;
import com.huawei.hms.support.api.entity.core.JosStatusCodes;
import j.F;
import j.N;
import j.X;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* compiled from: AudioSettings.java */
@VY0.c
@X
/* loaded from: classes.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    public static final List<Integer> f24951a = Collections.unmodifiableList(Arrays.asList(48000, 44100, 22050, 11025, Integer.valueOf(JosStatusCodes.RTN_CODE_COMMON_ERROR), 4800));

    /* compiled from: AudioSettings.java */
    @c.a
    /* renamed from: androidx.camera.video.internal.audio.a$a, reason: collision with other inner class name */
    public static abstract class AbstractC1538a {
        @N
        public final a a() {
            n.b bVar = (n.b) this;
            String strQ = bVar.f25016a == null ? " audioSource" : "";
            if (bVar.f25017b == null) {
                strQ = strQ.concat(" sampleRate");
            }
            if (bVar.f25018c == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " channelCount");
            }
            if (bVar.f25019d == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " audioFormat");
            }
            if (!strQ.isEmpty()) {
                throw new IllegalStateException("Missing required properties:".concat(strQ));
            }
            n nVar = new n(bVar.f25016a.intValue(), bVar.f25017b.intValue(), bVar.f25018c.intValue(), bVar.f25019d.intValue(), null);
            String strQ2 = nVar.f25012b == -1 ? " audioSource" : "";
            if (nVar.f25013c <= 0) {
                strQ2 = strQ2.concat(" sampleRate");
            }
            if (nVar.f25014d <= 0) {
                strQ2 = androidx.appcompat.app.r.q(strQ2, " channelCount");
            }
            if (nVar.f25015e == -1) {
                strQ2 = androidx.appcompat.app.r.q(strQ2, " audioFormat");
            }
            if (strQ2.isEmpty()) {
                return nVar;
            }
            throw new IllegalArgumentException("Required settings missing or non-positive:".concat(strQ2));
        }

        @N
        public abstract AbstractC1538a b(int i12);

        @N
        public abstract AbstractC1538a c(int i12);

        @N
        public abstract AbstractC1538a d(@F int i12);

        @N
        public abstract AbstractC1538a e(@F int i12);
    }

    @N
    @SuppressLint({"Range"})
    public static AbstractC1538a a() {
        n.b bVar = new n.b();
        bVar.f25016a = -1;
        bVar.f25017b = -1;
        bVar.f25018c = -1;
        bVar.f25019d = -1;
        return bVar;
    }

    public abstract int b();

    public abstract int c();

    public final int d() {
        int iB2 = b();
        int iE2 = e();
        z.a("Invalid channel count: " + iE2, iE2 > 0);
        if (iB2 == 2) {
            return iE2 * 2;
        }
        if (iB2 == 3) {
            return iE2;
        }
        if (iB2 != 4) {
            if (iB2 == 21) {
                return iE2 * 3;
            }
            if (iB2 != 22) {
                throw new IllegalArgumentException(AK.c.g(iB2, "Invalid audio encoding: "));
            }
        }
        return iE2 * 4;
    }

    @F
    public abstract int e();

    @F
    public abstract int f();
}
