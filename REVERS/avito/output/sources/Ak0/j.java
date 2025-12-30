package AK0;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.preference.s;
import javax.inject.Inject;
import kotlin.Metadata;

/* compiled from: PreferenceFactory.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\t\b\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAK0/j;", "LAK0/i;", "<init>", "()V", "_avito-discouraged_persistence_core"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class j implements i {
    @Inject
    public j() {
        new Handler(Looper.getMainLooper());
    }

    @Override // AK0.i
    @Y61.k
    public final o a(@Y61.k Context context, @Y61.k String str) {
        return new o(context.getApplicationContext().getSharedPreferences(context.getPackageName() + '_' + str, 0));
    }

    @Override // AK0.i
    @Y61.k
    public final o b(@Y61.k Context context) {
        Context applicationContext = context.getApplicationContext();
        return new o(applicationContext.getSharedPreferences(s.a(applicationContext), 0));
    }
}
