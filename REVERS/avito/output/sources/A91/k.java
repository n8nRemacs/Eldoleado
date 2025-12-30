package A91;

import android.os.Bundle;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class k extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ ru.mts.biometry.sdk.base.p f149l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public k(ru.mts.biometry.sdk.base.p pVar) {
        super(0);
        this.f149l = pVar;
    }

    @Override // Y41.a
    public final Object invoke() {
        ru.mts.biometry.sdk.base.p pVar = this.f149l;
        Bundle arguments = pVar.getArguments();
        int i12 = arguments != null ? arguments.getInt("arg_icon", 0) : 0;
        if (i12 != 0) {
            return androidx.core.content.d.getDrawable(pVar.requireContext(), i12);
        }
        Bundle arguments2 = pVar.getArguments();
        int i13 = arguments2 != null ? arguments2.getInt("arg_icon_attr", 0) : 0;
        if (i13 != 0) {
            return H91.b.c(i13, pVar.requireContext());
        }
        return null;
    }
}
