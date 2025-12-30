package androidx.camera.core.imagecapture;

import android.graphics.Matrix;
import android.graphics.Rect;
import androidx.camera.core.C20056e0;
import com.google.common.util.concurrent.D0;
import j.X;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: ProcessingRequest.java */
@X
/* loaded from: classes.dex */
class B {

    /* renamed from: a, reason: collision with root package name */
    @j.P
    public final C20056e0.l f23762a;

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final Rect f23763b;

    /* renamed from: c, reason: collision with root package name */
    public final int f23764c;

    /* renamed from: d, reason: collision with root package name */
    public final int f23765d;

    /* renamed from: e, reason: collision with root package name */
    @j.N
    public final Matrix f23766e;

    /* renamed from: f, reason: collision with root package name */
    @j.N
    public final D f23767f;

    /* renamed from: g, reason: collision with root package name */
    @j.N
    public final String f23768g;

    /* renamed from: h, reason: collision with root package name */
    @j.N
    public final ArrayList f23769h = new ArrayList();

    /* renamed from: i, reason: collision with root package name */
    @j.N
    public final D0<Void> f23770i;

    public B(@j.N androidx.camera.core.impl.G g12, @j.P C20056e0.l lVar, @j.N Rect rect, int i12, int i13, @j.N Matrix matrix, @j.N D d12, @j.N D0 d02) {
        this.f23762a = lVar;
        this.f23765d = i13;
        this.f23764c = i12;
        this.f23763b = rect;
        this.f23766e = matrix;
        this.f23767f = d12;
        this.f23768g = String.valueOf(g12.hashCode());
        List<androidx.camera.core.impl.I> listA = g12.a();
        Objects.requireNonNull(listA);
        for (androidx.camera.core.impl.I i14 : listA) {
            ArrayList arrayList = this.f23769h;
            i14.getClass();
            arrayList.add(0);
        }
        this.f23770i = d02;
    }
}
