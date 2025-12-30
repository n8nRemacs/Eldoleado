package androidx.camera.core.impl;

import androidx.camera.core.impl.S;
import java.util.List;

/* compiled from: AutoValue_EncoderProfilesProxy_ImmutableEncoderProfilesProxy.java */
/* renamed from: androidx.camera.core.impl.g, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20093g extends S.b {

    /* renamed from: a, reason: collision with root package name */
    public final int f24109a;

    /* renamed from: b, reason: collision with root package name */
    public final int f24110b;

    /* renamed from: c, reason: collision with root package name */
    public final List<S.a> f24111c;

    /* renamed from: d, reason: collision with root package name */
    public final List<S.c> f24112d;

    public C20093g(int i12, int i13, List<S.a> list, List<S.c> list2) {
        this.f24109a = i12;
        this.f24110b = i13;
        if (list == null) {
            throw new NullPointerException("Null audioProfiles");
        }
        this.f24111c = list;
        if (list2 == null) {
            throw new NullPointerException("Null videoProfiles");
        }
        this.f24112d = list2;
    }

    @Override // androidx.camera.core.impl.S
    public final int a() {
        return this.f24110b;
    }

    @Override // androidx.camera.core.impl.S
    @j.N
    public final List<S.a> b() {
        return this.f24111c;
    }

    @Override // androidx.camera.core.impl.S
    public final int c() {
        return this.f24109a;
    }

    @Override // androidx.camera.core.impl.S
    @j.N
    public final List<S.c> d() {
        return this.f24112d;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof S.b)) {
            return false;
        }
        S.b bVar = (S.b) obj;
        if (this.f24109a == ((C20093g) bVar).f24109a) {
            C20093g c20093g = (C20093g) bVar;
            if (this.f24110b == c20093g.f24110b && this.f24111c.equals(c20093g.f24111c) && this.f24112d.equals(c20093g.f24112d)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((((((this.f24109a ^ 1000003) * 1000003) ^ this.f24110b) * 1000003) ^ this.f24111c.hashCode()) * 1000003) ^ this.f24112d.hashCode();
    }

    public final String toString() {
        return "ImmutableEncoderProfilesProxy{defaultDurationSeconds=" + this.f24109a + ", recommendedFileFormat=" + this.f24110b + ", audioProfiles=" + this.f24111c + ", videoProfiles=" + this.f24112d + "}";
    }
}
