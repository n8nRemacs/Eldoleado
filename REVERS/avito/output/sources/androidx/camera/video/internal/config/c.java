package androidx.camera.video.internal.config;

import android.util.Range;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.S;
import androidx.camera.video.AbstractC20150a;
import androidx.camera.video.internal.encoder.AbstractC20159a;
import androidx.core.util.T;
import j.N;
import j.X;

/* compiled from: AudioEncoderConfigAudioProfileResolver.java */
@X
/* loaded from: classes.dex */
public final class c implements T<AbstractC20159a> {

    /* renamed from: a, reason: collision with root package name */
    public final String f25064a;

    /* renamed from: b, reason: collision with root package name */
    public final int f25065b;

    /* renamed from: c, reason: collision with root package name */
    public final AbstractC20150a f25066c;

    /* renamed from: d, reason: collision with root package name */
    public final androidx.camera.video.internal.audio.a f25067d;

    /* renamed from: e, reason: collision with root package name */
    public final S.a f25068e;

    public c(@N String str, int i12, @N AbstractC20150a abstractC20150a, @N androidx.camera.video.internal.audio.a aVar, @N S.a aVar2) {
        this.f25064a = str;
        this.f25065b = i12;
        this.f25066c = abstractC20150a;
        this.f25067d = aVar;
        this.f25068e = aVar2;
    }

    @Override // androidx.core.util.T
    @N
    public final AbstractC20159a get() {
        C20140q0.d(3, "AudioEncAdPrflRslvr");
        Range<Integer> rangeB = this.f25066c.b();
        S.a aVar = this.f25068e;
        int iB2 = aVar.b();
        androidx.camera.video.internal.audio.a aVar2 = this.f25067d;
        int iB3 = b.b(iB2, aVar2.e(), aVar.c(), aVar2.f(), aVar.g(), rangeB);
        AbstractC20159a.AbstractC1539a abstractC1539aD = AbstractC20159a.d();
        abstractC1539aD.e(this.f25064a);
        abstractC1539aD.f(this.f25065b);
        abstractC1539aD.d();
        abstractC1539aD.c(aVar2.e());
        abstractC1539aD.g(aVar2.f());
        abstractC1539aD.b(iB3);
        return abstractC1539aD.a();
    }
}
