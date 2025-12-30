package androidx.camera.video;

import android.content.Context;
import android.media.MediaMuxer;
import android.net.Uri;
import android.os.Build;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.RestrictTo;
import androidx.camera.core.C20140q0;
import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.K0;
import androidx.camera.core.impl.F0;
import androidx.camera.core.impl.S;
import androidx.camera.core.impl.Timebase;
import androidx.camera.core.impl.o0;
import androidx.camera.core.impl.q0;
import androidx.camera.video.AbstractC20184v;
import androidx.camera.video.C20157h;
import androidx.camera.video.C20179p;
import androidx.camera.video.T;
import androidx.camera.video.VideoOutput;
import androidx.camera.video.W;
import androidx.camera.video.internal.audio.AudioSourceAccessException;
import androidx.camera.video.internal.audio.i;
import androidx.camera.video.internal.encoder.AbstractC20159a;
import androidx.camera.video.internal.encoder.C20166h;
import androidx.camera.video.internal.encoder.EncodeException;
import androidx.camera.video.internal.encoder.InterfaceC20167i;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import androidx.camera.video.internal.encoder.InterfaceC20170l;
import androidx.camera.video.m0;
import androidx.camera.video.n0;
import androidx.concurrent.futures.b;
import androidx.core.util.InterfaceC22791e;
import com.google.common.util.concurrent.D0;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: Recorder.java */
@j.X
/* loaded from: classes.dex */
public final class O implements VideoOutput {

    /* renamed from: b0, reason: collision with root package name */
    public static final Set<j> f24719b0 = Collections.unmodifiableSet(EnumSet.of(j.f24812c, j.f24813d));

    /* renamed from: c0, reason: collision with root package name */
    public static final Set<j> f24720c0 = Collections.unmodifiableSet(EnumSet.of(j.f24811b, j.f24814e, j.f24818i, j.f24817h, j.f24819j));

    /* renamed from: d0, reason: collision with root package name */
    public static final D f24721d0;

    /* renamed from: e0, reason: collision with root package name */
    public static final n0 f24722e0;

    /* renamed from: f0, reason: collision with root package name */
    public static final AbstractC20184v f24723f0;

    /* renamed from: g0, reason: collision with root package name */
    @j.k0
    public static final androidx.appcompat.app.r f24724g0;

    /* renamed from: h0, reason: collision with root package name */
    public static final Executor f24725h0;

    /* renamed from: A, reason: collision with root package name */
    public final o0<AbstractC20184v> f24726A;

    /* renamed from: B, reason: collision with root package name */
    public androidx.camera.video.internal.audio.i f24727B;

    /* renamed from: C, reason: collision with root package name */
    public InterfaceC20169k f24728C;

    /* renamed from: D, reason: collision with root package name */
    public androidx.camera.video.internal.encoder.w f24729D;

    /* renamed from: E, reason: collision with root package name */
    public InterfaceC20169k f24730E;

    /* renamed from: F, reason: collision with root package name */
    public androidx.camera.video.internal.encoder.w f24731F;

    /* renamed from: G, reason: collision with root package name */
    public g f24732G;

    /* renamed from: H, reason: collision with root package name */
    @j.N
    public Uri f24733H;

    /* renamed from: I, reason: collision with root package name */
    public long f24734I;

    /* renamed from: J, reason: collision with root package name */
    public long f24735J;

    /* renamed from: K, reason: collision with root package name */
    @j.k0
    public long f24736K;

    /* renamed from: L, reason: collision with root package name */
    @j.k0
    public long f24737L;

    /* renamed from: M, reason: collision with root package name */
    public long f24738M;

    /* renamed from: N, reason: collision with root package name */
    public long f24739N;

    /* renamed from: O, reason: collision with root package name */
    public long f24740O;

    /* renamed from: P, reason: collision with root package name */
    public long f24741P;

    /* renamed from: Q, reason: collision with root package name */
    public int f24742Q;

    /* renamed from: R, reason: collision with root package name */
    public InterfaceC20167i f24743R;

    /* renamed from: S, reason: collision with root package name */
    @j.N
    public final androidx.camera.core.internal.utils.a f24744S;

    /* renamed from: T, reason: collision with root package name */
    public Throwable f24745T;

    /* renamed from: U, reason: collision with root package name */
    public boolean f24746U;

    /* renamed from: V, reason: collision with root package name */
    public VideoOutput.SourceState f24747V;

    /* renamed from: W, reason: collision with root package name */
    public ScheduledFuture<?> f24748W;

    /* renamed from: X, reason: collision with root package name */
    public boolean f24749X;

    /* renamed from: Y, reason: collision with root package name */
    @j.N
    public l0 f24750Y;

    /* renamed from: Z, reason: collision with root package name */
    @j.P
    public l0 f24751Z;

    /* renamed from: a, reason: collision with root package name */
    public final o0<W> f24752a;

    /* renamed from: a0, reason: collision with root package name */
    public double f24753a0;

    /* renamed from: b, reason: collision with root package name */
    public final Executor f24754b;

    /* renamed from: c, reason: collision with root package name */
    public final Executor f24755c;

    /* renamed from: d, reason: collision with root package name */
    public final androidx.appcompat.app.r f24756d;

    /* renamed from: e, reason: collision with root package name */
    public final androidx.appcompat.app.r f24757e;

    /* renamed from: f, reason: collision with root package name */
    public final Object f24758f = new Object();

    /* renamed from: g, reason: collision with root package name */
    public final boolean f24759g;

    /* renamed from: h, reason: collision with root package name */
    @j.B
    public j f24760h;

    /* renamed from: i, reason: collision with root package name */
    @j.B
    public j f24761i;

    /* renamed from: j, reason: collision with root package name */
    @j.B
    public int f24762j;

    /* renamed from: k, reason: collision with root package name */
    @j.B
    public C20176m f24763k;

    /* renamed from: l, reason: collision with root package name */
    @j.B
    public C20176m f24764l;

    /* renamed from: m, reason: collision with root package name */
    @j.B
    public long f24765m;

    /* renamed from: n, reason: collision with root package name */
    public i f24766n;

    /* renamed from: o, reason: collision with root package name */
    public boolean f24767o;

    /* renamed from: p, reason: collision with root package name */
    @j.P
    public K0.d f24768p;

    /* renamed from: q, reason: collision with root package name */
    @j.P
    public K0.d f24769q;

    /* renamed from: r, reason: collision with root package name */
    public androidx.camera.video.internal.f f24770r;

    /* renamed from: s, reason: collision with root package name */
    public final ArrayList f24771s;

    /* renamed from: t, reason: collision with root package name */
    public Integer f24772t;

    /* renamed from: u, reason: collision with root package name */
    public Integer f24773u;

    /* renamed from: v, reason: collision with root package name */
    public K0 f24774v;

    /* renamed from: w, reason: collision with root package name */
    public Timebase f24775w;

    /* renamed from: x, reason: collision with root package name */
    public Surface f24776x;

    /* renamed from: y, reason: collision with root package name */
    public Surface f24777y;

    /* renamed from: z, reason: collision with root package name */
    public MediaMuxer f24778z;

