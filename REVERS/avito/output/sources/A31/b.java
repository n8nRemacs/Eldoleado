package A31;

import kotlin.jvm.internal.L;

/* loaded from: classes8.dex */
public final class b {

    /* renamed from: c, reason: collision with root package name */
    public static final b f78c = new b(null, "");

    /* renamed from: a, reason: collision with root package name */
    public final String f79a;

    /* renamed from: b, reason: collision with root package name */
    public final Integer f80b;

    public b(Integer num, String str) {
        this.f79a = str;
        this.f80b = num;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return L.f(this.f79a, bVar.f79a) && L.f(this.f80b, bVar.f80b);
    }

    public final int hashCode() {
        int iHashCode = this.f79a.hashCode() * 31;
        Integer num = this.f80b;
        return iHashCode + (num == null ? 0 : num.hashCode());
    }

    public final String toString() {
        return "WebViewSsoState(link=" + this.f79a + ", color=" + this.f80b + ")";
    }
}
