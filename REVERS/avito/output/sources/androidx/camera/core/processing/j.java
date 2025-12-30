package androidx.camera.core.processing;

import android.util.Size;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.S;
import androidx.camera.core.impl.Timebase;
import androidx.camera.video.b0;
import androidx.camera.video.internal.encoder.InvalidConfigException;
import androidx.camera.video.internal.encoder.J;
import androidx.camera.video.internal.encoder.M;
import androidx.work.impl.model.H;
import androidx.work.multiprocess.RemoteWorkManagerClient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.C42745f0;
import q.InterfaceC47174a;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class j implements InterfaceC47174a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f24442a;

    public /* synthetic */ j(int i12) {
        this.f24442a = i12;
    }

    @Override // q.InterfaceC47174a
    public final Object apply(Object obj) {
        ArrayList arrayList = null;
        switch (this.f24442a) {
            case 0:
                return new k((androidx.camera.core.F) obj);
            case 1:
                J j12 = (J) obj;
                androidx.camera.video.impl.a<?> aVar = b0.c.f24879a;
                try {
                    return M.i(j12);
                } catch (InvalidConfigException unused) {
                    C20140q0.d(5, "VideoCapture");
                    return null;
                }
            case 2:
                S.c cVarA = (S.c) obj;
                if (cVarA == null) {
                    return null;
                }
                J.a aVarD = J.d();
                aVarD.f(cVarA.i());
                aVarD.g(cVarA.j());
                aVarD.h(new Size(cVarA.k(), cVarA.h()));
                aVarD.d(cVarA.f());
                aVarD.b(cVarA.c());
                aVarD.e(Timebase.f24048b);
                J jA2 = aVarD.a();
                try {
                    M mI2 = M.i(jA2);
                    int iE2 = jA2.e();
                    int iIntValue = ((Integer) mI2.f25127b.getBitrateRange().clamp(Integer.valueOf(iE2))).intValue();
                    if (iIntValue != iE2) {
                        cVarA = S.c.a(cVarA.e(), cVarA.i(), iIntValue, cVarA.f(), cVarA.k(), cVarA.h(), cVarA.j(), cVarA.b(), cVarA.d(), cVarA.g());
                    }
                    return cVarA;
                } catch (InvalidConfigException unused2) {
                    return null;
                }
            case 3:
                List list = (List) obj;
                H.a aVar2 = H.f55793y;
                if (list != null) {
                    List list2 = list;
                    arrayList = new ArrayList(C42745f0.q(list2, 10));
                    Iterator it = list2.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((H.c) it.next()).a());
                    }
                }
                return arrayList;
            default:
                j jVar = RemoteWorkManagerClient.f56113i;
                return null;
        }
    }
}
