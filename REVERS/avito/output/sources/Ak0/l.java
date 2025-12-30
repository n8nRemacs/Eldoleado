package AK0;

import android.content.SharedPreferences;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;

/* compiled from: Preferences.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\bf\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAK0/l;", "", "_common_preferences_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public interface l {
    void a(int i12, @Y61.k String str);

    boolean c(@Y61.k String str);

    void clear();

    boolean contains(@Y61.k String str);

    @Y61.l
    String d(@Y61.k String str);

    @Y61.k
    SharedPreferences e();

    @Y61.l
    Set<String> f(@Y61.k String str);

    @Y61.k
    Map<String, Object> getAll();

    boolean getBoolean(@Y61.k String str, boolean z12);

    int getInt(@Y61.k String str, int i12);

    long getLong(@Y61.k String str, long j12);

    @Y61.l
    String getString(@Y61.k String str);

    @Y61.l
    String getString(@Y61.k String str, @Y61.l String str2);

    void putBoolean(@Y61.k String str, boolean z12);

    void putLong(@Y61.k String str, long j12);

    void putString(@Y61.k String str, @Y61.l String str2);

    void putStringSet(@Y61.k String str, @Y61.l Set<String> set);

    void remove(@Y61.k String str);
}
