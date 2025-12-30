package androidx.camera.core;

import android.graphics.Matrix;
import android.graphics.Rect;
import androidx.annotation.RestrictTo;
import androidx.camera.core.K0;

/* compiled from: AutoValue_SurfaceRequest_TransformationInfo.java */
/* renamed from: androidx.camera.core.m, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20125m extends K0.d {

    /* renamed from: a, reason: collision with root package name */
    public final Rect f24385a;

    /* renamed from: b, reason: collision with root package name */
    public final int f24386b;

    /* renamed from: c, reason: collision with root package name */
    public final int f24387c;

    /* renamed from: d, reason: collision with root package name */
    public final boolean f24388d;

    /* renamed from: e, reason: collision with root package name */
    public final Matrix f24389e;

    /* renamed from: f, reason: collision with root package name */
    public final boolean f24390f;

    public C20125m(Rect rect, int i12, int i13, boolean z12, Matrix matrix, boolean z13) {
        if (rect == null) {
            throw new NullPointerException("Null getCropRect");
        }
        this.f24385a = rect;
        this.f24386b = i12;
        this.f24387c = i13;
        this.f24388d = z12;
        if (matrix == null) {
            throw new NullPointerException("Null getSensorToBufferTransform");
        }
        this.f24389e = matrix;
        this.f24390f = z13;
    }

    @Override // androidx.camera.core.K0.d
    @j.N
    public final Rect a() {
        return this.f24385a;
    }

    @Override // androidx.camera.core.K0.d
    @RestrictTo
    public final boolean b() {
        return this.f24390f;
    }

    @Override // androidx.camera.core.K0.d
    public final int c() {
        return this.f24386b;
    }

    @Override // androidx.camera.core.K0.d
    @j.N
    @RestrictTo
    public final Matrix d() {
        return this.f24389e;
    }

    @Override // androidx.camera.core.K0.d
    @RestrictTo
    public final int e() {
        return this.f24387c;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof K0.d)) {
            return false;
        }
        K0.d dVar = (K0.d) obj;
        return this.f24385a.equals(dVar.a()) && this.f24386b == dVar.c() && this.f24387c == dVar.e() && this.f24388d == dVar.f() && this.f24389e.equals(dVar.d()) && this.f24390f == dVar.b();
    }

    @Override // androidx.camera.core.K0.d
    @RestrictTo
    public final boolean f() {
        return this.f24388d;
    }

    public final int hashCode() {
        return ((((((((((this.f24385a.hashCode() ^ 1000003) * 1000003) ^ this.f24386b) * 1000003) ^ this.f24387c) * 1000003) ^ (this.f24388d ? 1231 : 1237)) * 1000003) ^ this.f24389e.hashCode()) * 1000003) ^ (this.f24390f ? 1231 : 1237);
    }

    public final String toString() {
        StringBuilder sb2 = new StringBuilder("TransformationInfo{getCropRect=");
        sb2.append(this.f24385a);
        sb2.append(", getRotationDegrees=");
        sb2.append(this.f24386b);
        sb2.append(", getTargetRotation=");
        sb2.append(this.f24387c);
        sb2.append(", hasCameraTransform=");
        sb2.append(this.f24388d);
        sb2.append(", getSensorToBufferTransform=");
        sb2.append(this.f24389e);
        sb2.append(", getMirroring=");
        return androidx.appcompat.app.r.s("}", sb2, this.f24390f);
    }
}