    /* compiled from: Recorder.java */
    public class a implements androidx.camera.core.impl.utils.futures.c<Void> {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ androidx.camera.video.internal.audio.i f24779a;

        public a(androidx.camera.video.internal.audio.i iVar) {
            this.f24779a = iVar;
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@j.N Throwable th2) {
            String.format("An error occurred while attempting to release audio source: 0x%x", Integer.valueOf(this.f24779a.hashCode()));
            C20140q0.d(3, "Recorder");
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void onSuccess(@j.P Void r22) {
            String.format("Released audio source successfully: 0x%x", Integer.valueOf(this.f24779a.hashCode()));
            C20140q0.d(3, "Recorder");
        }
    }

    /* compiled from: Recorder.java */
    public class b implements InterfaceC20170l {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ b.a f24780b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ i f24781c;

        public b(b.a aVar, i iVar) {
            this.f24780b = aVar;
            this.f24781c = iVar;
        }

        @Override // androidx.camera.video.internal.encoder.InterfaceC20170l
        public final void a(@j.N androidx.camera.video.internal.encoder.w wVar) {
            O.this.f24729D = wVar;
        }

        @Override // androidx.camera.video.internal.encoder.InterfaceC20170l
        public final void b() {
            this.f24780b.b(null);
        }

        @Override // androidx.camera.video.internal.encoder.InterfaceC20170l
        public final void c(@j.N EncodeException encodeException) {
            this.f24780b.d(encodeException);
        }

        @Override // androidx.camera.video.internal.encoder.InterfaceC20170l
        public final void d(@j.N InterfaceC20167i interfaceC20167i) throws Exception {
            boolean z12;
            O o12 = O.this;
            MediaMuxer mediaMuxer = o12.f24778z;
            i iVar = this.f24781c;
            if (mediaMuxer != null) {
                try {
                    o12.K(interfaceC20167i, iVar);
                    interfaceC20167i.close();
                    return;
                } catch (Throwable th2) {
                    try {
                        interfaceC20167i.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                    throw th2;
                }
            }
            if (o12.f24767o) {
                C20140q0.d(3, "Recorder");
                interfaceC20167i.close();
                return;
            }
            InterfaceC20167i interfaceC20167i2 = o12.f24743R;
            if (interfaceC20167i2 != null) {
                interfaceC20167i2.close();
                o12.f24743R = null;
                z12 = true;
            } else {
                z12 = false;
            }
            if (!interfaceC20167i.M3()) {
                if (z12) {
                    C20140q0.d(3, "Recorder");
                }
                C20140q0.d(3, "Recorder");
                o12.f24728C.e();
                interfaceC20167i.close();
                return;
            }
            o12.f24743R = interfaceC20167i;
            if (!o12.m() || !o12.f24744S.c()) {
                C20140q0.d(3, "Recorder");
                o12.C(iVar);
            } else if (z12) {
                C20140q0.d(3, "Recorder");
            } else {
                C20140q0.d(3, "Recorder");
            }
        }
    }

    /* compiled from: Recorder.java */
    public class c implements i.b {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ G f24783a;

        public c(G g12) {
            this.f24783a = g12;
        }

        @Override // androidx.camera.video.internal.audio.i.b
        public final void a(boolean z12) {
            O o12 = O.this;
            if (o12.f24746U == z12) {
                C20140q0.d(5, "Recorder");
            } else {
                o12.f24746U = z12;
                o12.H();
            }
        }

        @Override // androidx.camera.video.internal.audio.i.b
        public final void b(double d12) {
            O.this.f24753a0 = d12;
        }

        @Override // androidx.camera.video.internal.audio.i.b
        public final void onError(@j.N Throwable th2) {
            C20140q0.c("Recorder");
            if (th2 instanceof AudioSourceAccessException) {
                this.f24783a.accept(th2);
            }
        }
    }

    /* compiled from: Recorder.java */
    public class d implements InterfaceC20170l {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ b.a f24785b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ G f24786c;

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ i f24787d;

        public d(b.a aVar, G g12, i iVar) {
            this.f24785b = aVar;
            this.f24786c = g12;
            this.f24787d = iVar;
        }

        @Override // androidx.camera.video.internal.encoder.InterfaceC20170l
        public final void a(@j.N androidx.camera.video.internal.encoder.w wVar) {
            O.this.f24731F = wVar;
        }

        @Override // androidx.camera.video.internal.encoder.InterfaceC20170l
        public final void b() {
            this.f24785b.b(null);
        }

        @Override // androidx.camera.video.internal.encoder.InterfaceC20170l
        public final void c(@j.N EncodeException encodeException) {
            if (O.this.f24745T == null) {
                this.f24786c.accept(encodeException);
            }
        }

        @Override // androidx.camera.video.internal.encoder.InterfaceC20170l
        public final void d(@j.N InterfaceC20167i interfaceC20167i) throws Exception {
            O o12 = O.this;
            if (o12.f24732G == g.f24794d) {
                interfaceC20167i.close();
                throw new AssertionError("Audio is not enabled but audio encoded data is being produced.");
            }
            MediaMuxer mediaMuxer = o12.f24778z;
            i iVar = this.f24787d;
            if (mediaMuxer != null) {
                try {
                    o12.J(interfaceC20167i, iVar);
                    interfaceC20167i.close();
                    return;
                } catch (Throwable th2) {
                    try {
                        interfaceC20167i.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                    throw th2;
                }
            }
            if (o12.f24767o) {
                C20140q0.d(3, "Recorder");
            } else {
                o12.f24744S.b(new C20166h(interfaceC20167i));
                if (o12.f24743R != null) {
                    C20140q0.d(3, "Recorder");
                    o12.C(iVar);
                } else {
                    C20140q0.d(3, "Recorder");
                }
            }
            interfaceC20167i.close();
        }
    }

    /* compiled from: Recorder.java */
    public class e implements androidx.camera.core.impl.utils.futures.c<List<Void>> {
        public e() {
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@j.N Throwable th2) {
            O o12 = O.this;
            androidx.core.util.z.g("In-progress recording shouldn't be null", o12.f24766n != null);
            if (o12.f24766n.j()) {
                return;
            }
            Objects.toString(th2);
            C20140q0.d(3, "Recorder");
            o12.h(o12.f24778z == null ? 8 : 6);
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void onSuccess(@j.P List<Void> list) {
            C20140q0.d(3, "Recorder");
            O o12 = O.this;
            o12.h(o12.f24742Q);
        }
    }

