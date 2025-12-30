package androidx.camera.extensions.internal;

/* compiled from: AutoValue_Version.java */
/* loaded from: classes.dex */
final class b extends l {

    /* renamed from: d, reason: collision with root package name */
    public final int f24623d;

    /* renamed from: e, reason: collision with root package name */
    public final int f24624e;

    /* renamed from: f, reason: collision with root package name */
    public final int f24625f;

    /* renamed from: g, reason: collision with root package name */
    public final String f24626g;

    public b(int i12, int i13, String str, int i14) {
        this.f24623d = i12;
        this.f24624e = i13;
        this.f24625f = i14;
        if (str == null) {
            throw new NullPointerException("Null description");
        }
        this.f24626g = str;
    }

    @Override // androidx.camera.extensions.internal.l
    public final String c() {
        return this.f24626g;
    }

    @Override // androidx.camera.extensions.internal.l
    public final int d() {
        return this.f24623d;
    }

    @Override // androidx.camera.extensions.internal.l
    public final int e() {
        return this.f24624e;
    }

    @Override // androidx.camera.extensions.internal.l
    public final int f() {
        return this.f24625f;
    }
}
