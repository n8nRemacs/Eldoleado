package androidx.camera.video.internal.encoder;

import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Range;
import androidx.camera.core.C20140q0;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import androidx.camera.video.internal.encoder.v;
import java.util.ArrayDeque;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class o implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25175b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ v f25176c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ long f25177d;

    public /* synthetic */ o(v vVar, long j12, int i12) {
        this.f25175b = i12;
        this.f25176c = vVar;
        this.f25177d = j12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f25175b) {
            case 0:
                v vVar = this.f25176c;
                switch (vVar.f25215t.ordinal()) {
                    case 0:
                    case 2:
                    case 3:
                    case 5:
                    case 7:
                        return;
                    case 1:
                        long j12 = this.f25177d;
                        androidx.camera.video.internal.c.c(j12);
                        C20140q0.d(3, vVar.f25196a);
                        vVar.f25210o.addLast(Range.create(Long.valueOf(j12), Long.MAX_VALUE));
                        vVar.m(v.d.f25229d);
                        return;
                    case 4:
                        vVar.m(v.d.f25232g);
                        return;
                    case 6:
                    case 8:
                        throw new IllegalStateException("Encoder is released");
                    default:
                        throw new IllegalStateException("Unknown state: " + vVar.f25215t);
                }
            default:
                v vVar2 = this.f25176c;
                int iOrdinal = vVar2.f25215t.ordinal();
                v.d dVar = v.d.f25228c;
                MediaCodec mediaCodec = vVar2.f25200e;
                long j13 = this.f25177d;
                InterfaceC20169k.b bVar = vVar2.f25201f;
                String str = vVar2.f25196a;
                switch (iOrdinal) {
                    case 0:
                        vVar2.f25219x = null;
                        androidx.camera.video.internal.c.c(j13);
                        C20140q0.d(3, str);
                        try {
                            if (vVar2.f25193A) {
                                vVar2.l();
                            }
                            vVar2.f25216u = Range.create(Long.valueOf(j13), Long.MAX_VALUE);
                            mediaCodec.start();
                            if (bVar instanceof v.c) {
                                ((v.c) bVar).f(true);
                            }
                            vVar2.m(dVar);
                            return;
                        } catch (MediaCodec.CodecException e12) {
                            vVar2.g(1, e12.getMessage(), e12);
                            return;
                        }
                    case 1:
                    case 4:
                    case 7:
                        return;
                    case 2:
                        vVar2.f25219x = null;
                        ArrayDeque arrayDeque = vVar2.f25210o;
                        Range range = (Range) arrayDeque.removeLast();
                        androidx.core.util.z.g("There should be a \"pause\" before \"resume\"", range != null && ((Long) range.getUpper()).longValue() == Long.MAX_VALUE);
                        Long l12 = (Long) range.getLower();
                        long jLongValue = l12.longValue();
                        arrayDeque.addLast(Range.create(l12, Long.valueOf(j13)));
                        androidx.camera.video.internal.c.c(j13);
                        androidx.camera.video.internal.c.c(j13 - jLongValue);
                        C20140q0.d(3, str);
                        boolean z12 = vVar2.f25198c;
                        if ((z12 || androidx.camera.video.internal.compat.quirk.e.f25058a.b(androidx.camera.video.internal.compat.quirk.a.class) == null) && (!z12 || androidx.camera.video.internal.compat.quirk.e.f25058a.b(androidx.camera.video.internal.compat.quirk.s.class) == null)) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("drop-input-frames", 0);
                            mediaCodec.setParameters(bundle);
                            if (bVar instanceof v.c) {
                                ((v.c) bVar).f(true);
                            }
                        }
                        if (z12) {
                            vVar2.k();
                        }
                        vVar2.m(dVar);
                        return;
                    case 3:
                    case 5:
                        vVar2.m(v.d.f25231f);
                        return;
                    case 6:
                    case 8:
                        throw new IllegalStateException("Encoder is released");
                    default:
                        throw new IllegalStateException("Unknown state: " + vVar2.f25215t);
                }
        }
    }
}
