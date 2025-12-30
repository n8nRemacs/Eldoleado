package androidx.camera.video;

import android.net.Uri;

/* compiled from: AutoValue_OutputResults.java */
/* renamed from: androidx.camera.video.j, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20173j extends AbstractC20187y {

    /* renamed from: a, reason: collision with root package name */
    public final Uri f25282a;

    public C20173j(Uri uri) {
        if (uri == null) {
            throw new NullPointerException("Null outputUri");
        }
        this.f25282a = uri;
    }

    @Override // androidx.camera.video.AbstractC20187y
    @j.N
    public final Uri a() {
        return this.f25282a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractC20187y) {
            return this.f25282a.equals(((AbstractC20187y) obj).a());
        }
        return false;
    }

    public final int hashCode() {
        return this.f25282a.hashCode() ^ 1000003;
    }

    public final String toString() {
        return "OutputResults{outputUri=" + this.f25282a + "}";
    }
}
