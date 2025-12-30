package androidx.camera.video;

import androidx.camera.core.C20140q0;
import androidx.camera.video.O;
import androidx.camera.video.internal.encoder.InterfaceC20169k;
import java.util.Objects;

/* compiled from: Recorder.java */
/* loaded from: classes.dex */
class M implements androidx.camera.core.impl.utils.futures.c<InterfaceC20169k> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ l0 f24715a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ O f24716b;

    public M(O o12, l0 l0Var) {
        this.f24716b = o12;
        this.f24715a = l0Var;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        C20176m c20176m;
        Objects.toString(th2);
        C20140q0.d(3, "Recorder");
        O o12 = this.f24716b;
        synchronized (o12.f24758f) {
            try {
                c20176m = null;
                switch (o12.f24760h.ordinal()) {
                    case 1:
                    case 2:
                        C20176m c20176m2 = o12.f24764l;
                        o12.f24764l = null;
                        c20176m = c20176m2;
                    case 0:
                        o12.B(-1);
                        o12.A(O.j.f24819j);
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        throw new AssertionError("Encountered encoder setup error while in unexpected state " + o12.f24760h + ": " + th2);
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
        if (c20176m != null) {
            o12.i(c20176m, 7);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c5 A[Catch: all -> 0x0086, TryCatch #0 {all -> 0x0086, blocks: (B:19:0x0074, B:20:0x007a, B:46:0x00e9, B:22:0x007f, B:25:0x0089, B:28:0x008f, B:29:0x0096, B:32:0x009a, B:33:0x00a7, B:34:0x00ba, B:37:0x00be, B:40:0x00c5, B:42:0x00cb, B:43:0x00d6, B:44:0x00e1), top: B:58:0x0074 }] */
    @Override // androidx.camera.core.impl.utils.futures.c
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onSuccess(@j.P androidx.camera.video.internal.encoder.InterfaceC20169k r9) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.M.onSuccess(java.lang.Object):void");
    }
}
