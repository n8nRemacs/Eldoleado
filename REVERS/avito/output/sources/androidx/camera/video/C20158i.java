package androidx.camera.video;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.location.Location;
import android.net.Uri;
import androidx.camera.video.C20185w;

/* compiled from: AutoValue_MediaStoreOutputOptions_MediaStoreOutputOptionsInternal.java */
/* renamed from: androidx.camera.video.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20158i extends C20185w.b {

    /* renamed from: a, reason: collision with root package name */
    public final long f24930a;

    /* renamed from: b, reason: collision with root package name */
    public final long f24931b;

    /* renamed from: c, reason: collision with root package name */
    public final Location f24932c;

    /* renamed from: d, reason: collision with root package name */
    public final ContentResolver f24933d;

    /* renamed from: e, reason: collision with root package name */
    public final Uri f24934e;

    /* renamed from: f, reason: collision with root package name */
    public final ContentValues f24935f;

    /* compiled from: AutoValue_MediaStoreOutputOptions_MediaStoreOutputOptionsInternal.java */
    /* renamed from: androidx.camera.video.i$b */
    public static final class b extends C20185w.b.a {
    }

    public C20158i(long j12, long j13, Location location, ContentResolver contentResolver, Uri uri, ContentValues contentValues, a aVar) {
        this.f24930a = j12;
        this.f24931b = j13;
        this.f24932c = location;
        this.f24933d = contentResolver;
        this.f24934e = uri;
        this.f24935f = contentValues;
    }

    @Override // androidx.camera.video.AbstractC20186x.b
    @j.F
    public final long a() {
        return this.f24931b;
    }

    @Override // androidx.camera.video.AbstractC20186x.b
    @j.F
    public final long b() {
        return this.f24930a;
    }

    @Override // androidx.camera.video.AbstractC20186x.b
    @j.P
    public final Location c() {
        return this.f24932c;
    }

    @Override // androidx.camera.video.C20185w.b
    @j.N
    public final Uri d() {
        return this.f24934e;
    }

    @Override // androidx.camera.video.C20185w.b
    @j.N
    public final ContentResolver e() {
        return this.f24933d;
    }

    public final boolean equals(Object obj) {
        Location location;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C20185w.b)) {
            return false;
        }
        C20185w.b bVar = (C20185w.b) obj;
        return this.f24930a == bVar.b() && this.f24931b == bVar.a() && ((location = this.f24932c) != null ? location.equals(bVar.c()) : bVar.c() == null) && this.f24933d.equals(bVar.e()) && this.f24934e.equals(bVar.d()) && this.f24935f.equals(bVar.f());
    }

    @Override // androidx.camera.video.C20185w.b
    @j.N
    public final ContentValues f() {
        return this.f24935f;
    }

    public final int hashCode() {
        long j12 = this.f24930a;
        long j13 = this.f24931b;
        int i12 = (((((int) (j12 ^ (j12 >>> 32))) ^ 1000003) * 1000003) ^ ((int) ((j13 >>> 32) ^ j13))) * 1000003;
        Location location = this.f24932c;
        return ((((((i12 ^ (location == null ? 0 : location.hashCode())) * 1000003) ^ this.f24933d.hashCode()) * 1000003) ^ this.f24934e.hashCode()) * 1000003) ^ this.f24935f.hashCode();
    }

    public final String toString() {
        return "MediaStoreOutputOptionsInternal{fileSizeLimit=" + this.f24930a + ", durationLimitMillis=" + this.f24931b + ", location=" + this.f24932c + ", contentResolver=" + this.f24933d + ", collectionUri=" + this.f24934e + ", contentValues=" + this.f24935f + "}";
    }
}
