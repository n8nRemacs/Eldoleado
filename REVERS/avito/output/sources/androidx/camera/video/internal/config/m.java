package androidx.camera.video.internal.config;

import android.util.Range;
import android.util.Size;
import androidx.camera.core.C20140q0;
import androidx.camera.core.F;
import androidx.camera.core.K0;
import androidx.camera.core.impl.S;
import androidx.camera.core.impl.Timebase;
import androidx.camera.video.internal.encoder.J;
import androidx.camera.video.internal.encoder.K;
import androidx.camera.video.n0;
import androidx.core.util.T;
import j.N;
import j.X;
import java.util.Objects;

/* compiled from: VideoEncoderConfigVideoProfileResolver.java */
@X
/* loaded from: classes.dex */
public class m implements T<J> {

    /* renamed from: a, reason: collision with root package name */
    public final String f25097a;

    /* renamed from: b, reason: collision with root package name */
    public final Timebase f25098b;

    /* renamed from: c, reason: collision with root package name */
    public final n0 f25099c;

    /* renamed from: d, reason: collision with root package name */
    public final Size f25100d;

    /* renamed from: e, reason: collision with root package name */
    public final S.c f25101e;

    /* renamed from: f, reason: collision with root package name */
    public final F f25102f;

    /* renamed from: g, reason: collision with root package name */
    public final Range<Integer> f25103g;

    public m(@N String str, @N Timebase timebase, @N n0 n0Var, @N Size size, @N S.c cVar, @N F f12, @N Range<Integer> range) {
        this.f25097a = str;
        this.f25098b = timebase;
        this.f25099c = n0Var;
        this.f25100d = size;
        this.f25101e = cVar;
        this.f25102f = f12;
        this.f25103g = range;
    }

    @Override // androidx.core.util.T
    @N
    public final J get() {
        S.c cVar = this.f25101e;
        int iF2 = cVar.f();
        Range<Integer> range = K0.f23588o;
        Range<Integer> range2 = this.f25103g;
        int iIntValue = !Objects.equals(range2, range) ? ((Integer) range2.clamp(Integer.valueOf(iF2))).intValue() : iF2;
        Integer numValueOf = Integer.valueOf(iIntValue);
        Integer numValueOf2 = Integer.valueOf(iF2);
        boolean zEquals = Objects.equals(range2, range);
        Object obj = range2;
        if (!zEquals) {
            obj = "<UNSPECIFIED>";
        }
        String.format("Resolved frame rate %dfps [Video profile frame rate: %dfps, Expected operating range: %s]", numValueOf, numValueOf2, obj);
        C20140q0.d(3, "VidEncVdPrflRslvr");
        C20140q0.d(3, "VidEncVdPrflRslvr");
        Range<Integer> rangeC = this.f25099c.c();
        C20140q0.d(3, "VidEncVdPrflRslvr");
        int iC2 = cVar.c();
        int i12 = this.f25102f.f23569b;
        int iB2 = cVar.b();
        int iF3 = cVar.f();
        Size size = this.f25100d;
        int iD2 = k.d(iC2, i12, iB2, iIntValue, iF3, size.getWidth(), cVar.k(), size.getHeight(), cVar.h(), rangeC);
        int iJ2 = cVar.j();
        String str = this.f25097a;
        K kA2 = k.a(iJ2, str);
        J.a aVarD = J.d();
        aVarD.f(str);
        aVarD.e(this.f25098b);
        aVarD.h(size);
        aVarD.b(iD2);
        aVarD.d(iIntValue);
        aVarD.g(iJ2);
        aVarD.c(kA2);
        return aVarD.a();
    }
}
