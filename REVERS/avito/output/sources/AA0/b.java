package aA0;

import Y61.k;
import Y61.l;
import androidx.compose.runtime.C22026a;
import androidx.compose.runtime.internal.P;
import com.avito.android.deep_linking.links.DeepLink;
import com.avito.android.printable_text.PrintableText;
import com.avito.android.remote.error.ApiError;
import kotlin.Metadata;
import kotlin.jvm.internal.L;

/* compiled from: StrSellerImportantToNoteOneTimeEvent.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0006\u0002\u0003\u0004\u0005\u0006\u0007\u0082\u0001\u0006\b\t\n\u000b\f\r¨\u0006\u000e"}, d2 = {"LaA0/b;", "", "a", "b", "c", "d", "e", "f", "LaA0/b$a;", "LaA0/b$b;", "LaA0/b$c;", "LaA0/b$d;", "LaA0/b$e;", "LaA0/b$f;", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public interface b {

    /* compiled from: StrSellerImportantToNoteOneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaA0/b$a;", "LaA0/b;", "<init>", "()V", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class a implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final a f20789a = new a();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof a);
        }

        public final int hashCode() {
            return 1385837157;
        }

        @k
        public final String toString() {
            return "Close";
        }
    }

    /* compiled from: StrSellerImportantToNoteOneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaA0/b$b;", "LaA0/b;", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* renamed from: aA0.b$b, reason: collision with other inner class name */
    public static final /* data */ class C1486b implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final DeepLink f20790a;

        public C1486b(@k DeepLink deepLink) {
            this.f20790a = deepLink;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof C1486b) && L.f(this.f20790a, ((C1486b) obj).f20790a);
        }

        public final int hashCode() {
            return this.f20790a.hashCode();
        }

        @k
        public final String toString() {
            return com.avito.android.actions_sheet.a.v(new StringBuilder("LaunchDeeplink(deeplink="), this.f20790a, ')');
        }
    }

    /* compiled from: StrSellerImportantToNoteOneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaA0/b$c;", "LaA0/b;", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class c implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final String f20791a;

        public c(@k String str) {
            this.f20791a = str;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof c) && L.f(this.f20791a, ((c) obj).f20791a);
        }

        public final int hashCode() {
            return this.f20791a.hashCode();
        }

        @k
        public final String toString() {
            return C22026a.c(new StringBuilder("OpenWebView(url="), this.f20791a, ')');
        }
    }

    /* compiled from: StrSellerImportantToNoteOneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\n\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LaA0/b$d;", "LaA0/b;", "<init>", "()V", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class d implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public static final d f20792a = new d();

        public final boolean equals(@l Object obj) {
            return this == obj || (obj instanceof d);
        }

        public final int hashCode() {
            return 292025259;
        }

        @k
        public final String toString() {
            return "SendFinishResult";
        }
    }

    /* compiled from: StrSellerImportantToNoteOneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaA0/b$e;", "LaA0/b;", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class e implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final com.avito.android.str_seller_orders_important_to_note.domain.b f20793a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final String f20794b;

        public e(@k com.avito.android.str_seller_orders_important_to_note.domain.b bVar, @k String str) {
            this.f20793a = bVar;
            this.f20794b = str;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof e)) {
                return false;
            }
            e eVar = (e) obj;
            return L.f(this.f20793a, eVar.f20793a) && L.f(this.f20794b, eVar.f20794b);
        }

        public final int hashCode() {
            return this.f20794b.hashCode() + (this.f20793a.hashCode() * 31);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("ShowConfirmationBottomSheet(buttonAction=");
            sb2.append(this.f20793a);
            sb2.append(", bookingId=");
            return C22026a.c(sb2, this.f20794b, ')');
        }
    }

    /* compiled from: StrSellerImportantToNoteOneTimeEvent.kt */
    @P
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LaA0/b$f;", "LaA0/b;", "_avito_str-seller-orders-important-to-note_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class f implements b {

        /* renamed from: a, reason: collision with root package name */
        @k
        public final PrintableText f20795a;

        /* renamed from: b, reason: collision with root package name */
        @k
        public final ApiError f20796b;

        public f(@k PrintableText printableText, @k ApiError apiError) {
            this.f20795a = printableText;
            this.f20796b = apiError;
        }

        public final boolean equals(@l Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof f)) {
                return false;
            }
            f fVar = (f) obj;
            return L.f(this.f20795a, fVar.f20795a) && L.f(this.f20796b, fVar.f20796b);
        }

        public final int hashCode() {
            return this.f20796b.hashCode() + (this.f20795a.hashCode() * 31);
        }

        @k
        public final String toString() {
            StringBuilder sb2 = new StringBuilder("ShowErrorToast(message=");
            sb2.append(this.f20795a);
            sb2.append(", error=");
            return AK.c.n(sb2, this.f20796b, ')');
        }
    }
}
