package androidx.camera.core.processing;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Size;
import j.N;
import j.P;

/* compiled from: AutoValue_Packet.java */
/* renamed from: androidx.camera.core.processing.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20135c<T> extends r<T> {

    /* renamed from: a, reason: collision with root package name */
    public final T f24414a;

    /* renamed from: b, reason: collision with root package name */
    public final androidx.camera.core.impl.utils.i f24415b;

    /* renamed from: c, reason: collision with root package name */
    public final int f24416c;

    /* renamed from: d, reason: collision with root package name */
    public final Size f24417d;

    /* renamed from: e, reason: collision with root package name */
    public final Rect f24418e;

    /* renamed from: f, reason: collision with root package name */
    public final int f24419f;

    /* renamed from: g, reason: collision with root package name */
    public final Matrix f24420g;

    /* renamed from: h, reason: collision with root package name */
    public final androidx.camera.core.impl.r f24421h;

    public C20135c(T t12, @P androidx.camera.core.impl.utils.i iVar, int i12, Size size, Rect rect, int i13, Matrix matrix, androidx.camera.core.impl.r rVar) {
        if (t12 == null) {
            throw new NullPointerException("Null data");
        }
        this.f24414a = t12;
        this.f24415b = iVar;
        this.f24416c = i12;
        if (size == null) {
            throw new NullPointerException("Null size");
        }
        this.f24417d = size;
        if (rect == null) {
            throw new NullPointerException("Null cropRect");
        }
        this.f24418e = rect;
        this.f24419f = i13;
        if (matrix == null) {
            throw new NullPointerException("Null sensorToBufferTransform");
        }
        this.f24420g = matrix;
        if (rVar == null) {
            throw new NullPointerException("Null cameraCaptureResult");
        }
        this.f24421h = rVar;
    }

    @Override // androidx.camera.core.processing.r
    @N
    public final androidx.camera.core.impl.r a() {
        return this.f24421h;
    }

    @Override // androidx.camera.core.processing.r
    @N
    public final Rect b() {
        return this.f24418e;
    }

    @Override // androidx.camera.core.processing.r
    @N
    public final T c() {
        return this.f24414a;
    }

    @Override // androidx.camera.core.processing.r
    @P
    public final androidx.camera.core.impl.utils.i d() {
        return this.f24415b;
    }

    @Override // androidx.camera.core.processing.r
    public final int e() {
        return this.f24416c;
    }

    public final boolean equals(Object obj) {
        androidx.camera.core.impl.utils.i iVar;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof r)) {
            return false;
        }
        r rVar = (r) obj;
        return this.f24414a.equals(rVar.c()) && ((iVar = this.f24415b) != null ? iVar.equals(rVar.d()) : rVar.d() == null) && this.f24416c == rVar.e() && this.f24417d.equals(rVar.h()) && this.f24418e.equals(rVar.b()) && this.f24419f == rVar.f() && this.f24420g.equals(rVar.g()) && this.f24421h.equals(rVar.a());
    }

    @Override // androidx.camera.core.processing.r
    public final int f() {
        return this.f24419f;
    }

    @Override // androidx.camera.core.processing.r
    @N
    public final Matrix g() {
        return this.f24420g;
    }

    @Override // androidx.camera.core.processing.r
    @N
    public final Size h() {
        return this.f24417d;
    }

    public final int hashCode() {
        int iHashCode = (this.f24414a.hashCode() ^ 1000003) * 1000003;
        androidx.camera.core.impl.utils.i iVar = this.f24415b;
        return ((((((((((((iHashCode ^ (iVar == null ? 0 : iVar.hashCode())) * 1000003) ^ this.f24416c) * 1000003) ^ this.f24417d.hashCode()) * 1000003) ^ this.f24418e.hashCode()) * 1000003) ^ this.f24419f) * 1000003) ^ this.f24420g.hashCode()) * 1000003) ^ this.f24421h.hashCode();
    }

    public final String toString() {
        return "Packet{data=" + this.f24414a + ", exif=" + this.f24415b + ", format=" + this.f24416c + ", size=" + this.f24417d + ", cropRect=" + this.f24418e + ", rotationDegrees=" + this.f24419f + ", sensorToBufferTransform=" + this.f24420g + ", cameraCaptureResult=" + this.f24421h + "}";
    }
}
