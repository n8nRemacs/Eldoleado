package androidx.camera.video;

import VY0.c;
import androidx.camera.video.AbstractC20186x;
import androidx.camera.video.C20156g;
import java.io.File;

/* compiled from: FileOutputOptions.java */
@j.X
/* renamed from: androidx.camera.video.u, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20183u extends AbstractC20186x {

    /* renamed from: b, reason: collision with root package name */
    public final b f25335b;

    /* compiled from: FileOutputOptions.java */
    @j.X
    /* renamed from: androidx.camera.video.u$a */
    public static final class a extends AbstractC20186x.a<C20183u, a> {

        /* renamed from: a, reason: collision with root package name */
        public final b.a f25336a;

        public a(@j.N File file) {
            C20156g.b bVar = new C20156g.b();
            bVar.f24914a = 0L;
            bVar.f24915b = 0L;
            this.f25336a = bVar;
            bVar.b(file);
        }

        @j.N
        public final C20183u a() {
            return new C20183u(this.f25336a.a());
        }
    }

    /* compiled from: FileOutputOptions.java */
    @VY0.c
    /* renamed from: androidx.camera.video.u$b */
    public static abstract class b extends AbstractC20186x.b {

        /* compiled from: FileOutputOptions.java */
        @c.a
        /* renamed from: androidx.camera.video.u$b$a */
        public static abstract class a extends AbstractC20186x.b.a<a> {
            @j.N
            public abstract C20156g a();

            @j.N
            public abstract C20156g.b b(@j.N File file);
        }

        @j.N
        public abstract File d();
    }

    public C20183u(@j.N b bVar) {
        super(bVar);
        this.f25335b = bVar;
    }

    public final boolean equals(@j.P Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C20183u)) {
            return false;
        }
        return this.f25335b.equals(((C20183u) obj).f25335b);
    }

    public final int hashCode() {
        return this.f25335b.hashCode();
    }

    @j.N
    public final String toString() {
        return this.f25335b.toString().replaceFirst("FileOutputOptionsInternal", "FileOutputOptions");
    }
}
