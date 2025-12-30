package androidx.camera.video;

import VY0.c;
import android.annotation.SuppressLint;
import androidx.annotation.RestrictTo;
import androidx.camera.video.C20157h;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: MediaSpec.java */
@VY0.c
@RestrictTo
@j.X
/* renamed from: androidx.camera.video.v, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20184v {

    /* compiled from: MediaSpec.java */
    @c.a
    @RestrictTo
    /* renamed from: androidx.camera.video.v$a */
    public static abstract class a {
        @j.N
        public abstract AbstractC20184v a();

        @SuppressLint({"KotlinPropertyAccess"})
        public abstract n0 b();
    }

    /* compiled from: MediaSpec.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.camera.video.v$b */
    public @interface b {
    }

    @j.N
    public static a a() {
        C20157h.b bVar = new C20157h.b();
        bVar.f24924c = -1;
        bVar.f24923b = AbstractC20150a.a().a();
        bVar.f24922a = n0.a().a();
        return bVar;
    }

    @j.N
    public abstract AbstractC20150a b();

    public abstract int c();

    @j.N
    public abstract n0 d();

    @j.N
    public abstract a e();
}
