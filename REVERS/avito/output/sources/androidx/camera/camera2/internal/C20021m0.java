package androidx.camera.camera2.internal;

import androidx.camera.core.CameraControl;
import androidx.concurrent.futures.b;
import java.util.concurrent.Executor;

/* compiled from: ExposureControl.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.m0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C20021m0 {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final C20026p f23372a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final C20023n0 f23373b;

    /* renamed from: c, reason: collision with root package name */
    @j.N
    public final Executor f23374c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f23375d = false;

    /* renamed from: e, reason: collision with root package name */
    @j.P
    public b.a<Integer> f23376e;

    /* renamed from: f, reason: collision with root package name */
    @j.P
    public C20019l0 f23377f;

    public C20021m0(@j.N C20026p c20026p, @j.N androidx.camera.camera2.internal.compat.n nVar, @j.N Executor executor) {
        this.f23372a = c20026p;
        this.f23373b = new C20023n0(nVar);
        this.f23374c = executor;
    }

    public final void a() {
        b.a<Integer> aVar = this.f23376e;
        if (aVar != null) {
            aVar.d(new CameraControl.OperationCanceledException("Cancelled by another setExposureCompensationIndex()"));
            this.f23376e = null;
        }
        C20019l0 c20019l0 = this.f23377f;
        if (c20019l0 != null) {
            this.f23372a.f23388b.f23414a.remove(c20019l0);
            this.f23377f = null;
        }
    }
}
