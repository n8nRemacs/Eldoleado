package Ai;

import Y61.k;
import androidx.fragment.app.ActivityC22955m;
import kotlin.Metadata;

/* compiled from: BottomSheetCloseHandler.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAi/b;", "LAi/c;", "_avito_beduin_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ai.b, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C13036b implements InterfaceC13037c {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final com.avito.android.lib.design.bottom_sheet.d f586a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final ActivityC22955m f587b;

    public C13036b(@k com.avito.android.lib.design.bottom_sheet.d dVar, @k ActivityC22955m activityC22955m) {
        this.f586a = dVar;
        this.f587b = activityC22955m;
    }

    @Override // Ai.InterfaceC13037c
    public final void close() {
        DialogInterfaceOnDismissListenerC13035a dialogInterfaceOnDismissListenerC13035a = new DialogInterfaceOnDismissListenerC13035a(this, 0);
        com.avito.android.lib.design.bottom_sheet.d dVar = this.f586a;
        dVar.setOnDismissListener(dialogInterfaceOnDismissListenerC13035a);
        dVar.r();
    }
}
