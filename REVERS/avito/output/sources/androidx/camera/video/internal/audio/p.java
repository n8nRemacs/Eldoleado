package androidx.camera.video.internal.audio;

import androidx.camera.video.internal.audio.AudioStream;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class p implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25022b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ r f25023c;

    public /* synthetic */ p(r rVar, int i12) {
        this.f25022b = i12;
        this.f25023c = rVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f25022b) {
            case 0:
                r rVar = this.f25023c;
                rVar.f25037k.set(false);
                rVar.f25033g.stop();
                synchronized (rVar.f25031e) {
                    rVar.f25032f = null;
                    rVar.f25029c.clear();
                }
                return;
            case 1:
                r rVar2 = this.f25023c;
                rVar2.f25037k.set(false);
                rVar2.f25033g.release();
                synchronized (rVar2.f25031e) {
                    rVar2.f25032f = null;
                    rVar2.f25029c.clear();
                }
                return;
            case 2:
                r rVar3 = this.f25023c;
                rVar3.getClass();
                try {
                    rVar3.f25033g.start();
                    if (rVar3.f25037k.getAndSet(true)) {
                        return;
                    }
                    rVar3.c();
                    return;
                } catch (AudioStream.AudioStreamException e12) {
                    throw new RuntimeException(e12);
                }
            default:
                this.f25023c.c();
                return;
        }
    }
}
