package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;

/* compiled from: DecayAnimationSpec.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/core/S1;", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/O1;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
final class S1<V extends AbstractC20330x> implements O1<V> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final X f26139a;

    /* renamed from: b, reason: collision with root package name */
    public V f26140b;

    /* renamed from: c, reason: collision with root package name */
    public V f26141c;

    /* renamed from: d, reason: collision with root package name */
    public V f26142d;

    /* renamed from: e, reason: collision with root package name */
    public final float f26143e;

    public S1(@Y61.k X x12) {
        this.f26139a = x12;
        this.f26143e = x12.a();
    }

    @Override // androidx.compose.animation.core.O1
    /* renamed from: a, reason: from getter */
    public final float getF26143e() {
        return this.f26143e;
    }

    @Override // androidx.compose.animation.core.O1
    @Y61.k
    public final V b(long j12, @Y61.k V v12, @Y61.k V v13) {
        if (this.f26140b == null) {
            this.f26140b = (V) v12.c();
        }
        V v14 = this.f26140b;
        if (v14 == null) {
            v14 = null;
        }
        int iB2 = v14.b();
        for (int i12 = 0; i12 < iB2; i12++) {
            V v15 = this.f26140b;
            if (v15 == null) {
                v15 = null;
            }
            v15.e(this.f26139a.d(v12.a(i12), v13.a(i12), j12), i12);
        }
        V v16 = this.f26140b;
        if (v16 == null) {
            return null;
        }
        return v16;
    }

    @Override // androidx.compose.animation.core.O1
    @Y61.k
    public final V c(long j12, @Y61.k V v12, @Y61.k V v13) {
        if (this.f26141c == null) {
            this.f26141c = (V) v12.c();
        }
        V v14 = this.f26141c;
        if (v14 == null) {
            v14 = null;
        }
        int iB2 = v14.b();
        for (int i12 = 0; i12 < iB2; i12++) {
            V v15 = this.f26141c;
            if (v15 == null) {
                v15 = null;
            }
            v12.getClass();
            v15.e(this.f26139a.e(j12, v13.a(i12)), i12);
        }
        V v16 = this.f26141c;
        if (v16 == null) {
            return null;
        }
        return v16;
    }

    @Y61.k
    public final V d(@Y61.k V v12, @Y61.k V v13) {
        if (this.f26142d == null) {
            this.f26142d = (V) v12.c();
        }
        V v14 = this.f26142d;
        if (v14 == null) {
            v14 = null;
        }
        int iB2 = v14.b();
        for (int i12 = 0; i12 < iB2; i12++) {
            V v15 = this.f26142d;
            if (v15 == null) {
                v15 = null;
            }
            v15.e(this.f26139a.c(v12.a(i12), v13.a(i12)), i12);
        }
        V v16 = this.f26142d;
        if (v16 == null) {
            return null;
        }
        return v16;
    }
}
