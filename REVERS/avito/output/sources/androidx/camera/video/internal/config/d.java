package androidx.camera.video.internal.config;

import android.util.Range;
import androidx.camera.core.C20140q0;
import androidx.camera.video.AbstractC20150a;
import androidx.camera.video.internal.encoder.AbstractC20159a;
import androidx.core.util.T;
import j.N;
import j.X;

/* compiled from: AudioEncoderConfigDefaultResolver.java */
@X
/* loaded from: classes.dex */
public final class d implements T<AbstractC20159a> {

    /* renamed from: a, reason: collision with root package name */
    public final String f25069a;

    /* renamed from: b, reason: collision with root package name */
    public final int f25070b;

    /* renamed from: c, reason: collision with root package name */
    public final AbstractC20150a f25071c;

    /* renamed from: d, reason: collision with root package name */
    public final androidx.camera.video.internal.audio.a f25072d;

    public d(@N String str, int i12, @N AbstractC20150a abstractC20150a, @N androidx.camera.video.internal.audio.a aVar) {
        this.f25069a = str;
        this.f25070b = i12;
        this.f25071c = abstractC20150a;
        this.f25072d = aVar;
    }

    @Override // androidx.core.util.T
    @N
    public final AbstractC20159a get() {
        Range<Integer> rangeB = this.f25071c.b();
        C20140q0.d(3, "AudioEncCfgDefaultRslvr");
        androidx.camera.video.internal.audio.a aVar = this.f25072d;
        int iB2 = b.b(156000, aVar.e(), 2, aVar.f(), 48000, rangeB);
        AbstractC20159a.AbstractC1539a abstractC1539aD = AbstractC20159a.d();
        abstractC1539aD.e(this.f25069a);
        abstractC1539aD.f(this.f25070b);
        abstractC1539aD.d();
        abstractC1539aD.c(aVar.e());
        abstractC1539aD.g(aVar.f());
        abstractC1539aD.b(iB2);
        return abstractC1539aD.a();
    }
}
