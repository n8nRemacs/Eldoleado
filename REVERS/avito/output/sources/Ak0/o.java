package AK0;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import j.k0;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;

/* compiled from: SharedPreferencesImpl.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAK0/o;", "LAK0/l;", "_common_preferences_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SuppressLint({"ApplySharedPref"})
/* loaded from: classes5.dex */
public final class o implements l {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final SharedPreferences f316a;

    @k0
    public o(@Y61.k SharedPreferences sharedPreferences) {
        this.f316a = sharedPreferences;
    }

    @Override // AK0.l
    public final void a(int i12, @Y61.k String str) {
        this.f316a.edit().putInt(str, i12).commit();
    }

    @Override // AK0.l
    public final boolean c(@Y61.k String str) {
        return this.f316a.getBoolean(str, false);
    }

    @Override // AK0.l
    public final void clear() {
        this.f316a.edit().clear().commit();
    }

    @Override // AK0.l
    public final boolean contains(@Y61.k String str) {
        return this.f316a.contains(str);
    }

    @Override // AK0.l
    @Y61.l
    public final String d(@Y61.k String str) {
        Object obj = this.f316a.getAll().get(str);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    @Override // AK0.l
    @Y61.k
    /* renamed from: e, reason: from getter */
    public final SharedPreferences getF316a() {
        return this.f316a;
    }

    @Override // AK0.l
    @Y61.l
    public final Set<String> f(@Y61.k String str) {
        return this.f316a.getStringSet(str, null);
    }

    @Override // AK0.l
    @Y61.k
    public final Map<String, Object> getAll() {
        return this.f316a.getAll();
    }

    @Override // AK0.l
    public final boolean getBoolean(@Y61.k String str, boolean z12) {
        return this.f316a.getBoolean(str, z12);
    }

    @Override // AK0.l
    public final int getInt(@Y61.k String str, int i12) {
        return this.f316a.getInt(str, i12);
    }

    @Override // AK0.l
    public final long getLong(@Y61.k String str, long j12) {
        return this.f316a.getLong(str, j12);
    }

    @Override // AK0.l
    @Y61.l
    public final String getString(@Y61.k String str, @Y61.l String str2) {
        return this.f316a.getString(str, str2);
    }

    @Override // AK0.l
    public final void putBoolean(@Y61.k String str, boolean z12) {
        this.f316a.edit().putBoolean(str, z12).commit();
    }

    @Override // AK0.l
    public final void putLong(@Y61.k String str, long j12) {
        this.f316a.edit().putLong(str, j12).commit();
    }

    @Override // AK0.l
    public final void putString(@Y61.k String str, @Y61.l String str2) {
        this.f316a.edit().putString(str, str2).commit();
    }

    @Override // AK0.l
    public final void putStringSet(@Y61.k String str, @Y61.l Set<String> set) {
        this.f316a.edit().putStringSet(str, set).commit();
    }

    @Override // AK0.l
    public final void remove(@Y61.k String str) {
        this.f316a.edit().remove(str).commit();
    }

    @Override // AK0.l
    @Y61.l
    public final String getString(@Y61.k String str) {
        return this.f316a.getString(str, null);
    }
}
