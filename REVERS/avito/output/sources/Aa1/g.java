package aa1;

import android.graphics.Bitmap;
import android.graphics.Rect;
import kotlin.jvm.internal.N;
import kotlin.jvm.internal.l0;

/* loaded from: classes9.dex */
public final class g extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ n f20940l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ l0.h f20941m;

    /* renamed from: n, reason: collision with root package name */
    public final /* synthetic */ Rect f20942n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public g(n nVar, l0.h hVar, Rect rect) {
        super(0);
        this.f20940l = nVar;
        this.f20941m = hVar;
        this.f20942n = rect;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // Y41.a
    public final Object invoke() {
        return this.f20940l.f20958c.b((Bitmap) this.f20941m.f406842b, this.f20942n);
    }
}
