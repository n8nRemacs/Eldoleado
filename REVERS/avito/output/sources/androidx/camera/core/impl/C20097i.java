package androidx.camera.core.impl;

/* compiled from: AutoValue_Identifier.java */
/* renamed from: androidx.camera.core.impl.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20097i extends X {

    /* renamed from: a, reason: collision with root package name */
    public final Object f24125a;

    public C20097i(Object obj) {
        this.f24125a = obj;
    }

    @Override // androidx.camera.core.impl.X
    @j.N
    public final Object b() {
        return this.f24125a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof X) {
            return this.f24125a.equals(((X) obj).b());
        }
        return false;
    }

    public final int hashCode() {
        return this.f24125a.hashCode() ^ 1000003;
    }

    public final String toString() {
        return androidx.appcompat.app.r.o(this.f24125a, "}", new StringBuilder("Identifier{value="));
    }
}
