package androidx.camera.video.internal.audio;

import androidx.camera.video.internal.audio.AudioStream;
import androidx.camera.video.internal.audio.i;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class k implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24997b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ boolean f24998c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f24999d;

    public /* synthetic */ k(int i12, Object obj, boolean z12) {
        this.f24997b = i12;
        this.f24999d = obj;
        this.f24998c = z12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f24997b) {
            case 0:
                ((i.c) ((AudioStream.a) this.f24999d)).a(this.f24998c);
                break;
            default:
                ((i.b) this.f24999d).a(this.f24998c);
                break;
        }
    }
}
