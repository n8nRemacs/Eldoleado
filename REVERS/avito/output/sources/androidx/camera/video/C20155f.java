package androidx.camera.video;

import android.location.Location;
import android.os.ParcelFileDescriptor;
import androidx.camera.video.C20182t;

/* compiled from: AutoValue_FileDescriptorOutputOptions_FileDescriptorOutputOptionsInternal.java */
/* renamed from: androidx.camera.video.f, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20155f extends C20182t.b {

    /* renamed from: a, reason: collision with root package name */
    public final long f24904a;

    /* renamed from: b, reason: collision with root package name */
    public final long f24905b;

    /* renamed from: c, reason: collision with root package name */
    public final Location f24906c;

    /* renamed from: d, reason: collision with root package name */
    public final ParcelFileDescriptor f24907d;

    /* compiled from: AutoValue_FileDescriptorOutputOptions_FileDescriptorOutputOptionsInternal.java */
    /* renamed from: androidx.camera.video.f$b */
    public static final class b extends C20182t.b.a {
    }

    public C20155f(long j12, long j13, Location location, ParcelFileDescriptor parcelFileDescriptor, a aVar) {
        this.f24904a = j12;
        this.f24905b = j13;
        this.f24906c = location;
        this.f24907d = parcelFileDescriptor;
    }

    @Override // androidx.camera.video.AbstractC20186x.b
    @j.F
    public final long a() {
        return this.f24905b;
    }

    @Override // androidx.camera.video.AbstractC20186x.b
    @j.F
    public final long b() {
        return this.f24904a;
    }

    @Override // androidx.camera.video.AbstractC20186x.b
    @j.P
    public final Location c() {
        return this.f24906c;
    }

    @Override // androidx.camera.video.C20182t.b
    @j.N
    public final ParcelFileDescriptor d() {
        return this.f24907d;
    }

    public final boolean equals(Object obj) {
        Location location;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C20182t.b)) {
            return false;
        }
        C20182t.b bVar = (C20182t.b) obj;
        return this.f24904a == bVar.b() && this.f24905b == bVar.a() && ((location = this.f24906c) != null ? location.equals(bVar.c()) : bVar.c() == null) && this.f24907d.equals(bVar.d());
    }

    public final int hashCode() {
        long j12 = this.f24904a;
        long j13 = this.f24905b;
        int i12 = (((((int) (j12 ^ (j12 >>> 32))) ^ 1000003) * 1000003) ^ ((int) ((j13 >>> 32) ^ j13))) * 1000003;
        Location location = this.f24906c;
        return ((i12 ^ (location == null ? 0 : location.hashCode())) * 1000003) ^ this.f24907d.hashCode();
    }

    public final String toString() {
        return "FileDescriptorOutputOptionsInternal{fileSizeLimit=" + this.f24904a + ", durationLimitMillis=" + this.f24905b + ", location=" + this.f24906c + ", parcelFileDescriptor=" + this.f24907d + "}";
    }
}
