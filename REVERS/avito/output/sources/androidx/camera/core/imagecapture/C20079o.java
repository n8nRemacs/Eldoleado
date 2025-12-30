package androidx.camera.core.imagecapture;

import android.util.Size;
import androidx.camera.core.C20140q0;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.InterfaceC20128n0;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.C20094g0;
import androidx.camera.core.y0;
import j.X;
import j.k0;
import java.util.HashSet;
import java.util.Objects;

/* compiled from: CaptureNode.java */
@X
/* renamed from: androidx.camera.core.imagecapture.o, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C20079o implements androidx.camera.core.processing.o<b, c> {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final HashSet f23845a = new HashSet();

    /* renamed from: b, reason: collision with root package name */
    public B f23846b = null;

    /* renamed from: c, reason: collision with root package name */
    @j.P
    public y0 f23847c;

    /* renamed from: d, reason: collision with root package name */
    @j.P
    public C20067c f23848d;

    /* renamed from: e, reason: collision with root package name */
    @j.P
    public C20066b f23849e;

    /* compiled from: CaptureNode.java */
    /* renamed from: androidx.camera.core.imagecapture.o$a */
    public class a implements androidx.camera.core.impl.utils.futures.c<Void> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ B f23850a;

        public a(B b12) {
            this.f23850a = b12;
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@j.N Throwable th2) {
            androidx.camera.core.impl.utils.t.a();
            C20079o c20079o = C20079o.this;
            if (this.f23850a == c20079o.f23846b) {
                c20079o.f23846b = null;
            }
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final /* bridge */ /* synthetic */ void onSuccess(@j.P Void r12) {
        }
    }

    /* compiled from: CaptureNode.java */
    @VY0.c
    /* renamed from: androidx.camera.core.imagecapture.o$b */
    public static abstract class b {

        /* renamed from: a, reason: collision with root package name */
        @j.N
        public AbstractC20109p f23852a = new a();

        /* renamed from: b, reason: collision with root package name */
        @j.P
        public C20094g0 f23853b;

        /* compiled from: CaptureNode.java */
        /* renamed from: androidx.camera.core.imagecapture.o$b$a */
        public class a extends AbstractC20109p {
        }

        @j.N
        public abstract androidx.camera.core.processing.l<ImageCaptureException> a();

        @j.P
        public abstract InterfaceC20128n0 b();

        public abstract int c();

        public abstract int d();

        @j.N
        public abstract androidx.camera.core.processing.l<B> e();

        public abstract Size f();

        public abstract boolean g();
    }

    /* compiled from: CaptureNode.java */
    @VY0.c
    /* renamed from: androidx.camera.core.imagecapture.o$c */
    public static abstract class c {
        public abstract androidx.camera.core.processing.l<InterfaceC20120j0> a();

        public abstract int b();

        public abstract int c();

        public abstract androidx.camera.core.processing.l<B> d();
    }

    @j.K
    public final int a() {
        int iG2;
        androidx.camera.core.impl.utils.t.a();
        androidx.core.util.z.g("The ImageReader is not initialized.", this.f23847c != null);
        y0 y0Var = this.f23847c;
        synchronized (y0Var.f24589a) {
            iG2 = y0Var.f24592d.g() - y0Var.f24590b;
        }
        return iG2;
    }

    @k0
    @j.K
    public final void b(@j.N InterfaceC20120j0 interfaceC20120j0) {
        androidx.camera.core.impl.utils.t.a();
        if (this.f23846b == null) {
            Objects.toString(interfaceC20120j0);
            C20140q0.d(3, "CaptureNode");
            interfaceC20120j0.close();
            return;
        }
        Object obj = interfaceC20120j0.r1().b().f23987a.get(this.f23846b.f23768g);
        Objects.requireNonNull(obj);
        Integer num = (Integer) obj;
        int iIntValue = num.intValue();
        HashSet hashSet = this.f23845a;
        androidx.core.util.z.g("Received an unexpected stage id" + iIntValue, hashSet.contains(num));
        hashSet.remove(num);
        C20067c c20067c = this.f23848d;
        Objects.requireNonNull(c20067c);
        c20067c.f23817a.accept(interfaceC20120j0);
        if (hashSet.isEmpty()) {
            B b12 = this.f23846b;
            this.f23846b = null;
            D d12 = b12.f23767f;
            androidx.camera.core.impl.utils.t.a();
            if (d12.f23779g) {
                return;
            }
            d12.f23777e.b(null);
        }
    }

    @k0
    @j.K
    public final void c(@j.N B b12) {
        androidx.camera.core.impl.utils.t.a();
        androidx.core.util.z.g("Too many acquire images. Close image to be able to process next.", a() > 0);
        B b13 = this.f23846b;
        HashSet hashSet = this.f23845a;
        androidx.core.util.z.g("The previous request is not complete", b13 == null || hashSet.isEmpty());
        this.f23846b = b12;
        hashSet.addAll(b12.f23769h);
        C20067c c20067c = this.f23848d;
        Objects.requireNonNull(c20067c);
        c20067c.f23818b.accept(b12);
        androidx.camera.core.impl.utils.futures.f.a(b12.f23770i, new a(b12), androidx.camera.core.impl.utils.executor.c.a());
    }
}
