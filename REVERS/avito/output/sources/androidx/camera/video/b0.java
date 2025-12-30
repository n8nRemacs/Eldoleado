package androidx.camera.video;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaCodec;
import android.util.Range;
import android.util.Size;
import androidx.annotation.RestrictTo;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.camera.core.N0;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.G0;
import androidx.camera.core.impl.InterfaceC20086c0;
import androidx.camera.core.impl.InterfaceC20088d0;
import androidx.camera.core.impl.InterfaceC20106m0;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.S;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.Timebase;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.q0;
import androidx.camera.core.impl.s0;
import androidx.camera.core.impl.w0;
import androidx.camera.core.internal.j;
import androidx.camera.core.processing.C;
import androidx.camera.core.processing.z;
import androidx.camera.video.C20181s;
import androidx.camera.video.VideoOutput;
import androidx.camera.video.W;
import androidx.camera.video.b0;
import com.google.common.util.concurrent.D0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import q.InterfaceC47174a;

/* compiled from: VideoCapture.java */
@j.X
/* loaded from: classes.dex */
public final class b0<T extends VideoOutput> extends N0 {

    /* renamed from: A, reason: collision with root package name */
    public static final c f24861A = new c();

    /* renamed from: B, reason: collision with root package name */
    @j.k0
    public static final boolean f24862B;

    /* renamed from: C, reason: collision with root package name */
    public static final boolean f24863C;

    /* renamed from: n, reason: collision with root package name */
    public DeferrableSurface f24864n;

    /* renamed from: o, reason: collision with root package name */
    @j.P
    public androidx.camera.core.processing.z f24865o;

    /* renamed from: p, reason: collision with root package name */
    public W f24866p;

    /* renamed from: q, reason: collision with root package name */
    @j.N
    public SessionConfig.b f24867q;

    /* renamed from: r, reason: collision with root package name */
    public D0<Void> f24868r;

    /* renamed from: s, reason: collision with root package name */
    public K0 f24869s;

    /* renamed from: t, reason: collision with root package name */
    public VideoOutput.SourceState f24870t;

    /* renamed from: u, reason: collision with root package name */
    @j.P
    public androidx.camera.core.processing.C f24871u;

    /* renamed from: v, reason: collision with root package name */
    @j.P
    public androidx.camera.video.internal.encoder.L f24872v;

    /* renamed from: w, reason: collision with root package name */
    @j.P
    public Rect f24873w;

    /* renamed from: x, reason: collision with root package name */
    public int f24874x;

    /* renamed from: y, reason: collision with root package name */
    public boolean f24875y;

    /* renamed from: z, reason: collision with root package name */
    public final q0.a<W> f24876z;

    /* compiled from: VideoCapture.java */
    public class a implements q0.a<W> {
        public a() {
        }

        @Override // androidx.camera.core.impl.q0.a
        public final void a(@j.P W w12) throws ExecutionException, InterruptedException {
            W w13 = w12;
            if (w13 == null) {
                throw new IllegalArgumentException("StreamInfo can't be null");
            }
            b0 b0Var = b0.this;
            if (b0Var.f24870t == VideoOutput.SourceState.f24839d) {
                return;
            }
            Objects.toString(b0Var.f24866p);
            w13.toString();
            C20140q0.d(3, "VideoCapture");
            W w14 = b0Var.f24866p;
            b0Var.f24866p = w13;
            G0 g02 = b0Var.f23621g;
            g02.getClass();
            int iA2 = w14.a();
            int iA3 = w13.a();
            Set<Integer> set = W.f24842b;
            if ((!set.contains(Integer.valueOf(iA2)) && !set.contains(Integer.valueOf(iA3)) && iA2 != iA3) || (b0Var.f24875y && w14.b() != null && w13.b() == null)) {
                String strE = b0Var.e();
                androidx.camera.video.impl.a<T> aVar = (androidx.camera.video.impl.a) b0Var.f23620f;
                G0 g03 = b0Var.f23621g;
                g03.getClass();
                b0Var.K(strE, aVar, g03);
                return;
            }
            if ((w14.a() != -1 && w13.a() == -1) || (w14.a() == -1 && w13.a() != -1)) {
                b0Var.G(b0Var.f24867q, w13, g02);
                b0Var.D(b0Var.f24867q.k());
                b0Var.p();
            } else if (w14.c() != w13.c()) {
                b0Var.G(b0Var.f24867q, w13, g02);
                b0Var.D(b0Var.f24867q.k());
                Iterator it = b0Var.f23615a.iterator();
                while (it.hasNext()) {
                    ((N0.d) it.next()).b(b0Var);
                }
            }
        }

