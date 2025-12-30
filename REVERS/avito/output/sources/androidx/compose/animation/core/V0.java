package androidx.compose.animation.core;

import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: AnimationSpec.kt */
@X41.g
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0087@\u0018\u00002\u00020\u0001:\u0001\u0002\u0088\u0001\u0003\u0092\u0001\u00020\u0004¨\u0006\u0005"}, d2 = {"Landroidx/compose/animation/core/V0;", "", "a", "value", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class V0 {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public static final a f26154b = new a(null);

    /* renamed from: c, reason: collision with root package name */
    public static final int f26155c = -1;

    /* renamed from: a, reason: collision with root package name */
    public final int f26156a;

    /* compiled from: AnimationSpec.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/core/V0$a;", "", "<init>", "()V", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof V0) {
            return this.f26156a == ((V0) obj).f26156a;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.f26156a);
    }

    public final String toString() {
        return androidx.appcompat.app.r.t(new StringBuilder("StartOffsetType(value="), this.f26156a, ')');
    }
}
