package androidx.camera.video.internal.config;

import android.util.Range;
import android.util.Size;
import androidx.camera.core.C20140q0;
import androidx.camera.core.F;
import androidx.camera.core.K0;
import androidx.camera.core.impl.Timebase;
import androidx.camera.video.internal.encoder.J;
import androidx.camera.video.internal.encoder.K;
import androidx.camera.video.n0;
import androidx.core.util.T;
import j.N;
import j.X;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* compiled from: VideoEncoderConfigDefaultResolver.java */
@X
/* loaded from: classes.dex */
public class l implements T<J> {

    /* renamed from: g, reason: collision with root package name */
    public static final Size f25089g = new Size(1280, 720);

    /* renamed from: h, reason: collision with root package name */
    public static final Range<Integer> f25090h = new Range<>(1, 60);

    /* renamed from: a, reason: collision with root package name */
    public final String f25091a;

    /* renamed from: b, reason: collision with root package name */
    public final Timebase f25092b;

    /* renamed from: c, reason: collision with root package name */
    public final n0 f25093c;

    /* renamed from: d, reason: collision with root package name */
    public final Size f25094d;

    /* renamed from: e, reason: collision with root package name */
    public final F f25095e;

    /* renamed from: f, reason: collision with root package name */
    public final Range<Integer> f25096f;

    public l(@N String str, @N Timebase timebase, @N n0 n0Var, @N Size size, @N F f12, @N Range<Integer> range) {
        this.f25091a = str;
        this.f25092b = timebase;
        this.f25093c = n0Var;
        this.f25094d = size;
        this.f25095e = f12;
        this.f25096f = range;
    }

    @Override // androidx.core.util.T
    @N
    public final J get() {
        Integer num;
        Range<Integer> range = K0.f23588o;
        Range<Integer> range2 = this.f25096f;
        int iIntValue = !Objects.equals(range2, range) ? ((Integer) f25090h.clamp((Integer) range2.getUpper())).intValue() : 30;
        Integer numValueOf = Integer.valueOf(iIntValue);
        boolean zEquals = Objects.equals(range2, range);
        Object obj = range2;
        if (!zEquals) {
            obj = "<UNSPECIFIED>";
        }
        String.format("Default resolved frame rate: %dfps. [Expected operating range: %s]", numValueOf, obj);
        C20140q0.d(3, "VidEncCfgDefaultRslvr");
        C20140q0.d(3, "VidEncCfgDefaultRslvr");
        Range<Integer> rangeC = this.f25093c.c();
        C20140q0.d(3, "VidEncCfgDefaultRslvr");
        F f12 = this.f25095e;
        int i12 = f12.f23569b;
        Size size = this.f25094d;
        int width = size.getWidth();
        Size size2 = f25089g;
        int iD2 = k.d(14000000, i12, 8, iIntValue, 30, width, size2.getWidth(), size.getHeight(), size2.getHeight(), rangeC);
        HashMap map = androidx.camera.video.internal.utils.a.f25268c;
        String str = this.f25091a;
        Map map2 = (Map) map.get(str);
        int iIntValue2 = (map2 == null || (num = (Integer) map2.get(f12)) == null) ? -1 : num.intValue();
        K kA2 = k.a(iIntValue2, str);
        J.a aVarD = J.d();
        aVarD.f(str);
        aVarD.e(this.f25092b);
        aVarD.h(size);
        aVarD.b(iD2);
        aVarD.d(iIntValue);
        aVarD.g(iIntValue2);
        aVarD.c(kA2);
        return aVarD.a();
    }
}
