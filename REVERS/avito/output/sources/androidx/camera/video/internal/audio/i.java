package androidx.camera.video.internal.audio;

import android.content.Context;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.q0;
import androidx.camera.video.internal.BufferProvider;
import androidx.camera.video.internal.audio.AudioStream;
import androidx.camera.video.internal.encoder.E;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import com.google.common.util.concurrent.D0;
import j.N;
import j.P;
import j.X;
import j.a0;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: AudioSource.java */
@X
/* loaded from: classes.dex */
public final class i {

    /* renamed from: a, reason: collision with root package name */
    public final Executor f24969a;

    /* renamed from: d, reason: collision with root package name */
    public final r f24972d;

    /* renamed from: e, reason: collision with root package name */
    public final s f24973e;

    /* renamed from: f, reason: collision with root package name */
    public final long f24974f;

    /* renamed from: i, reason: collision with root package name */
    public boolean f24977i;

    /* renamed from: j, reason: collision with root package name */
    @P
    public Executor f24978j;

    /* renamed from: k, reason: collision with root package name */
    @P
    public b f24979k;

    /* renamed from: l, reason: collision with root package name */
    @P
    public InterfaceC20169k.a f24980l;

    /* renamed from: m, reason: collision with root package name */
    @P
    public androidx.camera.core.impl.utils.futures.c<E> f24981m;

    /* renamed from: n, reason: collision with root package name */
    @P
    public q0.a<BufferProvider.State> f24982n;

    /* renamed from: o, reason: collision with root package name */
    public boolean f24983o;

    /* renamed from: p, reason: collision with root package name */
    public long f24984p;

    /* renamed from: q, reason: collision with root package name */
    public boolean f24985q;

    /* renamed from: r, reason: collision with root package name */
    public boolean f24986r;

    /* renamed from: s, reason: collision with root package name */
    @P
    public byte[] f24987s;

    /* renamed from: t, reason: collision with root package name */
    public double f24988t;

    /* renamed from: v, reason: collision with root package name */
    public final int f24990v;

    /* renamed from: b, reason: collision with root package name */
    public final AtomicReference<Boolean> f24970b = new AtomicReference<>(null);

    /* renamed from: c, reason: collision with root package name */
    public final AtomicBoolean f24971c = new AtomicBoolean(false);

    /* renamed from: g, reason: collision with root package name */
    @N
    public d f24975g = d.f24993b;

    /* renamed from: h, reason: collision with root package name */
    @N
    public BufferProvider.State f24976h = BufferProvider.State.f24943c;

    /* renamed from: u, reason: collision with root package name */
    public long f24989u = 0;

    /* compiled from: AudioSource.java */
    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f24991a;

