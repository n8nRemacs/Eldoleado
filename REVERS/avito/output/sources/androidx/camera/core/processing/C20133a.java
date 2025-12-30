package androidx.camera.core.processing;

import androidx.camera.core.processing.k;
import androidx.concurrent.futures.b;
import j.N;

/* compiled from: AutoValue_DefaultSurfaceProcessor_PendingSnapshot.java */
/* renamed from: androidx.camera.core.processing.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20133a extends k.b {

    /* renamed from: a, reason: collision with root package name */
    public final int f24408a;

    /* renamed from: b, reason: collision with root package name */
    public final int f24409b;

    /* renamed from: c, reason: collision with root package name */
    public final b.a<Void> f24410c;

    public C20133a(int i12, int i13, b.a<Void> aVar) {
        this.f24408a = i12;
        this.f24409b = i13;
        this.f24410c = aVar;
    }

    @Override // androidx.camera.core.processing.k.b
    @N
    public final b.a<Void> a() {
        return this.f24410c;
    }

    @Override // androidx.camera.core.processing.k.b
    @j.F
    public final int b() {
        return this.f24408a;
    }

    @Override // androidx.camera.core.processing.k.b
    @j.F
    public final int c() {
        return this.f24409b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof k.b)) {
            return false;
        }
        k.b bVar = (k.b) obj;
        return this.f24408a == bVar.b() && this.f24409b == bVar.c() && this.f24410c.equals(bVar.a());
    }

    public final int hashCode() {
        return ((((this.f24408a ^ 1000003) * 1000003) ^ this.f24409b) * 1000003) ^ this.f24410c.hashCode();
    }

    public final String toString() {
        return "PendingSnapshot{jpegQuality=" + this.f24408a + ", rotationDegrees=" + this.f24409b + ", completer=" + this.f24410c + "}";
    }
}
