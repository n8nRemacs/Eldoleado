package androidx.biometric;

import android.os.CancellationSignal;
import androidx.core.os.C22778f;
import j.P;
import j.X;
import j.k0;

/* compiled from: CancellationSignalProvider.java */
/* loaded from: classes.dex */
class s {

    /* renamed from: a, reason: collision with root package name */
    @P
    public CancellationSignal f22815a;

    /* renamed from: b, reason: collision with root package name */
    @P
    public C22778f f22816b;

    /* compiled from: CancellationSignalProvider.java */
    public class a implements c {
    }

    /* compiled from: CancellationSignalProvider.java */
    @X
    public static class b {
        private b() {
        }

        public static void a(CancellationSignal cancellationSignal) {
            cancellationSignal.cancel();
        }

        public static CancellationSignal b() {
            return new CancellationSignal();
        }
    }

    /* compiled from: CancellationSignalProvider.java */
    @k0
    public interface c {
    }
}