        static {
            int[] iArr = new int[d.values().length];
            f24991a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f24991a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f24991a[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: AudioSource.java */
    public interface b {
        void a(boolean z12);

        void b(double d12);

        void onError(@N Throwable th2);
    }

    /* compiled from: AudioSource.java */
    public class c implements AudioStream.a {
        public c() {
        }

        public final void a(boolean z12) {
            i iVar = i.this;
            iVar.f24985q = z12;
            if (iVar.f24975g == d.f24994c) {
                iVar.a();
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: AudioSource.java */
    public static final class d {

        /* renamed from: b, reason: collision with root package name */
        public static final d f24993b;

        /* renamed from: c, reason: collision with root package name */
        public static final d f24994c;

        /* renamed from: d, reason: collision with root package name */
        public static final d f24995d;

        /* renamed from: e, reason: collision with root package name */
        public static final /* synthetic */ d[] f24996e;

        static {
            d dVar = new d("CONFIGURED", 0);
            f24993b = dVar;
            d dVar2 = new d("STARTED", 1);
            f24994c = dVar2;
            d dVar3 = new d("RELEASED", 2);
            f24995d = dVar3;
            f24996e = new d[]{dVar, dVar2, dVar3};
        }

        public d() {
            throw null;
        }

        public static d valueOf(String str) {
            return (d) Enum.valueOf(d.class, str);
        }

        public static d[] values() {
            return (d[]) f24996e.clone();
        }
    }

    @a0
    public i(@N androidx.camera.video.internal.audio.a aVar, @N Executor executor, @P Context context) throws AudioSourceAccessException {
        Executor executorF = androidx.camera.core.impl.utils.executor.c.f(executor);
        this.f24969a = executorF;
        this.f24974f = TimeUnit.MILLISECONDS.toNanos(3000L);
        try {
            r rVar = new r(new l(aVar, context), aVar);
            this.f24972d = rVar;
            rVar.a(new c(), executorF);
            this.f24973e = new s(aVar);
            this.f24990v = aVar.b();
        } catch (AudioStream.AudioStreamException | IllegalArgumentException e12) {
            throw new AudioSourceAccessException("Unable to create AudioStream", e12);
        }
    }

    public final void a() {
        Executor executor = this.f24978j;
        b bVar = this.f24979k;
        if (executor == null || bVar == null) {
            return;
        }
        boolean z12 = this.f24986r || this.f24983o || this.f24985q;
        if (Objects.equals(this.f24970b.getAndSet(Boolean.valueOf(z12)), Boolean.valueOf(z12))) {
            return;
        }
        executor.execute(new k(1, bVar, z12));
    }

    public final void b(@P InterfaceC20169k.a aVar) throws ExecutionException, InterruptedException {
        InterfaceC20169k.a aVar2 = this.f24980l;
        BufferProvider.State state = null;
        if (aVar2 != null) {
            q0.a<BufferProvider.State> aVar3 = this.f24982n;
            Objects.requireNonNull(aVar3);
            aVar2.b(aVar3);
            this.f24980l = null;
            this.f24982n = null;
            this.f24981m = null;
            this.f24976h = BufferProvider.State.f24943c;
            e();
        }
        if (aVar != null) {
            this.f24980l = aVar;
            this.f24982n = new g(this, aVar);
            this.f24981m = new h(this, aVar);
            try {
                D0<BufferProvider.State> d0C = aVar.c();
                if (d0C.isDone()) {
                    state = d0C.get();
                }
            } catch (InterruptedException | ExecutionException unused) {
            }
            if (state != null) {
                this.f24976h = state;
                e();
            }
            this.f24980l.d(this.f24969a, this.f24982n);
        }
    }

    public final void c(d dVar) {
        Objects.toString(this.f24975g);
        Objects.toString(dVar);
        C20140q0.d(3, "AudioSource");
        this.f24975g = dVar;
    }

    public final void d() {
        if (this.f24977i) {
            this.f24977i = false;
            C20140q0.d(3, "AudioSource");
            this.f24972d.stop();
        }
    }

    public final void e() {
        if (this.f24975g != d.f24994c) {
            d();
            return;
        }
        boolean z12 = this.f24976h == BufferProvider.State.f24942b;
        boolean z13 = !z12;
        Executor executor = this.f24978j;
        b bVar = this.f24979k;
        if (executor != null && bVar != null && this.f24971c.getAndSet(z13) != z13) {
            executor.execute(new androidx.camera.video.internal.audio.b(bVar, z13));
        }
        if (!z12) {
            d();
            return;
        }
        if (this.f24977i) {
            return;
        }
        try {
            C20140q0.d(3, "AudioSource");
            this.f24972d.start();
            this.f24983o = false;
        } catch (AudioStream.AudioStreamException unused) {
            C20140q0.d(5, "AudioSource");
            this.f24983o = true;
            this.f24973e.start();
            this.f24984p = System.nanoTime();
            a();
        }
        this.f24977i = true;
        InterfaceC20169k.a aVar = this.f24980l;
        Objects.requireNonNull(aVar);
        D0<E> d0E = aVar.e();
        androidx.camera.core.impl.utils.futures.c<E> cVar = this.f24981m;
        Objects.requireNonNull(cVar);
        androidx.camera.core.impl.utils.futures.f.a(d0E, cVar, this.f24969a);
    }
}
