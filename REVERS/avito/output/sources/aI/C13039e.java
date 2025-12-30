package Ai;

import Y61.k;
import kotlin.Metadata;

/* compiled from: PushBottomSheetCloseHandler.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAi/e;", "LAi/c;", "_avito_beduin_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ai.e, reason: case insensitive filesystem */
/* loaded from: classes11.dex */
public final class C13039e implements InterfaceC13037c {

    /* renamed from: a, reason: collision with root package name */
    @k
    public final com.avito.android.beduin.ui.universal.c f589a;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final String f590b;

    public C13039e(@k com.avito.android.beduin.ui.universal.c cVar, @k String str) {
        this.f589a = cVar;
        this.f590b = str;
    }

    @Override // Ai.InterfaceC13037c
    public final void close() {
        this.f589a.M1(this.f590b);
    }
}
