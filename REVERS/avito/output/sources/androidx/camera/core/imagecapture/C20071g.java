package androidx.camera.core.imagecapture;

import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.imagecapture.A;

/* compiled from: AutoValue_ProcessingNode_InputPacket.java */
/* renamed from: androidx.camera.core.imagecapture.g, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20071g extends A.b {

    /* renamed from: a, reason: collision with root package name */
    public final B f23828a;

    /* renamed from: b, reason: collision with root package name */
    public final InterfaceC20120j0 f23829b;

    public C20071g(B b12, InterfaceC20120j0 interfaceC20120j0) {
        if (b12 == null) {
            throw new NullPointerException("Null processingRequest");
        }
        this.f23828a = b12;
        if (interfaceC20120j0 == null) {
            throw new NullPointerException("Null imageProxy");
        }
        this.f23829b = interfaceC20120j0;
    }

    @Override // androidx.camera.core.imagecapture.A.b
    @j.N
    public final InterfaceC20120j0 a() {
        return this.f23829b;
    }

    @Override // androidx.camera.core.imagecapture.A.b
    @j.N
    public final B b() {
        return this.f23828a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof A.b)) {
            return false;
        }
        A.b bVar = (A.b) obj;
        return this.f23828a.equals(bVar.b()) && this.f23829b.equals(bVar.a());
    }

    public final int hashCode() {
        return ((this.f23828a.hashCode() ^ 1000003) * 1000003) ^ this.f23829b.hashCode();
    }

    public final String toString() {
        return "InputPacket{processingRequest=" + this.f23828a + ", imageProxy=" + this.f23829b + "}";
    }
}
