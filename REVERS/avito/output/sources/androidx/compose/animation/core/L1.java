package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: VectorizedAnimationSpec.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/core/L1;", "Landroidx/compose/animation/core/z;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class L1 implements InterfaceC20336z {

    /* renamed from: a, reason: collision with root package name */
    public final C20263a0[] f26106a;

    public L1(float f12, float f13, AbstractC20330x abstractC20330x) {
        int f26428e = abstractC20330x.getF26428e();
        C20263a0[] c20263a0Arr = new C20263a0[f26428e];
        for (int i12 = 0; i12 < f26428e; i12++) {
            c20263a0Arr[i12] = new C20263a0(f12, f13, abstractC20330x.a(i12));
        }
        this.f26106a = c20263a0Arr;
    }

    @Override // androidx.compose.animation.core.InterfaceC20336z
    public final W get(int i12) {
        return this.f26106a[i12];
    }
}
