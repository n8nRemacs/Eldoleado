package androidx.compose.animation;

import androidx.compose.animation.core.C20327w;
import kotlin.Metadata;

/* compiled from: ColorVectorConverter.kt */
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Landroidx/compose/animation/core/w;", "vector", "Landroidx/compose/ui/graphics/T;", "invoke-vNxB06k", "(Landroidx/compose/animation/core/w;)J", "<anonymous>"}, k = 3, mv = {1, 9, 0})
/* renamed from: androidx.compose.animation.t0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20387t0 extends kotlin.jvm.internal.N implements Y41.l<C20327w, androidx.compose.ui.graphics.T> {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ androidx.compose.ui.graphics.colorspace.c f26592l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C20387t0(androidx.compose.ui.graphics.colorspace.c cVar) {
        super(1);
        this.f26592l = cVar;
    }

    @Override // Y41.l
    public final androidx.compose.ui.graphics.T invoke(C20327w c20327w) {
        C20327w c20327w2 = c20327w;
        float f12 = c20327w2.f26425b;
        if (f12 < 0.0f) {
            f12 = 0.0f;
        }
        if (f12 > 1.0f) {
            f12 = 1.0f;
        }
        float f13 = c20327w2.f26426c;
        if (f13 < -0.5f) {
            f13 = -0.5f;
        }
        if (f13 > 0.5f) {
            f13 = 0.5f;
        }
        float f14 = c20327w2.f26427d;
        float f15 = f14 >= -0.5f ? f14 : -0.5f;
        float f16 = f15 <= 0.5f ? f15 : 0.5f;
        float f17 = c20327w2.f26424a;
        float f18 = f17 >= 0.0f ? f17 : 0.0f;
        float f19 = f18 <= 1.0f ? f18 : 1.0f;
        androidx.compose.ui.graphics.colorspace.f.f39390a.getClass();
        return androidx.compose.ui.graphics.T.a(androidx.compose.ui.graphics.T.b(androidx.compose.ui.graphics.V.a(f12, f13, f16, f19, androidx.compose.ui.graphics.colorspace.f.f39414y), this.f26592l));
    }
}
