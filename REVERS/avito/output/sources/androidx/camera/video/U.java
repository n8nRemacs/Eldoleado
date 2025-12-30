package androidx.camera.video;

import androidx.camera.core.C20140q0;
import androidx.camera.video.O;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: Recording.java */
@j.X
/* loaded from: classes.dex */
public final class U implements AutoCloseable {

    /* renamed from: b, reason: collision with root package name */
    public final AtomicBoolean f24832b;

    /* renamed from: c, reason: collision with root package name */
    public final O f24833c;

    /* renamed from: d, reason: collision with root package name */
    public final long f24834d;

    /* renamed from: e, reason: collision with root package name */
    public final C20183u f24835e;

    /* renamed from: f, reason: collision with root package name */
    public final androidx.camera.core.impl.utils.f f24836f;

    public U(@j.N O o12, long j12, @j.N C20183u c20183u, boolean z12) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.f24832b = atomicBoolean;
        androidx.camera.core.impl.utils.f fVarB = androidx.camera.core.impl.utils.f.b();
        this.f24836f = fVarB;
        this.f24833c = o12;
        this.f24834d = j12;
        this.f24835e = c20183u;
        if (z12) {
            atomicBoolean.set(true);
        } else {
            fVarB.c("stop");
        }
    }

    public final void a(@j.P final RuntimeException runtimeException, final int i12) {
        this.f24836f.a();
        if (this.f24832b.getAndSet(true)) {
            return;
        }
        final O o12 = this.f24833c;
        synchronized (o12.f24758f) {
            try {
                if (!O.o(this, o12.f24764l) && !O.o(this, o12.f24763k)) {
                    Objects.toString(this.f24835e);
                    C20140q0.d(3, "Recorder");
                    return;
                }
                C20176m c20176m = null;
                switch (o12.f24760h.ordinal()) {
                    case 0:
                    case 3:
                        throw new IllegalStateException("Calling stop() while idling or initializing is invalid.");
                    case 1:
                    case 2:
                        androidx.core.util.z.g(null, O.o(this, o12.f24764l));
                        C20176m c20176m2 = o12.f24764l;
                        o12.f24764l = null;
                        o12.w();
                        c20176m = c20176m2;
                        break;
                    case 4:
                    case 5:
                        o12.A(O.j.f24817h);
                        final long micros = TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
                        final C20176m c20176m3 = o12.f24763k;
                        o12.f24755c.execute(new Runnable() { // from class: androidx.camera.video.I
                            @Override // java.lang.Runnable
                            public final void run() throws Exception {
                                Set<O.j> set = O.f24719b0;
                                o12.F(c20176m3, micros, i12, runtimeException);
                            }
                        });
                        break;
                    case 6:
                    case 7:
                        androidx.core.util.z.g(null, O.o(this, o12.f24763k));
                        break;
                }
                if (c20176m != null) {
                    if (i12 == 10) {
                        C20140q0.b("Recorder");
                    }
                    new RuntimeException("Recording was stopped before any data could be produced.", runtimeException);
                    o12.i(c20176m, 8);
                }
            } finally {
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        a(null, 0);
    }

    public final void finalize() throws Throwable {
        try {
            this.f24836f.d();
            a(new RuntimeException("Recording stopped due to being garbage collected."), 10);
        } finally {
            super.finalize();
        }
    }
}
