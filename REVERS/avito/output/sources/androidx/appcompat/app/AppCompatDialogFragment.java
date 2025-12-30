package androidx.appcompat.app;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.RestrictTo;
import androidx.fragment.app.DialogFragment;
import j.N;
import j.P;

/* loaded from: classes.dex */
public class AppCompatDialogFragment extends DialogFragment {
    public AppCompatDialogFragment() {
    }

    @Override // androidx.fragment.app.DialogFragment
    @N
    public Dialog onCreateDialog(@P Bundle bundle) {
        return new A(getContext(), getTheme());
    }

    @Override // androidx.fragment.app.DialogFragment
    @RestrictTo
    public void setupDialog(@N Dialog dialog, int i12) {
        if (!(dialog instanceof A)) {
            super.setupDialog(dialog, i12);
            return;
        }
        A a12 = (A) dialog;
        if (i12 != 1 && i12 != 2) {
            if (i12 != 3) {
                return;
            } else {
                dialog.getWindow().addFlags(24);
            }
        }
        a12.q();
    }

    public AppCompatDialogFragment(@j.I int i12) {
        super(i12);
    }
}
