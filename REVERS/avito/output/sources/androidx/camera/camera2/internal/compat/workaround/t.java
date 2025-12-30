package androidx.camera.camera2.internal.compat.workaround;

import android.hardware.camera2.CaptureRequest;
import androidx.camera.camera2.impl.b;
import androidx.camera.camera2.internal.compat.quirk.C20001l;
import androidx.camera.camera2.internal.compat.quirk.H;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.H;
import j.N;
import j.S;
import j.X;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: TorchStateReset.java */
@X
/* loaded from: classes.dex */
public class t {

    /* renamed from: a, reason: collision with root package name */
    public final boolean f23269a;

    public t() {
        this.f23269a = C20001l.f23216a.b(H.class) != null;
    }

    @N
    @S
    public static androidx.camera.core.impl.H a(@N androidx.camera.core.impl.H h12) {
        H.a aVar = new H.a();
        aVar.f23978c = h12.f23970c;
        Iterator it = Collections.unmodifiableList(h12.f23968a).iterator();
        while (it.hasNext()) {
            aVar.d((DeferrableSurface) it.next());
        }
        aVar.c(h12.f23969b);
        b.a aVar2 = new b.a();
        aVar2.c(CaptureRequest.FLASH_MODE, 0);
        aVar.c(aVar2.a());
        return aVar.e();
    }

    public final boolean b(@N ArrayList arrayList, boolean z12) {
        if (!this.f23269a || !z12) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) ((CaptureRequest) it.next()).get(CaptureRequest.FLASH_MODE);
            if (num != null && num.intValue() == 2) {
                return true;
            }
        }
        return false;
    }
}
