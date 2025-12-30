package androidx.camera.extensions;

import androidx.camera.core.C20140q0;
import androidx.camera.core.InterfaceC20143u;
import androidx.camera.extensions.impl.InitializerImpl;
import androidx.concurrent.futures.b;
import j.B;
import j.X;

@X
/* loaded from: classes.dex */
public final class ExtensionsManager {

    /* renamed from: a, reason: collision with root package name */
    public static final Object f24621a = new Object();

    /* renamed from: b, reason: collision with root package name */
    @B
    public static ExtensionsManager f24622b;

    /* renamed from: androidx.camera.extensions.ExtensionsManager$1, reason: invalid class name */
    class AnonymousClass1 implements InitializerImpl.OnExtensionsInitializedCallback {
        final /* synthetic */ InterfaceC20143u val$cameraProvider;
        final /* synthetic */ b.a val$completer;

        public AnonymousClass1(b.a aVar, InterfaceC20143u interfaceC20143u) {
            this.val$completer = aVar;
            this.val$cameraProvider = interfaceC20143u;
        }

        public void onFailure(int i12) {
            C20140q0.b("ExtensionsManager");
            b.a aVar = this.val$completer;
            ExtensionsAvailability extensionsAvailability = ExtensionsAvailability.LIBRARY_AVAILABLE;
            aVar.b(ExtensionsManager.a());
        }

        public void onSuccess() {
            C20140q0.d(3, "ExtensionsManager");
            b.a aVar = this.val$completer;
            ExtensionsAvailability extensionsAvailability = ExtensionsAvailability.LIBRARY_AVAILABLE;
            aVar.b(ExtensionsManager.a());
        }
    }

    /* renamed from: androidx.camera.extensions.ExtensionsManager$2, reason: invalid class name */
    class AnonymousClass2 implements InitializerImpl.OnExtensionsDeinitializedCallback {
        final /* synthetic */ b.a val$completer;

        public AnonymousClass2(b.a aVar) {
            this.val$completer = aVar;
        }

        public void onFailure(int i12) {
            this.val$completer.d(new Exception("Failed to deinitialize extensions."));
        }

        public void onSuccess() {
            this.val$completer.b(null);
        }
    }

    public enum ExtensionsAvailability {
        LIBRARY_AVAILABLE,
        LIBRARY_UNAVAILABLE_ERROR_LOADING,
        LIBRARY_UNAVAILABLE_MISSING_IMPLEMENTATION,
        NONE
    }

    public static ExtensionsManager a() {
        synchronized (f24621a) {
            try {
                ExtensionsManager extensionsManager = f24622b;
                if (extensionsManager != null) {
                    return extensionsManager;
                }
                ExtensionsManager extensionsManager2 = new ExtensionsManager();
                f24622b = extensionsManager2;
                return extensionsManager2;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
