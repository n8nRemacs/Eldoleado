package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.media.ImageWriter;
import androidx.camera.camera2.internal.compat.quirk.C20001l;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.C20094g0;

/* compiled from: ZslControlImpl.java */
@j.X
/* loaded from: classes.dex */
final class d1 implements b1 {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final androidx.camera.camera2.internal.compat.n f23313a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    @j.k0
    public final androidx.camera.core.internal.utils.f f23314b;

    /* renamed from: c, reason: collision with root package name */
    public boolean f23315c = false;

    /* renamed from: d, reason: collision with root package name */
    public boolean f23316d = false;

    /* renamed from: e, reason: collision with root package name */
    public final boolean f23317e;

    /* renamed from: f, reason: collision with root package name */
    public final boolean f23318f;

    /* renamed from: g, reason: collision with root package name */
    public androidx.camera.core.y0 f23319g;

    /* renamed from: h, reason: collision with root package name */
    public AbstractC20109p f23320h;

    /* renamed from: i, reason: collision with root package name */
    public C20094g0 f23321i;

    /* renamed from: j, reason: collision with root package name */
    @j.P
    public ImageWriter f23322j;

    public d1(@j.N androidx.camera.camera2.internal.compat.n nVar) {
        boolean z12;
        this.f23317e = false;
        this.f23318f = false;
        this.f23313a = nVar;
        int[] iArr = (int[]) nVar.a(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        if (iArr != null) {
            for (int i12 : iArr) {
                if (i12 == 4) {
                    z12 = true;
                    break;
                }
            }
            z12 = false;
        } else {
            z12 = false;
        }
        this.f23317e = z12;
        this.f23318f = C20001l.f23216a.b(androidx.camera.camera2.internal.compat.quirk.K.class) != null;
        this.f23314b = new androidx.camera.core.internal.utils.f(3, new G(12));
    }
}
