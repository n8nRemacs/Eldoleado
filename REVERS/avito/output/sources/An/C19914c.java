package an;

import Hr.InterfaceC14024a;
import Y61.k;
import Y61.l;
import android.content.Context;
import android.content.Intent;
import androidx.compose.runtime.internal.P;
import cn.C27223b;
import com.avito.android.cart_sheet_after_adding.ui.CartSheetAfterAddingActivity;
import com.avito.android.cart_sheet_after_adding.ui.CartSheetAfterAddingArguments;
import com.squareup.anvil.annotations.ContributesBinding;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: CartSheetAfterAddingIntentFactoryImpl.kt */
@P
@ContributesBinding
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lan/c;", "Lan/b;", "_avito_cart-sheet-after-adding_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: an.c, reason: case insensitive filesystem */
/* loaded from: classes12.dex */
public final class C19914c implements InterfaceC19913b {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final InterfaceC14024a f21159a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final Context f21160b;

    @Inject
    public C19914c(@k InterfaceC14024a interfaceC14024a, @k Context context) {
        this.f21159a = interfaceC14024a;
        this.f21160b = context;
    }

    @Override // an.InterfaceC19913b
    @k
    public final Intent a(@l String str, @l String str2, @k C27223b c27223b) {
        CartSheetAfterAddingArguments cartSheetAfterAddingArguments = new CartSheetAfterAddingArguments(this.f21159a.a(c27223b), str, str2);
        CartSheetAfterAddingActivity.f115457m.getClass();
        return new Intent(this.f21160b, (Class<?>) CartSheetAfterAddingActivity.class).putExtra("cartSheetAfterAddingArguments", cartSheetAfterAddingArguments);
    }
}
