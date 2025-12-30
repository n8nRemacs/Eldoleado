package androidx.camera.core.imagecapture;

import android.util.Size;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.InterfaceC20128n0;
import androidx.camera.core.imagecapture.C20079o;

/* compiled from: AutoValue_CaptureNode_In.java */
/* renamed from: androidx.camera.core.imagecapture.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20066b extends C20079o.b {

    /* renamed from: c, reason: collision with root package name */
    public final Size f23810c;

    /* renamed from: d, reason: collision with root package name */
    public final int f23811d;

    /* renamed from: e, reason: collision with root package name */
    public final int f23812e;

    /* renamed from: f, reason: collision with root package name */
    public final boolean f23813f;

    /* renamed from: g, reason: collision with root package name */
    public final InterfaceC20128n0 f23814g;

    /* renamed from: h, reason: collision with root package name */
    public final androidx.camera.core.processing.l<B> f23815h;

    /* renamed from: i, reason: collision with root package name */
    public final androidx.camera.core.processing.l<ImageCaptureException> f23816i;

    public C20066b(Size size, int i12, int i13, boolean z12, @j.P InterfaceC20128n0 interfaceC20128n0, androidx.camera.core.processing.l<B> lVar, androidx.camera.core.processing.l<ImageCaptureException> lVar2) {
        if (size == null) {
            throw new NullPointerException("Null size");
        }
        this.f23810c = size;
        this.f23811d = i12;
        this.f23812e = i13;
        this.f23813f = z12;
        this.f23814g = interfaceC20128n0;
        this.f23815h = lVar;
        this.f23816i = lVar2;
    }

    @Override // androidx.camera.core.imagecapture.C20079o.b
    @j.N
    public final androidx.camera.core.processing.l<ImageCaptureException> a() {
        return this.f23816i;
    }

    @Override // androidx.camera.core.imagecapture.C20079o.b
    @j.P
    public final InterfaceC20128n0 b() {
        return this.f23814g;
    }

    @Override // androidx.camera.core.imagecapture.C20079o.b
    public final int c() {
        return this.f23811d;
    }

    @Override // androidx.camera.core.imagecapture.C20079o.b
    public final int d() {
        return this.f23812e;
    }

    @Override // androidx.camera.core.imagecapture.C20079o.b
    @j.N
    public final androidx.camera.core.processing.l<B> e() {
        return this.f23815h;
    }

    public final boolean equals(Object obj) {
        InterfaceC20128n0 interfaceC20128n0;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C20079o.b)) {
            return false;
        }
        C20079o.b bVar = (C20079o.b) obj;
        return this.f23810c.equals(bVar.f()) && this.f23811d == bVar.c() && this.f23812e == bVar.d() && this.f23813f == bVar.g() && ((interfaceC20128n0 = this.f23814g) != null ? interfaceC20128n0.equals(bVar.b()) : bVar.b() == null) && this.f23815h.equals(bVar.e()) && this.f23816i.equals(bVar.a());
    }

    @Override // androidx.camera.core.imagecapture.C20079o.b
    public final Size f() {
        return this.f23810c;
    }

    @Override // androidx.camera.core.imagecapture.C20079o.b
    public final boolean g() {
        return this.f23813f;
    }

    public final int hashCode() {
        int iHashCode = (((((((this.f23810c.hashCode() ^ 1000003) * 1000003) ^ this.f23811d) * 1000003) ^ this.f23812e) * 1000003) ^ (this.f23813f ? 1231 : 1237)) * 1000003;
        InterfaceC20128n0 interfaceC20128n0 = this.f23814g;
        return ((((iHashCode ^ (interfaceC20128n0 == null ? 0 : interfaceC20128n0.hashCode())) * 1000003) ^ this.f23815h.hashCode()) * 1000003) ^ this.f23816i.hashCode();
    }

    public final String toString() {
        return "In{size=" + this.f23810c + ", inputFormat=" + this.f23811d + ", outputFormat=" + this.f23812e + ", virtualCamera=" + this.f23813f + ", imageReaderProxyProvider=" + this.f23814g + ", requestEdge=" + this.f23815h + ", errorEdge=" + this.f23816i + "}";
    }
}
