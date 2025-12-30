package androidx.camera.video.internal.encoder;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.util.Range;
import android.view.Surface;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.Timebase;
import androidx.camera.core.impl.q0;
import androidx.camera.video.i0;
import androidx.camera.video.internal.BufferProvider;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import androidx.camera.video.internal.encoder.v;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.InterfaceC42164u;
import j.P;
import j.X;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: EncoderImpl.java */
@X
/* loaded from: classes.dex */
public class v implements InterfaceC20169k {

    /* renamed from: D, reason: collision with root package name */
    public static final Range<Long> f25192D = Range.create(Long.MAX_VALUE, Long.MAX_VALUE);

    /* renamed from: a, reason: collision with root package name */
    public final String f25196a;

    /* renamed from: c, reason: collision with root package name */
    public final boolean f25198c;

    /* renamed from: d, reason: collision with root package name */
    public final MediaFormat f25199d;

    /* renamed from: e, reason: collision with root package name */
    public final MediaCodec f25200e;

    /* renamed from: f, reason: collision with root package name */
    public final InterfaceC20169k.b f25201f;

    /* renamed from: g, reason: collision with root package name */
    public final D f25202g;

    /* renamed from: h, reason: collision with root package name */
    public final Executor f25203h;

    /* renamed from: i, reason: collision with root package name */
    public final D0<Void> f25204i;

    /* renamed from: j, reason: collision with root package name */
    public final b.a<Void> f25205j;

    /* renamed from: p, reason: collision with root package name */
    public final Timebase f25211p;

    /* renamed from: t, reason: collision with root package name */
    public d f25215t;

    /* renamed from: b, reason: collision with root package name */
    public final Object f25197b = new Object();

    /* renamed from: k, reason: collision with root package name */
    public final ArrayDeque f25206k = new ArrayDeque();

    /* renamed from: l, reason: collision with root package name */
    public final ArrayDeque f25207l = new ArrayDeque();

    /* renamed from: m, reason: collision with root package name */
    public final HashSet f25208m = new HashSet();

    /* renamed from: n, reason: collision with root package name */
    public final HashSet f25209n = new HashSet();

    /* renamed from: o, reason: collision with root package name */
    public final ArrayDeque f25210o = new ArrayDeque();

    /* renamed from: q, reason: collision with root package name */
    public final H f25212q = new H();

    /* renamed from: r, reason: collision with root package name */
    @j.B
    public InterfaceC20170l f25213r = InterfaceC20170l.f25174a;

    /* renamed from: s, reason: collision with root package name */
    @j.B
    public Executor f25214s = androidx.camera.core.impl.utils.executor.c.a();

    /* renamed from: u, reason: collision with root package name */
    public Range<Long> f25216u = f25192D;

    /* renamed from: v, reason: collision with root package name */
    public long f25217v = 0;

    /* renamed from: w, reason: collision with root package name */
    public boolean f25218w = false;

    /* renamed from: x, reason: collision with root package name */
    public Long f25219x = null;

    /* renamed from: y, reason: collision with root package name */
    public ScheduledFuture f25220y = null;

    /* renamed from: z, reason: collision with root package name */
    public e f25221z = null;

    /* renamed from: A, reason: collision with root package name */
    public boolean f25193A = false;

    /* renamed from: B, reason: collision with root package name */
    public boolean f25194B = false;

    /* renamed from: C, reason: collision with root package name */
    public boolean f25195C = false;

    /* compiled from: EncoderImpl.java */
    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f25222a;

