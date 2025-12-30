package aa1;

import android.graphics.Bitmap;
import kotlin.jvm.internal.N;
import kotlin.jvm.internal.l0;
import wa1.C49587b;

/* loaded from: classes9.dex */
public final class k extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ n f20951l;

    /* renamed from: m, reason: collision with root package name */
    public final /* synthetic */ l0.h f20952m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public k(n nVar, l0.h hVar) {
        super(0);
        this.f20951l = nVar;
        this.f20952m = hVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // Y41.a
    public final Object invoke() {
        C49587b c49587b = this.f20951l.f20961f;
        Bitmap bitmap = (Bitmap) this.f20952m.f406842b;
        c49587b.getClass();
        return Boolean.valueOf(C49587b.a(bitmap, false));
    }
}