        @Override // androidx.camera.core.impl.q0.a
        public final void onError(@j.N Throwable th2) {
            C20140q0.d(5, "VideoCapture");
        }
    }

    /* compiled from: VideoCapture.java */
    @RestrictTo
    public static final class c implements androidx.camera.core.impl.J<androidx.camera.video.impl.a<?>> {

        /* renamed from: a, reason: collision with root package name */
        public static final androidx.camera.video.impl.a<?> f24879a;

        /* renamed from: b, reason: collision with root package name */
        public static final Range<Integer> f24880b;

        /* renamed from: c, reason: collision with root package name */
        public static final androidx.camera.core.F f24881c;

        static {
            e0 e0Var = new e0();
            androidx.camera.core.processing.j jVar = new androidx.camera.core.processing.j(1);
            f24880b = new Range<>(30, 30);
            androidx.camera.core.F f12 = androidx.camera.core.F.f23562d;
            f24881c = f12;
            b bVar = new b(e0Var);
            Config.a<Integer> aVar = L0.f24001u;
            androidx.camera.core.impl.n0 n0Var = bVar.f24878a;
            n0Var.K(aVar, 5);
            n0Var.K(androidx.camera.video.impl.a.f24940H, jVar);
            n0Var.K(InterfaceC20086c0.f24089f, f12);
            n0Var.K(L0.f24006z, UseCaseConfigFactory.CaptureType.f24057e);
            f24879a = new androidx.camera.video.impl.a<>(s0.Q(n0Var));
        }
    }

    static {
        boolean z12;
        w0 w0Var = androidx.camera.video.internal.compat.quirk.e.f25058a;
        boolean z13 = w0Var.b(androidx.camera.video.internal.compat.quirk.p.class) != null;
        boolean z14 = w0Var.b(androidx.camera.video.internal.compat.quirk.o.class) != null;
        boolean z15 = w0Var.b(androidx.camera.video.internal.compat.quirk.j.class) != null;
        Iterator it = w0Var.c(androidx.camera.video.internal.compat.quirk.t.class).iterator();
        while (true) {
            if (!it.hasNext()) {
                z12 = false;
                break;
            } else if (((androidx.camera.video.internal.compat.quirk.t) it.next()).a()) {
                z12 = true;
                break;
            }
        }
        boolean z16 = androidx.camera.video.internal.compat.quirk.e.f25058a.b(androidx.camera.video.internal.compat.quirk.i.class) != null;
        f24863C = z13 || z14 || z15;
        f24862B = z14 || z15 || z12 || z16;
    }

    public b0(@j.N androidx.camera.video.impl.a<T> aVar) {
        super(aVar);
        this.f24866p = W.f24841a;
        this.f24867q = new SessionConfig.b();
        this.f24868r = null;
        this.f24870t = VideoOutput.SourceState.f24839d;
        this.f24875y = false;
        this.f24876z = new a();
    }

    public static void E(@j.N HashSet hashSet, int i12, int i13, @j.N Size size, @j.N androidx.camera.video.internal.encoder.L l12) {
        if (i12 > size.getWidth() || i13 > size.getHeight()) {
            return;
        }
        try {
            hashSet.add(new Size(i12, ((Integer) l12.d(i12).clamp(Integer.valueOf(i13))).intValue()));
        } catch (IllegalArgumentException unused) {
            C20140q0.d(5, "VideoCapture");
        }
        try {
            hashSet.add(new Size(((Integer) l12.a(i13).clamp(Integer.valueOf(i12))).intValue(), i13));
        } catch (IllegalArgumentException unused2) {
            C20140q0.d(5, "VideoCapture");
        }
    }

