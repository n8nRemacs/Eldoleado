package androidx.biometric;

import android.content.res.Resources;
import androidx.biometric.p;
import androidx.biometric.r;
import androidx.view.C23038g0;
import androidx.view.InterfaceC23040h0;
import java.util.concurrent.Executor;

/* compiled from: BiometricFragment.java */
/* loaded from: classes.dex */
class f implements InterfaceC23040h0<p.b> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BiometricFragment f22753b;

    public f(BiometricFragment biometricFragment) {
        this.f22753b = biometricFragment;
    }

    @Override // androidx.view.InterfaceC23040h0
    public final void onChanged(p.b bVar) throws Resources.NotFoundException {
        p.b bVar2 = bVar;
        if (bVar2 != null) {
            BiometricFragment biometricFragment = this.f22753b;
            r rVar = biometricFragment.f22728g0;
            if (rVar.f22798S) {
                rVar.f22798S = false;
                Executor bVar3 = rVar.f22788E;
                if (bVar3 == null) {
                    bVar3 = new r.b();
                }
                bVar3.execute(new n(biometricFragment, bVar2));
            }
            biometricFragment.dismiss();
            r rVar2 = biometricFragment.f22728g0;
            if (rVar2.f22802W == null) {
                rVar2.f22802W = new C23038g0<>();
            }
            r.qe(rVar2.f22802W, null);
        }
    }
}
