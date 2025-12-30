package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.Executor;

/* compiled from: CaptureSessionRepository.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.g0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C20009g0 {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final Executor f23327a;

    /* renamed from: b, reason: collision with root package name */
    public final Object f23328b = new Object();

    /* renamed from: c, reason: collision with root package name */
    @j.B
    public final LinkedHashSet f23329c = new LinkedHashSet();

    /* renamed from: d, reason: collision with root package name */
    @j.B
    public final LinkedHashSet f23330d = new LinkedHashSet();

    /* renamed from: e, reason: collision with root package name */
    @j.B
    public final LinkedHashSet f23331e = new LinkedHashSet();

    /* renamed from: f, reason: collision with root package name */
    public final CameraDevice.StateCallback f23332f = new a();

    public C20009g0(@j.N Executor executor) {
        this.f23327a = executor;
    }

    @j.N
    public final ArrayList a() {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        synchronized (this.f23328b) {
            arrayList = new ArrayList();
            synchronized (this.f23328b) {
                arrayList2 = new ArrayList(this.f23329c);
            }
            arrayList.addAll(arrayList2);
            synchronized (this.f23328b) {
                arrayList3 = new ArrayList(this.f23331e);
            }
            arrayList.addAll(arrayList3);
        }
        return arrayList;
    }

    /* compiled from: CaptureSessionRepository.java */
    /* renamed from: androidx.camera.camera2.internal.g0$a */
    public class a extends CameraDevice.StateCallback {

        /* renamed from: b, reason: collision with root package name */
        public static final /* synthetic */ int f23333b = 0;

        public a() {
        }

        public final void a() {
            ArrayList arrayListA;
            synchronized (C20009g0.this.f23328b) {
                arrayListA = C20009g0.this.a();
                C20009g0.this.f23331e.clear();
                C20009g0.this.f23329c.clear();
                C20009g0.this.f23330d.clear();
            }
            Iterator it = arrayListA.iterator();
            while (it.hasNext()) {
                ((K0) it.next()).i();
            }
        }

        public final void b() {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            synchronized (C20009g0.this.f23328b) {
                linkedHashSet.addAll(C20009g0.this.f23331e);
                linkedHashSet.addAll(C20009g0.this.f23329c);
            }
            C20009g0.this.f23327a.execute(new RunnableC20041x(linkedHashSet, 2));
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onClosed(@j.N CameraDevice cameraDevice) {
            a();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onDisconnected(@j.N CameraDevice cameraDevice) {
            b();
            a();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onError(@j.N CameraDevice cameraDevice, int i12) {
            b();
            a();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onOpened(@j.N CameraDevice cameraDevice) {
        }
    }
}
