package androidx.camera.video.internal.encoder;

import android.media.MediaCodec;
import j.P;

/* compiled from: EncoderImpl.java */
/* loaded from: classes.dex */
class t implements androidx.camera.core.impl.utils.futures.c<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ u f25190a;

    public t(u uVar) {
        this.f25190a = uVar;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        boolean z12 = th2 instanceof MediaCodec.CodecException;
        u uVar = this.f25190a;
        if (!z12) {
            uVar.f25191a.g(0, th2.getMessage(), th2);
            return;
        }
        v vVar = uVar.f25191a;
        MediaCodec.CodecException codecException = (MediaCodec.CodecException) th2;
        vVar.getClass();
        vVar.g(1, codecException.getMessage(), codecException);
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final /* bridge */ /* synthetic */ void onSuccess(@P Void r12) {
    }
}
