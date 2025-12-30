package androidx.camera.video.internal.audio;

import androidx.camera.video.internal.audio.AudioStream;
import androidx.camera.video.internal.audio.i;
import java.util.concurrent.Executor;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class c implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24954b = 0;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Executor f24955c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f24956d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f24957e;

    public /* synthetic */ c(i iVar, Executor executor, i.b bVar) {
        this.f24956d = iVar;
        this.f24955c = executor;
        this.f24957e = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f24954b) {
            case 0:
                i iVar = (i) this.f24956d;
                int iOrdinal = iVar.f24975g.ordinal();
                if (iOrdinal == 0) {
                    iVar.f24978j = this.f24955c;
                    iVar.f24979k = (i.b) this.f24957e;
                    return;
                } else {
                    if (iOrdinal == 1 || iOrdinal == 2) {
                        throw new AssertionError("The audio recording callback must be registered before the audio source is started.");
                    }
                    return;
                }
            default:
                ((r) this.f24956d).f25033g.a((AudioStream.a) this.f24957e, this.f24955c);
                return;
        }
    }

    public /* synthetic */ c(r rVar, AudioStream.a aVar, Executor executor) {
        this.f24956d = rVar;
        this.f24957e = aVar;
        this.f24955c = executor;
    }
}
