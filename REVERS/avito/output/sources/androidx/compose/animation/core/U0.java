package androidx.compose.animation.core;

import kotlin.Metadata;

/* compiled from: AnimationSpec.kt */
@X41.g
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\b\u0087@\u0018\u00002\u00020\u0001\u0088\u0001\u0002\u0092\u0001\u00020\u0003¨\u0006\u0004"}, d2 = {"Landroidx/compose/animation/core/U0;", "", "value", "", "animation-core_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes.dex */
public final class U0 {

    /* renamed from: a, reason: collision with root package name */
    public final long f26150a;

    public static long a(int i12) {
        V0.f26154b.getClass();
        return i12 * V0.f26155c;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof U0) {
            return this.f26150a == ((U0) obj).f26150a;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.f26150a);
    }

    public final String toString() {
        return androidx.appcompat.app.r.u(new StringBuilder("StartOffset(value="), this.f26150a, ')');
    }
}
