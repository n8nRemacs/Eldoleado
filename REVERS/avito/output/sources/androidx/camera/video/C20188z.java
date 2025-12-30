package androidx.camera.video;

import android.content.Context;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.camera.video.O;
import androidx.camera.video.VideoOutput;
import j.InterfaceC42154j;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: PendingRecording.java */
@j.X
/* renamed from: androidx.camera.video.z, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20188z {

    /* renamed from: a, reason: collision with root package name */
    public final Context f25338a;

    /* renamed from: b, reason: collision with root package name */
    public final O f25339b;

    /* renamed from: c, reason: collision with root package name */
    public final C20183u f25340c;

    /* renamed from: d, reason: collision with root package name */
    public androidx.camera.core.internal.d f25341d;

    /* renamed from: e, reason: collision with root package name */
    public Executor f25342e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f25343f = false;

    public C20188z(@j.N Context context, @j.N O o12, @j.N C20183u c20183u) {
        this.f25338a = androidx.camera.core.impl.utils.h.a(context);
        this.f25339b = o12;
        this.f25340c = c20183u;
    }

    @j.N
    @InterfaceC42154j
    public final U a(@j.N Executor executor, @j.N androidx.camera.core.internal.d dVar) {
        long j12;
        C20176m c20176m;
        int i12;
        C20176m c20176m2;
        androidx.core.util.z.f(executor, "Listener Executor can't be null.");
        this.f25342e = executor;
        this.f25341d = dVar;
        final O o12 = this.f25339b;
        o12.getClass();
        synchronized (o12.f24758f) {
            try {
                j12 = o12.f24765m + 1;
                o12.f24765m = j12;
                c20176m = null;
                i12 = 0;
                switch (o12.f24760h.ordinal()) {
                    case 0:
                    case 3:
                    case 6:
                    case 7:
                    case 8:
                        O.j jVar = o12.f24760h;
                        O.j jVar2 = O.j.f24814e;
                        if (jVar == jVar2) {
                            androidx.core.util.z.g("Expected recorder to be idle but a recording is either pending or in progress.", o12.f24763k == null && o12.f24764l == null);
                        }
                        try {
                            C20176m c20176m3 = new C20176m(this.f25340c, this.f25342e, this.f25341d, this.f25343f, j12);
                            c20176m3.i(this.f25338a);
                            o12.f24764l = c20176m3;
                            O.j jVar3 = o12.f24760h;
                            if (jVar3 == jVar2) {
                                o12.A(O.j.f24812c);
                                final int i13 = 0;
                                o12.f24755c.execute(new Runnable() { // from class: androidx.camera.video.L
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        boolean z12;
                                        O.i iVarP;
                                        int i14;
                                        C20176m c20176m4;
                                        switch (i13) {
                                            case 0:
                                                O o13 = o12;
                                                synchronized (o13.f24758f) {
                                                    try {
                                                        int iOrdinal = o13.f24760h.ordinal();
                                                        boolean z13 = true;
                                                        z12 = false;
                                                        iVarP = null;
                                                        if (iOrdinal != 1) {
                                                            if (iOrdinal != 2) {
                                                                i14 = 0;
                                                            }
                                                            c20176m4 = null;
                                                        } else {
                                                            z13 = false;
                                                        }
                                                        if (o13.f24763k == null && !o13.f24749X) {
                                                            if (o13.f24747V == VideoOutput.SourceState.f24839d) {
                                                                C20176m c20176m5 = o13.f24764l;
                                                                o13.f24764l = null;
                                                                o13.w();
                                                                Set<O.j> set = O.f24719b0;
                                                                boolean z14 = z13;
                                                                c20176m4 = c20176m5;
                                                                i14 = 4;
                                                                z12 = z14;
                                                            } else if (o13.f24728C != null) {
                                                                iVarP = o13.p(o13.f24760h);
                                                                i14 = 0;
                                                                z12 = z13;
                                                                c20176m4 = null;
                                                            }
                                                        }
                                                        i14 = 0;
                                                        z12 = z13;
                                                        c20176m4 = null;
                                                    } catch (Throwable th2) {
                                                        throw th2;
                                                    }
                                                }
                                                if (iVarP != null) {
                                                    o13.E(iVarP, z12);
                                                    return;
                                                } else {
                                                    if (c20176m4 != null) {
                                                        o13.i(c20176m4, i14);
                                                        return;
                                                    }
                                                    return;
                                                }
                                            default:
                                                O o14 = o12;
                                                K0 k02 = o14.f24774v;
                                                if (k02 == null) {
                                                    throw new AssertionError("surface request is required to retry initialization.");
                                                }
                                                o14.g(k02, o14.f24775w);
                                                return;
                                        }
                                    }
                                });
                            } else if (jVar3 == O.j.f24819j) {
                                o12.A(O.j.f24812c);
                                final int i14 = 1;
                                o12.f24755c.execute(new Runnable() { // from class: androidx.camera.video.L
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        boolean z12;
                                        O.i iVarP;
                                        int i142;
                                        C20176m c20176m4;
                                        switch (i14) {
                                            case 0:
                                                O o13 = o12;
                                                synchronized (o13.f24758f) {
                                                    try {
                                                        int iOrdinal = o13.f24760h.ordinal();
                                                        boolean z13 = true;
                                                        z12 = false;
                                                        iVarP = null;
                                                        if (iOrdinal != 1) {
                                                            if (iOrdinal != 2) {
                                                                i142 = 0;
                                                            }
                                                            c20176m4 = null;
                                                        } else {
                                                            z13 = false;
                                                        }
                                                        if (o13.f24763k == null && !o13.f24749X) {
                                                            if (o13.f24747V == VideoOutput.SourceState.f24839d) {
                                                                C20176m c20176m5 = o13.f24764l;
                                                                o13.f24764l = null;
                                                                o13.w();
                                                                Set<O.j> set = O.f24719b0;
                                                                boolean z14 = z13;
                                                                c20176m4 = c20176m5;
                                                                i142 = 4;
                                                                z12 = z14;
                                                            } else if (o13.f24728C != null) {
                                                                iVarP = o13.p(o13.f24760h);
                                                                i142 = 0;
                                                                z12 = z13;
                                                                c20176m4 = null;
                                                            }
                                                        }
                                                        i142 = 0;
                                                        z12 = z13;
                                                        c20176m4 = null;
                                                    } catch (Throwable th2) {
                                                        throw th2;
                                                    }
                                                }
                                                if (iVarP != null) {
                                                    o13.E(iVarP, z12);
                                                    return;
                                                } else {
                                                    if (c20176m4 != null) {
                                                        o13.i(c20176m4, i142);
                                                        return;
                                                    }
                                                    return;
                                                }
                                            default:
                                                O o14 = o12;
                                                K0 k02 = o14.f24774v;
                                                if (k02 == null) {
                                                    throw new AssertionError("surface request is required to retry initialization.");
                                                }
                                                o14.g(k02, o14.f24775w);
                                                return;
                                        }
                                    }
                                });
                            } else {
                                o12.A(O.j.f24812c);
                            }
                            e = null;
                            break;
                        } catch (IOException e12) {
                            e = e12;
                            i12 = 5;
                            break;
                        }
                        break;
                    case 1:
                    case 2:
                        c20176m2 = o12.f24764l;
                        c20176m2.getClass();
                        c20176m = c20176m2;
                        e = null;
                        break;
                    case 4:
                    case 5:
                        c20176m2 = o12.f24763k;
                        c20176m = c20176m2;
                        e = null;
                        break;
                    default:
                        e = null;
                        break;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (c20176m != null) {
            throw new IllegalStateException("A recording is already in progress. Previous recordings must be stopped before a new recording can be started.");
        }
        if (i12 == 0) {
            return new U(this.f25339b, j12, this.f25340c, false);
        }
        Objects.toString(e);
        C20140q0.b("Recorder");
        o12.i(new C20176m(this.f25340c, this.f25342e, this.f25341d, this.f25343f, j12), i12);
        return new U(this.f25339b, j12, this.f25340c, true);
    }
}
