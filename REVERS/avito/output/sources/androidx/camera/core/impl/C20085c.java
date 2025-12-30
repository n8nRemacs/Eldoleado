package androidx.camera.core.impl;

import android.os.Handler;
import java.util.concurrent.Executor;

/* compiled from: AutoValue_CameraThreadConfig.java */
/* renamed from: androidx.camera.core.impl.c, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20085c extends F {

    /* renamed from: a, reason: collision with root package name */
    public final Executor f24086a;

    /* renamed from: b, reason: collision with root package name */
    public final Handler f24087b;

    public C20085c(Executor executor, Handler handler) {
        if (executor == null) {
            throw new NullPointerException("Null cameraExecutor");
        }
        this.f24086a = executor;
        if (handler == null) {
            throw new NullPointerException("Null schedulerHandler");
        }
        this.f24087b = handler;
    }

    @Override // androidx.camera.core.impl.F
    @j.N
    public final Executor b() {
        return this.f24086a;
    }

    @Override // androidx.camera.core.impl.F
    @j.N
    public final Handler c() {
        return this.f24087b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof F)) {
            return false;
        }
        F f12 = (F) obj;
        return this.f24086a.equals(f12.b()) && this.f24087b.equals(f12.c());
    }

    public final int hashCode() {
        return ((this.f24086a.hashCode() ^ 1000003) * 1000003) ^ this.f24087b.hashCode();
    }

    public final String toString() {
        return "CameraThreadConfig{cameraExecutor=" + this.f24086a + ", schedulerHandler=" + this.f24087b + "}";
    }
}
