package aL;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import kotlin.Metadata;
import kotlin.collections.C42745f0;
import kotlin.collections.C42756l;
import kotlin.jvm.internal.L;
import kotlin.text.C43066x;

/* compiled from: SealedToStringDelegate.kt */
@P
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0087\b\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0001¨\u0006\u0003"}, d2 = {"LaL/a;", "", "T", "_avito_iac-dialer-finished_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aL.a, reason: case insensitive filesystem */
/* loaded from: classes14.dex */
public final /* data */ class C19787a<T> {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final Class<T> f20868a;

    public C19787a(@k Class<T> cls) {
        this.f20868a = cls;
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
        simpleName = canonicalName.substring(C43066x.I(canonicalName, this.f20868a.getSimpleName(), 0, false, 6));
        return simpleName + '(' + C42745f0.O(C42756l.B(strArr), null, null, null, null, 63) + ')';
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C19787a) && L.f(this.f20868a, ((C19787a) obj).f20868a);
    }

    public final int hashCode() {
        return this.f20868a.hashCode();
    }

    @k
    public final String toString() {
        return "SealedToStringDelegate(rootClass=" + this.f20868a + ')';
    }
}
