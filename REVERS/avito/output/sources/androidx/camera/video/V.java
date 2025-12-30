package androidx.camera.video;

/* compiled from: RecordingStats.java */
@VY0.c
@j.X
/* loaded from: classes.dex */
public abstract class V {
    @j.N
    public static V d(long j12, long j13, @j.N AbstractC20151b abstractC20151b) {
        androidx.core.util.z.a("duration must be positive value.", j12 >= 0);
        androidx.core.util.z.a("bytes must be positive value.", j13 >= 0);
        return new C20177n(j12, j13, abstractC20151b);
    }

    @j.N
    public abstract AbstractC20151b a();

    public abstract long b();

    public abstract long c();
}
