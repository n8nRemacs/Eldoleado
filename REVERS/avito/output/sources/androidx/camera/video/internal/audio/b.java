package androidx.camera.video.internal.audio;

import androidx.camera.core.C20140q0;
import androidx.camera.video.internal.audio.AudioStream;
import androidx.camera.video.internal.audio.i;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class b implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24952b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f24953c;

    public /* synthetic */ b(i.b bVar, boolean z12) {
        this.f24952b = 2;
        this.f24953c = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f24952b) {
            case 0:
                i iVar = (i) this.f24953c;
                int iOrdinal = iVar.f24975g.ordinal();
                if (iOrdinal == 1) {
                    iVar.c(i.d.f24993b);
                    iVar.e();
                    break;
                } else if (iOrdinal == 2) {
                    C20140q0.d(5, "AudioSource");
                    break;
                }
                break;
            case 1:
                ((i.c) ((AudioStream.a) this.f24953c)).a(true);
                break;
            default:
                ((i.b) this.f24953c).getClass();
                break;
        }
    }

    public /* synthetic */ b(Object obj, int i12) {
        this.f24952b = i12;
        this.f24953c = obj;
    }
}
