package androidx.camera.video;

import android.location.Location;
import androidx.camera.video.C20183u;
import java.io.File;

/* compiled from: AutoValue_FileOutputOptions_FileOutputOptionsInternal.java */
/* renamed from: androidx.camera.video.g, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20156g extends C20183u.b {

    /* renamed from: a, reason: collision with root package name */
    public final long f24910a;

    /* renamed from: b, reason: collision with root package name */
    public final long f24911b;

    /* renamed from: c, reason: collision with root package name */
    public final Location f24912c;

    /* renamed from: d, reason: collision with root package name */
    public final File f24913d;

    /* compiled from: AutoValue_FileOutputOptions_FileOutputOptionsInternal.java */
    /* renamed from: androidx.camera.video.g$b */
    public static final class b extends C20183u.b.a {

        /* renamed from: a, reason: collision with root package name */
        public Long f24914a;

        /* renamed from: b, reason: collision with root package name */
        public Long f24915b;

        /* renamed from: c, reason: collision with root package name */
        public File f24916c;

        @Override // androidx.camera.video.C20183u.b.a
        public final C20156g a() {
            String strQ = this.f24914a == null ? " fileSizeLimit" : "";
            if (this.f24915b == null) {
                strQ = strQ.concat(" durationLimitMillis");
            }
            if (this.f24916c == null) {
                strQ = androidx.appcompat.app.r.q(strQ, " file");
            }
            if (strQ.isEmpty()) {
                return new C20156g(this.f24914a.longValue(), this.f24915b.longValue(), null, this.f24916c, null);
            }
            throw new IllegalStateException("Missing required properties:".concat(strQ));
        }

        @Override // androidx.camera.video.C20183u.b.a
        public final b b(File file) {
            this.f24916c = file;
            return this;
        }
    }

    public C20156g(long j12, long j13, Location location, File file, a aVar) {
        this.f24910a = j12;
        this.f24911b = j13;
        this.f24912c = location;
        this.f24913d = file;
    }

    @Override // androidx.camera.video.AbstractC20186x.b
    @j.F
    public final long a() {
        return this.f24911b;
    }

    @Override // androidx.camera.video.AbstractC20186x.b
    @j.F
    public final long b() {
        return this.f24910a;
    }

    @Override // androidx.camera.video.AbstractC20186x.b
    @j.P
    public final Location c() {
        return this.f24912c;
    }

    @Override // androidx.camera.video.C20183u.b
    @j.N
    public final File d() {
        return this.f24913d;
    }

    public final boolean equals(Object obj) {
        Location location;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C20183u.b)) {
            return false;
        }
        C20183u.b bVar = (C20183u.b) obj;
        return this.f24910a == bVar.b() && this.f24911b == bVar.a() && ((location = this.f24912c) != null ? location.equals(bVar.c()) : bVar.c() == null) && this.f24913d.equals(bVar.d());
    }

    public final int hashCode() {
        long j12 = this.f24910a;
        long j13 = this.f24911b;
        int i12 = (((((int) (j12 ^ (j12 >>> 32))) ^ 1000003) * 1000003) ^ ((int) ((j13 >>> 32) ^ j13))) * 1000003;
        Location location = this.f24912c;
        return ((i12 ^ (location == null ? 0 : location.hashCode())) * 1000003) ^ this.f24913d.hashCode();
    }

    public final String toString() {
        return "FileOutputOptionsInternal{fileSizeLimit=" + this.f24910a + ", durationLimitMillis=" + this.f24911b + ", location=" + this.f24912c + ", file=" + this.f24913d + "}";
    }
}
