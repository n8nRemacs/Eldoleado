package a60;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.runtime.internal.P;
import com.avito.android.analytics.screens.mvi.q;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: CheckMergePossibilityState.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"La60/c;", "Lcom/avito/android/analytics/screens/mvi/q;", "_avito_passport_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final /* data */ class c extends q {

    /* renamed from: b, reason: collision with root package name */
    public final boolean f20744b;

    /* renamed from: c, reason: collision with root package name */
    public final boolean f20745c;

    /* JADX WARN: Illegal instructions before constructor call */
    public c() {
        boolean z12 = false;
        this(z12, z12, 3, null);
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return this.f20744b == cVar.f20744b && this.f20745c == cVar.f20745c;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.f20745c) + (Boolean.hashCode(this.f20744b) * 31);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("CheckMergePossibilityState(isLoading=");
        sb2.append(this.f20744b);
        sb2.append(", hasUnknownError=");
        return r.x(sb2, this.f20745c, ')');
    }

    public /* synthetic */ c(boolean z12, boolean z13, int i12, C42822w c42822w) {
        this((i12 & 1) != 0 ? false : z12, (i12 & 2) != 0 ? false : z13);
    }

    public c(boolean z12, boolean z13) {
        this.f20744b = z12;
        this.f20745c = z13;
    }
}
