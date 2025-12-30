package androidx.camera.video;

import VY0.c;
import android.util.Range;
import androidx.annotation.RestrictTo;
import androidx.camera.video.C20152c;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: AudioSpec.java */
@VY0.c
@RestrictTo
@j.X
/* renamed from: androidx.camera.video.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20150a {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public static final Range<Integer> f24857a = new Range<>(0, Integer.MAX_VALUE);

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public static final Range<Integer> f24858b = new Range<>(0, Integer.MAX_VALUE);

    /* compiled from: AudioSpec.java */
    @c.a
    @RestrictTo
    /* renamed from: androidx.camera.video.a$a, reason: collision with other inner class name */
    public static abstract class AbstractC1537a {
        @j.N
        public abstract AbstractC20150a a();
    }

    /* compiled from: AudioSpec.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.camera.video.a$b */
    public @interface b {
    }

    /* compiled from: AudioSpec.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.camera.video.a$c */
    public @interface c {
    }

    static {
        C20152c.b bVar = (C20152c.b) a();
        bVar.f24891e = 0;
        bVar.a();
    }

    @j.N
    public static AbstractC1537a a() {
        C20152c.b bVar = new C20152c.b();
        bVar.f24888b = -1;
        bVar.f24889c = -1;
        bVar.f24891e = -1;
        Range<Integer> range = f24857a;
        if (range == null) {
            throw new NullPointerException("Null bitrate");
        }
        bVar.f24887a = range;
        Range<Integer> range2 = f24858b;
        if (range2 == null) {
            throw new NullPointerException("Null sampleRate");
        }
        bVar.f24890d = range2;
        return bVar;
    }

    @j.N
    public abstract Range<Integer> b();

    public abstract int c();

    @j.N
    public abstract Range<Integer> d();

    public abstract int e();

    public abstract int f();
}
