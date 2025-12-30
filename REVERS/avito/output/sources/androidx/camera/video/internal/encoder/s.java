package androidx.camera.video.internal.encoder;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.view.Surface;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.q0;
import androidx.camera.video.i0;
import androidx.camera.video.internal.BufferProvider;
import androidx.camera.video.internal.encoder.v;
import androidx.concurrent.futures.b;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class s implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25187b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f25188c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f25189d;

    public /* synthetic */ s(int i12, Object obj, Object obj2) {
        this.f25187b = i12;
        this.f25188c = obj;
        this.f25189d = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        InterfaceC20170l interfaceC20170l;
        Executor executor;
        switch (this.f25187b) {
            case 0:
                ((v) this.f25188c).f25208m.remove((F) this.f25189d);
                return;
            case 1:
                LinkedHashMap linkedHashMap = ((v.c) this.f25188c).f25223a;
                q0.a aVar = (q0.a) this.f25189d;
                aVar.getClass();
                linkedHashMap.remove(aVar);
                return;
            case 2:
                v vVar = v.this;
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
                        MediaCodec.CodecException codecException = (MediaCodec.CodecException) this.f25189d;
                        vVar.g(1, codecException.getMessage(), codecException);
                        return;
                    default:
                        throw new IllegalStateException("Unknown state: " + vVar.f25215t);
                }
            case 3:
                v.e eVar = (v.e) this.f25188c;
                MediaFormat mediaFormat = (MediaFormat) this.f25189d;
                if (eVar.f25246i) {
                    C20140q0.d(5, v.this.f25196a);
                    return;
                }
                switch (v.this.f25215t.ordinal()) {
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
                        synchronized (v.this.f25197b) {
                            v vVar2 = v.this;
                            interfaceC20170l = vVar2.f25213r;
                            executor = vVar2.f25214s;
                        }
                        try {
                            executor.execute(new s(4, interfaceC20170l, mediaFormat));
                            return;
                        } catch (RejectedExecutionException unused) {
                            C20140q0.c(v.this.f25196a);
                            return;
                        }
                    default:
                        throw new IllegalStateException("Unknown state: " + v.this.f25215t);
                }
            case 4:
                int i12 = v.e.f25237k;
                ((InterfaceC20170l) this.f25188c).a(new w((MediaFormat) this.f25189d, 2));
                return;
            case 5:
                int i13 = v.e.f25237k;
                ((InterfaceC20170l) this.f25188c).d((C20168j) this.f25189d);
                return;
            case 6:
                ((v) this.f25188c).f25207l.remove((b.a) this.f25189d);
                return;
            case 7:
                ((q0.a) ((Map.Entry) this.f25188c).getKey()).a((BufferProvider.State) this.f25189d);
                return;
            case 8:
                ((q0.a) this.f25188c).a((BufferProvider.State) this.f25189d);
                return;
            default:
                ((i0) this.f25188c).a((Surface) this.f25189d);
                return;
        }
    }
}
