package androidx.camera.core.processing;

import androidx.camera.core.processing.C;
import j.N;
import java.util.List;

/* compiled from: AutoValue_SurfaceProcessorNode_In.java */
/* renamed from: androidx.camera.core.processing.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20136d extends C.b {

    /* renamed from: a, reason: collision with root package name */
    public final z f24422a;

    /* renamed from: b, reason: collision with root package name */
    public final List<C.d> f24423b;

    public C20136d(z zVar, List<C.d> list) {
        if (zVar == null) {
            throw new NullPointerException("Null surfaceEdge");
        }
        this.f24422a = zVar;
        if (list == null) {
            throw new NullPointerException("Null outConfigs");
        }
        this.f24423b = list;
    }

    @Override // androidx.camera.core.processing.C.b
    @N
    public final List<C.d> a() {
        return this.f24423b;
    }

    @Override // androidx.camera.core.processing.C.b
    @N
    public final z b() {
        return this.f24422a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C.b)) {
            return false;
        }
        C.b bVar = (C.b) obj;
        return this.f24422a.equals(bVar.b()) && this.f24423b.equals(bVar.a());
    }

    public final int hashCode() {
        return ((this.f24422a.hashCode() ^ 1000003) * 1000003) ^ this.f24423b.hashCode();
    }

    public final String toString() {
        return "In{surfaceEdge=" + this.f24422a + ", outConfigs=" + this.f24423b + "}";
    }
}
