package aN;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.foundation.H;
import androidx.compose.runtime.C22026a;
import com.avito.android.analytics.screens.mvi.q;
import com.avito.android.remote.model.AdvertDetails;
import com.avito.android.serp.adapter.PersistableSpannedItem;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.C42784z0;
import kotlin.jvm.internal.C42822w;
import kotlin.jvm.internal.L;

/* compiled from: ImvGoodsAdvertState.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"LaN/c;", "Lcom/avito/android/analytics/screens/mvi/q;", "a", "_avito_imv-goods-advert_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes14.dex */
public final /* data */ class c extends q {

    /* renamed from: g, reason: collision with root package name */
    @k
    public static final a f20885g = new a(null);

    /* renamed from: h, reason: collision with root package name */
    @k
    public static final c f20886h = new c(null, 0, null, false, null, 31, null);

    /* renamed from: b, reason: collision with root package name */
    @l
    public final AdvertDetails f20887b;

    /* renamed from: c, reason: collision with root package name */
    public final int f20888c;

    /* renamed from: d, reason: collision with root package name */
    @k
    public final List<PersistableSpannedItem> f20889d;

    /* renamed from: e, reason: collision with root package name */
    public final boolean f20890e;

    /* renamed from: f, reason: collision with root package name */
    @l
    public final String f20891f;

    /* compiled from: ImvGoodsAdvertState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaN/c$a;", "", "<init>", "()V", "_avito_imv-goods-advert_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public c() {
        this(null, 0, null, false, null, 31, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static c a(c cVar, AdvertDetails advertDetails, int i12, ArrayList arrayList, boolean z12, String str, int i13) {
        if ((i13 & 1) != 0) {
            advertDetails = cVar.f20887b;
        }
        AdvertDetails advertDetails2 = advertDetails;
        if ((i13 & 2) != 0) {
            i12 = cVar.f20888c;
        }
        int i14 = i12;
        List list = arrayList;
        if ((i13 & 4) != 0) {
            list = cVar.f20889d;
        }
        List list2 = list;
        if ((i13 & 8) != 0) {
            z12 = cVar.f20890e;
        }
        boolean z13 = z12;
        if ((i13 & 16) != 0) {
            str = cVar.f20891f;
        }
        cVar.getClass();
        return new c(advertDetails2, i14, list2, z13, str);
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return L.f(this.f20887b, cVar.f20887b) && this.f20888c == cVar.f20888c && L.f(this.f20889d, cVar.f20889d) && this.f20890e == cVar.f20890e && L.f(this.f20891f, cVar.f20891f);
    }

    public final int hashCode() {
        AdvertDetails advertDetails = this.f20887b;
        int i12 = r.i(H.e(r.e(this.f20888c, (advertDetails == null ? 0 : advertDetails.hashCode()) * 31, 31), 31, this.f20889d), 31, this.f20890e);
        String str = this.f20891f;
        return i12 + (str != null ? str.hashCode() : 0);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("ImvGoodsAdvertState(advertDetails=");
        sb2.append(this.f20887b);
        sb2.append(", currentPhotoPosition=");
        sb2.append(this.f20888c);
        sb2.append(", data=");
        sb2.append(this.f20889d);
        sb2.append(", isError=");
        sb2.append(this.f20890e);
        sb2.append(", errorMessage=");
        return C22026a.c(sb2, this.f20891f, ')');
    }

    /* JADX WARN: Multi-variable type inference failed */
    public c(@l AdvertDetails advertDetails, int i12, @k List<? extends PersistableSpannedItem> list, boolean z12, @l String str) {
        this.f20887b = advertDetails;
        this.f20888c = i12;
        this.f20889d = list;
        this.f20890e = z12;
        this.f20891f = str;
    }

    public c(AdvertDetails advertDetails, int i12, List list, boolean z12, String str, int i13, C42822w c42822w) {
        this((i13 & 1) != 0 ? null : advertDetails, (i13 & 2) != 0 ? 0 : i12, (i13 & 4) != 0 ? C42784z0.f406748b : list, (i13 & 8) == 0 ? z12 : false, (i13 & 16) != 0 ? null : str);
    }
}
