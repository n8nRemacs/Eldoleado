package androidx.camera.video.impl;

import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.InterfaceC20088d0;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.s0;
import androidx.camera.core.internal.j;
import androidx.camera.video.VideoOutput;
import androidx.camera.video.b0;
import androidx.camera.video.internal.encoder.J;
import androidx.camera.video.internal.encoder.L;
import j.N;
import j.X;
import q.InterfaceC47174a;

/* compiled from: VideoCaptureConfig.java */
@X
/* loaded from: classes.dex */
public final class a<T extends VideoOutput> implements L0<b0<T>>, InterfaceC20088d0, j {

    /* renamed from: G, reason: collision with root package name */
    public static final Config.a<VideoOutput> f24939G = Config.a.a(VideoOutput.class, "camerax.video.VideoCapture.videoOutput");

    /* renamed from: H, reason: collision with root package name */
    public static final Config.a<InterfaceC47174a<J, L>> f24940H = Config.a.a(InterfaceC47174a.class, "camerax.video.VideoCapture.videoEncoderInfoFinder");

    /* renamed from: F, reason: collision with root package name */
    public final s0 f24941F;

    public a(@N s0 s0Var) {
        this.f24941F = s0Var;
    }

    @Override // androidx.camera.core.impl.x0
    @N
    public final Config getConfig() {
        return this.f24941F;
    }

    @Override // androidx.camera.core.impl.InterfaceC20086c0
    public final int getInputFormat() {
        return 34;
    }
}
