package androidx.camera.video.internal.encoder;

import android.media.MediaFormat;
import androidx.camera.core.C20140q0;
import androidx.camera.video.internal.BufferProvider;
import androidx.camera.video.internal.encoder.v;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class w implements b.c, G {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25256b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f25257c;

    public /* synthetic */ w(Object obj, int i12) {
        this.f25256b = i12;
        this.f25257c = obj;
    }

    public MediaFormat a() {
        int i12 = v.e.f25237k;
        return (MediaFormat) this.f25257c;
    }

    @Override // androidx.concurrent.futures.b.c
    public Object d(final b.a aVar) {
        switch (this.f25256b) {
            case 0:
                final v.c cVar = (v.c) this.f25257c;
                final int i12 = 1;
                cVar.f25226d.f25203h.execute(new Runnable() { // from class: androidx.camera.video.internal.encoder.x
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i12) {
                            case 0:
                                final v.c cVar2 = cVar;
                                BufferProvider.State state = cVar2.f25224b;
                                BufferProvider.State state2 = BufferProvider.State.f24942b;
                                b.a aVar2 = aVar;
                                if (state != state2) {
                                    if (state != BufferProvider.State.f24943c) {
                                        aVar2.d(new IllegalStateException("Unknown state: " + cVar2.f25224b));
                                        break;
                                    } else {
                                        aVar2.d(new IllegalStateException("BufferProvider is not active."));
                                        break;
                                    }
                                } else {
                                    v vVar = cVar2.f25226d;
                                    final D0<E> d0F = vVar.f();
                                    androidx.camera.core.impl.utils.futures.f.i(aVar2, d0F);
                                    final int i13 = 0;
                                    aVar2.a(new Runnable() { // from class: androidx.camera.video.internal.encoder.y
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i13) {
                                                case 0:
                                                    v.c cVar3 = cVar2;
                                                    cVar3.getClass();
                                                    D0 d02 = d0F;
                                                    if (!d02.cancel(true)) {
                                                        androidx.core.util.z.g(null, d02.isDone());
                                                        try {
                                                            ((E) d02.get()).cancel();
                                                            break;
                                                        } catch (InterruptedException | CancellationException | ExecutionException e12) {
                                                            String str = cVar3.f25226d.f25196a;
                                                            e12.toString();
                                                            C20140q0.d(5, str);
                                                            return;
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    cVar2.f25225c.remove(d0F);
                                                    break;
                                            }
                                        }
                                    }, androidx.camera.core.impl.utils.executor.c.a());
                                    cVar2.f25225c.add(d0F);
                                    final int i14 = 1;
                                    d0F.N(new Runnable() { // from class: androidx.camera.video.internal.encoder.y
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i14) {
                                                case 0:
                                                    v.c cVar3 = cVar2;
                                                    cVar3.getClass();
                                                    D0 d02 = d0F;
                                                    if (!d02.cancel(true)) {
                                                        androidx.core.util.z.g(null, d02.isDone());
                                                        try {
                                                            ((E) d02.get()).cancel();
                                                            break;
                                                        } catch (InterruptedException | CancellationException | ExecutionException e12) {
                                                            String str = cVar3.f25226d.f25196a;
                                                            e12.toString();
                                                            C20140q0.d(5, str);
                                                            return;
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    cVar2.f25225c.remove(d0F);
                                                    break;
                                            }
                                        }
                                    }, vVar.f25203h);
                                    break;
                                }
                            default:
                                aVar.b(cVar.f25224b);
                                break;
                        }
                    }
                });
                return "fetchData";
            default:
                final v.c cVar2 = (v.c) this.f25257c;
                final int i13 = 0;
                cVar2.f25226d.f25203h.execute(new Runnable() { // from class: androidx.camera.video.internal.encoder.x
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i13) {
                            case 0:
                                final v.c cVar22 = cVar2;
                                BufferProvider.State state = cVar22.f25224b;
                                BufferProvider.State state2 = BufferProvider.State.f24942b;
                                b.a aVar2 = aVar;
                                if (state != state2) {
                                    if (state != BufferProvider.State.f24943c) {
                                        aVar2.d(new IllegalStateException("Unknown state: " + cVar22.f25224b));
                                        break;
                                    } else {
                                        aVar2.d(new IllegalStateException("BufferProvider is not active."));
                                        break;
                                    }
                                } else {
                                    v vVar = cVar22.f25226d;
                                    final D0 d0F = vVar.f();
                                    androidx.camera.core.impl.utils.futures.f.i(aVar2, d0F);
                                    final int i132 = 0;
                                    aVar2.a(new Runnable() { // from class: androidx.camera.video.internal.encoder.y
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i132) {
                                                case 0:
                                                    v.c cVar3 = cVar22;
                                                    cVar3.getClass();
                                                    D0 d02 = d0F;
                                                    if (!d02.cancel(true)) {
                                                        androidx.core.util.z.g(null, d02.isDone());
                                                        try {
                                                            ((E) d02.get()).cancel();
                                                            break;
                                                        } catch (InterruptedException | CancellationException | ExecutionException e12) {
                                                            String str = cVar3.f25226d.f25196a;
                                                            e12.toString();
                                                            C20140q0.d(5, str);
                                                            return;
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    cVar22.f25225c.remove(d0F);
                                                    break;
                                            }
                                        }
                                    }, androidx.camera.core.impl.utils.executor.c.a());
                                    cVar22.f25225c.add(d0F);
                                    final int i14 = 1;
                                    d0F.N(new Runnable() { // from class: androidx.camera.video.internal.encoder.y
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i14) {
                                                case 0:
                                                    v.c cVar3 = cVar22;
                                                    cVar3.getClass();
                                                    D0 d02 = d0F;
                                                    if (!d02.cancel(true)) {
                                                        androidx.core.util.z.g(null, d02.isDone());
                                                        try {
                                                            ((E) d02.get()).cancel();
                                                            break;
                                                        } catch (InterruptedException | CancellationException | ExecutionException e12) {
                                                            String str = cVar3.f25226d.f25196a;
                                                            e12.toString();
                                                            C20140q0.d(5, str);
                                                            return;
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    cVar22.f25225c.remove(d0F);
                                                    break;
                                            }
                                        }
                                    }, vVar.f25203h);
                                    break;
                                }
                            default:
                                aVar.b(cVar2.f25224b);
                                break;
                        }
                    }
                });
                return "acquireBuffer";
        }
    }
}
