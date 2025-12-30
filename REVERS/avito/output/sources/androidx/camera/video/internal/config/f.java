package androidx.camera.video.internal.config;

import android.util.Range;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.S;
import androidx.camera.video.AbstractC20150a;
import androidx.camera.video.internal.audio.a;
import androidx.core.util.T;
import j.N;
import j.X;

/* compiled from: AudioSettingsAudioProfileResolver.java */
@X
/* loaded from: classes.dex */
public final class f implements T<androidx.camera.video.internal.audio.a> {

    /* renamed from: a, reason: collision with root package name */
    public final AbstractC20150a f25073a;

    /* renamed from: b, reason: collision with root package name */
    public final S.a f25074b;

    public f(@N AbstractC20150a abstractC20150a, @N S.a aVar) {
        this.f25073a = abstractC20150a;
        this.f25074b = aVar;
    }

    @Override // androidx.core.util.T
    @N
    public final androidx.camera.video.internal.audio.a get() {
        AbstractC20150a abstractC20150a = this.f25073a;
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
        int iC2 = abstractC20150a.c();
        Range<Integer> rangeD = abstractC20150a.d();
        S.a aVar = this.f25074b;
        int iC3 = aVar.c();
        if (iC2 == -1) {
            C20140q0.d(3, "AudioSrcAdPrflRslvr");
            iC2 = iC3;
        } else {
            C20140q0.d(3, "AudioSrcAdPrflRslvr");
        }
        int iC4 = b.c(rangeD, iC2, iF2, aVar.g());
        C20140q0.d(3, "AudioSrcAdPrflRslvr");
        a.AbstractC1538a abstractC1538aA = androidx.camera.video.internal.audio.a.a();
        abstractC1538aA.c(iE2);
        abstractC1538aA.b(iF2);
        abstractC1538aA.d(iC2);
        abstractC1538aA.e(iC4);
        return abstractC1538aA.a();
    }
}