    /* compiled from: Recorder.java */
    public static /* synthetic */ class f {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f24790a;

        /* renamed from: b, reason: collision with root package name */
        public static final /* synthetic */ int[] f24791b;

        static {
            int[] iArr = new int[g.values().length];
            f24791b = iArr;
            try {
                iArr[4] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f24791b[5] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f24791b[3] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f24791b[2] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f24791b[1] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f24791b[0] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[j.values().length];
            f24790a = iArr2;
            try {
                iArr2[5] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f24790a[4] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f24790a[2] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f24790a[1] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f24790a[7] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f24790a[6] = 6;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f24790a[0] = 7;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f24790a[8] = 8;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f24790a[3] = 9;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: Recorder.java */
    public static final class g {

        /* renamed from: b, reason: collision with root package name */
        public static final g f24792b;

        /* renamed from: c, reason: collision with root package name */
        public static final g f24793c;

        /* renamed from: d, reason: collision with root package name */
        public static final g f24794d;

        /* renamed from: e, reason: collision with root package name */
        public static final g f24795e;

        /* renamed from: f, reason: collision with root package name */
        public static final g f24796f;

        /* renamed from: g, reason: collision with root package name */
        public static final g f24797g;

        /* renamed from: h, reason: collision with root package name */
        public static final /* synthetic */ g[] f24798h;

        static {
            g gVar = new g("INITIALIZING", 0);
            f24792b = gVar;
            g gVar2 = new g("IDLING", 1);
            f24793c = gVar2;
            g gVar3 = new g("DISABLED", 2);
            f24794d = gVar3;
            g gVar4 = new g("ENABLED", 3);
            f24795e = gVar4;
            g gVar5 = new g("ERROR_ENCODER", 4);
            f24796f = gVar5;
            g gVar6 = new g("ERROR_SOURCE", 5);
            f24797g = gVar6;
            f24798h = new g[]{gVar, gVar2, gVar3, gVar4, gVar5, gVar6};
        }

        public g() {
            throw null;
        }

        public static g valueOf(String str) {
            return (g) Enum.valueOf(g.class, str);
        }

        public static g[] values() {
            return (g[]) f24798h.clone();
        }
    }

    /* compiled from: Recorder.java */
    @j.X
    public static final class h {

        /* renamed from: a, reason: collision with root package name */
        public final AbstractC20184v.a f24799a;

        /* renamed from: b, reason: collision with root package name */
        public final androidx.appcompat.app.r f24800b;

        /* renamed from: c, reason: collision with root package name */
        public final androidx.appcompat.app.r f24801c;

        public h() {
            androidx.appcompat.app.r rVar = O.f24724g0;
            this.f24800b = rVar;
            this.f24801c = rVar;
            this.f24799a = AbstractC20184v.a();
        }

        @j.N
        public final void a(@j.N D d12) {
            androidx.core.util.z.f(d12, "The specified quality selector can't be null.");
            AbstractC20184v.a aVar = this.f24799a;
            n0.a aVarF = aVar.b().f();
            ((C20179p.b) aVarF).f25330a = d12;
            ((C20157h.b) aVar).f24922a = aVarF.a();
        }
    }

    /* compiled from: Recorder.java */
    @VY0.c
    @j.X
    public static abstract class i implements AutoCloseable {

        /* renamed from: f, reason: collision with root package name */
        public final AtomicReference<InterfaceC22791e<Uri>> f24806f;

        /* renamed from: b, reason: collision with root package name */
        public final androidx.camera.core.impl.utils.f f24802b = androidx.camera.core.impl.utils.f.b();

        /* renamed from: c, reason: collision with root package name */
        public final AtomicBoolean f24803c = new AtomicBoolean(false);

        /* renamed from: d, reason: collision with root package name */
        public final AtomicReference<d> f24804d = new AtomicReference<>(null);

        /* renamed from: e, reason: collision with root package name */
        public final AtomicReference<c> f24805e = new AtomicReference<>(null);

        /* renamed from: g, reason: collision with root package name */
        public final AtomicBoolean f24807g = new AtomicBoolean(false);

        /* compiled from: Recorder.java */
        public class a implements c {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ Context f24808a;

            /* renamed from: b, reason: collision with root package name */
            public final /* synthetic */ C20176m f24809b;

            public a(C20176m c20176m, Context context) {
                this.f24809b = c20176m;
                this.f24808a = context;
            }

            @Override // androidx.camera.video.O.i.c
            @j.N
            @j.a0
            public final androidx.camera.video.internal.audio.i a(@j.N androidx.camera.video.internal.audio.a aVar, @j.N Executor executor) {
                return new androidx.camera.video.internal.audio.i(aVar, executor, this.f24808a);
            }
        }

        /* compiled from: Recorder.java */
        public class b implements c {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ C20176m f24810a;

            public b(C20176m c20176m) {
                this.f24810a = c20176m;
            }

            @Override // androidx.camera.video.O.i.c
            @j.N
            @j.a0
            public final androidx.camera.video.internal.audio.i a(@j.N androidx.camera.video.internal.audio.a aVar, @j.N Executor executor) {
                return new androidx.camera.video.internal.audio.i(aVar, executor, null);
            }
        }

        /* compiled from: Recorder.java */
        public interface c {
            @j.N
            @j.a0
            androidx.camera.video.internal.audio.i a(@j.N androidx.camera.video.internal.audio.a aVar, @j.N Executor executor);
        }

        /* compiled from: Recorder.java */
        public interface d {
            @j.N
            MediaMuxer a(int i12, @j.N j0 j0Var);
        }

        public i() {
            final int i12 = 0;
            this.f24806f = new AtomicReference<>(new InterfaceC22791e() { // from class: androidx.camera.video.P
                @Override // androidx.core.util.InterfaceC22791e
                public final void accept(Object obj) {
                    switch (i12) {
                        case 0:
                            return;
                        default:
                            throw null;
                    }
                }
            });
        }

        public final void a(@j.N Uri uri) {
            if (this.f24803c.get()) {
                b(this.f24806f.getAndSet(null), uri);
            }
        }

        public final void b(@j.P InterfaceC22791e<Uri> interfaceC22791e, @j.N Uri uri) {
            if (interfaceC22791e != null) {
                this.f24802b.a();
                interfaceC22791e.accept(uri);
            } else {
                throw new AssertionError("Recording " + this + " has already been finalized");
            }
        }

        @j.P
        public abstract Executor c();

        @Override // java.lang.AutoCloseable
        public final void close() {
            a(Uri.EMPTY);
        }

        @j.P
        public abstract InterfaceC22791e<m0> d();

        @j.N
        public abstract AbstractC20186x f();

        public final void finalize() throws Throwable {
            try {
                this.f24802b.d();
                InterfaceC22791e<Uri> andSet = this.f24806f.getAndSet(null);
                if (andSet != null) {
                    b(andSet, Uri.EMPTY);
                }
            } finally {
                super.finalize();
            }
        }

        public abstract long g();

        public abstract boolean h();

        public final void i(@j.N Context context) {
            if (this.f24803c.getAndSet(true)) {
                throw new AssertionError("Recording " + this + " has already been initialized");
            }
            C20176m c20176m = (C20176m) this;
            final C20183u c20183u = c20176m.f25310h;
            this.f24802b.c("finalizeRecording");
            this.f24804d.set(new d() { // from class: androidx.camera.video.Q
                @Override // androidx.camera.video.O.i.d
                public final MediaMuxer a(int i12, j0 j0Var) {
                    Uri uri = Uri.EMPTY;
                    AbstractC20186x abstractC20186x = c20183u;
                    if (!(abstractC20186x instanceof C20183u)) {
                        if (abstractC20186x instanceof C20182t) {
                            throw null;
                        }
                        if (!(abstractC20186x instanceof C20185w)) {
                            throw new AssertionError("Invalid output options type: ".concat(abstractC20186x.getClass().getSimpleName()));
                        }
                        ((C20185w) abstractC20186x).getClass();
                        throw null;
                    }
                    File fileD = ((C20183u) abstractC20186x).f25335b.d();
                    File parentFile = fileD.getParentFile();
                    if (!(parentFile == null ? false : parentFile.exists() ? parentFile.isDirectory() : parentFile.mkdirs())) {
                        fileD.getAbsolutePath();
                        C20140q0.d(5, "Recorder");
                    }
                    MediaMuxer mediaMuxer = new MediaMuxer(fileD.getAbsolutePath(), i12);
                    ((O) j0Var.f25284c).f24733H = Uri.fromFile(fileD);
                    return mediaMuxer;
                }
            });
            if (c20176m.f25313k) {
                int i12 = Build.VERSION.SDK_INT;
                AtomicReference<c> atomicReference = this.f24805e;
                if (i12 >= 31) {
                    atomicReference.set(new a(c20176m, context));
                } else {
                    atomicReference.set(new b(c20176m));
                }
            }
        }

        public abstract boolean j();

        @j.N
        public final MediaMuxer k(int i12, @j.N j0 j0Var) throws IOException {
            if (!this.f24803c.get()) {
                throw new AssertionError("Recording " + this + " has not been initialized");
            }
            d andSet = this.f24804d.getAndSet(null);
            if (andSet == null) {
                throw new AssertionError("One-time media muxer creation has already occurred for recording " + this);
            }
            try {
                return andSet.a(i12, j0Var);
            } catch (RuntimeException e12) {
                throw new IOException("Failed to create MediaMuxer by " + e12, e12);
            }
        }

        public final void m(@j.N m0 m0Var) {
            int i12;
            AbstractC20186x abstractC20186xF = f();
            AbstractC20186x abstractC20186x = m0Var.f25315a;
            if (!Objects.equals(abstractC20186x, abstractC20186xF)) {
                throw new AssertionError("Attempted to update event listener with event from incorrect recording [Recording: " + abstractC20186x + ", Expected: " + f() + "]");
            }
            "Sending VideoRecordEvent ".concat(m0Var.getClass().getSimpleName());
            if ((m0Var instanceof m0.a) && (i12 = ((m0.a) m0Var).f25316b) != 0) {
                switch (i12) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        break;
                    default:
                        StringBuilder sb2 = new StringBuilder("Unknown(");
                        sb2.append(i12);
                        sb2.append(")");
                        break;
                }
            }
            C20140q0.d(3, "Recorder");
            if (c() == null || d() == null) {
                return;
            }
            try {
                c().execute(new K(1, this, m0Var));
            } catch (RejectedExecutionException unused) {
                C20140q0.c("Recorder");
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: Recorder.java */
    public static final class j {

        /* renamed from: b, reason: collision with root package name */
        public static final j f24811b;

        /* renamed from: c, reason: collision with root package name */
        public static final j f24812c;

        /* renamed from: d, reason: collision with root package name */
        public static final j f24813d;

        /* renamed from: e, reason: collision with root package name */
        public static final j f24814e;

        /* renamed from: f, reason: collision with root package name */
        public static final j f24815f;

        /* renamed from: g, reason: collision with root package name */
        public static final j f24816g;

        /* renamed from: h, reason: collision with root package name */
        public static final j f24817h;

        /* renamed from: i, reason: collision with root package name */
        public static final j f24818i;

        /* renamed from: j, reason: collision with root package name */
        public static final j f24819j;

        /* renamed from: k, reason: collision with root package name */
        public static final /* synthetic */ j[] f24820k;

        static {
            j jVar = new j("CONFIGURING", 0);
            f24811b = jVar;
            j jVar2 = new j("PENDING_RECORDING", 1);
            f24812c = jVar2;
            j jVar3 = new j("PENDING_PAUSED", 2);
            f24813d = jVar3;
            j jVar4 = new j("IDLING", 3);
            f24814e = jVar4;
            j jVar5 = new j("RECORDING", 4);
            f24815f = jVar5;
            j jVar6 = new j("PAUSED", 5);
            f24816g = jVar6;
            j jVar7 = new j("STOPPING", 6);
            f24817h = jVar7;
            j jVar8 = new j("RESETTING", 7);
            f24818i = jVar8;
            j jVar9 = new j("ERROR", 8);
            f24819j = jVar9;
            f24820k = new j[]{jVar, jVar2, jVar3, jVar4, jVar5, jVar6, jVar7, jVar8, jVar9};
        }

        public j() {
            throw null;
        }

        public static j valueOf(String str) {
            return (j) Enum.valueOf(j.class, str);
        }

        public static j[] values() {
            return (j[]) f24820k.clone();
        }
    }

    static {
        A a12 = A.f24682c;
        D dA2 = D.a(Arrays.asList(a12, A.f24681b, A.f24680a), C20181s.a(a12));
        f24721d0 = dA2;
        n0.a aVarA = n0.a();
        ((C20179p.b) aVarA).f25330a = dA2;
        aVarA.b(-1);
        n0 n0VarA = aVarA.a();
        f24722e0 = n0VarA;
        C20157h.b bVar = (C20157h.b) AbstractC20184v.a();
        bVar.f24924c = -1;
        bVar.f24922a = n0VarA;
        f24723f0 = bVar.a();
        new RuntimeException("The video frame producer became inactive before any data was received.");
        f24724g0 = new androidx.appcompat.app.r();
        f24725h0 = androidx.camera.core.impl.utils.executor.c.f(androidx.camera.core.impl.utils.executor.c.c());
    }

    public O(@j.N AbstractC20184v abstractC20184v, @j.N androidx.appcompat.app.r rVar, @j.N androidx.appcompat.app.r rVar2) {
        this.f24759g = androidx.camera.video.internal.compat.quirk.e.f25058a.b(androidx.camera.video.internal.compat.quirk.g.class) != null;
        this.f24760h = j.f24811b;
        this.f24761i = null;
        this.f24762j = 0;
        this.f24763k = null;
        this.f24764l = null;
        this.f24765m = 0L;
        this.f24766n = null;
        this.f24767o = false;
        this.f24768p = null;
        this.f24769q = null;
        this.f24770r = null;
        this.f24771s = new ArrayList();
        this.f24772t = null;
        this.f24773u = null;
        this.f24776x = null;
        this.f24777y = null;
        this.f24778z = null;
        this.f24727B = null;
        this.f24728C = null;
        this.f24729D = null;
        this.f24730E = null;
        this.f24731F = null;
        this.f24732G = g.f24792b;
        this.f24733H = Uri.EMPTY;
        this.f24734I = 0L;
        this.f24735J = 0L;
        this.f24736K = Long.MAX_VALUE;
        this.f24737L = Long.MAX_VALUE;
        this.f24738M = Long.MAX_VALUE;
        this.f24739N = Long.MAX_VALUE;
        this.f24740O = 0L;
        this.f24741P = 0L;
        this.f24742Q = 1;
        this.f24743R = null;
        this.f24744S = new androidx.camera.core.internal.utils.a(60, null);
        this.f24745T = null;
        this.f24746U = false;
        this.f24747V = VideoOutput.SourceState.f24839d;
        this.f24748W = null;
        this.f24749X = false;
        this.f24751Z = null;
        this.f24753a0 = 0.0d;
        Executor executorC = androidx.camera.core.impl.utils.executor.c.c();
        this.f24754b = executorC;
        Executor executorF = androidx.camera.core.impl.utils.executor.c.f(executorC);
        this.f24755c = executorF;
        AbstractC20184v.a aVarE = abstractC20184v.e();
        if (abstractC20184v.d().b() == -1) {
            n0.a aVarF = aVarE.b().f();
            aVarF.b(f24722e0.b());
            ((C20157h.b) aVarE).f24922a = aVarF.a();
        }
        this.f24726A = new o0<>(aVarE.a());
        int i12 = this.f24762j;
        W.a aVarL = l(this.f24760h);
        W w12 = W.f24841a;
        this.f24752a = new o0<>(new C20178o(i12, aVarL, null));
        this.f24756d = rVar;
        this.f24757e = rVar2;
        this.f24750Y = new l0(rVar, executorF, executorC);
    }

    public static Object k(@j.N F0 f02) {
        try {
            return f02.c().get();
        } catch (InterruptedException | ExecutionException e12) {
            throw new IllegalStateException(e12);
        }
    }

    @j.N
    public static W.a l(@j.N j jVar) {
        return (jVar == j.f24815f || (jVar == j.f24817h && ((androidx.camera.video.internal.compat.quirk.d) androidx.camera.video.internal.compat.quirk.e.f25058a.b(androidx.camera.video.internal.compat.quirk.d.class)) == null)) ? W.a.f24844b : W.a.f24845c;
    }

    public static boolean o(@j.N U u12, @j.P C20176m c20176m) {
        return c20176m != null && u12.f24834d == c20176m.f25314l;
    }

    public static void q(@j.N InterfaceC20169k interfaceC20169k) {
        if (interfaceC20169k instanceof androidx.camera.video.internal.encoder.v) {
            androidx.camera.video.internal.encoder.v vVar = (androidx.camera.video.internal.encoder.v) interfaceC20169k;
            vVar.f25203h.execute(new androidx.camera.video.internal.encoder.p(vVar, 5));
        }
    }

    @j.B
    public final void A(@j.N j jVar) {
        j jVar2 = this.f24760h;
        if (jVar2 == jVar) {
            throw new AssertionError("Attempted to transition to state " + jVar + ", but Recorder is already in state " + jVar);
        }
        Objects.toString(jVar2);
        Objects.toString(jVar);
        C20140q0.d(3, "Recorder");
        Set<j> set = f24719b0;
        W.a aVarL = null;
        if (set.contains(jVar)) {
            if (!set.contains(this.f24760h)) {
                if (!f24720c0.contains(this.f24760h)) {
                    throw new AssertionError("Invalid state transition. Should not be transitioning to a PENDING state from state " + this.f24760h);
                }
                j jVar3 = this.f24760h;
                this.f24761i = jVar3;
                aVarL = l(jVar3);
            }
        } else if (this.f24761i != null) {
            this.f24761i = null;
        }
        this.f24760h = jVar;
        if (aVarL == null) {
            aVarL = l(jVar);
        }
        int i12 = this.f24762j;
        K0.d dVar = this.f24768p;
        W w12 = W.f24841a;
        this.f24752a.a(new C20178o(i12, aVarL, dVar));
    }

    @j.B
    public final void B(int i12) {
        if (this.f24762j == i12) {
            return;
        }
        C20140q0.d(3, "Recorder");
        this.f24762j = i12;
        W.a aVarL = l(this.f24760h);
        K0.d dVar = this.f24768p;
        W w12 = W.f24841a;
        this.f24752a.a(new C20178o(i12, aVarL, dVar));
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x00f4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00de A[Catch: all -> 0x0061, TryCatch #1 {all -> 0x0061, blocks: (B:14:0x0024, B:15:0x002f, B:17:0x0035, B:19:0x0043, B:20:0x0047, B:21:0x004f, B:23:0x0055, B:26:0x0065, B:30:0x0072, B:34:0x0093, B:36:0x00a4, B:41:0x00b3, B:56:0x00d0, B:57:0x00da, B:59:0x00de, B:60:0x00e8, B:62:0x00f4, B:71:0x011e, B:67:0x0114, B:72:0x0123, B:78:0x0150, B:80:0x0166, B:81:0x0176, B:82:0x0182, B:84:0x0188, B:75:0x0146, B:51:0x00c7, B:88:0x0196), top: B:102:0x0024, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0166 A[Catch: all -> 0x0061, TryCatch #1 {all -> 0x0061, blocks: (B:14:0x0024, B:15:0x002f, B:17:0x0035, B:19:0x0043, B:20:0x0047, B:21:0x004f, B:23:0x0055, B:26:0x0065, B:30:0x0072, B:34:0x0093, B:36:0x00a4, B:41:0x00b3, B:56:0x00d0, B:57:0x00da, B:59:0x00de, B:60:0x00e8, B:62:0x00f4, B:71:0x011e, B:67:0x0114, B:72:0x0123, B:78:0x0150, B:80:0x0166, B:81:0x0176, B:82:0x0182, B:84:0x0188, B:75:0x0146, B:51:0x00c7, B:88:0x0196), top: B:102:0x0024, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0188 A[Catch: all -> 0x0061, LOOP:2: B:82:0x0182->B:84:0x0188, LOOP_END, TRY_LEAVE, TryCatch #1 {all -> 0x0061, blocks: (B:14:0x0024, B:15:0x002f, B:17:0x0035, B:19:0x0043, B:20:0x0047, B:21:0x004f, B:23:0x0055, B:26:0x0065, B:30:0x0072, B:34:0x0093, B:36:0x00a4, B:41:0x00b3, B:56:0x00d0, B:57:0x00da, B:59:0x00de, B:60:0x00e8, B:62:0x00f4, B:71:0x011e, B:67:0x0114, B:72:0x0123, B:78:0x0150, B:80:0x0166, B:81:0x0176, B:82:0x0182, B:84:0x0188, B:75:0x0146, B:51:0x00c7, B:88:0x0196), top: B:102:0x0024, inners: #2, #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void C(@j.N androidx.camera.video.O.i r19) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 439
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.O.C(androidx.camera.video.O$i):void");
    }

    @j.a0
    public final void D(@j.N i iVar) {
        AbstractC20184v abstractC20184v = (AbstractC20184v) k(this.f24726A);
        androidx.camera.video.internal.config.e eVarA = androidx.camera.video.internal.config.b.a(abstractC20184v, this.f24770r);
        AbstractC20150a abstractC20150aB = abstractC20184v.b();
        S.a aVarC = eVarA.c();
        androidx.camera.video.internal.audio.a aVar = (androidx.camera.video.internal.audio.a) (aVarC != null ? new androidx.camera.video.internal.config.f(abstractC20150aB, aVarC) : new androidx.camera.video.internal.config.g(abstractC20150aB)).get();
        if (this.f24727B != null) {
            s();
        }
        Executor executor = f24725h0;
        if (!iVar.h()) {
            throw new AssertionError("Recording does not have audio enabled. Unable to create audio source for recording " + iVar);
        }
        i.c andSet = iVar.f24805e.getAndSet(null);
        if (andSet == null) {
            throw new AssertionError("One-time audio source creation has already occurred for recording " + iVar);
        }
        androidx.camera.video.internal.audio.i iVarA = andSet.a(aVar, executor);
        this.f24727B = iVarA;
        String.format("Set up new audio source: 0x%x", Integer.valueOf(iVarA.hashCode()));
        C20140q0.d(3, "Recorder");
        AbstractC20150a abstractC20150aB2 = abstractC20184v.b();
        S.a aVarC2 = eVarA.c();
        AbstractC20159a abstractC20159a = (AbstractC20159a) (aVarC2 != null ? new androidx.camera.video.internal.config.c(eVarA.a(), eVarA.b(), abstractC20150aB2, aVar, aVarC2) : new androidx.camera.video.internal.config.d(eVarA.a(), eVarA.b(), abstractC20150aB2, aVar)).get();
        this.f24757e.getClass();
        androidx.camera.video.internal.encoder.v vVar = new androidx.camera.video.internal.encoder.v(this.f24754b, abstractC20159a);
        this.f24730E = vVar;
        InterfaceC20169k.b bVar = vVar.f25201f;
        if (!(bVar instanceof InterfaceC20169k.a)) {
            throw new AssertionError("The EncoderInput of audio isn't a ByteBufferInput.");
        }
        androidx.camera.video.internal.audio.i iVar2 = this.f24727B;
        iVar2.f24969a.execute(new androidx.camera.video.internal.audio.f(0, iVar2, (InterfaceC20169k.a) bVar));
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void E(@j.N androidx.camera.video.O.i r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 337
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.O.E(androidx.camera.video.O$i, boolean):void");
    }

    public final void F(@j.N i iVar, long j12, int i12, @j.P Exception exc) throws Exception {
        if (this.f24766n != iVar || this.f24767o) {
            return;
        }
        this.f24767o = true;
        this.f24742Q = i12;
        if (m()) {
            while (true) {
                androidx.camera.core.internal.utils.a aVar = this.f24744S;
                if (aVar.c()) {
                    break;
                } else {
                    aVar.a();
                }
            }
            this.f24730E.d(j12);
        }
        InterfaceC20167i interfaceC20167i = this.f24743R;
        if (interfaceC20167i != null) {
            interfaceC20167i.close();
            this.f24743R = null;
        }
        if (this.f24747V != VideoOutput.SourceState.f24838c) {
            this.f24748W = androidx.camera.core.impl.utils.executor.c.d().schedule(new K(4, this, this.f24728C), 1000L, TimeUnit.MILLISECONDS);
        } else {
            q(this.f24728C);
        }
        this.f24728C.d(j12);
    }

    public final void G(@j.N i iVar, boolean z12) {
        ArrayList arrayList = this.f24771s;
        if (!arrayList.isEmpty()) {
            D0 d0B = androidx.camera.core.impl.utils.futures.f.b(arrayList);
            if (!d0B.isDone()) {
                d0B.cancel(true);
            }
            arrayList.clear();
        }
        arrayList.add(androidx.concurrent.futures.b.a(new J(0, this, iVar)));
        if (m() && !z12) {
            arrayList.add(androidx.concurrent.futures.b.a(new J(1, this, iVar)));
        }
        androidx.camera.core.impl.utils.futures.f.a(androidx.camera.core.impl.utils.futures.f.b(arrayList), new e(), androidx.camera.core.impl.utils.executor.c.a());
    }

    public final void H() {
        i iVar = this.f24766n;
        if (iVar != null) {
            iVar.m(new m0.e(iVar.f(), j()));
        }
    }

    @j.B
    public final void I(@j.N j jVar) {
        if (!f24719b0.contains(this.f24760h)) {
            throw new AssertionError("Can only updated non-pending state from a pending state, but state is " + this.f24760h);
        }
        if (!f24720c0.contains(jVar)) {
            throw new AssertionError("Invalid state transition. State is not a valid non-pending state while in a pending state: " + jVar);
        }
        if (this.f24761i != jVar) {
            this.f24761i = jVar;
            int i12 = this.f24762j;
            W.a aVarL = l(jVar);
            K0.d dVar = this.f24768p;
            W w12 = W.f24841a;
            this.f24752a.a(new C20178o(i12, aVarL, dVar));
        }
    }

    public final void J(@j.N InterfaceC20167i interfaceC20167i, @j.N i iVar) {
        long size = interfaceC20167i.size() + this.f24734I;
        long j12 = this.f24740O;
        if (j12 != 0 && size > j12) {
            String.format("Reach file size limit %d > %d", Long.valueOf(size), Long.valueOf(this.f24740O));
            C20140q0.d(3, "Recorder");
            r(iVar, 2, null);
            return;
        }
        long jS02 = interfaceC20167i.S0();
        long j13 = this.f24737L;
        if (j13 == Long.MAX_VALUE) {
            this.f24737L = jS02;
            String.format("First audio time: %d (%s)", Long.valueOf(jS02), androidx.camera.video.internal.c.c(this.f24737L));
            C20140q0.d(3, "Recorder");
        } else {
            TimeUnit timeUnit = TimeUnit.MICROSECONDS;
            long nanos = timeUnit.toNanos(jS02 - Math.min(this.f24736K, j13));
            androidx.core.util.z.g("There should be a previous data for adjusting the duration.", this.f24739N != Long.MAX_VALUE);
            long nanos2 = timeUnit.toNanos(jS02 - this.f24739N) + nanos;
            long j14 = this.f24741P;
            if (j14 != 0 && nanos2 > j14) {
                String.format("Audio data reaches duration limit %d > %d", Long.valueOf(nanos2), Long.valueOf(this.f24741P));
                C20140q0.d(3, "Recorder");
                r(iVar, 9, null);
                return;
            }
        }
        this.f24778z.writeSampleData(this.f24772t.intValue(), interfaceC20167i.E(), interfaceC20167i.C0());
        this.f24734I = size;
        this.f24739N = jS02;
    }

    public final void K(@j.N InterfaceC20167i interfaceC20167i, @j.N i iVar) {
        if (this.f24773u == null) {
            throw new AssertionError("Video data comes before the track is added to MediaMuxer.");
        }
        long size = interfaceC20167i.size() + this.f24734I;
        long j12 = this.f24740O;
        long j13 = 0;
        if (j12 != 0 && size > j12) {
            String.format("Reach file size limit %d > %d", Long.valueOf(size), Long.valueOf(this.f24740O));
            C20140q0.d(3, "Recorder");
            r(iVar, 2, null);
            return;
        }
        long jS02 = interfaceC20167i.S0();
        long j14 = this.f24736K;
        if (j14 == Long.MAX_VALUE) {
            this.f24736K = jS02;
            String.format("First video time: %d (%s)", Long.valueOf(jS02), androidx.camera.video.internal.c.c(this.f24736K));
            C20140q0.d(3, "Recorder");
        } else {
            TimeUnit timeUnit = TimeUnit.MICROSECONDS;
            long nanos = timeUnit.toNanos(jS02 - Math.min(j14, this.f24737L));
            androidx.core.util.z.g("There should be a previous data for adjusting the duration.", this.f24738M != Long.MAX_VALUE);
            long nanos2 = timeUnit.toNanos(jS02 - this.f24738M) + nanos;
            long j15 = this.f24741P;
            if (j15 != 0 && nanos2 > j15) {
                String.format("Video data reaches duration limit %d > %d", Long.valueOf(nanos2), Long.valueOf(this.f24741P));
                C20140q0.d(3, "Recorder");
                r(iVar, 9, null);
                return;
            }
            j13 = nanos;
        }
        this.f24778z.writeSampleData(this.f24773u.intValue(), interfaceC20167i.E(), interfaceC20167i.C0());
        this.f24734I = size;
        this.f24735J = j13;
        this.f24738M = jS02;
        H();
    }

    @Override // androidx.camera.video.VideoOutput
    public final void a(@j.N K0 k02) {
        b(k02, Timebase.f24048b);
    }

    @Override // androidx.camera.video.VideoOutput
    @RestrictTo
    public final void b(@j.N K0 k02, @j.N Timebase timebase) {
        synchronized (this.f24758f) {
            try {
                Objects.toString(this.f24760h);
                C20140q0.d(3, "Recorder");
                if (this.f24760h == j.f24819j) {
                    A(j.f24811b);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        this.f24755c.execute(new H(this, k02, timebase, 1));
    }

    @Override // androidx.camera.video.VideoOutput
    @j.N
    @RestrictTo
    public final q0<AbstractC20184v> c() {
        return this.f24726A;
    }

    @Override // androidx.camera.video.VideoOutput
    @j.N
    @RestrictTo
    public final q0<W> d() {
        return this.f24752a;
    }

    @Override // androidx.camera.video.VideoOutput
    @RestrictTo
    public final void e(@j.N VideoOutput.SourceState sourceState) {
        this.f24755c.execute(new K(0, this, sourceState));
    }

    @Override // androidx.camera.video.VideoOutput
    @j.N
    @RestrictTo
    public final X f(@j.N InterfaceC20142t interfaceC20142t) {
        return new T((androidx.camera.core.impl.B) interfaceC20142t);
    }

    public final void g(@j.N K0 k02, @j.N Timebase timebase) {
        A value;
        if (k02.f23594f.isDone()) {
            C20140q0.d(5, "Recorder");
            return;
        }
        Executor executor = this.f24755c;
        k02.b(executor, new F(this));
        T t12 = new T((androidx.camera.core.impl.B) k02.f23593e.getCameraInfo());
        androidx.camera.core.F f12 = k02.f23591c;
        T.a aVarD = t12.d(f12);
        Size size = k02.f23590b;
        if (aVarD == null) {
            value = A.f24686g;
        } else {
            TreeMap<Size, A> treeMap = aVarD.f24829b;
            Map.Entry<Size, A> entryCeilingEntry = treeMap.ceilingEntry(size);
            if (entryCeilingEntry != null) {
                value = entryCeilingEntry.getValue();
            } else {
                Map.Entry<Size, A> entryFloorEntry = treeMap.floorEntry(size);
                value = entryFloorEntry != null ? entryFloorEntry.getValue() : A.f24686g;
            }
        }
        Objects.toString(value);
        Objects.toString(size);
        C20140q0.d(3, "Recorder");
        if (value != A.f24686g) {
            androidx.camera.video.internal.f fVarB = t12.b(value, f12);
            this.f24770r = fVarB;
            if (fVarB == null) {
                throw new AssertionError("Camera advertised available quality but did not produce EncoderProfiles  for advertised quality.");
            }
        }
        Objects.toString(this.f24728C);
        C20140q0.d(3, "Recorder");
        l0 l0Var = this.f24750Y;
        l0Var.a();
        androidx.camera.core.impl.utils.futures.f.h(l0Var.f25299j).N(new H(this, k02, timebase, 0), executor);
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x0130 A[Catch: all -> 0x0105, TryCatch #0 {all -> 0x0105, blocks: (B:40:0x00d0, B:42:0x00d4, B:43:0x00dc, B:81:0x016a, B:46:0x00e5, B:48:0x00e9, B:50:0x00ef, B:54:0x00f9, B:58:0x0108, B:61:0x0113, B:62:0x0126, B:65:0x012a, B:67:0x0130, B:68:0x013b, B:70:0x013f, B:72:0x0145, B:76:0x014f, B:77:0x0157, B:79:0x015b, B:95:0x0193, B:96:0x019a), top: B:103:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x013b A[Catch: all -> 0x0105, TryCatch #0 {all -> 0x0105, blocks: (B:40:0x00d0, B:42:0x00d4, B:43:0x00dc, B:81:0x016a, B:46:0x00e5, B:48:0x00e9, B:50:0x00ef, B:54:0x00f9, B:58:0x0108, B:61:0x0113, B:62:0x0126, B:65:0x012a, B:67:0x0130, B:68:0x013b, B:70:0x013f, B:72:0x0145, B:76:0x014f, B:77:0x0157, B:79:0x015b, B:95:0x0193, B:96:0x019a), top: B:103:0x00d0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void h(int r10) {
        /*
            Method dump skipped, instructions count: 448
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.O.h(int):void");
    }

    public final void i(@j.N i iVar, int i12) {
        Uri uri = Uri.EMPTY;
        iVar.a(uri);
        AbstractC20186x abstractC20186xF = iVar.f();
        Throwable th2 = this.f24745T;
        Set<Integer> set = AbstractC20151b.f24860a;
        V vD2 = V.d(0L, 0L, new C20153d(1, 0.0d, th2));
        androidx.core.util.z.f(uri, "OutputUri cannot be null.");
        new C20173j(uri);
        androidx.core.util.z.a("An error type is required.", i12 != 0);
        iVar.m(new m0.a(abstractC20186xF, vD2, i12));
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0044  */
    @j.N
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final androidx.camera.video.V j() {
        /*
            r9 = this;
            long r0 = r9.f24735J
            long r2 = r9.f24734I
            androidx.camera.video.O$g r4 = r9.f24732G
            int r5 = r4.ordinal()
            if (r5 == 0) goto L44
            r6 = 2
            if (r5 == r6) goto L44
            r7 = 5
            r8 = 3
            if (r5 == r8) goto L2f
            r6 = 4
            if (r5 == r6) goto L2d
            if (r5 != r7) goto L19
            goto L45
        L19:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Invalid internal audio state: "
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L2d:
            r6 = r8
            goto L45
        L2f:
            androidx.camera.video.O$i r4 = r9.f24766n
            if (r4 == 0) goto L3d
            java.util.concurrent.atomic.AtomicBoolean r4 = r4.f24807g
            boolean r4 = r4.get()
            if (r4 == 0) goto L3d
            r6 = r7
            goto L45
        L3d:
            boolean r4 = r9.f24746U
            if (r4 == 0) goto L42
            goto L45
        L42:
            r6 = 0
            goto L45
        L44:
            r6 = 1
        L45:
            java.lang.Throwable r4 = r9.f24745T
            double r7 = r9.f24753a0
            java.util.Set<java.lang.Integer> r5 = androidx.camera.video.AbstractC20151b.f24860a
            androidx.camera.video.d r5 = new androidx.camera.video.d
            r5.<init>(r6, r7, r4)
            androidx.camera.video.V r0 = androidx.camera.video.V.d(r0, r2, r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.O.j():androidx.camera.video.V");
    }

    public final boolean m() {
        return this.f24732G == g.f24795e;
    }

    public final boolean n() {
        i iVar = this.f24766n;
        return iVar != null && iVar.j();
    }

    @j.B
    @j.N
    public final i p(@j.N j jVar) {
        boolean z12;
        if (jVar == j.f24813d) {
            z12 = true;
        } else {
            if (jVar != j.f24812c) {
                throw new AssertionError("makePendingRecordingActiveLocked() can only be called from a pending state.");
            }
            z12 = false;
        }
        if (this.f24763k != null) {
            throw new AssertionError("Cannot make pending recording active because another recording is already active.");
        }
        C20176m c20176m = this.f24764l;
        if (c20176m == null) {
            throw new AssertionError("Pending recording should exist when in a PENDING state.");
        }
        this.f24763k = c20176m;
        this.f24764l = null;
        if (z12) {
            A(j.f24816g);
        } else {
            A(j.f24815f);
        }
        return c20176m;
    }

    public final void r(@j.N i iVar, int i12, @j.P Exception exc) {
        boolean z12;
        if (iVar != this.f24766n) {
            throw new AssertionError("Internal error occurred on recording that is not the current in-progress recording.");
        }
        synchronized (this.f24758f) {
            try {
                z12 = false;
                switch (this.f24760h.ordinal()) {
                    case 0:
                    case 3:
                    case 8:
                        throw new AssertionError("In-progress recording error occurred while in unexpected state: " + this.f24760h);
                    case 4:
                    case 5:
                        A(j.f24817h);
                        z12 = true;
                    case 1:
                    case 2:
                    case 6:
                    case 7:
                        if (iVar != this.f24763k) {
                            throw new AssertionError("Internal error occurred for recording but it is not the active recording.");
                        }
                        break;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (z12) {
            F(iVar, -1L, i12, exc);
        }
    }

    public final void s() {
        final androidx.camera.video.internal.audio.i iVar = this.f24727B;
        if (iVar == null) {
            throw new AssertionError("Cannot release null audio source.");
        }
        this.f24727B = null;
        String.format("Releasing audio source: 0x%x", Integer.valueOf(iVar.hashCode()));
        C20140q0.d(3, "Recorder");
        androidx.camera.core.impl.utils.futures.f.a(androidx.concurrent.futures.b.a(new b.c() { // from class: androidx.camera.video.internal.audio.d
            @Override // androidx.concurrent.futures.b.c
            public final Object d(b.a aVar) {
                i iVar2 = iVar;
                iVar2.f24969a.execute(new f(1, iVar2, aVar));
                return "AudioSource-release";
            }
        }), new a(iVar), androidx.camera.core.impl.utils.executor.c.a());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void t(boolean z12) {
        boolean z13;
        boolean z14;
        synchronized (this.f24758f) {
            try {
                z13 = true;
                z14 = false;
                switch (this.f24760h.ordinal()) {
                    case 0:
                    case 3:
                    case 8:
                        break;
                    case 1:
                    case 2:
                        I(j.f24818i);
                        break;
                    case 4:
                    case 5:
                        androidx.core.util.z.g("In-progress recording shouldn't be null when in state " + this.f24760h, this.f24766n != null);
                        if (this.f24763k != this.f24766n) {
                            throw new AssertionError("In-progress recording does not match the active recording. Unable to reset encoder.");
                        }
                        if (!n()) {
                            A(j.f24818i);
                            z14 = true;
                            z13 = false;
                        }
                        break;
                    case 6:
                        A(j.f24818i);
                        z13 = false;
                        break;
                    case 7:
                    default:
                        z13 = false;
                        break;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (!z13) {
            if (z14) {
                F(this.f24766n, -1L, 4, null);
            }
        } else if (z12) {
            v();
        } else {
            u();
        }
    }

    public final void u() {
        if (this.f24730E != null) {
            C20140q0.d(3, "Recorder");
            this.f24730E.release();
            this.f24730E = null;
            this.f24731F = null;
        }
        if (this.f24727B != null) {
            s();
        }
        x(g.f24792b);
        v();
    }

    public final void v() {
        K0 k02;
        boolean z12 = true;
        if (this.f24728C != null) {
            C20140q0.d(3, "Recorder");
            l0 l0Var = this.f24751Z;
            if (l0Var != null) {
                androidx.core.util.z.g(null, l0Var.f25293d == this.f24728C);
                Objects.toString(this.f24728C);
                C20140q0.d(3, "Recorder");
                this.f24751Z.b();
                this.f24751Z = null;
                this.f24728C = null;
                this.f24729D = null;
                z(null);
            } else {
                Objects.toString(this.f24728C);
                C20140q0.d(3, "Recorder");
                l0 l0Var2 = this.f24750Y;
                l0Var2.a();
                androidx.camera.core.impl.utils.futures.f.h(l0Var2.f25299j);
            }
        }
        synchronized (this.f24758f) {
            try {
                switch (this.f24760h.ordinal()) {
                    case 1:
                    case 2:
                        I(j.f24811b);
                        break;
                    case 4:
                    case 5:
                    case 8:
                        if (n()) {
                            z12 = false;
                            break;
                        }
                    case 3:
                    case 6:
                    case 7:
                        A(j.f24811b);
                        break;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        this.f24749X = false;
        if (!z12 || (k02 = this.f24774v) == null || k02.f23594f.isDone()) {
            return;
        }
        g(this.f24774v, this.f24775w);
    }

    @j.B
    public final void w() {
        if (f24719b0.contains(this.f24760h)) {
            A(this.f24761i);
        } else {
            throw new AssertionError("Cannot restore non-pending state when in state " + this.f24760h);
        }
    }

    public final void x(@j.N g gVar) {
        Objects.toString(this.f24732G);
        Objects.toString(gVar);
        C20140q0.d(3, "Recorder");
        this.f24732G = gVar;
    }

    public final void y(@j.P K0.d dVar) {
        Objects.toString(dVar);
        C20140q0.d(3, "Recorder");
        this.f24768p = dVar;
        synchronized (this.f24758f) {
            o0<W> o0Var = this.f24752a;
            int i12 = this.f24762j;
            W.a aVarL = l(this.f24760h);
            W w12 = W.f24841a;
            o0Var.a(new C20178o(i12, aVarL, dVar));
        }
    }

    public final void z(@j.P Surface surface) {
        int iHashCode;
        if (this.f24776x == surface) {
            return;
        }
        this.f24776x = surface;
        synchronized (this.f24758f) {
            if (surface != null) {
                try {
                    iHashCode = surface.hashCode();
                } catch (Throwable th2) {
                    throw th2;
                }
            } else {
                iHashCode = 0;
            }
            B(iHashCode);
        }
    }
}
