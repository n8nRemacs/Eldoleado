package androidx.biometric;

import androidx.biometric.r;
import androidx.view.C23038g0;
import androidx.view.InterfaceC23040h0;
import com.avito.android.R;
import java.util.concurrent.Executor;

/* compiled from: BiometricFragment.java */
/* loaded from: classes.dex */
class i implements InterfaceC23040h0<Boolean> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BiometricFragment f22756b;

    public i(BiometricFragment biometricFragment) {
        this.f22756b = biometricFragment;
    }

    @Override // androidx.view.InterfaceC23040h0
    public final void onChanged(Boolean bool) {
        if (bool.booleanValue()) {
            BiometricFragment biometricFragment = this.f22756b;
            if (biometricFragment.e5()) {
                biometricFragment.i5(biometricFragment.getString(R.string.fingerprint_not_recognized));
            }
            r rVar = biometricFragment.f22728g0;
            if (rVar.f22798S) {
                Executor bVar = rVar.f22788E;
                if (bVar == null) {
                    bVar = new r.b();
                }
                bVar.execute(new e(biometricFragment));
            }
            r rVar2 = biometricFragment.f22728g0;
            if (rVar2.f22805Z == null) {
                rVar2.f22805Z = new C23038g0<>();
            }
            r.qe(rVar2.f22805Z, Boolean.FALSE);
        }
    }
}
