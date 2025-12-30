package androidx.biometric;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.TextView;
import androidx.biometric.FingerprintDialogFragment;
import androidx.view.InterfaceC23040h0;
import com.avito.android.R;

/* compiled from: FingerprintDialogFragment.java */
/* loaded from: classes.dex */
class w implements InterfaceC23040h0<Integer> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ FingerprintDialogFragment f22817b;

    public w(FingerprintDialogFragment fingerprintDialogFragment) {
        this.f22817b = fingerprintDialogFragment;
    }

    @Override // androidx.view.InterfaceC23040h0
    public final void onChanged(Integer num) {
        Integer num2 = num;
        FingerprintDialogFragment fingerprintDialogFragment = this.f22817b;
        Handler handler = fingerprintDialogFragment.f22736f0;
        Runnable runnable = fingerprintDialogFragment.f22737g0;
        handler.removeCallbacks(runnable);
        int iIntValue = num2.intValue();
        if (fingerprintDialogFragment.f22741k0 != null) {
            int i12 = fingerprintDialogFragment.f22738h0.f22809d0;
            Context context = fingerprintDialogFragment.getContext();
            Drawable drawable = null;
            if (context != null) {
                int i13 = R.drawable.fingerprint_dialog_fp_icon;
                if (i12 == 0 && iIntValue == 1) {
                    drawable = androidx.core.content.d.getDrawable(context, i13);
                } else {
                    if (i12 == 1 && iIntValue == 2) {
                        i13 = R.drawable.fingerprint_dialog_error;
                    } else if ((i12 == 2 && iIntValue == 1) || (i12 == 1 && iIntValue == 3)) {
                    }
                    drawable = androidx.core.content.d.getDrawable(context, i13);
                }
            }
            if (drawable != null) {
                fingerprintDialogFragment.f22741k0.setImageDrawable(drawable);
                if ((i12 != 0 || iIntValue != 1) && ((i12 == 1 && iIntValue == 2) || (i12 == 2 && iIntValue == 1))) {
                    FingerprintDialogFragment.c.a(drawable);
                }
                fingerprintDialogFragment.f22738h0.f22809d0 = iIntValue;
            }
        }
        int iIntValue2 = num2.intValue();
        TextView textView = fingerprintDialogFragment.f22742l0;
        if (textView != null) {
            textView.setTextColor(iIntValue2 == 2 ? fingerprintDialogFragment.f22739i0 : fingerprintDialogFragment.f22740j0);
        }
        fingerprintDialogFragment.f22736f0.postDelayed(runnable, 2000L);
    }
}
