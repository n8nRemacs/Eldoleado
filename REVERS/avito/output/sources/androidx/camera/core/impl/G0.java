package androidx.camera.core.impl;

import VY0.c;
import android.util.Range;
import android.util.Size;
import androidx.camera.core.impl.C20105m;

/* compiled from: StreamSpec.java */
@VY0.c
@j.X
/* loaded from: classes.dex */
public abstract class G0 {

    /* renamed from: a, reason: collision with root package name */
    public static final Range<Integer> f23964a = new Range<>(0, 0);

    /* compiled from: StreamSpec.java */
    @c.a
    public static abstract class a {
        @j.N
        public abstract G0 a();

        @j.N
        public abstract a b(@j.N androidx.camera.core.F f12);

        @j.N
        public abstract a c(@j.N Range<Integer> range);

        @j.N
        public abstract a d(@j.N Config config);

        @j.N
        public abstract a e(@j.N Size size);
    }

    @j.N
    public static a a(@j.N Size size) {
        C20105m.b bVar = new C20105m.b();
        bVar.e(size);
        bVar.c(f23964a);
        bVar.f24157b = androidx.camera.core.F.f23562d;
        return bVar;
    }

    @j.N
    public abstract androidx.camera.core.F b();

    @j.N
    public abstract Range<Integer> c();

    @j.P
    public abstract Config d();

    @j.N
    public abstract Size e();

    @j.N
    public abstract a f();
}
