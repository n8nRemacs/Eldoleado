package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import kotlin.Metadata;

/* compiled from: VectorizedAnimationSpec.kt */
@androidx.compose.runtime.internal.P
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/core/R1;", "Landroidx/compose/animation/core/x;", "V", "Landroidx/compose/animation/core/Q1;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class R1<V extends AbstractC20330x> implements Q1<V> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final InterfaceC20336z f26128a;

    /* renamed from: b, reason: collision with root package name */
    public V f26129b;

    /* renamed from: c, reason: collision with root package name */
    public V f26130c;

    /* renamed from: d, reason: collision with root package name */
    public V f26131d;

    /* compiled from: VectorizedAnimationSpec.kt */
    @Metadata(d1 = {"\u0000\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"androidx/compose/animation/core/R1$a", "Landroidx/compose/animation/core/z;", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a implements InterfaceC20336z {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ W f26132a;

        public a(W w12) {
            this.f26132a = w12;
        }

        @Override // androidx.compose.animation.core.InterfaceC20336z
        public final W get(int i12) {
            return this.f26132a;
        }
    }

    public R1(@Y61.k InterfaceC20336z interfaceC20336z) {
        this.f26128a = interfaceC20336z;
    }

    @Override // androidx.compose.animation.core.K1
    public final long b(@Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        int iB2 = v12.getF26404b();
        long jMax = 0;
        for (int i12 = 0; i12 < iB2; i12++) {
            jMax = Math.max(jMax, this.f26128a.get(i12).d(v12.a(i12), v13.a(i12), v14.a(i12)));
        }
        return jMax;
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V c(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        if (this.f26130c == null) {
            this.f26130c = (V) v14.c();
        }
        V v15 = this.f26130c;
        if (v15 == null) {
            v15 = null;
        }
        int iB2 = v15.getF26404b();
        for (int i12 = 0; i12 < iB2; i12++) {
            V v16 = this.f26130c;
            if (v16 == null) {
                v16 = null;
            }
            v16.e(this.f26128a.get(i12).c(j12, v12.a(i12), v13.a(i12), v14.a(i12)), i12);
        }
        V v17 = this.f26130c;
        if (v17 == null) {
            return null;
        }
        return v17;
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V d(@Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        if (this.f26131d == null) {
            this.f26131d = (V) v14.c();
        }
        V v15 = this.f26131d;
        if (v15 == null) {
            v15 = null;
        }
        int iB2 = v15.getF26404b();
        for (int i12 = 0; i12 < iB2; i12++) {
            V v16 = this.f26131d;
            if (v16 == null) {
                v16 = null;
            }
            v16.e(this.f26128a.get(i12).e(v12.a(i12), v13.a(i12), v14.a(i12)), i12);
        }
        V v17 = this.f26131d;
        if (v17 == null) {
            return null;
        }
        return v17;
    }

    @Override // androidx.compose.animation.core.K1
    @Y61.k
    public final V e(long j12, @Y61.k V v12, @Y61.k V v13, @Y61.k V v14) {
        if (this.f26129b == null) {
            this.f26129b = (V) v12.c();
        }
        V v15 = this.f26129b;
        if (v15 == null) {
            v15 = null;
        }
        int iB2 = v15.getF26404b();
        for (int i12 = 0; i12 < iB2; i12++) {
            V v16 = this.f26129b;
            if (v16 == null) {
                v16 = null;
            }
            v16.e(this.f26128a.get(i12).f(j12, v12.a(i12), v13.a(i12), v14.a(i12)), i12);
        }
        V v17 = this.f26129b;
        if (v17 == null) {
            return null;
        }
        return v17;
    }

    public R1(@Y61.k W w12) {
        this(new a(w12));
    }
}
