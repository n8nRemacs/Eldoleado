package AC0;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import com.avito.android.remote.model.text.AttributedText;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: CptInfoCardContentDottedTextItem.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAC0/b;", "", "_avito_tariff-cpt_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class b {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final AttributedText f196a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final AttributedText f197b;

    public b(@k AttributedText attributedText, @k AttributedText attributedText2) {
        this.f196a = attributedText;
        this.f197b = attributedText2;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return L.f(this.f196a, bVar.f196a) && L.f(this.f197b, bVar.f197b);
    }

    public final int hashCode() {
        return this.f197b.hashCode() + (this.f196a.hashCode() * 31);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("CptInfoCardContentDottedTextItem(leftText=");
        sb2.append(this.f196a);
        sb2.append(", rightText=");
        return com.avito.android.actions_sheet.a.w(sb2, this.f197b, ')');
    }
}
