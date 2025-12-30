package androidx.camera.core.imagecapture;

import android.util.Size;
import android.view.Surface;
import androidx.camera.camera2.internal.RunnableC20041x;
import androidx.camera.core.AbstractC20131p;
import androidx.camera.core.C20130o0;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.InterfaceC20128n0;
import androidx.camera.core.imagecapture.A;
import androidx.camera.core.impl.C20094g0;
import androidx.camera.core.impl.H;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.Z;
import androidx.camera.core.r0;
import androidx.camera.core.y0;
import androidx.core.util.InterfaceC22791e;
import j.X;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: ImagePipeline.java */
@X
/* loaded from: classes.dex */
public class r {

    /* renamed from: g, reason: collision with root package name */
    public static final androidx.camera.core.internal.compat.workaround.b f23855g = new androidx.camera.core.internal.compat.workaround.b();

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final Z f23856a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final androidx.camera.core.impl.H f23857b;

    /* renamed from: c, reason: collision with root package name */
    @j.N
    public final C20079o f23858c;

    /* renamed from: d, reason: collision with root package name */
    @j.N
    public final J f23859d;

    /* renamed from: e, reason: collision with root package name */
    @j.N
    public final A f23860e;

    /* renamed from: f, reason: collision with root package name */
    @j.N
    public final C20066b f23861f;

