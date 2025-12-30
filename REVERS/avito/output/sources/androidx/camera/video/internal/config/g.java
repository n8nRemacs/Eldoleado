package androidx.camera.video.internal.config;

import android.util.Range;
import androidx.camera.core.C20140q0;
import androidx.camera.video.AbstractC20150a;
import androidx.camera.video.internal.audio.a;
import androidx.core.util.T;
import j.N;
import j.X;

/* compiled from: AudioSettingsDefaultResolver.java */
@X
/* loaded from: classes.dex */
public final class g implements T<androidx.camera.video.internal.audio.a> {

    /* renamed from: a, reason: collision with root package name */
    public final AbstractC20150a f25075a;

    public g(@N AbstractC20150a abstractC20150a) {
        this.f25075a = abstractC20150a;
    }

    @Override // androidx.core.util.T
    @N
    public final androidx.camera.video.internal.audio.a get() {
        int iC2;
        AbstractC20150a abstractC20150a = this.f25075a;
        int iE2 = abstractC20150a.e();
        if (iE2 == -1) {
            C20140q0.d(3, "AudioConfigUtil");
            iE2 = 5;
        } else {
            C20140q0.d(3, "AudioConfigUtil");
        }
        int iF2 = abstractC20150a.f();
        if (iF2 == -1) {
            C20140q0.d(3, "AudioConfigUtil");
            iF2 = 2;
        } else {
            C20140q0.d(3, "AudioConfigUtil");
        }
        int iC3 = abstractC20150a.c();
        if (iC3 == -1) {
            C20140q0.d(3, "DefAudioResolver");
            iC3 = 1;
        } else {
            C20140q0.d(3, "DefAudioResolver");
        }
        Range<Integer> rangeD = abstractC20150a.d();
        if (AbstractC20150a.f24858b.equals(rangeD)) {
            C20140q0.d(3, "DefAudioResolver");
            iC2 = 44100;
        } else {
            iC2 = b.c(rangeD, iC3, iF2, ((Integer) rangeD.getUpper()).intValue());
            C20140q0.d(3, "DefAudioResolver");
        }
        a.AbstractC1538a abstractC1538aA = androidx.camera.video.internal.audio.a.a();
        abstractC1538aA.c(iE2);
        abstractC1538aA.b(iF2);
        abstractC1538aA.d(iC3);
        abstractC1538aA.e(iC2);
        return abstractC1538aA.a();
    }
}
