package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: CameraDeviceStateCallbacks.java */
@j.X
/* loaded from: classes.dex */
public final class U {

    /* compiled from: CameraDeviceStateCallbacks.java */
    public static final class a extends CameraDevice.StateCallback {

        /* renamed from: a, reason: collision with root package name */
        public final ArrayList f23070a = new ArrayList();

        public a(@j.N ArrayList arrayList) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                CameraDevice.StateCallback stateCallback = (CameraDevice.StateCallback) it.next();
                if (!(stateCallback instanceof b)) {
                    this.f23070a.add(stateCallback);
                }
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onClosed(@j.N CameraDevice cameraDevice) {
            Iterator it = this.f23070a.iterator();
            while (it.hasNext()) {
                ((CameraDevice.StateCallback) it.next()).onClosed(cameraDevice);
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onDisconnected(@j.N CameraDevice cameraDevice) {
            Iterator it = this.f23070a.iterator();
            while (it.hasNext()) {
                ((CameraDevice.StateCallback) it.next()).onDisconnected(cameraDevice);
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onError(@j.N CameraDevice cameraDevice, int i12) {
            Iterator it = this.f23070a.iterator();
            while (it.hasNext()) {
                ((CameraDevice.StateCallback) it.next()).onError(cameraDevice, i12);
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onOpened(@j.N CameraDevice cameraDevice) {
            Iterator it = this.f23070a.iterator();
            while (it.hasNext()) {
                ((CameraDevice.StateCallback) it.next()).onOpened(cameraDevice);
            }
        }
    }

    /* compiled from: CameraDeviceStateCallbacks.java */
    public static final class b extends CameraDevice.StateCallback {
        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onClosed(@j.N CameraDevice cameraDevice) {
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onDisconnected(@j.N CameraDevice cameraDevice) {
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onOpened(@j.N CameraDevice cameraDevice) {
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onError(@j.N CameraDevice cameraDevice, int i12) {
        }
    }
}
