package AK0;

import android.content.SharedPreferences;
import android.os.Handler;
import com.google.gson.Gson;
import java.util.Map;
import java.util.Set;
import kotlin.InterfaceC42830m;
import kotlin.Metadata;
import kotlin.jvm.internal.C42822w;

/* compiled from: DbPreferencesImpl.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAK0/b;", "LAK0/l;", "_avito-discouraged_persistence_core"}, k = 1, mv = {1, 9, 0}, xi = 48)
@InterfaceC42830m
/* loaded from: classes5.dex */
public final class b implements l {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final com.avito.android.util.preferences.db_preferences.c f295a;

    public b(com.avito.android.db.sqlbrite.a aVar, Gson gson, Handler handler, String str, int i12, C42822w c42822w) {
        this.f295a = new com.avito.android.util.preferences.db_preferences.c(aVar, gson, handler, (i12 & 8) != 0 ? "shared_preferences" : str);
    }

    @Override // AK0.l
    public final void a(int i12, @Y61.k String str) {
        SharedPreferences.Editor editorEdit = this.f295a.edit();
        editorEdit.putInt(str, i12);
        editorEdit.commit();
    }

    @Override // AK0.l
    public final boolean c(@Y61.k String str) {
        return this.f295a.getBoolean(str, false);
    }

    @Override // AK0.l
    public final void clear() {
        SharedPreferences.Editor editorEdit = this.f295a.edit();
        editorEdit.clear();
        editorEdit.commit();
    }

    @Override // AK0.l
    public final boolean contains(@Y61.k String str) {
        return this.f295a.contains(str);
    }

    @Override // AK0.l
    @Y61.l
    public final String d(@Y61.k String str) {
        return this.f295a.c(str);
    }

    @Override // AK0.l
    /* renamed from: e */
    public final SharedPreferences getF316a() {
        return this.f295a;
    }

    @Override // AK0.l
    @Y61.l
    public final Set<String> f(@Y61.k String str) {
        return this.f295a.getStringSet(str, null);
    }

    @Override // AK0.l
    @Y61.k
    public final Map<String, Object> getAll() {
        return this.f295a.getAll();
    }

    @Override // AK0.l
    public final boolean getBoolean(@Y61.k String str, boolean z12) {
        return this.f295a.getBoolean(str, z12);
    }

    @Override // AK0.l
    public final int getInt(@Y61.k String str, int i12) {
        return this.f295a.getInt(str, i12);
    }

    @Override // AK0.l
    public final long getLong(@Y61.k String str, long j12) {
        return this.f295a.getLong(str, j12);
    }

    @Override // AK0.l
    @Y61.l
    public final String getString(@Y61.k String str, @Y61.l String str2) {
        return this.f295a.getString(str, str2);
    }

    @Override // AK0.l
    public final void putBoolean(@Y61.k String str, boolean z12) {
        SharedPreferences.Editor editorEdit = this.f295a.edit();
        editorEdit.putBoolean(str, z12);
        editorEdit.commit();
    }

    @Override // AK0.l
    public final void putLong(@Y61.k String str, long j12) {
        SharedPreferences.Editor editorEdit = this.f295a.edit();
        editorEdit.putLong(str, j12);
        editorEdit.commit();
    }

    @Override // AK0.l
    public final void putString(@Y61.k String str, @Y61.l String str2) {
        SharedPreferences.Editor editorEdit = this.f295a.edit();
        editorEdit.putString(str, str2);
        editorEdit.commit();
    }

    @Override // AK0.l
    public final void putStringSet(@Y61.k String str, @Y61.l Set<String> set) {
        SharedPreferences.Editor editorEdit = this.f295a.edit();
        editorEdit.putStringSet(str, set);
        editorEdit.commit();
    }

    @Override // AK0.l
    public final void remove(@Y61.k String str) {
        SharedPreferences.Editor editorEdit = this.f295a.edit();
        editorEdit.remove(str);
        editorEdit.commit();
    }

    @Override // AK0.l
    @Y61.l
    public final String getString(@Y61.k String str) {
        return this.f295a.getString(str, null);
    }
}
