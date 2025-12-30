package aK;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import kotlin.Metadata;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.L;
import kotlin.text.C43066x;

/* compiled from: SealedToStringDelegate.kt */
@P
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0087\b\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0001¨\u0006\u0003"}, d2 = {"LaK/a;", "", "T", "_avito_iac-calls-history_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aK.a, reason: case insensitive filesystem */
/* loaded from: classes13.dex */
public final /* data */ class C19782a<T> {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final Class<T> f20865a;

    public C19782a(@k Class<T> cls) {
        this.f20865a = cls;
    }

    @k
    public final String a(@k String[] strArr, @k Object obj) {
        String simpleName;
        String canonicalName;
        try {
            canonicalName = obj.getClass().getCanonicalName();
        } catch (Exception unused) {
            simpleName = obj.getClass().getSimpleName();
        }
        if (canonicalName == null) {
            throw new NullPointerException();
        }
        simpleName = canonicalName.substring(C43066x.I(canonicalName, this.f20865a.getSimpleName(), 0, false, 6));
        return simpleName + '(' + C42756l.P(strArr, null, null, null, null, 63) + ')';
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C19782a) && L.f(this.f20865a, ((C19782a) obj).f20865a);
    }

    public final int hashCode() {
        return this.f20865a.hashCode();
    }

    @k
    public final String toString() {
        return "SealedToStringDelegate(rootClass=" + this.f20865a + ')';
    }
}
