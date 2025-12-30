package A2;

import androidx.compose.runtime.internal.P;
import kotlin.jvm.internal.L;
import kotlinx.serialization.internal.E0;
import kotlinx.serialization.w;

@P
@w
/* loaded from: classes10.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    public final String f55a;

    static {
        new b();
    }

    public c(int i12, String str) {
        if (1 == (i12 & 1)) {
            this.f55a = str;
        } else {
            a.f53a.getClass();
            E0.b(i12, 1, a.f54b);
            throw null;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof c) && L.f(this.f55a, ((c) obj).f55a);
    }

    public final int hashCode() {
        return this.f55a.hashCode();
    }

    public final String toString() {
        return AK.c.s(new StringBuilder("SessionIdRequest(sessionId="), this.f55a, ")");
    }

    public c(String str) {
        this.f55a = str;
    }
}
