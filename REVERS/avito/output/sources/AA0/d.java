package aA0;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.internal.P;
import com.avito.android.remote.model.text.AttributedText;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.C42784z0;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: StrSellerImportantToNoteState.kt */
@P
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"LaA0/d;", "", "a", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class d {

    /* renamed from: c, reason: collision with root package name */
    @k
    public static final a f20803c = new a(null);

    /* renamed from: d, reason: collision with root package name */
    @k
    public static final d f20804d = new d(C42784z0.f406748b, null);

    /* renamed from: a, reason: collision with root package name */
    @k
    public final List<com.avito.conveyor_item.a> f20805a;

    /* renamed from: b, reason: collision with root package name */
    @l
    public final AttributedText f20806b;

    /* compiled from: StrSellerImportantToNoteState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaA0/d$a;", "", "<init>", "()V", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public d(@k List<? extends com.avito.conveyor_item.a> list, @l AttributedText attributedText) {
        this.f20805a = list;
        this.f20806b = attributedText;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return L.f(this.f20805a, dVar.f20805a) && L.f(this.f20806b, dVar.f20806b);
    }

    public final int hashCode() {
        int iHashCode = this.f20805a.hashCode() * 31;
        AttributedText attributedText = this.f20806b;
        return iHashCode + (attributedText == null ? 0 : attributedText.hashCode());
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("StrSellerImportantToNoteViewState(items=");
        sb2.append(this.f20805a);
        sb2.append(", title=");
        return com.avito.android.actions_sheet.a.w(sb2, this.f20806b, ')');
    }
}
