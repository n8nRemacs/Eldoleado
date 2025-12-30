package androidx.camera.core.imagecapture;

import android.graphics.Matrix;
import android.graphics.Rect;
import androidx.camera.core.C20056e0;
import androidx.camera.core.impl.AbstractC20109p;
import java.util.List;
import java.util.concurrent.Executor;
import ru.cyberity.cbr.core.domain.camera.CameraX;

/* compiled from: AutoValue_TakePictureRequest.java */
/* renamed from: androidx.camera.core.imagecapture.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20072h extends O {

    /* renamed from: b, reason: collision with root package name */
    public final Executor f23830b;

    /* renamed from: c, reason: collision with root package name */
    public final C20056e0.j f23831c;

    /* renamed from: d, reason: collision with root package name */
    public final CameraX.d f23832d;

    /* renamed from: e, reason: collision with root package name */
    public final C20056e0.l f23833e;

    /* renamed from: f, reason: collision with root package name */
    public final Rect f23834f;

    /* renamed from: g, reason: collision with root package name */
    public final Matrix f23835g;

    /* renamed from: h, reason: collision with root package name */
    public final int f23836h;

    /* renamed from: i, reason: collision with root package name */
    public final int f23837i;

    /* renamed from: j, reason: collision with root package name */
    public final int f23838j;

    /* renamed from: k, reason: collision with root package name */
    public final List<AbstractC20109p> f23839k;

    public C20072h(Executor executor, @j.P C20056e0.j jVar, @j.P CameraX.d dVar, @j.P C20056e0.l lVar, Rect rect, Matrix matrix, int i12, int i13, int i14, List list) {
        if (executor == null) {
            throw new NullPointerException("Null appExecutor");
        }
        this.f23830b = executor;
        this.f23831c = jVar;
        this.f23832d = dVar;
        this.f23833e = lVar;
        this.f23834f = rect;
        if (matrix == null) {
            throw new NullPointerException("Null sensorToBufferTransform");
        }
        this.f23835g = matrix;
        this.f23836h = i12;
        this.f23837i = i13;
        this.f23838j = i14;
        if (list == null) {
            throw new NullPointerException("Null sessionConfigCameraCaptureCallbacks");
        }
        this.f23839k = list;
    }

    @Override // androidx.camera.core.imagecapture.O
    @j.N
    public final Executor a() {
        return this.f23830b;
    }

    @Override // androidx.camera.core.imagecapture.O
    public final int b() {
        return this.f23838j;
    }

    @Override // androidx.camera.core.imagecapture.O
    @j.N
    public final Rect c() {
        return this.f23834f;
    }

    @Override // androidx.camera.core.imagecapture.O
    @j.P
    public final C20056e0.j d() {
        return this.f23831c;
    }

    @Override // androidx.camera.core.imagecapture.O
    @j.F
    public final int e() {
        return this.f23837i;
    }

    public final boolean equals(Object obj) {
        C20056e0.j jVar;
        CameraX.d dVar;
        C20056e0.l lVar;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof O)) {
            return false;
        }
        O o12 = (O) obj;
        return this.f23830b.equals(o12.a()) && ((jVar = this.f23831c) != null ? jVar.equals(o12.d()) : o12.d() == null) && ((dVar = this.f23832d) != null ? dVar.equals(o12.f()) : o12.f() == null) && ((lVar = this.f23833e) != null ? lVar.equals(o12.g()) : o12.g() == null) && this.f23834f.equals(o12.c()) && this.f23835g.equals(o12.i()) && this.f23836h == o12.h() && this.f23837i == o12.e() && this.f23838j == o12.b() && this.f23839k.equals(o12.j());
    }

    @Override // androidx.camera.core.imagecapture.O
    @j.P
    public final C20056e0.k f() {
        return this.f23832d;
    }

    @Override // androidx.camera.core.imagecapture.O
    @j.P
    public final C20056e0.l g() {
        return this.f23833e;
    }

    @Override // androidx.camera.core.imagecapture.O
    public final int h() {
        return this.f23836h;
    }

    public final int hashCode() {
        int iHashCode = (this.f23830b.hashCode() ^ 1000003) * 1000003;
        C20056e0.j jVar = this.f23831c;
        int iHashCode2 = (iHashCode ^ (jVar == null ? 0 : jVar.hashCode())) * 1000003;
        CameraX.d dVar = this.f23832d;
        int iHashCode3 = (iHashCode2 ^ (dVar == null ? 0 : dVar.hashCode())) * 1000003;
        C20056e0.l lVar = this.f23833e;
        return ((((((((((((iHashCode3 ^ (lVar != null ? lVar.hashCode() : 0)) * 1000003) ^ this.f23834f.hashCode()) * 1000003) ^ this.f23835g.hashCode()) * 1000003) ^ this.f23836h) * 1000003) ^ this.f23837i) * 1000003) ^ this.f23838j) * 1000003) ^ this.f23839k.hashCode();
    }

    @Override // androidx.camera.core.imagecapture.O
    @j.N
    public final Matrix i() {
        return this.f23835g;
    }

    @Override // androidx.camera.core.imagecapture.O
    @j.N
    public final List<AbstractC20109p> j() {
        return this.f23839k;
    }

    public final String toString() {
        return "TakePictureRequest{appExecutor=" + this.f23830b + ", inMemoryCallback=" + this.f23831c + ", onDiskCallback=" + this.f23832d + ", outputFileOptions=" + this.f23833e + ", cropRect=" + this.f23834f + ", sensorToBufferTransform=" + this.f23835g + ", rotationDegrees=" + this.f23836h + ", jpegQuality=" + this.f23837i + ", captureMode=" + this.f23838j + ", sessionConfigCameraCaptureCallbacks=" + this.f23839k + "}";
    }
}
