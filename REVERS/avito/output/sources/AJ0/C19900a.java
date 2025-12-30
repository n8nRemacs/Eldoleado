package aj0;

import Y61.k;
import Y61.l;
import androidx.compose.foundation.H;
import androidx.compose.runtime.C22026a;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: RegistrationStep.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Laj0/a;", "", "_avito_gig_registration-self-employment-redirect-screen_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aj0.a, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final /* data */ class C19900a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final String f21123a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final String f21124b;

    /* renamed from: c, reason: collision with root package name */
    @k
    public final String f21125c;

    public C19900a(@k String str, @k String str2, @k String str3) {
        this.f21123a = str;
        this.f21124b = str2;
        this.f21125c = str3;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C19900a)) {
            return false;
        }
        C19900a c19900a = (C19900a) obj;
        return L.f(this.f21123a, c19900a.f21123a) && L.f(this.f21124b, c19900a.f21124b) && L.f(this.f21125c, c19900a.f21125c);
    }

    public final int hashCode() {
        return this.f21125c.hashCode() + H.d(this.f21123a.hashCode() * 31, 31, this.f21124b);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("RegistrationStep(title=");
        sb2.append(this.f21123a);
        sb2.append(", description=");
        sb2.append(this.f21124b);
        sb2.append(", number=");
        return C22026a.c(sb2, this.f21125c, ')');
    }
}
