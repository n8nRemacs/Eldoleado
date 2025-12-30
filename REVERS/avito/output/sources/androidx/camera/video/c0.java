package androidx.camera.video;

import android.os.SystemClock;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.SessionConfig;
import androidx.concurrent.futures.b;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: VideoCapture.java */
/* loaded from: classes.dex */
class c0 extends AbstractC20109p {

    /* renamed from: a, reason: collision with root package name */
    public boolean f24892a = true;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AtomicBoolean f24893b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ b.a f24894c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ SessionConfig.b f24895d;

    public c0(AtomicBoolean atomicBoolean, b.a aVar, SessionConfig.b bVar) {
        this.f24893b = atomicBoolean;
        this.f24894c = aVar;
        this.f24895d = bVar;
    }

    @Override // androidx.camera.core.impl.AbstractC20109p
    public final void b(@j.N androidx.camera.core.impl.r rVar) {
        Object obj;
        if (this.f24892a) {
            this.f24892a = false;
            rVar.a();
            SystemClock.uptimeMillis();
            SystemClock.elapsedRealtime();
            C20140q0.d(3, "VideoCapture");
        }
        AtomicBoolean atomicBoolean = this.f24893b;
        if (atomicBoolean.get() || (obj = rVar.b().f23987a.get("androidx.camera.video.VideoCapture.streamUpdate")) == null) {
            return;
        }
        int iIntValue = ((Integer) obj).intValue();
        b.a aVar = this.f24894c;
        if (iIntValue == aVar.hashCode() && aVar.b(null) && !atomicBoolean.getAndSet(true)) {
            androidx.camera.core.impl.utils.executor.c.d().execute(new K(2, this, this.f24895d));
        }
    }
}
