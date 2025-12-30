package androidx.camera.video;

import VY0.c;
import android.util.Range;
import androidx.annotation.RestrictTo;
import androidx.camera.video.C20179p;
import java.util.Arrays;

/* compiled from: VideoSpec.java */
@VY0.c
@RestrictTo
@j.X
/* loaded from: classes.dex */
public abstract class n0 {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public static final Range<Integer> f25320a = new Range<>(0, Integer.MAX_VALUE);

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public static final Range<Integer> f25321b = new Range<>(0, Integer.MAX_VALUE);

    /* renamed from: c, reason: collision with root package name */
    @j.N
    public static final D f25322c;

    /* compiled from: VideoSpec.java */
    @c.a
    @RestrictTo
    public static abstract class a {
        @j.N
        public abstract n0 a();

        @j.N
        public abstract a b(int i12);
    }

    static {
        A a12 = A.f24682c;
        f25322c = D.a(Arrays.asList(a12, A.f24681b, A.f24680a), C20181s.a(a12));
    }

    @j.N
    public static a a() {
        C20179p.b bVar = new C20179p.b();
        D d12 = f25322c;
        if (d12 == null) {
            throw new NullPointerException("Null qualitySelector");
        }
        bVar.f25330a = d12;
        Range<Integer> range = f25320a;
        if (range == null) {
            throw new NullPointerException("Null frameRate");
        }
        bVar.f25331b = range;
        Range<Integer> range2 = f25321b;
        if (range2 == null) {
            throw new NullPointerException("Null bitrate");
        }
        bVar.f25332c = range2;
        bVar.f25333d = -1;
        return bVar;
    }

    public abstract int b();

    @j.N
    public abstract Range<Integer> c();

    @j.N
    public abstract Range<Integer> d();

    @j.N
    public abstract D e();

    @j.N
    public abstract a f();
}
