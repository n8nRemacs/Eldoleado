package androidx.biometric;

/* compiled from: BiometricFragment.java */
/* loaded from: classes.dex */
class e implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BiometricFragment f22752b;

    public e(BiometricFragment biometricFragment) {
        this.f22752b = biometricFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        r rVar = this.f22752b.f22728g0;
        if (rVar.f22789J == null) {
            rVar.f22789J = new q();
        }
        rVar.f22789J.getClass();
    }
}
