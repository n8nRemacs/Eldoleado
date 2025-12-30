package an;

import Hr.InterfaceC14024a;
import Y61.k;
import android.content.Context;
import androidx.compose.runtime.internal.P;
import com.avito.android.di.module.C30102l3;
import dagger.internal.h;
import javax.inject.Provider;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: CartSheetAfterAddingIntentFactoryImpl_Factory.kt */
@P
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0003¨\u0006\u0004"}, d2 = {"Lan/e;", "Ldagger/internal/h;", "Lan/c;", "a", "_avito_cart-sheet-after-adding_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes12.dex */
public final class e implements h<C19914c> {

    /* renamed from: c, reason: collision with root package name */
    @k
    public static final a f21161c = new a(null);

    /* renamed from: a, reason: collision with root package name */
    @k
    public final Provider<InterfaceC14024a> f21162a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final C30102l3 f21163b;

    /* compiled from: CartSheetAfterAddingIntentFactoryImpl_Factory.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lan/e$a;", "", "<init>", "()V", "_avito_cart-sheet-after-adding_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class a {
        public /* synthetic */ a(C42822w c42822w) {
            this();
        }

        public a() {
        }
    }

    public e(@k C30102l3 c30102l3, @k Provider provider) {
        this.f21162a = provider;
        this.f21163b = c30102l3;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        InterfaceC14024a interfaceC14024a = this.f21162a.get();
        Context context = (Context) this.f21163b.get();
        f21161c.getClass();
        return new C19914c(interfaceC14024a, context);
    }
}
