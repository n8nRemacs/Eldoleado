package androidx.camera.core;

import android.graphics.Rect;
import android.util.Size;
import androidx.camera.core.x0;

/* compiled from: AutoValue_ResolutionInfo_ResolutionInfoInternal.java */
/* renamed from: androidx.camera.core.j, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20119j extends x0.a {

    /* renamed from: a, reason: collision with root package name */
    public final Size f24377a;

    /* renamed from: b, reason: collision with root package name */
    public final Rect f24378b;

    /* renamed from: c, reason: collision with root package name */
    public final int f24379c;

    /* compiled from: AutoValue_ResolutionInfo_ResolutionInfoInternal.java */
    /* renamed from: androidx.camera.core.j$b */
    public static final class b extends x0.a.AbstractC1536a {
    }

    public C20119j(Size size, Rect rect, int i12, a aVar) {
        this.f24377a = size;
        this.f24378b = rect;
        this.f24379c = i12;
    }

    @Override // androidx.camera.core.x0.a
    @j.N
    public final Rect a() {
        return this.f24378b;
    }

    @Override // androidx.camera.core.x0.a
    @j.N
    public final Size b() {
        return this.f24377a;
    }

    @Override // androidx.camera.core.x0.a
    public final int c() {
        return this.f24379c;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof x0.a)) {
            return false;
        }
        x0.a aVar = (x0.a) obj;
        return this.f24377a.equals(aVar.b()) && this.f24378b.equals(aVar.a()) && this.f24379c == aVar.c();
    }

    public final int hashCode() {
        return ((((this.f24377a.hashCode() ^ 1000003) * 1000003) ^ this.f24378b.hashCode()) * 1000003) ^ this.f24379c;
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("ResolutionInfoInternal{resolution=");
        sb2.append(this.f24377a);
        sb2.append(", cropRect=");
        sb2.append(this.f24378b);
        sb2.append(", rotationDegrees=");
        return AK.c.i(this.f24379c, "}", sb2);
    }
}
