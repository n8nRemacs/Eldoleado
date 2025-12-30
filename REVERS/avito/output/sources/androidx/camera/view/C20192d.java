package androidx.camera.view;

import androidx.camera.core.C20140q0;
import androidx.camera.core.CameraControl;
import androidx.camera.core.N;
import j.P;

/* compiled from: CameraController.java */
/* renamed from: androidx.camera.view.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C20192d implements androidx.camera.core.impl.utils.futures.c<N> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AbstractC20193e f25414a;

    public C20192d(AbstractC20193e abstractC20193e) {
        this.f25414a = abstractC20193e;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        if (th2 instanceof CameraControl.OperationCanceledException) {
            C20140q0.d(3, "CameraController");
        } else {
            C20140q0.d(3, "CameraController");
            this.f25414a.f25431q.postValue(4);
        }
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(@P N n12) {
        N n13 = n12;
        if (n13 == null) {
            return;
        }
        C20140q0.d(3, "CameraController");
        this.f25414a.f25431q.postValue(Integer.valueOf(n13.f23614a ? 2 : 3));
    }
}
