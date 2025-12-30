package androidx.biometric;

import android.content.res.Resources;
import androidx.view.InterfaceC23040h0;
import com.avito.android.R;

/* compiled from: BiometricFragment.java */
/* loaded from: classes.dex */
class j implements InterfaceC23040h0<Boolean> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BiometricFragment f22757b;

    public j(BiometricFragment biometricFragment) {
        this.f22757b = biometricFragment;
    }

    @Override // androidx.view.InterfaceC23040h0
    public final void onChanged(Boolean bool) throws Resources.NotFoundException {
        if (bool.booleanValue()) {
            BiometricFragment biometricFragment = this.f22757b;
            if (biometricFragment.d5()) {
                biometricFragment.f5();
            } else {
                CharSequence charSequenceLe = biometricFragment.f22728g0.le();
                if (charSequenceLe == null) {
                    charSequenceLe = biometricFragment.getString(R.string.default_error_msg);
                }
                biometricFragment.g5(13, charSequenceLe);
                biometricFragment.b5(2);
            }
            biometricFragment.f22728g0.pe(false);
        }
    }
}