        static {
            int[] iArr = new int[d.values().length];
            f25222a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f25222a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f25222a[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f25222a[3] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f25222a[5] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f25222a[4] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f25222a[6] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f25222a[7] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f25222a[8] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    /* compiled from: EncoderImpl.java */
    @X
    public static class b {
        @InterfaceC42164u
        @j.N
        public static Surface a() {
            return MediaCodec.createPersistentInputSurface();
        }

        @InterfaceC42164u
        public static void b(@j.N MediaCodec mediaCodec, @j.N Surface surface) {
            mediaCodec.setInputSurface(surface);
        }
    }

    /* compiled from: EncoderImpl.java */
    public class c implements InterfaceC20169k.a {

        /* renamed from: a, reason: collision with root package name */
        public final LinkedHashMap f25223a = new LinkedHashMap();

        /* renamed from: b, reason: collision with root package name */
        public BufferProvider.State f25224b = BufferProvider.State.f24943c;

        /* renamed from: c, reason: collision with root package name */
        public final ArrayList f25225c = new ArrayList();

        public c() {
        }

        @Override // androidx.camera.core.impl.q0
        public final void b(@j.N q0.a<? super BufferProvider.State> aVar) {
            v.this.f25203h.execute(new s(1, this, aVar));
        }

        @Override // androidx.camera.core.impl.q0
        @j.N
        public final D0<BufferProvider.State> c() {
            return androidx.concurrent.futures.b.a(new w(this, 0));
        }

        @Override // androidx.camera.core.impl.q0
        public final void d(@j.N Executor executor, @j.N q0.a<? super BufferProvider.State> aVar) {
            v.this.f25203h.execute(new q(this, aVar, executor, 1));
        }

        @Override // androidx.camera.video.internal.BufferProvider
        @j.N
        public final D0<E> e() {
            return androidx.concurrent.futures.b.a(new w(this, 1));
        }

        public final void f(boolean z12) {
            BufferProvider.State state = BufferProvider.State.f24943c;
            BufferProvider.State state2 = z12 ? BufferProvider.State.f24942b : state;
            if (this.f25224b == state2) {
                return;
            }
            this.f25224b = state2;
            if (state2 == state) {
                ArrayList arrayList = this.f25225c;
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((D0) it.next()).cancel(true);
                }
                arrayList.clear();
            }
            for (Map.Entry entry : this.f25223a.entrySet()) {
                try {
                    ((Executor) entry.getValue()).execute(new s(7, entry, state2));
                } catch (RejectedExecutionException unused) {
                    C20140q0.c(v.this.f25196a);
                }
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: EncoderImpl.java */
    public static final class d {

        /* renamed from: b, reason: collision with root package name */
        public static final d f25227b;

        /* renamed from: c, reason: collision with root package name */
        public static final d f25228c;

        /* renamed from: d, reason: collision with root package name */
        public static final d f25229d;

        /* renamed from: e, reason: collision with root package name */
        public static final d f25230e;

        /* renamed from: f, reason: collision with root package name */
        public static final d f25231f;

        /* renamed from: g, reason: collision with root package name */
        public static final d f25232g;

        /* renamed from: h, reason: collision with root package name */
        public static final d f25233h;

        /* renamed from: i, reason: collision with root package name */
        public static final d f25234i;

        /* renamed from: j, reason: collision with root package name */
        public static final d f25235j;

        /* renamed from: k, reason: collision with root package name */
        public static final /* synthetic */ d[] f25236k;

        static {
            d dVar = new d("CONFIGURED", 0);
            f25227b = dVar;
            d dVar2 = new d("STARTED", 1);
            f25228c = dVar2;
            d dVar3 = new d("PAUSED", 2);
            f25229d = dVar3;
            d dVar4 = new d("STOPPING", 3);
            f25230e = dVar4;
            d dVar5 = new d("PENDING_START", 4);
            f25231f = dVar5;
            d dVar6 = new d("PENDING_START_PAUSED", 5);
            f25232g = dVar6;
            d dVar7 = new d("PENDING_RELEASE", 6);
            f25233h = dVar7;
            d dVar8 = new d("ERROR", 7);
            f25234i = dVar8;
            d dVar9 = new d("RELEASED", 8);
            f25235j = dVar9;
            f25236k = new d[]{dVar, dVar2, dVar3, dVar4, dVar5, dVar6, dVar7, dVar8, dVar9};
        }

        public d() {
            throw null;
        }

        public static d valueOf(String str) {
            return (d) Enum.valueOf(d.class, str);
        }

        public static d[] values() {
            return (d[]) f25236k.clone();
        }
    }

    /* compiled from: EncoderImpl.java */
    @X
    public class e extends MediaCodec.Callback {

        /* renamed from: k, reason: collision with root package name */
        public static final /* synthetic */ int f25237k = 0;

        /* renamed from: a, reason: collision with root package name */
        @P
        public final androidx.camera.video.internal.workaround.e f25238a;

        /* renamed from: b, reason: collision with root package name */
        public boolean f25239b = false;

        /* renamed from: c, reason: collision with root package name */
        public boolean f25240c = false;

        /* renamed from: d, reason: collision with root package name */
        public boolean f25241d = false;

        /* renamed from: e, reason: collision with root package name */
        public long f25242e = 0;

        /* renamed from: f, reason: collision with root package name */
        public long f25243f = 0;

        /* renamed from: g, reason: collision with root package name */
        public boolean f25244g = false;

        /* renamed from: h, reason: collision with root package name */
        public boolean f25245h = false;

        /* renamed from: i, reason: collision with root package name */
        public boolean f25246i = false;

        /* compiled from: EncoderImpl.java */
        public class a implements androidx.camera.core.impl.utils.futures.c<Void> {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ C20168j f25248a;

            public a(C20168j c20168j) {
                this.f25248a = c20168j;
            }

            @Override // androidx.camera.core.impl.utils.futures.c
            public final void a(@j.N Throwable th2) {
                e eVar = e.this;
                v.this.f25209n.remove(this.f25248a);
                boolean z12 = th2 instanceof MediaCodec.CodecException;
                v vVar = v.this;
                if (!z12) {
                    vVar.g(0, th2.getMessage(), th2);
                    return;
                }
                MediaCodec.CodecException codecException = (MediaCodec.CodecException) th2;
                vVar.getClass();
                vVar.g(1, codecException.getMessage(), codecException);
            }

            @Override // androidx.camera.core.impl.utils.futures.c
            public final void onSuccess(@P Void r22) {
                v.this.f25209n.remove(this.f25248a);
            }
        }

        public e() {
            Timebase timebase = null;
            if (!v.this.f25198c) {
                this.f25238a = null;
                return;
            }
            if (androidx.camera.video.internal.compat.quirk.e.f25058a.b(androidx.camera.video.internal.compat.quirk.c.class) != null) {
                C20140q0.d(5, v.this.f25196a);
            } else {
                timebase = v.this.f25211p;
            }
            this.f25238a = new androidx.camera.video.internal.workaround.e(v.this.f25212q, timebase);
        }

        public final void a(@j.N C20168j c20168j, @j.N InterfaceC20170l interfaceC20170l, @j.N Executor executor) {
            v vVar = v.this;
            vVar.f25209n.add(c20168j);
            androidx.camera.core.impl.utils.futures.f.a(androidx.camera.core.impl.utils.futures.f.h(c20168j.f25171f), new a(c20168j), vVar.f25203h);
            try {
                executor.execute(new s(5, interfaceC20170l, c20168j));
            } catch (RejectedExecutionException unused) {
                C20140q0.c(vVar.f25196a);
                c20168j.close();
            }
        }

        @Override // android.media.MediaCodec.Callback
        public final void onError(@j.N MediaCodec mediaCodec, @j.N MediaCodec.CodecException codecException) {
            v.this.f25203h.execute(new s(2, this, codecException));
        }

        @Override // android.media.MediaCodec.Callback
        public final void onInputBufferAvailable(@j.N MediaCodec mediaCodec, final int i12) {
            v.this.f25203h.execute(new Runnable() { // from class: androidx.camera.video.internal.encoder.z
                @Override // java.lang.Runnable
                public final void run() {
                    v.e eVar = this.f25264b;
                    boolean z12 = eVar.f25246i;
                    v vVar = v.this;
                    if (z12) {
                        C20140q0.d(5, vVar.f25196a);
                        return;
                    }
                    switch (vVar.f25215t.ordinal()) {
                        case 0:
                        case 7:
                        case 8:
                            return;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            vVar.f25206k.offer(Integer.valueOf(i12));
                            vVar.h();
                            return;
                        default:
                            throw new IllegalStateException("Unknown state: " + vVar.f25215t);
                    }
                }
            });
        }

        @Override // android.media.MediaCodec.Callback
        public final void onOutputBufferAvailable(@j.N MediaCodec mediaCodec, int i12, @j.N MediaCodec.BufferInfo bufferInfo) {
            v.this.f25203h.execute(new B(this, bufferInfo, mediaCodec, i12));
        }

        @Override // android.media.MediaCodec.Callback
        public final void onOutputFormatChanged(@j.N MediaCodec mediaCodec, @j.N MediaFormat mediaFormat) {
            v.this.f25203h.execute(new s(3, this, mediaFormat));
        }
    }

    /* compiled from: EncoderImpl.java */
    @X
    public class f implements InterfaceC20169k.c {

        /* renamed from: b, reason: collision with root package name */
        @j.B
        public Surface f25251b;

        /* renamed from: d, reason: collision with root package name */
        @j.B
        public i0 f25253d;

        /* renamed from: e, reason: collision with root package name */
        @j.B
        public Executor f25254e;

        /* renamed from: a, reason: collision with root package name */
        public final Object f25250a = new Object();

        /* renamed from: c, reason: collision with root package name */
        @j.B
        public final HashSet f25252c = new HashSet();

        public f() {
        }

        @Override // androidx.camera.video.internal.encoder.InterfaceC20169k.c
        public final void a(@j.N Executor executor, @j.N i0 i0Var) {
            Surface surface;
            synchronized (this.f25250a) {
                this.f25253d = i0Var;
                executor.getClass();
                this.f25254e = executor;
                surface = this.f25251b;
            }
            if (surface != null) {
                try {
                    executor.execute(new s(9, i0Var, surface));
                } catch (RejectedExecutionException unused) {
                    C20140q0.c(v.this.f25196a);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public v(@j.N Executor executor, @j.N InterfaceC20171m interfaceC20171m) throws InvalidConfigException {
        D m12;
        androidx.camera.video.internal.workaround.b bVar = new androidx.camera.video.internal.workaround.b();
        executor.getClass();
        interfaceC20171m.getClass();
        this.f25203h = androidx.camera.core.impl.utils.executor.c.f(executor);
        if (interfaceC20171m instanceof AbstractC20159a) {
            this.f25196a = "AudioEncoder";
            this.f25198c = false;
            this.f25201f = new c();
        } else {
            if (!(interfaceC20171m instanceof J)) {
                throw new InvalidConfigException("Unknown encoder config type");
            }
            this.f25196a = "VideoEncoder";
            this.f25198c = true;
            this.f25201f = new f();
        }
        Timebase timebaseB = interfaceC20171m.b();
        this.f25211p = timebaseB;
        String str = this.f25196a;
        Objects.toString(timebaseB);
        C20140q0.d(3, str);
        MediaFormat mediaFormatC = interfaceC20171m.c();
        this.f25199d = mediaFormatC;
        String str2 = this.f25196a;
        Objects.toString(mediaFormatC);
        C20140q0.d(3, str2);
        MediaCodec mediaCodecA = bVar.a(mediaFormatC);
        this.f25200e = mediaCodecA;
        String str3 = this.f25196a;
        mediaCodecA.getName();
        C20140q0.d(4, str3);
        boolean z12 = this.f25198c;
        MediaCodecInfo codecInfo = mediaCodecA.getCodecInfo();
        String strA = interfaceC20171m.a();
        if (z12) {
            m12 = new M(codecInfo, strA);
        } else {
            C20161c c20161c = new C20161c(codecInfo, strA);
            Objects.requireNonNull(c20161c.f25114a.getAudioCapabilities());
            m12 = c20161c;
        }
        this.f25202g = m12;
        boolean z13 = this.f25198c;
        if (z13) {
            L l12 = (L) m12;
            androidx.core.util.z.g(null, z13);
            if (mediaFormatC.containsKey("bitrate")) {
                int integer = mediaFormatC.getInteger("bitrate");
                int iIntValue = ((Integer) l12.b().clamp(Integer.valueOf(integer))).intValue();
                if (integer != iIntValue) {
                    mediaFormatC.setInteger("bitrate", iIntValue);
                    C20140q0.d(3, this.f25196a);
                }
            }
        }
        try {
            l();
            AtomicReference atomicReference = new AtomicReference();
            this.f25204i = androidx.camera.core.impl.utils.futures.f.h(androidx.concurrent.futures.b.a(new C20165g(atomicReference, 3)));
            b.a<Void> aVar = (b.a) atomicReference.get();
            aVar.getClass();
            this.f25205j = aVar;
            m(d.f25227b);
        } catch (MediaCodec.CodecException e12) {
            throw new InvalidConfigException(e12);
        }
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20169k
    public final void a(@j.N InterfaceC20170l interfaceC20170l, @j.N Executor executor) {
        synchronized (this.f25197b) {
            this.f25213r = interfaceC20170l;
            this.f25214s = executor;
        }
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20169k
    @j.N
    public final D0<Void> b() {
        return this.f25204i;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20169k
    public final int c() {
        MediaFormat mediaFormat = this.f25199d;
        if (mediaFormat.containsKey("bitrate")) {
            return mediaFormat.getInteger("bitrate");
        }
        return 0;
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20169k
    public final void d(final long j12) {
        final long jB2 = this.f25212q.b();
        this.f25203h.execute(new Runnable() { // from class: androidx.camera.video.internal.encoder.r
            @Override // java.lang.Runnable
            public final void run() {
                v vVar = this.f25184b;
                switch (vVar.f25215t.ordinal()) {
                    case 0:
                    case 3:
                    case 7:
                        return;
                    case 1:
                    case 2:
                        v.d dVar = vVar.f25215t;
                        vVar.m(v.d.f25230e);
                        Long l12 = (Long) vVar.f25216u.getLower();
                        long jLongValue = l12.longValue();
                        if (jLongValue == Long.MAX_VALUE) {
                            throw new AssertionError("There should be a \"start\" before \"stop\"");
                        }
                        long j13 = j12;
                        String str = vVar.f25196a;
                        if (j13 == -1) {
                            j13 = jB2;
                        } else if (j13 < jLongValue) {
                            C20140q0.d(5, str);
                            j13 = jB2;
                        }
                        if (j13 < jLongValue) {
                            throw new AssertionError("The start time should be before the stop time.");
                        }
                        vVar.f25216u = Range.create(l12, Long.valueOf(j13));
                        androidx.camera.video.internal.c.c(j13);
                        C20140q0.d(3, str);
                        if (dVar == v.d.f25229d && vVar.f25219x != null) {
                            vVar.n();
                            return;
                        } else {
                            vVar.f25218w = true;
                            vVar.f25220y = androidx.camera.core.impl.utils.executor.c.d().schedule(new p(vVar, 3), 1000L, TimeUnit.MILLISECONDS);
                            return;
                        }
                    case 4:
                    case 5:
                        vVar.m(v.d.f25227b);
                        return;
                    case 6:
                    case 8:
                        throw new IllegalStateException("Encoder is released");
                    default:
                        throw new IllegalStateException("Unknown state: " + vVar.f25215t);
                }
            }
        });
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20169k
    public final void e() {
        this.f25203h.execute(new p(this, 1));
    }

    @j.N
    public final D0<E> f() {
        switch (this.f25215t.ordinal()) {
            case 0:
                return androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("Encoder is not started yet."));
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                AtomicReference atomicReference = new AtomicReference();
                D0<E> d0A = androidx.concurrent.futures.b.a(new C20165g(atomicReference, 2));
                b.a aVar = (b.a) atomicReference.get();
                aVar.getClass();
                this.f25207l.offer(aVar);
                aVar.a(new s(6, this, aVar), this.f25203h);
                h();
                return d0A;
            case 7:
                return androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("Encoder is in error state."));
            case 8:
                return androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("Encoder is released."));
            default:
                throw new IllegalStateException("Unknown state: " + this.f25215t);
        }
    }

    public final void g(int i12, @P String str, @P Throwable th2) {
        switch (this.f25215t.ordinal()) {
            case 0:
                i(i12, str, th2);
                l();
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                m(d.f25234i);
                o(new B(this, i12, str, th2));
                break;
            case 7:
                C20140q0.d(5, this.f25196a);
                break;
        }
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20169k
    @j.N
    public final D getEncoderInfo() {
        return this.f25202g;
    }

    public final void h() {
        while (true) {
            ArrayDeque arrayDeque = this.f25207l;
            if (arrayDeque.isEmpty()) {
                return;
            }
            ArrayDeque arrayDeque2 = this.f25206k;
            if (arrayDeque2.isEmpty()) {
                return;
            }
            b.a aVar = (b.a) arrayDeque.poll();
            Objects.requireNonNull(aVar);
            Integer num = (Integer) arrayDeque2.poll();
            Objects.requireNonNull(num);
            try {
                F f12 = new F(this.f25200e, num.intValue());
                if (aVar.b(f12)) {
                    this.f25208m.add(f12);
                    androidx.camera.core.impl.utils.futures.f.h(f12.f25118d).N(new s(0, this, f12), this.f25203h);
                } else {
                    f12.cancel();
                }
            } catch (MediaCodec.CodecException e12) {
                g(1, e12.getMessage(), e12);
                return;
            }
        }
    }

    public final void i(int i12, @P String str, @P Throwable th2) {
        InterfaceC20170l interfaceC20170l;
        Executor executor;
        synchronized (this.f25197b) {
            interfaceC20170l = this.f25213r;
            executor = this.f25214s;
        }
        try {
            executor.execute(new q(interfaceC20170l, i12, str, th2));
        } catch (RejectedExecutionException unused) {
            C20140q0.c(this.f25196a);
        }
    }

    public final void j() {
        Surface surface;
        HashSet hashSet;
        if (this.f25193A) {
            this.f25200e.stop();
            this.f25193A = false;
        }
        this.f25200e.release();
        InterfaceC20169k.b bVar = this.f25201f;
        if (bVar instanceof f) {
            f fVar = (f) bVar;
            synchronized (fVar.f25250a) {
                surface = fVar.f25251b;
                fVar.f25251b = null;
                hashSet = new HashSet(fVar.f25252c);
                fVar.f25252c.clear();
            }
            if (surface != null) {
                surface.release();
            }
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                ((Surface) it.next()).release();
            }
        }
        m(d.f25235j);
        this.f25205j.b(null);
    }

    public final void k() {
        this.f25200e.setParameters(AK.c.e(0, "request-sync"));
    }

    public final void l() {
        i0 i0Var;
        Executor executor;
        this.f25216u = f25192D;
        this.f25217v = 0L;
        this.f25210o.clear();
        this.f25206k.clear();
        Iterator it = this.f25207l.iterator();
        while (it.hasNext()) {
            ((b.a) it.next()).c();
        }
        this.f25207l.clear();
        this.f25200e.reset();
        this.f25193A = false;
        this.f25194B = false;
        this.f25195C = false;
        this.f25218w = false;
        ScheduledFuture scheduledFuture = this.f25220y;
        Surface surfaceCreateInputSurface = null;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.f25220y = null;
        }
        e eVar = this.f25221z;
        if (eVar != null) {
            eVar.f25246i = true;
        }
        e eVar2 = new e();
        this.f25221z = eVar2;
        this.f25200e.setCallback(eVar2);
        this.f25200e.configure(this.f25199d, (Surface) null, (MediaCrypto) null, 1);
        InterfaceC20169k.b bVar = this.f25201f;
        if (bVar instanceof f) {
            f fVar = (f) bVar;
            fVar.getClass();
            androidx.camera.video.internal.compat.quirk.g gVar = (androidx.camera.video.internal.compat.quirk.g) androidx.camera.video.internal.compat.quirk.e.f25058a.b(androidx.camera.video.internal.compat.quirk.g.class);
            synchronized (fVar.f25250a) {
                try {
                    if (gVar == null) {
                        if (fVar.f25251b == null) {
                            surfaceCreateInputSurface = b.a();
                            fVar.f25251b = surfaceCreateInputSurface;
                        }
                        b.b(v.this.f25200e, fVar.f25251b);
                    } else {
                        Surface surface = fVar.f25251b;
                        if (surface != null) {
                            fVar.f25252c.add(surface);
                        }
                        surfaceCreateInputSurface = v.this.f25200e.createInputSurface();
                        fVar.f25251b = surfaceCreateInputSurface;
                    }
                    i0Var = fVar.f25253d;
                    executor = fVar.f25254e;
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            if (surfaceCreateInputSurface == null || i0Var == null || executor == null) {
                return;
            }
            try {
                executor.execute(new s(9, i0Var, surfaceCreateInputSurface));
            } catch (RejectedExecutionException unused) {
                C20140q0.c(v.this.f25196a);
            }
        }
    }

    public final void m(d dVar) {
        d dVar2 = this.f25215t;
        if (dVar2 == dVar) {
            return;
        }
        Objects.toString(dVar2);
        Objects.toString(dVar);
        C20140q0.d(3, this.f25196a);
        this.f25215t = dVar;
    }

    public final void n() {
        InterfaceC20169k.b bVar = this.f25201f;
        if (bVar instanceof c) {
            ((c) bVar).f(false);
            ArrayList arrayList = new ArrayList();
            Iterator it = this.f25208m.iterator();
            while (it.hasNext()) {
                arrayList.add(((E) it.next()).d());
            }
            androidx.camera.core.impl.utils.futures.f.k(arrayList).N(new p(this, 0), this.f25203h);
            return;
        }
        if (bVar instanceof f) {
            try {
                this.f25200e.signalEndOfInputStream();
                this.f25195C = true;
            } catch (MediaCodec.CodecException e12) {
                g(1, e12.getMessage(), e12);
            }
        }
    }

    public final void o(@P Runnable runnable) {
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = this.f25209n;
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            arrayList.add(androidx.camera.core.impl.utils.futures.f.h(((C20168j) it.next()).f25171f));
        }
        HashSet hashSet2 = this.f25208m;
        Iterator it2 = hashSet2.iterator();
        while (it2.hasNext()) {
            arrayList.add(((E) it2.next()).d());
        }
        if (!arrayList.isEmpty()) {
            hashSet.size();
            hashSet2.size();
            C20140q0.d(3, this.f25196a);
        }
        androidx.camera.core.impl.utils.futures.f.k(arrayList).N(new q(this, arrayList, runnable, 0), this.f25203h);
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20169k
    public final void pause() {
        this.f25203h.execute(new o(this, this.f25212q.b(), 0));
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20169k
    public final void release() {
        this.f25203h.execute(new p(this, 2));
    }

    @Override // androidx.camera.video.internal.encoder.InterfaceC20169k
    public final void start() {
        this.f25203h.execute(new o(this, this.f25212q.b(), 1));
    }
}
