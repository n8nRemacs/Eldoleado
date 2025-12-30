package androidx.biometric;

import android.content.res.Resources;
import androidx.view.C23038g0;
import androidx.view.InterfaceC23040h0;

/* compiled from: BiometricFragment.java */
/* loaded from: classes.dex */
class k implements InterfaceC23040h0<Boolean> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BiometricFragment f22758b;

    public k(BiometricFragment biometricFragment) {
        this.f22758b = biometricFragment;
    }

    @Override // androidx.view.InterfaceC23040h0
    public final void onChanged(Boolean bool) throws Resources.NotFoundException {
        if (bool.booleanValue()) {
            BiometricFragment biometricFragment = this.f22758b;
            biometricFragment.b5(1);
            biometricFragment.dismiss();
            r rVar = biometricFragment.f22728g0;
            if (rVar.f22808c0 == null) {
                rVar.f22808c0 = new C23038g0<>();
            }
            r.qe(rVar.f22808c0, Boolean.FALSE);
        }
    }
}
