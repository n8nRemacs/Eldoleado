package androidx.camera.core.internal;

import androidx.camera.core.N0;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.x0;
import j.P;
import j.X;

/* compiled from: UseCaseEventConfig.java */
@X
/* loaded from: classes.dex */
public interface k extends x0 {

    /* renamed from: E, reason: collision with root package name */
    public static final Config.a<N0.b> f24364E = Config.a.a(N0.b.class, "camerax.core.useCaseEventCallback");

    /* compiled from: UseCaseEventConfig.java */
    public interface a<B> {
    }

    @P
    default N0.b n() {
        return (N0.b) g(f24364E, null);
    }
}
