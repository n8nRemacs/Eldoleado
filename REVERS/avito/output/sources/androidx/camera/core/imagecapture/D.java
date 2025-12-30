package androidx.camera.core.imagecapture;

import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.X;

/* compiled from: RequestWithCallback.java */
@X
/* loaded from: classes.dex */
class D implements K {

    /* renamed from: a, reason: collision with root package name */
    public final O f23773a;

    /* renamed from: b, reason: collision with root package name */
    public final N f23774b;

    /* renamed from: e, reason: collision with root package name */
    public b.a<Void> f23777e;

    /* renamed from: f, reason: collision with root package name */
    public b.a<Void> f23778f;

    /* renamed from: h, reason: collision with root package name */
    @j.P
    public D0<Void> f23780h;

    /* renamed from: g, reason: collision with root package name */
    public boolean f23779g = false;

    /* renamed from: c, reason: collision with root package name */
    public final D0<Void> f23775c = androidx.concurrent.futures.b.a(new C(this, 0));

    /* renamed from: d, reason: collision with root package name */
    public final D0<Void> f23776d = androidx.concurrent.futures.b.a(new C(this, 1));

    public D(@j.N O o12, @j.N N n12) {
        this.f23773a = o12;
        this.f23774b = n12;
    }

    public final void a() {
        androidx.core.util.z.g("The callback can only complete once.", !this.f23776d.isDone());
        this.f23778f.b(null);
    }
}