    public static int F(boolean z12, int i12, int i13, @j.N Range<Integer> range) {
        int i14 = i12 % i13;
        if (i14 != 0) {
            i12 = z12 ? i12 - i14 : i12 + (i13 - i14);
        }
        return ((Integer) range.clamp(Integer.valueOf(i12))).intValue();
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void B(@j.N Rect rect) {
        this.f23623i = rect;
        L();
    }

    @j.K
    public final void G(@j.N SessionConfig.b bVar, @j.N W w12, @j.N G0 g02) {
        boolean z12 = w12.a() == -1;
        boolean z13 = w12.c() == W.a.f24844b;
        if (z12 && z13) {
            throw new IllegalStateException("Unexpected stream state, stream is error but active");
        }
        bVar.l();
        androidx.camera.core.F fB2 = g02.b();
        if (!z12) {
            if (z13) {
                bVar.i(this.f24864n, fB2);
            } else {
                bVar.f(this.f24864n, fB2);
            }
        }
        D0<Void> d02 = this.f24868r;
        if (d02 != null && d02.cancel(false)) {
            C20140q0.d(3, "VideoCapture");
        }
        D0<Void> d0A = androidx.concurrent.futures.b.a(new J(2, this, bVar));
        this.f24868r = d0A;
        androidx.camera.core.impl.utils.futures.f.a(d0A, new d0(this, d0A, z13), androidx.camera.core.impl.utils.executor.c.d());
    }

    @j.K
    public final void H() {
        androidx.camera.core.impl.utils.t.a();
        DeferrableSurface deferrableSurface = this.f24864n;
        if (deferrableSurface != null) {
            deferrableSurface.a();
            this.f24864n = null;
        }
        androidx.camera.core.processing.C c12 = this.f24871u;
        if (c12 != null) {
            c12.b();
            this.f24871u = null;
        }
        androidx.camera.core.processing.z zVar = this.f24865o;
        if (zVar != null) {
            androidx.camera.core.impl.utils.t.a();
            zVar.d();
            zVar.f24509o = true;
            this.f24865o = null;
        }
        this.f24872v = null;
        this.f24873w = null;
        this.f24869s = null;
        this.f24866p = W.f24841a;
        this.f24874x = 0;
        this.f24875y = false;
    }

    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3, types: [java.lang.String, java.lang.Throwable] */
    @j.N
    @j.K
    @SuppressLint({"WrongConstant"})
    public final SessionConfig.b I(@j.N final String str, @j.N final androidx.camera.video.impl.a<T> aVar, @j.N final G0 g02) throws ExecutionException, InterruptedException {
        AbstractC20184v abstractC20184v;
        String str2;
        androidx.camera.core.F f12;
        androidx.camera.video.internal.encoder.L dVar;
        ?? r112;
        boolean z12;
        Rect rect;
        Size size;
        androidx.camera.core.processing.C c12;
        androidx.camera.core.impl.utils.t.a();
        final CameraInternal cameraInternalC = c();
        cameraInternalC.getClass();
        Size sizeE = g02.e();
        f0 f0Var = new f0(this, 2);
        Range<Integer> rangeC = g02.c();
        if (Objects.equals(rangeC, G0.f23964a)) {
            rangeC = c.f24880b;
        }
        D0<AbstractC20184v> d0C = J().c().c();
        if (d0C.isDone()) {
            try {
                abstractC20184v = d0C.get();
            } catch (InterruptedException | ExecutionException e12) {
                throw new IllegalStateException(e12);
            }
        } else {
            abstractC20184v = null;
        }
        AbstractC20184v abstractC20184v2 = abstractC20184v;
        Objects.requireNonNull(abstractC20184v2);
        X xF2 = J().f(cameraInternalC.getCameraInfo());
        androidx.camera.core.F fB2 = g02.b();
        InterfaceC47174a interfaceC47174a = (InterfaceC47174a) aVar.f(androidx.camera.video.impl.a.f24940H);
        Objects.requireNonNull(interfaceC47174a);
        androidx.camera.video.internal.encoder.L l12 = this.f24872v;
        Timebase timebaseI = Timebase.f24048b;
        if (l12 != null) {
            dVar = l12;
            str2 = "VideoCapture";
            f12 = fB2;
        } else {
            androidx.camera.video.internal.f fVarC = xF2.c(sizeE, fB2);
            str2 = "VideoCapture";
            f12 = fB2;
            androidx.camera.video.internal.encoder.L l13 = (androidx.camera.video.internal.encoder.L) interfaceC47174a.apply(androidx.camera.video.internal.config.k.b(androidx.camera.video.internal.config.k.c(abstractC20184v2, fB2, fVarC), timebaseI, abstractC20184v2.d(), sizeE, fB2, rangeC));
            if (l13 == null) {
                C20140q0.d(5, str2);
                dVar = null;
            } else {
                Size size2 = fVarC != null ? new Size(fVarC.g().k(), fVarC.g().h()) : null;
                if (l13 instanceof androidx.camera.video.internal.workaround.d) {
                    dVar = l13;
                    this.f24872v = dVar;
                } else {
                    if (androidx.camera.video.internal.compat.quirk.e.f25058a.b(androidx.camera.video.internal.compat.quirk.k.class) == null) {
                        if (size2 != null && !l13.c(size2.getWidth(), size2.getHeight())) {
                            Range<Integer> rangeE = l13.e();
                            Range<Integer> rangeF = l13.f();
                            size2.toString();
                            Objects.toString(rangeE);
                            Objects.toString(rangeF);
                            C20140q0.d(5, "VideoEncoderInfoWrapper");
                        }
                        dVar = l13;
                        this.f24872v = dVar;
                    }
                    dVar = new androidx.camera.video.internal.workaround.d(l13, size2);
                    this.f24872v = dVar;
                }
            }
        }
        int iH2 = h(cameraInternalC, m(cameraInternalC));
        if (M()) {
            int iC2 = iH2 - this.f24866p.b().c();
            RectF rectF = androidx.camera.core.impl.utils.u.f24303a;
            iH2 = ((iC2 % 360) + 360) % 360;
        }
        this.f24874x = iH2;
        final Rect rect2 = this.f23623i;
        if (rect2 == null) {
            rect2 = new Rect(0, 0, sizeE.getWidth(), sizeE.getHeight());
        }
        if (dVar == null || dVar.c(rect2.width(), rect2.height())) {
            r112 = 0;
        } else {
            String.format("Adjust cropRect %s by width/height alignment %d/%d and supported widths %s / supported heights %s", androidx.camera.core.impl.utils.u.e(rect2), Integer.valueOf(dVar.h()), Integer.valueOf(dVar.g()), dVar.e(), dVar.f());
            C20140q0.d(3, str2);
            int iH3 = dVar.h();
            int iG2 = dVar.g();
            Range<Integer> rangeE2 = dVar.e();
            Range<Integer> rangeF2 = dVar.f();
            int iF2 = F(true, rect2.width(), iH3, rangeE2);
            int iF3 = F(false, rect2.width(), iH3, rangeE2);
            int iF4 = F(true, rect2.height(), iG2, rangeF2);
            int iF5 = F(false, rect2.height(), iG2, rangeF2);
            HashSet hashSet = new HashSet();
            E(hashSet, iF2, iF4, sizeE, dVar);
            E(hashSet, iF2, iF5, sizeE, dVar);
            E(hashSet, iF3, iF4, sizeE, dVar);
            E(hashSet, iF3, iF5, sizeE, dVar);
            if (hashSet.isEmpty()) {
                C20140q0.d(5, str2);
            } else {
                ArrayList arrayList = new ArrayList(hashSet);
                arrayList.toString();
                C20140q0.d(3, str2);
                Collections.sort(arrayList, new Comparator() { // from class: androidx.camera.video.a0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        Size size3 = (Size) obj;
                        Size size4 = (Size) obj2;
                        b0.c cVar = b0.f24861A;
                        int width = size3.getWidth();
                        Rect rect3 = rect2;
                        return (Math.abs(size3.getHeight() - rect3.height()) + Math.abs(width - rect3.width())) - (Math.abs(size4.getHeight() - rect3.height()) + Math.abs(size4.getWidth() - rect3.width()));
                    }
                });
                arrayList.toString();
                C20140q0.d(3, str2);
                Size size3 = (Size) arrayList.get(0);
                int width = size3.getWidth();
                int height = size3.getHeight();
                if (width == rect2.width() && height == rect2.height()) {
                    C20140q0.d(3, str2);
                } else {
                    r112 = 0;
                    androidx.core.util.z.g(null, width % 2 == 0 && height % 2 == 0 && width <= sizeE.getWidth() && height <= sizeE.getHeight());
                    Rect rect3 = new Rect(rect2);
                    if (width != rect2.width()) {
                        int iMax = Math.max(0, rect2.centerX() - (width / 2));
                        rect3.left = iMax;
                        int i12 = iMax + width;
                        rect3.right = i12;
                        if (i12 > sizeE.getWidth()) {
                            int width2 = sizeE.getWidth();
                            rect3.right = width2;
                            rect3.left = width2 - width;
                        }
                    }
                    if (height != rect2.height()) {
                        int iMax2 = Math.max(0, rect2.centerY() - (height / 2));
                        rect3.top = iMax2;
                        int i13 = iMax2 + height;
                        rect3.bottom = i13;
                        if (i13 > sizeE.getHeight()) {
                            int height2 = sizeE.getHeight();
                            rect3.bottom = height2;
                            rect3.top = height2 - height;
                        }
                    }
                    androidx.camera.core.impl.utils.u.e(rect2);
                    androidx.camera.core.impl.utils.u.e(rect3);
                    C20140q0.d(3, str2);
                    rect2 = rect3;
                }
            }
            r112 = 0;
        }
        int i14 = this.f24874x;
        if (M()) {
            K0.d dVarB = this.f24866p.b();
            dVarB.getClass();
            Rect rectA = dVarB.a();
            RectF rectF2 = androidx.camera.core.impl.utils.u.f24303a;
            Size sizeF = androidx.camera.core.impl.utils.u.f(new Size(rectA.width(), rectA.height()), i14);
            z12 = false;
            rect = new Rect(0, 0, sizeF.getWidth(), sizeF.getHeight());
        } else {
            z12 = false;
            rect = rect2;
        }
        this.f24873w = rect;
        if (!M() || rect.equals(rect2)) {
            size = sizeE;
        } else {
            float fHeight = rect.height() / rect2.height();
            size = new Size((int) Math.ceil(sizeE.getWidth() * fHeight), (int) Math.ceil(sizeE.getHeight() * fHeight));
        }
        if (M()) {
            this.f24875y = true;
        }
        Rect rect4 = this.f24873w;
        if (this.f23626l != null || ((cameraInternalC.j() && f24862B) || sizeE.getWidth() != rect4.width() || sizeE.getHeight() != rect4.height() || ((cameraInternalC.j() && m(cameraInternalC)) || M()))) {
            C20140q0.d(3, str2);
            CameraInternal cameraInternalC2 = c();
            Objects.requireNonNull(cameraInternalC2);
            if (this.f23626l != null) {
                throw r112;
            }
            c12 = new androidx.camera.core.processing.C(cameraInternalC2, new androidx.camera.core.processing.k(f12));
        } else {
            c12 = r112;
        }
        this.f24871u = c12;
        if (c12 != null || !cameraInternalC.j()) {
            timebaseI = cameraInternalC.c().i();
        }
        final Timebase timebase = timebaseI;
        Objects.toString(cameraInternalC.c().i());
        Objects.toString(timebase);
        C20140q0.d(3, str2);
        G0.a aVarF = g02.f();
        aVarF.e(size);
        aVarF.c(rangeC);
        G0 g0A = aVarF.a();
        androidx.core.util.z.g(r112, this.f24865o == null ? true : z12);
        androidx.camera.core.processing.z zVar = new androidx.camera.core.processing.z(2, 34, g0A, this.f23624j, cameraInternalC.j(), this.f24873w, this.f24874x, ((InterfaceC20088d0) this.f23620f).s(), (cameraInternalC.j() && m(cameraInternalC)) ? true : z12);
        this.f24865o = zVar;
        zVar.a(f0Var);
        if (this.f24871u != null) {
            androidx.camera.core.processing.z zVar2 = this.f24865o;
            int i15 = zVar2.f24500f;
            int i16 = zVar2.f24503i;
            RectF rectF3 = androidx.camera.core.impl.utils.u.f24303a;
            Rect rect5 = zVar2.f24498d;
            C.d dVarH = C.d.h(i15, zVar2.f24495a, rect5, androidx.camera.core.impl.utils.u.f(new Size(rect5.width(), rect5.height()), i16), zVar2.f24503i, zVar2.f24499e);
            final androidx.camera.core.processing.z zVar3 = this.f24871u.c(C.b.c(this.f24865o, Collections.singletonList(dVarH))).get(dVarH);
            Objects.requireNonNull(zVar3);
            zVar3.a(new Runnable() { // from class: androidx.camera.video.Y
                @Override // java.lang.Runnable
                public final void run() {
                    b0.c cVar = b0.f24861A;
                    b0 b0Var = this.f24848b;
                    CameraInternal cameraInternalC3 = b0Var.c();
                    CameraInternal cameraInternal = cameraInternalC;
                    if (cameraInternal == cameraInternalC3) {
                        b0Var.f24869s = zVar3.c(cameraInternal);
                        ((VideoOutput) aVar.f(androidx.camera.video.impl.a.f24939G)).b(b0Var.f24869s, timebase);
                        b0Var.L();
                    }
                }
            });
            this.f24869s = zVar3.c(cameraInternalC);
            androidx.camera.core.processing.z zVar4 = this.f24865o;
            zVar4.getClass();
            androidx.camera.core.impl.utils.t.a();
            zVar4.b();
            androidx.core.util.z.g("Consumer can only be linked once.", !zVar4.f24505k);
            zVar4.f24505k = true;
            z.a aVar2 = zVar4.f24507m;
            this.f24864n = aVar2;
            androidx.camera.core.impl.utils.futures.f.h(aVar2.f23933e).N(new K(5, this, aVar2), androidx.camera.core.impl.utils.executor.c.d());
        } else {
            K0 k0C = this.f24865o.c(cameraInternalC);
            this.f24869s = k0C;
            this.f24864n = k0C.f23599k;
        }
        ((VideoOutput) aVar.f(androidx.camera.video.impl.a.f24939G)).b(this.f24869s, timebase);
        L();
        this.f24864n.f23938j = MediaCodec.class;
        SessionConfig.b bVarM = SessionConfig.b.m(aVar, g02.e());
        bVarM.p(g02.c());
        bVarM.d(new SessionConfig.c() { // from class: androidx.camera.video.Z
            @Override // androidx.camera.core.impl.SessionConfig.c
            public final void onError() throws ExecutionException, InterruptedException {
                b0.c cVar = b0.f24861A;
                this.f24853a.K(str, aVar, g02);
            }
        });
        if (f24863C) {
            bVarM.s(1);
        }
        if (g02.d() != null) {
            bVarM.e(g02.d());
        }
        return bVarM;
    }

