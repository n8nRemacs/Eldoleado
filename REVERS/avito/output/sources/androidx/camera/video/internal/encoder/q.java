package androidx.camera.video.internal.encoder;

import android.media.MediaCodec;
import android.util.Range;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.q0;
import androidx.camera.video.internal.encoder.v;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class q implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25180b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f25181c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f25182d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f25183e;

    public /* synthetic */ q(InterfaceC20170l interfaceC20170l, int i12, String str, Throwable th2) {
        this.f25180b = 3;
        this.f25181c = interfaceC20170l;
        this.f25182d = str;
        this.f25183e = th2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        v.d dVar = v.d.f25234i;
        Object obj = this.f25181c;
        Object obj2 = this.f25183e;
        Object obj3 = this.f25182d;
        switch (this.f25180b) {
            case 0:
                v vVar = (v) obj;
                if (vVar.f25215t != dVar) {
                    if (!((ArrayList) obj3).isEmpty()) {
                        C20140q0.d(3, vVar.f25196a);
                    }
                    boolean z12 = vVar.f25201f instanceof v.f;
                    MediaCodec mediaCodec = vVar.f25200e;
                    if (!z12 || vVar.f25194B) {
                        mediaCodec.stop();
                    } else {
                        mediaCodec.flush();
                        vVar.f25193A = true;
                    }
                }
                ((Runnable) obj2).run();
                v.d dVar2 = vVar.f25215t;
                if (dVar2 != v.d.f25233h) {
                    if (!vVar.f25193A) {
                        vVar.l();
                    }
                    vVar.m(v.d.f25227b);
                    v.d dVar3 = v.d.f25231f;
                    v.d dVar4 = v.d.f25232g;
                    if (dVar2 == dVar3 || dVar2 == dVar4) {
                        vVar.start();
                        if (dVar2 == dVar4) {
                            vVar.pause();
                            break;
                        }
                    }
                } else {
                    vVar.j();
                    break;
                }
                break;
            case 1:
                v.c cVar = (v.c) obj;
                LinkedHashMap linkedHashMap = cVar.f25223a;
                q0.a aVar = (q0.a) obj3;
                aVar.getClass();
                Executor executor = (Executor) obj2;
                executor.getClass();
                linkedHashMap.put(aVar, executor);
                executor.execute(new s(8, aVar, cVar.f25224b));
                break;
            case 2:
                Executor executor2 = (Executor) obj3;
                InterfaceC20170l interfaceC20170l = (InterfaceC20170l) obj2;
                v vVar2 = v.this;
                if (vVar2.f25215t != dVar) {
                    try {
                        Objects.requireNonNull(interfaceC20170l);
                        executor2.execute(new A(interfaceC20170l, 2));
                        break;
                    } catch (RejectedExecutionException unused) {
                        C20140q0.c(vVar2.f25196a);
                        return;
                    }
                }
                break;
            default:
                Range<Long> range = v.f25192D;
                ((InterfaceC20170l) obj).c(new EncodeException((String) obj3, (Throwable) obj2));
                break;
        }
    }

    public /* synthetic */ q(Object obj, Object obj2, Object obj3, int i12) {
        this.f25180b = i12;
        this.f25181c = obj;
        this.f25182d = obj2;
        this.f25183e = obj3;
    }
}
