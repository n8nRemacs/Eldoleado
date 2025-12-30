package androidx.biometric;

import android.os.Handler;
import android.widget.TextView;
import androidx.view.InterfaceC23040h0;

/* compiled from: FingerprintDialogFragment.java */
/* loaded from: classes.dex */
class x implements InterfaceC23040h0<CharSequence> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ FingerprintDialogFragment f22818b;

    public x(FingerprintDialogFragment fingerprintDialogFragment) {
        this.f22818b = fingerprintDialogFragment;
    }

    @Override // androidx.view.InterfaceC23040h0
    public final void onChanged(CharSequence charSequence) {
        CharSequence charSequence2 = charSequence;
        FingerprintDialogFragment fingerprintDialogFragment = this.f22818b;
        Handler handler = fingerprintDialogFragment.f22736f0;
        Runnable runnable = fingerprintDialogFragment.f22737g0;
        handler.removeCallbacks(runnable);
        TextView textView = fingerprintDialogFragment.f22742l0;
        if (textView != null) {
            textView.setText(charSequence2);
        }
        fingerprintDialogFragment.f22736f0.postDelayed(runnable, 2000L);
    }
}
