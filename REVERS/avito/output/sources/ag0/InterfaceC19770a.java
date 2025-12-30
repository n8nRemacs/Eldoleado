package aG0;

import Y61.k;
import Y61.l;
import com.avito.android.deep_linking.links.DeepLink;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: TrxPromoStatusAction.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bp\u0018\u00002\u00020\u0001:\u0004\u0002\u0003\u0004\u0005\u0082\u0001\u0004\u0006\u0007\b\t¨\u0006\n"}, d2 = {"LaG0/a;", "", "a", "b", "c", "d", "LaG0/a$a;", "LaG0/a$b;", "LaG0/a$c;", "LaG0/a$d;", "_avito_trx-promo_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aG0.a, reason: case insensitive filesystem */
/* loaded from: classes4.dex */
public interface InterfaceC19770a {

    /* compiled from: TrxPromoStatusAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaG0/a$a;", "LaG0/a;", "<init>", "()V", "_avito_trx-promo_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aG0.a$a, reason: collision with other inner class name */
    public static final class C1487a implements InterfaceC19770a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final C1487a f20841a = new C1487a();
    }

    /* compiled from: TrxPromoStatusAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaG0/a$b;", "LaG0/a;", "_avito_trx-promo_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aG0.a$b */
    public static final /* data */ class b implements InterfaceC19770a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f20842a;

        public b(@k DeepLink deepLink) {
            this.f20842a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && L.f(this.f20842a, ((b) obj).f20842a);
        }

        public final int hashCode() {
            return this.f20842a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("DeeplinkClicked(deeplink="), this.f20842a, ')');
        }
    }

    /* compiled from: TrxPromoStatusAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaG0/a$c;", "LaG0/a;", "<init>", "()V", "_avito_trx-promo_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aG0.a$c */
    public static final class c implements InterfaceC19770a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final c f20843a = new c();
    }

    /* compiled from: TrxPromoStatusAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaG0/a$d;", "LaG0/a;", "<init>", "()V", "_avito_trx-promo_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aG0.a$d */
    public static final class d implements InterfaceC19770a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final d f20844a = new d();
    }
}
