package androidx.compose.animation.core;

import androidx.compose.animation.core.AbstractC20330x;
import androidx.compose.runtime.C22082i3;
import androidx.compose.runtime.C22126m3;
import androidx.compose.runtime.InterfaceC22204y1;
import kotlin.Metadata;

/* compiled from: AnimationState.kt */
@androidx.compose.runtime.internal.P
@kotlin.jvm.internal.s0
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0003*\u00020\u00022\u00020\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/o;", "T", "Landroidx/compose/animation/core/x;", "V", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: androidx.compose.animation.core.o, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20304o<T, V extends AbstractC20330x> {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final H1<T, V> f26385a;

    /* renamed from: b, reason: collision with root package name */
    public final T f26386b;

    /* renamed from: c, reason: collision with root package name */
    public final long f26387c;

    /* renamed from: d, reason: collision with root package name */
    @Y61.k
    public final kotlin.jvm.internal.N f26388d;

    /* renamed from: e, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26389e;

    /* renamed from: f, reason: collision with root package name */
    @Y61.k
    public V f26390f;

    /* renamed from: g, reason: collision with root package name */
    public long f26391g;

    /* renamed from: h, reason: collision with root package name */
    public long f26392h = Long.MIN_VALUE;

    /* renamed from: i, reason: collision with root package name */
    @Y61.k
    public final InterfaceC22204y1 f26393i = C22126m3.g(Boolean.TRUE);

    /* JADX WARN: Multi-variable type inference failed */
    public C20304o(Object obj, @Y61.k H1 h12, @Y61.k AbstractC20330x abstractC20330x, long j12, Object obj2, long j13, @Y61.k Y41.a aVar) {
        this.f26385a = h12;
        this.f26386b = obj2;
        this.f26387c = j13;
        this.f26388d = (kotlin.jvm.internal.N) aVar;
        this.f26389e = C22126m3.g(obj);
        this.f26390f = (V) C20333y.a(abstractC20330x);
        this.f26391g = j12;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [Y41.a, kotlin.jvm.internal.N] */
    public final void a() {
        ((C22082i3) this.f26393i).setValue(Boolean.FALSE);
        this.f26388d.invoke();
    }

    public final T b() {
        return this.f26385a.b().invoke(this.f26390f);
    }
}
