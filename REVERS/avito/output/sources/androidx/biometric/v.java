package androidx.biometric;

import android.content.Context;
import com.avito.android.R;
import j.N;
import j.P;

/* compiled from: ErrorUtils.java */
/* loaded from: classes.dex */
class v {
    @N
    public static String a(int i12, @P Context context) {
        if (context == null) {
            return "";
        }
        if (i12 == 1) {
            return context.getString(R.string.fingerprint_error_hw_not_available);
        }
        if (i12 != 7) {
            switch (i12) {
                case 9:
                    break;
                case 10:
                    return context.getString(R.string.fingerprint_error_user_canceled);
                case 11:
                    return context.getString(R.string.fingerprint_error_no_fingerprints);
                case 12:
                    return context.getString(R.string.fingerprint_error_hw_not_present);
                default:
                    return context.getString(R.string.default_error_msg);
            }
        }
        return context.getString(R.string.fingerprint_error_lockout);
    }
}
