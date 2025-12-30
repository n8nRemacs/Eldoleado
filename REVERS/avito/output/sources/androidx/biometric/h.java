package androidx.biometric;

import androidx.view.InterfaceC23040h0;

/* compiled from: BiometricFragment.java */
/* loaded from: classes.dex */
class h implements InterfaceC23040h0<CharSequence> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BiometricFragment f22755b;

    public h(BiometricFragment biometricFragment) {
        this.f22755b = biometricFragment;
    }

    @Override // androidx.view.InterfaceC23040h0
    public final void onChanged(CharSequence charSequence) {
        CharSequence charSequence2 = charSequence;
        if (charSequence2 != null) {
            BiometricFragment biometricFragment = this.f22755b;
            if (biometricFragment.e5()) {
                biometricFragment.i5(charSequence2);
            }
            biometricFragment.f22728g0.me(null);
        }
    }
}
