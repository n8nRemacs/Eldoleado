package androidx.camera.camera2.internal.compat.workaround;

import android.hardware.camera2.CaptureRequest;
import androidx.camera.camera2.internal.compat.quirk.C20001l;
import androidx.camera.camera2.internal.compat.quirk.F;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: StillCaptureFlow.java */
@X
/* loaded from: classes.dex */
public class q {

    /* renamed from: a, reason: collision with root package name */
    public final boolean f23265a;

    public q() {
        this.f23265a = ((F) C20001l.f23216a.b(F.class)) != null;
    }

    public final boolean a(@N ArrayList arrayList, boolean z12) {
        if (this.f23265a && z12) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Integer) ((CaptureRequest) it.next()).get(CaptureRequest.CONTROL_AE_MODE)).intValue();
                if (iIntValue == 2 || iIntValue == 3) {
                    return true;
                }
            }
        }
        return false;
    }
}
