package androidx.biometric;

import android.content.res.Resources;

/* compiled from: BiometricFragment.java */
/* loaded from: classes.dex */
class m implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f22760b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ CharSequence f22761c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ BiometricFragment f22762d;

    public m(BiometricFragment biometricFragment, int i12, CharSequence charSequence) {
        this.f22762d = biometricFragment;
        this.f22760b = i12;
        this.f22761c = charSequence;
    }

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        this.f22762d.g5(this.f22760b, this.f22761c);
    }
}
