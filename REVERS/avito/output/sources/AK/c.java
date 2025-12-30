package AK;

import JK.j;
import PK0.n;
import Y41.p;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import androidx.compose.runtime.B;
import androidx.core.view.J0;
import androidx.core.view.M;
import com.avito.android.beduin_models.DisplayingPredicate;
import com.avito.android.printable_text.PrintableText;
import com.avito.android.remote.error.ApiError;
import com.avito.android.remote.model.Image;
import com.avito.android.select.Arguments;
import com.avito.android.util.ApiException;
import com.yandex.div.json.l;
import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.ClassHandler;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.G0;
import kotlin.jvm.internal.J;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes14.dex */
public final /* synthetic */ class c implements j, l.a, D81.b, M {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f289b;

    public static int c(int i12, int i13, Map map) {
        return (map.hashCode() + i12) * i13;
    }

    public static n d(TypedArray typedArray, int i12, int i13, com.avito.android.lib.design.text_view.d dVar, Context context) {
        int resourceId = typedArray.getResourceId(i12, i13);
        dVar.getClass();
        return com.avito.android.lib.design.text_view.d.a(resourceId, context);
    }

    public static Bundle e(int i12, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt(str, i12);
        return bundle;
    }

    public static Integer f(int i12, Integer num, String str, String str2, HashMap map) {
        map.put(num, str);
        Integer numValueOf = Integer.valueOf(i12);
        map.put(numValueOf, str2);
        return numValueOf;
    }

    public static String g(int i12, String str) {
        return str + i12;
    }

    public static String i(int i12, String str, StringBuilder sb2) {
        sb2.append(i12);
        sb2.append(str);
        return sb2.toString();
    }

    public static String j(long j12, String str, StringBuilder sb2) {
        sb2.append(j12);
        sb2.append(str);
        return sb2.toString();
    }

    public static String k(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static String l(StringBuilder sb2, DisplayingPredicate displayingPredicate, char c12) {
        sb2.append(displayingPredicate);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String m(StringBuilder sb2, PrintableText printableText, char c12) {
        sb2.append(printableText);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String n(StringBuilder sb2, ApiError apiError, char c12) {
        sb2.append(apiError);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String o(StringBuilder sb2, Image image, char c12) {
        sb2.append(image);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String p(StringBuilder sb2, Arguments arguments, char c12) {
        sb2.append(arguments);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String q(StringBuilder sb2, ApiException apiException, char c12) {
        sb2.append(apiException);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String r(StringBuilder sb2, CharSequence charSequence, char c12) {
        sb2.append((Object) charSequence);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String s(StringBuilder sb2, String str, String str2) {
        sb2.append(str);
        sb2.append(str2);
        return sb2.toString();
    }

    public static String t(StringBuilder sb2, LocalDate localDate, char c12) {
        sb2.append(localDate);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String u(StringBuilder sb2, Set set, char c12) {
        sb2.append(set);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String v(StringBuilder sb2, G0 g02, char c12) {
        sb2.append(g02);
        sb2.append(c12);
        return sb2.toString();
    }

    public static LinkedHashMap w(String str, String str2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(str, str2);
        return linkedHashMap;
    }

    public static List x(Class cls, Archive archive, List list, boolean z12) {
        return archive.add(list, z12, new ClassHandler(cls));
    }

    public static void y(int i12, B b12, int i13, p pVar) {
        b12.H(Integer.valueOf(i12));
        b12.a(Integer.valueOf(i13), pVar);
    }

    public static void z(Class cls, Archive archive, List list, boolean z12) {
        archive.add(list, z12, new ClassHandler(cls));
    }

    @Override // D81.b
    public void a() {
        E41.c cVar = E41.c.f3761l;
    }

    @Override // JK.j
    public Object b(Object obj) {
        switch (this.f289b) {
            case 0:
                break;
            case 1:
                break;
            case 2:
            case 3:
            default:
                break;
            case 4:
                break;
        }
        return G0.f406611a;
    }

    @Override // androidx.core.view.M
    public J0 h(View view, J0 j02) {
        int i12 = j02.e(8).f44778d;
        int i13 = j02.e(2).f44778d;
        int i14 = J.f406821a;
        view.setPadding(0, 0, 0, i12 == 0 ? -i13 : i12 - (i13 * 2));
        return j02;
    }
}
