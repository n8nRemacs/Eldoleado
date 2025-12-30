package androidx.biometric;

import androidx.biometric.p;

/* compiled from: BiometricFragment.java */
/* loaded from: classes.dex */
class n implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ p.b f22763b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ BiometricFragment f22764c;

    public n(BiometricFragment biometricFragment, p.b bVar) {
        this.f22764c = biometricFragment;
        this.f22763b = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        r rVar = this.f22764c.f22728g0;
        if (rVar.f22789J == null) {
            rVar.f22789J = new q();
        }
        rVar.f22789J.b(this.f22763b);
    }
}
