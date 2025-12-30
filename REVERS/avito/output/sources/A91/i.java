package A91;

import android.content.Context;
import android.os.Bundle;
import kotlin.jvm.internal.N;

/* loaded from: classes9.dex */
public final class i extends N implements Y41.a {

    /* renamed from: l, reason: collision with root package name */
    public final /* synthetic */ ru.mts.biometry.sdk.base.p f147l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public i(ru.mts.biometry.sdk.base.p pVar) {
        super(0);
        this.f147l = pVar;
    }

    @Override // Y41.a
    public final Object invoke() {
        Context context;
        ru.mts.biometry.sdk.base.p pVar = this.f147l;
        Bundle arguments = pVar.getArguments();
        int i12 = arguments != null ? arguments.getInt("arg_description", 0) : 0;
        if (i12 == 0 || (context = pVar.getContext()) == null) {
            return null;
        }
        return context.getString(i12);
    }
}