    /* JADX WARN: Multi-variable type inference failed */
    @j.K
    public r(@j.N Z z12, @j.N Size size, @j.P AbstractC20131p abstractC20131p, boolean z13) {
        InterfaceC22791e interfaceC22791e;
        x xVar;
        androidx.camera.core.impl.utils.t.a();
        this.f23856a = z12;
        H.b bVar = (H.b) z12.g(L0.f24000t, null);
        if (bVar == null) {
            throw new IllegalStateException("Implementation is missing option unpacker for " + z12.j(z12.toString()));
        }
        H.a aVar = new H.a();
        bVar.a(z12, aVar);
        this.f23857b = aVar.e();
        final C20079o c20079o = new C20079o();
        this.f23858c = c20079o;
        final J j12 = new J();
        this.f23859d = j12;
        Executor executor = (Executor) z12.g(androidx.camera.core.internal.f.f24350A, androidx.camera.core.impl.utils.executor.c.c());
        Objects.requireNonNull(executor);
        Executor executor2 = executor;
        if (abstractC20131p != null) {
            androidx.core.util.z.b(false);
            throw null;
        }
        final A a12 = new A(executor2);
        this.f23860e = a12;
        int inputFormat = z12.getInputFormat();
        Integer num = (Integer) z12.g(Z.f24073J, null);
        C20066b c20066b = new C20066b(size, inputFormat, num != null ? num.intValue() : 256, z13, (InterfaceC20128n0) z12.g(Z.f24074K, null), new androidx.camera.core.processing.l(), new androidx.camera.core.processing.l());
        this.f23861f = c20066b;
        androidx.core.util.z.g("CaptureNode does not support recreation yet.", c20079o.f23849e == null && c20079o.f23847c == null);
        c20079o.f23849e = c20066b;
        InterfaceC20128n0 interfaceC20128n0 = c20066b.f23814g;
        Size size2 = c20066b.f23810c;
        int i12 = c20066b.f23811d;
        if (c20066b.f23813f || interfaceC20128n0 != null) {
            final x xVar2 = new x(interfaceC20128n0 != null ? interfaceC20128n0.newInstance() : C20130o0.a(size2.getWidth(), size2.getHeight(), i12, 4));
            interfaceC22791e = new InterfaceC22791e() { // from class: androidx.camera.core.imagecapture.n
                @Override // androidx.core.util.InterfaceC22791e
                public final void accept(Object obj) {
                    B b12 = (B) obj;
                    c20079o.c(b12);
                    x xVar3 = xVar2;
                    androidx.core.util.z.g("Pending request should be null", xVar3.f23865b == null);
                    xVar3.f23865b = b12;
                }
            };
            xVar = xVar2;
        } else {
            r0 r0Var = new r0(size2.getWidth(), size2.getHeight(), i12, 4);
            c20066b.f23852a = r0Var.f24519b;
            final int i13 = 0;
            interfaceC22791e = new InterfaceC22791e() { // from class: androidx.camera.core.imagecapture.m
                @Override // androidx.core.util.InterfaceC22791e
                public final void accept(Object obj) {
                    boolean z14;
                    switch (i13) {
                        case 0:
                            ((C20079o) c20079o).c((B) obj);
                            break;
                        case 1:
                            C20079o c20079o2 = (C20079o) c20079o;
                            ImageCaptureException imageCaptureException = (ImageCaptureException) obj;
                            androidx.camera.core.impl.utils.t.a();
                            B b12 = c20079o2.f23846b;
                            if (b12 != null) {
                                D d12 = b12.f23767f;
                                androidx.camera.core.impl.utils.t.a();
                                if (!d12.f23779g) {
                                    O o12 = d12.f23773a;
                                    androidx.camera.core.impl.utils.t.a();
                                    int i14 = o12.f23807a;
                                    if (i14 > 0) {
                                        z14 = true;
                                        o12.f23807a = i14 - 1;
                                    } else {
                                        z14 = false;
                                    }
                                    if (!z14) {
                                        androidx.camera.core.impl.utils.t.a();
                                        o12.a().execute(new z(5, o12, imageCaptureException));
                                    }
                                    d12.a();
                                    d12.f23777e.d(imageCaptureException);
                                    if (z14) {
                                        d12.f23774b.d(o12);
                                        break;
                                    }
                                }
                            }
                            break;
                        default:
                            A.b bVar2 = (A.b) obj;
                            A a13 = (A) c20079o;
                            if (!bVar2.b().f23767f.f23779g) {
                                a13.f23751a.execute(new z(0, a13, bVar2));
                                break;
                            }
                            break;
                    }
                }
            };
            xVar = r0Var;
        }
        Surface surface = xVar.getSurface();
        Objects.requireNonNull(surface);
        androidx.core.util.z.g("The surface is already set.", c20066b.f23853b == null);
        c20066b.f23853b = new C20094g0(surface, size2, i12);
        c20079o.f23847c = new y0(xVar);
        xVar.d(new C(c20079o, 2), androidx.camera.core.impl.utils.executor.c.d());
        c20066b.f23815h.f24454b = interfaceC22791e;
        final int i14 = 1;
        c20066b.f23816i.f24454b = new InterfaceC22791e() { // from class: androidx.camera.core.imagecapture.m
            @Override // androidx.core.util.InterfaceC22791e
            public final void accept(Object obj) {
                boolean z14;
                switch (i14) {
                    case 0:
                        ((C20079o) c20079o).c((B) obj);
                        break;
                    case 1:
                        C20079o c20079o2 = (C20079o) c20079o;
                        ImageCaptureException imageCaptureException = (ImageCaptureException) obj;
                        androidx.camera.core.impl.utils.t.a();
                        B b12 = c20079o2.f23846b;
                        if (b12 != null) {
                            D d12 = b12.f23767f;
                            androidx.camera.core.impl.utils.t.a();
                            if (!d12.f23779g) {
                                O o12 = d12.f23773a;
                                androidx.camera.core.impl.utils.t.a();
                                int i142 = o12.f23807a;
                                if (i142 > 0) {
                                    z14 = true;
                                    o12.f23807a = i142 - 1;
                                } else {
                                    z14 = false;
                                }
                                if (!z14) {
                                    androidx.camera.core.impl.utils.t.a();
                                    o12.a().execute(new z(5, o12, imageCaptureException));
                                }
                                d12.a();
                                d12.f23777e.d(imageCaptureException);
                                if (z14) {
                                    d12.f23774b.d(o12);
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        A.b bVar2 = (A.b) obj;
                        A a13 = (A) c20079o;
                        if (!bVar2.b().f23767f.f23779g) {
                            a13.f23751a.execute(new z(0, a13, bVar2));
                            break;
                        }
                        break;
                }
            }
        };
        C20067c c20067c = new C20067c(new androidx.camera.core.processing.l(), new androidx.camera.core.processing.l(), i12, c20066b.f23812e);
        c20079o.f23848d = c20067c;
        final int i15 = 0;
        c20067c.f23817a.f24454b = new InterfaceC22791e() { // from class: androidx.camera.core.imagecapture.H
            @Override // androidx.core.util.InterfaceC22791e
            public final void accept(Object obj) {
                switch (i15) {
                    case 0:
                        InterfaceC20120j0 interfaceC20120j0 = (InterfaceC20120j0) obj;
                        J j13 = j12;
                        androidx.camera.core.impl.utils.t.a();
                        androidx.core.util.z.g(null, j13.f23795a != null);
                        Object obj2 = interfaceC20120j0.r1().b().f23987a.get(j13.f23795a.f23768g);
                        Objects.requireNonNull(obj2);
                        androidx.core.util.z.g(null, ((Integer) obj2).intValue() == ((Integer) j13.f23795a.f23769h.get(0)).intValue());
                        j13.f23796b.f23825a.accept(new C20071g(j13.f23795a, interfaceC20120j0));
                        j13.f23795a = null;
                        break;
                    default:
                        B b12 = (B) obj;
                        J j14 = j12;
                        androidx.camera.core.impl.utils.t.a();
                        androidx.core.util.z.g("Cannot handle multi-image capture.", b12.f23769h.size() == 1);
                        androidx.core.util.z.g("Already has an existing request.", j14.f23795a == null);
                        j14.f23795a = b12;
                        androidx.camera.core.impl.utils.futures.f.a(b12.f23770i, new I(j14, b12), androidx.camera.core.impl.utils.executor.c.a());
                        break;
                }
            }
        };
        final int i16 = 1;
        c20067c.f23818b.f24454b = new InterfaceC22791e() { // from class: androidx.camera.core.imagecapture.H
            @Override // androidx.core.util.InterfaceC22791e
            public final void accept(Object obj) {
                switch (i16) {
                    case 0:
                        InterfaceC20120j0 interfaceC20120j0 = (InterfaceC20120j0) obj;
                        J j13 = j12;
                        androidx.camera.core.impl.utils.t.a();
                        androidx.core.util.z.g(null, j13.f23795a != null);
                        Object obj2 = interfaceC20120j0.r1().b().f23987a.get(j13.f23795a.f23768g);
                        Objects.requireNonNull(obj2);
                        androidx.core.util.z.g(null, ((Integer) obj2).intValue() == ((Integer) j13.f23795a.f23769h.get(0)).intValue());
                        j13.f23796b.f23825a.accept(new C20071g(j13.f23795a, interfaceC20120j0));
                        j13.f23795a = null;
                        break;
                    default:
                        B b12 = (B) obj;
                        J j14 = j12;
                        androidx.camera.core.impl.utils.t.a();
                        androidx.core.util.z.g("Cannot handle multi-image capture.", b12.f23769h.size() == 1);
                        androidx.core.util.z.g("Already has an existing request.", j14.f23795a == null);
                        j14.f23795a = b12;
                        androidx.camera.core.impl.utils.futures.f.a(b12.f23770i, new I(j14, b12), androidx.camera.core.impl.utils.executor.c.a());
                        break;
                }
            }
        };
        C20070f c20070f = new C20070f(new androidx.camera.core.processing.l(), c20067c.f23819c, c20067c.f23820d);
        j12.f23796b = c20070f;
        a12.f23752b = c20070f;
        final int i17 = 2;
        c20070f.f23825a.f24454b = new InterfaceC22791e() { // from class: androidx.camera.core.imagecapture.m
            @Override // androidx.core.util.InterfaceC22791e
            public final void accept(Object obj) {
                boolean z14;
                switch (i17) {
                    case 0:
                        ((C20079o) a12).c((B) obj);
                        break;
                    case 1:
                        C20079o c20079o2 = (C20079o) a12;
                        ImageCaptureException imageCaptureException = (ImageCaptureException) obj;
                        androidx.camera.core.impl.utils.t.a();
                        B b12 = c20079o2.f23846b;
                        if (b12 != null) {
                            D d12 = b12.f23767f;
                            androidx.camera.core.impl.utils.t.a();
                            if (!d12.f23779g) {
                                O o12 = d12.f23773a;
                                androidx.camera.core.impl.utils.t.a();
                                int i142 = o12.f23807a;
                                if (i142 > 0) {
                                    z14 = true;
                                    o12.f23807a = i142 - 1;
                                } else {
                                    z14 = false;
                                }
                                if (!z14) {
                                    androidx.camera.core.impl.utils.t.a();
                                    o12.a().execute(new z(5, o12, imageCaptureException));
                                }
                                d12.a();
                                d12.f23777e.d(imageCaptureException);
                                if (z14) {
                                    d12.f23774b.d(o12);
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        A.b bVar2 = (A.b) obj;
                        A a13 = (A) a12;
                        if (!bVar2.b().f23767f.f23779g) {
                            a13.f23751a.execute(new z(0, a13, bVar2));
                            break;
                        }
                        break;
                }
            }
        };
        a12.f23753c = new y();
        a12.f23754d = new C20080p(a12.f23760j);
        a12.f23757g = new s();
        a12.f23755e = new C20073i();
        a12.f23756f = new t();
        a12.f23758h = new v();
        if (c20070f.f23826b == 35 || a12.f23761k) {
            a12.f23759i = new u();
        }
    }

    @j.K
    public final void a() {
        androidx.camera.core.impl.utils.t.a();
        C20079o c20079o = this.f23858c;
        c20079o.getClass();
        androidx.camera.core.impl.utils.t.a();
        C20066b c20066b = c20079o.f23849e;
        Objects.requireNonNull(c20066b);
        y0 y0Var = c20079o.f23847c;
        Objects.requireNonNull(y0Var);
        C20094g0 c20094g0 = c20066b.f23853b;
        Objects.requireNonNull(c20094g0);
        c20094g0.a();
        C20094g0 c20094g02 = c20066b.f23853b;
        Objects.requireNonNull(c20094g02);
        androidx.camera.core.impl.utils.futures.f.h(c20094g02.f23933e).N(new RunnableC20041x(y0Var, 8), androidx.camera.core.impl.utils.executor.c.d());
        this.f23859d.getClass();
        this.f23860e.getClass();
    }

    @j.N
    public final SessionConfig.b b(@j.N Size size) {
        SessionConfig.b bVarM = SessionConfig.b.m(this.f23856a, size);
        C20094g0 c20094g0 = this.f23861f.f23853b;
        Objects.requireNonNull(c20094g0);
        bVarM.f(c20094g0, androidx.camera.core.F.f23562d);
        return bVarM;
    }
}
