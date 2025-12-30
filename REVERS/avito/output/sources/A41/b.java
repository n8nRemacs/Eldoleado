package A41;

import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/* compiled from: SentryServletRequestListener.java */
/* loaded from: classes8.dex */
public class b implements ServletRequestListener {

    /* renamed from: a, reason: collision with root package name */
    public static final org.slf4j.a f96a = org.slf4j.b.d(b.class);

    /* renamed from: b, reason: collision with root package name */
    public static final ThreadLocal<HttpServletRequest> f97b = new ThreadLocal<>();

    public static HttpServletRequest a() {
        return f97b.get();
    }
}
