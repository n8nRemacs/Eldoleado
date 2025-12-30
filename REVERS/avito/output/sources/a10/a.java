package A10;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.runtime.F3;
import com.avito.android.mortgage_calculator.internal.mvi.entity.state.CalculatorField;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: ChangeFieldValueAction.kt */
@F3
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0081\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA10/a;", "", "_avito_mortgage-calculator_util"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final /* data */ class a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final String f45a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final CalculatorField f46b;

    /* renamed from: c, reason: collision with root package name */
    @l
    public final String f47c;

    /* renamed from: d, reason: collision with root package name */
    @l
    public final String f48d;

    /* renamed from: e, reason: collision with root package name */
    public final long f49e;

    public a(@k String str, @k CalculatorField calculatorField, @l String str2, @l String str3, long j12) {
        this.f45a = str;
        this.f46b = calculatorField;
        this.f47c = str2;
        this.f48d = str3;
        this.f49e = j12;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return L.f(this.f45a, aVar.f45a) && this.f46b == aVar.f46b && L.f(this.f47c, aVar.f47c) && L.f(this.f48d, aVar.f48d) && this.f49e == aVar.f49e;
    }

    public final int hashCode() {
        int iHashCode = (this.f46b.hashCode() + (this.f45a.hashCode() * 31)) * 31;
        String str = this.f47c;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.f48d;
        return Long.hashCode(this.f49e) + ((iHashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("ChangeFieldValueAction(text=");
        sb2.append(this.f45a);
        sb2.append(", field=");
        sb2.append(this.f46b);
        sb2.append(", criticalCase=");
        sb2.append(this.f47c);
        sb2.append(", buttonName=");
        sb2.append(this.f48d);
        sb2.append(", value=");
        return r.u(sb2, this.f49e, ')');
    }
}
