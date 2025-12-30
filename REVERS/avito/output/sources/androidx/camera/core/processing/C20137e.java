package androidx.camera.core.processing;

import android.graphics.Rect;
import android.util.Size;
import androidx.camera.core.processing.C;
import j.N;
import java.util.UUID;

/* compiled from: AutoValue_SurfaceProcessorNode_OutConfig.java */
/* renamed from: androidx.camera.core.processing.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20137e extends C.d {

    /* renamed from: a, reason: collision with root package name */
    public final UUID f24424a;

    /* renamed from: b, reason: collision with root package name */
    public final int f24425b;

    /* renamed from: c, reason: collision with root package name */
    public final int f24426c;

    /* renamed from: d, reason: collision with root package name */
    public final Rect f24427d;

    /* renamed from: e, reason: collision with root package name */
    public final Size f24428e;

    /* renamed from: f, reason: collision with root package name */
    public final int f24429f;

    /* renamed from: g, reason: collision with root package name */
    public final boolean f24430g;

    public C20137e(UUID uuid, int i12, int i13, Rect rect, Size size, int i14, boolean z12) {
        if (uuid == null) {
            throw new NullPointerException("Null uuid");
        }
        this.f24424a = uuid;
        this.f24425b = i12;
        this.f24426c = i13;
        if (rect == null) {
            throw new NullPointerException("Null cropRect");
        }
        this.f24427d = rect;
        if (size == null) {
            throw new NullPointerException("Null size");
        }
        this.f24428e = size;
        this.f24429f = i14;
        this.f24430g = z12;
    }

    @Override // androidx.camera.core.processing.C.d
    @N
    public final Rect a() {
        return this.f24427d;
    }

    @Override // androidx.camera.core.processing.C.d
    public final int b() {
        return this.f24426c;
    }

    @Override // androidx.camera.core.processing.C.d
    public final boolean c() {
        return this.f24430g;
    }

    @Override // androidx.camera.core.processing.C.d
    public final int d() {
        return this.f24429f;
    }

    @Override // androidx.camera.core.processing.C.d
    @N
    public final Size e() {
        return this.f24428e;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C.d)) {
            return false;
        }
        C.d dVar = (C.d) obj;
        return this.f24424a.equals(dVar.g()) && this.f24425b == dVar.f() && this.f24426c == dVar.b() && this.f24427d.equals(dVar.a()) && this.f24428e.equals(dVar.e()) && this.f24429f == dVar.d() && this.f24430g == dVar.c();
    }

    @Override // androidx.camera.core.processing.C.d
    public final int f() {
        return this.f24425b;
    }

    @Override // androidx.camera.core.processing.C.d
    @N
    public final UUID g() {
        return this.f24424a;
    }

    public final int hashCode() {
        return ((((((((((((this.f24424a.hashCode() ^ 1000003) * 1000003) ^ this.f24425b) * 1000003) ^ this.f24426c) * 1000003) ^ this.f24427d.hashCode()) * 1000003) ^ this.f24428e.hashCode()) * 1000003) ^ this.f24429f) * 1000003) ^ (this.f24430g ? 1231 : 1237);
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("OutConfig{uuid=");
        sb2.append(this.f24424a);
        sb2.append(", targets=");
        sb2.append(this.f24425b);
        sb2.append(", format=");
        sb2.append(this.f24426c);
        sb2.append(", cropRect=");
        sb2.append(this.f24427d);
        sb2.append(", size=");
        sb2.append(this.f24428e);
        sb2.append(", rotationDegrees=");
        sb2.append(this.f24429f);
        sb2.append(", mirroring=");
        return androidx.appcompat.app.r.s("}", sb2, this.f24430g);
    }
}
