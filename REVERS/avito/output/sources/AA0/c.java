package aA0;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.runtime.internal.P;
import com.avito.android.analytics.screens.mvi.q;
import com.avito.android.remote.model.text.AttributedText;
import com.avito.android.str_seller_orders_important_to_note.ui.i;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.C42784z0;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: StrSellerImportantToNoteState.kt */
@P
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"LaA0/c;", "Lcom/avito/android/analytics/screens/mvi/q;", "a", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class c extends q {

    /* renamed from: f, reason: collision with root package name */
    @k
    public static final a f20797f = new a(null);

    /* renamed from: g, reason: collision with root package name */
    @k
    public static final c f20798g;

    /* renamed from: b, reason: collision with root package name */
    @l
    public final AttributedText f20799b;

    /* renamed from: c, reason: collision with root package name */
    @l
    public final List<i> f20800c;

    /* renamed from: d, reason: collision with root package name */
    public final boolean f20801d;

    /* renamed from: e, reason: collision with root package name */
    @k
    public final d f20802e;

    /* compiled from: StrSellerImportantToNoteState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaA0/c$a;", "", "<init>", "()V", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    static {
        C42784z0 c42784z0 = C42784z0.f406748b;
        d.f20803c.getClass();
        f20798g = new c(null, c42784z0, false, d.f20804d);
    }

    public c(@l AttributedText attributedText, @l List<i> list, boolean z12, @k d dVar) {
        this.f20799b = attributedText;
        this.f20800c = list;
        this.f20801d = z12;
        this.f20802e = dVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static c a(c cVar, AttributedText attributedText, ArrayList arrayList, boolean z12, d dVar, int i12) {
        if ((i12 & 1) != 0) {
            attributedText = cVar.f20799b;
        }
        List list = arrayList;
        if ((i12 & 2) != 0) {
            list = cVar.f20800c;
        }
        if ((i12 & 4) != 0) {
            z12 = cVar.f20801d;
        }
        if ((i12 & 8) != 0) {
            dVar = cVar.f20802e;
        }
        cVar.getClass();
        return new c(attributedText, list, z12, dVar);
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return L.f(this.f20799b, cVar.f20799b) && L.f(this.f20800c, cVar.f20800c) && this.f20801d == cVar.f20801d && L.f(this.f20802e, cVar.f20802e);
    }

    public final int hashCode() {
        AttributedText attributedText = this.f20799b;
        int iHashCode = (attributedText == null ? 0 : attributedText.hashCode()) * 31;
        List<i> list = this.f20800c;
        return this.f20802e.hashCode() + r.i((iHashCode + (list != null ? list.hashCode() : 0)) * 31, 31, this.f20801d);
    }

    @k
    public final String toString() {
        return "StrSellerImportantToNoteState(title=" + this.f20799b + ", orders=" + this.f20800c + ", isLoading=" + this.f20801d + ", viewState=" + this.f20802e + ')';
    }
}
