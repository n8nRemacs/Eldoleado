package androidx.camera.core;

import androidx.camera.core.C0;

/* compiled from: AutoValue_SurfaceOutput_Event.java */
/* renamed from: androidx.camera.core.k, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20121k extends C0.a {

    /* renamed from: a, reason: collision with root package name */
    public final C0 f24380a;

    public C20121k(C0 c02) {
        if (c02 == null) {
            throw new NullPointerException("Null surfaceOutput");
        }
        this.f24380a = c02;
    }

    @Override // androidx.camera.core.C0.a
    public final int a() {
        return 0;
    }

    @Override // androidx.camera.core.C0.a
    @j.N
    public final C0 b() {
        return this.f24380a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C0.a)) {
            return false;
        }
        C0.a aVar = (C0.a) obj;
        return aVar.a() == 0 && this.f24380a.equals(aVar.b());
    }

    public final int hashCode() {
        return this.f24380a.hashCode() ^ (-721379959);
    }

    public final String toString() {
        return "Event{eventCode=0, surfaceOutput=" + this.f24380a + "}";
    }
}
