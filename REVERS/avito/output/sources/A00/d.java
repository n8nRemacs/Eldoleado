package A00;

import Y61.k;
import androidx.compose.runtime.internal.P;
import com.avito.android.mortgage.person_form.list.items.PersonFormCompactItem;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: PersonFormSpanProvider.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LA00/d;", "Lcom/avito/android/mortgage/util/a;", "_avito_mortgage_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes15.dex */
public final class d extends com.avito.android.mortgage.util.a {

    /* renamed from: c, reason: collision with root package name */
    public final int f35c;

    public d() {
        this(0, 1, null);
    }

    @Override // com.avito.android.mortgage.util.f
    /* renamed from: a, reason: from getter */
    public final int getF35c() {
        return this.f35c;
    }

    @Override // com.avito.android.mortgage.util.a
    public final int c(@k com.avito.conveyor_item.a aVar) {
        if ((aVar instanceof PersonFormCompactItem) && ((PersonFormCompactItem) aVar).getF200752k()) {
            return 1;
        }
        return this.f35c;
    }

    public d(int i12, int i13, C42822w c42822w) {
        this.f35c = (i13 & 1) != 0 ? 2 : i12;
    }
}
