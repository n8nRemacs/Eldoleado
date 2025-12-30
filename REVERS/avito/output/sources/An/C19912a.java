package an;

import Y61.k;
import Y61.l;
import androidx.appcompat.app.r;
import androidx.compose.runtime.C22026a;
import androidx.compose.runtime.internal.P;
import com.avito.android.rec.ScreenSource;
import com.avito.android.remote.model.Image;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: AdvertDetailsFastOpenArgs.kt */
@P
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lan/a;", "", "_avito_cart-sheet-after-adding_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: an.a, reason: case insensitive filesystem */
/* loaded from: classes12.dex */
public final /* data */ class C19912a {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final String f21154a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final ScreenSource.CART_SIMILAR_ITEMS f21155b;

    /* renamed from: c, reason: collision with root package name */
    public final long f21156c;

    /* renamed from: d, reason: collision with root package name */
    @l
    public final Image f21157d;

    /* renamed from: e, reason: collision with root package name */
    @l
    public final String f21158e;

    public C19912a(@k String str, @k ScreenSource.CART_SIMILAR_ITEMS cart_similar_items, long j12, @l Image image, @l String str2) {
        this.f21154a = str;
        this.f21155b = cart_similar_items;
        this.f21156c = j12;
        this.f21157d = image;
        this.f21158e = str2;
    }

    public final boolean equals(@l Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C19912a)) {
            return false;
        }
        C19912a c19912a = (C19912a) obj;
        return L.f(this.f21154a, c19912a.f21154a) && L.f(this.f21155b, c19912a.f21155b) && this.f21156c == c19912a.f21156c && L.f(this.f21157d, c19912a.f21157d) && L.f(this.f21158e, c19912a.f21158e);
    }

    public final int hashCode() {
        int iG2 = r.g((this.f21155b.hashCode() + (this.f21154a.hashCode() * 31)) * 31, 31, this.f21156c);
        Image image = this.f21157d;
        int iHashCode = (iG2 + (image == null ? 0 : image.hashCode())) * 31;
        String str = this.f21158e;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }

    @k
    public final String toString() {
        StringBuilder sb2 = new StringBuilder("AdvertDetailsFastOpenArgs(title=");
        sb2.append(this.f21154a);
        sb2.append(", screenSource=");
        sb2.append(this.f21155b);
        sb2.append(", clickTime=");
        sb2.append(this.f21156c);
        sb2.append(", image=");
        sb2.append(this.f21157d);
        sb2.append(", price=");
        return C22026a.c(sb2, this.f21158e, ')');
    }
}
