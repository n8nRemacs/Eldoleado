package androidx.compose.animation;

import androidx.compose.runtime.InterfaceC22192v1;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimatedContent.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Landroidx/compose/animation/w0;", "", "animation_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.w0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20396w0 {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final AbstractC20359j1 f26603a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final AbstractC20365l1 f26604b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22192v1 f26605c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.l
    public o2 f26606d;

    public C20396w0(AbstractC20359j1 abstractC20359j1, AbstractC20365l1 abstractC20365l1, float f12, o2 o2Var, int i12, C42822w c42822w) {
        f12 = (i12 & 4) != 0 ? 0.0f : f12;
        o2Var = (i12 & 8) != 0 ? C20389u.d(null, 3) : o2Var;
        this.f26603a = abstractC20359j1;
        this.f26604b = abstractC20365l1;
        this.f26605c = androidx.compose.runtime.Q1.a(f12);
        this.f26606d = o2Var;
    }
}