    @j.N
    public final T J() {
        return (T) ((androidx.camera.video.impl.a) this.f23620f).f(androidx.camera.video.impl.a.f24939G);
    }

    @j.K
    public final void K(@j.N String str, @j.N androidx.camera.video.impl.a<T> aVar, @j.N G0 g02) throws ExecutionException, InterruptedException {
        H();
        if (k(str)) {
            SessionConfig.b bVarI = I(str, aVar, g02);
            this.f24867q = bVarI;
            G(bVarI, this.f24866p, g02);
            D(this.f24867q.k());
            p();
        }
    }

    public final void L() {
        CameraInternal cameraInternalC = c();
        androidx.camera.core.processing.z zVar = this.f24865o;
        if (cameraInternalC == null || zVar == null) {
            return;
        }
        int iH2 = h(cameraInternalC, m(cameraInternalC));
        if (M()) {
            int iC2 = iH2 - this.f24866p.b().c();
            RectF rectF = androidx.camera.core.impl.utils.u.f24303a;
            iH2 = ((iC2 % 360) + 360) % 360;
        }
        this.f24874x = iH2;
        zVar.h(iH2, ((InterfaceC20088d0) this.f23620f).s());
    }

    public final boolean M() {
        return this.f24866p.b() != null;
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    @j.P
    public final L0<?> f(boolean z12, @j.N UseCaseConfigFactory useCaseConfigFactory) {
        f24861A.getClass();
        androidx.camera.video.impl.a<?> aVar = c.f24879a;
        Config configA = useCaseConfigFactory.a(aVar.z(), 1);
        if (z12) {
            configA = Config.P(configA, aVar);
        }
        if (configA == null) {
            return null;
        }
        return new androidx.camera.video.impl.a(s0.Q(((b) j(configA)).f24878a));
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final Set<Integer> i() {
        HashSet hashSet = new HashSet();
        hashSet.add(2);
        return hashSet;
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final L0.a<?, ?, ?> j(@j.N Config config) {
        return new b(androidx.camera.core.impl.n0.S(config));
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final L0<?> t(@j.N androidx.camera.core.impl.B b12, @j.N L0.a<?, ?, ?> aVar) throws ExecutionException, InterruptedException {
        AbstractC20184v abstractC20184v;
        ArrayList arrayList;
        D0<AbstractC20184v> d0C = J().c().c();
        if (d0C.isDone()) {
            try {
                abstractC20184v = d0C.get();
            } catch (InterruptedException | ExecutionException e12) {
                throw new IllegalStateException(e12);
            }
        } else {
            abstractC20184v = null;
        }
        AbstractC20184v abstractC20184v2 = abstractC20184v;
        androidx.core.util.z.a("Unable to update target resolution by null MediaSpec.", abstractC20184v2 != null);
        androidx.camera.core.F fO2 = this.f23620f.G() ? this.f23620f.o() : c.f24881c;
        X xF2 = J().f(b12);
        ArrayList arrayListA = xF2.a(fO2);
        if (arrayListA.isEmpty()) {
            C20140q0.d(5, "VideoCapture");
        } else {
            n0 n0VarD = abstractC20184v2.d();
            D dE2 = n0VarD.e();
            dE2.getClass();
            if (arrayListA.isEmpty()) {
                C20140q0.d(5, "QualitySelector");
                arrayList = new ArrayList();
            } else {
                arrayListA.toString();
                C20140q0.d(3, "QualitySelector");
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator<A> it = dE2.f24693a.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    A next = it.next();
                    if (next == A.f24685f) {
                        linkedHashSet.addAll(arrayListA);
                        break;
                    }
                    if (next == A.f24684e) {
                        ArrayList arrayList2 = new ArrayList(arrayListA);
                        Collections.reverse(arrayList2);
                        linkedHashSet.addAll(arrayList2);
                        break;
                    }
                    if (arrayListA.contains(next)) {
                        linkedHashSet.add(next);
                    } else {
                        Objects.toString(next);
                        C20140q0.d(5, "QualitySelector");
                    }
                }
                if (!arrayListA.isEmpty() && !linkedHashSet.containsAll(arrayListA)) {
                    C20181s c20181s = dE2.f24694b;
                    Objects.toString(c20181s);
                    C20140q0.d(3, "QualitySelector");
                    if (c20181s != C20181s.f25334a) {
                        androidx.core.util.z.g("Currently only support type RuleStrategy", c20181s instanceof C20181s.b);
                        C20181s.b bVar = (C20181s.b) c20181s;
                        ArrayList arrayList3 = new ArrayList(A.f24688i);
                        A aB2 = bVar.b() == A.f24685f ? (A) arrayList3.get(0) : bVar.b() == A.f24684e ? (A) androidx.appcompat.app.r.j(1, arrayList3) : bVar.b();
                        int iIndexOf = arrayList3.indexOf(aB2);
                        androidx.core.util.z.g(null, iIndexOf != -1);
                        ArrayList arrayList4 = new ArrayList();
                        for (int i12 = iIndexOf - 1; i12 >= 0; i12--) {
                            A a12 = (A) arrayList3.get(i12);
                            if (arrayListA.contains(a12)) {
                                arrayList4.add(a12);
                            }
                        }
                        ArrayList arrayList5 = new ArrayList();
                        for (int i13 = iIndexOf + 1; i13 < arrayList3.size(); i13++) {
                            A a13 = (A) arrayList3.get(i13);
                            if (arrayListA.contains(a13)) {
                                arrayList5.add(a13);
                            }
                        }
                        arrayList3.toString();
                        Objects.toString(aB2);
                        arrayList4.toString();
                        arrayList5.toString();
                        C20140q0.d(3, "QualitySelector");
                        int iC2 = bVar.c();
                        if (iC2 != 0) {
                            if (iC2 == 1) {
                                linkedHashSet.addAll(arrayList4);
                                linkedHashSet.addAll(arrayList5);
                            } else if (iC2 == 2) {
                                linkedHashSet.addAll(arrayList4);
                            } else if (iC2 == 3) {
                                linkedHashSet.addAll(arrayList5);
                                linkedHashSet.addAll(arrayList4);
                            } else {
                                if (iC2 != 4) {
                                    throw new AssertionError("Unhandled fallback strategy: " + c20181s);
                                }
                                linkedHashSet.addAll(arrayList5);
                            }
                        }
                    }
                }
                arrayList = new ArrayList(linkedHashSet);
            }
            arrayList.toString();
            dE2.toString();
            C20140q0.d(3, "VideoCapture");
            if (arrayList.isEmpty()) {
                throw new IllegalArgumentException("Unable to find supported quality by QualitySelector");
            }
            int iB2 = n0VarD.b();
            HashMap map = new HashMap();
            for (A a14 : xF2.a(fO2)) {
                androidx.camera.video.internal.f fVarB = xF2.b(a14, fO2);
                Objects.requireNonNull(fVarB);
                S.c cVarG = fVarB.g();
                map.put(a14, new Size(cVarG.k(), cVarG.h()));
            }
            C c12 = new C(map, b12.o(this.f23620f.getInputFormat()));
            ArrayList arrayList6 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                List list = (List) c12.f24692a.get(new C20174k((A) it2.next(), iB2));
                arrayList6.addAll(list != null ? new ArrayList(list) : new ArrayList(0));
            }
            arrayList6.toString();
            C20140q0.d(3, "VideoCapture");
            aVar.d().K(InterfaceC20088d0.f24102p, arrayList6);
        }
        return aVar.b();
    }

    @j.N
    public final String toString() {
        return "VideoCapture:".concat(g());
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void u() throws ExecutionException, InterruptedException {
        androidx.core.util.z.f(this.f23621g, "The suggested stream specification should be already updated and shouldn't be null.");
        androidx.core.util.z.g("The surface request should be null when VideoCapture is attached.", this.f24869s == null);
        G0 g02 = this.f23621g;
        g02.getClass();
        q0<W> q0VarD = J().d();
        W w12 = W.f24841a;
        D0<W> d0C = q0VarD.c();
        if (d0C.isDone()) {
            try {
                w12 = d0C.get();
            } catch (InterruptedException | ExecutionException e12) {
                throw new IllegalStateException(e12);
            }
        }
        this.f24866p = w12;
        SessionConfig.b bVarI = I(e(), (androidx.camera.video.impl.a) this.f23620f, g02);
        this.f24867q = bVarI;
        G(bVarI, this.f24866p, g02);
        D(this.f24867q.k());
        o();
        J().d().d(androidx.camera.core.impl.utils.executor.c.d(), this.f24876z);
        VideoOutput.SourceState sourceState = VideoOutput.SourceState.f24838c;
        if (sourceState != this.f24870t) {
            this.f24870t = sourceState;
            J().e(sourceState);
        }
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void v() {
        androidx.core.util.z.g("VideoCapture can only be detached on the main thread.", androidx.camera.core.impl.utils.t.b());
        VideoOutput.SourceState sourceState = VideoOutput.SourceState.f24839d;
        if (sourceState != this.f24870t) {
            this.f24870t = sourceState;
            J().e(sourceState);
        }
        J().d().b(this.f24876z);
        D0<Void> d02 = this.f24868r;
        if (d02 != null && d02.cancel(false)) {
            C20140q0.d(3, "VideoCapture");
        }
        H();
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final G0 w(@j.N Config config) {
        this.f24867q.e(config);
        D(this.f24867q.k());
        G0.a aVarF = this.f23621g.f();
        aVarF.d(config);
        return aVarF.a();
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final G0 x(@j.N G0 g02) {
        Objects.toString(g02);
        C20140q0.d(3, "VideoCapture");
        ArrayList arrayListP = ((androidx.camera.video.impl.a) this.f23620f).p();
        if (arrayListP != null && !arrayListP.contains(g02.e())) {
            Objects.toString(g02.e());
            arrayListP.toString();
            C20140q0.d(5, "VideoCapture");
        }
        return g02;
    }

    /* compiled from: VideoCapture.java */
    @j.X
    public static final class b<T extends VideoOutput> implements L0.a<b0<T>, androidx.camera.video.impl.a<T>, b<T>>, InterfaceC20088d0.a<b<T>>, InterfaceC20086c0.a<b<T>>, j.a<b<T>> {

        /* renamed from: a, reason: collision with root package name */
        public final androidx.camera.core.impl.n0 f24878a;

        public b(@j.N androidx.camera.core.impl.n0 n0Var) {
            Object objF;
            this.f24878a = n0Var;
            if (!n0Var.f24173F.containsKey(androidx.camera.video.impl.a.f24939G)) {
                throw new IllegalArgumentException("VideoOutput is required");
            }
            Object objF2 = null;
            try {
                objF = n0Var.f(androidx.camera.core.internal.i.f24362C);
            } catch (IllegalArgumentException unused) {
                objF = null;
            }
            Class cls = (Class) objF;
            if (cls != null && !cls.equals(b0.class)) {
                throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
            }
            Config.a<Class<?>> aVar = androidx.camera.core.internal.i.f24362C;
            androidx.camera.core.impl.n0 n0Var2 = this.f24878a;
            n0Var2.K(aVar, b0.class);
            try {
                objF2 = n0Var2.f(androidx.camera.core.internal.i.f24361B);
            } catch (IllegalArgumentException unused2) {
            }
            if (objF2 == null) {
                n0Var2.K(androidx.camera.core.internal.i.f24361B, b0.class.getCanonicalName() + "-" + UUID.randomUUID());
            }
        }

        @Override // androidx.camera.core.impl.InterfaceC20088d0.a
        @j.N
        @RestrictTo
        public final Object a(@j.N Size size) {
            throw new UnsupportedOperationException("setTargetResolution is not supported.");
        }

        @Override // androidx.camera.core.impl.L0.a
        @j.N
        @RestrictTo
        public final L0 b() {
            return new androidx.camera.video.impl.a(s0.Q(this.f24878a));
        }

        @Override // androidx.camera.core.impl.InterfaceC20088d0.a
        @j.N
        public final Object c(int i12) {
            this.f24878a.K(InterfaceC20088d0.f24094h, Integer.valueOf(i12));
            return this;
        }

        @Override // androidx.camera.core.L
        @j.N
        @RestrictTo
        public final InterfaceC20106m0 d() {
            return this.f24878a;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public b(@j.N T t12) {
            androidx.camera.core.impl.n0 n0VarR = androidx.camera.core.impl.n0.R();
            n0VarR.K(androidx.camera.video.impl.a.f24939G, t12);
            this(n0VarR);
        }
    }
}
