package a71;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;
import org.jsoup.helper.d;

/* compiled from: StringUtil.java */
/* renamed from: a71.c, reason: case insensitive filesystem */
/* loaded from: classes7.dex */
public final class C19693c {

    /* renamed from: a, reason: collision with root package name */
    public static final String[] f20746a = {"", " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          ", "           ", "            ", "             ", "              ", "               ", "                ", "                 ", "                  ", "                   ", "                    "};

    /* renamed from: b, reason: collision with root package name */
    public static final Stack<StringBuilder> f20747b = new Stack<>();

    public static void a(String str, StringBuilder sb2, boolean z12) {
        int length = str.length();
        int iCharCount = 0;
        boolean z13 = false;
        boolean z14 = false;
        while (iCharCount < length) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (iCodePointAt == 32 || iCodePointAt == 9 || iCodePointAt == 10 || iCodePointAt == 12 || iCodePointAt == 13 || iCodePointAt == 160) {
                if ((!z12 || z13) && !z14) {
                    sb2.append(' ');
                    z14 = true;
                }
            } else if (iCodePointAt != 8203 && iCodePointAt != 173) {
                sb2.appendCodePoint(iCodePointAt);
                z14 = false;
                z13 = true;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
    }

    public static StringBuilder b() {
        StringBuilder sb2;
        Stack<StringBuilder> stack = f20747b;
        synchronized (stack) {
            try {
                sb2 = stack.empty() ? new StringBuilder(8192) : stack.pop();
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return sb2;
    }

    public static boolean c(String str, String... strArr) {
        for (String str2 : strArr) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean d(String str, String[] strArr) {
        return Arrays.binarySearch(strArr, str) >= 0;
    }

    public static boolean e(String str) {
        if (str != null && str.length() != 0) {
            int length = str.length();
            for (int i12 = 0; i12 < length; i12++) {
                if (!f(str.codePointAt(i12))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean f(int i12) {
        return i12 == 32 || i12 == 9 || i12 == 10 || i12 == 12 || i12 == 13;
    }

    public static String g(String str, ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        if (!it.hasNext()) {
            return "";
        }
        String string = it.next().toString();
        if (!it.hasNext()) {
            return string;
        }
        StringBuilder sbB = b();
        sbB.append(string);
        while (it.hasNext()) {
            sbB.append(str);
            sbB.append(it.next());
        }
        return h(sbB);
    }

    public static String h(StringBuilder sb2) {
        d.d(sb2);
        String string = sb2.toString();
        if (sb2.length() > 8192) {
            sb2 = new StringBuilder(8192);
        } else {
            sb2.delete(0, sb2.length());
        }
        Stack<StringBuilder> stack = f20747b;
        synchronized (stack) {
            try {
                stack.push(sb2);
                while (true) {
                    Stack<StringBuilder> stack2 = f20747b;
                    if (stack2.size() > 8) {
                        stack2.pop();
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return string;
    }

    public static URL i(URL url, String str) {
        if (str.startsWith("?")) {
            str = url.getPath() + str;
        }
        if (str.indexOf(46) == 0 && url.getFile().indexOf(47) != 0) {
            url = new URL(url.getProtocol(), url.getHost(), url.getPort(), "/" + url.getFile());
        }
        return new URL(url, str);
    }
}
