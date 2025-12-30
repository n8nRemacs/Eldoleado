package aA0;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.C22026a;
import androidx.compose.runtime.internal.P;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.str_seller_orders_important_to_note.domain.BookingButton;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: StrSellerImportantToNoteAction.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0006\u0002\u0003\u0004\u0005\u0006\u0007\u0082\u0001\u0006\b\t\n\u000b\f\r¨\u0006\u000e"}, d2 = {"LaA0/a;", "", "a", "b", "c", "d", "e", "f", "LaA0/a$a;", "LaA0/a$b;", "LaA0/a$c;", "LaA0/a$d;", "LaA0/a$e;", "LaA0/a$f;", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: aA0.a, reason: case insensitive filesystem */
/* loaded from: classes4.dex */
public interface InterfaceC19747a {

    /* compiled from: StrSellerImportantToNoteAction.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaA0/a$a;", "LaA0/a;", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aA0.a$a, reason: collision with other inner class name */
    public static final /* data */ class C1485a implements InterfaceC19747a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final com.avito.android.str_seller_orders_important_to_note.domain.b f20781a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final String f20782b;

        public C1485a(@k com.avito.android.str_seller_orders_important_to_note.domain.b bVar, @k String str) {
            this.f20781a = bVar;
            this.f20782b = str;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof C1485a)) {
                return false;
            }
            C1485a c1485a = (C1485a) obj;
            return L.f(this.f20781a, c1485a.f20781a) && L.f(this.f20782b, c1485a.f20782b);
        }

        public final int hashCode() {
            return this.f20782b.hashCode() + (this.f20781a.hashCode() * 31);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("ConfirmationDialogApproveClick(buttonAction=");
            sb2.append(this.f20781a);
            sb2.append(", bookingId=");
            return C22026a.c(sb2, this.f20782b, ')');
        }
    }

    /* compiled from: StrSellerImportantToNoteAction.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaA0/a$b;", "LaA0/a;", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aA0.a$b */
    public static final /* data */ class b implements InterfaceC19747a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final com.avito.android.str_seller_orders_important_to_note.domain.b f20783a;

        public b(@k com.avito.android.str_seller_orders_important_to_note.domain.b bVar) {
            this.f20783a = bVar;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && L.f(this.f20783a, ((b) obj).f20783a);
        }

        public final int hashCode() {
            return this.f20783a.hashCode();
        }

        @k
        public final String toString() {
            return "ConfirmationDialogCancelClick(buttonAction=" + this.f20783a + ')';
        }
    }

    /* compiled from: StrSellerImportantToNoteAction.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaA0/a$c;", "LaA0/a;", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aA0.a$c */
    public static final /* data */ class c implements InterfaceC19747a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final BookingButton f20784a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final String f20785b;

        public c(@k BookingButton bookingButton, @k String str) {
            this.f20784a = bookingButton;
            this.f20785b = str;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return L.f(this.f20784a, cVar.f20784a) && L.f(this.f20785b, cVar.f20785b);
        }

        public final int hashCode() {
            return this.f20785b.hashCode() + (this.f20784a.hashCode() * 31);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("OnBookingButtonClick(button=");
            sb2.append(this.f20784a);
            sb2.append(", bookingId=");
            return C22026a.c(sb2, this.f20785b, ')');
        }
    }

    /* compiled from: StrSellerImportantToNoteAction.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaA0/a$d;", "LaA0/a;", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aA0.a$d */
    public static final /* data */ class d implements InterfaceC19747a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f20786a;

        public d(@k DeepLink deepLink) {
            this.f20786a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof d) && L.f(this.f20786a, ((d) obj).f20786a);
        }

        public final int hashCode() {
            return this.f20786a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("OnBookingClick(deepLink="), this.f20786a, ')');
        }
    }

    /* compiled from: StrSellerImportantToNoteAction.kt */
    @P
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaA0/a$e;", "LaA0/a;", "<init>", "()V", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aA0.a$e */
    public static final /* data */ class e implements InterfaceC19747a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final e f20787a = new e();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof e);
        }

        public final int hashCode() {
            return -1384215883;
        }

        @k
        public final String toString() {
            return "OnClose";
        }
    }

    /* compiled from: StrSellerImportantToNoteAction.kt */
    @P
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaA0/a$f;", "LaA0/a;", "<init>", "()V", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aA0.a$f */
    public static final /* data */ class f implements InterfaceC19747a {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final f f20788a = new f();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof f);
        }

        public final int hashCode() {
            return -1409778694;
        }

        @k
        public final String toString() {
            return "OnReturnToScreen";
        }
    }
}
